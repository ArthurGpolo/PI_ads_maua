package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import java.awt.*;

/**
 * Tela de login da "Jornada do Conhecimento": faixa lateral com a marca e
 * formulário de credenciais à direita, com opção "Sou Professor".
 */
public class TelaLogin extends JFrame {
    private JogoController controller;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JCheckBox chkProfessor;

    public TelaLogin(JogoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Jornada do Conhecimento - Login");
        setSize(920, 560);
        setMinimumSize(new Dimension(880, 540));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarFaixaLateral(), BorderLayout.WEST);
        add(criarFormulario(), BorderLayout.CENTER);

        getRootPane().setDefaultButton((JButton) getRootPane().getClientProperty("btnLogin"));
        Tema.registrarFontes(this);
        SwingUtilities.invokeLater(() -> txtEmail.requestFocusInWindow());
    }

    /** Faixa lateral com gradiente (teal escuro), logo e marca. */
    private JComponent criarFaixaLateral() {
        JPanel faixa = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, Tema.MAUA_AZUL_ESCURO, 0, getHeight(), new Color(6, 48, 45)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        faixa.setPreferredSize(new Dimension(360, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.insets = new Insets(8, 0, 8, 0);

        gbc.gridy = 0; faixa.add(Tema.logoMaua(true), gbc);

        JLabel lblMarca1 = new JLabel("Jornada do");
        lblMarca1.setFont(Tema.fonte(Font.BOLD, 28));
        lblMarca1.setForeground(Color.WHITE);
        gbc.gridy = 1; gbc.insets = new Insets(30, 0, 0, 0); faixa.add(lblMarca1, gbc);

        JLabel lblMarca2 = new JLabel("Conhecimento");
        lblMarca2.setFont(Tema.fonte(Font.BOLD, 28));
        lblMarca2.setForeground(Tema.MAUA_CIANO); // âmbar — 4.7:1 sobre o teal escuro
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 0, 0); faixa.add(lblMarca2, gbc);

        return faixa;
    }

    /** Formulário de credenciais. */
    private JComponent criarFormulario() {
        JPanel container = new JPanel(new BorderLayout());

        container.add(Tema.painelAcessibilidade(this, this::repaint), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblBemVindo = new JLabel("Bem-vindo(a)!");
        lblBemVindo.setFont(Tema.fonte(Font.BOLD, 26));
        lblBemVindo.setForeground(Tema.MAUA_AZUL);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 4, 0); form.add(lblBemVindo, gbc);

        JLabel lblSub = new JLabel("<html><div style='width:360px'>Insira suas credenciais para acessar o tabuleiro</div></html>");
        lblSub.setFont(Tema.fonte(Font.PLAIN, 16));
        lblSub.setForeground(Tema.CINZA_TEXTO);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 20, 0); form.add(lblSub, gbc);

        JLabel lblEmail = secaoLabel("E-MAIL", 'M');
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 4, 0); form.add(lblEmail, gbc);
        txtEmail = new JTextField();
        txtEmail.setFont(Tema.fonte(Font.PLAIN, 16));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 195, 205)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        lblEmail.setLabelFor(txtEmail);
        Tema.acessivel(txtEmail, "E-mail", "Digite seu e-mail de acesso");
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 16, 0); form.add(txtEmail, gbc);

        JLabel lblSenha = secaoLabel("SENHA", 'S');
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 4, 0); form.add(lblSenha, gbc);
        txtSenha = new JPasswordField();
        txtSenha.setFont(Tema.fonte(Font.PLAIN, 16));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 195, 205)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        lblSenha.setLabelFor(txtSenha);
        Tema.acessivel(txtSenha, "Senha", "Digite sua senha");
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 10, 0); form.add(txtSenha, gbc);

        chkProfessor = new JCheckBox("Sou Professor");
        chkProfessor.setFont(Tema.fonte(Font.PLAIN, 15));
        chkProfessor.setOpaque(false);
        chkProfessor.setMnemonic('P');
        Tema.acessivel(chkProfessor, "Sou Professor",
                "Marque para entrar na área de administração de desafios e jogadores");
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 20, 0); form.add(chkProfessor, gbc);

        JButton btnLogin = Tema.botaoPrimario("LOGIN");
        btnLogin.setMnemonic('L');
        Tema.acessivel(btnLogin, "Login", "Entrar com o e-mail e a senha informados");
        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 12, 0); form.add(btnLogin, gbc);
        getRootPane().putClientProperty("btnLogin", btnLogin);

        JButton btnCadastrar = new JButton("Criar nova conta");
        btnCadastrar.setMnemonic('C');
        Tema.acessivel(btnCadastrar, "Criar nova conta", "Abre a tela de cadastro de novo jogador");
        gbc.gridy = 8; gbc.insets = new Insets(0, 0, 0, 0); form.add(btnCadastrar, gbc);

        container.add(form, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> efetuarLogin());
        btnCadastrar.addActionListener(e -> new TelaCadastro(this, controller).setVisible(true));

        return container;
    }

    private JLabel secaoLabel(String texto, char mnem) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.fonte(Font.BOLD, 14));
        l.setForeground(Tema.MAUA_AZUL);
        l.setDisplayedMnemonic(mnem);
        return l;
    }

    private void efetuarLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        if (controller.autenticar(email, senha)) {
            if (chkProfessor.isSelected()) {
                new TelaAdmin(controller).setVisible(true);
            } else {
                new TelaTabuleiro(controller).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtEmail.requestFocusInWindow();
        }
    }
}
