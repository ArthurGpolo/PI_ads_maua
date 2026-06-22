# Relatório Final de Acessibilidade
## Jogo "Jornada do Conhecimento" — IMT / Instituto Mauá de Tecnologia

**Disciplina:** Projeto Integrador
**Tipo de sistema:** Aplicação desktop em Java Swing (NetBeans), Look & Feel Nimbus
**Data:** 21/06/2026
**Escopo avaliado:** TelaLogin, TelaCadastro, TelaTabuleiro, TelaDesafio e Main (configuração global)

---

## 1. Introdução

O sistema avaliado é um **jogo de tabuleiro digital desktop** desenvolvido em **Java Swing** sobre o ambiente NetBeans, com tema "Jornada do Conhecimento" para o Instituto Mauá de Tecnologia (IMT). O *Look & Feel* adotado é o **Nimbus**, definido em `Main.java`, do qual decorrem o fundo padrão de painel (~RGB 214,217,223), o texto padrão preto e a fonte padrão de aproximadamente 12 pt. O jogo é composto por quatro telas principais: a tela de login (`JFrame` não redimensionável 400×350), a tela de cadastro (`JFrame` 350×400 em `GridLayout(8,1)`), a tela do tabuleiro (`JFrame` 1200×800 com tabuleiro em `GridLayout(6,5)`, painel de status, log de partida e botões LANÇAR DADO / Logout / Ranking) e a tela de desafio (`JDialog` modal 600×450 com a pergunta e quatro botões de opção).

**Adaptação metodológica.** A atividade referencia critérios e ferramentas das **WCAG** (WebAIM Contrast Checker, Stark, WhoCanUse), que são **orientadas para a web** (CSS, navegador, zoom de página). Como este projeto **não é uma aplicação web**, os **mesmos critérios WCAG** foram **adaptados ao contexto Swing/desktop**:

- não há CSS — as cores são definidas via `java.awt.Color` (`setBackground`/`setForeground`) e as fontes via `java.awt.Font` (`setFont`);
- não há zoom de navegador — a ampliação depende de redimensionamento de janela e de controles próprios de fonte;
- a acessibilidade para leitores de tela (como o **NVDA**) depende da **Java Accessibility API** (`AccessibleContext`, `setAccessibleName`/`setAccessibleDescription`, `setLabelFor`) exposta via **Java Access Bridge**, no lugar de ARIA/HTML semântico.

Os contrastes apresentados na seção 2.1 foram **calculados deterministicamente** a partir das cores reais usadas no código-fonte, servindo de "chão de verdade". Além das WCAG e das NBR de acessibilidade (web e dispositivos móveis), a avaliação incorpora as **heurísticas de acessibilidade em jogos digitais** (diretrizes Cheiran/UFRGS), pertinentes ao caráter lúdico e por turnos do sistema. Os perfis-alvo são pessoas com limitações **visuais** (baixa visão, daltonismo, cegueira/leitor de tela), **motoras** (alvos de clique, operação por teclado) e **cognitivas** (clareza, feedback, recuperação de erro).

**Veredito geral.** O aplicativo é **operável por mouse e tem excelente contraste de texto/fundo**, mas é **INADEQUADO** para os três perfis no estado atual, por três causas estruturais: (a) **cor como único meio** de transmitir informação (fases e posição do jogador); (b) **tipografia majoritariamente abaixo de 16 px** com causa-raiz na ausência de defaults de fonte; e (c) **ausência total de instrumentação para leitor de tela e de operação por teclado** (sem `AccessibleContext`, sem botão default, sem mnemônicos).

---

## 2. Avaliação por critérios

### 2.1 Cores e Contraste

O código usa cor explícita em poucos pontos: dois botões coloridos (azul de login, verde do dado), dois fundos de painel claros (status, pergunta), seis fundos pastel de fase e bordas (`DARK_GRAY`/`RED`). Quanto ao **contraste texto/fundo puro**, o aplicativo está **majoritariamente conforme**: todos os textos pretos sobre fundos claros/pastel ficam entre **14,9:1 e 20,6:1** (muito acima de 4,5:1) e o botão azul de login passa com **5,54:1**.

