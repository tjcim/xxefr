---
title: "External Entity Protocols"
weight: 50
---

| | libxml2 | php | java | .Net |
|-|---------|-----|------|------|
| file | Yes | Yes | Yes | Yes |
| http | Yes | Yes | Yes | Yes |
| ftp | Yes | Yes | Yes | Yes |
| https | | | Yes | Yes |
| php | | Yes | | |
| compress.zlib | | Yes | | |
| compress.bzip2 | | Yes | | |
| data | | Yes | | |
| glob | | Yes | | |
| phar | | Yes | | |
| jar | | | Yes | |
| netdoc | | | Yes | |
| mailto | | | Yes | |
| gopher`*` | | | Yes | |

`*`Only available in older versions of Java

## PHP additional protocols

| Scheme | Extention Required |
|--------|--------------------|
| https | openssl |
| ftps | openssl |
| zip | zip |
| ssh2.shell | ssh2 |
| ssh2.exec | ssh2 |
| ssh2.tunnel | ssh2 |
| ssh2.sftp | ssh2 |
| ssh2.scp | ssh2 |
| rar | rar |
| ogg | oggvorbis |
| expect | expect |
