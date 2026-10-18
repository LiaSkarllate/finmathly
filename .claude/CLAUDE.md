# finmathly

A reference system for Brazilian fixed-income securities, made of a registry service and a calculation service. Read `README.md` first: it explains the domain, the system design, the data model and the software design, and the code is implemented against it.

## Layout

- `backend/`: Java, Spring Boot and Maven (`./mvnw`). The tests read the database settings from environment variables, as in `backend/src/test/resources/application.properties`.
- `frontend/`: React, TypeScript and Vite (`yarn dev`, `yarn build`).
- `docs/`: sources and generated images of the diagrams.
- `.claude/`: these instructions, the project settings and the skills in `.claude/skills/`.

## Rules

- Use `finmathly-committer` for every commit, and `finmathly-strategic-coder` when writing backend code.
- Everything is written in English: code, comments, commit messages and documentation.
- Work on short branches (`feat/...`, `fix/...`) and open a pull request to `dev`. `dev` goes to `main` only in releases. Never commit directly to `main`.
- The backend and the frontend are developed in separate sessions. A change to `README.md`, `docs/` or `.claude/` goes in its own pull request, for the maintainer to review.
- When the API or a message format changes, update `README.md` in the same commit. The snapshot and the index value messages must stay backward compatible.
- To change a diagram, edit its source in `docs/` and run `docs/render.sh`. Commit the source and the generated image together.
