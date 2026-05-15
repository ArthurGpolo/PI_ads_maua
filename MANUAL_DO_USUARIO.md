# Manual do Usuário - Jogo de Tabuleiro Mauá

## Bem-vindo! 👋

Este é o manual de uso para o **Jogo Digital Estilo Tabuleiro**. Um jogo interativo que simula a jornada profissional através de 6 fases desafiadoras.

## Índice

1. [Começar a Usar](#começar-a-usar)
2. [Criar sua Conta](#criar-sua-conta)
3. [Fazer Login](#fazer-login)
4. [Jogar](#jogar)
5. [Regras do Jogo](#regras-do-jogo)
6. [Ranking](#ranking)
7. [Dicas e Estratégias](#dicas-e-estratégias)
8. [Perguntas Frequentes](#perguntas-frequentes)

---

## Começar a Usar

### Instalação

1. **Instale Java**: Certifique-se de ter Java JDK 8+ instalado
2. **Instale MySQL**: Baixe e instale o MySQL
3. **Configure o Banco**: Execute o script `database.sql`
4. **Execute**: Abra o projeto em NetBeans e execute `Main.java`

Você verá a tela de login. Vamos começar!

---

## Criar sua Conta

### Passo 1: Acessar o Cadastro
- Na tela inicial, clique no botão **"Cadastrar-se"**

### Passo 2: Preencher os Dados
```
┌─────────────────────────────────┐
│     CADASTRO DE NOVO JOGADOR    │
├─────────────────────────────────┤
│ Nome: [_____________________]   │
│ Email: [____________________]   │
│ Senha: [____________________]   │
├─────────────────────────────────┤
│  [Cadastrar]    [Voltar]        │
└─────────────────────────────────┘
```

**Preencha:**
- **Nome**: Seu nome completo
- **Email**: Um email válido (será sua chave de login)
- **Senha**: Escolha uma senha segura

### Passo 3: Cadastrar
- Clique em **"Cadastrar"**
- Se tudo estiver correto, você verá a mensagem: _"Cadastro realizado com sucesso!"_
- Será redirecionado para a tela de login

---

## Fazer Login

### Passo 1: Acessar o Login
- Você estará na tela de login automaticamente

### Passo 2: Inserir Credenciais
```
┌─────────────────────────────────┐
│           LOGIN                 │
├─────────────────────────────────┤
│ Email: [____________________]   │
│ Senha: [____________________]   │
├─────────────────────────────────┤
│  [Entrar]   [Cadastrar-se]      │
└─────────────────────────────────┘
```

**Digite:**
- **Email**: O email cadastrado
- **Senha**: Sua senha

### Passo 3: Entrar
- Clique em **"Entrar"**
- Se as credenciais forem corretas, você terá acesso ao menu principal

---

## Jogar

### Menu Principal

Após fazer login, você verá o menu principal:

```
┌──────────────────────────────────┐
│ Bem-vindo, João!                 │
│ Pontos: 150 | Fase: Explorador   │
├──────────────────────────────────┤
│  ► [Iniciar Jogo]                │
│  ► [Continuar Partida]           │
│  ► [Ver Ranking]                 │
│  ► [Sair]                        │
└──────────────────────────────────┘
```

### Iniciando uma Partida

**Clique em "Iniciar Jogo"** para começar uma nova partida.

Você será levado à tela do jogo:

```
┌──────────────────────────────────────┐
│ Posição: 5/30  Pontos: 100  Fase: 1  │
├──────────────────────────────────────┤
│                                      │
│        Tabuleiro com 30 casas        │
│                                      │
│  Próximo desafio está na posição 7   │
├──────────────────────────────────────┤
│          [Lançar Dado]               │
│         [Voltar ao Menu]             │
└──────────────────────────────────────┘
```

### Lançar o Dado

**Clique em "Lançar Dado"** para:
1. Gerar um número aleatório de 1 a 6
2. Mover sua peça no tabuleiro
3. Se houver um desafio na casa, aparecer automaticamente

### Responder Desafio

Quando um desafio aparecer:

```
┌────────────────────────────────────┐
│     Missão Explorador 1            │
│ Seu primeiro desafio como          │
│ explorador                         │
├────────────────────────────────────┤
│ O que significa exploração         │
│ profissional?                      │
│                                    │
│ ◯ Investigar novas oportunidades   │
│ ◯ Ignorar novas áreas              │
│ ◯ Ficar na zona de conforto        │
│ ◯ Não aprender nada novo           │
│                                    │
│ Pontos em jogo: 10                 │
│     [Confirmar Resposta]           │
└────────────────────────────────────┘
```

**Como fazer:**
1. Selecione **uma das 4 opções**
2. Clique em **"Confirmar Resposta"**
3. Você verá se acertou ou errou

**Se acertar:**
- ✅ Ganha os pontos
- ➡️ Avança no tabuleiro

**Se errar:**
- ❌ Não ganha pontos
- ➡️ Continua no tabuleiro

### Progresso nas Fases

À medida que avança, você passa por 6 fases:

| Fase | Nome | Descrição |
|------|------|-----------|
| 1 | Explorador | Explora novas oportunidades |
| 2 | Conector | Conecta com pessoas e ideias |
| 3 | Transformador | Transforma conhecimento em ação |
| 4 | Conhecedor | Adquire conhecimento profundo |
| 5 | Planejador | Planeja o futuro |
| 6 | Realizador | Realiza seus objetivos |

### Finalizar Partida

A partida termina quando:
- ✅ Você chega na casa **30** (final do tabuleiro)
- ⚠️ Clica em **"Voltar ao Menu"**

Você verá sua pontuação final e será redirecionado ao menu.

---

## Regras do Jogo

### Pontuação
- Cada desafio correto = **pontos** (varia por fase)
- Fase 1 (Explorador): **10 pontos**
- Fase 2 (Conector): **15 pontos**
- Fase 3 (Transformador): **20 pontos**
- Fase 4 (Conhecedor): **20 pontos**
- Fase 5 (Planejador): **25 pontos**
- Fase 6 (Realizador): **30 pontos**

### Movimento
- Cada dado resulta em **1 a 6** casas de movimento
- Total de **30 casas** no tabuleiro
- Você avança uma fase a cada 5 casas

### Desafios
- Nem toda casa tem desafio
- Desafios têm **4 opções** (A, B, C, D)
- **1 resposta correta** por desafio
- Não há penalidade por resposta errada (apenas não ganha pontos)

---

## Ranking

### Ver o Ranking

No menu principal, clique em **"Ver Ranking"**.

Você verá a tabela:

```
┌─────┬──────────────┬──────────────┬────────────┐
│ Pos │ Jogador      │ Maior Pontua │ Partidas   │
├─────┼──────────────┼──────────────┼────────────┤
│ 1º  │ João Silva   │ 450          │ 12         │
│ 2º  │ Maria Santos │ 420          │ 8          │
│ 3º  │ Pedro Costa  │ 380          │ 5          │
│ ... │              │              │            │
└─────┴──────────────┴──────────────┴────────────┘
```

**Informações:**
- **Posição**: Seu ranking geral
- **Jogador**: Nome do jogador
- **Maior Pontuação**: Recorde de pontos
- **Partidas**: Total de jogos completados

---

## Dicas e Estratégias

### 🎯 Para Ganhar Mais Pontos

1. **Responda corretamente**
   - Leia com atenção todas as opções
   - Não tenha pressa em escolher

2. **Foque nas fases finais**
   - As últimas fases (5 e 6) têm mais pontos
   - Vale a pena chegar lá!

3. **Estude as respostas**
   - Algumas perguntas podem se repetir
   - Memorize as respostas

### 📚 Temas dos Desafios

- **Fase 1**: Exploração profissional
- **Fase 2**: Networking e comunicação
- **Fase 3**: Transformação e inovação
- **Fase 4**: Aprendizado profundo
- **Fase 5**: Planejamento de carreira
- **Fase 6**: Realização de objetivos

### ⏱️ Tempo

- Não há limite de tempo para responder
- Jogue no seu próprio ritmo

---

## Perguntas Frequentes

### P: Posso começar novamente?
**R:** Sim! Clique em "Iniciar Jogo" no menu para começar uma nova partida. Sua pontuação anterior será mantida no histórico.

### P: Como recupero minha senha?
**R:** Atualmente, não há sistema de recuperação de senha. Se esquecer, sua conta terá acesso restrito. Considere criar uma nova conta.

### P: Posso jogar com amigos?
**R:** Atualmente, o jogo é individual. Cada pessoa cria sua conta e compete no ranking!

### P: Quanto tempo leva uma partida?
**R:** Depende de você! Em média, uma partida leva de **5 a 15 minutos**.

### P: Quantas partidas posso jogar?
**R:** Ilimitadas! Todas serão registradas no banco de dados.

### P: Minha pontuação foi zerada, por quê?
**R:** Cada partida é independente. Você começa com 0 pontos. A maior pontuação é armazenada no ranking.

### P: Como editar meu perfil?
**R:** Atualmente, você só pode mudar nome e email jogando uma nova partida. Para alterações maiores, contate o administrador.

### P: O jogo funciona em tablets/celulares?
**R:** Não. O jogo foi desenvolvido para desktop (Windows, Linux, macOS).

### P: Há níveis de dificuldade?
**R:** Não. Todas as perguntas têm o mesmo nível nesta versão.

---

## Suporte e Contato

Se tiver dúvidas ou problemas:

1. Verifique este manual
2. Leia o README.md
3. Consulte o SETUP_GUIDE.md para problemas de instalação
4. Entre em contato com o suporte técnico

---

## Bom Jogo! 🎮

Aproveite sua jornada através das 6 fases e boa sorte no ranking!

**Instituto Mauá de Tecnologia**
