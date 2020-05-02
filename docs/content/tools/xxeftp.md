---
title: "xxeftp"
weight: 10
---

{{% notice note %}}
The VM has this tool in the `/home/vagrant/tools/xxeftp` directory
{{% /notice %}}

Github URL: [https://github.com/staaldraad/xxeserv](https://github.com/staaldraad/xxeserv)

Using the defaults:

```bash
go run xxeftp.go -w
```

| Protocol | Port |
|----------|------|
| UNO | 5000 |
| HTTP | 2122 |
| HTTPS | 2123 |
| FTP | 2121 |

The tools is designed to help you quickly setup an FTP/HTTP/HTTPS server. It has the ability to use one port (5000) to handle requests from all of these protocols.

Options (`go run xxeftp.go -h`):

```plaintext
-o string
    File location to log to
-p int
    Port to listen on (default 2121)
-uno int
    Global port to listen on (default 5000)
-w	Setup web-server for DTDs
-wd string
    Folder to server DTD(s) from (default "./")
-wp int
    Port to serve DTD on (default 2122)
-wps int
    SSL Port to serve DTD on (default 2123)
```

## Using xxeftp

To launch the tool login to the VM

```bash
vagrant ssh
```

From the VM go to the `tools` directory

```bash
cd ~/tools
```

Run the program using go

```bash
go run xxeftp.go -w
```
