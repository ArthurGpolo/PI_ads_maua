# PLANO DE MELHORIAS DE ACESSIBILIDADE
## Jogo "Jornada do Conhecimento" — IMT / Instituto Mauá de Tecnologia
### Backlog acionável (Java Swing + Nimbus) — Tech Lead

---

## 1. Visão geral e princípios

Este documento é um **backlog priorizado** para tornar o jogo de tabuleiro digital (Java Swing, NetBeans, Look&Feel Nimbus) acessível aos perfis **visual** (baixa visão, daltonismo, cegueira/leitor de tela), **motor** (alvos de clique, operação por teclado) e **cognitivo** (clareza, feedback, recuperação de erro).

Por **não ser uma aplicação web**, as ferramentas da atividade (WebAIM, Stark, WhoCanUse) servem só para a *medição de contraste sobre prints*. Os **mesmos critérios WCAG são adaptados ao desktop**:

| Web | Equivalente Swing/desktop |
|---|---|
| CSS `color` / `background` | `java.awt.Color` via `setForeground`/`setBackground` |
| CSS `font-size` | `java.awt.Font` + `UIManager` defaults |
| DOM semântico / ARIA | **Java Accessibility API**: `AccessibleContext`, `setAccessibleName/Description`, `setLabelFor` |
| Leitor de tela (NVDA no DOM) | **NVDA via Java Access Bridge** (`jabswitch -enable`) |
| Zoom do navegador | `setResizable(true)` + escala global de fonte por `UIManager` + `updateComponentTreeUI` |

### Princípios norteadores
- **P-1 — Não confiar na cor como único meio (WCAG 1.4.1):** todo estado de jogo (fase, "você está aqui", acerto/erro) precisa de redundância textual/ícone/luminância.
- **P-2 — Contraste mínimo (1.4.3 texto, 1.4.11 não-textual):** texto normal ≥ 4.5:1, texto grande ≥ 3:1, elemento gráfico ≥ 3:1.
- **P-3 — Operável por teclado (2.1.1):** tudo que clica também aciona por teclado; botão default; foco inicial; mnemônicos.
- **P-4 — Legibilidade (Critério 2):** fonte base ≥ 16px, hierarquia consistente, sem itálico em rótulos funcionais, zoom disponível.
- **P-5 — Alvos amplos (2.5.5 / NBR mobile):** áreas clicáveis ≥ 44px de altura.
- **P-6 — Prevenção e recuperação de erro (3.3.1/3.3.4 / heurísticas Cheiran):** confirmar ações destrutivas, identificar o campo com erro, devolver foco.
- **P-7 — Feedback redundante (heurísticas de jogo, Cheiran/UFRGS):** visual + textual + sonoro, nunca um canal só.
- **P-8 — Centralizar tema:** cores e fontes numa classe de tema, viabilizando alto contraste e escala em runtime.

Base normativa: **WCAG 2.1** adaptado, **NBR 17225/17060** (acessibilidade), e **heurísticas de acessibilidade em jogos digitais (Cheiran/UFRGS)**.

### Chão-de-verdade de contraste (não recalcular)
- `btnEntrar` branco s/ azul (50,100,200) = **5.54:1 — ADEQUADO** (referência de paleta).
- `btnDado` branco s/ verde (50,150,50) = **3.79:1** — passa como texto grande (BOLD 18 ≥ 3:1) mas **FALHA texto normal 4.5:1**.
- Textos pretos s/ pastéis das casas = **15.7:1 a 20.6:1 — ADEQUADO**.
- Status preto s/ (230,240,250) = 18.2:1; pergunta preto s/ (245,245,245) = 19.3:1; label preto s/ Nimbus (214,217,223) = 14.9:1 — todos **ADEQUADOS**.
- **Cor-como-único-meio:** 6 fundos de fase têm luminância quase igual (até **1.02:1 entre pares**) → fases indistinguíveis para daltônicos/visão monocromática.
- **Borda vermelha "você está aqui"** (255,0,0) 4px: contra os pastéis dá **2.99–3.92:1** → **FALHA WCAG 1.4.11 (3:1) contra o fundo Conector** *e* é cor-como-único-meio (vermelho/verde).

---

## 2. Backlog priorizado

Esforço: **S** ≤ 2h · **M** meio dia · **L** ≥ 1 dia. Arquivos sob `src/com/maua/jogo/`.

---

## P0 — Crítico (corrige falha de acessibilidade)

---

