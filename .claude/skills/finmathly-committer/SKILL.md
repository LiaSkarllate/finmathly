---
name: finmathly-committer
description: Use whenever creating a branch or a git commit, or opening or editing a pull request, in the finmathly repository, to decide how to name branches, to split or group the changes into commits, to write each message in the Conventional Commits format with finmathly's types, scopes and wording rules, and to write the title and description of each pull request.
---

# Commits and pull requests in finmathly

> This document is the result of a partnership between its author and AI. It was built over many iterations, in which the two studied each topic, drafted the text and revised it together. The author reviewed the final version in full.

The history of a repository is documentation: it tells when, what and why something changed. This skill explains how finmathly writes that history, from the reasoning behind a good history down to the rules of each commit and pull request. It can be read from start to end, and each section can also be read on its own.

- [1. Approach: the history as documentation](#1-approach-the-history-as-documentation)
- [2. Quality requirements](#2-quality-requirements)
- [3. Symptoms and causes](#3-symptoms-and-causes)
- [4. Principles](#4-principles)
  - [4.1 One logical change per commit](#41-one-logical-change-per-commit)
  - [4.2 When to split](#42-when-to-split)
  - [4.3 When to keep together](#43-when-to-keep-together)
  - [4.4 Order of the commits](#44-order-of-the-commits)
  - [4.5 How to split in practice](#45-how-to-split-in-practice)
  - [4.6 Each part of a message has one job](#46-each-part-of-a-message-has-one-job)
- [5. Conventional Commits](#5-conventional-commits)
  - [5.1 Type](#51-type)
  - [5.2 Scope](#52-scope)
  - [5.3 Description](#53-description)
  - [5.4 Body](#54-body)
  - [5.5 Footer](#55-footer)
  - [5.6 Examples](#56-examples)
- [6. Pull requests](#6-pull-requests)
  - [6.1 Branches](#61-branches)
  - [6.2 Title](#62-title)
  - [6.3 Description](#63-description)
  - [6.4 Merge](#64-merge)

## 1. Approach: the history as documentation

Code shows what the system does now. Only the history shows how it got there and why. A person who finds a strange line asks who changed it, when and for what reason, and a good history answers without anyone having to read the whole diff. It also lets a single change be reverted on its own, and it lets tools generate changelogs and version numbers.

AI tends to commit as it programs: it finishes a request and records everything in one (1) commit, with a message that repeats the diff. Each commit like that makes the history harder to read. This skill counters that tendency with the same five (5) steps that the [README](../../../README.md#1-approach-strategic-programming-with-ai) uses for good design:

1. the [quality attribute](#2-quality-requirements) that is wanted;
2. the [symptom](#3-symptoms-and-causes) that shows the attribute is missing;
3. the [cause](#3-symptoms-and-causes) that produces the symptom;
4. the [principle](#4-principles) that removes the cause;
5. the [convention](#5-conventional-commits) that offers a proven way to apply the principle.

The person behind the AI stays in control: commits and pull requests are made only when that person asks, under the git identity configured by the environment, which is never changed.

Everything in the repository is written in English: code, comments, commit messages, pull requests and documentation, even when the user talks in another language.

## 2. Quality requirements

A good history has three (3) attributes:

1. traceability, which means that a reader can find when, what and why something changed;
2. reversibility, which means that one (1) change can be undone with [`git revert`↗](https://git-scm.com/docs/git-revert) without undoing anything else;
3. integrity, which means that every commit leaves the project working, so that anyone can check out any commit, and [`git bisect`↗](https://git-scm.com/docs/git-bisect) can search the history for the commit that introduced a bug.

## 3. Symptoms and causes

The absence of these attributes shows through three (3) symptoms: (i) a reader needs the diff to learn what a commit did; (ii) reverting a commit also removes something that should stay; and (iii) a commit in the middle of the history does not build or fails its tests.

The symptoms have two (2) causes: (i) mixed commits, which join changes with different intents; and (ii) empty messages, which repeat the diff and leave out the reason.

## 4. Principles

### 4.1 One logical change per commit

A commit is one (1) logical change: the smallest change that makes sense on its own. This principle removes the cause of mixed commits. Three (3) tests decide whether a commit has the right size, and all of them must pass:

1. one (1) sentence: the header describes it without "and" joining two (2) unrelated things;
2. reverts alone: reverting it undoes exactly one (1) thing and breaks nothing that should stay;
3. works alone: after it, the project builds and every test passes.

When the work in progress fails the first two (2) tests, split it. When splitting it would fail the third, keep it together.

### 4.2 When to split

Split the changes when:

1. they have different intents. A refactoring and a new feature are two (2) commits, even in the same file. The refactoring comes first, so the feature commit only adds behavior, as in the advice of Kent Beck: make the change easy, then make the easy change. A bug fix noticed along the way gets its own `fix` commit;
2. they touch different services. The registry service and the calculation service are deployed separately, so a change to each is its own commit, and the description names the service when the scope alone does not;
3. formatting is mixed with logic. Reformatting, reordering imports and whitespace changes go in a `style` commit, so that the diff of the logic stays readable;
4. a file is moved or renamed and also edited. Move or rename it in one (1) commit and edit it in the next, so that git can follow its history;
5. dependencies are upgraded. A version bump goes in its own `build` commit, apart from the code that uses the new version, unless that code is needed for the build to pass;
6. documentation describes something else. A README change about a different subject from the code change is its own `docs` commit.

### 4.3 When to keep together

Keep the changes together when they are:

1. code and its tests. Tests that verify a change go in the same commit as the change, and a `test` commit is only for tests added to code that already exists;
2. a change and everything that keeps the project working. Renaming a method and updating its callers is one (1) commit, because either half alone does not compile. The same holds for a JPA entity change and the database migration it needs, which takes the `backend` scope, while `database` is kept for changes made only of migrations;
3. a change and the documentation of that same change. If a behavior changes, the README section or the comment that describes it changes in the same commit, so that the documentation is never wrong at any point in the history. A diagram follows the same rule: its source in `docs/` and its generated image go in the same commit;
4. a change and its own corrections before it is pushed. Typos and small fixes to a commit not yet pushed are folded into it with an amend, instead of becoming "fix typo" commits.

### 4.4 Order of the commits

The commits follow three (3) rules:

1. a refactoring comes before the feature that needs it;
2. in a change to the contract between the services, the side that must accept the new format goes first, and every change stays backward compatible, so that each commit can be deployed on its own;
3. commits already pushed to a shared branch are never rewritten, so a correction is a new commit.

### 4.5 How to split in practice

The work follows these steps:

1. read `git status` and `git diff`, and list the logical changes;
2. stage each logical change separately, by file or by hunk;
3. check it with `git diff --staged`, because the message must describe exactly what is staged, nothing more and nothing less;
4. if one (1) file holds two (2) logical changes, split it by hunks. If that is not possible, reconsider whether they are really separate.

Secrets, credentials, `.env` files and generated build output are never committed. The diagram images in `docs/` are the exception: they are generated, but they are committed together with their source.

### 4.6 Each part of a message has one job

This principle removes the cause of empty messages. The header says what changed, the body says why, and the footer carries breaking changes and references. The pull request description tells the story of the whole set of commits and how it was checked.

## 5. Conventional Commits

[Conventional Commits 1.0.0↗](https://www.conventionalcommits.org/en/v1.0.0/) is a specification for commit messages. People and tools can both read its fixed format, which maps to [Semantic Versioning↗](https://semver.org/). Every message of finmathly follows it:

```
<type>(<scope>)<!>: <description>

<body>

<footer>
```

The header is required and is a single line. The body and the footer are optional, each separated by one (1) blank line.

### 5.1 Type

The type states the main intent of the change:

| Type | Use for |
| --- | --- |
| `feat` | A new behavior visible to users or clients of the API |
| `fix` | A bug fix |
| `refactor` | A change of structure that keeps behavior, such as a rename, an extraction or moved code |
| `test` | Adding or correcting tests only |
| `docs` | Documentation only, including `README.md` and comments |
| `perf` | A performance improvement, measured before and after |
| `build` | Build system and dependencies (Maven, npm) |
| `ci` | Continuous integration configuration |
| `chore` | Maintenance that fits no other type, such as tooling, skills and repository settings |
| `revert` | Reverting a previous commit |
| `style` | Formatting only, with no change in meaning |

A refactoring that also fixes a bug is a `fix`, with the refactoring explained in the body.

### 5.2 Scope

The scope names the part of finmathly that changed:

| Scope | Part |
| --- | --- |
| `backend` | The backend code: the registry service (write side) and the calculation service (read side) |
| `frontend` | The React frontend |
| `database` | Database migrations, schema and extensions |
| `infra` | Terraform and AWS configuration |
| `contract` | The snapshot JSON and index value message contracts shared by both services |
| `skills` | The skills in `.claude/skills/` |

The scope is omitted when the change spans the whole project, as in `docs: ...` for `README.md`. A commit uses one (1) scope, because a change that needs two (2) is probably two (2) commits.

### 5.3 Description

The description says what changed, in the language of the domain, with the names of the [README](../../../README.md#26-glossary), such as `par unit price` or `day count convention`. It follows four (4) rules:

1. it uses the imperative mood, as if completing "If applied, this commit will...": `add`, `fix`, `rename`, and not `added` or `adds`;
2. it starts with a lowercase letter;
3. it has no final period;
4. the whole header has at most 72 characters.

A header that cannot say what changed without "and" joining unrelated things usually belongs to a commit that is too big.

### 5.4 Body

The body explains why: the cause of a bug, the constraint or decision behind the change, the trade-off, and what was left out on purpose. It never explains how, because the diff shows that.

A commit has a body when it is a `fix` whose cause is not obvious from the header, when it changes behavior, or when a decision or trade-off would otherwise be lost. It has none when the header is enough, as in a rename, a formatting change, a typo, a dependency bump, or tests that only add coverage.

The body is a few lines of plain prose, wrapped at 72 characters. It does not repeat the header or list files, and it mentions the design principle when that principle drove the change, as in "keeps behavior with its data". Its text is revised with the `rewriter` skill and then with the `stop-slop` skill, keeping every fact. The header does not need them.

### 5.5 Footer

The footer holds three (3) kinds of line:

1. `BREAKING CHANGE: <what breaks and how to migrate>`, when the change breaks a public API of the registry service or the calculation service, together with `!` after the type or scope in the header;
2. `Refs: #<issue>`, when an issue exists;
3. attribution trailers, such as `Co-Authored-By:`, which go last and only if the environment requires them. The project settings currently turn them off.

The snapshot JSON and the index value messages must stay backward compatible, so a change to them is not breaking, and `BREAKING CHANGE` is never a way around that rule.

### 5.6 Examples

A header is enough for a new behavior:

```
feat(backend): calculate the par unit price for any security category
```

A fix whose cause is not obvious explains it in the body:

```
fix(backend): resolve the transient security reference in EventService

Event.security arrived as a stand-in built from a DTO that only has the
security code, so creating an event threw TransientPropertyValueException.
The service now loads the persisted Security before saving.
```

A refactoring records the design reason:

```
refactor(backend): move event effects into PaymentScheduleState

Each EventType value only called back into the state, which forced the
state to expose its operations. The switch now lives next to the data,
and the operations are private.
```

Tests and documentation usually need only a header:

```
test(backend): cover the rejected events in the controller tests
```

```
docs: explain the transactional outbox in the README
```

A breaking change uses `!` and the footer:

```
refactor(backend)!: rename unitPrice to price in the calculation response

BREAKING CHANGE: clients of the calculation API must read price instead
of unitPrice.
```

## 6. Pull requests

A pull request tells the story of a set of commits, for the person who decides whether to merge it. In finmathly, each short branch goes to `dev` through a pull request, and `dev` goes to `main` only in releases, as explained in [README 3.6](../../../README.md#36-cicd).

### 6.1 Branches

Work happens on a short branch created from `dev`, never directly on `dev` or `main`. Its name starts with the type of the change and says what is specific to finmathly, as in `feat/par-unit-price` or `chore/refine-finmathly-skills`. A change to `README.md`, `docs/` or `.claude/` goes in its own branch and pull request, apart from code.

### 6.2 Title

The title follows the format of a commit header, in Conventional Commits, as explained in [5](#5-conventional-commits), such as `docs: revise the README and the architecture diagrams`.

### 6.3 Description

The description is simple Markdown: short paragraphs and bulleted or numbered lists, with no headings, tables or diagrams. It says what changes and why, how the change was checked, and anything the reviewer must know before merging. It does not list every commit or file. Its text is revised with the `rewriter` skill and then with the `stop-slop` skill, keeping every fact.

### 6.4 Merge

A pull request is merged with a merge commit and is never squashed, so that its commits stay in the history. Its source branch is deleted after the merge, except `dev` and `main`.
