# Jogo Digital Estilo Tabuleiro - Mauá

## Visão Geral

Um jogo digital estilo tabuleiro desenvolvido em **Java com JFrame** que simula a jornada profissional de um estudante através de 6 fases:

1. **Explorador** - Explora novas oportunidades
2. **Conector** - Conecta com pessoas e ideias
3. **Transformador** - Transforma conhecimento em ação
4. **Conhecedor** - Adquire conhecimento profundo
5. **Planejador** - Planeja o futuro
6. **Realizador** - Realiza seus objetivos

## Características Principais

- ✅ **Interface Gráfica em JFrame** - Interface desktop responsiva e intuitiva
- ✅ **Banco de Dados MySQL** - Armazenamento seguro de dados
- ✅ **Sistema de Cadastro e Login** - Gerenciamento de usuários
- ✅ **Movimentação em Tabuleiro** - Sistema de dados e progressão
- ✅ **Desafios Interativos** - Perguntas com múltiplas opções
- ✅ **Sistema de Pontuação** - Contabilização de pontos por desafio
- ✅ **Histórico de Partidas** - Registro completo no banco de dados
- ✅ **Ranking de Jogadores** - Visualizar melhor desempenho
- ✅ **Operações CRUD Completas** - Gerenciamento total de dados

## Arquitetura do Projeto

```
jogo_maua/
├── src/
│   └── com/maua/jogo/
│       ├── Main.java                 # Classe principal
│       ├── model/
│       │   ├── Fase.java             # Enum das fases
│       │   ├── Desafio.java          # Classe de desafios
│       │   ├── Casa.java             # Classe de posições do tabuleiro
│       │   ├── Tabuleiro.java        # Classe do tabuleiro
│       │   ├── Jogador.java          # Classe do jogador
│       │   └── Partida.java          # Classe de sessões
│       ├── view/
│       │   ├── TelaLogin.java        # Login e cadastro
│       │   ├── TelaMenu.java         # Menu principal
│       │   ├── TelaJogo.java         # Tela do jogo
│       │   ├── TelaDesafio.java      # Tela de desafios
│       │   └── TelaRanking.java      # Tela de ranking
│       ├── controller/
│       │   └── JogoController.java   # Controlador principal
│       └── util/
│           ├── ConexaoBD.java        # Gerenciador de conexão
│           ├── JogadorDAO.java       # CRUD de Jogadores
│           ├── DesafioDAO.java       # CRUD de Desafios
│           └── PartidaDAO.java       # CRUD de Partidas
├── db/
│   └── database.sql                  # Script de criação do BD
└── README.md                         # Este arquivo
```

## Requisitos do Sistema

### Tecnologias Necessárias
- **Java JDK 8 ou superior**
- **MySQL 5.7 ou superior**
- **IDE NetBeans** (recomendado) ou qualquer IDE Java

### Dependências
- Driver MySQL Connector/J (incluído no classpath)

## Instalação

### 1. Preparar o Banco de Dados

```bash
# Conectar ao MySQL
mysql -u root -p

# Executar o script SQL
source db/database.sql;
```

### 2. Configurar a Conexão

Editar o arquivo `src/com/maua/jogo/util/ConexaoBD.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/jogo_maua";
private static final String USUARIO = "root";      // Seu usuário MySQL
private static final String SENHA = "root";        // Sua senha MySQL
```

### 3. Compilar e Executar

**No NetBeans:**
1. Abrir o projeto
2. Clicar em "Clean and Build"
3. Executar (F6 ou Run > Run Project)

**Via Terminal:**
```bash
# Compilar
javac -d bin src/com/maua/jogo/**/*.java

# Executar
java -cp bin com.maua.jogo.Main
```

## Como Usar

### 1. **Iniciar Aplicação**
   - Execute a classe `Main.java`
   - Será exibida a tela de login

### 2. **Criar Conta**
   - Clique em "Cadastrar-se"
   - Preencha nome, email e senha
   - Clique em "Cadastrar"

### 3. **Fazer Login**
   - Digite email e senha
   - Clique em "Entrar"

### 4. **Jogar**
   - Clique em "Iniciar Jogo"
   - Clique em "Lançar Dado" para avançar
   - Responda os desafios que aparecerem
   - Acumule pontos e complete o tabuleiro

### 5. **Ver Ranking**
   - Volte ao menu
   - Clique em "Ver Ranking"
   - Visualize os melhores jogadores

## Banco de Dados

### Tabelas Principais

#### `jogadores`
- Armazena informações dos usuários
- Campos: id, nome, email, senha, posicao, pontos, fase_id, ativo

#### `fases`
- Define as 6 fases da jornada
- Campos: id, nome, numero, descricao

#### `desafios`
- Armazena perguntas e respostas
- Campos: id, titulo, pergunta, opcoes (A-D), resposta_correta, pontos, fase_id

#### `partidas`
- Registra histórico de jogos
- Campos: id, jogador_id, data_inicio, data_fim, pontuacao_final, concluida

#### `respostas_desafios`
- Registra cada resposta dada pelo jogador
- Campos: id, partida_id, desafio_id, resposta_escolhida, acertou

## Funcionalidades Implementadas

### ✅ Implementadas
- Cadastro e login de usuários
- Iniciar nova partida
- Movimentação no tabuleiro (dados)
- Sistema de desafios com 4 opções
- Pontuação automática
- Histórico de partidas
- Ranking de jogadores
- Operações CRUD completas (Create, Read, Update, Delete)

### 📋 Possíveis Melhorias (Bônus)
- Modo multiplayer local
- Animações de movimento
- Feedback sonoro
- Níveis de dificuldade
- Avatares de jogadores
- Temas visuais customizáveis

## Competências Desenvolvidas

- ✅ Programação Orientada a Objetos (POO)
- ✅ Desenvolvimento com GUI (JFrame)
- ✅ Integração com Banco de Dados
- ✅ Padrão MVC (Model-View-Controller)
- ✅ CRUD em Java
- ✅ Tratamento de exceções
- ✅ Organização de projeto

## Autor e Informações

**Projeto:** Jogo Digital Estilo Tabuleiro  
**Instituição:** Instituto Mauá de Tecnologia (IMT)  
**Linguagem:** Java 8+  
**Banco de Dados:** MySQL  
**Interface:** Swing (JFrame)

## Licença

Este projeto foi desenvolvido para fins educacionais.

## Suporte

Para dúvidas ou problemas:
1. Verifique a conexão com o MySQL
2. Certifique-se de que o banco foi criado corretamente
3. Confirme as credenciais em `ConexaoBD.java`