### P0-1 — Suporte zero a leitor de tela em todo o app (instrumentar Java Accessibility API)
- **Perfil:** visual (cegueira), baixa visão
- **Critério:** WCAG 4.1.2 (Nome, função, valor) / NBR 17225 / Cheiran (compatibilidade com TA) — *constatação AT-01 (confirmada: "zero acessibilidade no src")*
- **Arquivos:** `view/TelaLogin.java:36-52`, `view/TelaCadastro.java:31-38`, `view/TelaTabuleiro.java`, `view/TelaDesafio.java:26-44`
- **Esforço:** M
- **Antes** (`TelaLogin.java:36-42`):
```java
gbc.gridy = 1; pnlPrincipal.add(new JLabel("E-mail:"), gbc);
txtEmail = new JTextField(20);
gbc.gridx = 1; pnlPrincipal.add(txtEmail, gbc);
gbc.gridx = 0; gbc.gridy = 2; pnlPrincipal.add(new JLabel("Senha:"), gbc);
txtSenha = new JPasswordField(20);
```
- **Depois:**
```java
JLabel lblEmail = new JLabel("E-mail:");
gbc.gridy = 1; pnlPrincipal.add(lblEmail, gbc);
txtEmail = new JTextField(20);
lblEmail.setLabelFor(txtEmail);                       // associa rótulo->campo p/ NVDA
txtEmail.getAccessibleContext().setAccessibleName("E-mail");
txtEmail.getAccessibleContext().setAccessibleDescription("Digite seu e-mail de acesso");
gbc.gridx = 1; pnlPrincipal.add(txtEmail, gbc);

JLabel lblSenha = new JLabel("Senha:");
gbc.gridx = 0; gbc.gridy = 2; pnlPrincipal.add(lblSenha, gbc);
txtSenha = new JPasswordField(20);
lblSenha.setLabelFor(txtSenha);
txtSenha.getAccessibleContext().setAccessibleName("Senha");
```
- **Aceite:** com NVDA + Access Bridge ativo (`jabswitch -enable`), ao tabular cada campo/botão é anunciado por **nome e propósito** (não "caixa de edição" genérica). Documentar a ativação do Access Bridge no manual.

---

### P0-2 — Tabuleiro não expõe estado/semântica ao leitor de tela
- **Perfil:** visual (cegueira/baixa visão), cognitivo
- **Critério:** WCAG 1.3.1 / 4.1.2 / 4.1.3 / Cheiran (estado perceptível sem visão) — *AT-02 (confirmada)*
- **Arquivos:** `view/TelaTabuleiro.java:100-127`, `:154-167`
- **Esforço:** M
- **Antes** (`:103-104`, `:160-166`):
```java
JPanel pnlCasa = new JPanel(new BorderLayout());
pnlCasa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
// ...
if (i == j.getPosicao() - 1) {
    casasGraficas.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 4));
}
```
- **Depois:**
```java
JPanel pnlCasa = new JPanel(new BorderLayout());
pnlCasa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
pnlCasa.getAccessibleContext().setAccessibleName(
    "Casa " + casa.getPosicao() + ", fase " + casa.getFase().getNome());
// ...
if (i == j.getPosicao() - 1) {
    JPanel atual = casasGraficas.get(i);
    atual.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5)); // ver P0-4
    atual.getAccessibleContext().setAccessibleDescription("Posição atual do jogador");
    atual.scrollRectToVisible(atual.getBounds());     // ver P1-7
}
```
- **Aceite:** NVDA, ao percorrer o tabuleiro, anuncia "Casa N, fase X" e marca a casa atual como "posição atual do jogador".

---

### P0-3 — Fases codificadas SOMENTE por cor de fundo (1.02:1 entre pares)
- **Perfil:** daltonismo (deuteranopia/protanopia), visão monocromática, baixa visão, cegueira
- **Critério:** WCAG 1.4.1 (Uso da cor) — *C1-02 (confirmada: "1.02 é o par mínimo")*
- **Arquivo:** `view/TelaTabuleiro.java:109-122`
- **Esforço:** M
- **Antes** (`:109-110`):
```java
JLabel lblDesc = new JLabel(casa.getFase().getNome(), SwingConstants.CENTER);
lblDesc.setFont(new Font("Arial", Font.ITALIC, 10));
```
- **Depois** (redundância não-cromática: sigla legível + ícone + tooltip; cor vira reforço):
```java
JLabel lblDesc = new JLabel(siglaDaFase(casa.getFase()), SwingConstants.CENTER); // "EXP","CON","TRA"...
lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));   // reto, >=14 (ver P0-6/TIP-02)
lblDesc.setIcon(iconeDaFase(casa.getFase()));         // ícone/padrão por fase
pnlCasa.setToolTipText(casa.getFase().getNome());     // nome completo no hover
```
Adicionalmente, atribuir a cada fase **luminância progressivamente distinta** (não só matiz), centralizada na classe de tema (ver P1-9), para que diferam também em escala de cinza.
- **Aceite:** simulando deuteranopia/escala de cinza sobre o print, é possível identificar a fase de cada casa pela **sigla/ícone**, sem depender da cor.

---

