package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes do estado e das mutações do Jogador. */
public class JogadorTest {

    @Test
    public void jogadorNovoComecaNaPosicao1SemPontosNaFaseExplorador() {
        Jogador j = new Jogador();
        assertEquals(1, j.getPosicao());
        assertEquals(0, j.getPontos());
        assertEquals(Fase.EXPLORADOR, j.getFaseAtual());
        assertTrue(j.isAtivo());
    }

    @Test
    public void construtorComDadosPreencheNomeEmailSenha() {
        Jogador j = new Jogador("Ana", "ana@maua.br", "123");
        assertEquals("Ana", j.getNome());
        assertEquals("ana@maua.br", j.getEmail());
        assertEquals("123", j.getSenha());
    }

    @Test
    public void avancarPosicaoSomaCasas() {
        Jogador j = new Jogador();
        j.avancarPosicao(3);
        assertEquals(4, j.getPosicao());
        j.avancarPosicao(2);
        assertEquals(6, j.getPosicao());
    }

    @Test
    public void adicionarPontosAcumula() {
        Jogador j = new Jogador();
        j.adicionarPontos(10);
        j.adicionarPontos(15);
        assertEquals(25, j.getPontos());
    }

    @Test
    public void resetarProgressoVoltaAoEstadoInicial() {
        Jogador j = new Jogador();
        j.avancarPosicao(10);
        j.adicionarPontos(50);
        j.setFaseAtual(Fase.PLANEJADOR);
        j.resetarProgresso();
        assertEquals(1, j.getPosicao());
        assertEquals(0, j.getPontos());
        assertEquals(Fase.EXPLORADOR, j.getFaseAtual());
    }
}
