package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaTabuleiro extends JFrame {
    private JogoController controller;
    private JPanel pnlTabuleiro;
    private JLabel lblJogador, lblPontos, lblFase, lblPosicao;
    private List<JPanel> casasGraficas;
    private JTextArea txtLog;

    public TelaTabuleiro(JogoController controller) {
        this.controller = controller;
        this.casasGraficas = new ArrayList<>();
        
        if (controller.getPartidaAtual() == null) {
            controller.iniciarPartida();
        }
        
        initComponents();
        atualizarInterface();
    }

    private void initComponents() {
        setTitle("Jornada do Conhecimento - Tabuleiro");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlStatus = new JPanel(new GridLayout(1, 4, 10, 10));
        pnlStatus.setBorder(BorderFactory.createTitledBorder("Status do Jogador"));
        pnlStatus.setBackground(new Color(230, 240, 250));

        lblJogador = new JLabel("Jogador: " + controller.getJogadorAtual().getNome());
        lblPontos = new JLabel("Pontos: 0");
        lblFase = new JLabel("Fase: Explorador");
        lblPosicao = new JLabel("Posição: 1");

        Font fontStatus = new Font("Arial", Font.BOLD, 14);
        lblJogador.setFont(fontStatus);
        lblPontos.setFont(fontStatus);
        lblFase.setFont(fontStatus);
        lblPosicao.setFont(fontStatus);

        pnlStatus.add(lblJogador);
        pnlStatus.add(lblPontos);
        pnlStatus.add(lblFase);
        pnlStatus.add(lblPosicao);
        add(pnlStatus, BorderLayout.NORTH);

        pnlTabuleiro = new JPanel(new GridLayout(6, 5, 8, 8));
        pnlTabuleiro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        inicializarCasas();
        add(new JScrollPane(pnlTabuleiro), BorderLayout.CENTER);

        JPanel pnlLateral = new JPanel(new BorderLayout(5, 5));
        pnlLateral.setPreferredSize(new Dimension(300, 0));

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Histórico da Partida"));
        
        JButton btnRanking = new JButton("Ver Ranking Global");
        btnRanking.addActionListener(e -> mostrarRanking());

        pnlLateral.add(scrollLog, BorderLayout.CENTER);
        pnlLateral.add(btnRanking, BorderLayout.SOUTH);
        add(pnlLateral, BorderLayout.EAST);

        JPanel pnlAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnDado = new JButton("LANÇAR DADO");
        btnDado.setFont(new Font("Arial", Font.BOLD, 18));
        btnDado.setBackground(new Color(50, 150, 50));
        btnDado.setForeground(Color.WHITE);
        btnDado.setPreferredSize(new Dimension(200, 50));
        
        JButton btnSair = new JButton("Logout");
        btnSair.addActionListener(e -> {
            controller.logout();
            new TelaLogin(controller).setVisible(true);
            dispose();
        });

        pnlAcoes.add(btnDado);
        pnlAcoes.add(btnSair);
        add(pnlAcoes, BorderLayout.SOUTH);

        btnDado.addActionListener(e -> jogarDado());
    }

    private void inicializarCasas() {
        List<Casa> casas = controller.getTabuleiro().getCasas();
        for (Casa casa : casas) {
            JPanel pnlCasa = new JPanel(new BorderLayout());
            pnlCasa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            
            JLabel lblNum = new JLabel(String.valueOf(casa.getPosicao()), SwingConstants.CENTER);
            lblNum.setFont(new Font("Arial", Font.BOLD, 16));
            
            JLabel lblDesc = new JLabel(casa.getFase().getNome(), SwingConstants.CENTER);
            lblDesc.setFont(new Font("Arial", Font.ITALIC, 10));

            pnlCasa.add(lblNum, BorderLayout.CENTER);
            pnlCasa.add(lblDesc, BorderLayout.SOUTH);

            switch (casa.getFase()) {
                case EXPLORADOR: pnlCasa.setBackground(new Color(220, 255, 220)); break;
                case CONECTOR: pnlCasa.setBackground(new Color(220, 220, 255)); break;
                case TRANSFORMADOR: pnlCasa.setBackground(new Color(255, 255, 220)); break;
                case CONHECEDOR: pnlCasa.setBackground(new Color(255, 220, 255)); break;
                case PLANEJADOR: pnlCasa.setBackground(new Color(220, 255, 255)); break;
                case REALIZADOR: pnlCasa.setBackground(new Color(255, 220, 220)); break;
            }

            casasGraficas.add(pnlCasa);
            pnlTabuleiro.add(pnlCasa);
        }
    }

    private void jogarDado() {
        int valor = (int) (Math.random() * 6) + 1;
        txtLog.append("> Dado: " + valor + "\n");
        
        boolean fim = controller.moverJogador(valor);
        atualizarInterface();

        if (fim) {
            JOptionPane.showMessageDialog(this, "JORNADA CONCLUÍDA!\nPontuação: " + controller.getJogadorAtual().getPontos());
            controller.finalizarPartida();
            mostrarRanking();
            controller.logout();
            new TelaLogin(controller).setVisible(true);
            dispose();
            return;
        }

        Desafio desafio = controller.obterDesafioAtual();
        if (desafio != null) {
            new TelaDesafio(this, controller, desafio).setVisible(true);
        } else {
            txtLog.append("> Casa sem desafio cadastrado.\n");
        }
    }

    public void atualizarInterface() {
        Jogador j = controller.getJogadorAtual();
        lblPontos.setText("Pontos: " + j.getPontos());
        lblFase.setText("Fase: " + j.getFaseAtual().getNome());
        lblPosicao.setText("Posição: " + j.getPosicao());

        for (int i = 0; i < casasGraficas.size(); i++) {
            if (i == j.getPosicao() - 1) {
                casasGraficas.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 4));
            } else {
                casasGraficas.get(i).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            }
        }
    }

    private void mostrarRanking() {
        List<Object[]> dados = controller.obterRanking();
        if (dados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma partida registrada no ranking ainda.");
            return;
        }
        
        String[] colunas = {"Jogador", "Maior Pontuação", "Partidas"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        for (Object[] d : dados) model.addRow(d);
        
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Ranking Global", JOptionPane.INFORMATION_MESSAGE);
    }
}
