---
title: "Document Type Definition"
weight: 3
---

The Document Type Definition (DTD) defines how the XML document should be structured. An example is given below.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ELEMENT name (first,last)>
  <!ELEMENT first (#PCDATA)>
  <!ELEMENT last (#PCDATA)>
]>

<name>
  <first>foo</first>
  <last>bar</last>
</name>
```

* `!DOCTYPE name` - this defines the root element of the document as `name`
* `!ELEMENT name (first,last)` - this says that the name element must contain two child elements `first` and `last`
* `!ELEMENT first (#PCDATA)` - this says the `first` element has to be of type **P**arsed **C**haracter **Data**
* `!ELEMENT last (#PCDATA)` - this says the `last` element has to be of type **P**arsed **C**haracter **Data**

### Internal DTD

All of the examples provided so far have used an internal DTD. That means that the DTD content has been included in the XML.

### External DTD

A DTD is considered external if it is not defined within the XML. An example of an external DTD with the name "external.dtd" is shown below.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name SYSTEM "external.dtd">

<name>Blah</name>
```
