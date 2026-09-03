# POCBANK

A hands-on project to learn DDD, TDD, hexagonal architecture, Quarkus and the
JVM by building a current-account core API — from domain model to deploy, one
painful decision at a time.

This README is the diary of that build. It's expected to be mostly empty
right now — it fills in as decisions get made, not before.

## Why this exists

_TODO: what you want to learn and why._

## Scope

API only. No frontend, no templates. The interface is HTTP + JSON; the
client is `curl` or an automated test. Everything else is out of scope on
purpose.

## Ground rules

No AI-generated code goes in without review and understanding. AI is used as
a reviewer and a rubber duck, not as an author. If I can't explain a line
without it, the line doesn't ship. Build tooling and automation (Maven
setup, project layout, CI, scripts) are exempt — that's not what this
project is trying to teach.

## Domain

Current account, chosen because it has invariants worth protecting:

- Balance can never drop below the agreed overdraft limit
- A transfer is atomic: it debits and credits, or neither happens
- No operating across currencies without an explicit conversion
- Transferring to the same account isn't a transfer
- An operation amount is always positive (debit/credit are intents, not signs)

Each of these is a test before it's code.

## Architecture

Separate Maven modules, not packages, per layer — enforced by the dependency
rule, not by discipline:

```
pocbank-domain            zero dependencies beyond JUnit
pocbank-application        depends only on domain
pocbank-adapter-*         depend on application
```

`domain` depends on nothing. `application` depends on `domain`. Adapters
depend on `application`. Never the other direction — if it doesn't compile,
that's the point.

## Decision log

_Entries go here as choices get made — what was tried, what hurt, why it
changed._

## Current phase

**Phase 0 — pure domain, no framework.** No Quarkus, no database, no REST,
no Docker. Just Java and JUnit, so the test suite runs in milliseconds.

Suggested order (see the project outline for the full reasoning):

1. `Money` — arithmetic, comparison, reject mixed currencies, reject
   negative amounts.
2. `AccountId` — a real value object, not a raw `String`/`Long`.
3. `Account` — `debit()`/`credit()`, protects its own invariants.
4. Transfer — the open question: method on `Account`, a domain service, or
   its own aggregate? Try more than one, write down which hurt least.

Exit criteria: every invariant above has a test, `pocbank-domain` compiles
with zero framework dependencies, and each value object's reason for
existing can be explained out loud.

## Running

_Nothing to run yet — Phase 0 is domain and tests only._

```
mvn -pl pocbank-domain test
```
