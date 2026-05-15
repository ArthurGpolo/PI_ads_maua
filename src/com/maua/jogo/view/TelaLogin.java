package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de Login e Cadastro
 */
public class TelaLogin extends JFrame {
    private JogoController controller;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JTextField campoNome;
    private JButton btnLogin;
    private JButton btnCadastro;
    private JButton btnMostrarCadastro;
    private JPanel painelLogin;
    private JPanel painelCadastro;

    public TelaLogin(JogoController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Jogo de Tabuleiro - Mauá");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel de Login
        painelLogin = new JPanel();
        painelLogin.setLayout(new GridBagLayout());
        painelLogin.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel lblTitulo = new JLabel("LOGIN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelLogin.add(lblTitulo, gbc);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        painelLogin.add(lblEmail, gbc);

        campoEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelLogin.add(campoEmail, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelLogin.add(lblSenha, gbc);

        campoSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painelLogin.add(campoSenha, gbc);

        // Botões
        btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        painelLogin.add(btnLogin, gbc);

        btnMostrarCadastro = new JButton("Cadastrar-se");
        btnMostrarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPainelCadastro();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        painelLogin.add(btnMostrarCadastro, gbc);

        // Painel de Cadastro
        painelCadastro = new JPanel();
        painelCadastro.setLayout(new GridBagLayout());
        painelCadastro.setBackground(new Color(240, 248, 255));

        // Título Cadastro
        JLabel lblTituloCadastro = new JLabel("CADASTRO");
        lblTituloCadastro.setFont(new Font("Arial", Font.BOLD, 24));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelCadastro.add(lblTituloCadastro, gbc);

        // Nome
        JLabel lblNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        painelCadastro.add(lblNome, gbc);

        campoNome = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelCadastro.add(campoNome, gbc);

        // Email Cadastro
        JLabel lblEmailCadastro = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCadastro.add(lblEmailCadastro, gbc);

        JTextField campoEmailCadastro = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painelCadastro.add(campoEmailCadastro, gbc);

        // Senha Cadastro
        JLabel lblSenhaCadastro = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelCadastro.add(lblSenhaCadastro, gbc);

        JPasswordField campoSenhaCadastro = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        painelCadastro.add(campoSenhaCadastro, gbc);

        // Botões Cadastro
        btnCadastro = new JButton("Cadastrar");
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCadastro(campoNome.getText(), campoEmailCadastro.getText(), 
                                new String(campoSenhaCadastro.getPassword()));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        painelCadastro.add(btnCadastro, gbc);

        JButton btnVoltarCadastro = new JButton("Voltar");
        btnVoltarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPainelLogin();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        painelCadastro.add(btnVoltarCadastro, gbc);

        setContentPane(painelLogin);
        setVisible(true);
    }

    private void realizarLogin() {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        if (controller.autenticar(email, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            abrirTelaMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarCadastro(String nome, String email, String senha) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controller.cadastrarJogador(nome, email, senha)) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            mostrarPainelLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Email já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPainelLogin() {
        setContentPane(painelLogin);
        revalidate();
        repaint();
    }

    private void mostrarPainelCadastro() {
        setContentPane(painelCadastro);
        revalidate();
        repaint();
    }

    private void abrirTelaMenu() {
        dispose();
        new TelaMenu(controller);
    }
}
