---
title: "XML Entities"
weight: 4
---

## Entity Definition and Types

XML entities are a way of representing an item of data within an XML document, instead of using the data itself. There are three types of Entities:

* Pre-defined Entities
* General Entities
* Parameter Entities

The General and Parameter Entities can be defined internal to the DTD or externally - more on this later.

## When would you want to use an entity?

For example if you had a long or complicated string that you did not want to have to type out multiple times, you could use an entity:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE site [
  <!ELEMENT site ANY>
  <!ELEMENT copyright "XXE Firing Range &#xA9; Trevor 123 This Address Lane, Milwuakee ND. 84555">
]>

<site>
  <url>http://thissite.com</url>
  <copyright>&copyright;</copyright>
</site>
```

Entities can be used within the DTD also:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ELEMENT name ANY>
  <!ENTITY first "Trevor">
  <!ENTITY full "&first; Last">
]>

<name>&full;</name>
```

The XML parser will interpolate `&full;` and end up with: `<name>Trevor Last</name>`

## Pre-defined Entities

The XML standard defines a set of pre-defined entities.

| Entity | Result | Hex | Decimal |
|--------|--------|-----|---------|
| `<` | `&lt;` | `&#x3C` | `&#60` |
| `>` | `&gt;` | `&#x3E` | `&#62` |
| `"` | `&quot;` | `&#x22` | `&#34` |
| `'` | `&apos;` | `&#x27` | `&#39` |
| `&` | `&amp;` | `&#x26` | `&#38` |

## General Entities

General entities are the most basic. The are defined with the syntax `ELEMENT name "value"`. You can reference the general entities by prefixing an ampersand `&` and appending a semicolon `;`. General Entities function just like the stored variable in the first example.

### General Entity Rules

1. A General Entity cannot contain the definition of another entity.
2. A General Entity must contain data that an XML parser can parse (e.g. `<blah>` would not be allowed because there is no closing `</blah>` tag.)
3. A General Entity may be part of another enitty definition but only if that entity will be used directly in the document.

### Example

Here we store the long copyright string into an entity named copyright that we can refer to later in the document:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE site [
  <!ELEMENT site ANY>
  <!ELEMENT copyright "XXE Firing Range &#xA9; Trevor 123 This Address Lane, Milwuakee ND. 84555">
]>

<site>
  <url>http://thissite.com</url>
  <copyright>&copyright;</copyright>
</site>
```

## Parameter Entities

Parameter entities are defined with the syntax `ELEMENT % name "value"` and can be referenced by prefixing a percent `%` and appending a semicolon `;`. They are a bit more flexible than General Entities. For example a parameter entity can store the value of another entity:

```xml
<!ENTITY % outer "<!ENTITY inner 'Trevor'>">
```

Parameter entities do have their limitations though, the most important one is that parameter entities can only be defined and used within a DTD.

According to [w3](https://www.w3.org/TR/xml/#wfc-PEinInternalSubset)

> The use of parameter entities in the internal subset is restricted as described below.
...
> In the internal DTD subset, parameter-entity references MUST NOT occur within markup declarations; they may occur where markup declarations can occur. (This does not apply to references that occur in external parameter entities or to the external subset.)

### Parameter Entity Rules

1. A Parameter Entity can contain the definition of another entity.
2. A Parameter Entity cannot be used within the document.

### Example

To test it out in the app go to the Basic XXE page ("/basic-xxe.do") and submit the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % parameter_entity "<!ENTITY general_entity 'Some Value'>">
  %parameter_entity;
]>
<name>&general_entity;</name>
```

If everything worked out, you should see that `parameter_entity` was interpolated to the value `Some Value`.
