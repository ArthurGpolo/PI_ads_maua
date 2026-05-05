import random
from banco import BancoDeDados
from desafio import Desafio

class Partida:
    def __init__(self, jogador_id):
        self.jogador_id = jogador_id
        self.posicao = 0
        self.id = None

    def iniciar(self):
        db = BancoDeDados()
        db.executar(
            "INSERT INTO partidas (usuario_id, fase_atual_id) VALUES (%s, %s)",
            (self.jogador_id, 1)
        )
        self.id = db.cursor.lastrowid

    def jogar_turno(self, tabuleiro):
        dado = random.randint(1, 6)
        self.posicao += dado

        print(f"🎲 Você tirou {dado} e foi para casa {self.posicao}")

        casa = tabuleiro.casas[self.posicao % len(tabuleiro.casas)]

        desafio = Desafio(casa.fase_id)
        pergunta = desafio.obter_pergunta()

        if pergunta:
            print("Pergunta:", pergunta["enunciado"])
            resposta = input("Resposta: ")

            correta = resposta.lower() in ["brasília", "4"]  # simplificado

            db = BancoDeDados()
            db.executar("""
                INSERT INTO respostas (partida_id, pergunta_id, resposta_dada, correta)
                VALUES (%s, %s, %s, %s)
            """, (self.id, pergunta["id"], resposta, correta))

            if correta:
                print("✅ Correto! +100 pontos")
                db.executar(
                    "INSERT INTO pontuacoes (partida_id, pontos) VALUES (%s, %s)",
                    (self.id, 100)
                )
            else:
                print("❌ Errado!")