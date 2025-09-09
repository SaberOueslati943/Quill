# Quill Playwright Tests

This directory contains end-to-end tests for the Quill application using Playwright.

## Structure

```
playwright/
├── src/test/java/com/saberoueslati/quill/playwright/
│   ├── api/                    # API tests
│   │   ├── BaseApiTest.java    # Base class for API tests
│   │   ├── AuthorApiTest.java  # Author API tests
│   │   ├── BookApiTest.java    # Book API tests
│   │   ├── MagazineApiTest.java # Magazine API tests
│   │   └── PublicationApiTest.java # Publication API tests
│   └── ui/                     # UI tests
│       └── PublicationsScreenTest.java # UI tests for publications screen
├── build.gradle                # Gradle build configuration
├── Dockerfile                  # Docker configuration
├── package.json               # Node.js dependencies (for Playwright CLI)
├── playwright.config.js       # Playwright configuration
└── README.md                  # This file
```

## Running Tests

### Local Development

1. **Prerequisites**: Make sure the backend and frontend are running
   ```bash
   # From the project root
   docker-compose up -d backend frontend
   ```

2. **Run all tests**:
   ```bash
   cd playwright
   ./gradlew test
   ```

3. **Run specific test types**:
   ```bash
   # API tests only
   ./gradlew apiTest
   
   # UI tests only
   ./gradlew uiTest
   ```

4. **Run with different browsers**:
   ```bash
   # Run with Chrome (default)
   ./gradlew test -Dplaywright.browser=chromium
   
   # Run with Firefox
   ./gradlew test -Dplaywright.browser=firefox
   
   # Run with Safari/WebKit
   ./gradlew test -Dplaywright.browser=webkit
   ```

5. **Run in headed mode (see browser)**:
   ```bash
   ./gradlew test -Dplaywright.headless=false
   ```

### Docker (Recommended)

1. **Run tests in Docker**:
   ```bash
   # From the project root
   docker-compose --profile testing up playwright
   ```

2. **Run specific test types in Docker**:
   ```bash
   # API tests only
   docker-compose --profile testing run playwright ./gradlew apiTest
   
   # UI tests only
   docker-compose --profile testing run playwright ./gradlew uiTest
   ```

3. **Run with custom configuration**:
   ```bash
   docker-compose --profile testing run playwright ./gradlew test -Dplaywright.browser=firefox
   ```

## Test Types

### API Tests
- Test REST API endpoints directly
- Verify response codes, data structure, and content
- Located in `src/test/java/com/saberoueslati/quill/playwright/api/`

### UI Tests
- Test the web interface through browser automation
- Verify user interactions and visual elements
- Located in `src/test/java/com/saberoueslati/quill/playwright/ui/`

## Configuration

### Environment Variables
- `PLAYWRIGHT_BASE_URL`: Backend API URL (default: http://localhost:8080)
- `PLAYWRIGHT_FRONTEND_URL`: Frontend URL (default: http://localhost:80)

### System Properties
- `playwright.browser`: Browser to use (chromium, firefox, webkit)
- `playwright.headless`: Run in headless mode (true/false)
- `playwright.base.url`: Override base URL for API tests
- `playwright.frontend.url`: Override frontend URL for UI tests

## CI/CD Integration

The tests are designed to run in CI/CD pipelines:

```yaml
# Example GitHub Actions workflow
- name: Run Playwright Tests
  run: |
    docker-compose --profile testing up playwright
```

## Troubleshooting

1. **Tests fail with connection errors**: Ensure backend and frontend are running
2. **Browser not found**: Install Playwright browsers: `npx playwright install`
3. **UI tests fail**: Check if frontend is accessible at the configured URL
4. **API tests fail**: Verify backend API is running and accessible

## Adding New Tests

1. **API Tests**: Extend `BaseApiTest` class
2. **UI Tests**: Create new test class in `ui/` package
3. **Use proper naming**: Follow existing patterns (e.g., `*ApiTest.java`, `*ScreenTest.java`)
