---
layout: docs
title: Setup
---

To develop custom scalafix rules, you will first need to create an sbt project.

## scalacenter/scalafix.g8

Run the following commands to generate a skeleton project:

```sh
cd reponame # The project you want to implement rules for.

# --rule= should ideally match the GitHub repo name, to make
# it possible for users to run `scalafix "github:org/reponame/v1.0"`
sbt new scalacenter/scalafix.g8 --rule="reponame" --version="v1.0"
cd scalafix
sbt tests/test
```

Note that the `scalafix` directory is a self-contained sbt build and can be put into the root directory of your repo. The tests are written using {% doc_ref Setup, scalafix-testkit %}.

## scalafix-testkit
scalafix-testkit is a module to help you write/run/debug/test scalafix rules.

The scalacenter/scalafix.g8 template boilerplate already setups a project which uses scalafix-testkit.

The anatomy of a scalafix-testkit project is like this

```scala
scalafix
├── rules    // rule implementations
├── input    // code that runs through rule
├── output   // expected output from running input on rules
└── tests    // tiny project where unit tests run
```

scalafix-testkit features include:

- Input code for rules and expected output is written in plain .scala files, with full IDE support.

- Input and expected output files are kept in two separate sbt projects. Each project can have its own set of dependencies.

- Each individual *.scala file in the input project can have a custom .scalafix.conf configuration provided in a comment at the top of the file.

- Test failures are reported as unified diffs from the obtained output of the rule and the expected output in the `output` project.

- Assert that a {% glossary_ref LintMessage %} is expected at a particular line by suffixing the line with the comment `// assert: <LintCategory>`. The test fails if there exist reported lint messages that have no associated assertion in the input file. For an example, see the NoInfer test suite:

```scala
val x = List(1, "")// assert: NoInfer.any
```
