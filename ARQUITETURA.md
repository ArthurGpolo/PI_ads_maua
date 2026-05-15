# Estrutura e Organização do Projeto

## Arquitetura Geral

Este projeto segue o padrão **MVC (Model-View-Controller)** com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────────────┐
│                 Main.java                           │
│            (Ponto de Entrada)                       │
└──────────────────┬──────────────────────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
    ┌───▼────┐            ┌──▼──────┐
    │ Model  │            │ View    │
    │ (dados)│            │ (UI)    │
    └───┬────┘            └──┬──────┘
        │                    │
        │             ┌──────▼─────┐
        │             │ Controller │
        │             │ (lógica)   │
        │             └──────┬─────┘
        │                    │
        └────────┬───────────┘
                 │
         ┌───────▼────────┐
         │ Banco de Dados │
         │ (MySQL)        │
         └────────────────┘
```

## Camadas do Projeto

### 1. **Model** (`src/com/maua/jogo/model/`)
Contém as classes que representam os dados:

- **Fase.java** - Enum das 6 fases
- **Jogador.java** - Dados do jogador
- **Desafio.java** - Dados de cada desafio
- **Casa.java** - Posição no tabuleiro
- **Tabuleiro.java** - Mapa com 30 casas
- **Partida.java** - Sessão de jogo

### 2. **View** (`src/com/maua/jogo/view/`)
Interfaces gráficas (JFrame):

- **TelaLogin.java** - Login e cadastro
- **TelaMenu.java** - Menu principal
- **TelaJogo.java** - Tela de jogo
- **TelaDesafio.java** - Responder perguntas
- **TelaRanking.java** - Visualizar ranking

### 3. **Controller** (`src/com/maua/jogo/controller/`)
Lógica de negócio:

- **JogoController.java** - Controlador principal

### 4. **Util** (`src/com/maua/jogo/util/`)
Utilitários e acesso ao banco:

- **ConexaoBD.java** - Gerenciador de conexão
- **JogadorDAO.java** - CRUD de jogadores
- **DesafioDAO.java** - CRUD de desafios
- **PartidaDAO.java** - CRUD de partidas

## Fluxo de Dados

```
┌──────────┐
│  Usuário │
└────┬─────┘
     │ (interage)
     ▼
┌──────────────┐
│ View (JFrame)│
└────┬─────────┘
     │ (chama)
     ▼
┌──────────────────┐
│ Controller       │
└────┬─────────────┘
     │ (utiliza)
     ▼
┌──────────────────┐
│ Model            │
└────┬─────────────┘
     │ (persiste/recupera)
     ▼
┌──────────────────┐
│ DAO + Banco      │
└──────────────────┘
```

## Fluxo de Telas

```
┌─────────────────┐
│  TelaLogin      │ ◄─┐
│ (Login/Cadastro)│  │
└────┬────────────┘  │
     │ (login OK)    │
     ▼               │
┌─────────────────┐  │
│  TelaMenu       │  │
│ (Menu Principal)│  │
└────┬────────────┘  │
     │               │
  ┌──┴──┐            │
  │     └─────► "Sair"
  │             │
  ├─────────────┘
  │
  ├─► "Iniciar Jogo"
  │   │
  │   ▼
  │ ┌──────────────┐
  │ │ TelaJogo     │
  │ │ (Lançar dado)│
  │ │      │       │
  │ │      ▼       │
  │ │ Desafio?     │
  │ │      │       │
  │ │      ├─► Não ► Continua
  │ │      │       │
  │ │      └─► Sim │
  │ │            ▼
  │ │ ┌──────────────────┐
  │ │ │ TelaDesafio      │
  │ │ │ (Responder)      │
  │ │ │      │           │
  │ │ │      ▼           │
  │ │ │ Próxima pergunta?
  │ │ │      │           │
  │ │ │      └─ Não ► Volta
  │ └─────────────────────┘
  │
  └─► "Ver Ranking"
      │
      ▼
    ┌──────────────┐
    │ TelaRanking  │
    │ (Mostrar top)│
    └──────────────┘
```

## Exemplo de Fluxo Completo

```
1. Usuário abre TelaLogin
   ├─ Clica "Cadastrar-se"
   │  └─ Preenche formulário
   │     └─ Clica "Cadastrar"
   │        └─ JogadorDAO.criarJogador() salva em DB
   │           └─ Volta para TelaLogin
   │
   └─ Clica "Entrar"
      └─ JogadorController.autenticar()
         └─ JogadorDAO.obterJogadorPorEmailESenha()
            └─ Busca em DB
               └─ Abre TelaMenu

