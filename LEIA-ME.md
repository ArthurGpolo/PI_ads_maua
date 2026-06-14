# Jornada do Conhecimento - Projeto Final (NetBeans)

Este projeto foi reconstruído para integrar todas as funcionalidades lógicas originais com uma interface gráfica moderna em **Java Swing**.

## Funcionalidades Integradas
- **Login e Cadastro**: Sistema completo de autenticação persistido no MySQL.
- **Tabuleiro Visual**: Interface que mostra o progresso do jogador em tempo real.
- **Regras de Movimentação**: Avanço baseado em dados com verificação de fim de jornada.
- **Desafios por Etapa**: Perguntas específicas para cada fase da jornada.
- **Sistema de Pontuação**: Ganho de pontos ao acertar desafios.
- **Ranking Global**: Visualização dos melhores jogadores diretamente no jogo.

## Como Abrir no NetBeans
1. Abra o **Apache NetBeans**.
2. Vá em **File > Open Project**.
3. Selecione a pasta `JornadaDoConhecimento` (esta pasta).
4. O NetBeans reconhecerá o ícone do projeto automaticamente.

## Configuração do Banco de Dados
1. Execute o arquivo `database.sql` no seu **MySQL Workbench**.
2. Se necessário, altere o usuário e senha no arquivo `src/com/maua/jogo/util/ConexaoBD.java`.

## Como Rodar
- **Pelo NetBeans**: Clique com o botão direito no projeto e selecione **Run**.
- **Pelo Windows**: Use o arquivo `INSTALAR_E_JOGAR.bat` na raiz do projeto.

---
**Nota**: Certifique-se de que o driver `mysql-connector-j.jar` está dentro da pasta `lib`. O script `.bat` baixa ele automaticamente se estiver faltando.
