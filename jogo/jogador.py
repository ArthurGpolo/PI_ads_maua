from banco import BancoDeDados


class Jogador:
    def __init__(self, nome, email, senha):
        self.nome = nome
        self.email = email
        self.senha = senha
        self.db = BancoDeDados()

    def cadastrar(self):
        sql = """
        INSERT INTO usuarios (nome, email, senha)
        VALUES (%s, %s, %s)
        """
        self.db.executar(sql, (self.nome, self.email, self.senha))
        print("✅ Jogador cadastrado com sucesso!")

    def listar(self):
        sql = "SELECT * FROM usuarios"
        return self.db.consultar(sql)