Há **um ponto limítrofe de contraste** (botão verde do dado) e **dois problemas graves que não são de razão de contraste, mas de cor-como-único-meio (WCAG 1.4.1)**: as seis fases são distinguidas **apenas pela cor de fundo** (luminâncias quase idênticas, até **1,02:1** entre pares) e a posição atual do jogador é marcada **apenas por borda vermelha de 4 px**, que ainda **falha o contraste de componente não-textual (WCAG 1.4.11)** em pelo menos uma fase (ver tabela). Não existe modo de alto contraste nem alternativa não-cromática (ícone, rótulo, padrão).

#### Tabela de pares cor/contraste

| # | Par avaliado (texto/fundo ou elemento) | Tela / arquivo:linha | Cores reais (RGB) | Contraste | Limite aplicável | Classificação |
|---|---|---|---|---|---|---|
| 1 | Botão "Entrar" — branco sobre azul | TelaLogin:44-46 | `#FFFFFF` / `(50,100,200)` | **5,54:1** | 4,5:1 (texto normal) | ✅ Adequado |
| 2 | Botão "LANÇAR DADO" — branco sobre verde | TelaTabuleiro:80-83 | `#FFFFFF` / `(50,150,50)` | **3,79:1** | passa 3:1 (texto grande, BOLD 18); **falha** 4,5:1 (normal) | ⚠️ Parcial |
| 3 | Texto da casa — preto sobre fundo EXPLORADOR | TelaTabuleiro:116 | `#000000` / `(220,255,220)` | **19,4:1** | 4,5:1 | ✅ Adequado |
| 4 | Texto da casa — preto sobre fundo CONECTOR | TelaTabuleiro:117 | `#000000` / `(220,220,255)` | **15,7:1** | 4,5:1 | ✅ Adequado |
| 5 | Texto da casa — preto sobre fundo TRANSFORMADOR | TelaTabuleiro:118 | `#000000` / `(255,255,220)` | **20,6:1** | 4,5:1 | ✅ Adequado |
| 6 | Texto da casa — preto sobre fundo CONHECEDOR | TelaTabuleiro:119 | `#000000` / `(255,220,255)` | **16,9:1** | 4,5:1 | ✅ Adequado |
| 7 | Texto da casa — preto sobre fundo PLANEJADOR | TelaTabuleiro:120 | `#000000` / `(220,255,255)` | **19,8:1** | 4,5:1 | ✅ Adequado |
| 8 | Texto da casa — preto sobre fundo REALIZADOR | TelaTabuleiro:121 | `#000000` / `(255,220,220)` | **16,5:1** | 4,5:1 | ✅ Adequado |
| 9 | Painel de status — preto sobre azul-claro | TelaTabuleiro:39-50 | `#000000` / `(230,240,250)` | **18,2:1** | 4,5:1 | ✅ Adequado |
| 10 | Pergunta do desafio — preto sobre cinza-claro | TelaDesafio:26-32 | `#000000` / `(245,245,245)` | **19,3:1** | 4,5:1 | ✅ Adequado |
| 11 | Labels padrão — preto sobre fundo Nimbus | TelaLogin / TelaCadastro | `#000000` / `(214,217,223)` | **14,9:1** | 4,5:1 | ✅ Adequado |
| 12 | Borda da casa (separação estrutural) — `DARK_GRAY` sobre pastel | TelaTabuleiro:104 | `(64,64,64)` / pastel | > 3:1 | 3:1 (1.4.11) | ✅ Adequado |
| 13 | **Distinção ENTRE fundos de fase** (par mais próximo) | TelaTabuleiro:116-121 | pastel × pastel | **até 1,02:1** | informação por cor (1.4.1) | ❌ Inadequado |
| 14 | **Borda vermelha "você está aqui"** vs. fundos de fase | TelaTabuleiro:160-166 | `(255,0,0)` 4px / pastel | **2,99–3,92:1** (falha contra CONECTOR) | 3:1 (1.4.11) + 1.4.1 | ❌ Inadequado |

