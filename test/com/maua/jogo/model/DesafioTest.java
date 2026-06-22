package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes da verificação de resposta do Desafio. */
public class DesafioTest {

    private Desafio desafioComCorretaNoIndice(int indiceCorreto) {
        String[] opcoes = {"A", "B", "C", "D"};
        return new Desafio(1, "Casa 1", "desc", "Pergunta?", opcoes, indiceCorreto, 10, Fase.EXPLORADOR);
    }

    @Test
    public void verificarRespostaCertaRetornaTrue() {
        Desafio d = desafioComCorretaNoIndice(2);
        assertTrue(d.verificarResposta(2));
    }

    @Test
    public void verificarRespostaErradaRetornaFalse() {
        Desafio d = desafioComCorretaNoIndice(2);
        assertFalse(d.verificarResposta(0));
        assertFalse(d.verificarResposta(1));
        assertFalse(d.verificarResposta(3));
    }

    @Test
    public void funcionaParaRespostaCorretaEmQualquerPosicao() {
        // garante que o jogo não depende da correta estar sempre na opção A (índice 0)
        for (int i = 0; i < 4; i++) {
            Desafio d = desafioComCorretaNoIndice(i);
            assertTrue("correta deveria ser o índice " + i, d.verificarResposta(i));
        }
    }

    @Test
    public void gettersRefletemOsDados() {
        Desafio d = desafioComCorretaNoIndice(0);
        assertEquals("Casa 1", d.getTitulo());
        assertEquals("Pergunta?", d.getPergunta());
        assertEquals(10, d.getPontos());
        assertEquals(4, d.getOpcoes().length);
        assertEquals(Fase.EXPLORADOR, d.getFase());
    }
}
