# 📚 CRUD Completo de Livros - Documentação

## 📋 **Visão Geral**

CRUD completo para gerenciamento de livros no projeto Spring Boot, incluindo:
- ✅ Criação, leitura, atualização e exclusão de livros no banco de dados local
- ✅ Integração mantida com Google Books API para busca externa
- ✅ Flyway para migração de banco de dados
- ✅ Tratamento global de exceções
- ✅ Documentação Swagger completa

---

## 🏗️ **Arquitetura Implementada**

```
📦 books/
├── 📁 controller/
│   └── BookController.java       ← Endpoints REST
├── 📁 service/
│   └── BookService.java          ← Lógica de negócio
├── 📁 repository/
│   └── BookRepository.java       ← Acesso ao banco
├── 📁 entity/
│   └── BookEntity.java           ← Entidade JPA
├── 📁 dto/
│   └── BookDTO.java              ← Transfer Object
└── 📁 mapper/
    └── BookMapper.java           ← Conversão Entity ↔ DTO
```

---

## 📦 **1. Entidade (BookEntity.java)**

### **Localização:** `books/entity/BookEntity.java`

```java
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String authors;              // String separada por vírgula
    
    @Column(name = "published_year")
    private Integer publishedYear;
    
    @Column(name = "info_link", length = 500)
    private String infoLink;
    
    @Column(length = 500)
    private String thumbnail;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean favorite = false;     // Valor padrão
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

**Características:**
- ✅ Campo `authors` armazenado como string separada por vírgulas
- ✅ Campo `favorite` com valor padrão `false`
- ✅ Timestamps automáticos (`@PrePersist`, `@PreUpdate`)

---

## 📝 **2. DTO (BookDTO.java)**

### **Localização:** `books/dto/BookDTO.java`

```java
@Data
public class BookDTO {
    private Long id;
    private String title;
    private List<String> authors;        // Lista de autores
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
    private Boolean favorite;
}
```

**Características:**
- ✅ `authors` como `List<String>` para facilitar uso
- ✅ Lombok `@Data` para getters/setters automáticos

---

## 🔄 **3. Mapper (BookMapper.java)**

### **Localização:** `books/mapper/BookMapper.java`

```java
public class BookMapper {
    public static BookDTO toDTO(BookEntity entity) {
        // Converte String "autor1, autor2" → List<String>
    }
    
    public static BookEntity toEntity(BookDTO dto) {
        // Converte List<String> → String "autor1, autor2"
    }
}
```

**Conversões:**
- **Entity → DTO**: `"João, Maria"` → `["João", "Maria"]`
- **DTO → Entity**: `["João", "Maria"]` → `"João, Maria"`

---

## 🗄️ **4. Repository (BookRepository.java)**

### **Localização:** `books/repository/BookRepository.java`

```java
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
```

**Características:**
- ✅ Métodos CRUD herdados de `JpaRepository`
- ✅ Sem métodos customizados por enquanto

---

## 🧠 **5. Service (BookService.java)**

### **Localização:** `books/service/BookService.java`

### **Métodos Implementados:**

#### **5.1 create(BookDTO)**
```java
public BookDTO create(BookDTO bookDTO)
```
- Valida título obrigatório
- Converte DTO → Entity
- Salva no banco
- Retorna DTO criado

#### **5.2 findAll()**
```java
public List<BookDTO> findAll()
```
- Busca todos os livros
- Converte lista de entities → DTOs
- Retorna lista completa

#### **5.3 findById(Long id)**
```java
public BookDTO findById(Long id)
```
- Busca livro por ID
- Lança exceção se não encontrado
- Retorna DTO

#### **5.4 update(Long id, BookDTO)**
```java
public BookDTO update(Long id, BookDTO bookDTO)
```
- Busca livro existente
- Atualiza apenas campos não nulos
- Salva e retorna DTO atualizado

#### **5.5 delete(Long id)**
```java
public void delete(Long id)
```
- Verifica se livro existe
- Remove do banco

#### **5.6 searchGoogleBooks(String query, Integer limit)**
```java
public List<BookDTO> searchGoogleBooks(String query, Integer limit)
```
- Valida query
- Chama Google Books API via `GoogleBooksClient`
- Retorna lista de livros da API externa

---

## 🎮 **6. Controller (BookController.java)**

### **Localização:** `books/controller/BookController.java`

### **Endpoints Implementados:**

#### **POST /api/v1/books** - Cadastrar livro
```http
POST /api/v1/books
Content-Type: application/json

{
  "title": "Título do Livro",
  "authors": ["Autor 1", "Autor 2"],
  "publishedYear": 2024,
  "favorite": false
}
```
**Response:** `201 Created` + `BookDTO`

---

#### **GET /api/v1/books** - Listar todos
```http
GET /api/v1/books
```
**Response:** `200 OK` + `List<BookDTO>`

---

#### **GET /api/v1/books/{id}** - Buscar por ID
```http
GET /api/v1/books/1
```
**Response:** `200 OK` + `BookDTO`

---

#### **PUT /api/v1/books/{id}** - Atualizar
```http
PUT /api/v1/books/1
Content-Type: application/json

