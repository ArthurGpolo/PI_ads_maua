package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.Desafio;
import javax.swing.*;
import java.awt.*;

public class TelaDesafio extends JDialog {
    private JogoController controller;
    private Desafio desafio;
    private TelaTabuleiro telaPai;

    public TelaDesafio(TelaTabuleiro pai, JogoController controller, Desafio desafio) {
        super(pai, "DESAFIO: " + desafio.getTitulo(), true);
        this.telaPai = pai;
        this.controller = controller;
        this.desafio = desafio;
        initComponents();
    }

    private void initComponents() {
        setSize(600, 450);
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(10, 10));

        JTextArea txtPergunta = new JTextArea(desafio.getPergunta());
        txtPergunta.setLineWrap(true);
        txtPergunta.setWrapStyleWord(true);
        txtPergunta.setEditable(false);
        txtPergunta.setFont(new Font("Arial", Font.BOLD, 16));
        txtPergunta.setBackground(new Color(245, 245, 245));
        txtPergunta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(txtPergunta, BorderLayout.NORTH);

        JPanel pnlOpcoes = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlOpcoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        String[] opcoes = desafio.getOpcoes();
        for (int i = 0; i < opcoes.length; i++) {
            JButton btn = new JButton(opcoes[i]);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            final int index = i;
            btn.addActionListener(e -> responder(index));
            pnlOpcoes.add(btn);
        }
        add(pnlOpcoes, BorderLayout.CENTER);
    }

    private void responder(int index) {
        if (controller.responderDesafio(desafio, index)) {
            JOptionPane.showMessageDialog(this, "RESPOSTA CORRETA!\nVocê ganhou " + desafio.getPontos() + " pontos.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "RESPOSTA INCORRETA!\nMais sorte na próxima vez.", "Ops", JOptionPane.WARNING_MESSAGE);
        }
        telaPai.atualizarInterface();
        dispose();
    }
}
