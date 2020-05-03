---
title: "Eclipse"
weight: 100
---

Here are instructions to launch Eclipse on the Vagrant machine to view the source code and make changes.

Log into the vagrant box using

```bash
vagrant ssh
```

Start eclipse

```bash
eclipse &
```

You will be asked where to put the workspace. Enter `/vagrant` and hit Launch

![eclipse_workspace](/static/img/workspace_directory.png)

File > Import

![](/static/img/import.png)

Existing Projects

![](/static/img/existing_projects.png)

Import Project

![](/static/img/import_project.png)

Update the Maven project by right-clicking on the xxe element, choose Maven > Update Project

![Update Maven Project](/static/img/maven_update_project.png)

Run the project by right-clicking on the xxe element, choose Run As > Maven Build

![Run As Maven](/static/img/run_as_maven_build.png)

Configure Goals

![](/static/img/edit_config.png)

This will take a bit before it finishes as it will begin to download the required dependencies. At the end you should see something like this in the console:

```plaintext
INFO: Starting Servlet Engine: Apache Tomcat/7.0.47
Apr 25, 2020 9:47:33 PM org.apache.coyote.AbstractProtocol start
INFO: Starting ProtocolHandler ["http-bio-9090"]
```

Once done you will be able to reach the development server at [http://localhost:9090/xxe](http://localhost:9090/xxe).
