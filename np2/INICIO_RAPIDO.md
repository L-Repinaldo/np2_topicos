# ⚡ Início Rápido - Sistema de Biblioteca NP2

## 🎯 Para Executar em 5 Minutos

### 🚀 Opção 1: Executar com Script Automático (RECOMENDADO)

```bash
cd np2
./run.sh
```

Este script irá:
- ✅ Verificar se o PostgreSQL está rodando
- ✅ Criar o banco de dados automaticamente
- ✅ Configurar variáveis de ambiente
- ✅ Verificar se a porta 8085 está livre
- ✅ Iniciar o projeto

**Com senha customizada:**
```bash
./run-with-password.sh sua_senha
```

---

### 1️⃣ Criar o Banco de Dados (se não usar o script)
```bash
psql -U postgres
CREATE DATABASE biblioteca_np2;
\q
```

### 2️⃣ Abrir no IntelliJ
1. Abrir IntelliJ IDEA
2. **File → Open** → Selecionar pasta `np2`
3. Aguardar indexação e download de dependências

### 3️⃣ Configurar Variáveis de Ambiente
1. Abrir **Run → Edit Configurations...**
2. Em **Environment variables** adicionar:
```
DB_URL=jdbc:postgresql://localhost:5432/biblioteca_np2;DB_USER=postgres;DB_Pass=SUA_SENHA;GOOGLE_BOOKS_KEY=
```
3. **Substituir `SUA_SENHA`** pela senha do PostgreSQL!

### 4️⃣ Habilitar Lombok
1. **File → Settings → Plugins**
2. Instalar plugin **"Lombok"**
3. **File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
4. Marcar: **"Enable annotation processing"**

### 5️⃣ Executar
1. Abrir arquivo `Np2Application.java`
2. Clicar no ▶️ verde
3. Aguardar mensagem: `Started Np2Application`

### 6️⃣ Acessar Swagger
Abrir navegador em:
```
http://localhost:8085/swagger-ui.html
```

## 🧪 Teste Rápido

No Swagger:
1. Expandir **"Usuários"**
2. **POST /api/v1/user** → Try it out
3. Colar:
```json
{
  "name": "Teste",
  "email": "teste@email.com",
  "login": "teste123",
  "password": "senha123"
}
```
4. Execute → Deve retornar status 200

## ⚠️ Problemas?

### Erro de conexão com banco:
- Verificar se PostgreSQL está rodando: `pg_isready`
- Verificar senha nas variáveis de ambiente

### Lombok não funciona:
- Instalar plugin
- Habilitar Annotation Processors
- **File → Invalidate Caches / Restart**

### Porta 8085 em uso:
- Mudar porta em `application.properties`:
```properties
server.port=8086
```

## 📖 Guia Completo
Ver arquivo `GUIA_EXECUCAO.md` para documentação detalhada.

---
**Equipe:** Antônio Augusto, Joéliton Oliveira, Lucas Repinaldo  
**Repositório:** https://github.com/L-Repinaldo/np2_topicos.git