> **Observações sobre a tabela.** As linhas 13 e 14 são os pontos críticos: o problema **não** é o contraste do texto (excelente em todas as casas), mas (i) a **indistinguibilidade entre fases** para daltônicos/visão monocromática e (ii) o **marcador de posição por cor única** que, além de ser sinal vermelho/verde, **não atinge 3:1** contra o fundo CONECTOR (faixa real **2,99–3,92:1**), reprovando a WCAG 1.4.11. O contraste de texto, isoladamente, não resolve nenhum desses dois problemas.

### 2.2 Tipografia e Legibilidade

Resultado geral: **PARCIAL/INADEQUADO**. Apenas quatro fontes atingem o mínimo de **16 px**: título do login (Arial BOLD 20), número da casa (Arial BOLD 16), botão LANÇAR DADO (Arial BOLD 18) e enunciado da pergunta (Arial BOLD 16). **Todo o restante fica abaixo de 16 px.**

- **Causa-raiz (alta).** `Main.java` define o Nimbus mas **nunca sobrescreve os defaults de fonte** (não há `UIManager.put("Label.font", ...)` etc.). Em consequência, todo componente sem `setFont` explícito — rótulos e campos do login, **toda a TelaCadastro**, `btnRanking`, `btnSair`, `btnEntrar`, `btnCadastrar` — renderiza com a fonte padrão Nimbus (~12 px). Corrigir aqui eleva a base de várias telas de uma só vez.
- **Pior ponto pontual (alta).** A **descrição da casa** (`lblDesc`) usa **Arial ITALIC 10** (`TelaTabuleiro.java:110`): 10 px (6 abaixo do mínimo) **e itálico**, a combinação menos legível do sistema. Como a cor das fases é indistinguível para daltônicos, este rótulo textual **deveria ser** a alternativa à cor — porém é justamente o texto mais ilegível da tela.
- **TelaCadastro (alta).** Nenhuma chamada `setFont` em todo o arquivo e **nenhuma hierarquia de título** — apenas um `GridLayout(8,1)` empilhando rótulos pequenos, ao contrário do login, que tem título BOLD 20.
- **Abaixo do mínimo (médias/baixas).** Painel de status em BOLD 14; botões de opção do desafio em PLAIN 14; `btnRanking`/`btnSair` herdando ~12 px (inconsistentes com o `btnDado` BOLD 18); rótulos/campos/botões do login em ~12 px (só o título passa).
- **Espaçamento e blocos.** O log (`JTextArea` Monospaced 12) e a pergunta (`JTextArea`) usam *leading* padrão do componente, sem entrelinha aumentada — texto colado, que dificulta rastreamento (baixa visão) e leitura (dislexia).
- **Ampliação (alta).** Nenhuma tela oferece zoom ou ajuste de fonte, e o **login está com `setResizable(false)`** (`TelaLogin.java:22`), removendo a única alavanca manual de ampliação. Sem CSS/zoom de navegador, o usuário de baixa visão **não tem como aumentar o texto**.

**Correção central recomendada:** definir defaults de fonte do Nimbus em `Main.java` (`UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial", Font.PLAIN, 16))` e/ou as chaves `Label.font`/`Button.font`/`TextField.font`/`TextArea.font`/`TitledBorder.font`), antes de instanciar a TelaLogin; corrigir pontualmente a descrição da casa (PLAIN 14–16, sem itálico), status (≥16), opções do desafio (≥16); e adicionar controle de zoom de fonte (A−/A+).

### 2.3 Layout e Interação

O projeto acerta em pontos isolados: `btnDado` tem `setPreferredSize(200,50)` (alvo amplo); a TelaDesafio usa `GridLayout(4,1)` com gap 10 e é um diálogo modal; o tabuleiro usa gaps de 8 px e bordas. Porém há **falhas sistêmicas de interação** que afetam sobretudo os perfis **motor** (teclado) e **cognitivo** (clareza, feedback, recuperação de erro):

