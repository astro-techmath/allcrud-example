# Allcrud Example Project

Este é um projeto exemplo demonstrando o uso da biblioteca **Allcrud** em um cenário real de gerenciamento de livros.

## 📚 Sobre o Projeto

Este exemplo implementa um sistema simples de biblioteca com três entidades principais:

- **Author** (Autor) - Usa `Long` como ID e demonstra soft delete
- **Book** (Livro) - Usa `UUID` como ID e demonstra validações customizadas
- **Category** (Categoria) - Usa `String` como ID (chave de negócio)

## ✨ Recursos Demonstrados

### 1. Diferentes Tipos de ID
- `Long` para Author (auto-incremento tradicional)
- `UUID` para Book (identificador único universal)
- `String` para Category (código de negócio como "FICTION", "SCIENCE")

### 2. Validações Customizadas
O `BookService` adiciona validações de negócio:
- Verifica se o autor existe e está ativo
- Valida a existência da categoria
- Demonstra como estender o comportamento padrão

### 3. Auditoria Automática
A entidade `Author` estende `AuditableEntity`, que automaticamente registra:
- Data de criação
- Usuário que criou
- Data da última modificação
- Usuário que modificou

### 4. Relacionamentos JPA
- Author 1:N Book
- Category 1:N Book

### 5. Testes Completos
Três níveis de testes usando as classes base da Allcrud:
- **Service Tests** - Testes unitários do serviço
- **Controller Integration Tests** - Testes de integração completos

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Gradle 8.7+

### Instalação da Allcrud

Antes de executar este exemplo, você precisa ter a biblioteca Allcrud instalada localmente:

```bash
cd /path/to/allcrud
./gradlew publishToMavenLocal
```

### Executando o Projeto

```bash
cd allcrud-example
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8080`

### Acessando o H2 Console

O projeto usa H2 em memória com dados de exemplo pré-carregados.

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:allcrud_db`
- Username: `sa`
- Password: (deixe em branco)

## 📡 Endpoints Disponíveis

Todos os endpoints seguem o padrão REST e incluem operações CRUD completas.

### Categories (String ID)

```bash
# Listar todas as categorias (paginado)
GET http://localhost:8080/categories

# Buscar por ID
GET http://localhost:8080/categories/FICTION

# Criar nova categoria
POST http://localhost:8080/categories
Content-Type: application/json

{
  "id": "ROMANCE",
  "name": "Romance",
  "description": "Romantic novels and stories"
}

# Atualizar categoria (completo)
PUT http://localhost:8080/categories/ROMANCE
Content-Type: application/json

{
  "id": "ROMANCE",
  "name": "Romance Updated",
  "description": "Updated description"
}

# Atualizar categoria (parcial)
PATCH http://localhost:8080/categories/ROMANCE
Content-Type: application/json

{
  "description": "Only updating description"
}

# Deletar categoria
DELETE http://localhost:8080/categories/ROMANCE
```

### Authors (Long ID com Soft Delete)

```bash
# Listar todos os autores
GET http://localhost:8080/authors

# Filtrar autores ativos
GET http://localhost:8080/authors?active=true

# Paginação e ordenação
GET http://localhost:8080/authors?page=0&size=10&orderBy=name&direction=ASC

# Buscar por ID
GET http://localhost:8080/authors/1

# Criar novo autor
POST http://localhost:8080/authors
Content-Type: application/json

{
  "name": "J.K. Rowling",
  "email": "jk@example.com",
  "bio": "British author of Harry Potter series",
  "active": true
}

# Atualizar autor
PUT http://localhost:8080/authors/1
Content-Type: application/json

{
  "name": "George Orwell Updated",
  "email": "george@example.com",
  "bio": "Updated bio",
  "active": true
}

# Deletar autor (soft delete - marca como inativo)
DELETE http://localhost:8080/authors/1
```

### Books (UUID ID com Validações)

```bash
# Listar todos os livros
GET http://localhost:8080/books

# Filtrar por categoria
GET http://localhost:8080/books?categoryId=FICTION

# Criar novo livro
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "The Great Gatsby",
  "isbn": "978-0-7432-7356-5",
  "price": 14.99,
  "pages": 180,
  "publicationDate": "1925-04-10",
  "description": "A classic American novel",
  "authorId": 1,
  "categoryId": "FICTION"
}

# Atualizar livro
PUT http://localhost:8080/books/{uuid}
Content-Type: application/json

