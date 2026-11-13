# Screens

The structure shared by every screen, and the screens of a security: its record screen and its unit price calculation.

## 1. List and record screens

Each kind of record, a security, a modality or an index, has two screens:

- a **list screen**, with a search field, a **New** button and a table with one row per record; clicking a row opens that record;
- a **record screen**, used both for creating and for editing, with the record's fields and a **Save** button.

What exists only inside another record, a composition in the class diagram in the README, is edited on the record screen of its owner: the **event schedule** on the security's record screen, and the **index values** on the index's record screen.

## 2. The security's record screen

The security's record screen has four sections: identification, dates, remuneration and event schedule. The version of the security is shown at the top, as read-only data.

The modality decides which fields appear and which are required. Choosing the modality shows its rules as read-only data (rate class, day count convention, contractual rate and allowed events), and hides the fields that the modality does not allow; an information message explains the absence, such as that of an index. The event schedule is an editable table, and an information message explains that maturity does not need to be added as an event.

A modality that already has securities is locked, and its record screen says so. The backend validates the rule itself.

## 3. The unit price calculation

The unit price calculation screen opens from the security's record screen and shows what applies to that security:

| Security | What is calculated | Fields |
| --- | --- | --- |
| Fixed-rate with interest | Par unit price and unit price, with the premium or discount between them | Calculation date and market rate |
| Fixed-rate without interest, such as the LTN | Unit price, compared with the face value at issue | Calculation date and market rate |
| Floating-rate or hybrid | Par unit price | Calculation date |

When a calculation does not apply, the screen says why instead of hiding it.

## 4. Calculation details

No price appears without its **calculation details**, which the API returns with the price and which the interface shows below it:

- The **unit price** comes with a table of payments: one row for each payment after the calculation date, with its date, its term in days and in years, interest, amortization, payment, discounting factor and present value, and the unit price as the total.
- The **par unit price** comes with its components, shown as read-only data in the order of its formula, so that the user can follow it step by step:

| Step | Formula |
| --- | --- |
| Adjusted face value | Outstanding face value × adjustment factor = adjusted face value |
| Compounding factor | Rate factor × index factor = compounding factor |
| Par unit price | Adjusted face value × compounding factor = par unit price |

Below the components, the par unit price shows the date of the last payment or interest capitalization and, for a security with an index, the date of the last index value used. A factor equal to 1 is shown like any other value: it tells the user where the index factor did not enter.