### P0-4 — Posição atual marcada só por borda vermelha (FALHA 1.4.11 e cor-única)
- **Perfil:** daltonismo vermelho-verde, visão monocromática, cegueira
- **Critério:** WCAG 1.4.1 + **1.4.11 (FALHA: borda vermelha 2.99–3.92:1, falha 3:1 contra o fundo Conector)** — *C1-03 / AT-05 (corrigidas pela verificação adversarial → Inadequado)*
- **Arquivo:** `view/TelaTabuleiro.java:160-166`
- **Esforço:** S
- **Antes:**
```java
if (i == j.getPosicao() - 1) {
    casasGraficas.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 4));
} else {
    casasGraficas.get(i).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
}
```
- **Depois** (marcador não-cromático: ícone/rótulo "VOCÊ" + borda mais grossa e de cor que passe 3:1 contra TODOS os pastéis):
```java
JPanel atual = casasGraficas.get(i);
// borda preta 5px: contraste >=10:1 contra qualquer pastel (passa 1.4.11)
atual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
JLabel aqui = new JLabel("\u25B6 VOCÊ", SwingConstants.CENTER); // seta + texto
aqui.setFont(new Font("Arial", Font.BOLD, 12));
atual.add(aqui, BorderLayout.NORTH);
atual.getAccessibleContext().setAccessibleDescription("Posição atual do jogador");
```
(remover o marcador da casa anterior ao reposicionar).
- **Aceite:** a casa atual é distinguível em escala de cinza (texto "VOCÊ" + borda preta 5px) e a borda passa **≥ 3:1 contra o pastel Conector**; NVDA anuncia "posição atual".

---

### P0-5 — Fonte base ~12px em todo o app (Main não sobrescreve defaults Nimbus)
- **Perfil:** baixa visão, dislexia, idosos
- **Critério:** Critério 2 — fonte base ≥ 16px (causa-raiz) — *TIP-01 (confirmada: "fonte herda 12px")*
- **Arquivo:** `Main.java:14-23`
- **Esforço:** S (efeito em massa)
- **Antes:**
```java
for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
    if ("Nimbus".equals(info.getName())) {
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        break;
    }
}
```
- **Depois:**
```java
for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
    if ("Nimbus".equals(info.getName())) {
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        break;
    }
}
java.awt.Font base = new java.awt.Font("Arial", java.awt.Font.PLAIN, 16);
javax.swing.UIManager.getLookAndFeelDefaults().put("defaultFont", base);
javax.swing.UIManager.put("Label.font", base);
javax.swing.UIManager.put("Button.font", base);
javax.swing.UIManager.put("TextField.font", base);
javax.swing.UIManager.put("PasswordField.font", base);
javax.swing.UIManager.put("TextArea.font", base);
javax.swing.UIManager.put("Table.font", base);
javax.swing.UIManager.put("TitledBorder.font", new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
```
(executado antes do `invokeLater`, que já existe). Resolve em massa **TIP-04, TIP-05, TIP-06, TIP-07, TIP-10, ACESS-06**.
- **Aceite:** todo componente sem `setFont` explícito renderiza ≥ 16px; rótulos/campos/botões de Login, Cadastro, status e opções do desafio passam a ≥ 16px.

---

### P0-6 — Descrição da casa em Arial ITALIC 10 (a fonte mais ilegível do sistema)
- **Perfil:** baixa visão, dislexia, daltonismo (é a alternativa à cor)
- **Critério:** Critério 2 — tamanho ≥ 16px + legibilidade (sem itálico) — *TIP-02 (confirmada: "ITALIC 10 L110")*
- **Arquivo:** `view/TelaTabuleiro.java:110`
- **Esforço:** S
- **Antes:** `lblDesc.setFont(new Font("Arial", Font.ITALIC, 10));`
- **Depois:** `lblDesc.setFont(new Font("Arial", Font.PLAIN, 14)); // reto, >=14; tooltip com nome completo`
- **Aceite:** o rótulo da fase é legível sem itálico (≥ 14px) e funciona como alternativa textual à cor (integra P0-3).

---

### P0-7 — Operável por teclado: botão default, foco inicial e mnemônicos ausentes
- **Perfil:** motor, visual (cegueira)
- **Critério:** WCAG 2.1.1 / 2.4.3 / Cheiran (operação por teclado) — *AT-03 / C3-01 / C3-02 / C3-03 / C3-07 (confirmadas)*
- **Arquivos:** `TelaLogin.java:44-68`, `TelaCadastro.java:29,42`, `TelaTabuleiro.java:72,80,86`, `TelaDesafio.java:39-45`
- **Esforço:** M
- **Antes** (`TelaLogin.java:44-54`):
```java
JButton btnEntrar = new JButton("Entrar");
btnEntrar.setBackground(new Color(50, 100, 200));
btnEntrar.setForeground(Color.WHITE);
// ...
add(pnlPrincipal);
```
- **Depois:**
```java
JButton btnEntrar = new JButton("Entrar");
btnEntrar.setMnemonic('E');                 // Alt+E
btnEntrar.setBackground(new Color(50, 100, 200));
btnEntrar.setForeground(Color.WHITE);
// ...
add(pnlPrincipal);
getRootPane().setDefaultButton(btnEntrar);  // Enter = Entrar
SwingUtilities.invokeLater(() -> txtEmail.requestFocusInWindow()); // foco inicial
```
Aplicar o padrão: `TelaCadastro` → `getRootPane().setDefaultButton(btnSalvar)` + `btnSalvar.setMnemonic('C')`; `TelaTabuleiro` → `btnDado.setMnemonic('L')`, `btnSair.setMnemonic('L'→'S')`, `btnRanking.setMnemonic('R')`, foco inicial em `btnDado`, e acelerador de SPACE/ENTER para lançar o dado:
```java
getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    .put(KeyStroke.getKeyStroke("SPACE"), "lancar");
getRootPane().getActionMap().put("lancar", new AbstractAction(){
    public void actionPerformed(ActionEvent e){ jogarDado(); }});
```
`TelaDesafio` → foco inicial na 1ª opção + teclas 1-4 via InputMap/ActionMap.
- **Aceite:** em cada tela é possível concluir a ação principal **sem mouse** (Enter no formulário, Espaço lança o dado, 1-4 respondem o desafio); ao abrir, há foco inicial coerente.

