# Quill

A full-stack demo application built with a Spring Boot backend (Java 21), an Angular frontend (Angular 20) served by Nginx, and PostgreSQL for persistence. End-to-end/API/UI tests are implemented with Playwright and separated into their own module.

## Project Structure

```
Quill/
├── backend/           # Spring Boot (Java 21, Gradle)
├── frontend/          # Angular 20 app (built and served by Nginx container)
├── playwright/        # Playwright API/UI tests (Java + Playwright browsers)
├── docker-compose.yml # Base orchestration for all environments
├── docker-compose.dev.yml      # Dev environment overrides
├── docker-compose.staging.yml  # Staging environment overrides
├── docker-compose.prod.yml     # Production environment overrides
```

## Services
- Backend: Spring Boot on port 8080, connects to Postgres
- Frontend: Nginx on port 80, proxies `/api` to backend
- Database: Postgres 15 (port 5432, volume `postgres_data`)
- Tests: Playwright (runs against frontend/backend)

## Prerequisites
- Docker and Docker Compose
- macOS users using Colima: start it first
  ```bash
  colima start
  ```

## Quick Start (Docker)

1) Build and run all services (default: dev environment)
```bash
# From the repo root
# Dev environment
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up --build -d

# Staging environment
# docker-compose -f docker-compose.yml -f docker-compose.staging.yml up --build -d

# Production environment
# docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build -d
```

2) Open the app
- Frontend: http://localhost
- Backend API: http://localhost:8080/api

3) View logs
```bash
docker-compose logs -f          # all services
# or
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres
```

4) Stop everything
```bash
docker-compose down
```

## Rebuild a single service
```bash
# Backend
docker-compose build backend && docker-compose up -d backend

# Frontend
docker-compose build frontend && docker-compose up -d frontend
```

## Local Development (without Docker)

- Backend
```bash
cd backend
./gradlew bootRun
```
- Frontend
```bash
cd frontend
npm install
npm run start
```

## Playwright Tests
Tests are separated under `playwright/` and can be run locally or via Docker.

### Run Tests Locally
Make sure backend and frontend are running (via Docker or locally):
```bash
# Ensure services are up
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up -d backend frontend

# Run API tests
cd playwright
./gradlew apiTest

# Run UI tests
./gradlew uiTest

# Run all tests
./gradlew test
```

Options:
```bash
# Run in headed mode
./gradlew test -Dplaywright.headless=false

# Choose browser: chromium (default), firefox, webkit
./gradlew test -Dplaywright.browser=firefox
```

### Run Tests via Docker (CI-friendly)
```bash
# Build and run the Playwright test image
docker-compose --profile testing up --build playwright

# Or run specific suites
docker-compose --profile testing run --rm playwright ./gradlew apiTest
docker-compose --profile testing run --rm playwright ./gradlew uiTest
```

Note: UI tests in Docker should run headless; the provided configuration defaults to headless in CI. If you need headed mode in Docker, run them under Xvfb or switch to a Playwright base image with X support.

## Allure Reporting

Allure results are generated in an environment-specific directory: `playwright/allure-results/<environment>`.

- The environment is set via the `TEST_ENV` environment variable (e.g., `dev`, `staging`, `prod`).
- By default, if `TEST_ENV` is not set, results go to `allure-results/dev`.
- You can set `TEST_ENV` in your Docker Compose override files or as an environment variable when running tests.

### Example: Run Playwright tests with Allure results for staging
```bash
# Set environment variable
export TEST_ENV=staging

# Run tests
./gradlew test

# Results will be in playwright/allure-results/staging
```

### Generate and view Allure report
1. Install Allure CLI (if not already installed):
   ```bash
   npm install -g allure-commandline --save-dev
   # or
   brew install allure
   ```
2. Generate the report:
   ```bash
   allure generate playwright/allure-results/<environment> -o playwright/allure-report/<environment> --clean
   ```
3. Open the report in your browser:
   ```bash
   allure open playwright/allure-report/<environment>
   ```

You can repeat this for each environment to keep reports separated and organized.

## Environment Configuration
- Backend DB connection and other secrets are injected via Docker Compose env vars and Spring profiles:
  - `application-dev.properties`, `application-staging.properties`, `application-prod.properties` in `backend/src/main/resources`
  - Select profile with `SPRING_PROFILES_ACTIVE` (set in Compose override files)
- Frontend proxies `/api` to the backend container name `backend`.
- Playwright tests accept env vars:
  - `PLAYWRIGHT_BASE_URL` (default: `http://localhost:8080/api`)
  - `PLAYWRIGHT_FRONTEND_URL` (default: `http://localhost`)

## Troubleshooting
- Docker daemon not running (macOS + Colima): `colima start`
- Port conflicts: stop local apps using 80/8080/5432 or change mappings in `docker-compose.yml`.
- Frontend shows Nginx default page: ensure the Angular build copied to `/usr/share/nginx/html` (Dockerfile handles this).
- Playwright in Docker complaining about X server: ensure tests are headless (default) or use Xvfb.

## License
MIT
