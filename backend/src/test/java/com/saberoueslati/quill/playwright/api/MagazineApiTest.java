package com.saberoueslati.quill.playwright.api;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MagazineApiTest extends BaseApiTest {

    private final List<String> createdMagazineIds = new ArrayList<>();

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

    String createMagazinePayload(String title, int issueNumber, List<String> authorIds) {
        String authorsArray = authorIds.toString(); // Java list to JSON array format
        return String.format("""
                    {
                      "title": "%s",
                      "publicationDate": "2020-01-01",
                      "issueNumber": %d,
                      "authorIds": %s
                    }
                """, title, issueNumber, authorsArray);
    }

    @AfterEach
    void cleanUpMagazines() {
        for (String id : createdMagazineIds) {
            request.delete(BASE_URL + "/magazines/" + id);
        }
        createdMagazineIds.clear();
    }

    @Test
    void testAddMagazine() {
        List<String> authorIds = List.of(
                createAuthorAndGetId("Magazine Author 1"),
                createAuthorAndGetId("Magazine Author 2")
        );

        String payload = createMagazinePayload("Test Magazine", 101, authorIds);

        APIResponse response = request.post(BASE_URL + "/magazines",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        assertTrue(response.text().contains("Test Magazine"));

        String id = response.text().split("\"id\":")[1].split(",")[0].trim();
        createdMagazineIds.add(id);
    }

    @Test
    void testGetAllMagazines() {
        List<String> authorIds = List.of(createAuthorAndGetId("List Author"));
        String payload = createMagazinePayload("Magazine A", 123, authorIds);

        APIResponse response = request.post(BASE_URL + "/magazines",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        String id = response.text().split("\"id\":")[1].split(",")[0].trim();
        createdMagazineIds.add(id);

        APIResponse getResponse = request.get(BASE_URL + "/magazines");
        assertEquals(200, getResponse.status());
        assertTrue(getResponse.text().contains("Magazine A"));
    }

    @Test
    void testInvalidMagazinePayload() {
        String payload = """
                    {
                      "issueNumber": 404,
                      "authorIds": []
                    }
                """; // Missing title and publicationDate

        APIResponse response = request.post(BASE_URL + "/magazines",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(400, response.status());
    }

    @Test
    void testAddMagazinesInBulk() {
        String authorId = createAuthorAndGetId("Bulk Magazine Author");
        String payload = String.format("""
                    [
                      {
                        "title": "Bulk Mag 1",
                        "publicationDate": "2021-01-01",
                        "issueNumber": 111,
                        "authorIds": [%s]
                      },
                      {
                        "title": "Bulk Mag 2",
                        "publicationDate": "2021-01-01",
                        "issueNumber": 112,
                        "authorIds": [%s]
                      }
                    ]
                """, authorId, authorId);

        APIResponse response = request.post(BASE_URL + "/magazines/bulk",
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        assertEquals(200, response.status());
        assertTrue(response.text().contains("Bulk Mag 1"));
        assertTrue(response.text().contains("Bulk Mag 2"));

        for (String item : response.text().split("\\{")) {
            if (item.contains("\"id\":")) {
                String id = item.split("\"id\":")[1].split(",")[0].trim();
                createdMagazineIds.add(id);
            }
        }
    }
}