---

### P0-8 — Logout sem confirmação ao lado do botão primário (saída acidental); lógica duplicada
- **Perfil:** motor, cognitivo
- **Critério:** WCAG 3.3.4 (Prevenção de erros) / Cheiran (evitar ação destrutiva acidental) — *C3-05 / AT-07 (confirmadas) + "Logout duplicado L88-90 L140-142"*
- **Arquivo:** `view/TelaTabuleiro.java:33, 86-95, 140-142`
- **Esforço:** S
- **Antes** (`:86-95`):
```java
JButton btnSair = new JButton("Logout");
btnSair.addActionListener(e -> {
    controller.logout();
    new TelaLogin(controller).setVisible(true);
    dispose();
});
pnlAcoes.add(btnDado);
pnlAcoes.add(btnSair);
```
- **Depois** (confirmação + separação física + extração do fluxo duplicado em `:140-142`):
```java
JButton btnSair = new JButton("Logout");
btnSair.setMnemonic('S');
btnSair.addActionListener(e -> confirmarESair());
// btnDado centralizado; Logout em painel/BorderLayout.LINE_START separado
JPanel pnlEsq = new JPanel(); pnlEsq.add(btnSair);
add(pnlEsq, BorderLayout.SOUTH);   // longe do botão primário

// método único reaproveitado pelo fim de jornada (:140-142):
private void voltarAoLogin() {
    controller.logout();
    new TelaLogin(controller).setVisible(true);
    dispose();
}
private void confirmarESair() {
    int r = JOptionPane.showConfirmDialog(this,
        "Sair encerra a partida atual. Deseja continuar?",
        "Confirmar Logout", JOptionPane.YES_NO_OPTION);
    if (r == JOptionPane.YES_OPTION) voltarAoLogin();
}
```
Trocar também `setDefaultCloseOperation(EXIT_ON_CLOSE)` (`:33`) por `DO_NOTHING_ON_CLOSE` + `WindowListener` com a mesma confirmação.
- **Aceite:** clicar Logout ou fechar (X) exibe diálogo de confirmação; só sai no "Sim"; um clique impreciso não descarta a partida; código de saída em método único.

---

### P0-9 — Relatório final de acessibilidade não redigido (entregável central)
- **Perfil:** avaliador da atividade
- **Critério:** Atividade — relatório (Introdução / Avaliação por critérios / Problemas / Melhorias / Conclusão) — *ACESS-05 (confirmada)*
- **Arquivo-alvo:** criar `docs/RELATORIO_ACESSIBILIDADE.md` (ou PDF)
- **Esforço:** M
- **Ação:** redigir as 5 seções incorporando: metodologia adaptada ao desktop (Java Accessibility API / Access Bridge / NVDA no lugar de WebAIM/Stark), os contrastes já calculados, cor-como-único-meio (fases 1.02:1, posição 2.99–3.92:1), tipografia < 16px, ausência de zoom, citando NBR e heurísticas Cheiran/UFRGS, e referenciando este backlog como "Melhorias que podem ser implementadas".
- **Aceite:** documento com as 5 seções exigidas + tabela de classificação (P2-... abaixo) anexada.

---

## P1 — Importante

---

### P1-1 — Botão "LANÇAR DADO" no limite de contraste (3.79:1)
- **Perfil:** baixa visão, ambiente de baixo contraste
- **Critério:** WCAG 1.4.3 — *C1-01 (confirmada: "3.79:1 L80-83")*
- **Arquivo:** `view/TelaTabuleiro.java:81-83`
- **Esforço:** S
- **Antes:** `btnDado.setBackground(new Color(50, 150, 50)); btnDado.setForeground(Color.WHITE);`
- **Depois:** `btnDado.setBackground(new Color(0, 110, 40)); // verde escuro ~5:1 com branco`  (manter `setForeground(Color.WHITE)` e BOLD 18). Usar o azul de `btnEntrar` (5.54:1) como referência de paleta.
- **Aceite:** branco s/ o novo verde mede **≥ 4.5:1** na ferramenta sobre o print.

---

