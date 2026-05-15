package com.maua.jogo.util;

import com.maua.jogo.model.Jogador;
import com.maua.jogo.model.Fase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia todas as operações CRUD com Jogadores no banco de dados
 */
public class JogadorDAO {

    // CREATE
    public static boolean criarJogador(Jogador jogador) {
        String sql = "INSERT INTO jogadores (nome, email, senha, posicao, pontos, fase_id, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, jogador.getNome());
            stmt.setString(2, jogador.getEmail());
            stmt.setString(3, jogador.getSenha());
            stmt.setInt(4, jogador.getPosicao());
            stmt.setInt(5, jogador.getPontos());
            stmt.setInt(6, jogador.getFaseAtual().getNumero());
            stmt.setBoolean(7, jogador.isAtivo());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar jogador: " + e.getMessage());
            return false;
        }
    }

    // READ - Por ID
    public static Jogador obterJogadorPorId(int id) {
        String sql = "SELECT * FROM jogadores WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairJogador(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter jogador: " + e.getMessage());
        }
        
        return null;
    }

    // READ - Por Email e Senha (Login)
    public static Jogador obterJogadorPorEmailESenha(String email, String senha) {
        String sql = "SELECT * FROM jogadores WHERE email = ? AND senha = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairJogador(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar jogador: " + e.getMessage());
        }
        
        return null;
    }

    // READ - Todos os jogadores
    public static List<Jogador> obterTodosJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores WHERE ativo = true";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                jogadores.add(extrairJogador(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter jogadores: " + e.getMessage());
        }
        
        return jogadores;
    }

    // UPDATE
    public static boolean atualizarJogador(Jogador jogador) {
        String sql = "UPDATE jogadores SET nome = ?, email = ?, posicao = ?, pontos = ?, fase_id = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, jogador.getNome());
            stmt.setString(2, jogador.getEmail());
            stmt.setInt(3, jogador.getPosicao());
            stmt.setInt(4, jogador.getPontos());
            stmt.setInt(5, jogador.getFaseAtual().getNumero());
            stmt.setInt(6, jogador.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar jogador: " + e.getMessage());
            return false;
        }
    }

    // DELETE (Soft Delete)
    public static boolean deletarJogador(int idJogador) {
        String sql = "UPDATE jogadores SET ativo = false WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, idJogador);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar jogador: " + e.getMessage());
            return false;
        }
    }

    // Verificar se email existe
    public static boolean verificarEmailExistente(String email) {
        String sql = "SELECT COUNT(*) as total FROM jogadores WHERE email = ? AND ativo = true";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao verificar email: " + e.getMessage());
        }
        
        return false;
    }

    // Método auxiliar para extrair Jogador de ResultSet
    private static Jogador extrairJogador(ResultSet rs) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setId(rs.getInt("id"));
        jogador.setNome(rs.getString("nome"));
        jogador.setEmail(rs.getString("email"));
        jogador.setSenha(rs.getString("senha"));
        jogador.setPosicao(rs.getInt("posicao"));
        jogador.setPontos(rs.getInt("pontos"));
        jogador.setFaseAtual(Fase.getFasePorNumero(rs.getInt("fase_id")));
        jogador.setAtivo(rs.getBoolean("ativo"));
        return jogador;
    }
}
