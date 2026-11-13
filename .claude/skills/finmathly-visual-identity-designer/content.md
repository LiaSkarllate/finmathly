# Content

How the interface writes: languages, numbers and tables, voice, concept tips, messages and read-only data, and the names of the design itself.

## 1. Languages

The interface is available in Brazilian Portuguese (`pt-BR`) and in English (`en-US`). On the first visit, it follows the language of the browser: Portuguese if the browser's language starts with `pt`, and English otherwise. After that, a selector with two options, PT and EN, lets the user change it. The language selector and the theme selector sit together at the right of the top bar.

All translation happens in the frontend, because the API returns only stable codes and data:

- **Visible text** comes from the message dictionaries, one per language. No component contains visible text.
- **Enum values**, such as `BUSINESS_252`, are never shown. Each one is mapped to a label, such as "Dias úteis sobre 252" or "Business days over 252".
- **Errors** arrive with a code and parameters, from which the interface writes the message in each language.
- **Labels** always use the terms of the glossary in the README for their language. A new concept enters the glossary before it enters the interface.
- **Numbers and dates** are formatted with `Intl` for the selected language, never by hand: `966,662019` and `19/10/2026` in Portuguese, `966.662019` and `10/19/2026` in English. A decimal is passed to `Intl.NumberFormat` as a string, which it formats without turning it into a binary number.

## 2. Numbers and tables

- The interface shows each value exactly as the API returns it. It never rounds, truncates or recalculates a value; any rounding rule belongs to the backend.
- Decimal values stay strings from the API to the screen. An amount, a rate or a factor never passes through a JavaScript `number`, which is a binary floating-point number and loses digits.
- Rates are shown and typed as **percentages**, with their period in the label: `% a.a.` in Portuguese and `% p.a.` in English. The period is always the year, because every rate in finmathly is an effective annual rate. The API receives and returns rates as decimals.
- Under every rate field, a small **decimal reference** in Mono shows the decimal that is sent to the API, always in the API's format, whatever the language: `Decimal = 0.135000`. The conversion from percentage to decimal moves the decimal point on the text of the number, in a single place in the code, so no digit is lost.
- Numeric columns are aligned to the right, with tabular figures. Text and date columns are aligned to the left.
- Tables have a strong rule under the header and thin rules between rows, with no vertical lines and no alternating row colors. A selected row has the soft accent background and an accent bar on its left edge.
- Identifiers are set in Mono.

## 3. Voice

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

The interface uses no exclamation marks, no apologies and no filler words. Actions are written in the imperative, without a pronoun: "Calcule", not "Você pode calcular".

## 4. Concept tips

- Every concept of the glossary shown on a screen gets a **concept tip**: an ⓘ button that opens one or two sentences when it is hovered or tapped. Ordinary fields, such as a name, get none.
- The README is the source of every tip. The English tip follows the README's definition, and the Portuguese tip translates it faithfully.
- The tips live in one file of the frontend, with one entry per concept: the English text, the Portuguese text and the README section that defines the concept.
- Whenever a change touches a concept, its tip is compared with the README and updated in the same change. Before a screen is delivered, every tip on it is checked against the README.

## 5. Messages

A message is a sentence that the interface writes for the user. [Section 3](#3-voice) sets how each message is worded; this section sets how it looks. Messages have three levels, each with its own form:

| Level | When | Form |
| --- | --- | --- |
| Information | A consequence that needs no action: "Este título é prefixado, por isso não tem indexador." | `--color-paper` background, a left border in `--color-ink-muted`, and no icon |
| Warning | Something the user should know before acting: "O último valor divulgado do IPCA é de 30/09/2026." | `--color-warning-soft` background, a left border in `--color-warning`, and the word "Aviso" or "Warning" in `--color-warning` |
| Error | Something failed or is invalid, with the way around it when one exists | `--color-error-soft` background, a left border in `--color-error`, and the word "Erro" or "Error" in `--color-error`; a validation error sits next to its field and is linked to it with `aria-describedby` |

An information message has no icon, because the ⓘ button is reserved for concept tips, and an ⓘ on a message would look clickable. Information messages are kept rare: a sentence that only repeats data already on the screen is removed.

## 6. Read-only data

Values that belong to another record, or that the user cannot change, are shown as **read-only data**, never as a message. The rule that separates the two forms is simple: **a sentence is a message, and values are read-only data**.

Read-only data is a set of label and value pairs with a fixed structure:

- The label sits above the value. The label has the size and color of a field label, and the value has the size of a field's text.
- The pairs follow the same grid and alignment as the fields of the form, but have no input box, so they never look editable.
- **Each label and each value are the same as on the record's own screen.** Read-only data from a modality uses the field names of the modality's record screen, and shows each value exactly as that screen shows it.
- A label that is a concept of the glossary has its concept tip.
- An enum value is shown through its label, never as its code.

Read-only data appears, for example, in the rules of the chosen modality on the security's record screen, in the version of a security at the top of its record screen, in the summary at the top of the unit price calculation, and in the components of the par unit price.

## 7. Names of the design

Like the concepts of the domain, each word of the design has one name in each language.

| English | Portuguese | Meaning |
| --- | --- | --- |
| Accent color | Cor de destaque | The single color of interaction and selection, `--color-accent`; never "primary color" |
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
| Calculation details | Detalhamento do cálculo | The steps that show how a price was calculated: the payments of the unit price and the components of the par unit price |
| Logo | Logo | The fixed `fm` square, which is also the browser tab icon |
| Product name | Nome do produto | The text `finmathly` next to the logo |
| Ligature | Ligadura | The joined `fi` of the product name |