### P1-2 — TelaCadastro inteira ~12px e sem título/hierarquia
- **Perfil:** baixa visão, cognitivo
- **Critério:** Critério 2 (tamanho + hierarquia) — *TIP-03 (confirmada)*
- **Arquivo:** `view/TelaCadastro.java:23-38`
- **Esforço:** S (parcialmente coberto por P0-5)
- **Antes** (`:23`): `JPanel pnl = new JPanel(new GridLayout(8, 1, 5, 5));`
- **Depois** (título de tela + fonte ≥16 garantida localmente, caso P0-5 não esteja ativo):
```java
JLabel titulo = new JLabel("Criar Nova Conta", SwingConstants.CENTER);
titulo.setFont(new Font("Arial", Font.BOLD, 20));
add(titulo, BorderLayout.NORTH);
// pnl com campos em PLAIN 16
```
- **Aceite:** Cadastro tem título BOLD 20 destacado e corpo ≥ 16px, consistente com Login.

---

### P1-3 — TelaCadastro: GridLayout(8,1) sem vínculo rótulo↔campo
- **Perfil:** cognitivo, visual
- **Critério:** WCAG 1.3.1 / 3.3.2 — *C3-06 (confirmada)*
- **Arquivo:** `view/TelaCadastro.java:23,31-37`
- **Esforço:** M
- **Antes** (`:31-37`):
```java
pnl.add(new JLabel("Nome Completo:"));
pnl.add(txtNome);
// ...
pnl.add(new JLabel("")); // Espaço
```
- **Depois** (migrar para `GridBagLayout` como o Login, com `setLabelFor` e sem o JLabel-espaçador-gambiarra):
```java
JLabel lblNome = new JLabel("Nome Completo:");
lblNome.setDisplayedMnemonic('N');
lblNome.setLabelFor(txtNome);
// agrupar lblNome + txtNome na mesma linha; espaçamento via Insets/EmptyBorder
```
- **Aceite:** cada rótulo está associado ao campo (NVDA lê "Nome Completo, caixa de edição"); sem componente vazio de layout.

---

### P1-4 — Alvos de clique < 44px na maioria dos botões
- **Perfil:** motor, baixa visão
- **Critério:** WCAG 2.5.5 / NBR mobile / Cheiran — *C3-04 / AT-08 (confirmadas)*
- **Arquivos:** `TelaLogin.java:44,50`, `TelaCadastro.java:29`, `TelaTabuleiro.java:72,86`, `TelaDesafio.java:40`
- **Esforço:** S
- **Antes** (`TelaTabuleiro.java:86`): `JButton btnSair = new JButton("Logout");`
- **Depois:** `btnSair.setPreferredSize(new Dimension(140, 44));` — idem `btnRanking` (180×44), `btnEntrar`/`btnCadastrar`, `btnSalvar` e os 4 botões de opção do desafio (≥ 44px de altura), padronizando pelo `btnDado` (200×50).
- **Aceite:** todos os botões acionáveis têm altura ≥ 44px; alvos do desafio não ficam encostados (aumentar `vgap`).

---

### P1-5 — Sem feedback sonoro/redundante ao lançar o dado e ao acertar/errar
- **Perfil:** visual (cegueira/baixa visão), cognitivo
- **Critério:** WCAG 4.1.3 / 1.1.1 / Cheiran (feedback redundante) + diferencial "feedback sonoro" da spec — *AT-04 / C3-11 (confirmadas) + "Feedback só via JOptionPane"*
- **Arquivos:** `view/TelaTabuleiro.java:129-152`, `view/TelaDesafio.java:49-57`
- **Esforço:** M
- **Antes** (`TelaTabuleiro.java:130-131`):
```java
int valor = (int) (Math.random() * 6) + 1;
txtLog.append("> Dado: " + valor + "\n");
```
- **Depois** (som opcional + anúncio acessível, mantendo o texto — som nunca é o único meio):
```java
int valor = (int) (Math.random() * 6) + 1;
String msg = "Dado: " + valor;
txtLog.append("> " + msg + "\n");
Toolkit.getDefaultToolkit().beep();                          // reforço sonoro
lblStatusJogada.setText(msg);                                // JLabel visível dedicado
lblStatusJogada.getAccessibleContext().setAccessibleDescription(
    "Resultado do dado: " + valor);                          // NVDA anuncia
```
No `TelaDesafio.responder`, um `beep()` em acerto e outro padrão em erro, mantendo o `JOptionPane` textual.
- **Aceite:** ao lançar o dado/responder há **três canais** (visual destacado + texto + som); o resultado fica num JLabel com descrição acessível, não só no log.

---

### P1-6 — Fluxo de janelas: Cadastro abre como JFrame não-modal sobre o Login
- **Perfil:** cognitivo, visual
- **Critério:** Critério 3 (navegação) / WCAG 3.2.x — *C3-09 (confirmada)*
- **Arquivos:** `view/TelaCadastro.java:7,20`, `view/TelaLogin.java:67`
- **Esforço:** M
- **Antes:** `public class TelaCadastro extends JFrame {` … `btnCadastrar.addActionListener(e -> new TelaCadastro(controller).setVisible(true));`
- **Depois** (JDialog modal com o Login como dono, espelhando TelaDesafio):
```java
public class TelaCadastro extends JDialog {
    public TelaCadastro(Frame owner, JogoController controller) {
        super(owner, "Novo Jogador", true); // modal
    }
}
// TelaLogin: new TelaCadastro(this, controller).setVisible(true);
```
- **Aceite:** não é possível abrir múltiplas telas de cadastro; o foco fica preso ao cadastro até concluir/cancelar.

