---
name: finmathly-interface-designer
description: Use when designing, building, changing or reviewing any screen of the finmathly frontend, to apply its design system: the reasoning behind it, its colors, typography, spacing, components, voice, accessibility and design tokens, and the way to review a screen against them.
---

# The design system of finmathly

> This document is the result of a partnership between its author and AI. It was built over many iterations, in which the two studied each topic, drafted the text and revised it together. The author reviewed the final version in full.

Every screen of finmathly is built from the same decisions: how it looks, how it responds to the user, how it shows numbers, the words of its labels and messages, and how people with disabilities use it. This skill records those decisions, with the reason for each and the concepts behind them.

- [1. What a design system is](#1-what-a-design-system-is)
- [2. User experience](#2-user-experience)
  - [2.1 Quality attributes](#21-quality-attributes)
  - [2.2 Users and their tasks](#22-users-and-their-tasks)
  - [2.3 How people read and use a screen](#23-how-people-read-and-use-a-screen)
    - [2.3.1 Visual hierarchy](#231-visual-hierarchy)
    - [2.3.2 Cognitive load](#232-cognitive-load)
    - [2.3.3 Recognition rather than recall](#233-recognition-rather-than-recall)
    - [2.3.4 Familiar patterns](#234-familiar-patterns)
  - [2.4 Feedback and response time](#24-feedback-and-response-time)
    - [2.4.1 Feedback for every action](#241-feedback-for-every-action)
    - [2.4.2 Response time](#242-response-time)
    - [2.4.3 Layout shift](#243-layout-shift)
  - [2.5 Forms](#25-forms)
    - [2.5.1 Tolerant input](#251-tolerant-input)
    - [2.5.2 Defaults](#252-defaults)
  - [2.6 Text and languages](#26-text-and-languages)
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
    - [3.2.1 Palette and roles](#321-palette-and-roles)
    - [3.2.2 Light and dark themes](#322-light-and-dark-themes)
    - [3.2.3 Contrast](#323-contrast)
    - [3.2.4 State colors](#324-state-colors)
    - [3.2.5 Building the palette](#325-building-the-palette)
  - [3.3 Typography](#33-typography)
    - [3.3.1 Typefaces](#331-typefaces)
    - [3.3.2 Type scale and weights](#332-type-scale-and-weights)
    - [3.3.3 Line height and line length](#333-line-height-and-line-length)
    - [3.3.4 Financial numbers](#334-financial-numbers)
  - [3.4 Space and layout](#34-space-and-layout)
    - [3.4.1 Spacing scale](#341-spacing-scale)
    - [3.4.2 Grouping](#342-grouping)
    - [3.4.3 Layout and responsiveness](#343-layout-and-responsiveness)
  - [3.5 Shape, icons and motion](#35-shape-icons-and-motion)
    - [3.5.1 Corners, borders and shadows](#351-corners-borders-and-shadows)
    - [3.5.2 Icons](#352-icons)
    - [3.5.3 Motion](#353-motion)
  - [3.6 Components](#36-components)
    - [3.6.1 States](#361-states)
    - [3.6.2 Affordance and click targets](#362-affordance-and-click-targets)
    - [3.6.3 Buttons and links](#363-buttons-and-links)
    - [3.6.4 Fields](#364-fields)
    - [3.6.5 Combo box](#365-combo-box)
    - [3.6.6 Message boxes](#366-message-boxes)
    - [3.6.7 Tables](#367-tables)
    - [3.6.8 Prices and calculation details](#368-prices-and-calculation-details)
    - [3.6.9 Read-only data](#369-read-only-data)
    - [3.6.10 Confirmation dialogs](#3610-confirmation-dialogs)
    - [3.6.11 Skeletons](#3611-skeletons)
  - [3.7 Screens](#37-screens)
    - [3.7.1 List and record screens](#371-list-and-record-screens)
    - [3.7.2 The record screen of a security](#372-the-record-screen-of-a-security)
    - [3.7.3 The price calculation screen](#373-the-price-calculation-screen)
- [4. Patterns to avoid](#4-patterns-to-avoid)
- [5. Design tokens](#5-design-tokens)
  - [5.1 Tokens in CSS](#51-tokens-in-css)
  - [5.2 Token names](#52-token-names)
- [6. How an agent builds and reviews a screen](#6-how-an-agent-builds-and-reviews-a-screen)

## 1. What a design system is

A design system is the written set of decisions that make every screen of a product look and behave as part of the same product, together with the parts of a screen built from them, such as buttons and tables. In finmathly, for example, it decides that a price is the largest number on its screen, and that a user types every rate as a percentage.

These decisions cover the two (2) sides of an interface, which is what a person sees and uses on a screen. User interface design, or UI, decides how each screen looks. User experience design, or UX, decides how easily a person does what they came to do.

Without a design system, an interface is decided again on each screen, by whoever builds it. Each choice is small, but the choices drift apart: a button is green on one (1) screen and blue on another, and a rate shows two (2) decimal places in one (1) table and four (4) in the next. The user has to relearn the interface on every screen, and a reader of financial values starts to doubt whether two (2) numbers that look different mean different things.

What a design system protects is consistency: the same thing looks and works the same way everywhere. Consistency is one (1) of the [usability heuristics↗](https://www.nngroup.com/articles/ten-usability-heuristics/) of Jakob Nielsen, under the name "Consistency and standards".

This skill is the design system of finmathly. AI agents load it whenever they build or review a screen, and the person behind the agent reviews every screen against it. It follows the two (2) sides of interface design. [User experience](#2-user-experience) comes first, because it sets what the interface must achieve and for whom. [User interface](#3-user-interface) comes next, and its decisions serve those goals, from colors and letters to complete screens.

## 2. User experience

User experience covers what a person needs from finmathly and how easily they get it, before any decision about how the interface looks.

### 2.1 Quality attributes

A quality attribute is a property that the interface must have and against which every decision is judged. The interface of finmathly has three (3), in this order of priority, so that when two (2) of them conflict, the first one wins:

1. precision: every number on a screen can be read exactly and checked. A price shows every digit that the API returns, as set in [README 3.8](../../../README.md#38-formats), its digits line up with the numbers around it, and it comes with its [calculation details](#368-prices-and-calculation-details);
2. clarity: the most important information on a screen is found at a glance, without reading everything else first. On the [price calculation screen](#373-the-price-calculation-screen), the eye goes to the price before anything else;
3. trust: the interface looks like a serious financial system, sober and consistent, so that a user believes its numbers before checking them.

Precision comes first because finmathly is a reference system: a price that cannot be checked has no value to its users. Precision and clarity conflict, for example, when a price has many decimal places. Clarity alone would show `966.66` instead of `966.662019`, because it reads faster. Precision wins, so the interface shows every digit and makes it easy to read in other ways, such as aligning the digits of a column, as explained in [3.3.4](#334-financial-numbers).

Clarity and trust conflict, for example, when a screen could give every value the same emphasis, as a printed report does. Clarity wins, so the price stands out from the rest by its size, as explained in [2.3.1](#231-visual-hierarchy).

Accessibility is not ranked among them, because it is not traded against the others. It is a requirement that every screen meets, as explained in [2.7](#27-accessibility).

### 2.2 Users and their tasks

finmathly is used by fixed-income analysts in banks. They already know what a [par unit price](../../../README.md#224-par-unit-price-pu-par) or a [day count convention](../../../README.md#252-day-count-conventions) is, so the interface uses the terms of the market without explaining them, except in short [concept tips](#264-concept-tips). What they need from the interface is to check how each price was calculated, so the interface always shows it.

Their tasks, taken from the [README](../../../README.md), are:

1. register and edit [securities](../../../README.md#211-loan-and-security), with their [events](../../../README.md#2110-events);
2. register and edit [modalities](../../../README.md#2112-modalities), the rules shared by a kind of security;
3. register and edit [indexes and their values](../../../README.md#216-index-its-values-and-index-factor);
4. calculate the [par unit price](../../../README.md#224-par-unit-price-pu-par) and the [unit price](../../../README.md#225-unit-price-pu) of a security on a date, and [read the result](../../../README.md#23-reading-a-price).

These users work mostly on a computer, for long periods, often comparing numbers on the screen with numbers from another source. They also open finmathly on a phone, to check a price away from their desk. On a large screen, the design system therefore favors exact reading and density, which is many values on one (1) screen. Every screen also works well on a phone, as explained in [3.4.3](#343-layout-and-responsiveness).

### 2.3 How people read and use a screen

A person does not read a screen from top to bottom. The eye jumps to what stands out and groups what looks related, and the mind relies on what it sees and on habits from other products. The four (4) principles below describe that behavior and turn it into decisions.

#### 2.3.1 Visual hierarchy

Visual hierarchy is the order in which the eye visits the elements of a screen. It is set by differences in size, boldness, color, position and space: the element that differs most from its surroundings is seen first.

In finmathly, every screen has one (1) primary element, and it differs from the rest by size and boldness, never by color alone. On the [price calculation screen](#373-the-price-calculation-screen), it is the price, set at the largest size of the [type scale](#332-type-scale-and-weights), with its calculation details below it. When a security has two (2) prices, the unit price is the primary element, as set in [3.6.8](#368-prices-and-calculation-details).

Labels are smaller than the values they label and set in the [muted ink](#321-palette-and-roles), because the user looks for the value. The book [*Refactoring UI*↗](https://refactoringui.com/), by Adam Wathan and Steve Schoger, recommends this de-emphasis of labels.

The discarded alternative was to make everything equally strong, with bold labels, bold values and colored titles. Without hierarchy, every element competes for the eye, and the user has to read all of them to find the price.

#### 2.3.2 Cognitive load

[Cognitive load↗](https://www.nngroup.com/articles/minimize-cognitive-load/) is the mental effort needed to understand and use an interface. Every element on a screen adds a little, whether the user needs it or not.

finmathly reduces it in two (2) ways.

First, a screen shows only what its task needs. For example, a field that a [modality](../../../README.md#2112-modalities) does not allow is hidden, instead of shown disabled, which would make the user wonder why it cannot be filled, and a message explains its absence, as set in [3.7.2](#372-the-record-screen-of-a-security).

Second, the same concept always has the same name, its name in the [glossary](../../../README.md#26-glossary).

The domain has complexity of its own, such as [index factors](../../../README.md#216-index-its-values-and-index-factor) and [day count conventions](../../../README.md#252-day-count-conventions), and the interface cannot remove it. It can only avoid adding more.

#### 2.3.3 Recognition rather than recall

Recognizing something on the screen is easier than remembering it. Nielsen lists this as one (1) of his [usability heuristics↗](https://www.nngroup.com/articles/ten-usability-heuristics/), introduced in [1](#1-what-a-design-system-is), under the name "Recognition rather than recall".

In finmathly, the user never has to remember a value from another screen. The [record screen](#371-list-and-record-screens) of a security shows the rules of its modality, and the [price calculation screen](#373-the-price-calculation-screen) shows the security and the conventions it uses, as [read-only data](#369-read-only-data). A modality or an index is chosen from a list, never typed from memory, as explained in [3.6.5](#365-combo-box).

This does not conflict with [2.3.2](#232-cognitive-load): the task needs these values, and showing them spares the user the effort of remembering them, which is also cognitive load.

#### 2.3.4 Familiar patterns

People spend most of their time in other products, so they expect a new one to work like those. This observation is called [Jakob's Law↗](https://lawsofux.com/jakobs-law/), after Jakob Nielsen, the author of the usability heuristics of [1](#1-what-a-design-system-is).

finmathly therefore uses common patterns: a table that lists records and opens a record from its row, a form with its fields in reading order, a [primary button](#363-buttons-and-links) at the end of the form, and a link that looks like a link. It invents nothing new where a common pattern serves, because a new pattern makes the user learn it before using it.

### 2.4 Feedback and response time

When the user acts, such as clicking a button, they wait for the interface to answer. An answer that is missing, slow or unstable makes the user doubt the system, act twice or lose their place. The three (3) principles below set how finmathly answers each action.

#### 2.4.1 Feedback for every action

Feedback is the response of the interface to an action of the user. Without it, the user does not know whether a click worked, clicks again and may save twice.

Every action in finmathly has a visible response: (i) a button shows that it was pressed; (ii) an action that waits for the API cannot be started again until it ends, and shows its [loading state](#361-states); and (iii) the end of the action shows its result, as a success or an error [message](#262-messages).

finmathly shows a result only after the API confirms it. A price or a registration that appears and then disappears would harm [trust](#21-quality-attributes) more than a short wait.

#### 2.4.2 Response time

The [Doherty Threshold↗](https://lawsofux.com/doherty-threshold/) states that productivity rises when the system responds in less than 400 milliseconds, so that neither the person nor the system waits for the other. It is named after Walter J. Doherty, who published [the study behind it↗](https://jlelliotton.blogspot.com/p/the-economic-value-of-rapid-response.html) with Ahrvind J. Thadani, at IBM, in 1982. It is an observation, not a hard limit.

finmathly takes it as the limit beyond which the user must see that the system is working. Content that takes longer than 400 milliseconds to load shows a [skeleton](#3611-skeletons) in its place. Below that, nothing is shown in its place, so that a fast answer does not make a skeleton flash on the screen for an instant.

#### 2.4.3 Layout shift

A layout shift happens when an element moves on its own after the page has appeared, because something above it loaded late. The user can lose their place, or click the wrong thing. Google measures it as [Cumulative Layout Shift↗](https://web.dev/articles/cls), one (1) of its metrics of page quality.

In finmathly, every area that waits for data reserves at least its final height beforehand. The area of a price keeps its height while the price loads, and a table keeps its header and columns while its rows load. The height is a minimum, never a fixed value, so that text enlarged by the user is not cut, as required in [2.7](#27-accessibility).

### 2.5 Forms

A form is the set of fields where the user types or chooses the values of a record, such as the fields of a security on its record screen, or the market rate and the calculation date on the price calculation screen. The two (2) principles below set what a field accepts and what value it holds before the user types.

#### 2.5.1 Tolerant input

[Postel's Law↗](https://lawsofux.com/postels-law/) asks a system to be liberal in what it accepts and conservative in what it sends. In an interface, being liberal means accepting the small variations with which people type the same value, and being conservative means passing that value on in one (1) strict format.

In finmathly, a numeric field is liberal with spaces around the number and with a minus sign typed as a hyphen, because a [contractual rate](../../../README.md#219-contractual-rate-and-rate-factor), a [market rate](../../../README.md#225-unit-price-pu) and an [index value](../../../README.md#216-index-its-values-and-index-factor) of a daily rate can be negative. It is conservative in what it sends: the value goes to the API in the format of [README 3.8](../../../README.md#38-formats), whatever the language.

Being liberal never means guessing. Portuguese writes `1.350,25` and English writes `1,350.25`, so each language uses as its decimal separator the mark that the other uses to group thousands. A field therefore accepts only the decimal separator of the [language of the user](#263-languages-and-formats), the comma in Portuguese and the point in English, and rejects any other mark, the thousands separator included, with a [message](#262-messages) that shows the expected format. Without this rule, `1.350`, copied from an English source into a field in Portuguese, would be read as `1350` instead of `1.35`.

#### 2.5.2 Defaults

A default is the value that a field holds before the user changes it. [People tend to keep defaults↗](https://www.nngroup.com/articles/the-power-of-defaults/), so a default must be the value that most users want.

In finmathly, a field starts empty when no value is common enough to suggest, such as the [contractual rate](../../../README.md#219-contractual-rate-and-rate-factor) and the market rate, so that the user must type it. A rate kept by mistake would produce a wrong price without the user noticing. The defaults of the record screen of a security are the suggestions that [README 4.4.2](../../../README.md#442-security) sets.

The calculation date starts with the current date, the most common choice, except for a security indexed to a [monthly index number](../../../README.md#216-index-its-values-and-index-factor). There it starts empty, because finmathly calculates such a security only on a [monthly anniversary](../../../README.md#216-index-its-values-and-index-factor), a choice of its own model, explained in [README 2.4](../../../README.md#24-what-finmathly-calculates-and-what-it-doesnt). The current date is rarely one, and the frontend does not calculate which anniversary is valid, as set in [README 3.8](../../../README.md#38-formats).

### 2.6 Text and languages

Besides numbers, what the user reads in finmathly is short text, such as labels, buttons, messages and definitions, in Portuguese or in English. The four (4) principles below set how that text is written and shown.

#### 2.6.1 Voice

The voice is the way the texts of the interface are worded, such as its labels, buttons and messages. finmathly writes in a technical and direct voice, with the terms of the [glossary](../../../README.md#26-glossary), because its users are specialists who want facts. It uses no exclamation marks and no filler words.

A label is the term of the glossary, with its unit when it has one, such as "Market rate (% p.a.)". A button names the action and its object, with the verb in its base form, such as "Save security".

#### 2.6.2 Messages

A message is a sentence that the interface writes for the user. Messages have three (3) levels, so that the user knows how much a message matters before reading it:

1. information, for a consequence that needs no action, such as "This security is fixed-rate, so it has no index.". A success is also an information message, and states the fact and, when there is one, the new version, such as "Security saved as version 4.";
2. warning, for something the user should know before acting, such as "The last published IPCA value is for August 2026.";
3. error, for something that failed or is invalid, stated in the three (3) parts that [README 5.3.6](../../../README.md#536-errors-defined-out-of-existence-and-early-failure) sets: what happened, why, and the way around it, which starts with "Please".

An error has two (2) kinds. A validation error concerns the value of one (1) field. The frontend finds it while the user types, such as a number it cannot read, as set in [2.5.1](#251-tolerant-input), or the API returns it, such as a market rate of −100% or less. It sits below its field, in short sentences, such as "A market rate of −100% or less has no unit price. Please type a rate greater than −100%.".

Any other error concerns the whole action and comes from the API, such as a missing index value. It sits at the top of the form, such as "No IPCA value for September 2026, needed for 10/15/2026. The last published value is for August 2026. Please register the September value once it is published, or use a calculation date on a monthly anniversary up to 09/15/2026.".

Each level has its own appearance, set in [3.6.6](#366-message-boxes). An error of the API comes as [Problem Details↗](https://www.rfc-editor.org/rfc/rfc9457), with each value in its own field, as set in [README 3.8](../../../README.md#38-formats). The [dictionary](#263-languages-and-formats) of each language holds one (1) sentence per type of error, filled with those values in the format of the language, and the type also tells whether the error concerns a field, and which one.

A message is a sentence. Values that come from another record are not written as a sentence but shown as [read-only data](#369-read-only-data).

#### 2.6.3 Languages and formats

The interface is available in Portuguese (`pt-BR`) and English (`en-US`), as set in [README 3.8](../../../README.md#38-formats), which also sets the format of numbers, dates and times in each language.

On the first visit, the interface follows the language of the browser: Portuguese if its code starts with `pt`, and English otherwise. The user changes it in the [top bar](#343-layout-and-responsiveness), through a [selector](#363-buttons-and-links) of two (2) options, PT and EN, and the choice is kept for the next visits.

All visible text comes from a dictionary, a file that holds every text of the interface in one (1) language, so that no screen has text written in its code. Every label uses the term of the [glossary](../../../README.md#26-glossary) in its language, as set in [2.6.1](#261-voice). A code of the API, such as `BUSINESS_252`, is never shown as it comes: it has a label in each language, such as "Dias úteis sobre 252" and "Business days over 252".

#### 2.6.4 Concept tips

A concept tip is a short definition that opens next to the name of a concept of the domain. It serves a user who needs to recall a definition without leaving the screen.

Only the concepts of the [glossary](../../../README.md#26-glossary) get a tip, never an ordinary field such as a name. The text of a tip follows the definition in the README, in one (1) or two (2) sentences. When a definition changes in the README, its tip changes in the same commit.

The tip opens from a button with the [information icon](#352-icons), by click, tap or keyboard, and never when the pointer or the keyboard merely passes over it, so that it never covers content the user did not ask to cover. It stays open until the user closes it with Esc, a second click or a click outside it. It floats above the page as set in [3.5.1](#351-corners-borders-and-shadows).

### 2.7 Accessibility

Accessibility is the condition that people with disabilities can use the interface: people who do not distinguish some colors, who see poorly or enlarge the screen, who use a screen reader, a program that reads the screen aloud, who use only a keyboard, or who touch the screen with less precision. The reference is the [Web Content Accessibility Guidelines (WCAG) 2.2↗](https://www.w3.org/TR/WCAG22/), which has three (3) levels, A, AA and AAA, each stricter than the one before. finmathly meets level AA.

Every screen meets these requirements:

1. text contrast of at least 4.5:1, and 3:1 for what identifies a button or a field, as explained in [3.2.3](#323-contrast);
2. no information carried by color alone, as explained in [3.2.4](#324-state-colors);
3. every action [reachable with the keyboard↗](https://www.w3.org/TR/WCAG22/#keyboard), in reading order, with no place where the keyboard gets stuck, except inside an open dialog, which Esc closes;
4. a visible focus, the mark that shows where the keyboard is, never hidden under the top bar, as explained in [3.6.1](#361-states);
5. a [label on every field↗](https://www.w3.org/TR/WCAG22/#labels-or-instructions), with its error message linked to it, as explained in [3.6.4](#364-fields);
6. click targets of at least 24 by 24 pixels, as explained in [3.6.2](#362-affordance-and-click-targets);
7. a layout that [works at a width of 320 pixels↗](https://www.w3.org/TR/WCAG22/#reflow) and with text [enlarged to 200%↗](https://www.w3.org/TR/WCAG22/#resize-text), as explained in [3.4.3](#343-layout-and-responsiveness) and [3.3.2](#332-type-scale-and-weights);
8. no text cut when the user [increases the spacing↗](https://www.w3.org/TR/WCAG22/#text-spacing) of lines, letters, words or paragraphs, because every height is a minimum, as explained in [2.4.3](#243-layout-shift);
9. loading states, success messages and errors [announced to screen readers↗](https://www.w3.org/TR/WCAG22/#status-messages) without moving the focus;
10. the [language of the page↗](https://www.w3.org/TR/WCAG22/#language-of-page) declared, and updated when the user changes it;
11. tables with header cells, headings in order, and a [title on every page↗](https://www.w3.org/TR/WCAG22/#page-titled).

Beyond level AA, finmathly also: (i) turns motion off for a user who asked for less motion, as explained in [3.5.3](#353-motion); (ii) draws the focus as the AAA criterion for its appearance asks, as explained in [3.6.1](#361-states); and (iii) meets the line height and the width of the AAA criterion for blocks of text, as explained in [3.3.3](#333-line-height-and-line-length).

## 3. User interface

User interface covers how each screen looks, from the basic choices of color, letters and space to the components and the complete screens built from them.

### 3.1 Brand

The brand of finmathly is what makes it recognizable at a glance: how it feels to use, and its logo and name. The two (2) parts below set both.

#### 3.1.1 Personality and references

The personality of a brand is the set of qualities that a person should feel when using the product. finmathly should feel precise, sober and trustworthy, like a reference document of the market, and never playful or decorative. The personality follows from the [quality attributes](#21-quality-attributes).

The design system draws on two (2) references, as inspiration and never as a copy:

1. the Financial Times, for its tinted paper background instead of pure white, its dense tables with thin rules and its restrained use of color. finmathly does not use its paid typefaces or its salmon tint, which belong to the identity of the Financial Times;
2. the [Swiss Style↗](https://en.wikipedia.org/wiki/International_Typographic_Style), also called the International Typographic Style, for its simple letters without [serifs](#331-typefaces), its hierarchy through size and boldness, and its white space.

The discarded alternative was the bright and rounded look of consumer apps, which would make a reference system look like a game.

#### 3.1.2 Logo and product name

The logo is a square in `#0F5C57` with the letters `fm` in white, in IBM Plex Mono SemiBold. It is the file [`docs/logo.svg`](../../../docs/logo.svg), with its letters drawn as shapes rather than typed as text, so that it looks the same everywhere, even where IBM Plex is not installed. It is always used from that file, never redrawn or retyped.

The logo is fixed: it has the same colors in the [light and the dark theme](#322-light-and-dark-themes), so that the brand has one (1) appearance. It is also the icon of the browser tab, and finmathly does not control the theme of the browser around that tab. The logo is therefore the only element of the interface whose colors are written directly in it, instead of coming from the theme through a [design token](#51-tokens-in-css).

Next to the logo, the interface writes the product name, `finmathly`, always in lowercase, as text in IBM Plex Sans SemiBold, at the [body size](#332-type-scale-and-weights), in the [ink](#321-palette-and-roles) of the theme. Next to the product name, the logo is hidden from screen readers, so that they do not read the name twice.

### 3.2 Color

#### 3.2.1 Palette and roles

A palette is the closed set of colors of a product. Each color in it has a role, a job it does on every screen, and no color is used outside its role. A closed palette keeps every screen consistent and keeps the meaning of each color stable.

finmathly has two (2) themes, light and dark, explained in [3.2.2](#322-light-and-dark-themes), and each role has a value in each:

| Role | Light | Dark | Use |
| --- | --- | --- | --- |
| Paper | `#FAF7F2` | `#1A1917` | Background of the page |
| Surface | `#FFFFFF` | `#22211E` | Background of fields, panels and of what floats above the page |
| Ink | `#1F1D1A` | `#EDE9E2` | Text and numbers |
| Muted ink | `#5E5A53` | `#A8A39A` | Labels, secondary text, the text of a disabled control and the left border of an information message box |
| Rule | `#DDD7CD` | `#3A3833` | Thin rules between rows and around what has the surface background, the border of a disabled control, and skeletons |
| Strong rule | `#1F1D1A` | `#EDE9E2` | Rule under a table header and border of a field |
| Accent | `#0F5C57` | `#5FB8AE` | Links, buttons and the focus |
| Soft accent | `#E4EFED` | `#1F3330` | Background of what is under the mouse or chosen, as set in [3.6.1](#361-states) |
| Accent hover | `#0A4440` | `#86CEC5` | Fill of the primary button and color of a link under the mouse |
| Text on accent | `#FFFFFF` | `#1A1917` | Text on the accent and on the accent hover |
| Error | `#A3261D` | `#F08A7E` | Word, text and border of an error |
| Soft error | `#F7E3E0` | `#3A2220` | Background of an error message box |
| Warning | `#7A5200` | `#E0B45C` | Word and border of a warning |
| Soft warning | `#F6ECD6` | `#352B18` | Background of a warning message box |
| Backdrop | `#1F1D1A` at 40% | `#000000` at 60% | Layer that covers the screen behind a dialog |

The paper is warm, slightly yellow, rather than pure white, to recall printed financial reports. Forms and tables sit directly on it, as in a printed report, and the white surface is kept for what must stand out from the page: fields, [panels](#342-grouping) and what floats above the page. The discarded alternative was a white surface under every form and table, which would turn each one into a card, a box around a block, as explained in [4](#4-patterns-to-avoid).

The accent, a deep petrol green, is the only strong color, and it is used only for what the user can act on: (i) links; (ii) the fill of the primary button; (iii) the border and the text of the secondary button; and (iv) the focus. The discarded alternatives were navy blue, common to almost every bank, and burgundy, too close to the color of an error.

A financial value is never colored by its meaning. A [premium or a discount](../../../README.md#23-reading-a-price) is written in words, never shown in green or red, because a color that means good or bad would compete with the accent and add a judgment that the system does not make.

#### 3.2.2 Light and dark themes

A theme is a full set of values for the palette. finmathly has two (2): a light theme and a dark theme, with the same roles and different values, as listed in [3.2.1](#321-palette-and-roles). On the first visit, the interface follows the theme of the operating system. The user changes it in the [top bar](#343-layout-and-responsiveness), through a [selector](#363-buttons-and-links) of two (2) options, Light and Dark. The page also declares its theme to the browser with the CSS property [`color-scheme`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Properties/color-scheme), so that scroll bars and native controls follow it.

The dark values are not an inversion of the light ones. Each was chosen to keep the contrast of [3.2.3](#323-contrast), and the accent becomes lighter in the dark theme so that it remains visible on the dark paper.

#### 3.2.3 Contrast

Contrast is the difference in brightness between a text and its background. Low contrast makes text hard to read, above all for people who see poorly and for anyone in front of a bright window. WCAG measures it as a ratio from 1:1, no difference, to 21:1, black on white, and requires [at least 4.5:1 for normal text↗](https://www.w3.org/TR/WCAG22/#contrast-minimum).

Every pair of text and background in finmathly meets 4.5:1, in both themes. The ratios below were calculated with the formula of WCAG from the values in [3.2.1](#321-palette-and-roles):

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
| Text on accent hover | 10.97 | 9.74 |
| Accent hover on paper | 10.27 | 9.74 |
| Accent hover on surface | 10.97 | 8.93 |
| Ink on soft accent | 14.31 | 11.02 |
| Ink on soft error | 13.63 | 12.14 |
| Error on paper | 6.89 | 7.23 |
| Error on soft error | 5.97 | 6.04 |
| Error on surface | 7.36 | 6.63 |
| Ink on soft warning | 14.32 | 11.49 |
| Warning on soft warning | 5.89 | 7.19 |
| Warning on surface | 6.92 | 8.32 |

The thin rule between table rows has a ratio to the paper of 1.34 in the light theme and 1.50 in the dark theme, below the [3:1 that WCAG requires for what identifies a control↗](https://www.w3.org/TR/WCAG22/#non-text-contrast). It is allowed because it only separates rows and areas and identifies no active control, and WCAG exempts the border of a disabled control. The border of a field, which identifies a control, uses the strong rule, at 15.73 on the paper in the light theme and 14.52 in the dark theme.

#### 3.2.4 State colors

A state color tells the level of a [message](#262-messages): brick red for an error, ocher for a warning. finmathly has no success color, because a green would be confused with the accent. A success is an information message, in neutral colors, such as "Security saved as version 4.".

Color never carries meaning alone, as [WCAG requires↗](https://www.w3.org/TR/WCAG22/#use-of-color). A state color always comes with a word and a border, so that a user who does not distinguish red from green still reads the level of the message.

#### 3.2.5 Building the palette

The palette is stored in hexadecimal RGB, the format that every tool reads. A color is added or adjusted in [OKLCH↗](https://www.w3.org/TR/css-color-4/#ok-lab), a model whose lightness matches what the eye perceives, so that two (2) colors with the same lightness look about equally bright. The contrast of [3.2.3](#323-contrast) is then calculated again.

The discarded alternative was HSL, whose lightness does not match what the eye perceives: a yellow and a blue with the same HSL lightness look very different in brightness.

### 3.3 Typography

Typography is the choice and arrangement of letters: which typefaces, in which sizes, weights and spacing. In finmathly, most of what a user reads is numbers and short labels, so typography is chosen first for numbers.

#### 3.3.1 Typefaces

A typeface is a family of letters with a common design, such as IBM Plex. It comes in members with different designs, such as Sans and Mono, and each member comes in weights, the technical name for the boldness of its letters, from thin to bold.

finmathly uses two (2) members of [IBM Plex↗](https://github.com/IBM/plex), which is free under the SIL Open Font License:

1. IBM Plex Sans, for all text and numbers, except the three (3) uses of Mono below. Its letters have no serifs, the small strokes at the ends of letters, which suits the [Swiss Style](#311-personality-and-references), and its figures are tabular, as explained in [3.3.4](#334-financial-numbers);
2. IBM Plex Mono, a monospaced member, in which every character has the same width. It sets only three (3) things: (i) identifiers, such as the code of a security or a UUID; (ii) the decimal reference of a rate or percentage field, as explained in [3.6.4](#364-fields); and (iii) the letters of the logo.

A financial value shown for reading is never set in Mono, because Mono gives the decimal and thousands separators the full width of a digit, which breaks a long number into gaps. The discarded alternatives were a pairing with a serif typeface for titles, which would give the editorial look that the design system avoids, and Inter, whose name would be confused with Banco Inter.

#### 3.3.2 Type scale and weights

A type scale is the short, fixed list of text sizes that a product uses. With a scale, two (2) sizes that are almost equal never appear side by side, and the size of a text tells its importance, as required by [visual hierarchy](#231-visual-hierarchy).

| Role | Size | Weight |
| --- | --- | --- |
| Page title | 24px | SemiBold (600) |
| Key figure, such as the unit price | 28px | Medium (500) |
| Secondary figure, such as the par unit price next to the unit price | 20px | Medium (500) |
| Section title | 16px | SemiBold (600) |
| Body text, form fields and buttons | 16px | Regular (400) |
| Table cells | 14px | Regular (400) |
| Labels | 12px | Medium (500) |
| Hints, notes and validation errors | 12px | Regular (400) |
| Table headers | 12px | SemiBold (600), uppercase |

Form fields are 16px, the size below which [Safari on iOS enlarges the page↗](https://css-tricks.com/16px-or-larger-text-prevents-ios-form-zoom/) when a field gets the focus. Labels, hints and table headers share 12px and differ by weight and case.

The sizes are stored in rem, a unit relative to the text size of the root element of the page, at 1 rem per 16px, so that 12px is 0.75rem. finmathly never sets that root size, so that it stays the size that the user set in the browser and the whole scale follows it. The discarded alternative was a scale with 12, 13, 14 and 15px, whose neighbors were too close to read as different.

Only three (3) weights are used: (i) Regular for text and numbers; (ii) Medium for figures and labels; and (iii) SemiBold for titles, section titles, table headers and the product name. More weights would create differences too small to read as hierarchy.

Uppercase is used only for table headers, which are short, with a letter spacing of 6% of the size, because text in uppercase reads tighter than lowercase. [*Practical Typography*↗](https://practicaltypography.com/letterspacing.html), by Matthew Butterick, recommends between 5% and 12% for uppercase, and finmathly takes a value near the bottom, because its headers are small and dense. Longer uppercase text, such as a subtitle, is avoided, because it reads slower than lowercase.

#### 3.3.3 Line height and line length

Line height, also called leading, is the vertical distance between two (2) lines of text. Line length is the number of characters on one (1) line. Both decide how comfortably a paragraph reads: lines too close or too long make the eye lose the start of the next line.

Body text in finmathly has a line height of 1.5 times its size. Table cells have 1.4, to keep tables dense, and titles and figures have 1.2, because they rarely wrap. Paragraphs, such as messages and tips, are at most 65 characters wide, within the 45 to 90 characters that [*Practical Typography*↗](https://practicaltypography.com/line-length.html) recommends.

The line height and the width of body text also meet two (2) of the requirements of the [WCAG criterion for blocks of text↗](https://www.w3.org/TR/WCAG22/#visual-presentation): a line spacing of at least 1.5 and a width of at most 80 characters. That criterion is at level AAA, above the AA that finmathly is required to meet.

#### 3.3.4 Financial numbers

Numbers are the main content of finmathly, and four (4) rules make them exact and easy to compare:

1. every number that can appear in a column uses tabular figures, in which every digit has the same width, so that the units, tens and decimal places of a column line up. IBM Plex Sans draws its figures this way by default, and the CSS still asks for them with [`font-variant-numeric: tabular-nums`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Properties/font-variant-numeric), so that a fallback typeface also lines them up;
2. numeric columns are aligned to the right, so that the decimal separators line up whenever the values of a column have the same number of decimal places;
3. a value shows every digit that the API returns, as set in [README 3.8](../../../README.md#38-formats). The frontend never rounds, truncates or recalculates a value. It sets both the minimum and the maximum number of decimal places of [`Intl.NumberFormat`↗](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/NumberFormat/NumberFormat) to those of the value, because by default it keeps at most three (3) and drops trailing zeros. It passes the string that the API returns, never a JavaScript number, so that it formats [the exact value of the string↗](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/NumberFormat/format);
4. a rate or a percentage is shown and typed as a percentage, as set in [README 3.8](../../../README.md#38-formats). A rate has its period in the label, `% a.a.` in Portuguese and `% p.a.` in English, because every rate is an [effective annual rate](../../../README.md#251-effective-annual-rates), and the [index percentage](../../../README.md#216-index-its-values-and-index-factor) has only `%`.

Some financial interfaces set the last decimal places smaller or lighter, to bring the integer part forward. finmathly does not, because the user checks exactly those last digits, and [precision](#21-quality-attributes) comes first.

### 3.4 Space and layout

#### 3.4.1 Spacing scale

A spacing scale is the fixed list of distances that may separate elements. finmathly uses 4, 8, 12, 16, 20, 24 and 32 pixels. A scale makes equal relations look equal on every screen: the space between a label and its field is always 4 pixels, between two (2) fields always 16 pixels, and between two (2) sections of a form always 32 pixels.

The discarded alternative was to choose each distance by eye, which produces distances such as 13 and 15 pixels that the eye cannot tell apart but that break the alignment of a screen.

#### 3.4.2 Grouping

People see elements as a group when they are close to each other, inside the same area or similar in appearance. The Gestalt laws of [proximity↗](https://lawsofux.com/law-of-proximity/), [common region↗](https://lawsofux.com/law-of-common-region/) and [similarity↗](https://lawsofux.com/law-of-similarity/) describe these three (3) cases.

finmathly groups with space first. The label of a field is closer to its field than to the field above, and the sections of a form are separated by more space than the fields inside them. A panel, an area with the surface background and a thin rule around it, is used only to separate a group that the space alone does not separate, such as the calculation details below a price. Elements with the same role, such as all the values of a column, always look the same.

#### 3.4.3 Layout and responsiveness

The layout of a screen is the arrangement of its areas. Every screen of finmathly has a top bar and a content area below it. The top bar holds the logo, the navigation and, at its right, the selectors of language and theme. The content area is at most 1,180 pixels wide and centered on large screens, so that the eye does not cross a whole wide monitor to follow a row.

Responsiveness is the ability of a layout to adapt to the width of the screen. finmathly is a web application, and every screen works well from a width of 320 pixels, the width of a small phone, up to a large monitor. From 720 pixels up, the fields of a form sit in two (2) columns. Below 720 pixels, where two (2) columns no longer fit at a comfortable width: (i) the fields stack in one (1) column; (ii) the top bar keeps the logo and the selectors and folds the navigation into a menu; and (iii) a wide table scrolls sideways inside its own area, never the whole page.

### 3.5 Shape, icons and motion

#### 3.5.1 Corners, borders and shadows

Fields and buttons have almost square corners, with a radius of 2 pixels, and every other element, such as a panel, a dialog or a message box, has none. The square shape recalls printed reports and tables, as in the [references](#311-personality-and-references). The discarded alternative was large rounded corners, common in consumer apps, which would soften the sober look that the [personality](#311-personality-and-references) asks for.

Every rule and border is 1 pixel wide. Panels are separated by a thin rule, never by a shadow.

A shadow suggests that an element floats above the page and blocks it, so the only element with a shadow is a [dialog](#3610-confirmation-dialogs). A concept tip, the list of a combo box and the navigation menu on a phone also open above the page, but block nothing: they have the surface background, a thin rule around them and no shadow. The shadow of a dialog is `0 8px 24px` in the backdrop color, over the [backdrop](#321-palette-and-roles), and in the dark theme, where a shadow barely shows, the dialog also has a thin rule around it.

#### 3.5.2 Icons

An icon is a small image that stands for an action or an idea. finmathly uses few icons, because most of its actions are clearer as words, and an icon always comes with visible text or an accessible name. Every icon is drawn in SVG, never typed as a character, because a character missing from IBM Plex is drawn by another font, which differs from system to system.

The only icon with a fixed meaning is the information icon, a lowercase i in a circle, which opens a [concept tip](#264-concept-tips) and is never used for anything else.

#### 3.5.3 Motion

Motion is any change on the screen that happens over time, such as a color that fades or an element that slides. Motion helps the user follow a change, but too much of it distracts and slows down a frequent task.

finmathly uses almost no motion. Only changes of color or opacity are animated, such as the hover of a button, and they take 100 milliseconds, with an ease-out curve, which starts fast and slows down at the end, as the Nielsen Norman Group [recommends for simple feedback↗](https://www.nngroup.com/articles/animation-duration/).

Nothing slides, bounces, pulses or appears in sequence. For a user who asked the operating system for less motion, through the setting that the browser exposes as [`prefers-reduced-motion`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/At-rules/@media/prefers-reduced-motion), even these changes are instant.

### 3.6 Components

A component is a reusable part of a screen, such as a button or a table, built from the decisions of [3.2](#32-color) to [3.5](#35-shape-icons-and-motion). Every screen is assembled from components, so a component is decided once and looks the same everywhere.

The frontend is written in TypeScript and React, a library that builds an interface from components. It builds its own components from the [tokens](#5-design-tokens), without a component library. The discarded alternative was a component library, which brings its own corners, shadows, colors and messages, and would have to be fought on every screen to follow this skill.

#### 3.6.1 States

A state is a condition of a component that changes how it looks. Every component of finmathly has these states, when they apply:

1. normal, at rest;
2. hover, when the mouse is over it: the primary button and a link take the accent hover, a secondary button and a row of a list take the soft accent background, and an option of a combo box becomes its active option, as set in [3.6.5](#365-combo-box);
3. focus, when the keyboard is on it, shown by an outline of 2 pixels in the accent, 2 pixels away from the component, so that it shows even on the primary button. WCAG requires at level AA that [the focus be visible↗](https://www.w3.org/TR/WCAG22/#focus-visible) and [not entirely hidden by other content↗](https://www.w3.org/TR/WCAG22/#focus-not-obscured-minimum), such as the top bar. finmathly never hides any part of it, by keeping the scroll of the page clear of the top bar. The 2 pixels come from the [criterion for the appearance of the focus↗](https://www.w3.org/TR/WCAG22/#focus-appearance), at level AAA;
4. error, for a field with an invalid value, as explained in [3.6.4](#364-fields);
5. loading, while an action waits for the API, as required in [2.4.1](#241-feedback-for-every-action): the component keeps its size, cannot be activated again, and its text names the action in progress, such as "Saving security", with no spinner, as set in [3.5.3](#353-motion);
6. empty, for a list or table with no records, with a sentence that says so and the action that creates the first one;
7. disabled, for an action that cannot be done yet, with its text in the muted ink, its border in the rule and its reason close to it. A disabled button is marked as disabled for screen readers but stays reachable with the keyboard, so that a screen reader also reads its reason.

A component that lacks any of these states, where it applies, is not finished.

#### 3.6.2 Affordance and click targets

Affordance is the quality of an element that shows how it can be used: a button looks pressable, a link looks clickable and a field looks fillable. Without it, the user does not find the action, or tries to click what does nothing.

In finmathly, buttons, links and fields have the shapes set in [3.6.3](#363-buttons-and-links) and [3.6.4](#364-fields), and text that is not clickable is never underlined or in the accent.

[Fitts's Law↗](https://lawsofux.com/fittss-law/) states that a target is faster to reach the larger and the closer it is. WCAG sets a [minimum target of 24 by 24 pixels↗](https://www.w3.org/TR/WCAG22/#target-size-minimum). finmathly uses at least 32 pixels of height for every button, field and select, and 40 pixels for a primary button, enough for a mouse. A link inside a sentence or a table cell keeps the height of its line, as WCAG allows.

When the main pointer is a finger, which CSS detects with [`pointer: coarse`↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/At-rules/@media/pointer), every target is at least 44 by 44 pixels, as [Apple recommends↗](https://developer.apple.com/design/human-interface-guidelines/accessibility). The discarded alternative was 44 pixels everywhere, which would waste the density that the analysts need on a large screen.

#### 3.6.3 Buttons and links

finmathly has two (2) kinds of button and one (1) kind of link:

1. the primary button, filled with the accent, for the main action of a screen, such as "Save security". There is at most one (1) per screen, and one (1) per dialog, so that the main action is always clear;
2. the secondary button, with an accent border and no fill, for other actions, such as "Add event";
3. the link, underlined and in the accent, for navigation to a screen that shows data, such as "Calculate prices", which opens the [price calculation screen](#373-the-price-calculation-screen). Creating or closing a record is an action, and uses a button.

A selector, such as the language and the theme in the top bar, is a group of two (2) buttons. The chosen one has the soft accent background and its text in the accent, and is marked as pressed for screen readers.

The text of a button says the action and its object, as set in [2.6.1](#261-voice). A button that only says "OK" or "Yes" forces the user to read the rest of the screen to know what it does.

#### 3.6.4 Fields

A field has a label above it, a border in the strong rule and, when it applies, an error message below it. The label is always visible above the field, never written inside it, where it would disappear as the user types, taking with it what the field asks for.

A field with an invalid value has a border in the error color and, below it, its error message, in the error color, with no background, linked to the field so that a screen reader reads them together.

A number is typed as set in [2.5.1](#251-tolerant-input). A numeric field keeps its value as the decimal that the API receives, and only shows it in the format of the language, so a change of language shows every field in the new format without changing any value. While the user types, the field shows only digits, the minus sign and the decimal separator. When the user leaves it, it shows the value with the thousands separator, as set in [README 3.8](../../../README.md#38-formats), so that a number such as the [face value at issue](../../../README.md#213-face-value-and-face-value-at-issue-vne) of `1.000,00` of an NTN-B is easy to read.

The discarded alternative was a mask that inserts the separators as the user types, common in banking apps. It works for amounts with a fixed number of decimal places, such as cents, but values in finmathly have a variable number, such as rates with six (6) places and [index values](../../../README.md#253-rounding-and-truncation) with any number.

A date is typed in the format of the language, as set in [README 3.8](../../../README.md#38-formats).

A rate or percentage field is typed as a percentage, as set in [3.3.4](#334-financial-numbers). Below it, a decimal reference in IBM Plex Mono shows the decimal value that is sent to the API, in the format of the API whatever the language, such as `Decimal = 0.135678` for 13,5678 typed in Portuguese.

It shows the decimal because the [maximum number of decimal places](../../../README.md#253-rounding-and-truncation) of a rate or a percentage is counted in the decimal form, which the user does not see while typing a percentage. The conversion moves the decimal point on the text of the number, without converting it to a binary floating-point number, so that no digit is lost or added.

#### 3.6.5 Combo box

A combo box is a field that combines typing and choosing: the user types part of a name and picks it from a filtered list. Its behavior for the keyboard and for screen readers follows the [combo box pattern of the W3C↗](https://www.w3.org/WAI/ARIA/apg/patterns/combobox/). The active option of its list has the soft accent background.

finmathly uses it to choose the modality on the record screen of a security, because modalities grow with the market and a plain list would force the user to scroll it. Its list floats above the page as set in [3.5.1](#351-corners-borders-and-shadows).

The index is chosen from a native select of the browser, because the indexes are few, such as the IPCA, the IGP-M, the DI and the Selic, and a native select is accessible and fits a phone without more work. The discarded alternative was a combo box for both, which is the hardest of the two (2) to build correctly and gives nothing for a short list.

#### 3.6.6 Message boxes

A message box shows a [message](#262-messages) of the information, warning or error level. It has a background, a left border and, for a warning or an error, a word that names its level:

1. information, on the paper, with a left border in the muted ink and no word;
2. warning, on the soft warning, with a left border in the warning color and the word "Warning";
3. error, on the soft error, with a left border in the error color and the word "Error".

The word is in the color of its level, and the sentence in the ink. In a warning or an error, the left border marks the level together with the word and the background, as required in [3.2.4](#324-state-colors). In an information, the left border alone sets the message apart from the text around it. The left border is never used on other elements, as explained in [4](#4-patterns-to-avoid). A validation error is not a message box: it sits below its field, as set in [3.6.4](#364-fields).

#### 3.6.7 Tables

A table shows records or values in rows and columns, and it is the main component of finmathly. It has a strong rule under its header, thin rules between rows, no vertical rules and no alternating row colors. Numeric columns follow [3.3.4](#334-financial-numbers), and text and date columns are aligned to the left.

A row of a list opens its record, as in [2.3.4](#234-familiar-patterns). Its first cell is a link to the record, so that the keyboard reaches it and the user can open it in a new tab, and the whole row also opens the record when it is clicked.

#### 3.6.8 Prices and calculation details

A price, either the [unit price](../../../README.md#225-unit-price-pu) or the [par unit price](../../../README.md#224-par-unit-price-pu-par), is shown as a key figure, at the largest size of the [type scale](#332-type-scale-and-weights), with its label above it. When a security has both, the unit price is the key figure and the par unit price a secondary figure next to it. It never appears without its calculation details, the steps that show how it was calculated, which the API returns with it:

1. the unit price comes with the payments of its [payment schedule](../../../README.md#223-payment-schedule) after the calculation date, each with its present value, and the unit price as their total. The total is cut by the output rule, so it may differ from the sum of the column in its last decimal places, as explained in [README 2.5.3](../../../README.md#253-rounding-and-truncation);
2. the par unit price comes with its components, shown as [read-only data](#369-read-only-data) in the order of its formula.

When the API returns the [reading of a price](../../../README.md#23-reading-a-price), at par, at a premium or at a discount, the interface shows it in words, next to the values it compares: the two (2) prices or, without a par unit price, the unit price and the [face value at issue](../../../README.md#213-face-value-and-face-value-at-issue-vne) (VNE).

#### 3.6.9 Read-only data

Read-only data is a set of labels and values that the user cannot change on that screen, such as the rules of the modality of a security. Each label sits above its value, aligned with the fields of the form around it, but without a border, so that it never looks editable. Labels and values are the same as on the screen where that data is edited.

#### 3.6.10 Confirmation dialogs

A dialog is an area that opens above the screen and asks for a decision before the user goes on. Its behavior for the keyboard and for screen readers follows the [dialog pattern of the W3C↗](https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/), and its focus starts on the button that keeps the work, which is its primary button.

finmathly opens a dialog only before an action that loses work or deletes a record. Its title names the action, such as "Discard changes?", and not "Are you sure?". Its buttons name their results, such as "Discard changes" and "Keep editing", never "Yes" and "No".

When the user leaves a record screen with unsaved changes, by its close button or by navigation inside finmathly, the dialog asks whether to discard them, so that the close button never discards work silently. When the user closes the browser tab, only the dialog of the browser can ask, and its text cannot be changed.

#### 3.6.11 Skeletons

A skeleton is a shape in the rule color that takes the place and the size of content that is still loading, such as a price or the rows of a table. It shows that the content is coming and reserves its space, which prevents a [layout shift](#243-layout-shift). It does not pulse, as set in [3.5.3](#353-motion).

### 3.7 Screens

#### 3.7.1 List and record screens

Securities, modalities and indexes each have two (2) screens:

1. a list screen, with a search field, a primary button that creates a record, such as "Register security", and a table with one (1) row per record;
2. a record screen, the same for creating and for editing, with the fields of the record, a primary button that saves it and a secondary button that closes it.

A record that exists only inside another record is edited on the record screen of its owner, as in the [class diagram](../../../README.md#41-class-diagram): the [events](../../../README.md#2110-events) on the record screen of the security, and the [index values](../../../README.md#216-index-its-values-and-index-factor) on the record screen of the index.

An index can have thousands of values, so they form a table filtered by a range of reference dates, the most recent first, and each value is added or corrected and saved on its own, because each has its own version, as explained in [README 4.4.5](../../../README.md#445-index-and-index_value). A correction changes only the value, so the reference date of a saved value is read-only, and a new value of a monthly index number is chosen as a month, because its reference date is always the first day of that month.

The record screens also follow three (3) rules of the [README](../../../README.md#4-data-model): (i) a modality or an index that is [locked](../../../README.md#441-modality) shows its data as read-only data, with an information message that says why it cannot be changed; (ii) a security or an index value is never deleted, only corrected, and a modality or an index is deleted only while it is not locked, after a [confirmation dialog](#3610-confirmation-dialogs); and (iii) a save of a security or an index value that the API rejects, because the stored version differs from the one the user read, shows an error that names the version the user read and asks the user to reload the record.

#### 3.7.2 The record screen of a security

The record screen of a security has four (4) sections: identification, dates, remuneration and events. Choosing the modality shows its rules as [read-only data](#369-read-only-data) and hides the fields that the modality does not allow. An information message explains each absence, such as that of an index on a fixed-rate security.

#### 3.7.3 The price calculation screen

The price calculation screen opens from the record screen of a security, by the link "Calculate prices", and shows the calculations that apply to it, as set in [README 2.4](../../../README.md#24-what-finmathly-calculates-and-what-it-doesnt). When a calculation does not apply, the screen says why instead of hiding it. The unit price, or the only price that applies, comes first and its [calculation details](#368-prices-and-calculation-details) follow below it, always visible, as explained in [2.3.1](#231-visual-hierarchy). Hiding the details behind a button would break [precision](#21-quality-attributes).

## 4. Patterns to avoid

Some visual patterns have become so common in AI-generated interfaces that they mark a screen as made without decisions. A pattern is not wrong in itself: it is wrong when it appears by habit, with no role. finmathly avoids these:

| Pattern | Why it is avoided | What finmathly does instead |
| --- | --- | --- |
| Everything in a card | Boxes around every block flatten the [hierarchy](#231-visual-hierarchy) and waste space | Groups by space, and uses a panel only where space is not enough, as in [3.4.2](#342-grouping) |
| A rule between every block | Rules everywhere make the screen noisy | Separates with space, and keeps rules for tables, fields and what has the surface background |
| Many shades and transparencies of one (1) color | Colors outside the palette break its roles | Uses only the roles of [3.2.1](#321-palette-and-roles), where the backdrop is the only transparency |
| Gradient and decorative backgrounds | They compete with the content | Uses the flat paper |
| A colored left border as decoration | It suggests a state that does not exist | Uses it only for the level of a [message box](#366-message-boxes) |
| Uppercase subtitles | Long uppercase text reads slowly | Uses uppercase only for table headers, as in [3.3.2](#332-type-scale-and-weights) |
| Monospaced text everywhere | It makes reading slower, and Mono no longer marks identifiers | Uses Mono only where [3.3.1](#331-typefaces) lists it |
| Made-up numbers and statistics | They destroy [trust](#21-quality-attributes) in a reference system | Shows only values that come from the API |

## 5. Design tokens

### 5.1 Tokens in CSS

A design token is a decision of the design system stored under a name, such as the accent color stored as `--color-accent`. The code uses the name, never the value, so that a decision changes in one (1) place and every screen follows.

In CSS, the tokens are [custom properties↗](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Cascading_variables/Using_custom_properties), defined once for the light theme and defined again, with the same names, for the dark theme. The dark values apply when the user chose the dark theme or, before any choice, when the operating system asks for it. A component never knows which theme is active, and no component has a literal color, typeface, size or space. The logo is the only exception, as explained in [3.1.2](#312-logo-and-product-name).

```css
:root {
  color-scheme: light;
  --color-accent: #0F5C57;
  /* ... */
}

@media (prefers-color-scheme: dark) {
  :root:not([data-theme="light"]) {
    color-scheme: dark;
    --color-accent: #5FB8AE;
    /* ... */
  }
}

:root[data-theme="dark"] {
  color-scheme: dark;
  --color-accent: #5FB8AE;
  /* ... */
}
```

CSS does not read a custom property in a media query, so two (2) values are written there as literals: (i) the breakpoint, as `45em`, which is 720 pixels at the default text size and follows the text size that the user set; and (ii) `pointer: coarse`, under which `--height-control` and `--height-control-primary` are redefined to 44 pixels.

### 5.2 Token names

Each token is named by its role, never by its value, so that `--color-accent` stays correct if the accent changes from green to another color. The spacing tokens are the exception: a step of the scale has no role other than its size.

| Group | Tokens | Values in |
| --- | --- | --- |
| Color | `--color-paper`, `--color-surface`, `--color-ink`, `--color-ink-muted`, `--color-rule`, `--color-rule-strong`, `--color-accent`, `--color-accent-hover`, `--color-accent-soft`, `--color-on-accent`, `--color-error`, `--color-error-soft`, `--color-warning`, `--color-warning-soft`, `--color-backdrop` | [3.2.1](#321-palette-and-roles) |
| Typeface | `--font-sans`, `--font-mono` | [3.3.1](#331-typefaces) |
| Size | `--font-size-title`, `--font-size-key-figure`, `--font-size-figure`, `--font-size-body`, `--font-size-table`, `--font-size-small`, where the section title shares `--font-size-body` | [3.3.2](#332-type-scale-and-weights) |
| Weight | `--font-weight-regular`, `--font-weight-medium`, `--font-weight-semibold` | [3.3.2](#332-type-scale-and-weights) |
| Letter spacing | `--letter-spacing-caps` | [3.3.2](#332-type-scale-and-weights) |
| Line height | `--line-height-body`, `--line-height-table`, `--line-height-title`, the last one also used by figures | [3.3.3](#333-line-height-and-line-length) |
| Space | `--space-4`, `--space-8`, `--space-12`, `--space-16`, `--space-20`, `--space-24`, `--space-32` | [3.4.1](#341-spacing-scale) |
| Width | `--width-content`, `--width-text` | [3.4.3](#343-layout-and-responsiveness) and [3.3.3](#333-line-height-and-line-length) |
| Shape | `--radius-control`, `--border-width`, `--shadow-dialog` | [3.5.1](#351-corners-borders-and-shadows) |
| Motion | `--duration-fade`, `--easing-out` | [3.5.3](#353-motion) |
| Control | `--height-control`, `--height-control-primary`, `--focus-width`, `--focus-offset` | [3.6.2](#362-affordance-and-click-targets) and [3.6.1](#361-states) |

## 6. How an agent builds and reviews a screen

To build a screen, an agent follows these steps:

1. read the parts of the [README](../../../README.md) that the screen touches, and use the terms of the [glossary](../../../README.md#26-glossary);
2. name the task of the screen and its primary element, as explained in [2.3.1](#231-visual-hierarchy);
3. build the screen only from the [components](#36-components) and [tokens](#5-design-tokens) of this skill;
4. stop and ask the person when the screen needs a decision that this skill does not record, and record the answer here before using it.

To review a screen, an agent checks that:

1. the [primary element](#231-visual-hierarchy) is seen first;
2. every number follows [3.3.4](#334-financial-numbers), and every price comes with its [calculation details](#368-prices-and-calculation-details);
3. every text comes from the [dictionaries](#263-languages-and-formats), follows the [voice](#261-voice) and uses the terms of the glossary, and every value follows [README 3.8](../../../README.md#38-formats), in both languages;
4. the screen works in both themes, with the contrast of [3.2.3](#323-contrast), and at the width of a phone, as set in [3.4.3](#343-layout-and-responsiveness);
5. every action gives feedback, and every area that waits for data keeps its height, as set in [2.4](#24-feedback-and-response-time), and every field follows [2.5](#25-forms);
6. every component has its [states](#361-states);
7. every requirement of [2.7](#27-accessibility) is met;
8. no [pattern to avoid](#4-patterns-to-avoid) appears.
