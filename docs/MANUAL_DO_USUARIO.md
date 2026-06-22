# Manual do Usuário — Jornada do Conhecimento

Jogo de tabuleiro digital (desktop, Java Swing) baseado na trilha da disciplina
**Conexão e Desenvolvimento Profissional (CDP)** do Instituto Mauá de Tecnologia.

---

## 1. Objetivo do jogo

Percorrer um tabuleiro de **30 casas** divididas em **6 fases** da jornada do estudante —
**Explorador → Conector → Transformador → Conhecedor → Planejador → Realizador** —
respondendo a um **desafio** em cada casa. Cada acerto soma **pontos** (10 a 35, crescendo
por fase). Ao chegar à última casa, a **jornada é concluída** e a pontuação entra no
**ranking global**.

---

## 2. Antes de começar (instalação)

**Pré-requisitos:** Java (JDK) instalado e MySQL em execução.

1. **Banco de dados:** abra o MySQL Workbench e execute o arquivo [`database.sql`](../database.sql).
   Ele cria o banco `jogo_maua` com as tabelas `fases`, `jogadores`, `desafios`, `partidas`
   e `respostas`, e já insere os 30 desafios.
2. **Credenciais:** se necessário, ajuste usuário/senha do MySQL em
   `src/com/maua/jogo/util/ConexaoBD.java`.
3. **Executar:**
   - **Windows:** dê duplo clique em [`INSTALAR_E_JOGAR.bat`](../INSTALAR_E_JOGAR.bat)
     (baixa o driver MySQL se faltar, compila e abre o jogo).
   - **NetBeans:** abra o projeto e clique em **Run**.

Se aparecer "Não foi possível conectar ao banco de dados", confira se o MySQL está rodando,
se o banco `jogo_maua` foi criado e se as credenciais estão corretas.

---

## 3. Como jogar (passo a passo)

1. **Cadastro:** na tela de login, clique em **Criar Nova Conta**, preencha nome, e-mail e
   senha e confirme em **Cadastrar**.
2. **Login:** informe e-mail e senha e clique em **Entrar** (ou pressione **Enter**).
3. **Tabuleiro:** você verá as 30 casas (cada fase tem uma cor, uma **sigla** e um **ícone de
   forma** próprios) e um painel de **Status** com jogador, pontos, fase e posição.
4. **Lançar o dado:** clique em **LANÇAR DADO** (ou tecle **Espaço**). Seu peão avança o número
   de casas sorteado; a casa atual fica destacada com **borda preta** e o marcador **"▶ VOCÊ"**.
5. **Responder o desafio:** ao parar numa casa, abre-se uma pergunta com 4 opções
   (a ordem é **embaralhada** a cada vez). Clique na opção ou tecle **1 a 4**. Você recebe
   retorno de **acerto/erro** (visual, textual e sonoro) e, se acertar, ganha os pontos da casa.
6. **Progresso salvo:** posição, fase, pontos e cada resposta dada ficam registrados no banco —
   ao sair e logar de novo, você retoma de onde parou.
7. **Fim da jornada:** ao concluir a casa 30, aparece a mensagem de **Jornada Concluída**, o
   **ranking** é exibido e você volta ao login.

**Ranking:** clique em **Ver Ranking Global** a qualquer momento para ver os melhores jogadores
(maior pontuação e número de partidas).

**Sair:** o botão **Logout** (canto inferior esquerdo) pede **confirmação** antes de encerrar a
partida — assim você não perde o progresso por um clique acidental.

---

## 4. Recursos de acessibilidade

O jogo foi adaptado para diferentes perfis de usuários. No topo de cada tela principal há:

| Controle | Função |
|---|---|
| **A-** / **A+** | Diminui / aumenta o tamanho da fonte de toda a interface |
| **Alto Contraste** | Alterna para um tema escuro de alto contraste |

Outros recursos:
- **Fases distinguíveis sem depender da cor:** cada fase tem **sigla** (EXP, CNT, TRF, CNH, PLN,
  REA) e um **ícone de forma diferente**, úteis para daltônicos.
- **Leitor de tela (NVDA):** os campos, botões e casas têm nome/descrição acessíveis. Ative a
  **Java Access Bridge** uma vez no Windows com o comando `jabswitch -enable`.
- **Operação por teclado** (sem mouse) — veja os atalhos abaixo.

### Atalhos de teclado

| Tecla | Ação |
|---|---|
| **Enter** | Confirma o formulário (login / cadastro) |
| **Espaço** | Lança o dado (na tela do tabuleiro) |
| **1, 2, 3, 4** | Seleciona a opção correspondente no desafio |
| **Alt + letra sublinhada** | Aciona o botão/campo (ex.: Alt+E = Entrar, Alt+L = Lançar) |
| **Tab** | Navega entre os elementos da tela |

---

## 5. Dúvidas frequentes

- **O jogo não abre / erro de conexão:** o MySQL não está rodando ou o banco não foi criado.
  Execute [`database.sql`](../database.sql) e confira as credenciais em `ConexaoBD.java`.
- **As fases parecem iguais:** ative o **Alto Contraste** ou observe a **sigla/ícone** de cada
  casa — a cor é apenas um reforço.
- **Quero recomeçar:** basta jogar uma nova partida; cada partida concluída é somada ao seu
  histórico e ao ranking.
