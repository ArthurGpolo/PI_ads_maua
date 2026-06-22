package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.model.Desafio;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Desafio no estilo "Mauá Challenge": cabeçalho azul com a pergunta, opções
 * A/B/C/D em cards selecionáveis e botão "Confirmar resposta".
 */
public class TelaDesafio extends JDialog {
    private final JogoController controller;
    private final Desafio desafio;
    private final TelaTabuleiro telaPai;

    private final JToggleButton[] botoes = new JToggleButton[4];
    private final int[] indiceOriginal = new int[4]; // posição visível -> índice real da opção
    private int escolhidaOriginal = -1;
    private static final char[] LETRAS = {'A', 'B', 'C', 'D'};

    public TelaDesafio(TelaTabuleiro pai, JogoController controller, Desafio desafio) {
        super(pai, "Desafio", true);
        this.telaPai = pai;
        this.controller = controller;
        this.desafio = desafio;
        initComponents();
    }

    private void initComponents() {
        setSize(720, 600);
        setMinimumSize(new Dimension(640, 540));
        setLocationRelativeTo(telaPai);
        setLayout(new BorderLayout(0, 0));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarOpcoes(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);

        // teclas 1..4 selecionam a opção
        for (int i = 0; i < 4; i++) {
            final int pos = i;
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_1 + i, 0), "sel" + i);
            getRootPane().getActionMap().put("sel" + i, new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    if (botoes[pos] != null) botoes[pos].setSelected(true);
                }
            });
        }
        Tema.registrarFontes(getRootPane());
        SwingUtilities.invokeLater(() -> { if (botoes[0] != null) botoes[0].requestFocusInWindow(); });
    }

    private JComponent criarCabecalho() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        barra.add(Tema.logoMaua(false), BorderLayout.WEST);

        JButton btnSair = new JButton("X");
        btnSair.setFont(Tema.fonte(Font.BOLD, 18));
        btnSair.setForeground(Tema.MAUA_AZUL);
        btnSair.setToolTipText("Sair do desafio");
        Tema.acessivel(btnSair, "Sair do desafio", "Fecha o desafio sem responder");
        btnSair.addActionListener(e -> dispose());
        JPanel dir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        dir.setOpaque(false);
        dir.add(btnSair);
        barra.add(dir, BorderLayout.EAST);
        topo.add(barra, BorderLayout.NORTH);

        // caixa azul com fase + valor + pergunta
        JPanel caixa = new JPanel(new BorderLayout(0, 8));
        caixa.setBackground(Tema.MAUA_AZUL);
        caixa.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));
        JLabel lblNum = new JLabel("Fase " + desafio.getFase().getNome() + "  ·  vale " + desafio.getPontos() + " pts");
        lblNum.setFont(Tema.fonte(Font.BOLD, 15));
        lblNum.setForeground(Color.WHITE);
        caixa.add(lblNum, BorderLayout.NORTH);
        JLabel lblPergunta = new JLabel("<html>" + escapeHtml(desafio.getPergunta()) + "</html>");
        lblPergunta.setFont(Tema.fonte(Font.BOLD, 18));
        lblPergunta.setForeground(Color.WHITE);
        Tema.acessivel(lblPergunta, "Pergunta do desafio", desafio.getPergunta());
        caixa.add(lblPergunta, BorderLayout.CENTER);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(BorderFactory.createEmptyBorder(8, 0, 12, 0));
        wrap.add(caixa, BorderLayout.CENTER);
        topo.add(wrap, BorderLayout.CENTER);
        return topo;
    }

    private JComponent criarOpcoes() {
        JPanel pnl = new JPanel(new GridLayout(4, 1, 0, 12));
        pnl.setOpaque(false);

        String[] opcoes = desafio.getOpcoes();
        // embaralha a ordem de exibição (a correta pode cair em qualquer posição)
        java.util.List<Integer> ordem = new java.util.ArrayList<>();
        for (int i = 0; i < opcoes.length; i++) ordem.add(i);
        java.util.Collections.shuffle(ordem);

        ButtonGroup grupo = new ButtonGroup();
        for (int d = 0; d < 4; d++) {
            final int original = ordem.get(d);
            indiceOriginal[d] = original;
            JToggleButton b = new JToggleButton(
                    "<html><b>&nbsp;" + LETRAS[d] + "&nbsp;</b>&nbsp;&nbsp;&nbsp;" + escapeHtml(opcoes[original]) + "</html>");
            b.setFont(Tema.fonte(Font.PLAIN, 16));
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setFocusPainted(false);
            b.setBackground(Tema.CINZA_CARD);
            b.setBorder(bordaCard(false));
            Tema.acessivel(b, "Opção " + LETRAS[d], opcoes[original]);
            b.addItemListener(e -> {
                boolean sel = b.isSelected();
                b.setBorder(bordaCard(sel));
                b.setBackground(sel ? new Color(214, 232, 250) : Tema.CINZA_CARD);
                if (sel) escolhidaOriginal = original;
            });
            grupo.add(b);
            botoes[d] = b;
            pnl.add(b);
        }
        return pnl;
    }

    private JComponent criarRodape() {
        JPanel rod = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        rod.setOpaque(false);
        JButton btnConfirmar = Tema.botaoPrimario("Confirmar resposta");
        btnConfirmar.setMnemonic('C');
        Tema.acessivel(btnConfirmar, "Confirmar resposta", "Envia a opção selecionada");
        btnConfirmar.addActionListener(e -> confirmar());
        rod.add(btnConfirmar);
        getRootPane().setDefaultButton(btnConfirmar);
        return rod;
    }

    private void confirmar() {
        if (escolhidaOriginal < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma opção (A, B, C ou D).",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Toolkit.getDefaultToolkit().beep();
        boolean correta = controller.responderDesafio(desafio, escolhidaOriginal);
        if (correta) {
            JOptionPane.showMessageDialog(this,
                    "Resposta correta! +" + desafio.getPontos() + " pontos.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Resposta incorreta. +" + JogoController.PONTOS_PARTICIPACAO + " pontos pela participação.",
                    "Ops", JOptionPane.WARNING_MESSAGE);
        }
        telaPai.atualizarInterface();
        dispose();
    }

    private Border bordaCard(boolean selecionado) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(selecionado ? Tema.MAUA_AZUL : new Color(200, 205, 215),
                        selecionado ? 3 : 1, true),
                BorderFactory.createEmptyBorder(14, 16, 14, 16));
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