- **Sem operação por teclado (alta).** Nenhuma tela define botão default (`getRootPane().setDefaultButton`), então **Enter não confirma** login/cadastro/desafio. Nenhum componente recebe **foco inicial** (`requestFocusInWindow`), e **não há mnemônicos** (`setMnemonic`) nem atalhos para LANÇAR DADO e para as opções do desafio. Quem não usa mouse depende de Tab+Espaço, lento e não descobrível num jogo de turnos.
- **Logout perigoso (alta).** `btnSair` (Logout) fica **no mesmo `FlowLayout`, ao lado do `btnDado`**, com apenas ~20 px de gap e **sem confirmação**. Um clique impreciso encerra a partida e descarta o progresso. Pior: o handler de Logout aparece **duplicado** em `TelaTabuleiro.java` (linhas ~88-90 e ~140-142), o que indica lógica redundante a consolidar. Some-se a isso `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)` (linha 33), que **encerra a JVM** ao clicar no "X", sem aviso.
- **Alvos de clique pequenos (média).** `btnSair`, `btnRanking`, `btnSalvar`/`btnCadastrar` e os quatro botões de opção do desafio **não definem `setPreferredSize`**, herdando a altura mínima do Nimbus (~25-28 px), abaixo do alvo recomendado de ~44 px (WCAG 2.5.5). `btnDado` (200×50) é o bom contraexemplo.
- **Destaque de posição fora da viewport (média).** O tabuleiro fica num `JScrollPane`; ao mover, `atualizarInterface()` apenas troca a borda da casa atual, **sem `scrollRectToVisible`** — a posição corrente pode ficar fora da área visível.
- **Fluxo de janelas confuso (média).** A TelaCadastro abre como `JFrame` **não-modal** sobre o login, permitindo múltiplas janelas e foco ambíguo. O ideal é torná-la um `JDialog` modal com o login como dono (como a TelaDesafio faz).
- **Feedback restrito (baixa/média).** O resultado do dado vai **apenas para o `txtLog`** (não focado, não anunciado), e as mensagens de erro/validação usam **`JOptionPane` genérico**, sem indicar qual campo falhou nem devolver o foco a ele. Não há feedback sonoro (diferencial multimodal previsto e exigido pelas heurísticas Cheiran de feedback redundante).
- **Sem instrumentação para leitor de tela (alta).** Em nenhum dos arquivos há `getAccessibleContext().setAccessibleName/Description` ou `setLabelFor`. As 30 casas são `JPanel` decorativos sem nome acessível; o jogador cego não forma modelo mental do tabuleiro (posição, fase, resultado do dado).
- **Teste de uso com 1 pessoa (exigido).** Não há evidência registrada — pendência do grupo (ver seção final). Roteiro sugerido: cadastrar, logar, lançar o dado, responder um desafio e ver o ranking, registrando dificuldades observadas (distinguir fases por cor, clicar nas opções, ler a descrição em itálico 10 px).

---

## 3. Problemas identificados (priorizados por severidade)

### Severidade ALTA