{
  "title": "The Great Gatsby - Updated",
  "isbn": "978-0-7432-7356-5",
  "price": 16.99,
  "pages": 180,
  "publicationDate": "1925-04-10",
  "description": "Updated description",
  "authorId": 1,
  "categoryId": "FICTION"
}

# Deletar livro (hard delete)
DELETE http://localhost:8080/books/{uuid}
```

## 🧪 Executando os Testes

```bash
# Todos os testes
./gradlew test

# Apenas testes de serviço
./gradlew test --tests "*ServiceTest"

# Apenas testes de controller
./gradlew test --tests "*ControllerTest"

# Apenas testes de integração
./gradlew test --tests "*IntegrationTest"
```

## 📊 Headers de Paginação

Quando você faz uma requisição GET paginada, a resposta inclui os seguintes headers:

- `currentPage` - Página atual (zero-based)
- `currentElements` - Número de elementos na página atual
- `totalElements` - Total de elementos encontrados
- `totalPages` - Total de páginas

Exemplo:
```
currentPage: 0
currentElements: 10
totalElements: 25
totalPages: 3
```

## 🎯 Códigos de Status HTTP

A biblioteca Allcrud utiliza os seguintes códigos de status:

- `200 OK` - Operação bem-sucedida (GET por ID, PUT, PATCH)
- `201 Created` - Entidade criada com sucesso (POST)
- `204 No Content` - Operação bem-sucedida sem conteúdo (DELETE, GET vazio)
- `206 Partial Content` - Lista paginada com resultados (GET com dados)
- `400 Bad Request` - Erro de validação
- `404 Not Found` - Entidade não encontrada
- `422 Unprocessable Entity` - Regra de negócio violada

## 🔍 Exemplos de Filtros

A Allcrud suporta filtros dinâmicos baseados nos campos da VO:

```bash
# Filtrar autores por nome (case-insensitive)
GET http://localhost:8080/authors?name=orwell

# Filtrar livros por categoria
GET http://localhost:8080/books?categoryId=FICTION

# Combinar múltiplos filtros
GET http://localhost:8080/authors?active=true&name=martin
```

## 📝 Dados de Exemplo

O projeto inclui dados pré-carregados no arquivo `data.sql`:

**Categorias:**
- FICTION - Fiction
- SCIENCE - Science
- HISTORY - History
- TECHNOLOGY - Technology

**Autores:**
- George Orwell
- Isaac Asimov
- Martin Fowler

**Livros:**
- 1984 (George Orwell)
- Animal Farm (George Orwell)
- Foundation (Isaac Asimov)
- Refactoring (Martin Fowler)

## 🏗️ Estrutura do Projeto

```
src/main/java/com/techmath/example/
├── config/
│   ├── CustomControllerAdvice.java   # Exception handling
│   └── JpaAuditingConfig.java        # Audit configuration
├── controller/
│   ├── AuthorController.java         # Long ID
│   ├── BookController.java           # UUID
│   └── CategoryController.java       # String ID
├── converter/
│   ├── AuthorConverter.java
│   ├── BookConverter.java
│   └── CategoryConverter.java
├── entity/
│   ├── Author.java                   # SoftDeletable + Auditable
│   ├── Book.java                     # Standard entity
│   └── Category.java                 # String ID
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   └── CategoryRepository.java
├── service/
│   ├── AuthorService.java            # With soft delete
│   ├── BookService.java              # With custom validations
│   └── CategoryService.java
├── vo/
│   ├── AuthorVO.java
│   ├── BookVO.java
│   └── CategoryVO.java
└── AllcrudExampleApplication.java
```

## 💡 Dicas de Uso

1. **Soft Delete**: Para entidades que precisam de soft delete, implemente `SoftDeletable` e sobrescreva o método `softDelete()` no service.

2. **Validações Customizadas**: Sobrescreva os métodos `create()` e `update()` no service para adicionar validações de negócio.

3. **IDs Customizados**: A Allcrud suporta qualquer tipo de ID. Escolha o mais adequado ao seu domínio.

4. **Auditoria**: Para entidades auditáveis, estenda `AuditableEntity` em vez de implementar `AbstractEntity` diretamente.

5. **Testes**: Use as classes base de teste da Allcrud para ter cobertura completa com mínimo esforço.

## 📚 Recursos Adicionais

- [Documentação da Allcrud](https://github.com/mathmferreira/allcrud)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

## 🤝 Contribuindo

Este é um projeto exemplo. Para contribuir com a biblioteca Allcrud, visite o [repositório principal](https://github.com/mathmferreira/allcrud).

## 📄 Licença

Este projeto exemplo está sob a mesma licença MIT da biblioteca Allcrud.
