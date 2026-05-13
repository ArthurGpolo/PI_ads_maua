import sqlite3
from pathlib import Path


class BancoDeDados:
    """Gerencia conexão e operações com o banco de dados SQLite"""
    
    def __init__(self):
        self.conn = None
        self.cursor = None
        self.db_path = Path(__file__).parent.parent / "jogo.db"
        self.conectar()
    
    def conectar(self):
        """Estabelece conexão com o banco de dados"""
        try:
            self.conn = sqlite3.connect(str(self.db_path))
            self.conn.row_factory = sqlite3.Row  # Retorna resultados como dicts
            self.cursor = self.conn.cursor()
            print("✅ Conectado ao banco de dados SQLite!")
        except Exception as err:
            print(f"❌ Erro de conexão: {err}")
            self.conn = None
            self.cursor = None
    
    def executar(self, sql, parametros=None):
        """Executa comando SQL (INSERT, UPDATE, DELETE)"""
        try:
            if not self.cursor:
                return False
            
            if parametros:
                self.cursor.execute(sql, parametros)
            else:
                self.cursor.execute(sql)
            
            self.conn.commit()
            return True
        except Exception as err:
            print(f"❌ Erro ao executar: {err}")
            return False
    
    def consultar(self, sql, parametros=None):
        """Executa SELECT e retorna todos os resultados"""
        try:
            if not self.cursor:
                return []
            
            if parametros:
                self.cursor.execute(sql, parametros)
            else:
                self.cursor.execute(sql)
            
            return [dict(row) for row in self.cursor.fetchall()]
        except Exception as err:
            print(f"❌ Erro na consulta: {err}")
            return []
    
    def consultar_um(self, sql, parametros=None):
        """Executa SELECT e retorna apenas um resultado"""
        try:
            if not self.cursor:
                return None
            
            if parametros:
                self.cursor.execute(sql, parametros)
            else:
                self.cursor.execute(sql)
            
            row = self.cursor.fetchone()
            return dict(row) if row else None
        except Exception as err:
            print(f"❌ Erro na consulta: {err}")
            return None
    
    def obter_ultimo_id(self):
        """Retorna o ID da última inserção"""
        return self.cursor.lastrowid
    
    def fechar(self):
        """Fecha a conexão com o banco de dados"""
        if self.conn:
            self.cursor.close()
            self.conn.close()
            print("✅ Conexão fechada!")