# 🎨 Diagrama UML - Jogo de Tabuleiro Mauá

## Diagrama de Classes

```
┌─────────────────────────────────────────────────────────────────┐
│                         CAMADA MODEL                            │
└─────────────────────────────────────────────────────────────────┘

┌──────────────────┐
│     Fase         │
├──────────────────┤
│ - numero: int    │
│ - nome: String   │
├──────────────────┤
│ + proximaFase()  │
└──────────────────┘
         △
         │ extends
         │
    [1,2,3,4,5,6]


┌──────────────────────┐
│      Jogador         │
├──────────────────────┤
│ - id: int            │
│ - nome: String       │
│ - email: String      │
│ - senha: String      │
│ - posicao: int       │
│ - pontos: int        │
│ - faseAtual: Fase    │
│ - ativo: boolean     │
├──────────────────────┤
│ + avancarPosicao()   │
│ + adicionarPontos()  │
│ + resetarProgresso() │
└──────────────────────┘
         △
         │ 1
         │
         ├─────── * ─────────┐
         │                   │
┌────────────────────┐  ┌──────────────┐
│    Partida         │  │   Tabuleiro  │
├────────────────────┤  ├──────────────┤
│ - id: int          │  │ - casas[]    │
│ - dataInicio: DateTime │ - totalCasas │
│ - dataFim: DateTime│  ├──────────────┤
│ - pontuacaoFinal   │  │ + obterCasa()│
│ - concluida: bool  │  └──────────────┘
├────────────────────┤         │
│ + finalizarPartida()         │ 1
│ + adicionarEvento()          │
└────────────────────┘         │ * 
                       ┌───────────────┐
                       │     Casa      │
                       ├───────────────┤
                       │ - posicao: int│
                       │ - fase: Fase  │
                       │ - desafio: D. │
                       │ - descricao   │
                       ├───────────────┤
                       │ + temDesafio()│
                       └───────────────┘
                              │
                              │ 1
                              │
                              │ * 
                       ┌───────────────────┐
                       │    Desafio        │
                       ├───────────────────┤
                       │ - id: int         │
                       │ - titulo: String  │
                       │ - pergunta: String│
                       │ - opcoes[4]       │
                       │ - respostaCorreta │
                       │ - pontos: int     │
                       │ - fase: Fase      │
                       ├───────────────────┤
                       │ + verificarResposta()
                       └───────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                      CAMADA CONTROLLER                          │
└─────────────────────────────────────────────────────────────────┘

┌────────────────────────────┐
│    JogoController          │
├────────────────────────────┤
│ - jogadorAtual: Jogador    │
│ - partidaAtual: Partida    │
│ - tabuleiro: Tabuleiro     │
│ - desafios: Desafio[]      │
├────────────────────────────┤
│ + autenticar()             │
│ + cadastrarJogador()       │
│ + iniciarPartida()         │
│ + moverJogador()           │
│ + responderDesafio()       │
│ + finalizarPartida()       │
│ + obterRanking()           │
└────────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                        CAMADA VIEW                              │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────┐
│   JFrame            │
├─────────────────────┤
│ - controller        │
│ - components[]      │
├─────────────────────┤
│ + init()            │
│ + actionListener()  │
└─────────────────────┘
         △
         │ extends
         │
    ┌────┴────┬─────────┬──────────┬──────────┐
    │          │         │          │          │
TelaLogin TelaMenu TelaJogo TelaDesafio TelaRanking


┌─────────────────────────────────────────────────────────────────┐
│                    CAMADA UTIL (DAO)                            │
└─────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│    ConexaoBD         │
├──────────────────────┤
│ - connection         │
│ - url, user, passwd  │
├──────────────────────┤
│ + obterConexao()     │
│ + fecharConexao()    │
│ + testarConexao()    │
└──────────────────────┘
         △
         │ uses
         │
    ┌────┴────┬──────────┬─────────────┐
    │          │          │             │
JogadorDAO DesafioDAO PartidaDAO    MySQL
```

---

## Diagrama de Banco de Dados (ER)

```
┌──────────────────────┐
│      fases           │
├──────────────────────┤
│ id (PK)              │
│ nome (UNIQUE)        │
│ numero (UNIQUE)      │
│ descricao            │
└──────────────────────┘
         △
         │ 1
         │
    ┌────┴─────────────────┬────────────────────┐
    │ *                    │ *                  │
┌────────────────────┐ ┌──────────────────┐
│    jogadores       │ │    desafios      │
├────────────────────┤ ├──────────────────┤
│ id (PK)            │ │ id (PK)          │
│ nome               │ │ titulo           │
│ email (UNIQUE)     │ │ pergunta         │
│ senha              │ │ opcao_a-d        │
│ posicao            │ │ resposta_correta │
│ pontos             │ │ pontos           │
│ fase_id (FK)       │ │ fase_id (FK)     │
│ ativo              │ └──────────────────┘
│ data_criacao       │
└────────────────────┘
         △
         │ 1
         │
         * 
┌────────────────────┐
│     partidas       │
├────────────────────┤
│ id (PK)            │
│ jogador_id (FK) ─┐ │
│ data_inicio        │ │
│ data_fim           │ │
│ pontuacao_final    │ │
│ concluida          │ │
└────────────────────┘ │
         △             │
         │ 1           │
         │             │
    * ┌──────────────────────────────┐
    └─→ respostas_desafios
       │ - id (PK)
       │ - partida_id (FK)
       │ - desafio_id (FK) ────────→ desafios
       │ - resposta_escolhida
       │ - acertou
       └──────────────────────────────┘
```

