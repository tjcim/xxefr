---
title: "Internal Vs External Entities"
weight: 5
---

## Internal Entity

Just like with Internal vs External DTDs the difference is where they are defined. An internal entity is defined within the XML:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ELEMENT name ANY>
  <!ENTITY me "Trevor">
]>

<name>&me;</name>
```

## External Entity

As the name suggests the entity is defined using data from outside the XML. To use an external entity we define an entity using the keyword `SYSTEM`. Here is an example:

Contents of `/home/vagrant/test_files/love.txt`:

```plaintext
I love XXE!
```

And the XML that uses the contents of the file to define an entity:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ELEMENT name ANY>
  <!ENTITY me SYSTEM "file:///home/vagrant/name.txt">
]>

<name>&me;</name>
```

The resulting XML would look like this:

``` xml
<name>I love XXE!</name>
```

Do you see how someone can abuse this function?
