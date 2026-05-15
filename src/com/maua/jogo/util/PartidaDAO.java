package com.maua.jogo.util;

import com.maua.jogo.model.Partida;
import com.maua.jogo.model.Jogador;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia todas as operações CRUD com Partidas no banco de dados
 */
public class PartidaDAO {

    // CREATE
    public static boolean criarPartida(Partida partida) {
        String sql = "INSERT INTO partidas (jogador_id, data_inicio, pontuacao_final, concluida) VALUES (?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, partida.getJogador().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(partida.getDataInicio()));
            stmt.setInt(3, partida.getPontuacaoFinal());
            stmt.setBoolean(4, partida.isConcluida());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar partida: " + e.getMessage());
            return false;
        }
    }

    // READ - Por ID
    public static Partida obterPartidaPorId(int id) {
        String sql = "SELECT * FROM partidas WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairPartida(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter partida: " + e.getMessage());
        }
        
        return null;
    }

    // READ - Por Jogador
    public static List<Partida> obterPartidas(int idJogador) {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partidas WHERE jogador_id = ? ORDER BY data_inicio DESC";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, idJogador);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                partidas.add(extrairPartida(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter partidas: " + e.getMessage());
        }
        
        return partidas;
    }

    // READ - Todas as partidas
    public static List<Partida> obterTodasPartidas() {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partidas ORDER BY data_inicio DESC";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                partidas.add(extrairPartida(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter partidas: " + e.getMessage());
        }
        
        return partidas;
    }

    // UPDATE
    public static boolean atualizarPartida(Partida partida) {
        String sql = "UPDATE partidas SET data_fim = ?, pontuacao_final = ?, concluida = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            if (partida.getDataFim() != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(partida.getDataFim()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }
            stmt.setInt(2, partida.getPontuacaoFinal());
            stmt.setBoolean(3, partida.isConcluida());
            stmt.setInt(4, partida.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar partida: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public static boolean deletarPartida(int idPartida) {
        String sql = "DELETE FROM partidas WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, idPartida);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar partida: " + e.getMessage());
            return false;
        }
    }

    // Obter ranking das partidas
    public static List<Object[]> obterRanking() {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT j.nome, MAX(p.pontuacao_final) as maior_pontuacao, COUNT(p.id) as total_partidas " +
                     "FROM partidas p JOIN jogadores j ON p.jogador_id = j.id " +
                     "WHERE p.concluida = true GROUP BY p.jogador_id ORDER BY maior_pontuacao DESC";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] linha = new Object[3];
                linha[0] = rs.getString("nome");
                linha[1] = rs.getInt("maior_pontuacao");
                linha[2] = rs.getInt("total_partidas");
                ranking.add(linha);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao obter ranking: " + e.getMessage());
        }
        
        return ranking;
    }

    // Método auxiliar para extrair Partida de ResultSet
    private static Partida extrairPartida(ResultSet rs) throws SQLException {
        Partida partida = new Partida();
        partida.setId(rs.getInt("id"));
        
        int idJogador = rs.getInt("jogador_id");
        Jogador jogador = JogadorDAO.obterJogadorPorId(idJogador);
        partida.setJogador(jogador);
        
        Timestamp dataInicio = rs.getTimestamp("data_inicio");
        if (dataInicio != null) {
            partida.setDataInicio(dataInicio.toLocalDateTime());
        }
        
        Timestamp dataFim = rs.getTimestamp("data_fim");
        if (dataFim != null) {
            partida.setDataFim(dataFim.toLocalDateTime());
        }
        
        partida.setPontuacaoFinal(rs.getInt("pontuacao_final"));
        partida.setConcluida(rs.getBoolean("concluida"));
        
        return partida;
    }
}
