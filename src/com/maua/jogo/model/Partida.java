package com.maua.jogo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma partida/sessão do jogo
 */
public class Partida {
    private int id;
    private Jogador jogador;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private int pontuacaoFinal;
    private boolean concluida;
    private List<String> historico;

    public Partida() {
        this.dataInicio = LocalDateTime.now();
        this.pontuacaoFinal = 0;
        this.concluida = false;
        this.historico = new ArrayList<>();
    }

    public Partida(Jogador jogador) {
        this();
        this.jogador = jogador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public int getPontuacaoFinal() {
        return pontuacaoFinal;
    }

    public void setPontuacaoFinal(int pontuacaoFinal) {
        this.pontuacaoFinal = pontuacaoFinal;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public List<String> getHistorico() {
        return new ArrayList<>(historico);
    }

    public void adicionarEvento(String evento) {
        this.historico.add(LocalDateTime.now() + " - " + evento);
    }

    public void finalizarPartida() {
        this.dataFim = LocalDateTime.now();
        this.concluida = true;
        if (jogador != null) {
            this.pontuacaoFinal = jogador.getPontos();
        }
    }

    public void resetarPartida() {
        this.dataInicio = LocalDateTime.now();
        this.dataFim = null;
        this.pontuacaoFinal = 0;
        this.concluida = false;
        this.historico.clear();
        if (jogador != null) {
            jogador.resetarProgresso();
        }
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", jogador=" + (jogador != null ? jogador.getNome() : "null") +
                ", concluida=" + concluida +
                ", pontuacaoFinal=" + pontuacaoFinal +
                '}';
    }
}
