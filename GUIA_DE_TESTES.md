# Guia de Testes - Jogo de Tabuleiro Mauá

## Visão Geral

Este documento fornece um plano de testes para validar todas as funcionalidades do jogo.

## 1. Testes de Conexão com Banco de Dados

### TC-DB-001: Testar Conexão com MySQL
**Pré-condição:** MySQL rodando, banco criado

**Passos:**
1. Abra Main.java
2. Execute a aplicação
3. Observe a saída no console

**Resultado Esperado:**
```
Conexão com banco de dados estabelecida com sucesso!
```

**Critério de Aceite:** ✅ Mensagem aparece sem erros

---

## 2. Testes de Autenticação

### TC-AUTH-001: Cadastro de Novo Usuário
**Passos:**
1. Execute a aplicação
2. Clique em "Cadastrar-se"
3. Preencha:
   - Nome: "João Silva"
   - Email: "joao@email.com"
   - Senha: "senha123"
4. Clique "Cadastrar"

**Resultado Esperado:** ✅ Mensagem: "Cadastro realizado com sucesso!"

### TC-AUTH-002: Validação de Email Duplicado
**Passos:**
1. Tente cadastrar com email já existente
2. Insira: "teste@email.com"
3. Clique "Cadastrar"

**Resultado Esperado:** ❌ Mensagem: "Email já cadastrado!"

### TC-AUTH-003: Login com Credenciais Válidas
**Passos:**
1. Email: "teste@email.com"
2. Senha: "senha123"
3. Clique "Entrar"

**Resultado Esperado:** ✅ Acesso ao menu principal

### TC-AUTH-004: Login com Credenciais Inválidas
**Passos:**
1. Email: "invalido@email.com"
2. Senha: "errada123"
3. Clique "Entrar"

**Resultado Esperado:** ❌ Mensagem: "Email ou senha inválidos!"

### TC-AUTH-005: Validação de Campos Vazios
**Passos:**
1. Deixe os campos em branco
2. Clique "Entrar"

**Resultado Esperado:** ❌ Erro no login ou bloqueio de campo

---

## 3. Testes de Menu Principal

### TC-MENU-001: Visualizar Informações do Jogador
**Passos:**
1. Faça login
2. Observe a tela de menu

**Resultado Esperado:**
- ✅ Nome do jogador exibido
- ✅ Pontos exibidos
- ✅ Fase atual exibida

### TC-MENU-002: Iniciar Novo Jogo
**Passos:**
1. Clique "Iniciar Jogo"

**Resultado Esperado:**
- ✅ Abre TelaJogo
- ✅ Posição reinicia em 1
- ✅ Pontos reiniciam em 0

### TC-MENU-003: Continuar Partida
**Passos:**
1. Inicie um jogo, lance dados
2. Volte ao menu
3. Clique "Continuar Partida"

**Resultado Esperado:**
- ✅ Posição anterior mantida
- ✅ Pontos anteriores mantidos

### TC-MENU-004: Logout
**Passos:**
1. Clique "Sair"

**Resultado Esperado:**
- ✅ Volta para tela de login
- ✅ Sessão finalizada

---

## 4. Testes de Jogo

### TC-GAME-001: Lançar Dado
**Passos:**
1. Inicie um jogo
2. Clique "Lançar Dado" 5 vezes

**Resultado Esperado:**
- ✅ Número entre 1-6 aparece
- ✅ Posição avança
- ✅ Posição exibida aumenta

### TC-GAME-002: Progressão de Fases
**Passos:**
1. Lance dados para avançar 5+ casas
2. Observe a fase

**Resultado Esperado:**
- ✅ Fase muda de "Explorador" para "Conector"
- ✅ Fase exibida corretamente

### TC-GAME-003: Detecção de Fim de Jogo
**Passos:**
1. Avance até casa 30+
2. Lance dado que ultrapassaria posição 30

**Resultado Esperado:**
- ✅ Mensagem: "Parabéns! Você completou o tabuleiro!"
- ✅ Exibe pontuação final
- ✅ Volta ao menu