{
  "title": "Novo Título",
  "favorite": true
}
```
**Response:** `200 OK` + `BookDTO`

---

#### **DELETE /api/v1/books/{id}** - Remover
```http
DELETE /api/v1/books/1
```
**Response:** `204 No Content`

---

#### **GET /api/v1/books/search** - Buscar na Google Books API
```http
GET /api/v1/books/search?query=musculacao&limit=3
```
**Response:** `200 OK` + `List<BookDTO>`

---

## 🗃️ **7. Migração Flyway**

### **Localização:** `src/main/resources/db/migration/V1__create_table_books.sql`

```sql
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    authors VARCHAR(1000),
    published_year INTEGER,
    info_link VARCHAR(500),
    thumbnail VARCHAR(500),
    favorite BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_favorite ON books(favorite);
```

---

## ⚙️ **8. Configuração (application.properties)**

```properties
# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration

# Google Books API Configuration
google.books.key=${GOOGLE_BOOKS_KEY:}
```

**Mudanças:**
- ✅ `ddl-auto=validate` → Flyway gerencia o schema
- ✅ `show-sql=true` → Ver queries no console
- ✅ Flyway habilitado

---

## 🛡️ **9. Tratamento de Exceções**

### **Localização:** `exception/ApiExceptionHandler.java`

```java
@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException() {
        // Retorna 400 Bad Request
    }
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handleApiException() {
        // Retorna status específico da ApiException
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException() {
        // Retorna 500 Internal Server Error
    }
}
```

**Respostas de Erro:**
```json
{
  "message": "Livro não encontrado com ID: 1",
  "key": "VALIDATION_ERROR"
}
```

---

## 🚀 **10. Como Executar**

### **Passo 1: Configurar variável de ambiente**
```bash
export GOOGLE_BOOKS_KEY="AIzaSyCV-lDfLJ9q5ogiOCtWChJ-NCIzN_kTzJ0"
```

### **Passo 2: Iniciar aplicação**
```bash
./mvnw spring-boot:run
```

### **Passo 3: Acessar Swagger**
```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 **11. Testes**

### **Arquivo:** `test_books_crud.http`

```http
### 1. Cadastrar livro
POST http://localhost:8080/api/v1/books
Content-Type: application/json

{
  "title": "Musculação: Montagem de Programas",
  "authors": ["Renato Barroso"],
  "publishedYear": 2020,
  "favorite": false
}

### 2. Listar todos
GET http://localhost:8080/api/v1/books

### 3. Buscar por ID
GET http://localhost:8080/api/v1/books/1

### 4. Atualizar
PUT http://localhost:8080/api/v1/books/1
Content-Type: application/json

{
  "favorite": true
}

### 5. Remover
DELETE http://localhost:8080/api/v1/books/1

### 6. Buscar na Google API
GET http://localhost:8080/api/v1/books/search?query=musculacao&limit=3
```

---

## 📊 **12. Fluxo Completo**

### **Cadastro de Livro:**
```
Usuario → Controller → Service → Repository → PostgreSQL
                                     ↓
                                BookMapper
```

### **Busca na Google Books API:**
```
Usuario → Controller → Service → GoogleBooksClient → Google API
                                     ↓
                               List<BookDTO>
```

---

## ✨ **13. Recursos Implementados**

### **CRUD Local:**
- ✅ **Create** - Cadastrar livros no banco local
- ✅ **Read** - Listar todos ou buscar por ID
- ✅ **Update** - Atualizar informações
- ✅ **Delete** - Remover livros

### **Integração Externa:**
- ✅ Busca na Google Books API mantida
- ✅ Conversão automática para DTO

### **Qualidade:**
- ✅ Validações em múltiplas camadas
- ✅ Tratamento global de exceções
- ✅ Logging completo
- ✅ Documentação Swagger
- ✅ Migrações Flyway

### **Banco de Dados:**
- ✅ Tabela `books` criada via Flyway
- ✅ Índices para otimização
- ✅ Timestamps automáticos
- ✅ Campo `favorite` com padrão `false`

---

## 🎯 **14. Resumo**

**O que foi criado:**
1. ✅ Entidade JPA (`BookEntity`)
2. ✅ DTO (`BookDTO`)
3. ✅ Mapper (`BookMapper`)
4. ✅ Repository (`BookRepository`)
5. ✅ Service com 6 métodos (`BookService`)
6. ✅ Controller com 6 endpoints (`BookController`)
7. ✅ Migração SQL (`V1__create_table_books.sql`)
8. ✅ Tratamento de exceções (`ApiExceptionHandler`)
9. ✅ Arquivo de testes HTTP (`test_books_crud.http`)
10. ✅ Flyway configurado no `pom.xml`

**Status:**
🟢 **Sistema completo e funcional!**

**Próximos passos sugeridos:**
- [ ] Implementar busca por favoritos
- [ ] Adicionar paginação
- [ ] Implementar busca por título/autor
- [ ] Criar testes unitários completos

