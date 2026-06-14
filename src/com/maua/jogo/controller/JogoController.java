package com.maua.jogo.controller;

import com.maua.jogo.model.*;
import com.maua.jogo.util.*;
import java.util.List;

public class JogoController {
    private Jogador jogadorAtual;
    private Partida partidaAtual;
    private Tabuleiro tabuleiro;

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
        }
    }

    public boolean moverJogador(int casas) {
        if (jogadorAtual == null) return false;
        
        int novaPosicao = jogadorAtual.getPosicao() + casas;
        
        if (tabuleiro.eFimDoTabuleiro(novaPosicao)) {
            jogadorAtual.setPosicao(tabuleiro.getTotalCasas());
            finalizarPartida();
            return true;
        }
        
        jogadorAtual.avancarPosicao(casas);
        Fase novaFase = tabuleiro.obterFaseAtual(jogadorAtual.getPosicao());
        jogadorAtual.setFaseAtual(novaFase);
        
        JogadorDAO.atualizarJogador(jogadorAtual);
        return false;
    }

    public Desafio obterDesafioAtual() {
        // Busca o desafio específico da casa (ID do desafio = Posição do jogador)
        return DesafioDAO.obterDesafioPorId(jogadorAtual.getPosicao());
    }

    public boolean responderDesafio(Desafio desafio, int respostaEscolhida) {
        if (desafio.verificarResposta(respostaEscolhida)) {
            jogadorAtual.adicionarPontos(desafio.getPontos());
            if (partidaAtual != null) {
                partidaAtual.setPontuacaoFinal(jogadorAtual.getPontos());
                PartidaDAO.atualizarPartida(partidaAtual);
            }
            JogadorDAO.atualizarJogador(jogadorAtual);
            return true;
        }
        return false;
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

    public void logout() {
        this.jogadorAtual = null;
        this.partidaAtual = null;
    }
}
