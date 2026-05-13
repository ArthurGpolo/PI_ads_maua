# 🎮 Quick Start - Sistema de Jogo Tabuleiro

## ⚡ Passos Rápidos

### 1️⃣ Instalar dependências
```bash
pip install -r requirements.txt
```

### 2️⃣ Configurar o banco (SÓ NA PRIMEIRA VEZ)
```bash
python setup_db.py    # Cria banco e tabelas
python seed_db.py     # Insere dados iniciais
```

### 3️⃣ Executar o jogo
```bash
cd jogo
python main.py
```

---

## 📋 Menu do Jogo

1. **Cadastrar novo jogador** - Cria um novo usuário
2. **Iniciar nova partida** - Começa uma partida (rola dados, responde perguntas)
3. **Ver ranking** - Mostra pontuação dos jogadores
4. **Ver histórico** - Lista todos os jogadores
5. **Sair** - Encerra o programa

---

## 🎯 Como Jogar Uma Partida

1. Digite seu e-mail para acessar a conta
2. Pressione ENTER para rodar o dado e avançar no tabuleiro
3. Responda a pergunta da fase correspondente
4. Ganhe pontos por acertos (+ pontos em casas especiais)
5. Chegue na casa 30 para vencer!

---

## 🗄️ Estrutura do Banco

| Tabela | Descrição |
|--------|-----------|
| **usuarios** | Jogadores cadastrados |
| **fases** | Níveis do jogo (Geografia, História, Matemática) |
| **perguntas** | Banco de perguntas por fase |
| **partidas** | Histórico de partidas |
| **respostas** | Respostas dadas pelos jogadores |
| **pontuacoes** | Pontuação final de cada partida |

---

## ❌ Solução de Problemas

**"Erro ao conectar ao MySQL"**
- Verifique se MySQL está rodando
- Confira a senha em `jogo/config.py`

**"Banco de dados não existe"**
- Execute `python setup_db.py` primeiro

**"Nenhuma pergunta disponível"**
- Execute `python seed_db.py` para inserir perguntas

---

## 🔒 Segurança

As senhas são armazenadas usando **SHA256** (hash, não reversível).

---

**Pronto para jogar! 🎲**