---

## Fluxo de Dados - Login

```
┌─────────────────────┐
│  Usuário            │
│ Digita credenciais  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  TelaLogin.java     │
│ Lê email/senha      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ JogoController      │
│ autenticar()        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ JogadorDAO          │
│ obterJogadorPor     │
│ EmailESenha()       │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  MySQL Database     │
│ SELECT * FROM      │
│ jogadores WHERE... │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Jogador encontrado? │
├─────────────────────┤
│ Sim ──→ TelaMenu    │
│ Não ──→ Erro        │
└─────────────────────┘
```

---

## Fluxo de Jogo - Jogar

```
┌──────────────────┐
│   TelaJogo       │
└────────┬─────────┘
         │ Usuário clica "Lançar Dado"
         ▼
┌──────────────────────────┐
│ JogoController           │
│ moverJogador(resultado)  │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ Atualizar posição        │
│ jogador.avancarPosicao() │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────┐
│ JogadorDAO           │
│ atualizarJogador()   │
└────────┬─────────────┘
         │
         ▼
┌──────────────────┐
│ MySQL UPDATE     │
└────────┬─────────┘
         │
         ▼
┌──────────────────────────┐
│ Verifica desafio         │
│ na posição atual         │
└────────┬─────────────────┘
         │
    ┌────┴────────┐
    │             │
    ▼             ▼
┌─────────┐   ┌──────────┐
│ Sim     │   │ Não      │
└────┬────┘   └────┬─────┘
     │             │
     ▼             ▼
┌──────────────┐  ┌──────────┐
│TelaDesafio   │  │Continua  │
│              │  │esperando │
└──────────────┘  └──────────┘
```

---

## Ciclo de Vida de uma Partida

```
1. Cadastro (TelaLogin)
   └─→ JogadorDAO.criarJogador()
       └─→ INSERT jogadores

2. Login (TelaLogin)
   └─→ JogadorDAO.obterJogadorPorEmailESenha()
       └─→ SELECT jogadores

3. Menu (TelaMenu)
   └─→ Visualizar dados

4. Iniciar Partida (TelaJogo)
   └─→ JogoController.iniciarPartida()
       └─→ PartidaDAO.criarPartida()
           └─→ INSERT partidas

5. Jogar (TelaJogo)
   └─→ Lançar dados
   └─→ Mover jogador
   └─→ JogadorDAO.atualizarJogador()
       └─→ UPDATE jogadores

6. Desafios (TelaDesafio)
   └─→ DesafioDAO.obterDesafioPorId()
       └─→ SELECT desafios
   └─→ Responder
   └─→ PartidaDAO.registrarResposta()
       └─→ INSERT respostas_desafios

7. Fim (TelaJogo)
   └─→ JogoController.finalizarPartida()
       └─→ PartidaDAO.atualizarPartida()
           └─→ UPDATE partidas

8. Ranking (TelaRanking)
   └─→ PartidaDAO.obterRanking()
       └─→ SELECT com JOIN
           └─→ Exibir TOP 10
```

---

## Estrutura de Pacotes

```
com.maua.jogo
│
├── model/
│   ├── Fase.java           (Enum)
│   ├── Jogador.java        (Entity)
│   ├── Desafio.java        (Entity)
│   ├── Casa.java           (Entity)
│   ├── Tabuleiro.java      (Entity)
│   └── Partida.java        (Entity)
│
├── view/
│   ├── TelaLogin.java      (JFrame)
│   ├── TelaMenu.java       (JFrame)
│   ├── TelaJogo.java       (JFrame)
│   ├── TelaDesafio.java    (JFrame)
│   └── TelaRanking.java    (JFrame)
│
├── controller/
│   └── JogoController.java (Controller)
│
├── util/
│   ├── ConexaoBD.java      (Utility)
│   ├── JogadorDAO.java     (DAO)
│   ├── DesafioDAO.java     (DAO)
│   └── PartidaDAO.java     (DAO)
│
└── Main.java               (Entry Point)
```

---

## Relações entre Classes

```
Model ←→ Controller ←→ View
  ↓                      ↓
  └─→ Util (DAO) ←──────┘
          ↓
      MySQL BD

Fluxo:
1. Usuário interage com View (JFrame)
2. View chama métodos de Controller
3. Controller usa Model e DAO
4. DAO persiste/recupera dados do MySQL
5. Resultado retorna ao View
6. View exibe ao usuário
```

---

## Sequência de Autenticação

```
Usuário ──→ TelaLogin ──→ JogoController.autenticar()
                                  │
                                  ▼
                         JogadorDAO.obterJogadorPor
                         EmailESenha(email, senha)
                                  │
                                  ▼
                         ConexaoBD.obterConexao()
                                  │
                                  ▼
                         PreparedStatement com SQL
                                  │
                                  ▼
                         MySQL Query (SELECT)
                                  │
                         ┌────────┴────────┐
                         │                 │
                    Encontrado         Não encontrado
                         │                 │
                         ▼                 ▼
                    Criar Jogador    Retornar null
                    (Objeto Java)
                         │                 │
                         └────────┬────────┘
                                  │
                         ▼
                    TelaMenu (sucesso)
                    ou Erro (falha)
```

---

## Observações

- ✅ Diagrama mostra arquitetura MVC
- ✅ Separação clara de responsabilidades
- ✅ Todas as classes estão em seus pacotes corretos
- ✅ DAO abstrai acesso ao banco
- ✅ Controller coordena Model e View

---

**Versão:** 1.0  
**Padrão:** UML 2.5  
**Ferramenta:** Criado em ASCII
