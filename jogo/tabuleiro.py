class Casa:
    def __init__(self, numero, fase_id):
        self.numero = numero
        self.fase_id = fase_id

class Tabuleiro:
    def __init__(self):
        self.casas = []
        self.criar_tabuleiro()

    def criar_tabuleiro(self):
        fases = [1, 2, 3, 1, 2, 3]
        for i in range(30):
            fase_id = fases[i % len(fases)]
            self.casas.append(Casa(i, fase_id))