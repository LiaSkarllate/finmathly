---
name: finmathly-strategic-coder
description: Use when writing, changing, refactoring or reviewing finmathly code (registry and calculation services, Java/Spring) to program strategically and keep the code readable for human review.
---

# Strategic programming in finmathly

finmathly (always lowercase) is a fixed-income reference system with two microservices: the **registry service** (the write side, on RDS PostgreSQL) and the **calculation service** (the read side, on DynamoDB), kept in sync through a queue (SQS) with a transactional outbox. The README.md at the root of the repository explains the domain, the system design, the data model and the software design in full.

The code is written with AI help and **read in full by a human**. That makes **maintainability** the central quality attribute: analysability (easy to understand) and modifiability (easy to change without breaking anything).

Three sources shape this skill:
- **John Ousterhout**, *A Philosophy of Software Design*: complexity, deep modules, information hiding, strategic vs. tactical programming.
- **Ricardo Albuquerque**: AI-generated code must stay readable so humans can supervise it.
- **Filipe Deschamps**: changeability ("alterabilidade") is the main requirement of software, and modules should have a clear membrane, an interface that separates what is inside from what is outside.

## Mindset

- Program **strategically**, not tactically. Every change should leave the design a little better, not just make it work. Complexity grows incrementally and is only contained if every change takes care of it.
- Before writing, ask **where the knowledge lives** that the change uses. Behavior goes in the class that owns the data and the rules.
- Prefer small, reviewable changes. Large diffs make human supervision harder.

## How to think about quality (four groups)

1. **Quality attributes** (what we want): correctness, maintainability, testability, efficiency.
2. **Symptoms** of bad code: change amplification, cognitive load, unknown unknowns.
3. **Causes**: dependencies and obscurity.
4. **Principles** (how we fix it): a principle reduces a cause. Patterns and good practices are concrete ways to apply a principle.

When reviewing code, describe each problem by its symptom, its cause and the principle that fixes it.

## Names

- Everything in the code is written in **English**: class, method and variable names, comments, error messages, test names and documentation. The user may talk to Claude in another language; that never changes the language of the code.
- Financial concept names follow the domain section of the README.md: the English name of the concept, in camelCase (e.g. faceValueAtIssue, contractualRate, indexPercentage).
- Names are precise: a reader knows what the thing is from the name alone. Use businessDays, never days; compoundingFactor or indexFactor, never factor; marketRate, never rate.
- The same concept always has the same name. **Never give one name two meanings**: amortizationBase (the amortization base) and daysInYear (the day-count base) are never called just base.
- When introducing a new concept, check whether its name already means something else in the system.
- Rates and percentages are **always decimals** (10% = 0.10) in code, database, messages and API. This is a project-wide rule, so it does not need to be in the name.
- Money and rates use BigDecimal, never double.

## Functions

- A function does one thing, at a single level of abstraction. Calculation code should read like the formula (e.g. par unit price = adjusted face value × compounding factor).
- Split a function only when the extracted part has **its own domain concept**, with a domain name. Do not create shallow functions (e.g. multiplyValues) just to make code shorter.

## Comments

- First make the code explain itself (names, functions named after concepts). Comments say what the code cannot: the why, the business rule, the contract of public methods (inputs, outputs, units, when it fails).
- Do not repeat the code in a comment.
- Comment the contract, not the product: "the queue delivers each message at least once and without ordering", not "SQS...".

## Errors

- First try to **define the error out of existence**: model things so the error cannot happen. Make invalid states unrepresentable (e.g. the DayCountConvention enum has one value per valid pair; maturity is implicit).
- An error that cannot be eliminated **fails early and clearly**, at the input, with a message that says what happened and how to fix it. Never carry on by returning zero, null or a wrong value.
- A negative market rate is valid; only a market rate <= -1 is rejected.

## Classes and SOLID

