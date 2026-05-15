package com.maua.jogo.util;

import com.maua.jogo.model.Desafio;
import com.maua.jogo.model.Fase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia todas as operações CRUD com Desafios no banco de dados
 */
public class DesafioDAO {

    // CREATE
    public static boolean criarDesafio(Desafio desafio) {
        String sql = "INSERT INTO desafios (titulo, descricao, pergunta, opcao_a, opcao_b, opcao_c, opcao_d, resposta_correta, pontos, fase_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, desafio.getTitulo());
            stmt.setString(2, desafio.getDescricao());
            stmt.setString(3, desafio.getPergunta());
            stmt.setString(4, desafio.getOpcoes()[0]);
            stmt.setString(5, desafio.getOpcoes()[1]);
            stmt.setString(6, desafio.getOpcoes()[2]);
            stmt.setString(7, desafio.getOpcoes()[3]);
            stmt.setInt(8, desafio.getRespostaCorreta());
            stmt.setInt(9, desafio.getPontos());
            stmt.setInt(10, desafio.getFase().getNumero());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar desafio: " + e.getMessage());
            return false;
        }
    }

    // READ - Por ID
    public static Desafio obterDesafioPorId(int id) {
        String sql = "SELECT * FROM desafios WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairDesafio(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter desafio: " + e.getMessage());
        }
        
        return null;
    }

    // READ - Por Fase
    public static List<Desafio> obterDesafiosPorFase(Fase fase) {
        List<Desafio> desafios = new ArrayList<>();
        String sql = "SELECT * FROM desafios WHERE fase_id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, fase.getNumero());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                desafios.add(extrairDesafio(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter desafios: " + e.getMessage());
        }
        
        return desafios;
    }

    // READ - Todos os desafios
    public static List<Desafio> obterTodosDesafios() {
        List<Desafio> desafios = new ArrayList<>();
        String sql = "SELECT * FROM desafios";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                desafios.add(extrairDesafio(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter desafios: " + e.getMessage());
        }
        
        return desafios;
    }

    // UPDATE
    public static boolean atualizarDesafio(Desafio desafio) {
        String sql = "UPDATE desafios SET titulo = ?, descricao = ?, pergunta = ?, opcao_a = ?, opcao_b = ?, opcao_c = ?, opcao_d = ?, resposta_correta = ?, pontos = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, desafio.getTitulo());
            stmt.setString(2, desafio.getDescricao());
            stmt.setString(3, desafio.getPergunta());
            stmt.setString(4, desafio.getOpcoes()[0]);
            stmt.setString(5, desafio.getOpcoes()[1]);
            stmt.setString(6, desafio.getOpcoes()[2]);
            stmt.setString(7, desafio.getOpcoes()[3]);
            stmt.setInt(8, desafio.getRespostaCorreta());
            stmt.setInt(9, desafio.getPontos());
            stmt.setInt(10, desafio.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar desafio: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public static boolean deletarDesafio(int idDesafio) {
        String sql = "DELETE FROM desafios WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, idDesafio);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar desafio: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para extrair Desafio de ResultSet
    private static Desafio extrairDesafio(ResultSet rs) throws SQLException {
        String[] opcoes = new String[4];
        opcoes[0] = rs.getString("opcao_a");
        opcoes[1] = rs.getString("opcao_b");
        opcoes[2] = rs.getString("opcao_c");
        opcoes[3] = rs.getString("opcao_d");
        
        Desafio desafio = new Desafio();
        desafio.setId(rs.getInt("id"));
        desafio.setTitulo(rs.getString("titulo"));
        desafio.setDescricao(rs.getString("descricao"));
        desafio.setPergunta(rs.getString("pergunta"));
        desafio.setOpcoes(opcoes);
        desafio.setRespostaCorreta(rs.getInt("resposta_correta"));
        desafio.setPontos(rs.getInt("pontos"));
        desafio.setFase(Fase.getFasePorNumero(rs.getInt("fase_id")));
        
        return desafio;
    }
}
