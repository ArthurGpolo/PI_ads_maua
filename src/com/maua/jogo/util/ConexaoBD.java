package com.maua.jogo.util;

import java.sql.*;

/**
 * Classe utilitária para gerenciar a conexão com o banco de dados MySQL
 */
public class ConexaoBD {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jogo_maua";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver MySQL não encontrado!");
        }
    }

    public static Connection obterConexao() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fecharRecursos(Connection conexao, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conexao != null) conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean testarConexao() {
        try {
            Connection conexao = obterConexao();
            fecharConexao(conexao);
            return true;
        } catch (SQLException e) {
            System.out.println("Falha ao testar conexão: " + e.getMessage());
            return false;
        }
    }
}
