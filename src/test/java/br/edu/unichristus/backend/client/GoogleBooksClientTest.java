package br.edu.unichristus.backend.client;

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
class GoogleBooksClientTest {
    
    @Autowired
    private GoogleBooksClient googleBooksClient;
    
    @Test
    void testSearchBooks() {
        // Arrange
        String keyword = "musculacao";
        int limit = 2;
        
        // Act
        List<BookDTO> books = googleBooksClient.searchBooks(keyword, limit);
        
        // Assert
        assertNotNull(books);
        assertTrue(books.size() >= 0 && books.size() <= 2);
        
        // Verificar se os livros têm os campos mapeados corretamente
        for (BookDTO book : books) {
            assertNotNull(book.getTitle());
            assertNotNull(book.getAuthors());
            // publishedYear pode ser null se não estiver disponível
            // infoLink pode ser null se não estiver disponível
            // thumbnail pode ser null se não estiver disponível
        }
        
        System.out.println("Teste concluído com sucesso!");
        System.out.println("Livros encontrados: " + books.size());
        books.forEach(book -> {
            System.out.println("- " + book.getTitle() + " (" + book.getPublishedYear() + ")");
        });
    }
}
