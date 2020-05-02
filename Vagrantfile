# Eclipse IDE
$msg = <<MSG
----------------------------------------------------------
The machine is up and running.

To reach the main app homepage:

  http://localhost:8085/

To reach the docs homepage:

  http://localhost:8085/docs/

To reach the Java XXE app:

  http://localhost:8085/xxe/

----------------------------------------------------------
MSG

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"
  config.vm.post_up_message = $msg
  config.ssh.forward_x11 = true

  # Eclipse Development Server
  config.vm.network "forwarded_port", guest: 9090, host: 9090
  # Hugo Development Server
  config.vm.network "forwarded_port", guest: 1313, host: 1313
  # Nginx Main Access
  config.vm.network "forwarded_port", guest: 8085, host: 8085
  # Nginx Main Access
  config.vm.network "forwarded_port", guest: 8443, host: 8443
  # Tomcat
  config.vm.network "forwarded_port", guest: 8080, host: 8086

  config.vm.provider "virtualbox" do |vb|
    vb.name = "XXE"
    vb.memory = "8192"
    vb.cpus = "4"
    vb.customize ["modifyvm", :id, "--clipboard-mode", "bidirectional"]
    vb.customize ["modifyvm", :id, "--draganddrop", "bidirectional"]
  end
  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "provision/main.yml"
    ansible.compatibility_mode = "2.0"
  end
end
