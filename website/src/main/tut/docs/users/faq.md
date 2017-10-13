---
layout: docs
title: FAQ
---

# FAQ
{:.no_toc}
If you have any questions, don't hesitate to ask on {% gitter %}.

* TOC
{:toc}

## I get resolution errors for org.scalameta:semanticdb-scalac
Make sure you are using a supported Scala version: {{ site.supportedScalaVersions | join: ", " }}.
Note, the version must match exactly, including the last number.

## Enclosing tree [2873] does not include tree [2872]

Scalafix requires code to compile with the scalac option `-Yrangepos`.
A macro that emits invalid tree positions is usually the cause of compiler errors
triggered by `-Yrangepos`. Other tools like the presentation compiler (ENSIME/Scala IDE) require
`-Yrangepos` to work properly.

## I get exceptions about coursier
If you use sbt-coursier, make sure you are on version {{ site.coursierVersion }}.

## Scalafix doesn't do anything
- If you use sbt-scalafix, try {% doc_ref Installation, Verify sbt installation %}
- Make sure that you are running at least one rule.
- Make sure that you are using a supported Scala version: {{ site.supportedScalaVersions | join: ", " }}.

## RemoveUnusedImports does not remove unused imports
Make sure that you followed the instructions in {% rule_ref RemoveUnusedImports %} regarding scalac options.

## IDE support
Scalafix has no IDE support at the moment.
If you are interested in contributing IDE support for IntelliJ/ENSIME/vscode,
please reach out on {% gitter %}!
