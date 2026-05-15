package com.maua.jogo.controller;

import com.maua.jogo.model.*;
import com.maua.jogo.util.*;
import java.util.List;

/**
 * Controlador principal do jogo
 */
public class JogoController {
    private Jogador jogadorAtual;
    private Partida partidaAtual;
    private Tabuleiro tabuleiro;
    private List<Desafio> desafios;

    public JogoController() {
        this.tabuleiro = new Tabuleiro();
        this.desafios = DesafioDAO.obterTodosDesafios();
    }

    // Login
    public boolean autenticar(String email, String senha) {
        Jogador jogador = JogadorDAO.obterJogadorPorEmailESenha(email, senha);
        if (jogador != null) {
            this.jogadorAtual = jogador;
            return true;
        }
        return false;
    }

    // Cadastro
    public boolean cadastrarJogador(String nome, String email, String senha) {
        if (JogadorDAO.verificarEmailExistente(email)) {
            return false;
        }
        
        Jogador novoJogador = new Jogador(nome, email, senha);
        return JogadorDAO.criarJogador(novoJogador);
    }

    // Iniciar nova partida
    public void iniciarPartida() {
        if (jogadorAtual != null) {
            jogadorAtual.resetarProgresso();
            partidaAtual = new Partida(jogadorAtual);
            PartidaDAO.criarPartida(partidaAtual);
            partidaAtual.adicionarEvento("Partida iniciada na fase " + jogadorAtual.getFaseAtual().getNome());
        }
    }

    // Mover jogador
    public boolean moverJogador(int casas) {
        if (jogadorAtual == null) return false;
        
        int novaPosicao = jogadorAtual.getPosicao() + casas;
        
        if (tabuleiro.eFimDoTabuleiro(novaPosicao)) {
            finalizarPartida();
            return true;
        }
        
        jogadorAtual.avancarPosicao(casas);
        Fase novaFase = tabuleiro.obterFaseAtual(jogadorAtual.getPosicao());
        jogadorAtual.setFaseAtual(novaFase);
        
        partidaAtual.adicionarEvento("Jogador moveu " + casas + " casas. Posição: " + jogadorAtual.getPosicao());
        
        JogadorDAO.atualizarJogador(jogadorAtual);
        
        return false;
    }

    // Obter desafio da casa atual
    public Desafio obterDesafioAtual() {
        Casa casa = tabuleiro.obterCasa(jogadorAtual.getPosicao());
        if (casa != null && casa.temDesafio()) {
            return casa.getDesafio();
        }
        
        // Se não tem desafio, procura um da fase atual
        List<Desafio> desafiosFase = DesafioDAO.obterDesafiosPorFase(jogadorAtual.getFaseAtual());
        if (!desafiosFase.isEmpty()) {
            return desafiosFase.get(0);
        }
        
        return null;
    }

    // Responder desafio
    public boolean responderDesafio(Desafio desafio, int respostaEscolhida) {
        if (desafio.verificarResposta(respostaEscolhida)) {
            int pontos = desafio.getPontos();
            jogadorAtual.adicionarPontos(pontos);
            partidaAtual.adicionarEvento("Desafio " + desafio.getTitulo() + " respondido corretamente. Ganhou " + pontos + " pontos!");
            JogadorDAO.atualizarJogador(jogadorAtual);
            return true;
        } else {
            partidaAtual.adicionarEvento("Desafio " + desafio.getTitulo() + " respondido incorretamente.");
            return false;
        }
    }

    // Finalizar partida
    public void finalizarPartida() {
        if (partidaAtual != null) {
            partidaAtual.finalizarPartida();
            PartidaDAO.atualizarPartida(partidaAtual);
            partidaAtual.adicionarEvento("Partida finalizada. Pontuação final: " + jogadorAtual.getPontos());
        }
    }

    // Obter ranking
    public List<Object[]> obterRanking() {
        return PartidaDAO.obterRanking();
    }

    // Getters
    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public Partida getPartidaAtual() {
        return partidaAtual;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public List<Desafio> getDesafios() {
        return desafios;
    }

    public void logout() {
        this.jogadorAtual = null;
        this.partidaAtual = null;
    }
}
