package com.maua.jogo.model;

import org.junit.Test;
import static org.junit.Assert.*;

/** Testes das regras da enum Fase (sem banco de dados). */
public class FaseTest {

    @Test
    public void getFasePorNumeroRetornaFaseCorreta() {
        assertEquals(Fase.EXPLORADOR, Fase.getFasePorNumero(1));
        assertEquals(Fase.CONECTOR, Fase.getFasePorNumero(2));
        assertEquals(Fase.REALIZADOR, Fase.getFasePorNumero(6));
    }

    @Test
    public void getFasePorNumeroInvalidoUsaExploradorComoPadrao() {
        assertEquals(Fase.EXPLORADOR, Fase.getFasePorNumero(0));
        assertEquals(Fase.EXPLORADOR, Fase.getFasePorNumero(99));
        assertEquals(Fase.EXPLORADOR, Fase.getFasePorNumero(-1));
    }

    @Test
    public void proximaFaseAvancaUmNivel() {
        assertEquals(Fase.CONECTOR, Fase.EXPLORADOR.proximaFase());
        assertEquals(Fase.TRANSFORMADOR, Fase.CONECTOR.proximaFase());
        assertEquals(Fase.REALIZADOR, Fase.PLANEJADOR.proximaFase());
    }

    @Test
    public void proximaFaseNaUltimaFasePermaneceRealizador() {
        assertEquals(Fase.REALIZADOR, Fase.REALIZADOR.proximaFase());
    }

    @Test
    public void numeroENomeBatemComADefinicao() {
        assertEquals(1, Fase.EXPLORADOR.getNumero());
        assertEquals(6, Fase.REALIZADOR.getNumero());
        assertEquals("Explorador", Fase.EXPLORADOR.getNome());
        assertEquals("Realizador", Fase.REALIZADOR.getNome());
    }
}
