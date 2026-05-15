# Requisitos Técnicos - Jogo Digital Estilo Tabuleiro

## 1. Requisitos Funcionais

### 1.1 Gerenciamento de Usuários
- [x] Cadastro de novo jogador com nome, email e senha
- [x] Sistema de login com validação de email e senha
- [x] Armazenamento seguro de dados de usuário
- [x] Perfil de jogador com informações pessoais

### 1.2 Sistema de Jogo
- [x] Iniciar nova partida
- [x] Continuar partida anterior
- [x] Movimentação no tabuleiro baseada em dados (1-6)
- [x] 30 casas totais (5 casas por fase)
- [x] Progressão através de 6 fases
- [x] Finalização automática ao atingir a última casa

### 1.3 Sistema de Desafios
- [x] Apresentar desafios em cada fase
- [x] Perguntas com 4 opções de resposta (A, B, C, D)
- [x] Validação automática de respostas
- [x] Atribuição de pontos por resposta correta
- [x] Registro de resposta incorreta

### 1.4 Sistema de Pontuação
- [x] Acumular pontos por desafio acertado
- [x] Diferentes valores de pontos por fase
- [x] Pontuação total por partida
- [x] Visualização de pontos em tempo real

### 1.5 Histórico e Ranking
- [x] Registro completo de cada partida
- [x] Data e hora de início e fim
- [x] Ranking de melhores jogadores
- [x] Histórico de partidas do jogador

## 2. Requisitos Técnicos

### 2.1 Linguagem e Plataforma
- [x] Linguagem: **Java 8 ou superior**
- [x] Interface: **JFrame (Swing)**
- [x] Tipo: Aplicação Desktop

### 2.2 Banco de Dados
- [x] Sistema: **MySQL 5.7+**
- [x] Banco normalizado (3ª forma normal)
- [x] Operações CRUD completas
- [x] Script SQL para criação automática
- [x] Índices para performance

### 2.3 Arquitetura de Software
- [x] Padrão MVC (Model-View-Controller)
- [x] Programação Orientada a Objetos (POO)
- [x] Separação de camadas (Model, View, Controller, Util)
- [x] Tratamento de exceções
- [x] Design Patterns: DAO, Singleton

### 2.4 Telas/Interfaces Gráficas

#### Tela de Login
- [x] Campo para email
- [x] Campo para senha
- [x] Botão "Entrar"
- [x] Botão "Cadastrar-se"
- [x] Layout responsivo

#### Tela de Cadastro
- [x] Campo para nome
- [x] Campo para email
- [x] Campo para senha
- [x] Validação de campos obrigatórios
- [x] Verificação de email duplicado

#### Tela de Menu
- [x] Mensagem de boas-vindas
- [x] Informações do jogador (pontos, fase)
- [x] Botão "Iniciar Jogo"
- [x] Botão "Continuar Partida"
- [x] Botão "Ver Ranking"
- [x] Botão "Sair"

#### Tela do Jogo
- [x] Exibição de posição atual
- [x] Exibição de pontos acumulados
- [x] Exibição de fase atual
- [x] Botão "Lançar Dado"
- [x] Botão "Voltar ao Menu"
- [x] Detecção de fim de jogo

#### Tela de Desafio
- [x] Título do desafio
- [x] Descrição do desafio
- [x] Pergunta
- [x] 4 opções de resposta (Radio Buttons)
- [x] Exibição de pontos em jogo
- [x] Botão "Confirmar Resposta"
- [x] Feedback (acertou/errou)

#### Tela de Ranking
- [x] Tabela com jogadores
- [x] Posição (ranking)
- [x] Nome do jogador
- [x] Maior pontuação
- [x] Total de partidas
- [x] Ordenação decrescente por pontuação

## 3. Modelo de Dados

### Classes de Modelo

#### Fase (Enum)
- id: int
- numero: int (1-6)
- nome: String
- Métodos: getFasePorNumero(), proximaFase()

