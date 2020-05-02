---
title: "Validate XXE"
weight: 2
---

{{% notice note %}}
Make sure you are on the Basic XXE (`/xxe.do`) page of the XXE Firing Range app
{{% /notice %}}

We can see that the application processes XML due to the format of the post data as well as the Content-Type.

![Content-Type](/static/img/content_type.png?classes=border)

If we submit malformed XML such as this:

```xml
<todo>
  </description>
  <category>The Category</category>
</todo>
```

We see that the app tried to process the XML but was unable to do so, but more importantly it reflected the XML back to us.

![Reflected XML](/static/img/malformed_xml.png)

Now lets see if we include a properly formed XML that does not include the expected `description` or `category` tags if it will reflect back.

```xml
<todo>Missing tags</todo>
```

We see that it reflected just the contents of the `todo` tag.

![Missing Tags](/static/img/missing_tags.png)

Lets try submitting XML with two internally defined [general entities](/info/entities/#general-entities) to see if it is processed. In the following example we are defining the general entities `foo` and `bar`. We are then inserting these entities into the `description` tag. If everything goes as expected we should see an entry in the TODOs list with the interpolated XML.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY foo "Does this">
  <!ENTITY bar "expand?">
]>

<todo>
  <description>&foo; &bar;</description>
  <category>Category</category>
</todo>
```

As we can see, the XML was correctly interpolated and processed to give us the new entry.

![Entity Interpolation](/static/img/xxe_entity_interpolation.png)

What else can we do with general entities?
