# finmathly

A reference system for Brazilian fixed-income securities, made of a registry service and a calculation service. Read `README.md` first: it explains the domain, the system design, the data model and the software design, and the code is implemented against it.

## Layout

- `backend/`: Java, Spring Boot and Maven (`./mvnw`). The tests read the database settings from environment variables, as in `backend/src/test/resources/application.properties`.
- `frontend/`: React, TypeScript and Vite (`yarn dev`, `yarn build`).
- `docs/`: sources and generated images of the diagrams.
- `.claude/`: these instructions, the project settings and the skills in `.claude/skills/`.
