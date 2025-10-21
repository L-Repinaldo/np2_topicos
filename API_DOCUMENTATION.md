# 📚 Documentação Completa da API - Spring Boot Backend

## 🎯 Visão Geral

Sistema Spring Boot com duas funcionalidades principais:
- **Gerenciamento de Usuários** - CRUD completo de usuários
- **Busca de Livros** - Integração com Google Books API

---

## 🏗️ Arquitetura

### Estrutura em Camadas
```
Controller → Service → Repository/Client → Database/External API
```

### Padrão de Documentação Swagger/OpenAPI
Todos os endpoints seguem o padrão:
- **@Operation** - Resumo e tags
- **@ApiResponse** - Códigos de resposta e descrições
- **@Parameter** - Descrição de parâmetros
- **@Tag** - Categorização no Swagger UI

---

## 👥 API de Usuários (`/api/v1/user`)

### **PUT** `/api/v1/user` - Atualizar Usuário
```java
@Operation(summary = "atualiza os dados de um usuario existente.", tags = "User")
@ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso!")
@ApiResponse(responseCode = "400", description = "Dados invalidos fornecidos")
```

**Request Body:**
```json
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joao123",
  "password": "novaSenha123"
}
```

**Response 200:**
```json
{
  "id": 1,
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "login": "joao123",
  "password": "novaSenha123"
}
```

---

### **POST** `/api/v1/user` - Cadastrar Usuário
```java
@Operation(summary = "cadastra dados referente a usuarios.", tags = "User")
@ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!")
@ApiResponse(responseCode = "400", description = "-possiveis causas" + "O login informado ja existe")
```

**Request Body:**
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "password": "senha123"
}
```

**Response 200:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joao123",
  "password": "senha123"
}
```

**Response 400:**
```json
{
  "message": "O NOME DO USUARIO É OBRIGATORIO.",
  "key": "UNICHRISTUS.SERVICE.USER.BADREQUEST"
}
```

---

### **GET** `/api/v1/user/{id}` - Buscar Usuário por ID
```java
@Operation(summary = "retornar os dados de um usuario com base no ID.", tags = "User")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Retorno dos dados do usuario"),
    @ApiResponse(responseCode = "404", description = "O usuario com o ID informado nao foi encontrado", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
})
```

**Path Parameter:**
- `id` (Long) - ID do usuário

**Response 200:**
```json
{
  "id": 1,
  "name": "João Silva"
}
```

**Response 404:**
```json
{
  "message": "O USUARIO COM O ID INFORMADO NAO FOI ENCONTRADO.",
  "key": "UNICHRISTUS.SERVICE.USER.BADREQUEST"
}
```

---

### **DELETE** `/api/v1/user/{id}` - Remover Usuário
```java
@Operation(summary = "remove um usuario do sistema com base no ID.", tags = "User")
@ApiResponse(responseCode = "200", description = "Usuario removido com sucesso!")
@ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
```

**Path Parameter:**
- `id` (Long) - ID do usuário a ser removido

---

### **GET** `/api/v1/user/all` - Listar Todos os Usuários
```java
@Operation(summary = "retorna todos os usuarios cadastrados no sistema.", tags = "User")
@ApiResponse(responseCode = "200", description = "Lista de usuarios retornada com sucesso!")
```

**Response 200:**
```json
[
  {
    "id": 1,
    "name": "João Silva"
  },
  {
    "id": 2,
    "name": "Maria Santos"
  }
]
```

---

## 📚 API de Livros (`/api/v1/books`)

### **GET** `/api/v1/books/search` - Buscar Livros
```java
@Operation(summary = "busca livros usando a Google Books API com base em um termo de pesquisa.", tags = "Books")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de livros encontrados com sucesso!"),
    @ApiResponse(responseCode = "400", description = "Termo de busca invalido ou vazio", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno ao consultar Google Books API", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
})
```

**Query Parameters:**
- `query` (String, obrigatório) - Termo de busca para encontrar livros
- `limit` (Integer, opcional, padrão: 5) - Número máximo de resultados (1-10)

**Exemplo de Request:**
```
GET /api/v1/books/search?query=musculacao&limit=3
```

