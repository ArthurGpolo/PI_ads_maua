package com.maua.jogo.model;

/**
 * Representa o REGISTRO de uma resposta dada por um jogador a um desafio.
 * Atende o item "Registro de respostas" exigido na especificação do banco.
 */
public class Resposta {
    private int id;
    private int partidaId;
    private int jogadorId;
    private int desafioId;
    private int opcaoEscolhida;
    private boolean correta;
    private int pontosGanhos;

    public Resposta() {
    }

    public Resposta(int partidaId, int jogadorId, int desafioId,
                    int opcaoEscolhida, boolean correta, int pontosGanhos) {
        this.partidaId = partidaId;
        this.jogadorId = jogadorId;
        this.desafioId = desafioId;
        this.opcaoEscolhida = opcaoEscolhida;
        this.correta = correta;
        this.pontosGanhos = pontosGanhos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPartidaId() { return partidaId; }
    public void setPartidaId(int partidaId) { this.partidaId = partidaId; }

    public int getJogadorId() { return jogadorId; }
    public void setJogadorId(int jogadorId) { this.jogadorId = jogadorId; }

    public int getDesafioId() { return desafioId; }
    public void setDesafioId(int desafioId) { this.desafioId = desafioId; }

    public int getOpcaoEscolhida() { return opcaoEscolhida; }
    public void setOpcaoEscolhida(int opcaoEscolhida) { this.opcaoEscolhida = opcaoEscolhida; }

    public boolean isCorreta() { return correta; }
    public void setCorreta(boolean correta) { this.correta = correta; }

    public int getPontosGanhos() { return pontosGanhos; }
    public void setPontosGanhos(int pontosGanhos) { this.pontosGanhos = pontosGanhos; }

    @Override
    public String toString() {
        return "Resposta{" +
                "jogadorId=" + jogadorId +
                ", desafioId=" + desafioId +
                ", opcaoEscolhida=" + opcaoEscolhida +
                ", correta=" + correta +
                ", pontosGanhos=" + pontosGanhos +
                '}';
    }
}
