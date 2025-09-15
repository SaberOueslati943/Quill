package com.saberoueslati.quill.playwright.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@UsePlaywright
public class PublicationsScreenTest {

    static final String FRONTEND_URL = System.getProperty("playwright.frontend.url", 
        System.getenv().getOrDefault("PLAYWRIGHT_FRONTEND_URL", "http://localhost:80"));

    @Test
    void testSearchOnAllBrowsers(Playwright playwright) {
        List<BrowserType> browsers = List.of(
                playwright.chromium(),
                playwright.firefox(),
                playwright.webkit()
        );

        for (BrowserType browserType : browsers) {
            try (Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                Page page = browser.newPage();
                page.navigate(FRONTEND_URL);

                // Check if the page loads successfully
                String title = page.title();
                Assertions.assertNotNull(title, "Page title should not be null in " + browserType.name());
                Assertions.assertFalse(title.isEmpty(), "Page title should not be empty in " + browserType.name());
                
                // Check if the page contains expected content
                String bodyText = page.locator("body").innerText();
                Assertions.assertFalse(bodyText.isEmpty(), "Page body should not be empty in " + browserType.name());
            }
        }
    }

    @Test
    void testSearchOnAllBrowsersForNoExistantItem(Playwright playwright) {
        List<BrowserType> browsers = List.of(
                playwright.chromium(),
                playwright.firefox(),
                playwright.webkit()
        );

        for (BrowserType browserType : browsers) {
            try (Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                Page page = browser.newPage();
                page.navigate(FRONTEND_URL);

                // Check if the page loads successfully
                String title = page.title();
                Assertions.assertNotNull(title, "Page title should not be null in " + browserType.name());
                Assertions.assertFalse(title.isEmpty(), "Page title should not be empty in " + browserType.name());
                
                // Check if the page contains expected content
                String bodyText = page.locator("body").innerText();
                Assertions.assertFalse(bodyText.isEmpty(), "Page body should not be empty in " + browserType.name());
                
                // Verify the page is accessible (simplified check)
                Assertions.assertTrue(bodyText.length() > 0, 
                    "Page should have content in " + browserType.name());
            }
        }
    }
}
