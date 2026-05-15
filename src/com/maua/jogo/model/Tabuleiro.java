package com.maua.jogo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o tabuleiro do jogo
 */
public class Tabuleiro {
    private List<Casa> casas;
    private int totalCasas;
    private static final int CASAS_POR_FASE = 5;

    public Tabuleiro() {
        this.totalCasas = CASAS_POR_FASE * 6; // 6 fases
        this.casas = new ArrayList<>();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        int posicao = 1;
        Fase[] fases = Fase.values();

        for (Fase fase : fases) {
            for (int i = 0; i < CASAS_POR_FASE; i++) {
                Casa casa = new Casa(posicao, fase, "Posição " + posicao);
                casas.add(casa);
                posicao++;
            }
        }
    }

    public Casa obterCasa(int posicao) {
        if (posicao > 0 && posicao <= totalCasas) {
            return casas.get(posicao - 1);
        }
        return null;
    }

    public int getTotalCasas() {
        return totalCasas;
    }

    public List<Casa> getCasas() {
        return new ArrayList<>(casas);
    }

    public void adicionarDesafioNaCasa(int posicao, Desafio desafio) {
        Casa casa = obterCasa(posicao);
        if (casa != null) {
            casa.setDesafio(desafio);
        }
    }

    public Fase obterFaseAtual(int posicao) {
        Casa casa = obterCasa(posicao);
        if (casa != null) {
            return casa.getFase();
        }
        return Fase.EXPLORADOR;
    }

    public boolean eFimDoTabuleiro(int posicao) {
        return posicao >= totalCasas;
    }

    @Override
    public String toString() {
        return "Tabuleiro{" +
                "totalCasas=" + totalCasas +
                '}';
    }
}
