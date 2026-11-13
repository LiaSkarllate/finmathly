---
name: finmathly-visual-identity-designer
description: Use when designing, building, changing or reviewing the finmathly frontend (React, TypeScript, Vite). Holds the finmathly visual identity, its design tokens, voice and languages, and the rules for showing numbers, rates, tables, messages and screens.
---

# finmathly visual identity

finmathly (always lowercase) is a reference system for Brazilian fixed-income securities, used by fixed-income analysts in banks. The frontend registers securities, modalities and indexes, and calculates prices. Its identity serves **precision** first, then **institutional trust**, then **clarity**.

This skill is the only place in the repository where the visual identity is recorded. It has four files:

| File | What it holds |
| --- | --- |
| `SKILL.md` | How to work with the identity, and the rules that are never broken |
| [`foundations.md`](foundations.md) | Audience, references, color and its tokens, typography, shape and space, logo, themes |
| [`content.md`](content.md) | Languages, numbers and tables, voice, concept tips, messages, read-only data, names of the design |
| [`screens.md`](screens.md) | List and record screens, the security's record screen, the unit price calculation and its calculation details |

Two other documents complete it: the **glossary** of `README.md` (section 1.6), which gives each domain concept one English and one Portuguese name, and `.claude/skills/finmathly-committer/SKILL.md`, for commits, where frontend commits take the `frontend` scope.

## How to work

1. Before designing or building a screen, read the glossary and the files of this skill that the work touches.
2. Build from the tokens and the rules recorded here. A visual decision that is not recorded here is asked to the maintainer before it is made, and then recorded here.
3. When a decision changes, change it here, in the file that holds it, in the same change as the code. Every decision lives in one file only.
4. Before delivering, check the screen in both themes and both languages, and check every concept tip on it against the README.

## Scope

- The frontend is a React application written in TypeScript and built with Vite. Work only in `frontend/`; never change `backend/`.
- When the frontend needs something from the API, describe the missing part of the contract and stop to ask the maintainer.
- Ask before adding a dependency.

## Rules that are never broken

- **No literal colors or fonts in components.** Every color, font, size and space comes from a token, written as a CSS custom property. The logo file is the only exception. Styles are plain CSS on the tokens, with one CSS Module per component, and no CSS framework.
- **The accent color is only for interaction and selection.** It is the "accent color", never the "primary color".
- **Color never carries meaning alone, and never financial meaning.** A state color always comes with text and a border; a premium or a discount is written, with its sign, never in green or red.
- **No financial value is set in Mono.** Mono is only for identifiers, the decimal reference and the `fm` of the logo.
- **No visible text in components.** All text comes from the message dictionaries, and every label uses the glossary term of its language. An enum code is never shown.
- **No value is rounded, truncated or recalculated in the frontend.** Decimals stay strings from the API to the screen and never pass through a JavaScript `number`.
- **No price without its calculation details.**
- **A sentence is a message, and values are read-only data.** An information message never has an icon: the ⓘ belongs to concept tips.
- **Every interactive element has a visible focus state** in the accent color, every field has a label, and `prefers-reduced-motion` is respected.

## Code

- Apply the principles of `finmathly-strategic-coder` that are not specific to Java: precise names from the glossary, code that is readable for human review, KISS, YAGNI and explicit errors.
- Keep the access to the API behind a single data layer, so that the simulated data and the real API can be swapped without touching the screens.
- Write everything in the code in English.
