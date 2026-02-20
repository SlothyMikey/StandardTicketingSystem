# Ticketing API Agent Guide

## Stack
- Java 17
- Spring Boot 4
- Spring Data JPA
- PostgreSQL (Docker Compose)

## Architecture
Use layered architecture only:
- `controller`: REST endpoints
- `service`: business logic
- `repository`: Spring Data repositories
- `model`: JPA entities
- `dto`: request/response DTOs
- `config`: app config and seeders
- `exception`: custom exceptions and handlers

Do not create feature folders like `tickets/` that bypass these layers.

## Local Run
- App only: `.\mvnw.cmd spring-boot:run`
- Build: `.\mvnw.cmd -DskipTests package`
- Tests: `.\mvnw.cmd test`

## Docker Run
- Start: `docker compose up -d --build`
- Stop: `docker compose down`
- Logs: `docker compose logs -f`

Notes:
- Use `compose.yaml` as the source of truth.
- Postgres data is bind-mounted to `./.docker/postgre-data`.
- `.env` is required (copy from `.env.example`).

## Database/Seeding
- Datasource is configured via env vars:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- Seeder toggle:
  - `APP_SEED_ENABLED=true|false`
- Seed runs only when enabled and `tickets` table is empty.

## API Conventions
- Base path for tickets: `/api/tickets`
- Validate request DTOs with Jakarta Validation.
- Keep controller thin; move logic to service.
- Return:
  - `201 Created` for create
  - `204 No Content` for delete
  - `404 Not Found` when resource is missing

## Git/Repo Hygiene
- Never commit:
  - `.env`
  - `.docker/postgre-data/`
  - `target/`
- Keep changes scoped and minimal.
- Preserve existing style and naming.
