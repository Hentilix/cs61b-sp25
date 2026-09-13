# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

UC Berkeley CS61B "Data Structures" (Spring 2025) coursework repository. Each assignment (`lab01`–`lab11`, `hw0a`–`hw2`, `proj0`–`proj3`) is an independent Java module with its own `src/` and `tests/` directories. Skeleton code contains `TODO` markers where implementation is expected.

## Build & Test

There is no Maven or Gradle build. This is an IntelliJ IDEA project — each assignment is a separate module (`.iml` file) that references the shared JARs in `library-sp25-main/`. Compile and run within IntelliJ, or use javac/java directly from the CLI.

### Compile & run a single assignment from CLI

```bash
# Compile (from the assignment root, e.g., lab01/)
javac -cp ".;../library-sp25-main/*" -d out src/*.java
# Run a main class
java -cp "out;../library-sp25-main/*" <MainClass>
```

### Run tests from CLI

```bash
# Compile tests + sources together
javac -cp ".;../library-sp25-main/*" -d out src/*.java tests/*.java
# Run a specific test class with JUnit Platform
java -cp "out;../library-sp25-main/*" org.junit.platform.console.ConsoleLauncher \
     --select-class=<TestClassName>
```

### Checkstyle

```bash
java -jar library-sp25-main/checkstyle-10.5.0.jar -c <checkstyle_config_xml> <source_file>.java
```

## Key Libraries (all in `library-sp25-main/`)

| JAR | Purpose |
|-----|---------|
| `algs4.jar` | Princeton Algorithms, 4th ed. (StdIn, StdOut, StdDraw, etc.) |
| `jh61b.jar` | CS61B custom library (`GradedTest` annotation, `Reflection` utils) |
| `ucb.jar` | UC Berkeley utility library |
| `junit-jupiter-*.jar` | JUnit 5.9 testing framework |
| `truth-1.1.3.jar` | Google Truth assertion library |
| `checkstyle-10.5.0.jar` | Style checker |
| `spotbugs-annotations-4.7.3.jar` | Static analysis annotations |

## Code Conventions

- **Testing**: JUnit Jupiter 5.9 with `@Test`, `@Order`, `@DisplayName` annotations. Assertions use Google Truth: `assertThat(value).isEqualTo(expected)`. Autograder-compatible tests use `@GradedTest` from `jh61b.grader`.
- **Package naming**: Each assignment uses its own package (e.g., `game2048logic`, `ngrams`, `bomb`, `adventure`).
- **Author tag**: Most skeleton files include `@author Josh Hug` (the course instructor). Student implementations should keep this but do not need to add their own.
- **Imports**: `algs4` classes (like `StdIn`, `StdRandom`) are used heavily across assignments.
- **Data structures**: Assignments progressively build from arrays/lists through trees, hash maps, graphs, and tries (proj2/proj3).
