package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    private JogoController controller;
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;

    public TelaCadastro(JogoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Novo Jogador");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnl = new JPanel(new GridLayout(8, 1, 5, 5));
        pnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNome = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JPasswordField();
        JButton btnSalvar = new JButton("Cadastrar");

        pnl.add(new JLabel("Nome Completo:"));
        pnl.add(txtNome);
        pnl.add(new JLabel("E-mail:"));
        pnl.add(txtEmail);
        pnl.add(new JLabel("Senha:"));
        pnl.add(txtSenha);
        pnl.add(new JLabel("")); // Espaço
        pnl.add(btnSalvar);

        add(pnl);

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
                return;
            }

            if (controller.cadastrarJogador(nome, email, senha)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: E-mail já cadastrado!");
            }
        });
    }
}
