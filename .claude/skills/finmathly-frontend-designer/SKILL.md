---
name: finmathly-frontend-designer
description: Use when designing, building, changing or reviewing the finmathly frontend (React, TypeScript, Vite), to apply its visual identity, design tokens, voice and languages, and the rules for showing numbers, rates, tables, messages and screens.
---

# Designing the finmathly frontend

finmathly (always lowercase) is a reference system for Brazilian fixed-income securities, used by fixed-income analysts in banks. The frontend registers securities, modalities and indexes, and calculates prices. Its identity serves **precision** first, then **institutional trust**, then **clarity**.

This skill says how to apply three documents, which are the source of truth:

- `README.md`: the domain, the API, the data model and the **glossary** (section 1.6), which gives each concept one English name and one Portuguese name.
- `docs/visual-identity.md`: the visual identity, with the reason for each decision and the **values** of the tokens.
- `.claude/skills/finmathly-committer/SKILL.md`: how to commit. Frontend commits take the `frontend` scope.

Read the glossary and the visual identity before designing a screen. If this skill and those documents disagree, the documents win, and the skill must be fixed.

## Scope

- The frontend is a React application written in TypeScript and built with Vite.
- Work only in `frontend/`. Never change `backend/`.
- When the frontend needs something from the API, describe the missing part of the contract and stop to ask the maintainer.
- Ask before adding a dependency, and before making a visual decision that `docs/visual-identity.md` does not record.

## Tokens

- Every color, font, size and space comes from a token, written as a CSS custom property, such as `--color-accent` or `--font-mono`. No component uses a literal color or a literal font.
- The tokens are defined once for the light theme, on `:root`, and redefined with the same names for the dark theme. Components never know which theme is active.
- Styles are plain CSS on the tokens, with one CSS Module per component. No CSS framework is used.
- When the value of a token changes, `docs/visual-identity.md` changes in the same commit.

## Color

- There is one **accent color**, `--color-accent` ("cor de destaque"). Use it only for interaction and selection: links, the primary button, the selected tab, the active option of a selector, the focused field and the selected row. Never call it the "primary color".
- There are two **state colors**, `--color-error` and `--color-warning`. There is no success color: report a success with neutral text.
- Never let color carry meaning alone. A state color always comes with text and a border.
- Never show a premium, a discount or any other financial value in green or red. Write it, with its sign.
- Every pair of text and background meets WCAG AA (4.5:1). Check the contrast of any new pair in both themes.

## Typography

- Use IBM Plex Sans for all text and numbers, in three weights only: Regular (400) for text and numbers, Medium (500) for key figures and labels, and SemiBold (600) for titles and table headers.
- Use IBM Plex Mono only for three things: identifiers, such as the code of a security or a UUID; the decimal reference of a rate field, which shows a value in the API's format; and the `fm` of the logo. Never set in Mono a financial value shown for reading.
- Use tabular figures (`font-variant-numeric: tabular-nums`) for every number that can appear in a column.
- Keep to the type scale of `docs/visual-identity.md`. Build hierarchy from size, weight and position, not from color.

## Logo

- The logo is the fixed `fm` square of `docs/logo.svg`. Always use that file, never redraw or retype the logo. Its colors are the same in both themes, and it is the only element with literal colors.
- The logo is also the browser tab icon.
- Next to the logo, write the product name `finmathly`, always in lowercase, as text in IBM Plex Sans SemiBold, colored by `--color-ink` so that it follows the theme. Keep ligatures on, so that the `f` joins the `i`.

## Languages

- The interface exists in Brazilian Portuguese (`pt-BR`) and in English (`en-US`). The first visit follows the browser: Portuguese if its language starts with `pt`, and English otherwise. The PT | EN selector sits next to the Light | Dark selector, at the right of the top bar.
- All visible text comes from the message dictionaries, one per language. No component contains visible text.
- Every label uses the glossary term of its language. A new concept enters the glossary of the README before it enters the interface.
- The API returns codes, never translated text. Map every enum value to a label, such as `BUSINESS_252` to "Dias úteis sobre 252" or "Business days over 252", and never show the code itself. Map every error code, with its parameters, to a message in each language.
- Format numbers and dates with `Intl` for the selected language, never by hand. Pass each decimal to `Intl.NumberFormat` as a string, which it formats without turning it into a binary number.

## Themes