---

### P1-7 — Casa atual não rola para a área visível do JScrollPane
- **Perfil:** visual, cognitivo
- **Critério:** WCAG 2.4.3/2.4.7 — *C3-08 (confirmada)*
- **Arquivo:** `view/TelaTabuleiro.java:61,154-167`
- **Esforço:** S (integra-se ao P0-2/P0-4)
- **Depois** (em `atualizarInterface`, após identificar a casa atual):
```java
JComponent c = casasGraficas.get(j.getPosicao() - 1);
c.scrollRectToVisible(c.getBounds());
```
- **Aceite:** ao mover, a casa atual fica sempre visível mesmo com a janela reduzida.

---

### P1-8 — Sem zoom/ajuste de fonte e Login com setResizable(false)
- **Perfil:** baixa visão, idosos, motor
- **Critério:** WCAG 1.4.4 adaptado / Critério 2-3 — *TIP-09 / C3-10 / AC ESS-06 / AT-09 (confirmadas)*
- **Arquivos:** `view/TelaLogin.java:22`, `Main.java` (escala global)
- **Esforço:** M
- **Antes** (`TelaLogin.java:22`): `setResizable(false);`
- **Depois:** remover (ou `setMinimumSize(new Dimension(400, 350))`) e oferecer controle global A-/A+:
```java
static float escala = 1f;
static void aplicarEscala(JFrame janela, float fator){
    escala = fator;
    UIManager.put("Label.font", new Font("Arial", Font.PLAIN, Math.round(16*fator)));
    UIManager.put("Button.font", new Font("Arial", Font.PLAIN, Math.round(16*fator)));
    SwingUtilities.updateComponentTreeUI(janela);
}
```
ligado a botões/atalhos (Ctrl+Plus / Ctrl+Minus). Centralizar na classe de tema (P1-9).
- **Aceite:** usuário aumenta/diminui a fonte de toda a UI em runtime; a janela de login pode ser redimensionada.

---

### P1-9 — Centralizar tema + modo Alto Contraste
- **Perfil:** baixa visão severa, sensibilidade visual
- **Critério:** WCAG 1.4.6 / Cheiran (opções de acessibilidade) — *C1-04 (confirmada)*
- **Arquivos:** novo `util/Tema.java`; consumir em `Main.java`, `view/*` (cores hoje em `TelaLogin:45`, `TelaTabuleiro:39,82-83,116-121`, `TelaDesafio:31`)
- **Esforço:** L
- **Ação:** extrair literais `Color`/`Font` para uma classe `Tema` com dois esquemas (Normal / Alto Contraste — fundo (0,0,0), texto (255,255,255) ou (255,255,0), bordas fortes); botão/menu "Alto Contraste" reaplica `UIManager` + `updateComponentTreeUI`. Habilita também a luminância distinta por fase (P0-3).
- **Aceite:** alternar "Alto Contraste" troca todas as telas para o esquema escuro de alto contraste sem reiniciar.

---

### P1-10 — Mensagens de erro genéricas, sem identificar campo nem devolver foco
- **Perfil:** cognitivo, visual, motor
- **Critério:** WCAG 3.3.1 / 3.3.3 — *AT-06 (confirmada)*
- **Arquivos:** `view/TelaCadastro.java:47-57`, `view/TelaLogin.java:62-64`
- **Esforço:** S
- **Antes** (`TelaCadastro.java:47-49`):
```java
if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
    return;
}
```
- **Depois** (campo a campo + foco no problemático):
```java
if (nome.isEmpty())  { erro("Informe o nome completo.", txtNome);  return; }
if (email.isEmpty()) { erro("Informe o e-mail.", txtEmail);        return; }
if (senha.isEmpty()) { erro("Informe a senha.", txtSenha);         return; }
// ...
private void erro(String msg, JComponent campo){
    JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    campo.requestFocusInWindow();
}
```
No erro de e-mail já cadastrado (`:56`) e no login inválido (`:63`), focar `txtEmail`.
- **Aceite:** a mensagem diz **qual** campo falhou e o foco volta a ele.

---

### P1-11 — Testes unitários básicos ausentes (exigência da spec)
- **Perfil:** equipe / avaliador da spec
- **Critério:** spec docx — testes unitários básicos — *SPEC-01 (confirmada)*
- **Arquivos:** criar `test/com/maua/jogo/`; `lib/junit-4.13.2.jar` + `hamcrest-core`; classpath no `nbproject/`
- **Esforço:** M
- **Ação:** extrair interfaces dos DAOs e injetá-las no `JogoController` para testar sem MySQL; testes JUnit para `Fase.proximaFase()`/`getFasePorNumero` (incluindo limite REALIZADOR), `Tabuleiro/Casa` (posição→fase), `JogoController.moverJogador` (avanço e fim de jornada) e `responderDesafio` (acerto/erro e soma de pontos).
- **Aceite:** suíte JUnit verde cobrindo as regras de domínio, sem conexão real ao banco.

