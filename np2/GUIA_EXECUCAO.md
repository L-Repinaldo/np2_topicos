# 🚀 Guia Completo de Execução - Sistema de Biblioteca NP2

## 📋 Índice
1. [Pré-requisitos](#pré-requisitos)
2. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
3. [Configuração do IntelliJ IDEA](#configuração-do-intellij-idea)
4. [Executando a Aplicação](#executando-a-aplicação)
5. [Acessando o Swagger](#acessando-o-swagger)
6. [Testando a API](#testando-a-api)
7. [Problemas Comuns](#problemas-comuns)

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ✅ **Java 17** ou superior
- ✅ **PostgreSQL** (versão 12 ou superior)
- ✅ **IntelliJ IDEA** (Community ou Ultimate)
- ✅ **Maven** (geralmente já vem com o IntelliJ)

### Verificar instalações:

```bash
# Verificar Java
java -version

# Verificar PostgreSQL
psql --version

# Verificar Maven
mvn -version
```

---

## 🗄️ Configuração do Banco de Dados

### Passo 1: Iniciar o PostgreSQL

**No macOS:**
```bash
# Verificar se está rodando
pg_isready

# Se não estiver, iniciar
brew services start postgresql@14
# ou
pg_ctl -D /usr/local/var/postgres start
```

**No Windows:**
- Abrir "Services"
- Procurar "PostgreSQL"
- Clicar em "Start"

**No Linux:**
```bash
sudo service postgresql start
# ou
sudo systemctl start postgresql
```

### Passo 2: Criar o Banco de Dados

Abra o terminal e execute:

```bash
# Conectar ao PostgreSQL
psql -U postgres

# Dentro do psql, criar o banco
CREATE DATABASE biblioteca_np2;

# Verificar se foi criado
\l

# Sair
\q
```

### Passo 3: Configurar Variáveis de Ambiente

**IMPORTANTE:** O projeto usa variáveis de ambiente para segurança.

#### Opção A: Configurar no IntelliJ (RECOMENDADO)

Vamos configurar direto no IntelliJ no próximo passo.

#### Opção B: Configurar no Sistema

**No macOS/Linux:**
Edite o arquivo `~/.zshrc` ou `~/.bashrc`:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/biblioteca_np2
export DB_USER=postgres
export DB_Pass=SUA_SENHA_AQUI
export GOOGLE_BOOKS_KEY=
```

Depois execute: `source ~/.zshrc`

**No Windows:**
1. Pesquisar "Variáveis de Ambiente"
2. Clicar em "Variáveis de Ambiente"
3. Adicionar as variáveis no "Variáveis do usuário"

---

## 💻 Configuração do IntelliJ IDEA

### Passo 1: Abrir o Projeto

1. Abrir o IntelliJ IDEA
2. Clicar em **"Open"** ou **"File → Open"**
3. Navegar até a pasta do projeto: `/Users/joelitonoliveira/Desktop/np2_topicos/np2`
4. Clicar em **"Open"**

### Passo 2: Aguardar Indexação

- O IntelliJ vai **indexar** e **baixar dependências do Maven**
- Aguarde até ver "Indexing completed" no canto inferior
- Isso pode levar alguns minutos na primeira vez

### Passo 3: Configurar o SDK do Java

1. Ir em **"File → Project Structure"** (ou pressionar `Cmd + ;` no Mac / `Ctrl + Alt + Shift + S` no Windows)
2. Em **"Project"**:
   - **SDK:** Selecionar Java 17 ou superior
   - **Language Level:** 17
3. Clicar em **"Apply"** e depois **"OK"**

### Passo 4: Configurar Variáveis de Ambiente no IntelliJ

1. Localizar o arquivo **`Np2Application.java`** em:
   ```
   src/main/java/com/biblioteca/np2/Np2Application.java
   ```

2. Clicar com botão direito no arquivo → **"Run 'Np2Application'"**

3. A aplicação vai tentar rodar e provavelmente **vai dar erro** (normal!)

4. Ir em **"Run → Edit Configurations..."**

5. Na configuração **"Np2Application"**, adicionar em **"Environment variables"**:
   ```
   DB_URL=jdbc:postgresql://localhost:5432/biblioteca_np2;DB_USER=postgres;DB_Pass=SUA_SENHA_AQUI;GOOGLE_BOOKS_KEY=
   ```
   
   **ATENÇÃO:** Substituir `SUA_SENHA_AQUI` pela senha do seu PostgreSQL!

6. Clicar em **"Apply"** e **"OK"**

### Passo 5: Habilitar Anotações do Lombok (IMPORTANTE!)

1. Ir em **"File → Settings"** (Windows/Linux) ou **"IntelliJ IDEA → Preferences"** (Mac)
2. Pesquisar por **"Annotation Processors"**
3. Marcar a opção: **"Enable annotation processing"**
4. Clicar em **"Apply"** e **"OK"**

### Passo 6: Instalar Plugin do Lombok (se necessário)

1. Ir em **"File → Settings → Plugins"**
2. Pesquisar por **"Lombok"**
3. Se não estiver instalado, clicar em **"Install"**
4. Reiniciar o IntelliJ

---

## ▶️ Executando a Aplicação

### Método 1: Pelo IntelliJ (RECOMENDADO)

1. Abrir o arquivo **`Np2Application.java`**
2. Clicar no ícone **▶️ (Play)** verde ao lado da classe `Np2Application`
3. Ou usar o atalho: **`Shift + F10`** (Windows/Linux) ou **`Ctrl + R`** (Mac)

### Método 2: Pelo Terminal Integrado

1. Abrir o terminal no IntelliJ: **"View → Tool Windows → Terminal"**
2. Executar:
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/biblioteca_np2
   export DB_USER=postgres
   export DB_Pass=SUA_SENHA_AQUI
   
   ./mvnw spring-boot:run
   ```

### ✅ Aplicação Iniciada com Sucesso!

Você verá no console:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

Started Np2Application in 8.234 seconds (process running for 8.567)
```

**Porta:** http://localhost:8085

---

## 📖 Acessando o Swagger

### Passo 1: Abrir o Navegador

Com a aplicação rodando, abra seu navegador favorito (Chrome, Firefox, Safari, Edge).

### Passo 2: Acessar a URL do Swagger

Digite na barra de endereços:
```
http://localhost:8085/swagger-ui.html
```

### Passo 3: Explorar a Documentação

Você verá a interface do Swagger com **6 seções principais**:

```
┌──────────────────────────────────────────────────┐
│  Sistema de Biblioteca - NP2           v2.0      │
├──────────────────────────────────────────────────┤
│                                                   │
│  📚 Livros                  (5 endpoints)        │
│  ✍️ Autores                 (5 endpoints)        │
│  📂 Categorias              (5 endpoints)        │
│  🏢 Editoras                (5 endpoints)        │
│  👥 Usuários ⭐             (5 endpoints)        │
│  🔍 Livros Google ⭐        (1 endpoint)         │
│                                                   │
└──────────────────────────────────────────────────┘
```

---

## 🧪 Testando a API

### Teste 1: Criar um Usuário

1. No Swagger, expandir **"Usuários"**
2. Clicar em **POST /api/v1/user**
3. Clicar em **"Try it out"**
4. Colar este JSON no campo de texto:

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "password": "senha123"
}
```

5. Clicar em **"Execute"**
6. Verificar a resposta (deve retornar status **200** com o usuário criado + ID)

### Teste 2: Listar Todos os Usuários

1. Expandir **"Usuários"**
2. Clicar em **GET /api/v1/user/all**
3. Clicar em **"Try it out"**
4. Clicar em **"Execute"**
5. Ver a lista de usuários criados

### Teste 3: Buscar Livros no Google Books

1. Expandir **"Livros Google"**
2. Clicar em **GET /api/v1/books/search**
3. Clicar em **"Try it out"**
4. Preencher:
   - **query:** `java`
   - **limit:** `5`
5. Clicar em **"Execute"**
6. Ver a lista de livros retornados da API do Google

### Teste 4: Criar um Livro na Biblioteca

1. Primeiro, criar um autor (se ainda não tiver):
   - Expandir **"Autores"**
   - POST /api/v1/autor
   - JSON:
   ```json
   {
     "nome": "Robert C. Martin"
   }
   ```

2. Criar o livro:
   - Expandir **"Livros"**
   - POST /api/v1/livro
   - JSON:
   ```json
   {
     "titulo": "Clean Code",
     "isbn": "9780132350884",
     "autor": "Robert C. Martin",
     "categoria": "Programação",
     "editora": "Prentice Hall"
   }
   ```

3. O sistema vai:
   - ✅ Buscar ou criar automaticamente o autor
   - ✅ Buscar ou criar automaticamente a categoria
   - ✅ Buscar ou criar automaticamente a editora
   - ✅ Criar o livro com todos os relacionamentos

### Teste 5: Usando Postman ou Insomnia (Alternativa)

Se preferir usar Postman ou Insomnia:

**Criar Usuário:**
```
POST http://localhost:8085/api/v1/user
Content-Type: application/json

{
  "name": "Maria Santos",
  "email": "maria@email.com",
  "login": "maria123",
  "password": "senha456"
}
```

**Buscar Livros:**
```
GET http://localhost:8085/api/v1/books/search?query=python&limit=5
```

---

## ⚠️ Problemas Comuns

### Problema 1: Erro "Could not connect to database"

**Sintoma:**
```
org.postgresql.util.PSQLException: Connection refused
```

**Solução:**
1. Verificar se o PostgreSQL está rodando:
   ```bash
   pg_isready
   ```
2. Verificar se o banco `biblioteca_np2` existe:
   ```bash
   psql -U postgres -l
   ```
3. Verificar as credenciais nas variáveis de ambiente

### Problema 2: Porta 8085 já está em uso

**Sintoma:**
```
Port 8085 is already in use
```

**Solução:**
1. Verificar o que está usando a porta:
   ```bash
   lsof -i :8085
   ```
2. Matar o processo:
   ```bash
   kill -9 PID
   ```
3. Ou mudar a porta no `application.properties`:
   ```properties
   server.port=8086
   ```

### Problema 3: Lombok não funciona

**Sintoma:**
```
Cannot resolve symbol 'getName'
Cannot resolve symbol 'setName'
```

**Solução:**
1. Instalar plugin do Lombok (ver Passo 6 da configuração)
2. Habilitar "Annotation Processors" (ver Passo 5)
3. Fazer "Invalidate Caches / Restart" no IntelliJ:
   - **File → Invalidate Caches / Restart**

### Problema 4: Dependências não baixam

**Sintoma:**
```
Cannot resolve symbol 'SpringBootApplication'
```

**Solução:**
1. Clicar com botão direito no `pom.xml`
2. **Maven → Reload Project**
3. Aguardar o download das dependências

### Problema 5: Google Books API não funciona

**Sintoma:**
```
Erro ao consultar Google Books API
```

**Solução:**
- A API funciona **sem chave** mas com limite de requisições
- Para uso mais intenso, obter chave em: https://developers.google.com/books/docs/v1/using
- Adicionar a chave na variável `GOOGLE_BOOKS_KEY`

### Problema 6: Aplicação inicia mas Swagger não abre

**Sintoma:**
```
Cannot GET /swagger-ui.html
```

**Solução:**
1. Tentar a URL alternativa:
   ```
   http://localhost:8085/swagger-ui/index.html
   ```
2. Verificar se a dependência do Swagger está no `pom.xml`
3. Limpar e recompilar:
   ```bash
   ./mvnw clean install
   ```

---

## 📊 Estrutura do Projeto

```
np2/
├── src/main/java/com/biblioteca/np2/
│   ├── client/
│   │   └── GoogleBooksClient.java       # Cliente HTTP para Google Books
│   ├── controller/
│   │   ├── AutorController.java         # API de Autores
│   │   ├── CategoriaController.java     # API de Categorias
│   │   ├── EditoraController.java       # API de Editoras
│   │   ├── LivroController.java         # API de Livros
│   │   ├── UserController.java          # API de Usuários ⭐
│   │   └── BookController.java          # API Google Books ⭐
│   ├── service/
│   │   ├── AutorService.java            # Lógica de Autores
│   │   ├── CategoriaService.java        # Lógica de Categorias
│   │   ├── EditoraService.java          # Lógica de Editoras
│   │   ├── LivroService.java            # Lógica de Livros
│   │   ├── UserService.java             # Lógica de Usuários ⭐
│   │   └── BookService.java             # Lógica Google Books ⭐
│   ├── repository/
│   │   ├── AutorRepository.java
│   │   ├── CategoriaRepository.java
│   │   ├── EditoraRepository.java
│   │   ├── LivroRepository.java
│   │   └── UserRepository.java          # ⭐ Novo
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Autor.java
│   │   │   ├── Categoria.java
│   │   │   ├── Editora.java
│   │   │   ├── Livro.java
│   │   │   ├── User.java                # ⭐ Novo
│   │   │   └── book/
│   │   │       └── BookDto.java         # ⭐ Novo
│   │   └── dto/
│   │       └── User/
│   │           ├── UserDto.java         # ⭐ Novo
│   │           └── UserLowDto.java      # ⭐ Novo
│   ├── excepiton/
│   │   ├── ApiException.java
│   │   └── GlobalExceptionHandler.java
│   └── Np2Application.java              # Classe principal
└── src/main/resources/
    └── application.properties            # Configurações
```

---

## 🎯 Funcionalidades Principais

### 1. Gerenciamento de Livros
- ✅ Criar livros com autor, categoria e editora
- ✅ Listar todos os livros
- ✅ Buscar livro por ID
- ✅ Atualizar informações
- ✅ Deletar livros
- ✅ **Find or Create automático** para relacionamentos

### 2. Gerenciamento de Usuários ⭐ NOVO
- ✅ CRUD completo de usuários
- ✅ Validação de login único
- ✅ Logs detalhados
- ✅ Tratamento de erros

### 3. Busca de Livros (Google Books) ⭐ NOVO
- ✅ Busca por termo
- ✅ Limite configurável (1-10 resultados)
- ✅ Informações completas (título, autores, ano, capa, link)
- ✅ Timeout de 10 segundos
- ✅ Tratamento robusto de erros

### 4. Documentação Swagger/OpenAPI ⭐ NOVO
- ✅ Interface interativa para testar APIs
- ✅ Todos os endpoints documentados
- ✅ Exemplos de request/response
- ✅ Descrições detalhadas

---

## 🔐 Segurança

**IMPORTANTE:** Este projeto usa variáveis de ambiente para credenciais sensíveis.

**NUNCA** commite senhas no código!

Sempre usar:
```properties
spring.datasource.password=${DB_Pass}
```

Ao invés de:
```properties
spring.datasource.password=minhasenha123  # ❌ NUNCA FAZER ISSO
```

---

## 📚 Recursos Adicionais

### Documentação Oficial
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Swagger/OpenAPI](https://springdoc.org/)
- [Google Books API](https://developers.google.com/books)

### Tutoriais Úteis
- [PostgreSQL Tutorial](https://www.postgresql.org/docs/)
- [IntelliJ IDEA Guide](https://www.jetbrains.com/idea/guide/)

---

## 🤝 Equipe do Projeto

- **Antônio Augusto** - Mat: 23.1.000495
- **Joéliton Oliveira** - Mat: 24.1.000522
- **Lucas Repinaldo** - Mat: 00.0.000000

**Repositório:** https://github.com/L-Repinaldo/np2_topicos.git

---

## 📞 Suporte

Se encontrar algum problema:

1. Verificar a seção [Problemas Comuns](#problemas-comuns)
2. Verificar os logs no console do IntelliJ
3. Consultar a documentação do Swagger
4. Entrar em contato com a equipe

---

## ✅ Checklist de Execução Rápida

- [ ] Java 17 instalado
- [ ] PostgreSQL instalado e rodando
- [ ] Banco `biblioteca_np2` criado
- [ ] Projeto aberto no IntelliJ
- [ ] Dependências baixadas (Maven)
- [ ] Plugin Lombok instalado
- [ ] Annotation Processors habilitado
- [ ] Variáveis de ambiente configuradas
- [ ] Aplicação rodando sem erros
- [ ] Swagger acessível em `http://localhost:8085/swagger-ui.html`
- [ ] Testes básicos funcionando

---

**Boa sorte com o projeto! 🚀**

