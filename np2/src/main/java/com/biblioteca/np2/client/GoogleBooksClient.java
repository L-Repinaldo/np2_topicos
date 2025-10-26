package com.biblioteca.np2.client;

import com.biblioteca.np2.domain.model.book.BookDto;
import com.biblioteca.np2.excepiton.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleBooksClient {
    
    private static final Logger log = LoggerFactory.getLogger(GoogleBooksClient.class);
    private static final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1";
    private static final String VOLUMES_ENDPOINT = "/volumes";
    
    @Value("${google.books.key}")
    private String apiKey;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public GoogleBooksClient() {
        this.webClient = WebClient.builder()
                .baseUrl(GOOGLE_BOOKS_BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<BookDto> searchBooks(String keyword, int limit) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                "np2.client.googlebooks.badrequest", 
                "Palavra-chave não pode ser vazia");
        }
        
        int adjustedLimit = Math.max(1, Math.min(10, limit));
        
        String uri = String.format("%s?q=%s&key=%s&maxResults=%d", 
            VOLUMES_ENDPOINT, keyword.trim(), apiKey, adjustedLimit);
        
        log.info("GoogleBooksClient: Chamando API - query: '{}', limit: {}", keyword, adjustedLimit);
        
        long startTime = System.currentTimeMillis();
        
        try {
            String response = webClient.get()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
            
            long responseTime = System.currentTimeMillis() - startTime;
            log.info("GoogleBooksClient: Resposta recebida em {}ms", responseTime);
            
            return parseResponse(response);
            
        } catch (WebClientResponseException e) {
            log.error("GoogleBooksClient: Erro na chamada - Status {}", e.getStatusCode());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "np2.client.googlebooks.error", 
                "Erro ao consultar Google Books API");
        } catch (Exception e) {
            log.error("GoogleBooksClient: Erro inesperado: {}", e.getMessage(), e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "np2.client.googlebooks.error", 
                "Erro inesperado ao chamar API do Google Books");
        }
    }
    
    private List<BookDto> parseResponse(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.get("items");
            
            if (itemsNode == null || !itemsNode.isArray()) {
                log.info("GoogleBooksClient: Nenhum livro encontrado");
                return new ArrayList<>();
            }
            
            List<BookDto> books = new ArrayList<>();
            
            for (JsonNode item : itemsNode) {
                BookDto book = new BookDto();
                JsonNode volumeInfo = item.get("volumeInfo");
                
                if (volumeInfo != null) {
                    if (volumeInfo.has("title")) {
                        book.setTitle(volumeInfo.get("title").asText());
                    }
                    
                    if (volumeInfo.has("authors") && volumeInfo.get("authors").isArray()) {
                        List<String> authors = new ArrayList<>();
                        for (JsonNode author : volumeInfo.get("authors")) {
                            authors.add(author.asText());
                        }
                        book.setAuthors(authors);
                    } else {
                        book.setAuthors(new ArrayList<>());
                    }
                    
                    if (volumeInfo.has("publishedDate")) {
                        String publishedDate = volumeInfo.get("publishedDate").asText();
                        try {
                            if (publishedDate.length() >= 4) {
                                book.setPublishedYear(Integer.parseInt(publishedDate.substring(0, 4)));
                            }
                        } catch (NumberFormatException e) {
                            log.debug("Erro ao converter ano: {}", publishedDate);
                        }
                    }
                    
                    if (volumeInfo.has("infoLink")) {
                        book.setInfoLink(volumeInfo.get("infoLink").asText());
                    }
                    
                    if (volumeInfo.has("imageLinks") && volumeInfo.get("imageLinks").has("thumbnail")) {
                        book.setThumbnail(volumeInfo.get("imageLinks").get("thumbnail").asText());
                    }
                }
                
                books.add(book);
            }
            
            log.info("GoogleBooksClient: Mapeados {} livros", books.size());
            return books;
            
        } catch (Exception e) {
            log.error("GoogleBooksClient: Erro ao processar resposta: {}", e.getMessage(), e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "np2.client.googlebooks.parse", 
                "Erro ao processar resposta da API");
        }
    }
}


