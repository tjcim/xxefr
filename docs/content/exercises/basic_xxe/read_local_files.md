---
title: "Reading Local Content"
weight: 3
---

Now that we have verified interpolation of internally defined general entities. Lets see if we can define a general entity to the contents of a local file and then use that as the value of the `description` tag. Lets try reading the hostname at `/etc/hostname`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE todo [
  <!ENTITY xxe SYSTEM "file:///etc/hostname">
]>

<todo>
  <description>&xxe;</description>
  <category>XXE</category>
</todo>
```

Cool. It looks like that worked. We now have a Todo entry with the description of `ubuntu-bionic`.

![Host Name Todo](/static/img/host_name_todo.png)

We have verified that we can do entity interpolation. Earlier we also verified that a XML document that is unable to be read by the application is reflected back to us. Lets use these two concepts to show the contents of a local file `/etc/passwd`. We are going to send a properly formed XML that does not include the `description` or `category` tags and include an entity that reads the contents of a local file.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">]>

<todo>&xxe;</todo>
```

The response we receive contains the file:

![Basic XXE Result](/static/img/basic_xxe_result.png)

Using this process you can now view any file on the system that you have access to. Now if only there was a way to list the contents of a directory...

## Listing Contents of Directory

If the app is Java you have the ability to directly list the contents of a directory. To do that we will create a general entity and set it to the path of a directory. Try submitting this:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/">]>

<todo>&xxe;</todo>
```

![](/static/img/etc_directory.png)
