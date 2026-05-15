package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela de Ranking
 */
public class TelaRanking extends JFrame {
    private JogoController controller;

    public TelaRanking(JogoController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Ranking - Jogo de Tabuleiro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(240, 248, 255));

        // Título
        JLabel lblTitulo = new JLabel("RANKING DE JOGADORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblTitulo, BorderLayout.NORTH);

        // Tabela de Ranking
        String[] colunas = {"Posição", "Jogador", "Maior Pontuação", "Total de Partidas"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        List<Object[]> ranking = controller.obterRanking();
        int posicao = 1;
        for (Object[] linha : ranking) {
            Object[] novaLinha = new Object[4];
            novaLinha[0] = posicao++;
            novaLinha[1] = linha[0];
            novaLinha[2] = linha[1];
            novaLinha[3] = linha[2];
            model.addRow(novaLinha);
        }

        JTable tabelaRanking = new JTable(model);
        tabelaRanking.setRowHeight(25);
        tabelaRanking.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaRanking.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaRanking.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(tabelaRanking);
        painel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(painel);
        setVisible(true);
    }
}
