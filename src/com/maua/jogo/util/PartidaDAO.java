package com.maua.jogo.util;

import com.maua.jogo.model.Partida;
import com.maua.jogo.model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public static boolean criarPartida(Partida partida) {
        String sql = "INSERT INTO partidas (jogador_id, data_inicio, pontuacao_final, concluida) VALUES (?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, partida.getJogador().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(partida.getDataInicio()));
            stmt.setInt(3, partida.getPontuacaoFinal());
            stmt.setBoolean(4, partida.isConcluida());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    partida.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar partida: " + e.getMessage());
        }
        return false;
    }

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
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar partida: " + e.getMessage());
        }
        return false;
    }

    // READ - Por ID
    public static Partida obterPartidaPorId(int id) {
        String sql = "SELECT * FROM partidas WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extrair(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter partida: " + e.getMessage());
        }
        return null;
    }

    // READ - Por jogador
    public static List<Partida> obterPartidasPorJogador(int jogadorId) {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partidas WHERE jogador_id = ? ORDER BY id";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, jogadorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                partidas.add(extrair(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter partidas: " + e.getMessage());
        }
        return partidas;
    }

    // DELETE
    public static boolean deletarPartida(int id) {
        String sql = "DELETE FROM partidas WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar partida: " + e.getMessage());
        }
        return false;
    }

    private static Partida extrair(ResultSet rs) throws SQLException {
        Partida p = new Partida();
        p.setId(rs.getInt("id"));
        p.setJogador(JogadorDAO.obterJogadorPorId(rs.getInt("jogador_id")));
        Timestamp ini = rs.getTimestamp("data_inicio");
        if (ini != null) p.setDataInicio(ini.toLocalDateTime());
        Timestamp fim = rs.getTimestamp("data_fim");
        if (fim != null) p.setDataFim(fim.toLocalDateTime());
        p.setPontuacaoFinal(rs.getInt("pontuacao_final"));
        p.setConcluida(rs.getBoolean("concluida"));
        return p;
    }

    public static List<Object[]> obterRanking() {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT j.nome, COALESCE(MAX(p.pontuacao_final), 0) as maior_pontuacao, COUNT(p.id) as total_partidas " +
                     "FROM jogadores j " +
                     "LEFT JOIN partidas p ON j.id = p.jogador_id " +
                     "GROUP BY j.id, j.nome " +
                     "ORDER BY maior_pontuacao DESC, total_partidas DESC";
        
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
}
