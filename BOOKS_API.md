# 📚 API de Busca de Livros - Google Books Integration

## 🎯 Visão Geral

Sistema Spring Boot que integra com a **Google Books API** para permitir busca de livros através de um endpoint REST. O sistema implementa uma arquitetura em camadas seguindo as melhores práticas do Spring Boot.

## 🏗️ Arquitetura

### Estrutura de Pacotes
```
br.edu.unichristus.backend/
├── controller/     # Camada de apresentação (REST)
├── service/        # Camada de negócio
├── client/         # Camada de integração (APIs externas)
├── domain/
│   ├── dto/        # Data Transfer Objects
│   └── model/      # Entidades de domínio
├── exception/      # Tratamento de erros
└── util/          # Utilitários
```

### Fluxo de Dados
```
Controller → Service → Client → Google Books API
    ↓           ↓         ↓           ↓
BookController → BookService → GoogleBooksClient → https://www.googleapis.com/books/v1/volumes
    ↓           ↓         ↓           ↓
ResponseEntity ← List<BookDTO> ← List<BookDTO> ← JSON Response
```

## 🔧 Componentes Implementados

### 1. 📚 BookDTO (Data Transfer Object)
**Arquivo:** `src/main/java/br/edu/unichristus/backend/domain/model/book/BookDTO.java`

**Responsabilidade:** Representar dados de um livro

**Campos:**
- `title` - Título do livro
- `authors` - Lista de autores
- `publishedYear` - Ano de publicação
- `infoLink` - Link para informações do livro
- `thumbnail` - URL da capa do livro

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

### 2. 🌐 GoogleBooksClient (Cliente HTTP)
**Arquivo:** `src/main/java/br/edu/unichristus/backend/client/GoogleBooksClient.java`

**Responsabilidades:**
- ✅ **Comunicação HTTP** com Google Books API
- ✅ **Configuração WebClient** com timeouts (10s)
- ✅ **Montagem de URL** com parâmetros (q, key, maxResults)
- ✅ **Parse JSON** da resposta da API
- ✅ **Mapeamento** para BookDTO
- ✅ **Tratamento de erros** (4xx, 5xx, timeout)
- ✅ **Logging** de URL e tempo de resposta

**Endpoint:** `https://www.googleapis.com/books/v1/volumes`

**Método Principal:**
```java
public List<BookDTO> searchBooks(String keyword, int limit)
```

### 3. 🏢 BookService (Lógica de Negócio)
**Arquivo:** `src/main/java/br/edu/unichristus/backend/service/BookService.java`

**Responsabilidades:**
- ✅ **Validação** de entrada (query não vazia)
- ✅ **Limite padrão** (5 livros se não especificado)
- ✅ **Chamada ao client** GoogleBooksClient
- ✅ **Tratamento de exceções** com mensagens amigáveis
- ✅ **Logging** do fluxo completo

**Método Principal:**
```java
public List<BookDTO> getBooks(String query, Integer limit)
```

### 4. 🎮 BookController (API REST)
**Arquivo:** `src/main/java/br/edu/unichristus/backend/controller/BookController.java`

**Responsabilidades:**
- ✅ **Endpoint REST** `GET /api/v1/books/search`
- ✅ **Parâmetros:** `query` (obrigatório), `limit` (opcional)
- ✅ **Documentação Swagger** com anotações
- ✅ **Integração** com BookService

**Endpoint:**
```java
@GetMapping("/search")
public ResponseEntity<List<BookDTO>> searchBooks(
    @RequestParam String query,
    @RequestParam(required = false, defaultValue = "5") Integer limit)
```

## ⚙️ Configurações

### Dependências (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.1.1</version>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.6</version>
    </dependency>
</dependencies>
```

### Configuração de Ambiente
**Arquivo:** `src/main/resources/application.properties`
```properties
spring.application.name=backend

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/projeto-aula
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