1. **Ausência total de suporte a leitor de tela** (Java Accessibility API) em toda a aplicação — sem `setAccessibleName/Description`/`setLabelFor`. *WCAG 4.1.2.* `TelaLogin.java:9-54` (e análogos em TelaCadastro, TelaTabuleiro, TelaDesafio). Perfil: visual-cegueira/baixa visão.
2. **Tabuleiro não expõe estado nem semântica ao leitor de tela** — casas decorativas, resultado do dado só no log. *WCAG 1.3.1 / 4.1.3.* `TelaTabuleiro.java:100-127, 154-167`. Perfil: visual/cognitivo.
3. **Fases codificadas SOMENTE por cor de fundo** (luminâncias quase idênticas, 1,02:1 entre pares) — indistinguíveis para daltonismo/visão monocromática. *WCAG 1.4.1.* `TelaTabuleiro.java:115-122`. Perfil: visual-daltonismo/monocromático.
4. **Posição atual marcada SÓ por borda vermelha** — sinal vermelho/verde **e** contraste de componente real **2,99-3,92:1**, que **FALHA o mínimo de 3:1 (WCAG 1.4.11) contra o fundo CONECTOR**. *WCAG 1.4.1 + 1.4.11.* `TelaTabuleiro.java:160-166`. Perfil: visual-daltonismo/monocromático/leitor de tela.
5. **Nenhuma operação por teclado** — sem botão default, mnemônicos ou foco inicial; Enter não confirma formulários. *WCAG 2.1.1 / 2.4.3.* `TelaLogin.java:44-52, 56-67` (e demais telas). Perfil: motor/visual-cegueira.
6. **Causa-raiz de tipografia:** `Main.java` define Nimbus mas não sobrescreve defaults de fonte (~12 px herdado por todo o app). *Critério 2.* `Main.java:14-20`. Perfil: baixa visão/dislexia/idosos.
7. **Descrição da casa em Arial ITALIC 10** — menor e menos legível do sistema, e é a (frágil) alternativa textual à cor das fases. *Critério 2.* `TelaTabuleiro.java:110`. Perfil: baixa visão/dislexia/daltonismo.
8. **TelaCadastro inteira em ~12 px e sem hierarquia de título.** *Critério 2.* `TelaCadastro.java:23-38`. Perfil: baixa visão/cognitivo.
9. **Ausência de zoom/ajuste de fonte e login não redimensionável** (`setResizable(false)`). *WCAG 1.4.4 adaptado.* `TelaLogin.java:22`. Perfil: baixa visão/idosos/motor.
10. **Logout adjacente ao botão primário, sem confirmação, com handler duplicado e `EXIT_ON_CLOSE`** — saída acidental e perda de progresso. *WCAG 3.3.4 / heurística Cheiran.* `TelaTabuleiro.java:33, 79-95` (duplicação em ~88-90 e ~140-142). Perfil: motor/cognitivo.

### Severidade MÉDIA

11. **Botão verde "LANÇAR DADO" em 3,79:1** — passa como texto grande (BOLD 18, 3:1), mas falha 4,5:1 de texto normal. *WCAG 1.4.3.* `TelaTabuleiro.java:80-83`. Perfil: baixa visão/ambiente de baixo contraste.
12. **Ausência de modo de alto contraste / tema acessível.** *WCAG 1.4.3/1.4.6 + Cheiran.* `Main.java:14-23`. Perfil: baixa visão severa/sensibilidade visual.
13. **Feedback do dado e da movimentação apenas visual/textual, sem alternativa sonora.** *WCAG 1.1.1 / Cheiran.* `TelaTabuleiro.java:129-152`. Perfil: visual/cognitivo.
14. **Mensagens de erro genéricas via `JOptionPane`** — não identificam o campo nem devolvem o foco. *WCAG 3.3.1 / 3.3.3.* `TelaCadastro.java:47-57`; `TelaLogin.java:63`. Perfil: cognitivo/visual/motor.
15. **Alvos de clique abaixo de ~44 px** na maioria dos botões. *WCAG 2.5.5.* `TelaDesafio.java:35-45` e demais. Perfil: motor/baixa visão.
16. **Casa atual não é rolada para a viewport** (`scrollRectToVisible` ausente). *WCAG 2.4.3/2.4.7.* `TelaTabuleiro.java:61, 154-167`. Perfil: visual/cognitivo.
17. **Fluxo de janelas confuso:** TelaCadastro como `JFrame` não-modal. *WCAG 3.2.x.* `TelaLogin.java:67`; `TelaCadastro.java:7,20`. Perfil: cognitivo/visual.
18. **Status em BOLD 14 e opções do desafio em PLAIN 14** (< 16 px). *Critério 2.* `TelaTabuleiro.java:46-50`; `TelaDesafio.java:30-41`. Perfil: baixa visão/cognitivo.
19. **Componentes sem nome/descrição acessível** para navegação por leitor de tela. *WCAG 4.1.2.* `TelaTabuleiro.java:100-127, 66-70`; `TelaDesafio.java:26-46`. Perfil: visual.

### Severidade BAIXA