- **Keep behavior with its data.** Avoid feature envy: a method that only touches another class's data, or that forces another class to expose internal methods.
- **S, single responsibility**: a class has one reason to change (e.g. business-day counting lives in BusinessDayCalendar, outside the unit price calculation).
- **O, open/closed**: a new variant comes in as new code where change is likely. A switch without default over an enum, **in one place and next to the data**, is acceptable (e.g. Modality.allows, CashFlowState.apply). Switches over the same type scattered across the system are the signal to use polymorphism.
- **L, Liskov substitution**: any implementation of an interface works wherever the interface is expected, with no hidden rule. instanceof, or an if on the concrete type, signals a violation.
- **I, interface segregation**: clients do not depend on methods they do not use (e.g. SecuritySnapshotReader for the API, SecuritySnapshotWriter for the queue consumer). Do not split interfaces when there are no distinct clients.
- **D, dependency inversion**: business rules define the interfaces they need; database, queue and external API adapters implement them. Do not create interfaces for pure logic (e.g. BusinessDayCalendar).
- Prefer **deep modules**: a small interface hiding a lot of work. Hide internal decisions (information hiding) and avoid information leakage.

## Patterns

- **Strategy** when each variant is a complete, different algorithm (e.g. IndexFactorCalculator, one implementation per IndexValueType; yearFraction on each DayCountConvention value). Do not use it when each strategy would only forward the call.
- **Repository** for data access, in business language (e.g. IndexValueReader.findBetween).
- **Adapter** to translate ready-made parts (AWS SDK, external APIs) into domain interfaces (e.g. SqsMessagePublisher). Changing the data source changes only the adapter.

## Simplicity

- Kent Beck's rules of simple design, in priority order: (1) passes the tests; (2) reveals intention; (3) has no duplication; (4) has the fewest elements. When 2 and 3 conflict, clarity wins.
- KISS and YAGNI: the simplest solution that works; do not build what is not needed yet. This does not forbid keeping the code easy to change.
- Optimize only after measuring.

## Tests and refactoring

- Refactoring changes structure without changing behavior; run the tests at every step.
- The calculation is pure logic and must be testable without AWS (in-memory readers).
- Include reference cases with published rates and prices (Tesouro Nacional, ANBIMA), edge cases (calculation date on maturity, an event on the calculation date, a negative rate, a date after the last index value) and one test per rule, named after the rule.
- Tests are where the human supervises the correctness of generated code: keep them readable, with verifiable values.

## finmathly architecture constraints

- The calculation service never reads the registry database or calls the registry API. The services share no code; they share only the contract of the snapshot JSON (with schemaVersion and compatible changes) and of the index value messages.
- In the calculation service, version protection is the DynamoDB conditional write (write only if the version is higher). Index values use the same kind of condition on updatedAt. A condition failure is treated as an old message, not as an error.
- In the registry service: Java names are camelCase and the tables and columns derived from them are snake_case; ids are UUID version 7 generated by Hibernate; enums are stored as text with a CHECK constraint; a field that does not apply is null; audit attributes createdAt, createdBy, updatedAt and updatedBy (columns created_at, created_by, updated_at, updated_by); security and its event schedule are versioned together (@Version with OPTIMISTIC_FORCE_INCREMENT); security_history and index_value are the outbox tables (publishedAt; mark as published only if updatedAt has not changed).
- Securities, modalities and indexes are never deleted; a modality is locked after its first security.
- The day count convention lives on the modality: BUSINESS_252, ACTUAL_365, ACTUAL_360. It applies to the contractual rate and the market rate; the index factor follows the index's own rule.

## When delivering code

- Explain the design decisions and trade-offs in a few lines, naming the principle involved.
- Point out what was deliberately left out (YAGNI) and any open business-rule question, instead of inventing the rule.
- When a change alters the data model, the architecture or a message contract, update the README.md and the diagram sources in `docs/` in the same change, and run `docs/render.sh` so the images match.
