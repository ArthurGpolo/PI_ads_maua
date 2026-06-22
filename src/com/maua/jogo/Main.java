package com.maua.jogo;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.util.ConexaoBD;
import com.maua.jogo.util.Tema;
import com.maua.jogo.view.TelaLogin;
import javax.swing.JOptionPane;

/**
 * Classe Principal - Ponto de entrada do aplicativo
 */
public class Main {
    public static void main(String[] args) {
        // Configura o visual do sistema operacional
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            // Acessibilidade: define fontes >= 16px e o tema centralizado (ver util/Tema).
            // Resolve em massa a legibilidade de rótulos, campos e botões sem setFont explícito.
            Tema.aplicar();
            // Alinha fontes de janelas/modais abertos depois (ex.: JOptionPane) à fonte atual.
            Tema.instalarAjusteDeJanelas();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Verifica a conexão com o banco de dados
        if (!ConexaoBD.testarConexao()) {
            JOptionPane.showMessageDialog(null,
                "Erro: Não foi possível conectar ao banco de dados!\n" +
                "Certifique-se de que:\n" +
                "1. O MySQL está rodando\n" +
                "2. O banco 'jogo_maua' foi criado\n" +
                "3. As credenciais em ConexaoBD.java estão corretas",
                "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Inicia a aplicação
        JogoController controller = new JogoController();
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin(controller).setVisible(true);
        });
    }
}
