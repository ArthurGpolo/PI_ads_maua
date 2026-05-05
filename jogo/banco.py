import mysql.connector
from config import DB_CONFIG


class BancoDeDados:
    def __init__(self):
        try:
            self.conn = mysql.connector.connect(**DB_CONFIG)
            self.cursor = self.conn.cursor()
            print("✅ Conectado ao MySQL!")

        except mysql.connector.Error as err:
            print(f"❌ Erro ao conectar: {err}")
            self.conn = None
            self.cursor = None

    def executar(self, sql, valores=None):
        if not self.conn:
            return

        self.cursor.execute(sql, valores or ())
        self.conn.commit()

    def consultar(self, sql, valores=None):
        if not self.conn:
            return []

        self.cursor.execute(sql, valores or ())
        return self.cursor.fetchall()

    def fechar(self):
        if self.conn:
            self.cursor.close()
            self.conn.close()