---

### P1-12 — Senha em texto puro no banco/DAO
- **Perfil:** segurança (fora do escopo estrito de acessibilidade, mas bloqueia entregável de qualidade)
- **Critério:** spec docx / boas práticas — *SPEC-04 + "Senha texto puro JogadorDAO L23 L60-68"*
- **Arquivo:** `util/JogadorDAO.java:23, 60-68`
- **Esforço:** M
- **Evidência:** `criarJogador` grava `jogador.getSenha()` cru (`:23`) e o login compara `WHERE email = ? AND senha = ?` com a senha em claro (`:60-68`).
- **Ação:** aplicar hash (ex.: BCrypt) na criação e comparar hash no login (buscar por e-mail e validar com `BCrypt.checkpw`). Migrar dados de exemplo.
- **Aceite:** nenhuma senha trafega/armazena em texto puro; login valida por hash.

---

## P2 — Melhoria / Bônus

---

### P2-1 — Hierarquia tipográfica consistente entre telas
- **Perfil:** cognitivo, baixa visão · **Critério:** Critério 2 (hierarquia) — *TIP-10*
- **Arquivos:** `view/TelaTabuleiro.java:37-50,70`; demais telas · **Esforço:** S
- **Ação:** escala H1 BOLD 20 / H2 BOLD 16 / corpo PLAIN 16; adicionar título de tela ao Tabuleiro; `UIManager.put("TitledBorder.font", new Font("Arial", Font.BOLD, 16))` (vem do P0-5).
- **Aceite:** todas as telas seguem a mesma escala; TitledBorders ≥ 16px.

---

### P2-2 — Entrelinha em log e pergunta (blocos densos)
- **Perfil:** baixa visão, dislexia · **Critério:** Critério 2 (espaçamento) — *TIP-08*
- **Arquivos:** `view/TelaTabuleiro.java:66-68`, `view/TelaDesafio.java:26` · **Esforço:** M
- **Antes:** `txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));`
- **Depois:** fonte ≥ 16 e, para entrelinha real, migrar para `JTextPane` + `StyleConstants.setLineSpacing(attr, 0.3f)`; alternativa simples: linha em branco entre entradas do log.
- **Aceite:** log e pergunta com respiro entre linhas e fonte ≥ 16px.

---

### P2-3 — JTable de ranking sem rótulo acessível
- **Perfil:** visual (cegueira), cognitivo · **Critério:** WCAG 1.3.1 / 4.1.2 — *AT-10*
- **Arquivo:** `view/TelaTabuleiro.java:169-184` · **Esforço:** S
- **Depois:** `table.getAccessibleContext().setAccessibleName("Ranking global de jogadores");` + foco inicial na tabela; opção de ranking em texto linear no log.
- **Aceite:** NVDA anuncia o nome da tabela ao abrir o ranking.

---

### P2-4 — Desafios com resposta sempre na opção A (todas as 30 = índice 0)
- **Perfil:** cognitivo (heurística de jogo) / integridade do jogo · **Critério:** Cheiran (clareza/justiça) — *"Respostas 100% opção A"*
- **Arquivo:** `database.sql:48-87` (todas as linhas `resposta_correta = 0`)
- **Esforço:** S
- **Evidência:** as 30 perguntas têm `resposta_correta` = 0 (opção A) e a correta é sempre a primeira opção do INSERT; um jogador descobre o padrão e "vence" sem ler.
- **Ação:** embaralhar a posição da alternativa correta entre A–D no seed (e/ou randomizar a ordem das opções em runtime ao montar `TelaDesafio:38-45`, ajustando o índice comparado em `responder`).
- **Aceite:** a alternativa correta varia entre A–D ao longo dos desafios; embaralhamento em runtime mantém o mapeamento correto.

---

## 3. Conformidade com a especificação (docx)

| Item | Status | Ação | Ref. |
|---|---|---|---|
| Linguagem Java + GUI Swing | Adequado | Manter | — |
| MySQL + script SQL | Adequado | Manter; aplicar hash de senha (P1-12) | SPEC-04 |
| POO/MVC + DAO | Adequado | Extrair interfaces dos DAOs p/ viabilizar testes | SPEC-05 |
| Requisitos funcionais (cadastro, login, progresso, movimentação, desafios, pontuação, finalização) | Adequado | Manter | — |
| **Testes unitários básicos** | **Inadequado** | Criar `test/`, JUnit, testes de domínio sem BD | **P1-11 / SPEC-01** |
| **UML** | **Inadequado** | Diagrama de classes (PlantUML em `docs/uml/` + PNG/PDF) e casos de uso | SPEC-02 |
| **Manual do usuário** | **Parcial** | `docs/MANUAL_DO_USUARIO.md`: objetivo, fluxo Cadastro→Login→Tabuleiro, lançar dado, desafios/pontuação, 6 fases, ranking, fim de jornada, com screenshots | SPEC-03 |
| Apresentação e demo | Parcial | Versionar slides + roteiro/vídeo (ou link em `docs/`) | SPEC-06 |
| **Senha hasheada** | **Inadequado** | BCrypt na criação e validação no login | P1-12 |

