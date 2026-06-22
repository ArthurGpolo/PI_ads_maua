package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes das regras do tabuleiro: 30 casas, mapeamento casa->fase e fim de jornada. */
public class TabuleiroTest {

    private final Tabuleiro tabuleiro = new Tabuleiro();

    @Test
    public void tabuleiroTem30Casas() {
        assertEquals(30, tabuleiro.getTotalCasas());
        assertEquals(30, tabuleiro.getCasas().size());
    }

    @Test
    public void casasSaoNumeradasDe1A30EmOrdem() {
        for (int i = 0; i < 30; i++) {
            assertEquals(i + 1, tabuleiro.getCasas().get(i).getPosicao());
        }
    }

    @Test
    public void mapeamentoCasaParaFaseRespeita5CasasPorFase() {
        assertEquals(Fase.EXPLORADOR, tabuleiro.obterFaseAtual(1));
        assertEquals(Fase.EXPLORADOR, tabuleiro.obterFaseAtual(5));
        assertEquals(Fase.CONECTOR, tabuleiro.obterFaseAtual(6));
        assertEquals(Fase.CONECTOR, tabuleiro.obterFaseAtual(10));
        assertEquals(Fase.TRANSFORMADOR, tabuleiro.obterFaseAtual(11));
        assertEquals(Fase.CONHECEDOR, tabuleiro.obterFaseAtual(16));
        assertEquals(Fase.PLANEJADOR, tabuleiro.obterFaseAtual(21));
        assertEquals(Fase.REALIZADOR, tabuleiro.obterFaseAtual(26));
        assertEquals(Fase.REALIZADOR, tabuleiro.obterFaseAtual(30));
    }

    @Test
    public void eFimDoTabuleiroSomenteNaUltimaCasaOuAlem() {
        assertFalse(tabuleiro.eFimDoTabuleiro(29));
        assertTrue(tabuleiro.eFimDoTabuleiro(30));
        assertTrue(tabuleiro.eFimDoTabuleiro(35));
    }

    @Test
    public void obterCasaForaDosLimitesRetornaNull() {
        assertNull(tabuleiro.obterCasa(0));
        assertNull(tabuleiro.obterCasa(31));
        assertNotNull(tabuleiro.obterCasa(1));
        assertEquals(1, tabuleiro.obterCasa(1).getPosicao());
    }
}