**Response 200:**
```json
[
  {
    "title": "Técnicas de Musculação",
    "authors": ["João Silva", "Maria Santos"],
    "publishedYear": 2020,
    "infoLink": "https://books.google.com/books?id=123",
    "thumbnail": "https://books.google.com/books/content?id=123&printsec=frontcover&img=1"
  },
  {
    "title": "Musculação para Iniciantes",
    "authors": ["Carlos Oliveira"],
    "publishedYear": 2019,
    "infoLink": "https://books.google.com/books?id=456",
    "thumbnail": "https://books.google.com/books/content?id=456&printsec=frontcover&img=1"
  }
]
```

**Response 400:**
```json
{
  "message": "termo de busca não pode estar vazio",
  "key": "BOOKS.SERVICE.INVALID_QUERY"
}
```

**Response 500:**
```json
{
  "message": "erro ao consultar Google Books API",
  "key": "GOOGLE_BOOKS.CLIENT.SERVER_ERROR"
}
```

---

## 🔧 DTOs (Data Transfer Objects)

### UserDTO
```java
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
}
```

### UserLowDTO
```java
@Data
public class UserLowDTO {
    private Long id;
    private String name;
}
```

### BookDTO
```java
@Data
public class BookDTO {
    private String title;
    private List<String> authors;
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
}
```

### ErrorDTO
```java
public record ErrorDTO(String message, String key) { }
```

---

## 🚀 Como Testar

### 1. **Swagger UI**
Acesse: `http://localhost:8080/swagger-ui.html`

### 2. **cURL Commands**

#### Usuários:
```bash
# Criar usuário
curl -X POST http://localhost:8080/api/v1/user \
  -H "Content-Type: application/json" \
  -d '{"name":"João Silva","email":"joao@email.com","login":"joao123","password":"senha123"}'

# Listar usuários
curl http://localhost:8080/api/v1/user/all

# Buscar usuário por ID
curl http://localhost:8080/api/v1/user/1

# Atualizar usuário
curl -X PUT http://localhost:8080/api/v1/user \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"João Atualizado","email":"joao.novo@email.com","login":"joao123","password":"novaSenha"}'

# Remover usuário
curl -X DELETE http://localhost:8080/api/v1/user/1
```

#### Livros:
```bash
# Buscar livros
curl "http://localhost:8080/api/v1/books/search?query=java&limit=3"

# Buscar livros de romance
curl "http://localhost:8080/api/v1/books/search?query=romance&limit=5"

# Buscar livros de programação
curl "http://localhost:8080/api/v1/books/search?query=programacao&limit=2"
```

---

## ⚙️ Configuração

### Variáveis de Ambiente
```bash
# Google Books API Key
export GOOGLE_BOOKS_KEY=sua_chave_aqui
```

### application.properties
```properties
# Google Books API Configuration
google.books.key=${GOOGLE_BOOKS_KEY:}

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/backend
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

---

## 🧪 Testes

### Executar Todos os Testes
```bash
./mvnw test
```

### Testes Específicos
```bash
# Teste do GoogleBooksClient
./mvnw test -Dtest=GoogleBooksClientTest

# Teste do BookService
./mvnw test -Dtest=BookServiceTest
```

---

## 📊 Logs

### Padrão de Logs
```
[INFO] BookService: Iniciando busca de livros - query: 'java', limit: 3
[INFO] BookService: Usando limite de 3 resultados
[INFO] BookService: Chamando GoogleBooksClient.searchBooks()
[INFO] GoogleBooksClient: Chamando Google Books API: https://www.googleapis.com/books/v1/volumes?q=java&key=xxx&maxResults=3
[INFO] GoogleBooksClient: Resposta recebida em 1554ms
[INFO] GoogleBooksClient: Mapeados 3 livros da resposta
[INFO] BookService: Retornando 3 livros encontrados
```

---

## 🎯 Padrões de Documentação Implementados

### ✅ **Consistência**
- Todos os endpoints seguem o mesmo padrão de documentação
- Comentários `//resumo e tag` antes de cada operação
- Tags organizadas por funcionalidade (User, Books)

### ✅ **Completude**
- Códigos de resposta documentados (200, 400, 404, 500)
- Descrições claras em português
- Exemplos de request/response
- Tratamento de erros documentado

### ✅ **Clareza**
- Resumos descritivos das operações
- Parâmetros bem documentados
- Schemas de erro implementados
- Media types especificados

**A documentação está completa e seguindo o padrão estabelecido! 🎉**
