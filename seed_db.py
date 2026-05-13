import sqlite3
from pathlib import Path
import hashlib

DB_PATH = Path(__file__).parent / "jogo.db"

def hash_senha(senha):
    """Criptografa a senha usando SHA256"""
    return hashlib.sha256(senha.encode()).hexdigest()

def seed_database():
    """Insere dados iniciais no banco de dados SQLite"""
    try:
        conn = sqlite3.connect(str(DB_PATH))
        cursor = conn.cursor()
        
        print("📝 Inserindo dados iniciais...")
        
        # Inserir fases
        print("  → Inserindo fases...")
        fases = [
            ("Geografia", "Perguntas sobre geografia", 1),
            ("História", "Perguntas sobre história", 2),
            ("Matemática", "Perguntas sobre matemática", 3),
        ]
        
        for nome, desc, nivel in fases:
            cursor.execute(
                "INSERT INTO fases (nome, descricao, nivel) VALUES (?, ?, ?)",
                (nome, desc, nivel)
            )
        
        # Inserir perguntas
        print("  → Inserindo perguntas...")
        perguntas = [
            (1, "Qual é a capital do Brasil?", "brasília"),
            (1, "Qual é o maior continente?", "ásia"),
            (1, "Quantos continentes existem?", "7"),
            (1, "Qual é a capital da França?", "paris"),
            (1, "Qual é o maior país do mundo?", "rússia"),
            (2, "Em que ano terminou a Segunda Guerra Mundial?", "1945"),
            (2, "Quem foi o primeiro presidente do Brasil?", "deodoro da fonseca"),
            (2, "Em que ano o Brasil foi descoberto?", "1500"),
            (2, "Quem foi Napoleão Bonaparte?", "imperador da frança"),
            (3, "Quanto é 2 + 2?", "4"),
            (3, "Qual é a raiz quadrada de 16?", "4"),
            (3, "Quanto é 10 × 5?", "50"),
            (3, "Quanto é 100 ÷ 5?", "20"),
            (3, "Qual é o dobro de 15?", "30"),
        ]
        
        for fase_id, enunciado, resposta in perguntas:
            cursor.execute(
                "INSERT INTO perguntas (fase_id, enunciado, resposta_correta) VALUES (?, ?, ?)",
                (fase_id, enunciado, resposta)
            )
        
        # Inserir usuários de teste
        print("  → Inserindo usuários de teste...")
        usuarios = [
            ("Jogador Teste", "teste@teste.com", hash_senha("senha123")),
            ("João Silva", "joao@email.com", hash_senha("senha123")),
            ("Maria Santos", "maria@email.com", hash_senha("senha123")),
        ]
        
        for nome, email, senha in usuarios:
            cursor.execute(
                "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)",
                (nome, email, senha)
            )
        
        conn.commit()
        conn.close()
        
        print("✅ Dados iniciais inseridos com sucesso!")
        
    except Exception as err:
        print(f"❌ Erro: {err}")

if __name__ == "__main__":
    seed_database()
