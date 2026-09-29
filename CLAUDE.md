# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project state

Fresh Spring Boot scaffold (Spring Initializr): only `FlagWithApplication` and a `contextLoads` test exist. No domain code yet. `README.md` (Korean) is the concept doc; timeline and detailed stack are not decided.

## Product

flagWith is a GitHub-style, git-based team workspace that **CTF organizers** provision for participating teams. The customer is the organizer, not the participants. Accounts span three roles: organizer, contest, team.

Invariants that shape the design:
- **Team isolation:** a team can access only its own workspace. Every API request must check that the caller belongs to the target team (IDOR). This isolation is the main selling point because it blocks cross-team collaboration, so treat any authz gap as critical.
- **Auto-lock at contest end:** commit/push/pull/branch are all blocked once the contest ends. The repo becomes read-only and shows a timeline page built from commit data. The timeline is a writeup *reference/hint*, not a generated writeup.
- **Encryption at rest:** only code and problem files are encrypted, with a password set by the team owner, so a full server compromise does not leak them. Use standard crypto library calls and never invent schemes. Idea under consideration: store no password or hash, and authenticate by decrypting with the submitted password and checking for a known signature (e.g. the title).
- **Organizer monitoring dashboard:** live view of team progress per problem during the contest.

MVP: isolated workspaces, IDOR hardening, server-side encryption, auto-lock, timeline page, repo browser + diff viewer, organizer dashboard. Optional: real-time collaborative editor (via an existing CRDT library). Roadmap only: AI summaries.

Undecided (don't assume): git engine (existing OSS vs custom), web-only vs also supporting terminal git, per-contest one-off deployment vs hosted service, whether the dashboard is MVP.

## Stack

- Spring Boot 4.0.x, Gradle (Groovy DSL), Java 17 toolchain
- Spring Web MVC, Spring Data JPA, MySQL (`mysql-connector-j`), Lombok
- Mixed Java/Kotlin backend: the Kotlin JVM + `kotlin.plugin.spring` plugins are applied, so `src/main/kotlin` compiles alongside `src/main/java`. One backend dev (the repo owner) writes Kotlin; the other two write Java. When helping the owner, default to Kotlin, and keep code interoperable with Java callers (e.g. `@JvmStatic`, avoid Kotlin-only APIs at module boundaries).

## Team

Security 2, Front-end 1, Back-end 3. Security designs the data-protection and access-control architecture from the start, and it drives the backend and frontend structure.
- Base package: `com.flagwith.flagwith`. Config in `src/main/resources/application.yaml`.

## Commands

```bash
./gradlew bootRun                     # run app
./gradlew build                       # compile + test + jar
./gradlew test                        # all tests (JUnit 5)
./gradlew test --tests 'com.flagwith.flagwith.FlagWithApplicationTests'           # one class
./gradlew test --tests 'com.flagwith.flagwith.FlagWithApplicationTests.contextLoads'  # one method
```

## Gotchas

- JPA + MySQL driver are on the classpath but `application.yaml` has no `spring.datasource.*`. `bootRun` and `@SpringBootTest` (`contextLoads`) will fail to start until a datasource is configured (or tests get a test-scoped DB).
- Spring Boot 4 splits test starters per module (`spring-boot-starter-data-jpa-test`, `spring-boot-starter-webmvc-test`); add matching test starters when adding new starters.
