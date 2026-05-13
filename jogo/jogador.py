from banco import BancoDeDados
import hashlib


class Jogador:
    """Gerencia dados e operações do jogador"""
    
    def __init__(self, nome, email, senha):
        self.id = None
        self.nome = nome
        self.email = email
        self.senha = self._hash_senha(senha)
    
    @staticmethod
    def _hash_senha(senha):
        """Criptografa a senha usando SHA256"""
        return hashlib.sha256(senha.encode()).hexdigest()
    
    def cadastrar(self):
        """Insere um novo jogador no banco de dados"""
        db = BancoDeDados()
        sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)"
        
        if db.executar(sql, (self.nome, self.email, self.senha)):
            self.id = db.obter_ultimo_id()
            print(f"✅ Jogador '{self.nome}' cadastrado com sucesso! (ID: {self.id})")
            return True
        else:
            print("❌ Erro ao cadastrar jogador!")
            return False
    
    def buscar_por_email(self, email):
        """Busca um jogador pelo email"""
        db = BancoDeDados()
        sql = "SELECT * FROM usuarios WHERE email = ?"
        resultado = db.consultar_um(sql, (email,))
        
        if resultado:
            self.id = resultado['id']
            self.nome = resultado['nome']
            self.email = resultado['email']
            return True
        return False
    
    def buscar_por_id(self, jogador_id):
        """Busca um jogador pelo ID"""
        db = BancoDeDados()
        sql = "SELECT * FROM usuarios WHERE id = ?"
        resultado = db.consultar_um(sql, (jogador_id,))
        
        if resultado:
            self.id = resultado['id']
            self.nome = resultado['nome']
            self.email = resultado['email']
            return True
        return False
    
    @staticmethod
    def listar_todos():
        """Lista todos os jogadores cadastrados"""
        db = BancoDeDados()
        sql = "SELECT id, nome, email FROM usuarios ORDER BY nome"
        return db.consultar(sql)
    
    @staticmethod
    def obter_ranking():
        """Obtém o ranking de jogadores"""
        db = BancoDeDados()
        sql = """
            SELECT u.id, u.nome, 
                   COALESCE(SUM(p.pontos), 0) as total_pontos,
                   COUNT(pa.id) as total_partidas
            FROM usuarios u
            LEFT JOIN partidas pa ON pa.usuario_id = u.id
            LEFT JOIN pontuacoes p ON p.partida_id = pa.id
            GROUP BY u.id, u.nome
            ORDER BY total_pontos DESC
        """
        return db.consultar(sql)