#### Jogador
- id: int
- nome: String
- email: String
- senha: String
- posicao: int
- pontos: int
- faseAtual: Fase
- ativo: boolean
- Métodos: avancarPosicao(), adicionarPontos(), resetarProgresso()

#### Desafio
- id: int
- titulo: String
- descricao: String
- pergunta: String
- opcoes: String[4]
- respostaCorreta: int
- pontos: int
- fase: Fase
- Métodos: verificarResposta()

#### Casa
- posicao: int
- fase: Fase
- desafio: Desafio
- descricao: String
- Métodos: temDesafio()

#### Tabuleiro
- casas: List<Casa>
- totalCasas: int (30)
- Métodos: obterCasa(), obterFaseAtual(), eFimDoTabuleiro()

#### Partida
- id: int
- jogador: Jogador
- dataInicio: LocalDateTime
- dataFim: LocalDateTime
- pontuacaoFinal: int
- concluida: boolean
- historico: List<String>
- Métodos: finalizarPartida(), adicionarEvento()

### Tabelas do Banco de Dados

```sql
-- fases: id, nome, numero, descricao
-- jogadores: id, nome, email, senha, posicao, pontos, fase_id, ativo
-- desafios: id, titulo, descricao, pergunta, opcao_a-d, resposta_correta, pontos, fase_id
-- partidas: id, jogador_id, data_inicio, data_fim, pontuacao_final, concluida
-- respostas_desafios: id, partida_id, desafio_id, resposta_escolhida, acertou
```

## 4. Operações CRUD

### CRUD de Jogador (JogadorDAO)
- [x] **CREATE**: Inserir novo jogador
- [x] **READ**: Obter jogador por ID, por email/senha, listar todos
- [x] **UPDATE**: Atualizar dados do jogador
- [x] **DELETE**: Soft delete (marcar como inativo)

### CRUD de Desafio (DesafioDAO)
- [x] **CREATE**: Inserir novo desafio
- [x] **READ**: Obter por ID, por fase, listar todos
- [x] **UPDATE**: Atualizar desafio
- [x] **DELETE**: Remover desafio

### CRUD de Partida (PartidaDAO)
- [x] **CREATE**: Iniciar nova partida
- [x] **READ**: Obter por ID, por jogador, listar todas
- [x] **UPDATE**: Atualizar resultado de partida
- [x] **DELETE**: Remover partida

## 5. Validações

- [x] Email válido e único
- [x] Senha obrigatória
- [x] Nome não vazio
- [x] Resposta deve ser selecionada antes de confirmar
- [x] Verificação de fim de tabuleiro
- [x] Verificação de conexão com banco de dados

## 6. Performance

- [x] Índices nas tabelas principais
- [x] Queries otimizadas
- [x] Connection pooling (preparado para implementação)
- [x] Carregamento eficiente de dados

## 7. Segurança

- [x] Validação de entrada (SQL Injection prevention com PreparedStatement)
- [x] Proteção de senha (armazenada no banco)
- [x] Logout seguro
- [x] Soft delete para manter histórico

## 8. Padrões de Projeto Implementados

- [x] **MVC**: Model-View-Controller
- [x] **DAO**: Data Access Object
- [x] **Singleton**: Gerenciador de conexão
- [x] **Enum**: Para as fases

## 9. Tratamento de Erros

- [x] Try-catch em operações de banco de dados
- [x] Mensagens de erro ao usuário via JOptionPane
- [x] Log de erros no console
- [x] Recuperação graceful de falhas

## 10. Entregáveis

- [x] Código-fonte organizado em pacotes
- [x] Script SQL completo (database.sql)
- [x] README.md com instruções
- [x] SETUP_GUIDE.md com passo a passo
- [x] REQUISITOS.md (este arquivo)
- [x] Documentação inline no código (Javadoc)

---

**Status:** ✅ Todos os requisitos implementados
