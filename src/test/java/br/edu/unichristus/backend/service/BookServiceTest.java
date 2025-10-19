package br.edu.unichristus.backend.service;

import br.edu.unichristus.backend.domain.model.book.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "google.books.key=AIzaSyCV-lDfLJ9q5ogiOCtWChJ-NCIzN_kTzJ0"
})
class BookServiceTest {
    
    @Autowired
    private BookService bookService;
    
    @Test
    void testGetBooksWithValidQuery() {
        // Arrange
        String query = "musculacao";
        Integer limit = 3;
        
        // Act
        List<BookDTO> books = bookService.getBooks(query, limit);
        
        // Assert
        assertNotNull(books);
        assertTrue(books.size() >= 0 && books.size() <= 3);
        
        // Verificar se os livros têm os campos mapeados
        for (BookDTO book : books) {
            assertNotNull(book.getTitle());
            assertNotNull(book.getAuthors());
        }
        
        System.out.println("Teste BookService concluído com sucesso!");
        System.out.println("Livros encontrados: " + books.size());
        books.forEach(book -> {
            System.out.println("- " + book.getTitle() + " (" + book.getPublishedYear() + ")");
        });
    }
    
    @Test
    void testGetBooksWithNullLimit() {
        // Arrange
        String query = "java";
        Integer limit = null;
        
        // Act
        List<BookDTO> books = bookService.getBooks(query, limit);
        
        // Assert
        assertNotNull(books);
        assertTrue(books.size() >= 0 && books.size() <= 5); // Deve usar limite padrão 5
        
        System.out.println("Teste com limite nulo - usando padrão 5: " + books.size() + " livros");
    }
    
    @Test
    void testGetBooksWithEmptyQuery() {
        // Arrange
        String query = "";
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBooks(query, 5);
        });
        
        assertEquals("termo de busca não pode estar vazio", exception.getMessage());
        System.out.println("Teste de validação de query vazia passou!");
    }
    
    @Test
    void testGetBooksWithNullQuery() {
        // Arrange
        String query = null;
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBooks(query, 5);
        });
        
        assertEquals("termo de busca não pode estar vazio", exception.getMessage());
        System.out.println("Teste de validação de query nula passou!");
    }
}