20. **`btnRanking`/`btnSair` sem fonte definida** (herdam ~12 px), inconsistentes com o `btnDado`. *Critério 2.* `TelaTabuleiro.java:72-86`.
21. **Log e pergunta sem entrelinha aumentada.** *Critério 2.* `TelaTabuleiro.java:66-68`; `TelaDesafio.java:26`.
22. **Hierarquia tipográfica inconsistente entre telas** (TitledBorder ~12 px como único cabeçalho). *Critério 2.* `TelaTabuleiro.java:37-50`.
23. **TelaCadastro em `GridLayout(8,1)`** sem agrupamento rótulo-campo, sem `setLabelFor`, com espaçador "gambiarra". *WCAG 1.3.1 / 3.3.2.* `TelaCadastro.java:23-40`.
24. **Ranking em `JTable`/`JOptionPane` sem nome acessível.** *WCAG 1.3.1 / 4.1.2.* `TelaTabuleiro.java:169-184`.

### Observação de qualidade correlata (fora do escopo estrito de acessibilidade, mas pertinente à banca)

- **Segurança:** indícios de **senha armazenada em texto puro** (`JogadorDAO`, ~linhas 23 e 60-68) — recomenda-se hash (ex.: BCrypt).
- **Banco de desafios:** os 30 desafios aparentam ter **resposta correta sempre na opção A**, o que prejudica a validade do jogo e deve ser revisado.

---

## 4. Melhorias que podem ser implementadas (resumo)

> O detalhamento técnico (trechos de código, valores e passos) compõe o **plano de implementação** anexo; aqui está apenas a síntese priorizada.

**Cores e contraste**
- Escurecer o verde do `btnDado` para ≥ 4,5:1 com branco (ex.: `(0,110,40)` ou `(34,120,34)`), mantendo `Color.WHITE`.
- Adicionar **redundância não-cromática por fase**: rótulo/sigla legível (`lblDesc` ≥ 12-14 px, sem itálico), ícone/padrão e/ou diferenciação por **luminância** (não só matiz).
- Substituir/reforçar o destaque "você está aqui" com **marcador não-cromático** (ícone de peão, texto "AQUI", seta) **e** garantir contraste ≥ 3:1 (corrigindo a falha 1.4.11 contra o fundo CONECTOR).
- Oferecer **modo de alto contraste** centralizando cores numa classe de tema.

**Tipografia**
- Definir **defaults de fonte ≥ 16 px** no `Main.java` (resolve a base de várias telas) e corrigir pontualmente descrição da casa, status e opções do desafio.
- Adicionar **título** à TelaTabuleiro e à TelaCadastro e padronizar a escala (título BOLD 20, seção BOLD 16, corpo PLAIN 16).
- Implementar **controle de zoom/fonte (A−/A+)** e **remover `setResizable(false)`** do login.

**Layout e interação**
- Definir **botão default** e **foco inicial** em cada tela; adicionar **mnemônicos** e atalho para LANÇAR DADO e teclas 1-4 no desafio.
- **Separar fisicamente** o Logout do botão primário, adicionar **confirmação**, **consolidar o handler duplicado** e trocar `EXIT_ON_CLOSE` por confirmação no fechamento.
- Padronizar **alvos ≥ 44 px**; chamar `scrollRectToVisible` na casa atual; tornar o **cadastro modal**.
- **Instrumentar a Java Accessibility API** (nomes/descrições acessíveis, `setLabelFor`) e habilitar a **Java Access Bridge** (`jabswitch -enable`).
- **Feedback multimodal**: anunciar o resultado do dado para o leitor de tela e adicionar som (com texto sempre presente); validar erros **campo a campo** com retorno de foco.

---

## 5. Conclusão

No estado atual, o jogo tem uma base sólida de **contraste de texto** (todos os textos pretos entre 14,9:1 e 20,6:1; botão de login a 5,54:1) e arquitetura MVC organizada, mas é **inacessível para os três perfis-alvo** por três barreiras estruturais: a informação de estado do jogo depende **exclusivamente da cor**, a tipografia está **majoritariamente abaixo de 16 px sem possibilidade de ampliação**, e **não há operação por teclado nem suporte a leitor de tela**.

O impacto das melhorias propostas sobre a experiência do usuário é direto e cumulativo:

