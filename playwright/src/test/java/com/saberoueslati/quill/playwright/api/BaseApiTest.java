package com.saberoueslati.quill.playwright.api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    static Playwright playwright;
    static APIRequestContext request;
    static final String BASE_URL = System.getProperty("playwright.base.url", 
        System.getenv().getOrDefault("PLAYWRIGHT_BASE_URL", "http://localhost:8080/api"));

    @BeforeAll
    static void globalSetup() {
        playwright = Playwright.create();
        request = playwright.request().newContext();
    }

    @AfterAll
    static void globalTeardown() {
        request.dispose();
        playwright.close();
    }
}
