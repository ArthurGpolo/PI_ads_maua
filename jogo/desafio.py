from banco import BancoDeDados

class Desafio:
    def __init__(self, fase_id):
        self.fase_id = fase_id

    def obter_pergunta(self):
        db = BancoDeDados()
        perguntas = db.buscar(
            "SELECT * FROM perguntas WHERE fase_id=%s ORDER BY RAND() LIMIT 1",
            (self.fase_id,)
        )
        return perguntas[0] if perguntas else None