import random
from datetime import datetime
from banco import BancoDeDados
from desafio import Desafio


class Partida:
    """Gerencia uma partida do jogo"""
    
    def __init__(self, jogador_id):
        self.id = None
        self.jogador_id = jogador_id
        self.posicao = 0
        self.pontos = 0
        self.respostas_corretas = 0
        self.respostas_erradas = 0
        self.db = BancoDeDados()
    
    def iniciar(self, fase_inicial=1):
        """Inicia uma nova partida"""
        sql = "INSERT INTO partidas (usuario_id, fase_atual_id) VALUES (?, ?)"
        
        if self.db.executar(sql, (self.jogador_id, fase_inicial)):
            self.id = self.db.obter_ultimo_id()
            
            # Criar registro de pontuação
            sql_pontos = "INSERT INTO pontuacoes (partida_id, pontos) VALUES (?, ?)"
            self.db.executar(sql_pontos, (self.id, 0))
            
            print(f"\n🎮 Partida iniciada! ID: {self.id}")
            return True
        return False
    
    def jogar_turno(self, tabuleiro):
        """Realiza um turno da partida"""
        if not self.id:
            print("❌ Partida não foi iniciada!")
            return False
        
        # Rolar dado
        dado = random.randint(1, 6)
        print(f"\n🎲 Você tirou {dado}!")
        
        # Atualizar posição
        self.posicao += dado
        if self.posicao > 29:
            print("\n🎉 VOCÊ VENCEU! Chegou ao final do tabuleiro!")
            self.finalizar_partida()
            return True
        
        # Obter casa
        casa = tabuleiro.obter_casa(self.posicao)
        if not casa:
            print("❌ Casa inválida!")
            return False
        
        print(f"📍 Você foi para a casa {self.posicao} (Fase: {casa.fase_id}, Tipo: {casa.tipo})")
        
        # Obter desafio
        desafio = Desafio(casa.fase_id)
        pergunta = desafio.obter_pergunta()
        
        if not pergunta:
            print(f"⚠️  Nenhuma pergunta disponível para a Fase {casa.fase_id}")
            return False
        
        # Fazer pergunta
        print(f"\n❓ {pergunta['enunciado']}")
        resposta = input("Sua resposta: ").strip()
        
        # Verificar resposta
        correta = desafio.verificar_resposta(pergunta['id'], resposta)
        
        # Registrar resposta
        sql_resposta = """
            INSERT INTO respostas (partida_id, pergunta_id, resposta_dada, correta)
            VALUES (?, ?, ?, ?)
        """
        self.db.executar(sql_resposta, (self.id, pergunta['id'], resposta, int(correta)))
        
        # Calcular pontos
        if correta:
            pontos_ganhos = 10 if casa.tipo == "normal" else (20 if casa.tipo == "premium" else 15)
            self.pontos += pontos_ganhos
            self.respostas_corretas += 1
            print(f"✅ Correto! +{pontos_ganhos} pontos")
        else:
            self.respostas_erradas += 1
            print(f"❌ Errado! A resposta correta era: {pergunta['resposta_correta']}")
        
        # Atualizar pontuação no banco
        self._atualizar_pontuacao()
        print(f"📊 Pontos: {self.pontos} | Acertos: {self.respostas_corretas} | Erros: {self.respostas_erradas}")
        
        return False
    
    def _atualizar_pontuacao(self):
        """Atualiza a pontuação no banco de dados"""
        sql = "UPDATE pontuacoes SET pontos = ? WHERE partida_id = ?"
        self.db.executar(sql, (self.pontos, self.id))
    
    def finalizar_partida(self):
        """Finaliza a partida"""
        sql_partida = "UPDATE partidas SET data_fim = CURRENT_TIMESTAMP WHERE id = ?"
        self.db.executar(sql_partida, (self.id,))
        
        # Garantir que pontuação está salva
        sql_pontos = "SELECT * FROM pontuacoes WHERE partida_id = ?"
        resultado = self.db.consultar_um(sql_pontos, (self.id,))
        
        if not resultado:
            sql_insert = "INSERT INTO pontuacoes (partida_id, pontos) VALUES (?, ?)"
            self.db.executar(sql_insert, (self.id, self.pontos))
        else:
            sql_update = "UPDATE pontuacoes SET pontos = ? WHERE partida_id = ?"
            self.db.executar(sql_update, (self.pontos, self.id))
        
        print(f"\n🏁 Partida finalizada! Pontuação final: {self.pontos}")
    
    def obter_historico(self):
        """Obtém o histórico de respostas da partida"""
        sql = "SELECT * FROM respostas WHERE partida_id = ? ORDER BY data_resposta"
        return self.db.consultar(sql, (self.id,))
    
    def __repr__(self):
        return f"Partida(ID: {self.id}, Jogador: {self.jogador_id}, Posição: {self.posicao}, Pontos: {self.pontos})"