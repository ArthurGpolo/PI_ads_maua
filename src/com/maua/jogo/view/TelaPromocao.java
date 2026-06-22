package com.maua.jogo.view;

import com.maua.jogo.model.Fase;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import java.awt.*;

/**
 * Tela transitória de PROMOÇÃO DE NÍVEL: exibida ao avançar de fase.
 * Modal, fecha no botão "Continuar" ou automaticamente após alguns segundos.
 */
public class TelaPromocao extends JDialog {

    public TelaPromocao(Frame owner, Fase anterior, Fase nova) {
        super(owner, true);
        setUndecorated(true);
        initComponents(anterior, nova);
        setSize(520, 400);
        setLocationRelativeTo(owner);
    }

    private void initComponents(Fase anterior, Fase nova) {
        JPanel fundo = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, Tema.MAUA_AZUL, 0, getHeight(), Tema.MAUA_AZUL_ESCURO));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fundo.setBorder(BorderFactory.createLineBorder(Tema.MAUA_CIANO, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 24, 8, 24);

        JLabel lblTitulo = new JLabel("NÍVEL PROMOVIDO!", SwingConstants.CENTER);
        lblTitulo.setFont(Tema.fonte(Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridy = 0; gbc.insets = new Insets(24, 24, 4, 24);
        fundo.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Você concluiu a fase " + anterior.getNome(), SwingConstants.CENTER);
        lblSub.setFont(Tema.fonte(Font.PLAIN, 16));
        lblSub.setForeground(new Color(210, 235, 230));
        gbc.gridy = 1; gbc.insets = new Insets(0, 24, 8, 24);
        fundo.add(lblSub, gbc);

        JComponent circulo = new JComponent() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int d = Math.min(getWidth(), getHeight()) - 8;
                int x = (getWidth() - d) / 2, y = (getHeight() - d) / 2;
                g2.setColor(Tema.corFase(nova));
                g2.fillOval(x, y, d, d);
                g2.setColor(Tema.MAUA_CIANO);
                g2.setStroke(new BasicStroke(4));
                g2.drawOval(x, y, d, d);
                g2.setColor(new Color(30, 30, 30));
                g2.setFont(Tema.fonte(Font.BOLD, 40));
                String num = String.valueOf(nova.getNumero());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(num, getWidth() / 2 - fm.stringWidth(num) / 2, getHeight() / 2 + fm.getAscent() / 2 - 4);
                Tema.iconeFase(nova).paintIcon(this, g2, getWidth() / 2 - 8, y + 12);
            }
            @Override public Dimension getPreferredSize() { return new Dimension(120, 120); }
        };
        gbc.gridy = 2; gbc.insets = new Insets(12, 24, 4, 24);
        fundo.add(circulo, gbc);

        JLabel lblNova = new JLabel("Fase " + nova.getNumero() + ": " + nova.getNome(), SwingConstants.CENTER);
        lblNova.setFont(Tema.fonte(Font.BOLD, 22));
        lblNova.setForeground(Tema.MAUA_CIANO);
        gbc.gridy = 3; gbc.insets = new Insets(4, 24, 16, 24);
        fundo.add(lblNova, gbc);

        JButton btnContinuar = Tema.botaoPrimario("Continuar");
        btnContinuar.setMnemonic('C');
        Tema.acessivel(btnContinuar, "Continuar", "Fecha a tela de promoção e volta ao jogo");
        btnContinuar.addActionListener(e -> dispose());
        gbc.gridy = 4; gbc.insets = new Insets(0, 24, 24, 24);
        fundo.add(btnContinuar, gbc);

        setContentPane(fundo);
        getRootPane().setDefaultButton(btnContinuar);
        Tema.acessivel(lblTitulo, "Nível promovido",
                "Você avançou da fase " + anterior.getNome() + " para a fase " + nova.getNome());
        Tema.registrarFontes(getRootPane());
        SwingUtilities.invokeLater(btnContinuar::requestFocusInWindow);
        Toolkit.getDefaultToolkit().beep();

        // fecha sozinha após 5 segundos (tela transitória)
        Timer t = new Timer(5000, e -> { if (isDisplayable()) dispose(); });
        t.setRepeats(false);
        t.start();
    }
}