### TC-GAME-004: Voltar ao Menu
**Passos:**
1. Durante um jogo, clique "Voltar ao Menu"

**Resultado Esperado:**
- ✅ Partida finalizada
- ✅ Retorna ao menu
- ✅ Dados salvos no banco

---

## 5. Testes de Desafios

### TC-CHALLENGE-001: Desafio Aparece Corretamente
**Passos:**
1. Lance dados até encontrar um desafio
2. TelaDesafio deve aparecer

**Resultado Esperado:**
- ✅ Título do desafio exibido
- ✅ Pergunta exibida
- ✅ 4 opções exibidas (A, B, C, D)
- ✅ Pontos em jogo exibidos

### TC-CHALLENGE-002: Responder Corretamente
**Passos:**
1. Aguarde um desafio
2. Selecione a resposta correta
3. Clique "Confirmar Resposta"

**Resultado Esperado:**
- ✅ Mensagem: "Resposta correta!"
- ✅ Pontos adicionados
- ✅ Volta ao jogo

### TC-CHALLENGE-003: Responder Incorretamente
**Passos:**
1. Aguarde um desafio
2. Selecione uma resposta errada
3. Clique "Confirmar Resposta"

**Resultado Esperado:**
- ✅ Mensagem: "Resposta incorreta!"
- ✅ Mostra a resposta correta
- ✅ Nenhum ponto adicionado
- ✅ Volta ao jogo

### TC-CHALLENGE-004: Validação de Resposta Obrigatória
**Passos:**
1. Aguarde um desafio
2. NÃO selecione nenhuma opção
3. Clique "Confirmar Resposta"

**Resultado Esperado:**
- ❌ Mensagem: "Selecione uma resposta!"

### TC-CHALLENGE-005: Múltiplos Desafios
**Passos:**
1. Complete 5+ desafios em uma partida
2. Verifique respostas diferentes

**Resultado Esperado:**
- ✅ Desafios variam por fase
- ✅ Pontos acumulam corretamente

---

## 6. Testes de Pontuação

### TC-POINTS-001: Acumular Pontos
**Passos:**
1. Acerte 3 desafios valendo 10 pontos cada
2. Observe pontuação

**Resultado Esperado:**
- ✅ Pontos = 30
- ✅ Incremento visível em tempo real

### TC-POINTS-002: Pontos Não Decrementam
**Passos:**
1. Tenha 50 pontos
2. Erre um desafio

**Resultado Esperado:**
- ✅ Pontos continuam 50
- ✅ Sem penalidade

### TC-POINTS-003: Pontos por Fase
**Passos:**
1. Acerte desafio na Fase 1 (10 pts)
2. Avance para Fase 2
3. Acerte desafio (15 pts)

**Resultado Esperado:**
- ✅ Total = 25 pontos
- ✅ Valores corretos por fase

### TC-POINTS-004: Pontuação Salva no Banco
**Passos:**
1. Termine um jogo com 150 pontos
2. Volte ao menu
3. Inicie novo jogo
4. Verifique ranking

**Resultado Esperado:**
- ✅ Pontuação anterior aparece no ranking
- ✅ Múltiplas partidas registradas

---

## 7. Testes de Ranking

### TC-RANKING-001: Visualizar Ranking
**Passos:**
1. Clique "Ver Ranking"

**Resultado Esperado:**
- ✅ Tabela com jogadores exibida
- ✅ Colunas: Posição, Jogador, Pontuação, Partidas

### TC-RANKING-002: Ordenação Decrescente
**Passos:**
1. Verifique a tabela de ranking

**Resultado Esperado:**
- ✅ Jogador com mais pontos em 1º
- ✅ Ordenação correta

### TC-RANKING-003: Atualização do Ranking
**Passos:**
1. Jogue e consegua alta pontuação
2. Abra ranking novamente

**Resultado Esperado:**
- ✅ Sua pontuação aparece
- ✅ Ranking atualizado corretamente

---

## 8. Testes de Banco de Dados

### TC-DB-CRUD-001: CREATE - Novo Jogador
**Verificação no MySQL:**
```sql
SELECT * FROM jogadores WHERE email = 'teste@novo.com';
```

