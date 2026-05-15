package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.Desafio;
import com.maua.jogo.model.Jogador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela Principal do Jogo
 */
public class TelaJogo extends JFrame {
    private JogoController controller;
    private JLabel lblPosicao;
    private JLabel lblPontos;
    private JLabel lblFase;
    private JLabel lblDesafio;
    private JButton btnDado;
    private JButton btnVoltarMenu;

    public TelaJogo(JogoController controller) {
        this.controller = controller;
        controller.iniciarPartida();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Jogo de Tabuleiro - Jogo");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(240, 248, 255));

        // Painel Superior - Informações
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new GridLayout(1, 3));
        painelInfo.setBackground(new Color(70, 130, 180));

        lblPosicao = new JLabel("Posição: " + controller.getJogadorAtual().getPosicao() + "/" + 
                               controller.getTabuleiro().getTotalCasas());
        lblPosicao.setForeground(Color.WHITE);
        lblPosicao.setFont(new Font("Arial", Font.BOLD, 14));

        lblPontos = new JLabel("Pontos: " + controller.getJogadorAtual().getPontos());
        lblPontos.setForeground(Color.WHITE);
        lblPontos.setFont(new Font("Arial", Font.BOLD, 14));

        lblFase = new JLabel("Fase: " + controller.getJogadorAtual().getFaseAtual().getNome());
        lblFase.setForeground(Color.WHITE);
        lblFase.setFont(new Font("Arial", Font.BOLD, 14));

        painelInfo.add(lblPosicao);
        painelInfo.add(lblPontos);
        painelInfo.add(lblFase);

        painel.add(painelInfo, BorderLayout.NORTH);

        // Painel Central - Tabuleiro (simplificado)
        JPanel painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new FlowLayout());
        painelTabuleiro.setBackground(new Color(240, 248, 255));

        JLabel lblTabuleiro = new JLabel("Tabuleiro com " + controller.getTabuleiro().getTotalCasas() + " casas");
        lblTabuleiro.setFont(new Font("Arial", Font.BOLD, 16));
        painelTabuleiro.add(lblTabuleiro);

        painel.add(painelTabuleiro, BorderLayout.CENTER);

        // Painel de Desafio
        JPanel painelDesafio = new JPanel();
        painelDesafio.setLayout(new FlowLayout());
        painelDesafio.setBackground(new Color(255, 250, 205));

        lblDesafio = new JLabel("Clique em 'Lançar Dado' para começar!");
        lblDesafio.setFont(new Font("Arial", Font.PLAIN, 12));
        painelDesafio.add(lblDesafio);

        painel.add(painelDesafio, BorderLayout.CENTER);

        // Painel Inferior - Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.setBackground(new Color(240, 248, 255));

        btnDado = new JButton("Lançar Dado");
        btnDado.setFont(new Font("Arial", Font.BOLD, 14));
        btnDado.setPreferredSize(new Dimension(150, 40));
        btnDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancarDado();
            }
        });
        painelBotoes.add(btnDado);

        btnVoltarMenu = new JButton("Voltar ao Menu");
        btnVoltarMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltarMenu.setPreferredSize(new Dimension(150, 40));
        btnVoltarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.finalizarPartida();
                dispose();
                new TelaMenu(controller);
            }
        });
        painelBotoes.add(btnVoltarMenu);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painel);
        setVisible(true);
    }

    private void lancarDado() {
        int resultado = (int)(Math.random() * 6) + 1;
        
        boolean eFimDoJogo = controller.moverJogador(resultado);
        
        atualizarInterface();

        if (eFimDoJogo) {
            JOptionPane.showMessageDialog(this, 
                "Parabéns! Você completou o tabuleiro!\nPontuação final: " + 
                controller.getJogadorAtual().getPontos(),
                "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
            controller.finalizarPartida();
            dispose();
            new TelaMenu(controller);
        } else {
            Desafio desafio = controller.obterDesafioAtual();
            if (desafio != null) {
                dispose();
                new TelaDesafio(controller, desafio, this);
            } else {
                JOptionPane.showMessageDialog(this, "Dados lançados! Resultado: " + resultado + 
                                            "\nNova posição: " + controller.getJogadorAtual().getPosicao());
            }
        }
    }

    private void atualizarInterface() {
        Jogador jogador = controller.getJogadorAtual();
        lblPosicao.setText("Posição: " + jogador.getPosicao() + "/" + 
                          controller.getTabuleiro().getTotalCasas());
        lblPontos.setText("Pontos: " + jogador.getPontos());
        lblFase.setText("Fase: " + jogador.getFaseAtual().getNome());
    }
}
