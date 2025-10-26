# 📚 Sistema de Biblioteca - NP2

> API REST completa para gerenciamento de biblioteca desenvolvida com Spring Boot

## 🚀 Quick Start

```bash
# 1. Criar banco
psql -U postgres -c "CREATE DATABASE biblioteca_np2;"

# 2. Executar
cd np2
./mvnw spring-boot:run

# 3. Acessar Swagger
open http://localhost:8085/swagger-ui.html
```

📖 **[Ver Guia de Execução Completo](GUIA_EXECUCAO.md)**  
⚡ **[Ver Início Rápido](INICIO_RAPIDO.md)**

---

## ✨ Funcionalidades

### 📚 Gerenciamento de Biblioteca
- ✅ CRUD de Livros, Autores, Categorias e Editoras
- ✅ Relacionamentos automáticos (Find or Create)
- ✅ Validações robustas
- ✅ ISBN único

### 👥 Gerenciamento de Usuários
- ✅ CRUD completo de usuários
- ✅ Login único
- ✅ Validação de dados

### 🔍 Busca de Livros (Google Books API)
- ✅ Integração com Google Books
- ✅ Busca por termo
- ✅ Informações completas (capa, autores, ano)

### 📖 Documentação Swagger/OpenAPI
- ✅ Interface interativa
- ✅ Testes em tempo real
- ✅ Exemplos de uso

---

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.5.6**
- **PostgreSQL**
- **Spring Data JPA**
- **Lombok**
- **ModelMapper**
- **Swagger/OpenAPI**
- **WebClient (WebFlux)**

---

## 📊 Estrutura do Projeto

```
np2/
├── src/main/java/com/biblioteca/np2/
│   ├── controller/      # REST Controllers (6 controllers)
│   ├── service/         # Lógica de negócio (6 services)
│   ├── repository/      # Acesso a dados (5 repositories)
│   ├── domain/
│   │   ├── model/       # Entidades JPA (5 entidades)
│   │   └── dto/         # Data Transfer Objects
│   ├── client/          # Clientes HTTP externos
│   ├── excepiton/       # Tratamento global de erros
│   └── util/            # Utilitários
└── src/main/resources/
    └── application.properties
```

---

## 🔌 Endpoints Principais

### Livros
```http
POST   /api/v1/livro
GET    /api/v1/livro/all
GET    /api/v1/livro/{id}
PUT    /api/v1/livro
DELETE /api/v1/livro/{id}
```

### Usuários
```http
POST   /api/v1/user
GET    /api/v1/user/all
GET    /api/v1/user/{id}
PUT    /api/v1/user
DELETE /api/v1/user/{id}
```

### Busca Google Books
```http
GET /api/v1/books/search?query=java&limit=5
```

**[Ver documentação completa no Swagger](http://localhost:8085/swagger-ui.html)**

---

## 🧪 Exemplos de Uso

### Criar Usuário
```json
POST /api/v1/user
{
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "password": "senha123"
}
```

### Criar Livro
```json
POST /api/v1/livro
{
  "titulo": "Clean Code",
  "isbn": "9780132350884",
  "autor": "Robert C. Martin",
  "categoria": "Programação",
  "editora": "Prentice Hall"
}
```
*Autor, Categoria e Editora são criados automaticamente se não existirem!*

### Buscar Livros
```http
GET /api/v1/books/search?query=python&limit=5
```

---

## ⚙️ Configuração

### Variáveis de Ambiente

```bash
DB_URL=jdbc:postgresql://localhost:5432/biblioteca_np2
DB_USER=postgres
DB_Pass=sua_senha
GOOGLE_BOOKS_KEY=  # Opcional
```

### application.properties

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_Pass}

server.port=8085
```

---

## 🎓 Projeto Acadêmico

**Disciplina:** Tópicos Web  
**Instituição:** UniChristus 2025.2  
**Branch:** `feature/melhorias`

### 👥 Equipe
- **Antônio Augusto** - Mat: 23.1.000495
- **Joéliton Oliveira** - Mat: 24.1.000522
- **Lucas Repinaldo** - Mat: 00.0.000000

---

## 📝 Documentação

- 📖 **[Guia Completo de Execução](GUIA_EXECUCAO.md)** - Tutorial detalhado
- ⚡ **[Início Rápido](INICIO_RAPIDO.md)** - Setup em 5 minutos
- 🌐 **[Swagger UI](http://localhost:8085/swagger-ui.html)** - Documentação interativa

---

## 🐛 Problemas Comuns

| Problema | Solução |
|----------|---------|
| Erro de conexão DB | Verificar PostgreSQL rodando: `pg_isready` |
| Porta 8085 em uso | Alterar `server.port` no application.properties |
| Lombok não funciona | Instalar plugin + habilitar Annotation Processors |

**[Ver mais problemas e soluções](GUIA_EXECUCAO.md#problemas-comuns)**

---

## 📊 Estatísticas do Projeto

- **26 endpoints** REST
- **6 controllers** documentados
- **5 entidades** JPA
- **11 DTOs** para transferência de dados
- **100% documentado** no Swagger

---

## 🔗 Links Úteis

- 🌐 [Repositório GitHub](https://github.com/L-Repinaldo/np2_topicos.git)
- 📖 [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- 📚 [Google Books API](https://developers.google.com/books)

---

## 📄 Licença

Projeto acadêmico - UniChristus 2025.2

---

**Desenvolvido com ❤️ pela equipe NP2**

