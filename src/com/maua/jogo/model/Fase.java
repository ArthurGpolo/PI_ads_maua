package com.maua.jogo.model;

/**
 * Classe que representa as fases da jornada profissional
 */
public enum Fase {
    EXPLORADOR(1, "Explorador"),
    CONECTOR(2, "Conector"),
    TRANSFORMADOR(3, "Transformador"),
    CONHECEDOR(4, "Conhecedor"),
    PLANEJADOR(5, "Planejador"),
    REALIZADOR(6, "Realizador");

    private int numero;
    private String nome;

    Fase(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public static Fase getFasePorNumero(int numero) {
        for (Fase fase : Fase.values()) {
            if (fase.numero == numero) {
                return fase;
            }
        }
        return EXPLORADOR;
    }

    public Fase proximaFase() {
        if (this == REALIZADOR) {
            return REALIZADOR;
        }
        return Fase.values()[this.ordinal() + 1];
    }
}
