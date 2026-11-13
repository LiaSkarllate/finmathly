# Foundations

The audience of finmathly, the references of its identity, and the decisions on color, typography, shape, logo and themes, each with its reason.

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
- **There are two state colors**: brick red for an **error** and ochre for a **warning**. There is no success color, because a green would be confused with the accent color; a success is reported with neutral text. An **information** message uses only neutral colors, as described in [content.md](content.md#5-messages).
- **Color never carries meaning alone.** A state color always comes with text and a border, so the interface also works for people who do not distinguish colors.
- **Color never carries financial meaning.** A premium or a discount is written, with its sign, and is never shown in green or red.
- **Every pair of text and background meets [WCAG 2.2](https://www.w3.org/TR/WCAG22/) level AA (4.5:1).** The ink and the accent color reach level AAA (7:1).

### 3.2 Tokens

Each token is a CSS custom property, defined once for the light theme on `:root` and redefined with the same name for the dark theme.

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

finmathly uses two members of the [IBM Plex](https://www.ibm.com/plex/) family, which is free under the SIL Open Font License and served by Google Fonts, through the tokens `--font-sans` and `--font-mono`:

- **IBM Plex Sans** sets all text and all numbers, in three weights: Regular (400) for text and numbers, Medium (500) for key figures, such as a price, and for labels, and SemiBold (600) for titles, table headers and the product name next to the logo.
- **IBM Plex Mono** sets three things. The first is **identifiers**, meaning values that are codes, such as the code of a security or a UUID. The second is the **decimal reference** of a rate field, which shows a value in the API's format rather than for reading. The third is the `fm` of the logo, which is the identifier of the product itself. A financial value shown for reading is never set in Mono.

Plex Serif is not used, because the identity is not editorial, and Plex Sans Condensed is not needed yet. Every number that can appear in a column uses **tabular figures** (`font-variant-numeric: tabular-nums`), so that digits line up place by place.

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

Hierarchy comes from this scale, from weight and from position, never from color.

## 5. Shape and space

- Spacing follows a scale of 4, 8, 12, 16, 20, 24 and 32 pixels.
- Fields and buttons have almost square corners, with a radius of 2px; panels have no radius.
- Panels are separated by a 1px rule, never by a shadow.
- Interactive elements are at least 32px high, and primary actions at least 40px, so that they can be used on a phone.
- Running text stays near 65 characters per line.

## 6. Logo

The **logo** is a square in `#0F5C57`, the accent color of the light theme, with the letters `fm` in white (`#FFFFFF`), in IBM Plex Mono SemiBold. It is the file `docs/logo.svg`, with its letters converted to outlines, so that it looks the same everywhere without depending on a font. It is always used from that file, never redrawn or retyped.

The logo is **fixed**: it has the same colors in the light and the dark theme, so the brand has a single appearance. It is also the browser tab icon, which sits on the browser's own bar and is therefore always the same. The logo is the only element of the interface with literal colors.

Next to the logo, the interface writes the **product name**, `finmathly`, always in lowercase, as text in IBM Plex Sans SemiBold. The product name follows the theme, through `--color-ink`, so that it stays legible. In this typeface, the bar of the `f` joins the `i` through the `fi` **ligature**, so ligatures stay on.

## 7. Themes

The interface has a light theme and a dark theme. On the first visit, it follows the theme of the operating system; after that, a selector with two options, Light and Dark, lets the user change it. There is no third "system" option. The dark theme redefines the same tokens with other values, so no component needs to know which theme is active, and every screen is designed and checked in both themes.
