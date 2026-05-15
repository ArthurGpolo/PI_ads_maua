package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela do Menu Principal
 */
public class TelaMenu extends JFrame {
    private JogoController controller;

    public TelaMenu(JogoController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Jogo de Tabuleiro - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Bem-vindo
        JLabel lblBemVindo = new JLabel("Bem-vindo, " + controller.getJogadorAtual().getNome() + "!");
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(lblBemVindo, gbc);

        // Informações do jogador
        JLabel lblInfo = new JLabel("Pontos: " + controller.getJogadorAtual().getPontos() + 
                                   " | Fase: " + controller.getJogadorAtual().getFaseAtual().getNome());
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        painel.add(lblInfo, gbc);

        // Botão Iniciar Jogo
        JButton btnIniciarJogo = new JButton("Iniciar Jogo");
        btnIniciarJogo.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciarJogo.setPreferredSize(new Dimension(200, 50));
        btnIniciarJogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaJogo();
            }
        });
        gbc.gridy = 3;
        painel.add(btnIniciarJogo, gbc);

        // Botão Continuar Partida
        JButton btnContinuar = new JButton("Continuar Partida");
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 14));
        btnContinuar.setPreferredSize(new Dimension(200, 50));
        btnContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaJogo();
            }
        });
        gbc.gridy = 4;
        painel.add(btnContinuar, gbc);

        // Botão Ranking
        JButton btnRanking = new JButton("Ver Ranking");
        btnRanking.setFont(new Font("Arial", Font.BOLD, 14));
        btnRanking.setPreferredSize(new Dimension(200, 50));
        btnRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaRanking();
            }
        });
        gbc.gridy = 5;
        painel.add(btnRanking, gbc);

        // Botão Logout
        JButton btnLogout = new JButton("Sair");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.setPreferredSize(new Dimension(200, 50));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                dispose();
                new TelaLogin(controller);
            }
        });
        gbc.gridy = 6;
        painel.add(btnLogout, gbc);

        setContentPane(painel);
        setVisible(true);
    }

    private void abrirTelaJogo() {
        dispose();
        new TelaJogo(controller);
    }

    private void abrirTelaRanking() {
        new TelaRanking(controller);
    }
}
