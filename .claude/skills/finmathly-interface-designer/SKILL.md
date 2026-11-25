---
name: finmathly-interface-designer
description: Use when designing, building, changing or reviewing any screen of the finmathly frontend, to apply its visual identity: the reasoning behind it, its colors, typography, spacing, components, voice, accessibility and design tokens, and the way to review a screen against them.
---

# The visual identity of finmathly

> This document is the result of a partnership between its author and AI. It was built over many iterations, in which the two studied each topic, drafted the text and revised it together. The author reviewed the final version in full.

Every screen of finmathly is built from the same decisions about how it looks, how it behaves and how it writes: (i) its colors, typefaces, spacing and shapes; (ii) its components, such as buttons, forms and tables, and the way they respond; (iii) the way it shows numbers, rates and dates; (iv) its words, in two (2) languages; and (v) the conditions under which everyone can use it. This skill records those decisions, with the reason for each, and the concepts behind them.

- [1. Interface design and design systems](#1-interface-design-and-design-systems)
- [2. User experience](#2-user-experience)
  - [2.1 Quality attributes](#21-quality-attributes)
  - [2.2 Users and their tasks](#22-users-and-their-tasks)
  - [2.3 How people read a screen](#23-how-people-read-a-screen)
    - [2.3.1 Visual hierarchy](#231-visual-hierarchy)
    - [2.3.2 Cognitive load](#232-cognitive-load)
    - [2.3.3 Recognition over recall](#233-recognition-over-recall)
    - [2.3.4 Familiar patterns](#234-familiar-patterns)
  - [2.4 Feedback and response time](#24-feedback-and-response-time)
    - [2.4.1 Feedback for every action](#241-feedback-for-every-action)
    - [2.4.2 Response time](#242-response-time)
    - [2.4.3 Layout shift](#243-layout-shift)
  - [2.5 Forms](#25-forms)
    - [2.5.1 Tolerant input](#251-tolerant-input)
    - [2.5.2 Defaults](#252-defaults)
    - [2.5.3 Progressive disclosure](#253-progressive-disclosure)
  - [2.6 Voice and languages](#26-voice-and-languages)
    - [2.6.1 Voice](#261-voice)
    - [2.6.2 Messages](#262-messages)
    - [2.6.3 Languages and formats](#263-languages-and-formats)
    - [2.6.4 Concept tips](#264-concept-tips)
  - [2.7 Accessibility](#27-accessibility)
- [3. User interface](#3-user-interface)
  - [3.1 Brand](#31-brand)
    - [3.1.1 Personality and references](#311-personality-and-references)
    - [3.1.2 Logo and product name](#312-logo-and-product-name)
  - [3.2 Color](#32-color)
    - [3.2.1 Contrast](#321-contrast)
    - [3.2.2 Palette and roles](#322-palette-and-roles)
    - [3.2.3 Light and dark themes](#323-light-and-dark-themes)
    - [3.2.4 State colors](#324-state-colors)
    - [3.2.5 Building the palette](#325-building-the-palette)
  - [3.3 Typography](#33-typography)
    - [3.3.1 Typefaces](#331-typefaces)
    - [3.3.2 Type scale and weights](#332-type-scale-and-weights)
    - [3.3.3 Line height and line length](#333-line-height-and-line-length)
    - [3.3.4 Letter spacing](#334-letter-spacing)
    - [3.3.5 Financial numbers](#335-financial-numbers)
  - [3.4 Space and layout](#34-space-and-layout)
    - [3.4.1 Spacing scale](#341-spacing-scale)
    - [3.4.2 Grouping](#342-grouping)
    - [3.4.3 Layout and responsiveness](#343-layout-and-responsiveness)
  - [3.5 Shape and motion](#35-shape-and-motion)
    - [3.5.1 Corners, borders and shadows](#351-corners-borders-and-shadows)
    - [3.5.2 Icons](#352-icons)
    - [3.5.3 Motion](#353-motion)
  - [3.6 Components](#36-components)
    - [3.6.1 States](#361-states)
    - [3.6.2 Affordance and click targets](#362-affordance-and-click-targets)
    - [3.6.3 Buttons and links](#363-buttons-and-links)
    - [3.6.4 Text fields and the rate field](#364-text-fields-and-the-rate-field)
    - [3.6.5 Combo box](#365-combo-box)
    - [3.6.6 Tables](#366-tables)
    - [3.6.7 Prices and calculation details](#367-prices-and-calculation-details)
    - [3.6.8 Read-only data](#368-read-only-data)
    - [3.6.9 Confirmation dialogs](#369-confirmation-dialogs)
    - [3.6.10 Loading placeholders](#3610-loading-placeholders)
  - [3.7 Screens](#37-screens)
    - [3.7.1 List and record screens](#371-list-and-record-screens)
    - [3.7.2 The security record screen](#372-the-security-record-screen)
    - [3.7.3 The unit price calculation](#373-the-unit-price-calculation)
- [4. Patterns to avoid](#4-patterns-to-avoid)
- [5. From identity to code](#5-from-identity-to-code)
  - [5.1 Design tokens](#51-design-tokens)
  - [5.2 Token names](#52-token-names)
- [6. How an agent applies and reviews a screen](#6-how-an-agent-applies-and-reviews-a-screen)

## 1. Interface design and design systems

An interface is what a person sees and uses on a screen: its text, its numbers, its colors, its buttons and the way they respond. Designing an interface has two (2) sides. User interface design, or UI, decides how each screen looks. User experience design, or UX, decides how easily a person does what they came to do.

A visual identity is the set of decisions that make every screen of a product look and behave as part of the same product. In finmathly, for example, it decides that the price of a security is the largest number on its screen, and that a user types every rate as a percentage. When these decisions are written down, together with the parts of a screen built from them, such as buttons and tables, the result is called a design system.

Without a design system, an interface is decided again on each screen, by whoever builds it. Each choice is small, but the choices drift apart: a button is green on one (1) screen and blue on another, and a rate shows two (2) decimal places in one (1) table and four (4) in the next. The user has to relearn the interface on every screen, and a reader of financial values starts to doubt whether two (2) numbers that look different mean different things.

What a design system protects is consistency: the same thing looks and works the same way everywhere. Consistency is one (1) of the [10 usability heuristics↗](https://www.nngroup.com/articles/ten-usability-heuristics/) of Jakob Nielsen, under the name consistency and standards. Some companies publish their design systems, such as [Material Design↗](https://m3.material.io/), from Google, and the [Human Interface Guidelines↗](https://developer.apple.com/design/human-interface-guidelines), from Apple.

This skill is the design system of finmathly. AI agents load it whenever they build or review a screen, and the person behind the agent reviews every screen against it. It follows the two (2) sides of interface design. [User experience](#2-user-experience) comes first, because it sets what the interface must achieve and for whom. [User interface](#3-user-interface) comes next, and its decisions serve those goals, from colors and typefaces to complete screens.

Every decision states four (4) things: (i) the exact value; (ii) the reason for it; (iii) the alternative that was discarded; and (iv) what would go wrong without it. A decision that cannot be explained this way does not belong here.

## 2. User experience

User experience covers what a person needs from finmathly and how easily they get it, before any choice of color or typeface. This section sets the qualities that the interface must have, the people who use it and their tasks, the way it writes, and the conditions under which everyone can use it.

### 2.1 Quality attributes

A quality attribute is a property that the interface must have, against which every decision is judged. The interface of finmathly has three (3), in this order of priority, so that when two (2) of them conflict, the first one wins:

1. precision: every number on a screen can be read exactly and checked. A price shows every digit that the API returns, as set in [README 3.8](../../../README.md#38-formats), its digits line up with the numbers around it, and it comes with the details of how it was calculated;
2. clarity: the most important information on a screen is found at a glance, without reading everything else first. On the unit price calculation, the eye goes to the price before anything else;
3. trust: the interface looks like a serious financial system, sober and consistent, so that a user believes its numbers before checking them.

Precision comes first because finmathly is a reference system: a price that cannot be checked has no value to its users. Precision and clarity conflict, for example, when a price has many decimal places. Clarity alone would show `966.66` instead of `966.662019`, because it reads faster. Precision wins, so the interface shows every digit and makes it easy to read in other ways, such as aligning the digits of a column, as explained in [3.3.5](#335-financial-numbers).

Clarity comes before trust because a sober interface that hides the price still fails the user.

Accessibility, the condition that people with disabilities can use the interface, is not ranked among them, because it is not traded against the others. It is a requirement that every screen meets, as explained in [2.7](#27-accessibility).

### 2.2 Users and their tasks

finmathly is used by fixed-income analysts in banks. They already know what a [par unit price](../../../README.md#224-par-unit-price-pu-par) or a [day count convention](../../../README.md#252-day-count-conventions) is, so the interface uses the terms of the market without explaining what a security is. What they cannot do without the interface is check how each price was calculated, so the interface always shows it.

Their tasks, taken from the [README](../../../README.md), are:

1. register and edit [securities](../../../README.md#211-loan-and-security), with their [events](../../../README.md#2110-events);
2. register [modalities](../../../README.md#2112-modalities), the rules shared by a kind of security;
3. register [indexes and their values](../../../README.md#216-index-its-values-and-index-factor);
4. calculate the [par unit price](../../../README.md#224-par-unit-price-pu-par) and the [unit price](../../../README.md#225-unit-price-pu) of a security on a date, and [read the result](../../../README.md#23-reading-a-price).

These users work mostly on a computer, for long periods, often comparing numbers on the screen with numbers from another source. They also open finmathly on a phone, to check a price away from their desk. The identity therefore favors density and exact reading on a large screen, and every screen also works well on a phone, as explained in [3.4.3](#343-layout-and-responsiveness).

### 2.3 How people read a screen

A person does not read a screen from top to bottom. The eye jumps to what stands out, groups what looks related and skips the rest. The four (4) principles below describe that behavior and turn it into rules.

#### 2.3.1 Visual hierarchy

Visual hierarchy is the order in which the eye visits the elements of a screen. It is set by differences in size, weight, color, contrast, position and space: the element that differs most from its surroundings is seen first.

In finmathly, every screen has one (1) primary element, and it differs from the rest by size and weight, never by color alone. On the unit price calculation, it is the price, set at the largest size of the [type scale](#332-type-scale-and-weights). Labels are smaller and lighter than the values they label, because the user looks for the value. The book [*Refactoring UI*↗](https://www.refactoringui.com/), by Adam Wathan and Steve Schoger, recommends this de-emphasis of labels.

The discarded alternative was to make everything equally strong, with bold labels, bold values and colored titles. Without hierarchy, five (5) elements compete for the eye, and the user has to read all of them to find the price.

#### 2.3.2 Cognitive load

Cognitive load is the mental effort needed to understand and use an interface. Every element on a screen adds a little, whether the user needs it or not.

finmathly reduces it in three (3) ways: (i) a screen shows only what its task needs, and the rest is one (1) step away, as explained in [2.5.3](#253-progressive-disclosure); (ii) a field that a modality does not allow is hidden, instead of shown disabled; and (iii) the same concept always has the same name, the one of the [glossary](../../../README.md#26-glossary).

The domain has complexity of its own, such as [index factors](../../../README.md#216-index-its-values-and-index-factor) and [day count conventions](../../../README.md#252-day-count-conventions), and the interface cannot remove it. It can only avoid adding more.

#### 2.3.3 Recognition over recall

Recognizing something on the screen is easier than remembering it. Nielsen lists this as one (1) of his [10 usability heuristics↗](https://www.nngroup.com/articles/ten-usability-heuristics/), under the name recognition rather than recall.

In finmathly, the user never has to remember a value from another screen. The record screen of a security shows the rules of its modality, and the unit price calculation shows the security and the conventions it uses, as [read-only data](#368-read-only-data). A modality or an index is chosen from a list, in a [combo box](#365-combo-box), and never typed from memory.

#### 2.3.4 Familiar patterns

People spend most of their time in other products, so they expect a new one to work like those. This observation is called [Jakob's Law↗](https://lawsofux.com/jakobs-law/).

finmathly therefore uses common patterns: a table that lists records and opens one (1) when its row is clicked, a form with its fields in reading order, a primary button at the end of the form, and a link that looks like a link. It invents no new control where a common one serves.

### 2.4 Feedback and response time

#### 2.4.1 Feedback for every action

Feedback is the response of the interface to an action of the user. Without it, the user does not know whether a click worked, clicks again and may save twice.

Every action in finmathly has a visible response: (i) a button shows that it was pressed; (ii) an action that waits for the API shows that it is loading and cannot be repeated meanwhile; and (iii) the end of the action shows its result, as a confirmation or an [error message](#262-messages).

finmathly does not show a result before the API confirms it, a technique called optimistic update. A price or a registration that appears and then disappears would harm [trust](#21-quality-attributes) more than a short wait.

#### 2.4.2 Response time

The [Doherty Threshold↗](https://lawsofux.com/doherty-threshold/) states that people stay engaged when the system responds within about 400 milliseconds. It is an observation, not a hard limit.

In finmathly, an action that takes longer than that shows a [loading placeholder](#3610-loading-placeholders) or a loading state, so that the user sees that the system is working.

#### 2.4.3 Layout shift

A layout shift happens when an element moves on its own after the page has appeared, because something above it loaded late. The user can lose their place, or click the wrong thing. [Google measures it as a quality signal of a page↗](https://web.dev/articles/cls).

In finmathly, every area that waits for data reserves its final size beforehand. The area of a price keeps its height while the price loads, and a table keeps its header and columns while its rows load.

### 2.5 Forms

#### 2.5.1 Tolerant input

[Postel's Law↗](https://lawsofux.com/postels-law/) asks a system to be liberal in what it accepts and conservative in what it sends. It comes from the design of the TCP protocol, and interface design borrowed it.

In finmathly, a rate field accepts the decimal separator of the language of the user, and spaces around the number. It never guesses a value it cannot read with certainty: an ambiguous entry is rejected with a [message](#262-messages) that shows the expected format. The value sent to the API always follows [README 3.8](../../../README.md#38-formats).

#### 2.5.2 Defaults

A default is the value that a field holds before the user changes it. People tend to keep defaults, so a default must be the value that most users want, and a safe one.

In finmathly, the calculation date starts with the current date, the most frequent choice. A field whose wrong default could produce a wrong price, such as a rate, starts empty, so that the user must type it.

#### 2.5.3 Progressive disclosure

Progressive disclosure shows the essential information first and lets the user open the detail when they need it.

In finmathly, the unit price calculation shows the price first. Its [calculation details](#367-prices-and-calculation-details) are always present, but below it, so that the price is seen first and the details are found next. Progressive disclosure never hides information that the task needs: hiding the details behind a button would break [precision](#21-quality-attributes).

### 2.6 Voice and languages

#### 2.6.1 Voice

The voice is the way the interface writes. finmathly writes in a technical and direct voice, with the terms of the [glossary](../../../README.md#26-glossary), because its users are specialists who want facts. It uses no exclamation marks, no apologies and no filler words, and it writes actions in the imperative, without a pronoun, such as "Calcule" rather than "Você pode calcular".

Each kind of text has its own rule:

| Kind of text | Rule | Example |
| --- | --- | --- |
| Error | What happened, the data that explains it and, when one exists, the way around it | "Não há valor do IPCA para 15/10/2026. O último valor divulgado é de 30/09/2026. Calcule para 30/09/2026 ou uma data anterior." |
| Validation error | The same as an error, but short and next to the field | "A taxa de mercado precisa ser maior que −100%." |
| Information | A sentence that explains a consequence, such as why a field is absent | "Este título é prefixado, por isso não tem indexador." |
| Warning | The fact, with no way around it | "O último valor divulgado do IPCA é de 30/09/2026." |
| Confirmation | The fact, with no way around it | "Título salvo. Versão 4." |
| Label | The term of the glossary, with its unit when it has one | "Taxa de mercado (% a.a.)" |
| Button | The action and its object | "Salvar título" |

#### 2.6.2 Messages

A message is a sentence that the interface writes for the user. Messages have three (3) levels, each with its own form, so that the user knows its weight before reading it:

1. information, for a consequence that needs no action. It has the paper background and a left border in the muted ink, and no icon;
2. warning, for something the user should know before acting. It has the warning background, a left border in the warning color and the word "Aviso" or "Warning";
3. error, for something that failed or is invalid. It has the error background, a left border in the error color and the word "Erro" or "Error". A validation error sits next to its field.

The left border marks the level of the message, together with its word and its background, as required in [3.2.4](#324-state-colors). It is never used on other elements as decoration, as explained in [4](#4-patterns-to-avoid).

A message is a sentence. Values that come from another record are not written as a sentence but shown as [read-only data](#368-read-only-data).

#### 2.6.3 Languages and formats

The interface is available in Portuguese (`pt-BR`) and English (`en-US`), as set in [README 3.8](../../../README.md#38-formats), together with the format of numbers, dates and times in each language. On the first visit, it follows the language of the browser: Portuguese if that language starts with `pt`, and English otherwise. A selector with two (2) options, PT and EN, sits at the right of the top bar.

All visible text comes from one (1) dictionary per language, so that no screen has text written in its code. Every label uses the term of the [glossary](../../../README.md#26-glossary) in its language. An enumerated value of the API, such as `BUSINESS_252`, is never shown as it comes: each one has a label in each language, such as "Dias úteis sobre 252" and "Business days over 252".

#### 2.6.4 Concept tips

A concept tip is a short definition that opens next to the name of a concept of the domain, when the user hovers over or taps a small ⓘ button. It serves a user who needs to recall a definition without leaving the screen.

Only the concepts of the [glossary](../../../README.md#26-glossary) get a tip, never an ordinary field such as a name. The text of a tip follows the definition in the README, in one (1) or two (2) sentences, and is translated faithfully into Portuguese. When a definition changes in the README, its tip changes in the same change.

### 2.7 Accessibility

Accessibility is the condition that people with disabilities can use the interface: people who do not see colors, who see poorly, who use a screen reader, or who use only a keyboard. The reference is the [Web Content Accessibility Guidelines (WCAG) 2.2↗](https://www.w3.org/TR/WCAG22/), and finmathly meets its level AA.

Every screen meets these rules, each one explained where it applies:

1. text contrast of at least 4.5:1, as explained in [3.2.1](#321-contrast);
2. no information carried by color alone, as explained in [3.2.4](#324-state-colors);
3. every action reachable with the keyboard, in reading order;
4. a visible focus on the element that the keyboard is on, as explained in [3.6.1](#361-states);
5. a label on every field, linked to its error message;
6. click targets of at least 24 by 24 pixels, as explained in [3.6.2](#362-affordance-and-click-targets);
7. no motion for a user who asked the system for less motion, as explained in [3.5.3](#353-motion).

## 3. User interface

User interface covers how each screen looks, from the basic choices of color, type and space to the components and the complete screens built from them.

### 3.1 Brand

#### 3.1.1 Personality and references

The personality of a brand is the set of qualities that a person should feel when using the product. finmathly should feel precise, sober and trustworthy, like a reference document of the market, and never playful or decorative. The personality follows from the [quality attributes](#21-quality-attributes).

The identity draws on two (2) references, as inspiration and never as a copy:

1. the Financial Times, for its warm paper background, its dense tables with thin rules and its single accent color. finmathly does not use its paid typefaces nor its salmon background, which is its own identity;
2. the Swiss Style, also called the International Typographic Style, for its strict grid, its sans-serif type, its hierarchy through size and weight, and its white space.

The discarded alternative was the bright and rounded look of consumer apps, which would make a reference system look like a game.

#### 3.1.2 Logo and product name

The logo is a square in `#0F5C57` with the letters `fm` in white, in IBM Plex Mono SemiBold. It is the file [`docs/logo.svg`](../../../docs/logo.svg), with its letters converted to outlines, so that it looks the same everywhere without depending on a font. It is always used from that file, never redrawn or retyped.

The logo is fixed: it has the same colors in the light and the dark theme, so that the brand has one (1) appearance. It is also the icon of the browser tab, which sits on the bar of the browser, whose theme finmathly does not control. The logo is the only element with literal colors, as explained in [5.1](#51-design-tokens).

Next to the logo, the interface writes the product name, `finmathly`, always in lowercase, as text in IBM Plex Sans SemiBold, in the ink color of the theme.

### 3.2 Color

#### 3.2.1 Contrast

Contrast is the difference in brightness between a text and its background. Low contrast makes text hard to read, above all for people who see poorly and for anyone in front of a bright window. WCAG measures it as a ratio from 1:1, no difference, to 21:1, black on white, and requires [at least 4.5:1 for normal text↗](https://www.w3.org/TR/WCAG22/#contrast-minimum).

Every pair of text and background in finmathly meets 4.5:1, in both themes. The ratios below were calculated with the formula of WCAG from the values in [3.2.2](#322-palette-and-roles):

| Text on background | Light | Dark |
| --- | --- | --- |
| Ink on paper | 15.73 | 14.52 |
| Ink on surface | 16.81 | 13.31 |
| Muted ink on paper | 6.42 | 7.00 |
| Muted ink on surface | 6.86 | 6.42 |
| Accent on paper | 7.31 | 7.49 |
| Accent on surface | 7.81 | 6.86 |
| Accent on soft accent | 6.65 | 5.69 |
| Text on accent | 7.81 | 7.49 |
| Ink on soft accent | 14.31 | 11.02 |
| Error on soft error | 5.97 | 6.04 |
| Error on surface | 7.36 | 6.63 |
| Warning on soft warning | 5.89 | 7.19 |
| Warning on surface | 6.92 | 8.32 |

The thin rule between table rows has a ratio of 1.43 to the surface, below the [3:1 that WCAG requires for the parts of a control↗](https://www.w3.org/TR/WCAG22/#non-text-contrast). It is allowed because it only separates rows and identifies no control. The border of a field, which identifies a control, uses the strong rule, at 16.81.

#### 3.2.2 Palette and roles

A palette is the closed set of colors of a product. Each color in it has a role, a job it does on every screen, and no color is used outside its role. A closed palette keeps every screen consistent and keeps the meaning of each color stable.

| Role | Light | Dark | Use |
| --- | --- | --- | --- |
| Paper | `#FAF7F2` | `#1A1917` | Background of the page |
| Surface | `#FFFFFF` | `#22211E` | Background of panels, forms and tables |
| Ink | `#1F1D1A` | `#EDE9E2` | Text and numbers |
| Muted ink | `#5E5A53` | `#A8A39A` | Labels and secondary text |
| Rule | `#DDD7CD` | `#3A3833` | Thin lines between rows and around panels |
| Strong rule | `#1F1D1A` | `#EDE9E2` | Line under a table header and border of a field |
| Accent | `#0F5C57` | `#5FB8AE` | Interaction and selection |
| Soft accent | `#E4EFED` | `#1F3330` | Background of a selected item |
| Text on accent | `#FFFFFF` | `#1A1917` | Text on the accent |
| Error | `#A3261D` | `#F08A7E` | Text and border of an error |
| Soft error | `#F7E3E0` | `#3A2220` | Background of an error |
| Warning | `#7A5200` | `#E0B45C` | Text and border of a warning |
| Soft warning | `#F6ECD6` | `#352B18` | Background of a warning |

The paper is warm, slightly yellow, rather than pure white, to recall printed financial reports and to tire the eyes less over long periods. The accent, a deep petrol green, is the only strong color, and it is used only for interaction and selection: links, the primary button, the selected tab, the active option, the focused field and the selected row. The discarded alternatives were navy blue, common to almost every bank, and burgundy, too close to the color of an error.

A financial value is never colored by its meaning. A premium or a discount is written with its sign, never shown in green or red, because a color that means good or bad would compete with the accent and add a judgment that the system does not make.

#### 3.2.3 Light and dark themes

A theme is a full set of values for the palette. finmathly has two (2): a light theme and a dark theme, with the same roles and different values, as listed in [3.2.2](#322-palette-and-roles). On the first visit, the interface follows the theme of the operating system. A selector with two (2) options, Light and Dark, sits next to the language selector.

The dark values are not an inversion of the light ones. Each was chosen to keep the contrast of [3.2.1](#321-contrast), and the accent becomes lighter in the dark theme so that it remains visible on the dark paper.

#### 3.2.4 State colors

A state color tells the level of a [message](#262-messages): brick red for an error, ochre for a warning. finmathly has no success color, because a green would be confused with the accent. A success is reported in neutral text, such as "Título salvo".

Color never carries meaning alone, as [WCAG requires↗](https://www.w3.org/TR/WCAG22/#use-of-color). A state color always comes with a word and a border, so that a user who does not distinguish red from green still reads the level of the message.

#### 3.2.5 Building the palette

A color can be described in several models. RGB describes it by the amount of red, green and blue light on a screen. HSL describes it by hue, saturation and lightness, but its lightness does not match what the eye perceives: a yellow and a blue with the same HSL lightness look very different in brightness. [OKLCH↗](https://www.w3.org/TR/css-color-4/#ok-lab) describes a color by perceived lightness, chroma and hue, so two (2) colors with the same lightness in OKLCH look equally bright.

The palette of finmathly is stored in hexadecimal RGB, the format that every tool reads. When a color is added or adjusted, it is built in OKLCH, so that its lightness matches the lightness of the color it replaces or sits next to, and the contrast of [3.2.1](#321-contrast) is then calculated again. This keeps the palette coherent as it grows, without asking every tool to read OKLCH.

### 3.3 Typography

Typography is the choice and arrangement of letters: which typefaces, in which sizes, weights and spacing. In finmathly, most of what a user reads is numbers and short labels, so typography is chosen first for numbers.

#### 3.3.1 Typefaces

A typeface is a family of letters with a common design, such as IBM Plex. It comes in members with different designs, such as Sans and Mono, and each member comes in weights, from thin to bold.

finmathly uses two (2) members of [IBM Plex↗](https://github.com/IBM/plex), which is free under the SIL Open Font License:

1. IBM Plex Sans, for all text and all numbers. Its letters have no serifs, the small strokes at the ends of letters, which suits the [Swiss Style](#311-personality-and-references), and its figures can be set tabular, as explained in [3.3.5](#335-financial-numbers);
2. IBM Plex Mono, a monospaced member, in which every character has the same width. It sets only three (3) things: identifiers, such as the code of a security or a UUID; the decimal reference of a rate field, as explained in [3.6.4](#364-text-fields-and-the-rate-field); and the letters of the logo.

A financial value shown for reading is never set in Mono, because its wide letters make long numbers harder to read. The discarded alternatives were a pairing with a serif typeface for titles, which gave the editorial look that the identity avoids, and Inter, whose name would be confused with Banco Inter.

#### 3.3.2 Type scale and weights

A type scale is the short, fixed list of text sizes that a product uses. With a scale, two (2) sizes that are almost equal never appear side by side, and the size of a text tells its importance, as required by [visual hierarchy](#231-visual-hierarchy).

| Role | Size | Weight |
| --- | --- | --- |
| Page title | 24px | SemiBold (600) |
| Key figure, such as a price | 28px | Medium (500) |
| Secondary figure | 20px | Medium (500) |
| Body text and form fields | 15px | Regular (400) |
| Table cells | 14px | Regular (400) |
| Hints and notes | 13px | Regular (400) |
| Labels | 12px | Medium (500) |
| Table headers | 12px | SemiBold (600), uppercase |

Only three (3) weights are used: Regular for text and numbers, Medium for key figures and labels, and SemiBold for titles, table headers and the product name. More weights would create differences too small to read as hierarchy.

#### 3.3.3 Line height and line length

Line height, also called leading, is the vertical distance between two (2) lines of text. Line length is the number of characters on one (1) line. Both decide how comfortably a paragraph reads: lines too close or too long make the eye lose the start of the next line.

Body text in finmathly has a line height of 1.5 times its size, the value that [WCAG recommends for blocks of text↗](https://www.w3.org/TR/WCAG22/#visual-presentation). Table cells have 1.4, to keep tables dense, and titles have 1.2, because they rarely wrap. Paragraphs, such as messages and tips, are at most about 65 characters wide, within the 80 that WCAG sets as a maximum in the same criterion.

#### 3.3.4 Letter spacing

Letter spacing, also called tracking, is the space added between all the letters of a text. Text in uppercase reads tighter than lowercase, so it needs more space.

finmathly adds letter spacing only to the uppercase table headers, of 0.04 times the size, and keeps the default spacing of IBM Plex everywhere else. Uppercase is used only for table headers, which are short. Longer uppercase text, such as a subtitle, is avoided, because it reads slower than lowercase.

#### 3.3.5 Financial numbers

Numbers are the main content of finmathly, and four (4) rules make them exact and easy to compare:

1. every number that can appear in a column uses tabular figures, in which every digit has the same width, so that the units, tens and decimal places of a column line up. In CSS, they are set with [`font-variant-numeric: tabular-nums`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/font-variant-numeric);
2. numeric columns are aligned to the right, and every value of a column has the same number of decimal places, so that the decimal separators line up;
3. a value shows every digit that the API returns, as set in [README 3.8](../../../README.md#38-formats). The frontend never rounds, truncates or recalculates a value;
4. a rate is shown and typed as a percentage, with its period in the label, `% a.a.` in Portuguese and `% p.a.` in English, as set in [README 3.8](../../../README.md#38-formats).

Some financial interfaces set the last decimal places smaller or lighter, to bring the integer part forward. finmathly does not, because the user checks exactly those last digits, and [precision](#21-quality-attributes) comes first.

### 3.4 Space and layout

#### 3.4.1 Spacing scale

A spacing scale is the fixed list of distances that may separate elements. finmathly uses 4, 8, 12, 16, 20, 24 and 32 pixels. A scale makes equal relations look equal on every screen: the space between a label and its field is always 4 pixels, and between two (2) fields always 16 pixels.

The discarded alternative was to choose each distance by eye, which produces distances such as 13 and 15 pixels that the eye cannot tell apart but that break the alignment of a screen.

#### 3.4.2 Grouping

People see elements as a group when they are close to each other, inside the same area or similar in appearance. The [Gestalt laws↗](https://lawsofux.com/law-of-proximity/) of proximity, [common region↗](https://lawsofux.com/law-of-common-region/) and [similarity↗](https://lawsofux.com/law-of-similarity/) describe these three (3) cases.

finmathly groups with space first. The label of a field is closer to its field than to the field above, and the sections of a form are separated by more space than the fields inside them. A panel, an area with its own background and a thin rule around it, is used only to separate a group that the space alone does not separate, such as the calculation details below a price. Elements with the same role, such as all the values of a column, always look the same.

#### 3.4.3 Layout and responsiveness

The layout of a screen is the arrangement of its areas. Every screen of finmathly has a top bar, with the logo, the navigation and the selectors, and a content area below it, at most 1,180 pixels wide and centered on large screens.

Responsiveness is the ability of a layout to adapt to the width of the screen. finmathly is a web application, and every screen works well from the width of a phone to that of a large monitor. Below 720 pixels, the fields of a form stack in one (1) column, the top bar keeps the logo and the selectors and folds the navigation into a menu, and a wide table scrolls sideways inside its own area, never the whole page.

### 3.5 Shape and motion

#### 3.5.1 Corners, borders and shadows

Fields and buttons have almost square corners, with a radius of 2 pixels, and panels have none. The square shape recalls printed reports and tables, as in the [references](#311-personality-and-references). The discarded alternative was large rounded corners, common in consumer apps, which soften the look that the [personality](#311-personality-and-references) asks to keep sober.

Panels are separated by a thin rule, never by a shadow. A shadow suggests that an element floats above the page, which suits a dialog, but not a section of a form. The only element with a shadow is a [dialog](#369-confirmation-dialogs), which does float above the page.

#### 3.5.2 Icons

An icon is a small image that stands for an action or an idea. finmathly uses few icons, because most of its actions are clearer as words, and an icon always comes with a text or an accessible name. The only icon with a fixed meaning is the ⓘ of the [concept tips](#264-concept-tips), which is never used for anything else.

#### 3.5.3 Motion

Motion is any change on the screen that happens over time, such as a color that fades or a panel that slides. Motion helps the user follow a change, but too much of it distracts and slows down a frequent task.

finmathly uses almost no motion. Only a change of color or opacity, such as the hover of a button, takes 150 milliseconds, with an ease-out curve, which starts fast and slows down at the end. Nothing slides, bounces or appears in sequence. For a user who asked the operating system for less motion, through the setting that the browser exposes as [`prefers-reduced-motion`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/@media/prefers-reduced-motion), even these changes are instant.

### 3.6 Components

A component is a reusable part of a screen, such as a button or a table, built from the decisions of [3.2](#32-color) to [3.5](#35-shape-and-motion). Every screen is assembled from components, so a component is decided once and looks the same everywhere.

#### 3.6.1 States

A state is a condition of a component that changes how it looks. Every interactive component of finmathly has these states, when they apply:

1. normal, at rest;
2. hover, when the mouse is over it;
3. focus, when the keyboard is on it, shown by an outline of 2 pixels in the accent color, as [WCAG requires↗](https://www.w3.org/TR/WCAG22/#focus-visible);
4. error, for a field with an invalid value, shown by a border in the error color and a [message](#262-messages) next to it;
5. loading, while an action waits for the API, as explained in [2.4.1](#241-feedback-for-every-action);
6. empty, for a list or table with no records, with a sentence that says so and the action that creates the first one;
7. disabled, for an action that cannot be done yet, with its reason close to it.

A component without one (1) of these states, where it applies, is not finished.

#### 3.6.2 Affordance and click targets

Affordance is the quality of an element that shows how it can be used: a button looks pressable, a link looks clickable and a field looks fillable. Without it, the user does not find the action, or tries to click what does nothing.

In finmathly, a button has a filled or bordered shape, a link is underlined and in the accent color, and a field has the strong border of [3.2.2](#322-palette-and-roles). Text that is not clickable is never underlined nor in the accent color.

[Fitts's Law↗](https://lawsofux.com/fittss-law/) states that a target is faster to reach the larger and the closer it is. WCAG sets a [minimum target of 24 by 24 pixels↗](https://www.w3.org/TR/WCAG22/#target-size-minimum). finmathly uses at least 32 pixels of height for every interactive element and 40 pixels for a primary action, so that they also work on a phone.

#### 3.6.3 Buttons and links

finmathly has two (2) kinds of button and one (1) kind of link:

1. the primary button, filled with the accent, for the main action of a screen, such as "Salvar título". There is at most one (1) per screen, so that the main action is always clear;
2. the secondary button, with an accent border and no fill, for other actions, such as "Calcular PU";
3. the link, underlined and in the accent color, for navigation to another screen.

The text of a button says the action and its object. A button that only says "OK" or "Sim" forces the user to read the rest of the screen to know what it does.

#### 3.6.4 Text fields and the rate field

A text field has a label above it, a strong border and, when it applies, an error message below it. The label is always visible, never a placeholder that disappears while the user types, because the user would lose what the field asks for.

A rate field is typed as a percentage, as set in [3.3.5](#335-financial-numbers). Below it, a decimal reference in IBM Plex Mono shows the decimal that is sent to the API, in the format of the API whatever the language, such as `Decimal = 0.135000`. The conversion moves the decimal point on the text of the number, without passing through a binary number, so that no digit is lost.

#### 3.6.5 Combo box

A combo box is a field that combines typing and choosing: the user types part of a name and picks it from a filtered list. Its behavior for the keyboard and for screen readers follows the [combo box pattern of the W3C↗](https://www.w3.org/WAI/ARIA/apg/patterns/combobox/).

finmathly uses it to choose a modality or an index on the record screen of a security, where the list can be long and a plain list would force the user to scroll it.

#### 3.6.6 Tables

A table shows records or values in rows and columns, and it is the main component of finmathly. It has a strong rule under its header, thin rules between rows, no vertical lines and no alternating row colors. Numeric columns follow [3.3.5](#335-financial-numbers), and text and date columns are aligned to the left.

A selected row has the soft accent background and an accent bar on its left edge. A row of a list opens its record when it is clicked.

#### 3.6.7 Prices and calculation details

A price is shown as a key figure, at the largest size of the [type scale](#332-type-scale-and-weights), with its label above it. It never appears without its calculation details, the steps that show how it was calculated, which the API returns with it:

1. the [unit price](../../../README.md#225-unit-price-pu) comes with the table of its [payment schedule](../../../README.md#223-payment-schedule), with the present value of each payment and the unit price as the total;
2. the [par unit price](../../../README.md#224-par-unit-price-pu-par) comes with its components, shown as [read-only data](#368-read-only-data) in the order of its formula.

When both prices are shown, the interface also shows whether the security trades at par, at a premium or at a discount, in words, as explained in the [README](../../../README.md#23-reading-a-price).

#### 3.6.8 Read-only data

Read-only data is a set of labels and values that the user cannot change on that screen, such as the rules of the modality of a security. Each label sits above its value, on the same grid as the fields of a form, but without a border, so that it never looks editable. Each label and each value are the same as on the screen where that data is edited.

#### 3.6.9 Confirmation dialogs

A dialog is a panel that opens above the screen and asks for a decision before the user goes on. Its behavior for the keyboard and for screen readers follows the [dialog pattern of the W3C↗](https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/).

finmathly opens a dialog only before an action that loses work. Its title names the action, such as "Descartar as alterações?", and not "Tem certeza?". Its buttons name their results, such as "Descartar alterações" and "Continuar editando", never "Sim" and "Não". When the user leaves a record screen with unsaved changes, by its close button or by navigation, the dialog asks whether to discard them, so that the close button never discards work silently.

#### 3.6.10 Loading placeholders

A loading placeholder is a gray shape that takes the place and the size of content that is still loading, such as a price or the rows of a table. It shows that the content is coming and reserves its space, which prevents the [layout shift](#243-layout-shift) of 2.4.3.

### 3.7 Screens

#### 3.7.1 List and record screens

Each kind of record, a security, a modality or an index, has two (2) screens:

1. a list screen, with a search field, a button that creates a record and a table with one (1) row per record;
2. a record screen, the same for creating and for editing, with the fields of the record and a button that saves it.

What exists only inside another record is edited on the record screen of its owner, as in the [class diagram](../../../README.md#41-class-diagram): the [events](../../../README.md#2110-events) on the record screen of the security, and the [index values](../../../README.md#216-index-its-values-and-index-factor) on the record screen of the index.

#### 3.7.2 The security record screen

The record screen of a security has four (4) sections: identification, dates, remuneration and events. Choosing the modality shows its rules as [read-only data](#368-read-only-data) and hides the fields that the modality does not allow. An information message explains each absence, such as that of an index on a fixed-rate security.

#### 3.7.3 The unit price calculation

The unit price calculation opens from the record screen of a security and shows what applies to it, as set in [README 2.4](../../../README.md#24-what-finmathly-calculates-and-what-it-doesnt). When a calculation does not apply, the screen says why instead of hiding it. The price comes first and its [calculation details](#367-prices-and-calculation-details) follow, as explained in [2.5.3](#253-progressive-disclosure).

## 4. Patterns to avoid

Some visual patterns have become so common in generated interfaces that they mark a screen as made without decisions. A pattern is not wrong in itself: it is wrong when it appears by habit, with no role. finmathly avoids these, except where noted:

| Pattern | Why it is avoided | What finmathly does instead |
| --- | --- | --- |
| Everything in a card | Boxes around every block flatten the [hierarchy](#231-visual-hierarchy) and waste space | Groups by space, and uses a panel only where space is not enough, as in [3.4.2](#342-grouping) |
| A divider between every block | Lines everywhere make the screen noisy | Separates with space, and keeps rules for tables |
| Many shades and transparencies of one (1) color | Colors outside the palette break its roles | Uses only the roles of [3.2.2](#322-palette-and-roles) |
| Gradient and decorative backgrounds | They compete with the content | Uses the flat paper |
| A colored left border as decoration | It suggests a state that does not exist | Uses it only for the level of a [message](#262-messages) and a selected row |
| Uppercase subtitles | Long uppercase text reads slowly | Uses uppercase only for table headers, as in [3.3.4](#334-letter-spacing) |
| Monospaced text everywhere | It makes reading slower and loses its meaning | Uses Mono only for identifiers, as in [3.3.1](#331-typefaces) |
| Made-up numbers and statistics | They destroy [trust](#21-quality-attributes) in a reference system | Shows only values that come from the API |

## 5. From identity to code

### 5.1 Design tokens

A design token is a decision of the identity stored under a name, such as the accent color stored as `--color-accent`. The code uses the name, never the value, so that a decision changes in one (1) place and every screen follows.

In CSS, the tokens are [custom properties↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties), defined once for the light theme and defined again, with the same names, for the dark theme. A component never knows which theme is active, and no component has a literal color, font or size. The logo is the only exception, because its colors are fixed, as explained in [3.1.2](#312-logo-and-product-name).

```css
:root {
  --color-accent: #0F5C57;
  // ...
}

:root[data-theme="dark"] {
  --color-accent: #5FB8AE;
  // ...
}
```

### 5.2 Token names

Each token is named by its role, never by its value, so that `--color-accent` stays correct if the accent changes from green to another color:

| Group | Tokens |
| --- | --- |
| Color | `--color-paper`, `--color-surface`, `--color-ink`, `--color-ink-muted`, `--color-rule`, `--color-rule-strong`, `--color-accent`, `--color-accent-soft`, `--color-on-accent`, `--color-error`, `--color-error-soft`, `--color-warning`, `--color-warning-soft` |
| Typeface | `--font-sans`, `--font-mono` |
| Size | `--font-size-title`, `--font-size-key-figure`, `--font-size-figure`, `--font-size-body`, `--font-size-table`, `--font-size-note`, `--font-size-label` |
| Space | `--space-4`, `--space-8`, `--space-12`, `--space-16`, `--space-20`, `--space-24`, `--space-32` |
| Shape | `--radius-control` |
| Motion | `--duration-fade`, `--easing-out` |

The values of each token are the ones in [3](#3-user-interface).

## 6. How an agent applies and reviews a screen

To build a screen, an agent follows these steps:

1. read the parts of the [README](../../../README.md) that the screen touches, and use the terms of the [glossary](../../../README.md#26-glossary);
2. name the task of the screen and its primary element, as explained in [2.3.1](#231-visual-hierarchy);
3. build the screen only from the [components](#36-components) and [tokens](#5-from-identity-to-code) of this skill;
4. stop and ask the person when the screen needs a decision that this skill does not record, and record the answer here before using it.

To review a screen, an agent checks that:

1. the primary element is seen first;
2. every number follows [3.3.5](#335-financial-numbers), and every price comes with its calculation details;
3. every text comes from the dictionaries and uses the terms of the glossary, in both languages;
4. the screen works in both themes, with the contrast of [3.2.1](#321-contrast);
5. every interactive component has its [states](#361-states) and can be used with the keyboard;
6. no [pattern to avoid](#4-patterns-to-avoid) appears without a reason.
