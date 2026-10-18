---
name: finmathly-committer
description: Use whenever creating git commits in the finmathly repository, to decide how to split or group the changes into commits and to write each message in the Conventional Commits format with finmathly's types, scopes and wording rules.
---

# Committing in finmathly

This skill covers two decisions: how to divide the work into commits, and how to write the message of each one. Every message follows [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/). A good history lets a reader find when, what and why something changed without reading the diff, lets any single change be reverted on its own, and lets tools generate changelogs and versions.

## Splitting and grouping changes

### The rule

A commit is **one logical change**: the smallest change that makes sense on its own. Three tests decide whether a commit has the right size, and all three must pass:

1. **One sentence.** The header describes it without "and" joining two unrelated things.
2. **Reverts alone.** Reverting it undoes exactly one thing and does not break anything that should stay.
3. **Works alone.** After it, the project builds and every test passes. Nobody checking out this commit finds a broken state.

When the work in progress fails the first two tests, split it. When splitting it would fail the third, keep it together.

### Split when

- **The changes have different intents.** A refactoring and a new feature are two commits, even in the same file. Do the refactoring first, so the feature commit only adds behavior ("make the change easy, then make the easy change"). The same applies to a bug fix noticed along the way: it gets its own `fix` commit.
- **The changes touch different services.** The registry service and the calculation service are deployed separately, so a change to each is its own commit, and the description names the service when the scope alone does not.
- **Formatting is mixed with logic.** Reformatting, reordering imports or whitespace changes go in a `style` commit, so the logic diff stays readable.
- **A file is moved or renamed and also edited.** Move or rename it in one commit and edit it in the next, so git can follow the file's history.
- **Dependencies are upgraded.** A version bump goes in its own `build` commit, apart from the code that uses the new version, unless that code is needed for the build to pass.
- **Documentation describes something else.** A README change that explains a different subject from the code change is its own `docs` commit.

### Keep together when

- **Code and its tests.** Tests that verify a change go in the same commit as the change. A `test` commit is only for tests added to code that already exists.
- **A change and everything that keeps the project working.** Renaming a method and updating all its callers is one commit, because either half alone does not compile. The same holds for a JPA entity change and the database migration it needs; that commit takes the `backend` scope, and `database` is kept for migration-only changes.
- **A change and the documentation of that same change.** If a behavior changes, the README section or the comment that describes it changes in the same commit, so the documentation is never wrong at any point in history. The same holds for a diagram: its source in `docs/` and its generated image go in the same commit.
- **A change and its own corrections before it is pushed.** Typos and small fixes to a commit that has not been pushed are folded into it (amend or squash) instead of becoming "fix typo" commits.

### Order

- Refactoring before the feature that needs it.
- For a contract change between the services, the side that must accept the new format first goes first, and every change stays backward compatible, so each commit can be deployed on its own.
- Never rewrite commits that were already pushed to a shared branch; add a new commit instead.

### How to do it

- Read `git status` and `git diff` first and list the logical changes in the work.
- Stage each logical change separately, by file or by hunk, and check it with `git diff --staged` before committing. The message must describe exactly what is staged, nothing more and nothing less.
- If one file holds two logical changes, split it by hunks; if that is not possible, reconsider whether they are really separate.
- Never commit secrets, credentials, `.env` files or generated build output. The diagram images in `docs/` are the exception: they are generated, but they are committed together with their source.

## Message structure

```
<type>(<scope>)<!>: <description>

<body>

<footer>
```

- The **header** is required and is a single line. Body and footer are optional, each separated by one blank line.
- Everything is written in **English**.
- Each part has one job. The header says **what** changed, the body says **why**, and the footer carries breaking changes and references. The pull request description tells the story of the whole set of commits and how to check it.

### Type

| Type | Use for |
| --- | --- |
| `feat` | A new behavior visible to users or clients of the API |
| `fix` | A bug fix |
| `refactor` | A change of structure that keeps behavior (renames, extractions, moving code) |
| `test` | Adding or correcting tests only |
| `docs` | Documentation only, including README.md and comments |
| `perf` | A performance improvement, measured before and after |
| `build` | Build system and dependencies (Maven, npm) |
| `ci` | Continuous integration configuration |
| `chore` | Maintenance that fits no other type (tooling, skills, repository settings) |
| `revert` | Reverting a previous commit |
| `style` | Formatting only, with no change in meaning |

- Choose by the **main intent** of the change. A refactoring that also fixes a bug is a `fix`, with the refactoring explained in the body.

### Scope

The scope names the part of finmathly that changed:

| Scope | Part |
| --- | --- |
| `backend` | The back-end code: the registry service (write side) and the calculation service (read side) |
| `frontend` | React front-end |
| `database` | Database migrations, schema and extensions |
| `infra` | Terraform and AWS configuration |
| `contract` | The snapshot JSON and index value message contracts shared by both services |
| `skills` | The skills in `.claude/skills/` |

- Omit the scope when the change spans the whole project, for example `docs: ...` for README.md.
- Use one scope. If a change needs two scopes, it is probably two commits.

### Description

- **Imperative mood**, as if completing "If applied, this commit will...": `add`, `fix`, `rename`, not `added` or `adds`.
- Starts with a lowercase letter, has no final period.
- At most 72 characters in the whole header. If the header cannot say what changed without "and" joining unrelated things, the commit is probably too big and should be split.
- Says **what** changed, in domain language. Use the concept names from the README (`par unit price`, `day count convention`, `outstanding face value`).

### Body

- Explains **why**: the cause of a bug, the constraint or decision behind the change, the trade-off, and what was left out on purpose. Never explain **how**: the diff shows that.
- **Write a body** for a `fix` whose cause is not obvious from the header, for a change of behavior, and whenever a decision or trade-off would otherwise be lost.
- **Skip the body** when the header is enough: a rename, formatting, a typo, a dependency bump, or tests that only add coverage.
- Keep it short: a few lines of plain prose, wrapped at 72 characters. Do not repeat the header and do not list files.
- Mention the design principle when it drove the change, briefly (for example "keeps behavior with its data").

### Footer

- `BREAKING CHANGE: <what breaks and how to migrate>` when the change breaks a public API (`/api/securities`, `/api/calculations`). Also add `!` after the type or scope in the header.
- The snapshot JSON and the index value messages must stay backward compatible, so a change to them is not breaking; do not use `BREAKING CHANGE` to get around that rule.
- `Refs: #<issue>` when an issue exists.
- Attribution trailers (such as `Co-Authored-By:`) go last, only if the environment requires them. The project settings currently turn them off.

## Authorship

- Commits use the author's local git identity, so they appear under the author's name.
- Never change the git identity configured by the environment.

## Examples

```
feat(backend): calculate the par unit price for any security class
```

```
fix(backend): resolve the transient asset reference in FlowService

Flow.asset arrived as a stand-in built from a DTO that only has the asset
name, so creating a flow by asset name threw TransientPropertyValueException.
The service now loads the persisted Asset before saving.
```

```
refactor(backend): move event effects into CashFlowState

Each EventType value only called back into the state, which forced the
state to expose its operations. The switch now lives next to the data,
and the operations are private.
```

```
test(backend): cover Flow negative paths in controller tests
```

```
docs: explain the transactional outbox in the README
```

```
refactor(backend)!: rename unitPrice to price in the calculation response

BREAKING CHANGE: clients of /api/calculations must read price instead of
unitPrice.
```
