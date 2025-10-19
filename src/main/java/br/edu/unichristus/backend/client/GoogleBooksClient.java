package br.edu.unichristus.backend.client;

import br.edu.unichristus.backend.domain.model.book.BookDTO;
import br.edu.unichristus.backend.exception.ApiException;
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
    
    public List<BookDTO> searchBooks(String keyword, int limit) {
        // Validar keyword
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                "GOOGLE_BOOKS.CLIENT.INVALID_KEYWORD", 
                "Palavra-chave não pode ser vazia");
        }
        
        // Validar e ajustar limit
        int adjustedLimit = Math.max(1, Math.min(10, limit));
        
        // Montar URI
        String uri = String.format("%s?q=%s&key=%s&maxResults=%d", 
            VOLUMES_ENDPOINT, 
            keyword.trim(), 
            apiKey, 
            adjustedLimit);
        
        log.info("Chamando Google Books API: {}", GOOGLE_BOOKS_BASE_URL + uri);
        
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
            log.info("Resposta recebida em {}ms", responseTime);
            
            return parseResponse(response);
            
        } catch (WebClientResponseException e) {
            long responseTime = System.currentTimeMillis() - startTime;
            log.error("Erro na chamada à API ({}ms): Status {} - {}", responseTime, e.getStatusCode(), e.getResponseBodyAsString());
            
            if (e.getStatusCode().is4xxClientError()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, 
                    "GOOGLE_BOOKS.CLIENT.CLIENT_ERROR", 
                    "Erro do cliente na API do Google Books: " + e.getStatusCode());
            } else if (e.getStatusCode().is5xxServerError()) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "GOOGLE_BOOKS.CLIENT.SERVER_ERROR", 
                    "Erro do servidor na API do Google Books: " + e.getStatusCode());
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                    "GOOGLE_BOOKS.CLIENT.UNKNOWN_ERROR", 
                    "Erro desconhecido na API do Google Books: " + e.getStatusCode());
            }
            
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            log.error("Erro inesperado na chamada à API ({}ms): {}", responseTime, e.getMessage(), e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "GOOGLE_BOOKS.CLIENT.UNEXPECTED_ERROR", 
                "Erro inesperado ao chamar API do Google Books");
        }
    }
    
    private List<BookDTO> parseResponse(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.get("items");
            
            if (itemsNode == null || !itemsNode.isArray()) {
                log.info("Resposta sem campo 'items' ou não é array - retornando lista vazia");
                return new ArrayList<>();
            }
            
            List<BookDTO> books = new ArrayList<>();
            
            for (JsonNode item : itemsNode) {
                BookDTO book = new BookDTO();
                JsonNode volumeInfo = item.get("volumeInfo");
                
                if (volumeInfo != null) {
                    // Title
                    if (volumeInfo.has("title")) {
                        book.setTitle(volumeInfo.get("title").asText());
                    }
                    
                    // Authors
                    if (volumeInfo.has("authors") && volumeInfo.get("authors").isArray()) {
                        List<String> authors = new ArrayList<>();
                        for (JsonNode author : volumeInfo.get("authors")) {
                            authors.add(author.asText());
                        }
                        book.setAuthors(authors);
                    } else {
                        book.setAuthors(new ArrayList<>());
                    }
                    
                    // Published Year
                    if (volumeInfo.has("publishedDate")) {
                        String publishedDate = volumeInfo.get("publishedDate").asText();
                        try {
                            // Extrair os 4 primeiros dígitos se existirem
                            if (publishedDate.length() >= 4) {
                                String yearStr = publishedDate.substring(0, 4);
                                book.setPublishedYear(Integer.parseInt(yearStr));
                            }
                        } catch (NumberFormatException e) {
                            log.debug("Erro ao converter ano da data: {}", publishedDate);
                        }
                    }
                    
                    // Info Link
                    if (volumeInfo.has("infoLink")) {
                        book.setInfoLink(volumeInfo.get("infoLink").asText());
                    }
                    
                    // Thumbnail
                    if (volumeInfo.has("imageLinks") && volumeInfo.get("imageLinks").has("thumbnail")) {
                        book.setThumbnail(volumeInfo.get("imageLinks").get("thumbnail").asText());
                    }
                }
                
                books.add(book);
            }
            
            log.info("Mapeados {} livros da resposta", books.size());
            return books;
            
        } catch (Exception e) {
            log.error("Erro ao fazer parse da resposta JSON: {}", e.getMessage(), e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "GOOGLE_BOOKS.CLIENT.PARSE_ERROR", 
                "Erro ao processar resposta da API do Google Books");
        }
    }
}