**Resultado Esperado:** ✅ Jogador aparece com dados corretos

### TC-DB-CRUD-002: READ - Obter Jogador
**Verificação:**
1. Faça login
2. Verifique dados no banco

**Resultado Esperado:** ✅ Dados correspondem ao banco

### TC-DB-CRUD-003: UPDATE - Atualizar Pontos
**Verificação no MySQL:**
```sql
SELECT pontos, posicao FROM jogadores WHERE id = 1;
```

**Após jogar:**
```sql
SELECT pontos, posicao FROM jogadores WHERE id = 1;
```

**Resultado Esperado:** ✅ Valores foram atualizados

### TC-DB-CRUD-004: Partida Salva
**Verificação no MySQL:**
```sql
SELECT * FROM partidas WHERE jogador_id = 1;
```

**Resultado Esperado:**
- ✅ Nova linha inserida
- ✅ data_inicio preenchida
- ✅ data_fim preenchida (se concluída)
- ✅ pontuacao_final correta

### TC-DB-CRUD-005: Resposta Registrada
**Verificação no MySQL:**
```sql
SELECT * FROM respostas_desafios WHERE partida_id = X;
```

**Resultado Esperado:**
- ✅ Todas as respostas registradas
- ✅ acertou = 1 ou 0 corretamente

---

## 9. Testes de Interface

### TC-UI-001: Responsividade
**Passos:**
1. Redimensione a janela

**Resultado Esperado:**
- ✅ Componentes se ajustam
- ✅ Nenhuma sobreposição

### TC-UI-002: Navegabilidade
**Passos:**
1. Teste todos os botões
2. Use Tab para navegar

**Resultado Esperado:**
- ✅ Todos os botões funcionam
- ✅ Navegação por teclado funciona

### TC-UI-003: Mensagens de Erro
**Passos:**
1. Tente ações inválidas
2. Observe mensagens

**Resultado Esperado:**
- ✅ Mensagens claras
- ✅ Sem erros de sintaxe

---

## 10. Testes de Integração

### TC-INT-001: Fluxo Completo de Jogo
**Passos:**
1. Execute aplicação
2. Cadastre novo usuário
3. Faça login
4. Inicie jogo
5. Lance dados 5 vezes
6. Responda 2 desafios
7. Volte ao menu
8. Veja ranking

**Resultado Esperado:** ✅ Tudo funciona sem erros

### TC-INT-002: Múltiplas Partidas
**Passos:**
1. Jogue 3 partidas diferentes
2. Verifique histórico

**Resultado Esperado:**
- ✅ Todas as partidas registradas
- ✅ Histórico correto no banco

### TC-INT-003: Concorrência
**Passos:**
1. Abra dois clientes (2 Java executando)
2. Cadastre em ambos
3. Joguem simultaneamente

**Resultado Esperado:**
- ✅ Ambos funcionam sem conflito
- ✅ Dados salvos corretamente

---

## 11. Relatório de Testes

### Template para Preenchimento

```
Data: __/__/____
Testador: ________________

┌─────────────────────────────────────┐
│ Resultado: PASSOU / FALHOU          │
├─────────────────────────────────────┤
│ Testes Passados: __ / __            │
│ Testes Falhados: __ / __            │
│ Taxa de Sucesso: __% 		      │
└─────────────────────────────────────┘

Problemas Encontrados:
[ ] Nenhum
[ ] Bugs menores
[ ] Bugs maiores
[ ] Críticos

Observações:
_________________________________
_________________________________
_________________________________
```

---

## 12. Checklist Final

- [ ] Todas as telas abrem corretamente
- [ ] Banco de dados funciona
- [ ] Cadastro e login funcionam
- [ ] Jogo avança corretamente
- [ ] Desafios aparecem
- [ ] Pontuação é correta
- [ ] Ranking atualiza
- [ ] Dados salvam no banco
- [ ] Sem erros no console
- [ ] Interface é usável
- [ ] Aplicação é estável
- [ ] Performance é aceitável

---

**Projeto pronto para produção quando todos os testes passarem! ✅**
