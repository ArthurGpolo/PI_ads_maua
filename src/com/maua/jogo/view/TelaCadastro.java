package com.maua.jogo.view;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.util.Tema;
import javax.swing.*;
import java.awt.*;

/**
 * Cadastro de novo jogador.
 * P1-6: agora é um JDialog MODAL (tendo o Login como dono) — evita múltiplas janelas
 * de cadastro e prende o foco até concluir/cancelar.
 */
public class TelaCadastro extends JDialog {
    private JogoController controller;
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;

    public TelaCadastro(Frame owner, JogoController controller) {
        super(owner, "Novo Jogador", true); // modal
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(440, 380);
        setMinimumSize(new Dimension(440, 380));
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        // P1-2: título de tela + hierarquia tipográfica consistente com o Login
        JLabel titulo = new JLabel("Criar Nova Conta", SwingConstants.CENTER);
        titulo.setFont(Tema.fonte(Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        add(titulo, BorderLayout.NORTH);

        // P1-3: GridBagLayout com vínculo rótulo<->campo (setLabelFor), sem JLabel-espaçador
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtNome = new JTextField(18);
        txtEmail = new JTextField(18);
        txtSenha = new JPasswordField(18);

        JLabel lblNome = rotulo("Nome Completo:", 'N', txtNome, "Digite seu nome completo");
        JLabel lblEmail = rotulo("E-mail:", 'm', txtEmail, "Digite um e-mail válido");
        JLabel lblSenha = rotulo("Senha:", 'S', txtSenha, "Crie uma senha");

        addLinha(pnl, gbc, 0, lblNome, txtNome);
        addLinha(pnl, gbc, 1, lblEmail, txtEmail);
        addLinha(pnl, gbc, 2, lblSenha, txtSenha);
        add(pnl, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setMnemonic('C');                          // P0-7
        btnSalvar.setPreferredSize(new Dimension(160, 44));  // P1-4
        Tema.acessivel(btnSalvar, "Cadastrar", "Salva o novo jogador");
        JPanel pnlSul = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        pnlSul.add(btnSalvar);
        add(pnlSul, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnSalvar);                            // P0-7: Enter = Cadastrar
        Tema.registrarFontes(this);
        SwingUtilities.invokeLater(() -> txtNome.requestFocusInWindow());     // P0-7: foco inicial

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());

            // P1-10: validação campo a campo, com foco no campo problemático
            if (nome.isEmpty())  { erro("Informe o nome completo.", txtNome);  return; }
            if (email.isEmpty()) { erro("Informe o e-mail.", txtEmail);        return; }
            if (senha.isEmpty()) { erro("Informe a senha.", txtSenha);         return; }

            if (controller.cadastrarJogador(nome, email, senha)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                erro("E-mail já cadastrado. Use outro e-mail.", txtEmail);
            }
        });
    }

    private JLabel rotulo(String texto, char mnem, JComponent campo, String desc) {
        JLabel l = new JLabel(texto);
        l.setDisplayedMnemonic(mnem);
        l.setLabelFor(campo);
        Tema.acessivel(campo, texto.replace(":", ""), desc);
        return l;
    }

    private void addLinha(JPanel pnl, GridBagConstraints gbc, int linha, JLabel lbl, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0;
        pnl.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        pnl.add(campo, gbc);
    }

    private void erro(String msg, JComponent campo) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
        campo.requestFocusInWindow();
    }
}
