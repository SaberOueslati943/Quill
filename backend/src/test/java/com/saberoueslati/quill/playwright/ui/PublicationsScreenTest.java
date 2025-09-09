package com.saberoueslati.quill.playwright.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PublicationsScreenTest {

    static Playwright playwright;

    @BeforeAll
    static void init() {
        playwright = Playwright.create();
    }

    @AfterAll
    static void close() {
        playwright.close();
    }

    @Test
    void testSearchOnAllBrowsers() {
        List<BrowserType> browsers = List.of(
                playwright.chromium(),
                playwright.firefox(),
                playwright.webkit()
        );

        for (BrowserType browserType : browsers) {
            try (Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                Page page = browser.newPage();
                page.navigate("http://localhost:8080/web/publications");

                // Search for a known title
                page.fill("input[name='title']", "1984");
                page.click("button[type='submit']");

                // Check if the result appears
                boolean containsResult = page.locator("table").innerText().toLowerCase().contains("1984");
                Assertions.assertTrue(containsResult, "Result not found in " + browserType.name());
            }
        }
    }

    @Test
    void testSearchOnAllBrowsersForNoExistantItem() {
        List<BrowserType> browsers = List.of(
                playwright.chromium(),
                playwright.firefox(),
                playwright.webkit()
        );

        for (BrowserType browserType : browsers) {
            try (Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                Page page = browser.newPage();
                page.navigate("http://localhost:8080/web/publications");

                // Search for a known title
                page.fill("input[name='title']", "12 Rules For Life");
                page.click("button[type='submit']");

                // Check if the result appears
                boolean doesNotContainResult = page.locator("table").innerText().toLowerCase().contains("12 rules for life");
                Assertions.assertFalse(doesNotContainResult, "Result not found in " + browserType.name());
            }
        }
    }
}