- **Perfil visual.** Tornar as fases distinguíveis por rótulo/ícone/luminância e a posição por marcador não-cromático elimina a dependência de cor; elevar as fontes a ≥ 16 px e oferecer zoom resolve a legibilidade para baixa visão; instrumentar o `AccessibleContext` e a Access Bridge dá ao jogador **cego** um modelo mental do tabuleiro (casa, fase, posição, resultado do dado) — passando de "inviável" para "jogável de forma autônoma".
- **Perfil motor.** Botão default, mnemônicos, atalhos, foco inicial e alvos ≥ 44 px permitem jogar **sem mouse e com baixa precisão**; separar o Logout e adicionar confirmação remove o risco de saída acidental.
- **Perfil cognitivo.** Hierarquia tipográfica consistente, feedback multimodal claro do dado e dos desafios, mensagens de erro específicas com retorno de foco e prevenção de ações destrutivas reduzem a carga cognitiva e habilitam **recuperação de erro** — exatamente o que as heurísticas de jogos digitais (Cheiran/UFRGS) priorizam.

Em síntese, as correções são **de baixo custo e alto retorno**: várias derivam de uma única mudança central (defaults de fonte no `Main.java`) e da adoção sistemática da Java Accessibility API, convertendo um aplicativo hoje **operável apenas por mouse e com visão plena** em um jogo **perceptível, operável e compreensível** pelos públicos com deficiência visual, motora e cognitiva.

---

## Pendências de entrega do grupo (evidências manuais exigidas)

Os itens abaixo são **artefatos do grupo** (não código) exigidos pela atividade e que **dependem de execução manual**; **não constam no repositório** e precisam ser produzidos e versionados (sugestão: pasta `docs/`).

- [ ] **Prints das 4 telas com medição de contraste** — capturar TelaLogin (400×350), TelaCadastro (350×400), TelaTabuleiro (1200×800) e TelaDesafio (600×450). Para cada par texto/fundo, inserir as **cores reais do código** nas ferramentas (ex.: WebAIM/Stark/WhoCanUse adaptadas ao desktop): `btnEntrar` branco sobre `(50,100,200)`; `btnDado` branco sobre `(50,150,50)`; textos pretos sobre os pastéis. Anexar o screenshot da medição a cada print. *(ACESS-01)*
- [ ] **Marcações/anotações no Figma** — importar os prints e anotar visualmente cada problema sobre as telas: `btnDado` limítrofe (3,79:1), descrição em itálico 10 px, fontes < 16 px em rótulos/campos/botões, fundos de fase indistinguíveis (1,02:1 entre pares) e destaque de posição por cor única (borda vermelha, 2,99-3,92:1). Versionar o link. *(ACESS-02)*
- [ ] **Teste de uso com 1 colega** — conduzir teste com tarefas (cadastrar, logar, lançar o dado, responder um desafio, ver o ranking), registrar as dificuldades observadas e anexar evidência (anotações/foto/vídeo). Exigido explicitamente no Critério 3. *(ACESS-03)*
- [ ] **Tabela de classificação consolidada (Adequado/Parcial/Inadequado)** — montar tabela por tela × critério com justificativa, reaproveitando as linhas-chave já conhecidas (login 5,54:1 Adequado; `btnDado` 3,79:1 Parcial; textos sobre pastel 15-20:1 Adequado; cor-como-único-meio das fases e da posição Inadequado; tipografia < 16 px Inadequado/Parcial; ausência de zoom Inadequado). *(ACESS-04)*
- [ ] **Entrega final deste relatório** com as 5 seções exigidas (Introdução; Avaliação por critérios; Problemas; Melhorias; Conclusão), acompanhado dos artefatos acima. *(ACESS-05)*

> Pendências correlatas da especificação técnica (úteis para a banca, embora fora do escopo estrito de acessibilidade): **testes unitários** (não há pasta `test/` nem JUnit), **diagrama UML** (não versionado), **manual do usuário** voltado ao jogador (o `LEIA-ME.md` cobre apenas build/instalação) e **slides/demo** não versionados.