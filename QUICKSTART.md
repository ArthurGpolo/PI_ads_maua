# QUICKSTART - Iniciar Rápido

## ⚡ 5 Minutos para Começar

### Pré-requisitos Mínimos
- ✅ Java JDK 8+
- ✅ MySQL rodando
- ✅ NetBeans IDE

---

## Passo 1: Criar o Banco (1 min)

### Windows - CMD
```cmd
mysql -u root -p
```

```sql
source "C:\Users\SEU_USUARIO\Documents\PI_ads_maua\db\database.sql"
```

### macOS/Linux - Terminal
```bash
mysql -u root -p < ~/Documents/PI_ads_maua/db/database.sql
```

---

## Passo 2: Abrir no NetBeans (1 min)

1. File > Open Project
2. Navegue até: `PI_ads_maua`
3. Clique "Open Project"

---

## Passo 3: Configurar Credenciais (1 min)

Abra: `src/com/maua/jogo/util/ConexaoBD.java`

**Linha 9-12:**
```java
private static final String USUARIO = "root";  // Seu usuário MySQL
private static final String SENHA = "root";    // Sua senha MySQL
```

---

## Passo 4: Compilar (1 min)

1. Run > Clean and Build Project
2. Aguarde "BUILD SUCCESSFUL"

---

## Passo 5: Executar! (1 min)

1. Right-click em `Main.java`
2. Clique em "Run File"
3. Enjoy! 🎮

---

## 🎮 Jogar em 60 Segundos

### 1. Cadastre-se
- Email: seu@email.com
- Senha: 123456

### 2. Faça Login

### 3. Clique "Iniciar Jogo"

### 4. "Lançar Dado"

### 5. Responda os desafios

### 6. Veja seu ranking!

---

## ✅ Dados de Teste (Já Cadastrados)

```
Email: teste@email.com
Senha: senha123
```

---

## 🆘 Erro? Tente Isto

### "ClassNotFoundException"
```
Solução: Adicione mysql-connector-java.jar ao classpath
Projeto > Properties > Libraries > Add JAR
```

### "Access Denied"
```
Solução: Altere USUARIO/SENHA em ConexaoBD.java
```

### "Unknown Database"
```
Solução: Execute novamente o database.sql
```

---

## 📁 Arquivos Importantes

| Arquivo | Função |
|---------|--------|
| Main.java | Iniciar app |
| ConexaoBD.java | Conectar BD |
| TelaLogin.java | Login/Cadastro |
| JogoController.java | Lógica do jogo |

---

## 💡 Dicas

- Use dados de teste: `teste@email.com / senha123`
- Lance dados múltiplas vezes para pegar desafios
- Respostas corretas: Fase 1 = resposta A
- Máximo de pontos: Complete todas as 6 fases

---

## 📖 Documentação Completa

- **README.md** - Visão geral
- **SETUP_GUIDE.md** - Instalação detalhada
- **MANUAL_DO_USUARIO.md** - Como jogar
- **ARQUITETURA.md** - Técnico

---

## 🎯 Próximas Ações

1. ✅ Execute `Main.java`
2. ✅ Crie uma conta
3. ✅ Jogue uma partida
4. ✅ Veja seu ranking

---

**Pronto? Clique "Run" e divirta-se! 🚀**
