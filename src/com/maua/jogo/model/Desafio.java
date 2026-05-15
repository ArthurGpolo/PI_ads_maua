package com.maua.jogo.model;

/**
 * Classe que representa um desafio no jogo
 */
public class Desafio {
    private int id;
    private String titulo;
    private String descricao;
    private String pergunta;
    private String[] opcoes;
    private int respostaCorreta;
    private int pontos;
    private Fase fase;

    public Desafio() {
    }

    public Desafio(int id, String titulo, String descricao, String pergunta, 
                   String[] opcoes, int respostaCorreta, int pontos, Fase fase) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
        this.pontos = pontos;
        this.fase = fase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(String[] opcoes) {
        this.opcoes = opcoes;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(int respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public boolean verificarResposta(int resposta) {
        return resposta == this.respostaCorreta;
    }

    @Override
    public String toString() {
        return "Desafio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", pergunta='" + pergunta + '\'' +
                ", pontos=" + pontos +
                '}';
    }
}
