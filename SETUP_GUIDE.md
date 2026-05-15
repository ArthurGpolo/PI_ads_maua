# Guia de Configuração - Jogo de Tabuleiro Mauá

## Pré-requisitos

Certifique-se de ter instalado:

1. **Java Development Kit (JDK)**
   - Versão 8 ou superior
   - Baixar: https://www.oracle.com/java/technologies/javase-downloads.html
   - Verificar instalação: `java -version`

2. **MySQL Server**
   - Versão 5.7 ou superior
   - Baixar: https://www.mysql.com/downloads/mysql/
   - Certifique-se de que o serviço está rodando

3. **NetBeans IDE** (Recomendado)
   - Baixar: https://netbeans.apache.org/
   - Ou use outra IDE Java de sua preferência

## Passo 1: Criar o Banco de Dados

### Via MySQL Workbench

1. Abra MySQL Workbench
2. Conecte ao servidor local
3. Vá para File > Open SQL Script
4. Selecione `db/database.sql`
5. Clique em Execute (⚡)

### Via Linha de Comando

```bash
# Abrir MySQL
mysql -u root -p

# Digitar a senha quando solicitado
# Cole todo o conteúdo de db/database.sql
# Ou execute:
source C:\Users\seu_usuario\Documents\PI_ads_maua\db\database.sql;

# Verificar se o banco foi criado
SHOW DATABASES;
USE jogo_maua;
SHOW TABLES;
```

## Passo 2: Configurar Credenciais do Banco

Edite o arquivo `src/com/maua/jogo/util/ConexaoBD.java`:

```java
// Linha 9-12
private static final String URL = "jdbc:mysql://localhost:3306/jogo_maua";
private static final String USUARIO = "root";    // Mude se necessário
private static final String SENHA = "root";      // Mude se necessário
```

**Exemplos:**
- Se sua senha MySQL é vazia: `private static final String SENHA = "";`
- Se usar usuário diferente: `private static final String USUARIO = "seu_usuario";`

## Passo 3: Configurar o NetBeans

1. **Abrir o Projeto**
   - File > Open Project
   - Selecione a pasta `PI_ads_maua`

2. **Adicionar Driver MySQL** (se necessário)
   - Tools > Libraries
   - New Library > "MySQL"
   - Add JAR/Folder
   - Selecione `mysql-connector-java-*.jar`

3. **Compilar Projeto**
   - Run > Clean and Build Project

4. **Executar**
   - Right-click em `Main.java` > Run File
   - Ou pressione F6

## Passo 4: Primeira Execução

Na primeira execução, você verá:

```
Conexão com banco de dados estabelecida com sucesso!
```

Se não aparecer essa mensagem, verifique:

- ✅ MySQL está rodando?
- ✅ Banco `jogo_maua` foi criado?
- ✅ Credenciais estão corretas em `ConexaoBD.java`?

## Dados de Teste

Um jogador de teste foi criado:
- **Email:** teste@email.com
- **Senha:** senha123

## Possíveis Problemas e Soluções

### Problema: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Solução:**
1. Adicione o driver MySQL ao classpath do projeto
2. Em NetBeans: Right-click Projeto > Properties > Libraries
3. Clique em "Add JAR"
4. Selecione `mysql-connector-java-*.jar`

### Problema: "Access denied for user 'root'@'localhost'"

**Solução:**
1. Verifique se a senha em `ConexaoBD.java` está correta
2. Teste a conexão manual: `mysql -u root -p` no terminal

### Problema: "Unknown database 'jogo_maua'"

**Solução:**
1. Execute o script SQL novamente
2. Verifique se não há erros na criação
3. Confirme com: `USE jogo_maua; SHOW TABLES;`

### Problema: "Can't connect to MySQL server on 'localhost:3306'"

**Solução:**
1. Verifique se MySQL está rodando (Services ou terminal)
2. Windows: Abra "Services" e procure por "MySQL"
3. macOS: `brew services list` para verificar MySQL
4. Linux: `sudo systemctl status mysql`

### Problema: A tela não aparece

**Solução:**
1. Verifique o console para mensagens de erro
2. Certifique-se de que o JDK está instalado corretamente
3. Tente em outra IDE Java

## Compilação Manual (Sem IDE)

Se preferir compilar sem usar a IDE:

```bash
# Navegue até a pasta do projeto
cd C:\Users\seu_usuario\Documents\PI_ads_maua

# Compile todos os arquivos
javac -d bin -cp . src/com/maua/jogo/**/*.java

# Execute
java -cp bin com.maua.jogo.Main
```

## Estrutura de Pastas Esperada

```
PI_ads_maua/
├── src/
│   └── com/maua/jogo/
│       ├── Main.java
│       ├── model/
│       ├── view/
│       ├── controller/
│       └── util/
├── db/
│   └── database.sql
└── README.md
```

## Próximos Passos

1. ✅ Configuração completa
2. ✅ Execute `Main.java`
3. ✅ Crie uma conta
4. ✅ Comece a jogar!

## Suporte

Para problemas adicionais:
1. Verifique o console de erros (F8 no NetBeans)
2. Consulte a documentação do MySQL
3. Revise o README.md

**Bom jogo! 🎮**
