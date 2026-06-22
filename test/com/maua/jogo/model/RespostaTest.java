package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes do registro de resposta (objeto de domínio). */
public class RespostaTest {

    @Test
    public void construtorPreencheTodosOsCampos() {
        Resposta r = new Resposta(7, 3, 12, 2, true, 20);
        assertEquals(7, r.getPartidaId());
        assertEquals(3, r.getJogadorId());
        assertEquals(12, r.getDesafioId());
        assertEquals(2, r.getOpcaoEscolhida());
        assertTrue(r.isCorreta());
        assertEquals(20, r.getPontosGanhos());
    }

    @Test
    public void respostaIncorretaNaoPontua() {
        Resposta r = new Resposta(7, 3, 12, 1, false, 0);
        assertFalse(r.isCorreta());
        assertEquals(0, r.getPontosGanhos());
    }
}