# Google Books API Configuration
google.books.key=${GOOGLE_BOOKS_KEY:}
```

## 🌐 Endpoints Disponíveis

### 📚 Busca de Livros
- **URL:** `GET /api/v1/books/search`
- **Parâmetros:**
  - `query` (obrigatório): Termo de busca
  - `limit` (opcional): Número máximo de resultados (1-10, padrão: 5)
- **Exemplo:** `GET /api/v1/books/search?query=musculacao&limit=3`

### 👥 Gestão de Usuários
- `POST /api/v1/user` - Criar usuário
- `GET /api/v1/user/all` - Listar usuários
- `GET /api/v1/user/{id}` - Buscar usuário por ID
- `PUT /api/v1/user` - Atualizar usuário
- `DELETE /api/v1/user/{id}` - Deletar usuário

## 📊 Exemplo de Resposta

### Requisição
```http
GET /api/v1/books/search?query=java&limit=2
```

### Resposta
```json
[
  {
    "title": "Java for Students",
    "authors": ["Doug Bell", "Mike Parr"],
    "publishedYear": 2001,
    "infoLink": "http://books.google.com.br/books?id=TRUdyfwdaSoC&dq=java&hl=&source=gbs_api",
    "thumbnail": "http://books.google.com/books/content?id=TRUdyfwdaSoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
  },
  {
    "title": "Java For Dummies",
    "authors": ["Barry A. Burd"],
    "publishedYear": 2011,
    "infoLink": "http://books.google.com.br/books?id=x8BvqSRbR3cC&dq=java&hl=&source=gbs_api",
    "thumbnail": "http://books.google.com/books/content?id=x8BvqSRbR3cC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
  }
]
```

## 📝 Logs de Execução

### Exemplo de Logs
```
2025-10-19T01:02:14.230-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.service.BookService        : BookService: Iniciando busca de livros - query: 'java', limit: 2
2025-10-19T01:02:14.230-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.service.BookService        : BookService: Usando limite de 2 resultados
2025-10-19T01:02:14.230-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.service.BookService        : BookService: Chamando GoogleBooksClient.searchBooks()
2025-10-19T01:02:14.230-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.client.GoogleBooksClient   : Chamando Google Books API: https://www.googleapis.com/books/v1/volumes?q=java&key=...&maxResults=2
2025-10-19T01:02:15.784-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.client.GoogleBooksClient   : Resposta recebida em 1554ms
2025-10-19T01:02:15.790-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.client.GoogleBooksClient   : Mapeados 2 livros da resposta
2025-10-19T01:02:15.790-03:00  INFO 43262 --- [nio-8080-exec-8] b.e.u.backend.service.BookService        : BookService: Retornando 2 livros encontrados
```

## 🧪 Testes Implementados

### Testes de Integração
- ✅ **GoogleBooksClientTest** - Testa comunicação com API externa
- ✅ **BookServiceTest** - Testa lógica de negócio e validações

### Cenários Testados
- ✅ Busca com query válida
- ✅ Limite nulo (usa padrão 5)
- ✅ Query vazia (lança exceção)
- ✅ Query nula (lança exceção)

### Executar Testes
```bash
# Executar todos os testes
./mvnw test

# Executar teste específico
./mvnw test -Dtest=BookServiceTest
```

## 📖 Documentação

### Swagger UI
- **URL:** `http://localhost:8080/swagger-ui.html`
- **Funcionalidades:** Teste interativo dos endpoints, documentação automática

### Acesso à Documentação
1. Inicie a aplicação: `./mvnw spring-boot:run`
2. Acesse: `http://localhost:8080/swagger-ui.html`
3. Explore os endpoints disponíveis

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL
- Chave da Google Books API

### Configuração
1. **Clone o repositório**
2. **Configure a variável de ambiente:**
   ```bash
   export GOOGLE_BOOKS_KEY=sua_chave_aqui
   ```
3. **Configure o banco de dados** no `application.properties`
4. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

### Verificar Funcionamento
```bash
# Teste básico
curl "http://localhost:8080/api/v1/books/search?query=java&limit=2"

# Teste com termo em português
curl "http://localhost:8080/api/v1/books/search?query=musculacao&limit=3"
```

## 🛡️ Tratamento de Erros

### Tipos de Erro Tratados
- **4xx (Client Error):** Erro do cliente na API do Google Books
- **5xx (Server Error):** Erro do servidor na API do Google Books
- **Timeout:** Timeout de conexão (10 segundos)
- **Query vazia:** Validação de entrada
- **Parse JSON:** Erro ao processar resposta

### Exemplo de Resposta de Erro
```json
{
  "message": "termo de busca não pode estar vazio",
  "key": "GOOGLE_BOOKS.SERVICE.INVALID_QUERY"
}
```

## 📈 Performance

### Configurações de Timeout
- **Conexão:** 10 segundos
- **Leitura:** 10 segundos
- **Limite de resultados:** 1-10 livros

### Otimizações
- ✅ **WebClient** para comunicação assíncrona
- ✅ **Pool de conexões** HikariCP
- ✅ **Logging** para monitoramento
- ✅ **Validação** de entrada

## 🔍 Monitoramento

### Logs Importantes
- **Início de busca:** Query e limite
- **Chamada à API:** URL completa
- **Tempo de resposta:** Milissegundos
- **Quantidade de resultados:** Livros encontrados
- **Erros:** Stack trace completo

### Métricas
- Tempo de resposta da API externa
- Quantidade de livros retornados
- Taxa de erro
- Uso de memória

## 🎯 Funcionalidades Principais

1. **🔍 Busca de Livros** - Integração com Google Books API
2. **👤 Gestão de Usuários** - CRUD completo
3. **📚 Documentação Automática** - Swagger UI
4. **🛡️ Tratamento de Erros** - Exceções centralizadas
5. **📝 Logging Detalhado** - Rastreamento completo
6. **⚡ Performance** - Timeouts configurados
7. **🧪 Testes** - Cobertura de integração

## 📋 Status do Projeto

- ✅ **Aplicação rodando** na porta 8080
- ✅ **Endpoint funcionando** e retornando livros
- ✅ **Swagger atualizado** com documentação de livros
- ✅ **Testes passando** com sucesso
- ✅ **Logging funcionando** corretamente
- ✅ **Integração com Google Books** operacional

## 🔧 Tecnologias Utilizadas

- **Spring Boot 3.5.5**
- **Java 17**
- **PostgreSQL**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **ModelMapper**
- **SpringDoc OpenAPI (Swagger)**
- **Maven**
- **WebClient (Spring WebFlux)**

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs da aplicação
2. Consulte a documentação Swagger
3. Execute os testes para validar funcionalidades
4. Verifique a configuração da chave da API

---

**O sistema está 100% funcional para busca de livros! 🎉✨**
