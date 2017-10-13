---
layout: docs
title: RemoveXmlLiterals
---
# RemoveXmlLiterals

This rules replaces XML literals with a `xml""` interpolator from [scala-xml-quote](https://github.com/densh/scala-xml-quote) project.

```scala
// tries to use single quote when possible
<div>{bar}</div>
xml"<div>${bar}</div>"

// multi-line literals get triple quote
<div>
  <span>{"Hello"}</span>
</div>
xml"""<div>
  <span>${"Hello"}</span>
</div>"""

// skips XML literals in pattern position
x match { case <a></a> => }
x match { case <a></a> => }

// replaces escaped {% raw %}{{{% endraw %} with single curly brace {
<div>{% raw %}{{{% endraw %}</div>
xml"<div>{</div>"
```
