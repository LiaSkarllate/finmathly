# Visual identity

This document records the visual identity of the finmathly frontend: who the interface is for, what it should convey, and each decision that follows, with its reason. The names of the domain concepts come from the [glossary](../README.md#16-glossary) of the README. The rules for applying this identity in code are in the skill [finmathly-frontend-designer](../.claude/skills/finmathly-frontend-designer/SKILL.md).

## 1. Audience and purpose

finmathly is used by **fixed-income analysts in banks**. They already know what a par unit price or a discounting factor is, and they need to register securities and calculate their prices quickly and correctly. The interface therefore speaks the language of the market and does not explain what a security is. What it does explain is each price, by showing how it was calculated.

The identity serves three qualities, in this order of priority:

1. **Precision**: every number can be checked. Prices come with their calculation details, values keep the digits they were calculated with, and identifiers are unambiguous.
2. **Institutional trust**: the interface looks like a serious financial system: sober, consistent and without decoration.
3. **Clarity**: the main result can be read at a glance, and the hierarchy comes from size, weight and position, not from color.

## 2. References

The identity draws on two references, as inspiration and never as a copy:

- **Origami**, the design system of the Financial Times, for its warm paper background, its dense tables with thin rules and its single accent color. finmathly does not use its typefaces, Financier and Metric, because they are paid, nor its salmon background, which is its own identity.
- **Swiss Style**, also called the International Typographic Style, for its strict grid, its sans-serif typography, its hierarchy through size and weight, and its generous white space.

## 3. Color

### 3.1 Principles

- **The palette is closed.** Every color in the interface is one of the tokens in 3.2, and no component uses a literal color. The only exception is the logo, whose colors are fixed, as described in [section 6](#6-logo).
- **There is one accent color**, a deep petrol green. It is used only for interaction and selection: links, the primary button, the selected tab, the active option of a selector, the focused field and the selected row.
- **There are two state colors**: brick red for an **error** and ochre for a **warning**. There is no success color, because a green would be confused with the accent color; a success is reported with neutral text. An **information** message uses only neutral colors, as described in [section 11](#11-messages).
- **Color never carries meaning alone.** A state color always comes with text and a border, so the interface also works for people who do not distinguish colors.
- **Color never carries financial meaning.** A premium or a discount is written, with its sign, and is never shown in green or red.
- **Every pair of text and background meets [WCAG 2.2](https://www.w3.org/TR/WCAG22/) level AA (4.5:1).** The ink and the accent color reach level AAA (7:1).

### 3.2 Tokens

| Token | Use | Light | Dark |
| --- | --- | --- | --- |
| `--color-paper` | Page background | `#FAF7F2` | `#1A1917` |
| `--color-surface` | Background of panels, forms and tables | `#FFFFFF` | `#22211E` |
| `--color-ink` | Text and numbers | `#1F1D1A` | `#EDE9E2` |
| `--color-ink-muted` | Labels and secondary text | `#5E5A53` | `#A8A39A` |
| `--color-rule` | Thin rules between rows and around panels | `#DDD7CD` | `#3A3833` |
| `--color-rule-strong` | Table header rule and field borders | `#1F1D1A` | `#EDE9E2` |
| `--color-accent` | Accent color | `#0F5C57` | `#5FB8AE` |
| `--color-accent-soft` | Background of a selected item | `#E4EFED` | `#1F3330` |
| `--color-on-accent` | Text on the accent color | `#FFFFFF` | `#1A1917` |
| `--color-error` | Error text and border | `#A3261D` | `#F08A7E` |
| `--color-error-soft` | Error background | `#F7E3E0` | `#3A2220` |
| `--color-warning` | Warning text and border | `#7A5200` | `#E0B45C` |
| `--color-warning-soft` | Warning background | `#F6ECD6` | `#352B18` |

## 4. Typography

finmathly uses two members of the [IBM Plex](https://www.ibm.com/plex/) family, which is free under the SIL Open Font License and served by Google Fonts:

- **IBM Plex Sans** sets all text and all numbers, in three weights: Regular (400) for text and numbers, Medium (500) for key figures, such as a price, and for labels, and SemiBold (600) for titles, table headers and the product name next to the logo.
- **IBM Plex Mono** sets three things. The first is **identifiers**, meaning values that are codes, such as the code of a security or a UUID. The second is the **decimal reference** of a rate field, which shows a value in the API's format rather than for reading. The third is the `fm` of the logo, which is the identifier of the product itself. A financial value shown for reading is never set in Mono.

Plex Serif is not used, because the identity is not editorial, and Plex Sans Condensed is not needed yet. Every number in a column uses **tabular figures** (`font-variant-numeric: tabular-nums`), so that digits line up place by place.

| Role | Size | Weight |
| --- | --- | --- |
| Page title | 24px | SemiBold |
| Key figure, such as a price | 28px | Medium |
| Secondary figure | 20px | Medium |
| Body text and form fields | 15px | Regular |
| Table cells | 14px | Regular |
| Hints, notes and tips | 13px | Regular |
| Labels | 12px | Medium |
| Table headers | 12px | SemiBold, uppercase, with letter spacing |

## 5. Shape and space

- Spacing follows a scale of 4, 8, 12, 16, 20, 24 and 32 pixels.
- Fields and buttons have almost square corners, with a radius of 2px; panels have no radius.
- Panels are separated by a 1px rule, never by a shadow.
- Interactive elements are at least 32px high, and primary actions at least 40px, so that they can be used on a phone.
- Running text stays near 65 characters per line.

## 6. Logo

The **logo** is a square in `#0F5C57`, the accent color of the light theme, with the letters `fm` in white (`#FFFFFF`), in IBM Plex Mono SemiBold. It is the file [`docs/logo.svg`](logo.svg), with its letters converted to outlines, so that it looks the same everywhere without depending on a font.

The logo is **fixed**: it has the same colors in the light and the dark theme, so the brand has a single appearance. It is also the browser tab icon, which sits on the browser's own bar and is therefore always the same. The logo is the only element of the interface with literal colors.

Next to the logo, the interface writes the **product name**, `finmathly`, always in lowercase, as text in IBM Plex Sans SemiBold. The product name follows the theme, through `--color-ink`, so that it stays legible. In this typeface, the bar of the `f` joins the `i` through the `fi` **ligature**.

## 7. Themes

The interface has a light theme and a dark theme. On the first visit, it follows the theme of the operating system; after that, a selector with two options, Light and Dark, lets the user change it. The dark theme redefines the same tokens with other values, so no component needs to know which theme is active.

## 8. Languages

The interface is available in Brazilian Portuguese and in English. On the first visit, it follows the language of the browser: Portuguese if the browser's language is Portuguese, and English otherwise. After that, a selector with two options, PT and EN, lets the user change it. The language selector and the theme selector sit together at the right of the top bar.

All translation happens in the frontend, because the API returns only stable codes and data:

- **Enum values**, such as `BUSINESS_252`, are never shown. Each one is mapped to a label, such as "Dias úteis sobre 252" or "Business days over 252".
- **Errors** arrive with a code and parameters, from which the interface writes the message in each language.
- **Labels** always use the terms of the glossary.
- **Numbers and dates** are formatted for the selected language: `966,662019` and `19/10/2026` in Portuguese, `966.662019` and `10/19/2026` in English.

## 9. Numbers and tables

- The interface shows each value exactly as the API returns it. It never rounds or truncates a value; any rounding rule belongs to the backend.
- Rates are shown and typed as **percentages**, with their period in the label: `% a.a.` in Portuguese and `% p.a.` in English. The period is always the year, because every rate in finmathly is an effective annual rate. The API receives and returns rates as decimals.
- Under every rate field, a small **decimal reference** in Mono shows the decimal that is sent to the API, always in the API's format: `Decimal = 0.135000`. The conversion from percentage to decimal works on the text of the number, never through a binary floating-point number, so no digit is lost.
- Numeric columns are aligned to the right, with tabular figures. Text and date columns are aligned to the left.
- Tables have a strong rule under the header and thin rules between rows, with no vertical lines and no alternating row colors. A selected row has the soft accent background and an accent bar on its left edge.
- Identifiers are set in Mono.

## 10. Voice

The interface writes in a technical and direct voice, always with the terms of the glossary. Each kind of text follows its own rule:

| Kind of text | Rule | Example |
| --- | --- | --- |
| Error | What happened, the data that explains it and, when one exists, the way around it | "Não há valor do IPCA para 15/10/2026. O último valor divulgado é de 30/09/2026. Calcule para 30/09/2026 ou uma data anterior." |
| Validation error | The same as an error, but short and next to the field | "A taxa de mercado precisa ser maior que −100%." |
| Information | A sentence that explains a consequence, such as why a field is absent | "Este título é prefixado, por isso não tem indexador." |
| Warning | The fact, with no way around it | "O último valor divulgado do IPCA é de 30/09/2026." |
| Confirmation | The fact, with no way around it | "Título salvo. Versão 4." |
| Label | The glossary term, with its unit when it has one, and no explanation | "Taxa de mercado (% a.a.)" |
| Concept tip | One or two sentences from the README's definition of the concept | "PU ao par: valor de uma unidade na data do cálculo pela remuneração do próprio contrato." |

The interface uses no exclamation marks, no apologies and no filler words. Actions are written in the imperative, without a pronoun.

**Concept tips** appear only next to the domain concepts of the glossary, never next to ordinary fields such as a name. A tip opens when the user hovers over or taps its ⓘ button. The README is the source of every tip: the English tip follows the README's definition, and the Portuguese tip translates it faithfully.

## 11. Messages

A message is a sentence that the interface writes for the user. [Section 10](#10-voice) sets how each message is worded; this section sets how it looks. Messages have three levels, each with its own form:

| Level | When | Form |
| --- | --- | --- |
| Information | A consequence that needs no action: "Este título é prefixado, por isso não tem indexador." | `--color-paper` background, a left border in `--color-ink-muted`, and no icon |
| Warning | Something the user should know before acting: "O último valor divulgado do IPCA é de 30/09/2026." | `--color-warning-soft` background, a left border in `--color-warning`, and the word "Aviso" or "Warning" in `--color-warning` |
| Error | Something failed or is invalid, with the way around it when one exists | `--color-error-soft` background, a left border in `--color-error`, and the word "Erro" or "Error" in `--color-error`; a validation error sits next to its field |

An information message has no icon, because the ⓘ button is reserved for concept tips, and an ⓘ on a message would look clickable. Information messages are kept rare: a sentence that only repeats data already on the screen is removed.

## 12. Read-only data

Values that belong to another record, or that the user cannot change, are shown as **read-only data**, never as a message. The rule that separates the two forms is simple: **a sentence is a message, and values are read-only data**.

Read-only data is a set of label and value pairs with a fixed structure:

- The label sits above the value. The label has the size and color of a field label, and the value has the size of a field's text.
- The pairs follow the same grid and alignment as the fields of the form, but have no input box, so they never look editable.
- **Each label and each value are the same as on the record's own screen.** Read-only data from a modality uses the field names of the modality's record screen, and shows each value exactly as that screen shows it.
- A label that is a concept of the glossary has its concept tip.
- An enum value is shown through its label, never as its code.

Read-only data appears, for example, in three places: the rules of the chosen modality on the security's record screen (rate class, day count convention, contractual rate and allowed events), the version of a security at the top of its record screen, and the summary at the top of the unit price calculation.

## 13. Screens

### 13.1 List and record screens

Each kind of record, a security, a modality or an index, has two screens:

- a **list screen**, with a search field, a **New** button and a table with one row per record; clicking a row opens that record;
- a **record screen**, used both for creating and for editing, with the record's fields and a **Save** button.

What exists only inside another record, a composition in the [class diagram](../README.md#31-class-diagram-of-the-write-side), is edited on the record screen of its owner: the **event schedule** on the security's record screen, and the **index values** on the index's record screen.

### 13.2 The security's record screen

The security's record screen has four sections: identification, dates, remuneration and event schedule. The version of the security is shown at the top.

Choosing the modality shows its rules as read-only data, and hides the fields that the modality does not allow; an information message explains the absence, such as that of an index. The event schedule is an editable table, and an information message explains that maturity does not need to be added as an event. A modality that already has securities is locked, and its record screen says so.

### 13.3 The unit price calculation

The unit price calculation screen opens from the security's record screen and shows what applies to that security:

| Security | What is calculated | Fields |
| --- | --- | --- |
| Fixed-rate with interest | Par unit price and unit price, with the premium or discount between them | Calculation date and market rate |
| Fixed-rate without interest, such as the LTN | Unit price, compared with the face value at issue | Calculation date and market rate |
| Floating-rate or hybrid | Par unit price | Calculation date |

When a calculation does not apply, the screen says why instead of hiding it. Below the results, the **calculation details** table shows every payment with its date, term, interest, amortization, payment, discounting factor and present value, with the unit price as its total.

## 14. Names used in this identity

Like the concepts of the domain, each word of the design has one name in each language.

| English | Portuguese | Meaning |
| --- | --- | --- |
| Accent color | Cor de destaque | The single color of interaction and selection, `--color-accent` |
| State color | Cor de estado | The error color and the warning color |
| Information | Informação | The neutral message that explains a consequence |
| Warning | Aviso | The message about something to know before acting |
| Error | Erro | The message about something that failed or is invalid |
| Read-only data | Dados somente leitura | Label and value pairs that cannot be changed |
| Token | Token | A named design decision, such as a color, used through its name |
| Concept tip | Dica de conceito | The ⓘ text that defines a domain concept |
| Decimal reference | Referência decimal | The decimal shown under a rate field |
| List screen | Tela de lista | The screen with many records |
| Record screen | Tela de registro | The screen with one record, for creating and editing |
| Unit price calculation | Cálculo de PU | The screen that calculates the prices of a security |
| Calculation details | Memória de cálculo | The table that shows how a price was calculated |
| Logo | Logo | The fixed `fm` square, which is also the browser tab icon |
| Product name | Nome do produto | The text `finmathly` next to the logo |
| Ligature | Ligadura | The joined `fi` of the product name |