---

## 4. Entregáveis da atividade a produzir

### 4.1 Prints + medição de contraste (ACESS-01)
Capturar print de cada tela (Login 400×350, Cadastro 350×400, Tabuleiro 1200×800, Desafio 600×450) e, no WebAIM/Stark/WhoCanUse, medir as cores **reais do código** sobre o print:
- `btnEntrar` branco s/ (50,100,200) = 5.54:1 (Adequado);
- `btnDado` branco s/ (50,150,50) = 3.79:1 (Parcial) e, após P1-1, branco s/ (0,110,40) ≥ 4.5:1;
- textos pretos s/ pastéis = 15.7–20.6:1 (Adequado);
- borda vermelha de posição contra pastéis = 2.99–3.92:1 (Inadequado, falha 1.4.11 contra Conector).

### 4.2 Marcações no Figma (ACESS-02)
Importar os prints para um arquivo Figma e anotar visualmente: `btnDado` limítrofe, descrição italic 10px, fontes < 16px, fundos de fase indistinguíveis (1.02:1), e posição por borda vermelha única. Versionar o link em `docs/`.

### 4.3 Teste de uso com 1 colega (ACESS-03) — roteiro
**Tarefas:** (1) cadastrar conta; (2) fazer login; (3) lançar o dado; (4) responder um desafio; (5) abrir o ranking; (6) sair.
**O que observar:** consegue distinguir a fase de cada casa sem perguntar? Acha onde está no tabuleiro? Clica na opção certa sem errar o alvo? Percebe o resultado do dado? Tenta usar Enter/teclado?
**Registro:** anotar erros, tempo por tarefa e frases do participante; anexar foto/vídeo/anotações em `docs/`. **Critério 3 exige este teste.**

### 4.4 Tabela de classificação (ACESS-04)

| Tela | Critério 1 – Cores/Contraste | Critério 2 – Tipografia | Critério 3 – Layout/Interação |
|---|---|---|---|
| Login | **Adequado** (btnEntrar 5.54:1; labels 14.9:1) | **Parcial** (título BOLD 20 ok; corpo ~12px) | **Inadequado** (sem botão default/foco/mnemônico; resizable false) |
| Cadastro | Adequado (labels 14.9:1) | **Inadequado** (~12px, sem título) | **Parcial** (GridLayout sem labelFor; JFrame não-modal) |
| Tabuleiro | **Inadequado** (fases 1.02:1; posição 2.99–3.92:1 cor-única; btnDado 3.79:1 parcial) | **Inadequado** (desc italic 10; status 14) | **Inadequado** (logout sem confirmação; sem teclado/foco) |
| Desafio | Adequado (pergunta 19.3:1) | **Parcial** (pergunta 16 ok; opções 14) | **Parcial** (sem foco inicial/teclas 1-4; alvos < 44px) |

### 4.5 Relatório final (ACESS-05)
Documento com Introdução; Avaliação por critérios (Cores, Tipografia, Layout); Problemas identificados; Melhorias (este backlog); Conclusão — ver **P0-9**.

---

## 5. Sequenciamento sugerido (ordem de execução)

**Onda 0 — base de massa (1 dia):** P0-5 (fonte base) → P0-6 (italic da casa) → P0-1 (a11y nos formulários) → P0-7 (teclado/foco/default). *Resolve em massa TIP-04/05/06/07/10 e a operação por teclado.*

**Onda 1 — estado do jogo perceptível (1 dia):** P0-3 (fases redundantes) → P0-4 (posição não-cromática + contraste) → P0-2 (a11y do tabuleiro) → P1-7 (scroll) → P1-1 (verde do dado).

**Onda 2 — segurança de interação (meio dia):** P0-8 (logout/confirmação/dedup) → P1-10 (erros por campo) → P1-6 (cadastro modal) → P1-4 (alvos 44px).

**Onda 3 — adaptabilidade e feedback (1 dia):** P1-9 (tema + alto contraste) → P1-8 (zoom/resizable) → P1-5 (som + anúncio) → P2-1/P2-2/P2-3.

**Onda 4 — conformidade spec (1-2 dias):** P1-11 (testes) → P1-12 (hash) → P2-4 (respostas) → SPEC-02/03/06 (UML, manual, demo).

**Onda 5 — entregáveis da atividade (meio dia):** ACESS-01 (prints/medição, após P1-1) → ACESS-02 (Figma) → ACESS-03 (teste com colega) → ACESS-04 (tabela) → P0-9/ACESS-05 (relatório).

> Regra de pronto global: nenhum item fecha sem (a) compilar, (b) print "antes/depois", e (c) reteste do fluxo afetado por teclado **e** mouse.