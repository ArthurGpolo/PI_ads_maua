package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.*;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Tabuleiro estilizado da "Jornada do Conhecimento": trilha com os 6 nós de fase.
 * Lógica: o jogador responde perguntas da FASE ATUAL e acumula pontos nela até
 * atingir a meta; então avança para a próxima fase. O tabuleiro é desenhado em código.
 */
public class TelaTabuleiro extends JFrame {
    private final JogoController controller;
    private BoardPanel board;
    private JPanel barraTopo;
    private JLabel lblJogador, lblPontos, lblFase, lblProgresso;
    private JButton btnResponder;

    public TelaTabuleiro(JogoController controller) {
        this.controller = controller;
        if (controller.getPartidaAtual() == null) {
            controller.iniciarPartida();
        }
        initComponents();
        atualizarInterface();
    }

    private void initComponents() {
        setTitle("Jornada do Conhecimento - Tabuleiro");
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { confirmarESair(); }
        });
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== NORTE: acessibilidade + barra de status =====
        JPanel norte = new JPanel(new BorderLayout());
        norte.add(Tema.painelAcessibilidade(this, this::repaint), BorderLayout.NORTH);

        barraTopo = new JPanel(new GridLayout(1, 4, 16, 0));
        barraTopo.setBackground(Tema.MAUA_AZUL);
        barraTopo.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        lblJogador = chip("Jogador: " + controller.getJogadorAtual().getNome());
        lblPontos = chip("Pontos: 0");
        lblFase = chip("Fase: Explorador");
        lblProgresso = chip("Meta: 0 / 0 pts");
        for (JLabel l : new JLabel[]{lblJogador, lblPontos, lblFase, lblProgresso}) barraTopo.add(l);
        Tema.acessivel(lblProgresso, "Progresso na fase", "Pontos acumulados na fase atual e meta para avançar");
        norte.add(barraTopo, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // ===== CENTRO: tabuleiro desenhado =====
        board = new BoardPanel();
        board.getAccessibleContext().setAccessibleName("Tabuleiro da Jornada do Conhecimento");
        add(board, BorderLayout.CENTER);

        // ===== SUL: ações =====
        JPanel acoes = new JPanel(new BorderLayout());
        acoes.setBorder(BorderFactory.createEmptyBorder(8, 16, 12, 16));

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        btnResponder = Tema.botaoPrimario("RESPONDER PERGUNTA");
        btnResponder.setBackground(new Color(0, 150, 70)); // verde de ação (≥4.5:1 com branco)
        btnResponder.setFont(Tema.fonte(Font.BOLD, 20));
        btnResponder.setPreferredSize(new Dimension(400, 54));
        btnResponder.setMnemonic('P');
        Tema.acessivel(btnResponder, "Responder pergunta",
                "Abre uma pergunta da fase atual. Atalho: tecla Espaço");
        btnResponder.addActionListener(e -> responderPergunta());
        centro.add(btnResponder);
        acoes.add(centro, BorderLayout.CENTER);

        JButton btnRanking = new JButton("Ver Ranking");
        btnRanking.setMnemonic('R');
        btnRanking.setPreferredSize(new Dimension(150, 44));
        Tema.acessivel(btnRanking, "Ver ranking global", "Mostra os melhores jogadores");
        btnRanking.addActionListener(e -> mostrarRanking());
        JPanel dir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        dir.add(btnRanking);
        acoes.add(dir, BorderLayout.EAST);

        JButton btnSair = new JButton("Logout");
        btnSair.setMnemonic('S');
        btnSair.setPreferredSize(new Dimension(130, 44));
        Tema.acessivel(btnSair, "Logout", "Sai da conta e encerra a partida");
        btnSair.addActionListener(e -> confirmarESair());

        JButton btnReiniciar = new JButton("Reiniciar Jogo");
        btnReiniciar.setMnemonic('I');
        btnReiniciar.setPreferredSize(new Dimension(160, 44));
        Tema.acessivel(btnReiniciar, "Reiniciar jogo", "Zera seu progresso e volta para a primeira fase");
        btnReiniciar.addActionListener(e -> reiniciarJogo());

        JPanel esq = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        esq.add(btnSair);
        esq.add(btnReiniciar);
        acoes.add(esq, BorderLayout.WEST);

        add(acoes, BorderLayout.SOUTH);

        // Teclado: Espaço responde; Enter = botão default
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("SPACE"), "responder");
        getRootPane().getActionMap().put("responder", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { responderPergunta(); }
        });
        getRootPane().setDefaultButton(btnResponder);
        SwingUtilities.invokeLater(() -> btnResponder.requestFocusInWindow());

        Tema.registrarFontes(this);
    }

    private JLabel chip(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(Tema.fonte(Font.BOLD, 16));
        l.setForeground(Color.WHITE);
        return l;
    }

    /** Abre uma pergunta da fase atual; ao responder, verifica se atingiu a meta da fase. */
    private void responderPergunta() {
        Desafio d = controller.sortearDesafio();
        if (d == null) {
            JOptionPane.showMessageDialog(this, "Não há perguntas cadastradas para esta fase.");
            return;
        }
        new TelaDesafio(this, controller, d).setVisible(true); // modal: responde e atualiza a interface

        if (controller.metaAtingida()) {
            Fase concluida = controller.getJogadorAtual().getFaseAtual();
            boolean fim = controller.avancarFase(); // finaliza internamente se for a última fase
            atualizarInterface();
            if (fim) {
                JOptionPane.showMessageDialog(this,
                        "PARABÉNS! Você concluiu a Jornada do Conhecimento!\n"
                        + "Pontuação final: " + controller.getJogadorAtual().getPontos());
                mostrarRanking();
                voltarAoLogin();
            } else {
                // tela transitória de promoção de nível
                new TelaPromocao(this, concluida, controller.getJogadorAtual().getFaseAtual()).setVisible(true);
            }
        }
    }

    public void atualizarInterface() {
        Jogador j = controller.getJogadorAtual();
        lblPontos.setText("Pontos: " + j.getPontos());
        lblFase.setText("Fase: " + j.getFaseAtual().getNome());
        lblProgresso.setText("Meta: " + controller.getPontosNaFase() + " / " + controller.metaFaseAtual() + " pts");
        board.getAccessibleContext().setAccessibleDescription(
                "Fase " + j.getFaseAtual().getNome() + ". Progresso na fase: "
                + controller.getPontosNaFase() + " de " + controller.metaFaseAtual()
                + " pontos. Pontuação total: " + j.getPontos());
        board.repaint();
    }

    private void reiniciarJogo() {
        int r = JOptionPane.showConfirmDialog(this,
                "Isso vai ZERAR seu progresso (volta para a fase Explorador, 0 pontos).\nDeseja continuar?",
                "Reiniciar Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            controller.reiniciarJogo();
            atualizarInterface();
            JOptionPane.showMessageDialog(this, "Jogo reiniciado! Você voltou para a fase Explorador.");
        }
    }

    private void voltarAoLogin() {
        controller.logout();
        new TelaLogin(controller).setVisible(true);
        dispose();
    }

    private void confirmarESair() {
        int r = JOptionPane.showConfirmDialog(this,
                "Sair encerra a partida atual. Deseja continuar?",
                "Confirmar Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r == JOptionPane.YES_OPTION) voltarAoLogin();
    }

    private void mostrarRanking() {
        List<Object[]> dados = controller.obterRanking();
        if (dados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma partida registrada no ranking ainda.");
            return;
        }
        String[] colunas = {"Jogador", "Maior Pontuação", "Partidas"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Object[] d : dados) model.addRow(d);
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.getAccessibleContext().setAccessibleName("Ranking global de jogadores");
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(440, 320));
        JOptionPane.showMessageDialog(this, scroll, "Ranking Global", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Tabuleiro: desenha a imagem de fundo (30 casas visíveis, /imagens/tabuleiro_fundo.png)
     * e sobrepõe os destaques (anel na fase atual + marcador VOCÊ na casa atual),
     * usando a MESMA fórmula de coordenadas da geração da imagem.
     */
    private class BoardPanel extends JPanel {
        // mesmas constantes usadas na geração da imagem
        static final int IW = 1280, IH = 620, MX = 130, MY = 90, RAIO = 30;
        final double COLGAP = (IW - 2 * MX) / 4.0; // 255
        final double ROWGAP = (IH - 2 * MY) / 5.0; // 88
        private double scale;
        private int offX, offY;

        BoardPanel() {
            setBackground(new Color(224, 232, 243));
        }

        private Point centroImg(int k) {
            int phase = k / 5, within = k % 5;
            int col = (phase % 2 == 0) ? within : (4 - within);
            return new Point((int) Math.round(MX + col * COLGAP), (int) Math.round(MY + phase * ROWGAP));
        }
        private int mx(double ix) { return (int) Math.round(offX + ix * scale); }
        private int my(double iy) { return (int) Math.round(offY + iy * scale); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int w = getWidth(), h = getHeight();
            boolean hc = Tema.altoContraste;

            // transformação "contain" (preserva proporção, centraliza)
            scale = Math.min(w / (double) IW, h / (double) IH);
            int dispW = (int) (IW * scale), dispH = (int) (IH * scale);
            offX = (w - dispW) / 2; offY = (h - dispH) / 2;

            g2.setColor(hc ? Color.BLACK : new Color(228, 236, 246));
            g2.fillRect(0, 0, w, h);

            java.awt.image.BufferedImage img = Tema.imagemTabuleiro();
            if (img != null) {
                g2.drawImage(img, offX, offY, dispW, dispH, null);
                if (hc) { // escurece um pouco para os destaques sobressaírem
                    g2.setColor(new Color(0, 0, 0, 70));
                    g2.fillRect(offX, offY, dispW, dispH);
                }
            } else {
                desenharFallback(g2, hc);
            }

            sobreporDestaques(g2, hc);
            g2.dispose();
        }

        /** Desenho simples caso a imagem do tabuleiro não seja encontrada. */
        private void desenharFallback(Graphics2D g2, boolean hc) {
            g2.setStroke(new BasicStroke((float) Math.max(6, 20 * scale), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(hc ? new Color(90, 90, 90) : new Color(248, 240, 222));
            for (int k = 0; k < 29; k++) {
                Point a = centroImg(k), b = centroImg(k + 1);
                g2.drawLine(mx(a.x), my(a.y), mx(b.x), my(b.y));
            }
            int rr = (int) Math.round(RAIO * scale);
            g2.setFont(new Font(Tema.familiaAtual(), Font.BOLD, Math.max(10, (int) Math.round(16 * scale))));
            for (int k = 0; k < 30; k++) {
                Point c = centroImg(k);
                Fase f = Fase.getFasePorNumero(k / 5 + 1);
                g2.setColor(hc ? new Color(20, 20, 20) : Tema.corFase(f));
                g2.fillOval(mx(c.x) - rr, my(c.y) - rr, rr * 2, rr * 2);
                g2.setStroke(new BasicStroke(2f));
                g2.setColor(hc ? Color.WHITE : new Color(70, 70, 80));
                g2.drawOval(mx(c.x) - rr, my(c.y) - rr, rr * 2, rr * 2);
                g2.setColor(hc ? Color.WHITE : new Color(35, 35, 45));
                String n = String.valueOf(k + 1);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(n, mx(c.x) - fm.stringWidth(n) / 2, my(c.y) + fm.getAscent() / 2 - 2);
            }
        }

        /** Anel na fase atual + marcador VOCÊ na casa atual (sobre as casas da imagem). */
        private void sobreporDestaques(Graphics2D g2, boolean hc) {
            Jogador j = controller.getJogadorAtual();
            int phase = j.getFaseAtual().getNumero() - 1;        // 0..5
            int meta = Math.max(1, controller.metaFaseAtual());
            int prog = controller.getPontosNaFase();
            int within = (int) Math.floor((double) prog / meta * 5);
            if (within < 0) within = 0; if (within > 4) within = 4;
            int casaAtual = phase * 5 + within;

            int rr = (int) Math.round(RAIO * scale);
            int gr = rr + (int) Math.round(6 * scale);
            Color anel = hc ? new Color(255, 215, 0) : Tema.MAUA_CIANO;

            // anel em todas as casas da fase atual
            g2.setStroke(new BasicStroke((float) Math.max(3, 4 * scale)));
            g2.setColor(anel);
            for (int k = phase * 5; k < phase * 5 + 5; k++) {
                Point c = centroImg(k);
                g2.drawOval(mx(c.x) - gr, my(c.y) - gr, gr * 2, gr * 2);
            }

            // casa atual: anel forte + marcador VOCÊ
            Point c = centroImg(casaAtual);
            int cx = mx(c.x), cy = my(c.y);
            g2.setStroke(new BasicStroke((float) Math.max(4, 6 * scale)));
            g2.setColor(Tema.MAUA_AZUL);
            g2.drawOval(cx - gr, cy - gr, gr * 2, gr * 2);

            g2.setFont(new Font(Tema.familiaAtual(), Font.BOLD, Math.max(11, (int) Math.round(13 * scale))));
            FontMetrics fm = g2.getFontMetrics();
            String txt = "VOCÊ";
            int tw = fm.stringWidth(txt);
            int pillH = fm.getHeight() + 4;
            int pillY = cy - gr - (int) Math.round(10 * scale) - pillH;
            g2.setColor(Tema.MAUA_AZUL);
            g2.fillRoundRect(cx - tw / 2 - 8, pillY, tw + 16, pillH, 12, 12);
            g2.setColor(Color.WHITE);
            g2.drawString(txt, cx - tw / 2, pillY + fm.getAscent() + 2);
            int triTop = pillY + pillH, triBot = cy - gr - 1;
            g2.setColor(Tema.MAUA_AZUL);
            g2.fillPolygon(new int[]{cx - 7, cx + 7, cx}, new int[]{triTop, triTop, triBot}, 3);
        }
    }
}
