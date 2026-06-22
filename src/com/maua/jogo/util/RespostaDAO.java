package com.maua.jogo.util;

import com.maua.jogo.model.Resposta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD do REGISTRO DE RESPOSTAS no banco de dados.
 */
public class RespostaDAO {

    // CREATE
    public static boolean criarResposta(Resposta resposta) {
        String sql = "INSERT INTO respostas (partida_id, jogador_id, desafio_id, opcao_escolhida, correta, pontos_ganhos) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (resposta.getPartidaId() > 0) {
                stmt.setInt(1, resposta.getPartidaId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setInt(2, resposta.getJogadorId());
            stmt.setInt(3, resposta.getDesafioId());
            stmt.setInt(4, resposta.getOpcaoEscolhida());
            stmt.setBoolean(5, resposta.isCorreta());
            stmt.setInt(6, resposta.getPontosGanhos());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    resposta.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao registrar resposta: " + e.getMessage());
        }
        return false;
    }

    // READ - Por ID
    public static Resposta obterRespostaPorId(int id) {
        String sql = "SELECT * FROM respostas WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extrair(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter resposta: " + e.getMessage());
        }
        return null;
    }

    // READ - Por jogador
    public static List<Resposta> obterRespostasPorJogador(int jogadorId) {
        return listar("SELECT * FROM respostas WHERE jogador_id = ? ORDER BY id", jogadorId);
    }

    // READ - Por partida
    public static List<Resposta> obterRespostasPorPartida(int partidaId) {
        return listar("SELECT * FROM respostas WHERE partida_id = ? ORDER BY id", partidaId);
    }

    // UPDATE
    public static boolean atualizarResposta(Resposta resposta) {
        String sql = "UPDATE respostas SET opcao_escolhida = ?, correta = ?, pontos_ganhos = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, resposta.getOpcaoEscolhida());
            stmt.setBoolean(2, resposta.isCorreta());
            stmt.setInt(3, resposta.getPontosGanhos());
            stmt.setInt(4, resposta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar resposta: " + e.getMessage());
        }
        return false;
    }

    // DELETE
    public static boolean deletarResposta(int id) {
        String sql = "DELETE FROM respostas WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar resposta: " + e.getMessage());
        }
        return false;
    }

    private static List<Resposta> listar(String sql, int parametro) {
        List<Resposta> respostas = new ArrayList<>();
        try (Connection conexao = ConexaoBD.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, parametro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                respostas.add(extrair(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar respostas: " + e.getMessage());
        }
        return respostas;
    }

    private static Resposta extrair(ResultSet rs) throws SQLException {
        Resposta r = new Resposta();
        r.setId(rs.getInt("id"));
        r.setPartidaId(rs.getInt("partida_id"));
        r.setJogadorId(rs.getInt("jogador_id"));
        r.setDesafioId(rs.getInt("desafio_id"));
        r.setOpcaoEscolhida(rs.getInt("opcao_escolhida"));
        r.setCorreta(rs.getBoolean("correta"));
        r.setPontosGanhos(rs.getInt("pontos_ganhos"));
        return r;
    }
}
