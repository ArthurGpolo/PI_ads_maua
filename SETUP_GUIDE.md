# PI_ads_maua - Sistema de Jogo Tabuleiro

Um sistema interativo de jogo de tabuleiro com perguntas e desafios, com gerenciamento de usuários e pontuação.

## 📋 Requisitos

- Python 3.7+
- MySQL 5.7+ ou MariaDB
- pip (gerenciador de pacotes Python)

## 🚀 Instalação e Configuração

### 1. Instalar dependências Python
```bash
pip install -r requirements.txt
```

### 2. Configurar o Banco de Dados

Edite o arquivo `jogo/config.py` e altere as credenciais do MySQL:
```python
DB_CONFIG = {
    'host': 'localhost',
    'user': 'seu_usuario',      # Mude aqui
    'password': 'sua_senha',    # Mude aqui
    'database': 'jogo_tabuleiro',
    'port': 3306
}
```

### 3. Criar o Banco de Dados
Execute o script de setup:
```bash
python setup_db.py
```

### 4. Inserir Dados Iniciais
Execute o script de seed:
```bash
python seed_db.py
```

## 🎮 Como Executar

```bash
cd jogo
python main.py
```

## 📁 Estrutura do Projeto

```
PI_ads_maua/
├── README.md
├── requirements.txt
├── setup_db.py          # Script para criar banco de dados
├── seed_db.py           # Script para inserir dados iniciais
├── db/
│   └── db.sql          # Schema do banco de dados
└── jogo/
    ├── config.py       # Configurações do banco
    ├── banco.py        # Classe de conexão com banco
    ├── jogador.py      # Classe do jogador
    ├── partida.py      # Classe de partida
    ├── tabuleiro.py    # Classe do tabuleiro
    ├── desafio.py      # Classe de desafios
    └── main.py         # Ponto de entrada do jogo
```

## 🗄️ Banco de Dados

O sistema utiliza MySQL com as seguintes tabelas principais:

- **usuarios**: Registro de jogadores
- **fases**: Níveis do jogo
- **partidas**: Histórico de partidas
- **perguntas**: Banco de perguntas
- **respostas**: Respostas dos jogadores
- **pontuacoes**: Pontuação das partidas
- **ranking**: View com ranking dos jogadores

## 🐛 Troubleshooting

**Erro: "Erro ao conectar ao MySQL"**
- Verifique se o MySQL está rodando
- Confira as credenciais em `jogo/config.py`

**Erro: "Banco de dados não existe"**
- Execute `python setup_db.py` primeiro

**Erro: "Não há dados"**
- Execute `python seed_db.py` para inserir dados iniciais

## 👤 Autor

Projeto Integrador - Disciplina de ADS - MAUA
