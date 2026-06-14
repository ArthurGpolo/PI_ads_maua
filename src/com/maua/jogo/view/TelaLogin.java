package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JogoController controller;
    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public TelaLogin(JogoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Jornada do Conhecimento");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel pnlPrincipal = new JPanel(new GridBagLayout());
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblTitulo = new JLabel("Jornada do Conhecimento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        pnlPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; pnlPrincipal.add(new JLabel("E-mail:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1; pnlPrincipal.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2; pnlPrincipal.add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(20);
        gbc.gridx = 1; pnlPrincipal.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(50, 100, 200));
        btnEntrar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        pnlPrincipal.add(btnEntrar, gbc);

        JButton btnCadastrar = new JButton("Criar Nova Conta");
        gbc.gridy = 4;
        pnlPrincipal.add(btnCadastrar, gbc);

        add(pnlPrincipal);

        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            if (controller.autenticar(email, senha)) {
                new TelaTabuleiro(controller).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCadastrar.addActionListener(e -> new TelaCadastro(controller).setVisible(true));
    }
}