2. No TelaMenu
   ├─ Clica "Iniciar Jogo"
   │  └─ JogoController.iniciarPartida()
   │     └─ Cria nova Partida
   │        └─ PartidaDAO.criarPartida() salva em DB
   │           └─ Abre TelaJogo

3. No TelaJogo
   ├─ Clica "Lançar Dado"
   │  └─ Gera número (1-6)
   │     └─ JogoController.moverJogador()
   │        └─ Atualiza posição
   │           └─ JogadorDAO.atualizarJogador() salva em DB
   │              └─ Verifica se há desafio
   │                 ├─ Sim: Abre TelaDesafio
   │                 └─ Não: Continua esperando

4. No TelaDesafio
   ├─ Seleciona resposta
   │  └─ Clica "Confirmar"
   │     └─ JogoController.responderDesafio()
   │        ├─ Se correto: adiciona pontos
   │        └─ PartidaDAO.registrarResposta() salva em DB
   │           └─ Volta para TelaJogo

5. Fim de jogo (casa 30)
   ├─ JogoController.finalizarPartida()
   │  └─ PartidaDAO.atualizarPartida() com status
   │     └─ TelaJogo mostra resultado
   │        └─ Volta para TelaMenu
```

## Padrões de Projeto Utilizados

### 1. **MVC (Model-View-Controller)**
- Separação clara de responsabilidades
- Model: dados puros
- View: apenas apresentação
- Controller: lógica de negócio

### 2. **DAO (Data Access Object)**
- Abstrai acesso ao banco de dados
- Classes: JogadorDAO, DesafioDAO, PartidaDAO
- Facilita manutenção e testes

### 3. **Singleton**
- ConexaoBD: conexão única com o banco

### 4. **Enum**
- Fase: lista imutável das fases

## Conectividade do Banco de Dados

```
┌──────────────────────────────────┐
│        ConexaoBD                 │
├──────────────────────────────────┤
│ - obterConexao()                 │
│ - fecharConexao()                │
│ - testarConexao()                │
└──────────────────────────────────┘
              │
              ▼
┌──────────────────────────────────┐
│     MySQL Driver                 │
├──────────────────────────────────┤
│ com.mysql.cj.jdbc.Driver         │
└──────────────────────────────────┘
              │
              ▼
┌──────────────────────────────────┐
│     Servidor MySQL               │
├──────────────────────────────────┤
│ localhost:3306                   │
│ Database: jogo_maua              │
└──────────────────────────────────┘
```

## Dependências Entre Classes

```
Main.java
  └─ JogoController
       ├─ Jogador
       ├─ Tabuleiro
       │  └─ Casa
       │     └─ Fase
       │     └─ Desafio
       ├─ Partida
       └─ DAO classes
            └─ ConexaoBD
```

## Estrutura de Pastas

```
PI_ads_maua/
│
├── src/com/maua/jogo/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── Fase.java
│   │   ├── Jogador.java
│   │   ├── Desafio.java
│   │   ├── Casa.java
│   │   ├── Tabuleiro.java
│   │   └── Partida.java
│   │
│   ├── view/
│   │   ├── TelaLogin.java
│   │   ├── TelaMenu.java
│   │   ├── TelaJogo.java
│   │   ├── TelaDesafio.java
│   │   └── TelaRanking.java
│   │
│   ├── controller/
│   │   └── JogoController.java
│   │
│   └── util/
│       ├── ConexaoBD.java
│       ├── JogadorDAO.java
│       ├── DesafioDAO.java
│       └── PartidaDAO.java
│
├── db/
│   └── database.sql
│
└── Documentação/
    ├── README.md
    ├── SETUP_GUIDE.md
    ├── REQUISITOS.md
    ├── MANUAL_DO_USUARIO.md
    └── ARQUITETURA.md (este arquivo)
```

## Convenções de Código

- **Pacotes**: lowercase (com.maua.jogo)
- **Classes**: PascalCase (TelaLogin)
- **Métodos**: camelCase (obterJogador)
- **Constantes**: UPPERCASE_WITH_UNDERSCORES
- **Variáveis**: camelCase (posicaoAtual)
- **Comentários**: Javadoc para métodos públicos

## Performance e Otimização

### Índices no Banco
- `jogadores.email` - para buscas rápidas
- `partidas.jogador_id` - para histórico
- `desafios.fase_id` - para desafios por fase

### Lazy Loading
- Desafios carregados conforme necessário
- Histórico de partidas sob demanda

### Caching
- Tabuleiro criado uma única vez por partida
- Fases armazenadas em enum

## Segurança

- **SQL Injection**: Prevenido com PreparedStatement
- **Senhas**: Armazenadas em texto (pode melhorar com hash)
- **Validações**: Entrada do usuário validada
- **Conexão**: Closure automático de recursos

---

**Este documento descreve a organização técnica do projeto.**
