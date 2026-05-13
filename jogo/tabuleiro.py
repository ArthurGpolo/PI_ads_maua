class Casa:
    """Representa uma casa do tabuleiro"""
    
    def __init__(self, numero, fase_id, tipo="normal"):
        self.numero = numero
        self.fase_id = fase_id
        self.tipo = tipo  # normal, premium, sorte
        self.ocupada = False
    
    def __repr__(self):
        return f"Casa({self.numero}, Fase: {self.fase_id}, Tipo: {self.tipo})"


class Tabuleiro:
    """Gerencia o tabuleiro do jogo"""
    
    CASAS_TOTAIS = 30
    FASES_DISPONIVEIS = [1, 2, 3]
    
    def __init__(self):
        self.casas = []
        self.criar_tabuleiro()
    
    def criar_tabuleiro(self):
        """Cria um tabuleiro com 30 casas e fases alternadas"""
        sequencia_fases = [1, 2, 3, 1, 2, 3, 1, 2, 3, 1]
        
        for i in range(self.CASAS_TOTAIS):
            fase_id = sequencia_fases[i % len(sequencia_fases)]
            
            # Algumas casas são especiais
            tipo = "premium" if i % 7 == 0 and i != 0 else "normal"
            tipo = "sorte" if i % 5 == 0 and i != 0 else tipo
            
            self.casas.append(Casa(i, fase_id, tipo))
    
    def obter_casa(self, numero):
        """Retorna uma casa específica do tabuleiro"""
        if 0 <= numero < len(self.casas):
            return self.casas[numero]
        return None
    
    def obter_casas_especiais(self):
        """Retorna todas as casas especiais"""
        return [c for c in self.casas if c.tipo != "normal"]
    
    def __repr__(self):
        return f"Tabuleiro({self.CASAS_TOTAIS} casas)"