package com.maua.jogo.model;

/**
 * Classe que representa um jogador
 */
public class Jogador {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int posicao;
    private int pontos;
    private Fase faseAtual;
    private boolean ativo;

    public Jogador() {
        this.posicao = 1;
        this.pontos = 0;
        this.faseAtual = Fase.EXPLORADOR;
        this.ativo = true;
    }

    public Jogador(String nome, String email, String senha) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Jogador(int id, String nome, String email, int posicao, int pontos, Fase faseAtual) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.posicao = posicao;
        this.pontos = pontos;
        this.faseAtual = faseAtual;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }

    public Fase getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(Fase faseAtual) {
        this.faseAtual = faseAtual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void avancarPosicao(int casas) {
        this.posicao += casas;
    }

    public void resetarProgresso() {
        this.posicao = 1;
        this.pontos = 0;
        this.faseAtual = Fase.EXPLORADOR;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", posicao=" + posicao +
                ", pontos=" + pontos +
                ", faseAtual=" + faseAtual.getNome() +
                '}';
    }
}
