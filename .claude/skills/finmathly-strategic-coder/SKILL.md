---
name: finmathly-strategic-coder
description: Use when writing, changing, refactoring or reviewing the backend code of finmathly (registry and calculation services, Java/Spring) to program strategically, following the design in the README, and keep the code readable for human review.
---

# Strategic programming in finmathly

> This document is the result of a partnership between its author and AI. It was built over many iterations, in which the two studied each topic, drafted the text and revised it together. The author reviewed the final version in full.

This skill sets the mindset and the way of working on the backend of finmathly. The design itself is in the [README](../../../README.md).

- [1. Mindset](#1-mindset)
- [2. The README is the source of truth](#2-the-readme-is-the-source-of-truth)
- [3. How to work](#3-how-to-work)
  - [3.1 Before writing](#31-before-writing)
  - [3.2 When reviewing](#32-when-reviewing)
  - [3.3 When delivering](#33-when-delivering)

## 1. Mindset

AI tends to program tactically: it makes the current request work and moves on. In finmathly, the backend is programmed strategically: working code is necessary but not enough, and every change leaves the design a little better. The reasoning behind this is in [README 1](../../../README.md#1-approach-strategic-programming-with-ai).

A person reads every line of this code before it is committed, so the code must be easy to read and easy to change. Three (3) habits follow from that:

1. before writing, ask where the knowledge that the change uses lives, and put the behavior in the class that owns the data and the rules;
2. prefer a small change that a person can review in full;
3. never invent a business rule. If the README does not settle it, ask.

## 2. The README is the source of truth

This skill holds no rule of design, domain or architecture, so that each rule lives in one (1) place. Read the part of the README that a change touches before writing code, and follow it. If the code and the README differ, the README wins, unless the user decides otherwise.

The table shows where to read:

| To work on | Read in the README |
| --- | --- |
| Financial concepts and their names | [2. Business domain](../../../README.md#2-business-domain-fixed-income-mathematics) and the [glossary](../../../README.md#26-glossary) |
| Rounding, truncation and precision | [2.5.3](../../../README.md#253-rounding-and-truncation) and [5.1.3](../../../README.md#513-correctness) |
| The two (2) services and their synchronization | [3.3 CQRS](../../../README.md#33-cqrs) and [3.4 Transactional outbox](../../../README.md#34-transactional-outbox) |
| Authentication and authorization | [3.7](../../../README.md#37-authentication-and-authorization) |
| Formats of the API and the snapshots | [3.8](../../../README.md#38-formats) |
| Tables, snapshots and validations | [4. Data model](../../../README.md#4-data-model) |
| Quality requirements of the code | [5.1](../../../README.md#51-quality-requirements) |
| Symptoms and causes of complexity | [5.2](../../../README.md#52-complexity) |
| Principles: modules, names, functions, comments, errors, SOLID, simplicity, tests | [5.3](../../../README.md#53-principles) |
| Design patterns: Strategy, Repository, Adapter | [5.4](../../../README.md#54-design-patterns) |

## 3. How to work

### 3.1 Before writing

Follow these steps:

1. read the parts of the README that the change touches, and use their names;
2. name the quality attribute that the change serves;
3. check whether the change adds a symptom of complexity;
4. choose the simplest design that follows the principles of the README.

Everything in the code is written in English, names, comments, error messages and tests included, even when the user talks in another language.

### 3.2 When reviewing

Describe each problem through the five (5) steps of [README 1](../../../README.md#1-approach-strategic-programming-with-ai): the attribute it hurts, its symptom, the cause of that symptom, the principle that removes the cause, and the design pattern when one applies.

### 3.3 When delivering

Follow these steps:

1. explain the design decisions and their trade-offs in a few lines, naming the principle involved;
2. point out what was left out on purpose and any open question of a business rule;
3. when the change alters the data model, the architecture, the API or a message contract, update the README and the diagram sources in `docs/` in the same commit, and run `docs/render.sh`. Commits and pull requests follow the `finmathly-committer` skill.
