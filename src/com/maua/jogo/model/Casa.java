package com.maua.jogo.model;

/**
 * Classe que representa uma casa/posição no tabuleiro
 */
public class Casa {
    private int posicao;
    private Fase fase;
    private Desafio desafio;
    private String descricao;

    public Casa(int posicao, Fase fase, String descricao) {
        this.posicao = posicao;
        this.fase = fase;
        this.descricao = descricao;
    }

    public Casa(int posicao, Fase fase, Desafio desafio, String descricao) {
        this.posicao = posicao;
        this.fase = fase;
        this.desafio = desafio;
        this.descricao = descricao;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean temDesafio() {
        return desafio != null;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "posicao=" + posicao +
                ", fase=" + fase.getNome() +
                ", temDesafio=" + temDesafio() +
                '}';
    }
}
