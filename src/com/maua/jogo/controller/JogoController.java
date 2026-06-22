package com.maua.jogo.controller;

import com.maua.jogo.model.*;
import com.maua.jogo.util.*;
import java.util.List;

public class JogoController {
    /** Pontos concedidos ao errar (participação). */
    public static final int PONTOS_PARTICIPACAO = 5;
    /** Quantos acertos (em pontos equivalentes) são necessários para passar de fase. */
    public static final int ACERTOS_PARA_AVANCAR = 3;

    private Jogador jogadorAtual;
    private Partida partidaAtual;
    private Tabuleiro tabuleiro;

    // Progressão por fase: o jogador responde perguntas da fase atual e acumula
    // pontos NELA até atingir a meta; então avança para a próxima fase.
    private int pontosNaFase = 0;
    private int ultimoDesafioId = -1;
    private java.util.List<Desafio> poolFase;
    private Fase poolFaseCarregada;

    public JogoController() {
        this.tabuleiro = new Tabuleiro();
    }

    public boolean autenticar(String email, String senha) {
        Jogador jogador = JogadorDAO.obterJogadorPorEmailESenha(email, senha);
        if (jogador != null) {
            this.jogadorAtual = jogador;
            return true;
        }
        return false;
    }

    public boolean cadastrarJogador(String nome, String email, String senha) {
        if (JogadorDAO.verificarEmailExistente(email)) return false;
        Jogador novoJogador = new Jogador(nome, email, senha);
        return JogadorDAO.criarJogador(novoJogador);
    }

    public void iniciarPartida() {
        if (jogadorAtual != null) {
            partidaAtual = new Partida(jogadorAtual);
            PartidaDAO.criarPartida(partidaAtual);
            pontosNaFase = 0;
        }
    }

    /** Carrega (com cache) as perguntas da fase atual do jogador. */
    private void carregarPool() {
        Fase fa = jogadorAtual.getFaseAtual();
        if (poolFase == null || poolFaseCarregada != fa) {
            poolFase = DesafioDAO.obterDesafiosPorFase(fa);
            poolFaseCarregada = fa;
        }
    }

    /** Valor (em pontos) de uma pergunta da fase atual. */
    public int valorPergunta() {
        carregarPool();
        if (poolFase != null && !poolFase.isEmpty()) return poolFase.get(0).getPontos();
        return 5 * (jogadorAtual.getFaseAtual().getNumero() + 1);
    }

    /** Meta de pontos a acumular NA FASE para avançar. */
    public int metaFaseAtual() {
        return valorPergunta() * ACERTOS_PARA_AVANCAR;
    }

    public int getPontosNaFase() { return pontosNaFase; }

    public boolean metaAtingida() {
        return pontosNaFase >= metaFaseAtual();
    }

    /** Sorteia uma pergunta da fase atual (evita repetir a imediatamente anterior). */
    public Desafio sortearDesafio() {
        carregarPool();
        if (poolFase == null || poolFase.isEmpty()) return null;
        if (poolFase.size() == 1) return poolFase.get(0);
        Desafio d;
        int guarda = 0;
        do {
            d = poolFase.get((int) (Math.random() * poolFase.size()));
            guarda++;
        } while (d.getId() == ultimoDesafioId && guarda < 12);
        ultimoDesafioId = d.getId();
        return d;
    }

    /** Avança para a próxima fase (zera o progresso da fase). Retorna true se a jornada terminou. */
    public boolean avancarFase() {
        Fase atual = jogadorAtual.getFaseAtual();
        if (atual == Fase.REALIZADOR) {
            finalizarPartida();
            return true;
        }
        jogadorAtual.setFaseAtual(atual.proximaFase());
        jogadorAtual.setPosicao(jogadorAtual.getFaseAtual().getNumero());
        pontosNaFase = 0;
        JogadorDAO.atualizarJogador(jogadorAtual);
        return false;
    }

    public boolean responderDesafio(Desafio desafio, int respostaEscolhida) {
        boolean correta = desafio.verificarResposta(respostaEscolhida);
        // Acerto: pontos cheios do desafio. Erro: pontos de participação.
        int pontosGanhos = correta ? desafio.getPontos() : PONTOS_PARTICIPACAO;

        jogadorAtual.adicionarPontos(pontosGanhos);
        pontosNaFase += pontosGanhos;
        if (partidaAtual != null) {
            partidaAtual.setPontuacaoFinal(jogadorAtual.getPontos());
            PartidaDAO.atualizarPartida(partidaAtual);
        }
        JogadorDAO.atualizarJogador(jogadorAtual);

        // REGISTRO DE RESPOSTAS (exigência do banco): grava qual opção o jogador
        // escolheu, se acertou e quantos pontos ganhou.
        Resposta registro = new Resposta(
                partidaAtual != null ? partidaAtual.getId() : 0,
                jogadorAtual.getId(),
                desafio.getId(),
                respostaEscolhida,
                correta,
                pontosGanhos);
        RespostaDAO.criarResposta(registro);

        return correta;
    }

    public void finalizarPartida() {
        if (partidaAtual != null) {
            partidaAtual.finalizarPartida();
            partidaAtual.setPontuacaoFinal(jogadorAtual.getPontos());
            PartidaDAO.atualizarPartida(partidaAtual);
        }
    }

    public List<Object[]> obterRanking() {
        return PartidaDAO.obterRanking();
    }

    public Jogador getJogadorAtual() { return jogadorAtual; }
    public Partida getPartidaAtual() { return partidaAtual; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }

    /** Zera o progresso do jogador (volta à fase Explorador, 0 pontos) e inicia nova partida. */
    public void reiniciarJogo() {
        if (jogadorAtual == null) return;
        jogadorAtual.resetarProgresso(); // posição 1, pontos 0, fase Explorador
        pontosNaFase = 0;
        ultimoDesafioId = -1;
        poolFase = null;
        poolFaseCarregada = null;
        JogadorDAO.atualizarJogador(jogadorAtual);
        iniciarPartida();
    }

    public void logout() {
        this.jogadorAtual = null;
        this.partidaAtual = null;
    }
}
