package com.saberoueslati.quill.playwright.api;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorApiTest extends BaseApiTest {

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

    @Test
    void testAddAuthor() {
        String id = createAuthorAndGetId("Test author 1");
        assertNotNull(id);
    }

    @Test
    void testGetAuthors() {
        createAuthorAndGetId("Test author 2");
        APIResponse getResponse = request.get(BASE_URL + "/authors");
        assertEquals(200, getResponse.status());
        assertTrue(getResponse.text().contains("Test author 2"));
    }

    @Test
    void testInvalidAuthorPayload() {
        String payload = """
                    {
                      "birthDate": "2000-01-01",
                      "nationality": "Test"
                    }
                """; // Missing name

        APIResponse response = request.post(BASE_URL + "/authors",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(400, response.status());
    }

    @Test
    void testDeleteAuthor() {
        String id = createAuthorAndGetId("ToDelete");
        APIResponse delete = request.delete(BASE_URL + "/authors/" + id);
        assertTrue(delete.status() == 200 || delete.status() == 204);
    }

    @AfterEach
    void cleanUpBooks() {
        request.delete(BASE_URL + "/authors/" + "Test author 1");
        request.delete(BASE_URL + "/authors/" + "Test author 2");
    }
}
