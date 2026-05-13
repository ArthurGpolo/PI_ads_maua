import sqlite3
from pathlib import Path

DB_PATH = Path(__file__).parent / "jogo.db"

def setup_database():
    """Cria o banco de dados e as tabelas SQLite"""
    try:
        # Conectar ao banco (cria se não existir)
        conn = sqlite3.connect(str(DB_PATH))
        cursor = conn.cursor()
        
        print("🔧 Criando banco de dados SQLite...")
        
        # Deletar tabelas antigas se existirem (para começar do zero)
        tables = [
            'respostas', 'pontuacoes', 'perguntas', 
            'partidas', 'fases', 'usuarios'
        ]
        for table in tables:
            cursor.execute(f"DROP TABLE IF EXISTS {table}")
        
        # =========================
        # USUÁRIOS
        # =========================
        cursor.execute("""
        CREATE TABLE usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome VARCHAR(120) NOT NULL,
            email VARCHAR(180) NOT NULL UNIQUE,
            senha VARCHAR(255) NOT NULL,
            data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
        """)
        print("  ✓ Tabela 'usuarios' criada")
        
        # =========================
        # FASES DO JOGO
        # =========================
        cursor.execute("""
        CREATE TABLE fases (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome VARCHAR(60) NOT NULL,
            descricao TEXT,
            nivel INTEGER NOT NULL
        )
        """)
        print("  ✓ Tabela 'fases' criada")
        
        # =========================
        # PARTIDAS
        # =========================
        cursor.execute("""
        CREATE TABLE partidas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER NOT NULL,
            fase_atual_id INTEGER,
            data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            data_fim TIMESTAMP NULL,
            FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
            FOREIGN KEY (fase_atual_id) REFERENCES fases(id)
        )
        """)
        print("  ✓ Tabela 'partidas' criada")
        
        # =========================
        # PERGUNTAS
        # =========================
        cursor.execute("""
        CREATE TABLE perguntas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            fase_id INTEGER NOT NULL,
            enunciado TEXT NOT NULL,
            resposta_correta VARCHAR(255) NOT NULL,
            FOREIGN KEY (fase_id) REFERENCES fases(id)
        )
        """)
        print("  ✓ Tabela 'perguntas' criada")
        
        # =========================
        # RESPOSTAS DO JOGADOR
        # =========================
        cursor.execute("""
        CREATE TABLE respostas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            partida_id INTEGER NOT NULL,
            pergunta_id INTEGER NOT NULL,
            resposta_dada VARCHAR(255) NOT NULL,
            correta INTEGER NOT NULL,
            data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (partida_id) REFERENCES partidas(id),
            FOREIGN KEY (pergunta_id) REFERENCES perguntas(id),
            UNIQUE(partida_id, pergunta_id)
        )
        """)
        print("  ✓ Tabela 'respostas' criada")
        
        # =========================
        # PONTUAÇÃO
        # =========================
        cursor.execute("""
        CREATE TABLE pontuacoes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            partida_id INTEGER NOT NULL UNIQUE,
            pontos INTEGER NOT NULL DEFAULT 0,
            FOREIGN KEY (partida_id) REFERENCES partidas(id)
        )
        """)
        print("  ✓ Tabela 'pontuacoes' criada")
        
        # =========================
        # ÍNDICES
        # =========================
        cursor.execute("CREATE INDEX idx_usuario_email ON usuarios(email)")
        cursor.execute("CREATE INDEX idx_partida_usuario ON partidas(usuario_id)")
        cursor.execute("CREATE INDEX idx_pergunta_fase ON perguntas(fase_id)")
        cursor.execute("CREATE INDEX idx_resposta_partida ON respostas(partida_id)")
        print("  ✓ Índices criados")
        
        conn.commit()
        conn.close()
        
        print(f"\n✅ Banco de dados criado com sucesso em: {DB_PATH}")
        
    except Exception as err:
        print(f"❌ Erro: {err}")

if __name__ == "__main__":
    setup_database()
