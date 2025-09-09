package com.saberoueslati.quill.playwright.api;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookApiTest extends BaseApiTest {

    private final List<String> createdBookIds = new ArrayList<>();

    String createAuthorAndGetId(String name) {
        String payload = String.format("""
                    {
                      "name": "%s",
                      "birthDate": "1970-01-01",
                      "nationality": "Test"
                    }
                """, name);

        APIResponse response = request.post(BASE_URL + "/authors",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        String body = response.text();
        return body.split("\"id\":")[1].split(",")[0].trim();
    }

    String createBookPayload(String title, String isbn, String authorId) {
        return String.format("""
                    {
                      "title": "%s",
                      "publicationDate": "2000-01-01",
                      "isbn": "%s",
                      "authorId": %s
                    }
                """, title, isbn, authorId);
    }

    @AfterEach
    void cleanUpBooks() {
        for (String id : createdBookIds) {
            request.delete(BASE_URL + "/books/" + id);
        }
        createdBookIds.clear();
    }

    @Test
    void testAddBook() {
        String authorId = createAuthorAndGetId("Book Author");
        String payload = createBookPayload("Test Book", "ISBN-001", authorId);

        APIResponse response = request.post(BASE_URL + "/books",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        assertTrue(response.text().contains("Test Book"));

        String bookId = response.text().split("\"id\":")[1].split(",")[0].trim();
        createdBookIds.add(bookId);
    }

    @Test
    void testGetBookByIsbn() {
        String authorId = createAuthorAndGetId("Search Author");
        String isbn = "ISBN-SEARCH";
        String payload = createBookPayload("Search Book", isbn, authorId);

        APIResponse response = request.post(BASE_URL + "/books",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));
        assertEquals(200, response.status());
        String bookId = response.text().split("\"id\":")[1].split(",")[0].trim();
        createdBookIds.add(bookId);

        APIResponse getResponse = request.get(BASE_URL + "/books/isbn/" + isbn);
        assertEquals(200, getResponse.status());
        assertTrue(getResponse.text().contains("Search Book"));
    }

    @Test
    void testGetAllBooks() {
        String authorId = createAuthorAndGetId("List Author");
        APIResponse response = request.post(BASE_URL + "/books",
                RequestOptions.create().setHeader("Content-Type", "application/json")
                        .setData(createBookPayload("Book A", "ISBN-A", authorId)));

        assertEquals(200, response.status());
        String bookId = response.text().split("\"id\":")[1].split(",")[0].trim();
        createdBookIds.add(bookId);

        APIResponse getAll = request.get(BASE_URL + "/books");
        assertEquals(200, getAll.status());
        assertTrue(getAll.text().contains("Book A"));
    }

    @Test
    void testInvalidBookPayload() {
        String payload = """
                    {
                      "publicationDate": "2020-01-01",
                      "isbn": "INVALID"
                    }
                """; // Missing title and authorId

        APIResponse response = request.post(BASE_URL + "/books",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(400, response.status());
    }

    @Test
    void testAddBooksInBulk() {
        String authorId = createAuthorAndGetId("Bulk Author");

        String payload = String.format("""
                    [
                      {
                        "title": "Bulk Book 1",
                        "publicationDate": "2020-01-01",
                        "isbn": "BULK-001",
                        "authorId": %s
                      },
                      {
                        "title": "Bulk Book 2",
                        "publicationDate": "2020-01-01",
                        "isbn": "BULK-002",
                        "authorId": %s
                      }
                    ]
                """, authorId, authorId);

        APIResponse response = request.post(BASE_URL + "/books/bulk",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        assertTrue(response.text().contains("Bulk Book 1"));
        assertTrue(response.text().contains("Bulk Book 2"));

        // Extract and store both book IDs
        for (String item : response.text().split("\\{")) {
            if (item.contains("\"id\":")) {
                String id = item.split("\"id\":")[1].split(",")[0].trim();
                createdBookIds.add(id);
            }
        }
    }
}
