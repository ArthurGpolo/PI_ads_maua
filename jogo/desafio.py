from banco import BancoDeDados


class Desafio:
    """Gerencia perguntas e desafios do jogo"""
    
    def __init__(self, fase_id):
        self.fase_id = fase_id
        self.db = BancoDeDados()
    
    def obter_pergunta(self):
        """Obtém uma pergunta aleatória da fase"""
        sql = "SELECT * FROM perguntas WHERE fase_id=? ORDER BY RANDOM() LIMIT 1"
        resultado = self.db.consultar_um(sql, (self.fase_id,))
        return resultado if resultado else None
    
    def obter_todas_perguntas(self):
        """Obtém todas as perguntas de uma fase"""
        sql = "SELECT * FROM perguntas WHERE fase_id=?"
        return self.db.consultar(sql, (self.fase_id,))
    
    def verificar_resposta(self, pergunta_id, resposta):
        """Verifica se a resposta está correta"""
        sql = "SELECT resposta_correta FROM perguntas WHERE id=?"
        resultado = self.db.consultar_um(sql, (pergunta_id,))
        
        if resultado:
            return resposta.lower().strip() == resultado['resposta_correta'].lower().strip()
        return False
    
    @staticmethod
    def obter_fases():
        """Retorna todas as fases disponíveis"""
        db = BancoDeDados()
        sql = "SELECT * FROM fases ORDER BY nivel"
        return db.consultar(sql)