- There is a light theme and a dark theme. The first visit follows the operating system, and the Light | Dark selector changes it. There is no third "system" option.
- Design and check every screen in both themes.

## Numbers, rates and tables

- Show each value exactly as the API returns it. Never round, truncate or recalculate a value in the frontend; rounding belongs to the backend.
- Keep decimal values as strings from the API to the screen. Never pass an amount, a rate or a factor through a JavaScript `number`: it is a binary floating-point number and loses digits.
- Show and accept rates as percentages, with their period in the label: `% a.a.` in Portuguese and `% p.a.` in English. The period is always the year, because every rate in finmathly is an effective annual rate.
- Convert a typed percentage into the decimal sent to the API by moving the decimal point on the text, in a single place in the code. Under every rate field, show the result in Mono, in the API's format, whatever the language: `Decimal = 0.135000`.
- Align numeric columns to the right, and text and date columns to the left. Use a strong rule under the header and thin rules between rows, with no vertical lines and no alternating row colors.
- Never show a price alone: the unit price calculation shows the calculation details table, with the unit price as its total.

## Voice

- Write in a technical and direct voice, with the glossary terms. Use no exclamation marks, no apologies and no filler.
- An **error** says what happened, the data that explains it and, when one exists, the way around it: "Não há valor do IPCA para 15/10/2026. O último valor divulgado é de 30/09/2026. Calcule para 30/09/2026 ou uma data anterior."
- An **information**, a **warning** or a **confirmation** states the fact, with no way around it: "Título salvo. Versão 4."
- A **label** is the glossary term with its unit, and no explanation.
- Write actions in the imperative, without a pronoun: "Calcule", not "Você pode calcular".

## Messages and read-only data

- A sentence is a message, and values are read-only data. Never write values as a sentence, and never show a sentence as read-only data.
- Messages have three levels, in the forms set by `docs/visual-identity.md`: **information** (neutral, with no icon), **warning** (`--color-warning`) and **error** (`--color-error`). Never give an information message an icon: the ⓘ button belongs to concept tips.
- Use an information message only to explain a consequence, such as why a field is absent. Remove one that only repeats data already on the screen.
- Show values from another record, and values the user cannot change, as read-only data: each label above its value, on the same grid as the form's fields, with no input box.
- Give each label and each value of read-only data exactly the name and the value they have on the record's own screen. Read-only data from a modality uses the field names of the modality's record screen and shows each value as that screen shows it.
- A label of read-only data that is a glossary concept gets its concept tip, and an enum value is shown through its label.

## Concept tips

- Every glossary concept shown on a screen gets a concept tip: an ⓘ button that opens one or two sentences when it is hovered or tapped. Ordinary fields, such as a name, get none.
- The README is the source of every tip. The English tip follows the README's definition, and the Portuguese tip translates it faithfully.
- Keep the tips in one file, with one entry per concept: the English text, the Portuguese text and the README section that defines the concept.
- Whenever a change touches a concept, compare its tip with the README and update the tip in the same change. Before delivering a screen, check every tip on it against the README.

## Screens

- Each kind of record has a **list screen**, with a search field, a **New** button and a table where each row opens its record, and a **record screen**, used both for creating and for editing, with a **Save** button.
- What exists only inside another record is edited on its owner's record screen: the event schedule on the security's, and the index values on the index's.
- On the security's record screen, the modality decides which fields appear and which are required. Never show a field that the modality does not allow. When a modality is chosen, show its rules as read-only data, and explain each absent field with an information message.
- A modality that has securities is locked, and its record screen says so. The backend validates the rule itself.
- The unit price calculation shows what applies to the security: the par unit price and the unit price for a fixed-rate security with interest; the unit price, compared with the face value at issue, for a fixed-rate security without interest; and only the par unit price for floating-rate and hybrid securities. When a calculation does not apply, say why.

## Accessibility

- Give every interactive element a visible focus state in the accent color.
- Make interactive elements at least 32px high, and primary actions at least 40px.
- Give every field a label, and link every error to its field with `aria-describedby`.
- Respect `prefers-reduced-motion`.

## Code

- Apply the principles of `finmathly-strategic-coder` that are not specific to Java: precise names from the glossary, code that is readable for human review, KISS, YAGNI and explicit errors.
- Keep the access to the API behind a single data layer, so that the simulated data and the real API can be swapped without touching the screens.
- Write everything in the code in English.
