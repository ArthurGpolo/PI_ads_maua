package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes do ciclo de vida da Partida. */
public class PartidaTest {

    @Test
    public void partidaNovaComecaEmAbertoComDataInicio() {
        Partida p = new Partida(new Jogador("Bia", "bia@maua.br", "x"));
        assertFalse(p.isConcluida());
        assertEquals(0, p.getPontuacaoFinal());
        assertNotNull(p.getDataInicio());
        assertNull(p.getDataFim());
    }

    @Test
    public void finalizarPartidaMarcaConcluidaECopiaPontuacaoDoJogador() {
        Jogador j = new Jogador("Bia", "bia@maua.br", "x");
        j.adicionarPontos(40);
        Partida p = new Partida(j);

        p.finalizarPartida();

        assertTrue(p.isConcluida());
        assertNotNull(p.getDataFim());
        assertEquals(40, p.getPontuacaoFinal());
    }

    @Test
    public void historicoRegistraEventos() {
        Partida p = new Partida(new Jogador());
        assertTrue(p.getHistorico().isEmpty());
        p.adicionarEvento("Lançou o dado");
        assertEquals(1, p.getHistorico().size());
        assertTrue(p.getHistorico().get(0).contains("Lançou o dado"));
    }

    @Test
    public void resetarPartidaLimpaEstadoEProgressoDoJogador() {
        Jogador j = new Jogador();
        j.avancarPosicao(5);
        j.adicionarPontos(30);
        Partida p = new Partida(j);
        p.finalizarPartida();

        p.resetarPartida();

        assertFalse(p.isConcluida());
        assertEquals(0, p.getPontuacaoFinal());
        assertNull(p.getDataFim());
        assertTrue(p.getHistorico().isEmpty());
        assertEquals(1, j.getPosicao());
        assertEquals(0, j.getPontos());
    }
}
