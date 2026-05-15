package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.Desafio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de Desafio/Pergunta
 */
public class TelaDesafio extends JFrame {
    private JogoController controller;
    private Desafio desafio;
    private JFrame telaAnterior;
    private int respostaSelecionada = -1;

    public TelaDesafio(JogoController controller, Desafio desafio, JFrame telaAnterior) {
        this.controller = controller;
        this.desafio = desafio;
        this.telaAnterior = telaAnterior;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Desafio - " + desafio.getTitulo());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(240, 248, 255));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título do Desafio
        JLabel lblTitulo = new JLabel(desafio.getTitulo());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblTitulo);
        painel.add(Box.createVerticalStrut(10));

        // Descrição
        JLabel lblDescricao = new JLabel(desafio.getDescricao());
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblDescricao);
        painel.add(Box.createVerticalStrut(20));

        // Pergunta
        JLabel lblPergunta = new JLabel(desafio.getPergunta());
        lblPergunta.setFont(new Font("Arial", Font.BOLD, 14));
        lblPergunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblPergunta);
        painel.add(Box.createVerticalStrut(20));

        // Opções
        ButtonGroup grupo = new ButtonGroup();
        String[] opcoes = desafio.getOpcoes();

        for (int i = 0; i < opcoes.length; i++) {
            JRadioButton rbOpcao = new JRadioButton("" + (char)('A' + i) + ") " + opcoes[i]);
            rbOpcao.setFont(new Font("Arial", Font.PLAIN, 12));
            final int indice = i;
            rbOpcao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    respostaSelecionada = indice;
                }
            });
            grupo.add(rbOpcao);
            painel.add(rbOpcao);
            painel.add(Box.createVerticalStrut(10));
        }

        painel.add(Box.createVerticalStrut(20));

        // Pontos
        JLabel lblPontos = new JLabel("Pontos em jogo: " + desafio.getPontos());
        lblPontos.setFont(new Font("Arial", Font.BOLD, 12));
        lblPontos.setForeground(new Color(0, 100, 0));
        lblPontos.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblPontos);
        painel.add(Box.createVerticalStrut(20));

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 248, 255));

        JButton btnConfirmar = new JButton("Confirmar Resposta");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarResposta();
            }
        });
        painelBotoes.add(btnConfirmar);

        painel.add(painelBotoes);

        setContentPane(new JScrollPane(painel));
        setVisible(true);
    }

    private void confirmarResposta() {
        if (respostaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma resposta!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean acertou = controller.responderDesafio(desafio, respostaSelecionada);

        if (acertou) {
            JOptionPane.showMessageDialog(this, 
                "Resposta correta!\nVocê ganhou " + desafio.getPontos() + " pontos!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Resposta incorreta!\nA resposta correta era: " + (char)('A' + desafio.getRespostaCorreta()),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
        if (telaAnterior != null) {
            telaAnterior.setVisible(true);
        }
    }
}
