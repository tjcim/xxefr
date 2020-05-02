---
title: "Sensitive Files"
weight: 10
---

This is not meant to be exhaustive or kept up-to-data, but simply to give you a starting point.

### Windows

```plaintext
C:/windows/repair/sam
C:/windows/System32/config/RegBack/SAM
C:/windows/repair/system
C:/windows/repair/software
C:/windows/repair/security
C:/windows/debug/NetSetup.log
C:/windows/iis6.log
C:/windows/system32/logfiles/httperr/httperr1.log
C:/sysprep.inf
C:/sysprep/sysprep.inf
C:/sysprep/sysprep.xml
C:/windows/Panther/Unattended.xml
C:/inetpub/wwwroot/Web.config
C:/windows/system32/config/AppEvent.Evt
C:/windows/system32/config/SecEvent.Evt
C:/windows/system32/config/default.sav
C:/windows/system32/config/security.sav
C:/windows/system32/config/software.sav
C:/windows/system32/config/system.sav
C:/windows/system32/inetsrv/config/applicationHost.config
C:/windows/system32/inetsrv/config/schema/ASPNET_schema.xml
C:/windows/System32/drivers/etc/hosts
C:/windows/System32/drivers/etc/networks
C:/windows/system32/config/SAM
C:/boot.ini
C:/windows/win.ini
C:/windows/php.ini
C:/php/php.ini
C:/php5/php.ini
C:/php4/php.ini
C:/apache/php/php.ini
C:/xampp/apache/bin/php.ini
C:/home2/bin/stable/apache/php.ini
C:/home/bin/stable/apache/php.ini
C:/Program Files/Apache Group/Apache/logs/access.log
C:/Program Files/Apache Group/Apache/logs/error.log
C:/Program Files/Apache Group/Apache/conf/httpd.conf
C:/Program Files/Apache Group/Apache2/conf/httpd.conf
C:/Program Files/xampp/apache/conf/httpd.conf
C:/Program Files/FileZilla Server/FileZilla Server.xml
C:/Program Files (x86)/Apache Group/Apache/logs/access.log
C:/Program Files (x86)/Apache Group/Apache/logs/error.log
C:/Program Files (x86)/Apache Group/Apache/conf/httpd.conf
C:/Program Files (x86)/Apache Group/Apache2/conf/httpd.conf
C:/Program Files (x86)/xampp/apache/conf/httpd.conf
C:/Program Files (x86)/FileZilla Server/FileZilla Server.xml
C:/AppServ/MySQL/data/mysql/user.MYD
```

### Linux

```plaintext
/etc/issue
/etc/motd
/etc/passwd
/etc/group
/etc/resolv.conf
/etc/shadow
/root/.bash_history
/root/.profile
/root/.ssh/authorized_keys
/root/.ssh/id_rsa
/root/.ssh/id_rsa.keystore
/root/.ssh/id_rsa.pub
/root/.ssh/known_hosts
/root/.mysql_history
/root/.my.cnf
/usr/local/apache2/conf/httpd.conf
/usr/local/apache2/conf/extra/httpd-ssl.conf
/etc/mtab
/etc/inetd.conf
/var/log/dmessage
/etc/httpd/logs/acces_log
/etc/httpd/logs/error_log
/var/www/logs/access_log
/var/www/logs/access.log
/var/log/apache/access_log
/var/log/apache2/access_log
/var/log/apache/access.log
/var/log/apache2/access.log
/var/log/access_log
/etc/tomcat6/tomcat-users.xml
/etc/tomcat7/tomcat-users.xml
/etc/tomcat8/tomcat-users.xml
/etc/tomcat9/tomcat-users.xml
```

Additional files that need modification

```plaintext
/home/[USERNAME]/.bash_history
/home/[USERNAME]/.mysql_history
/home/[USERNAME]/.my.cnf
/home/[USERNAME]/.profile
/home/[USERNAME]/.ssh/authorized_keys
/home/[USERNAME]/.ssh/id_rsa
/home/[USERNAME]/.ssh/id_rsa.keystore
/home/[USERNAME]/.ssh/id_rsa.pub
/home/[USERNAME]/.ssh/known_hosts
[WEBROOT]/.htaccess
[WEBROOT]/config.php
```
