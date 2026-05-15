package com.maua.jogo;

import com.maua.jogo.controller.JogoController;
import com.maua.jogo.util.ConexaoBD;
import com.maua.jogo.view.TelaLogin;

/**
 * Classe Principal - Ponto de entrada do aplicativo
 */
public class Main {
    public static void main(String[] args) {
        // Verifica a conexão com o banco de dados
        if (!ConexaoBD.testarConexao()) {
            System.out.println("Erro: Não foi possível conectar ao banco de dados!");
            System.out.println("Certifique-se de que:");
            System.out.println("1. O MySQL está rodando");
            System.out.println("2. O banco 'jogo_maua' foi criado");
            System.out.println("3. As credenciais estão corretas");
            System.exit(1);
        }

        System.out.println("Conexão com banco de dados estabelecida com sucesso!");

        // Inicia a aplicação
        JogoController controller = new JogoController();
        new TelaLogin(controller);
    }
}
