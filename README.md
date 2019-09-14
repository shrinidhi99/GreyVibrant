# GreyVibrant
---
## A music player app with android front-end and MySQL backend.
---
> Use **androidX** libraries wherever possible while adding project dependencies in app level build.gradle
---
> **ER Diagrams** can be found [here](https://www.lucidchart.com/documents/edit/49de7fd6-bd05-4c44-88aa-6464289c7979/0_0).

> **Database Schema** can be found [here](https://docs.google.com/document/d/1rn6Llg6dbmujGjjBwfU2Wy2GuWKYKwyU6Skg1fHytVU/edit).

> **Project abstract** can be found [here](https://docs.google.com/document/d/1k3d4zBtLIB2msmAUne6-Trskp821sG9PGr3VxfWNqXo/edit).
---
> PHP used as server scripting language in this project. Place the php files in this location --> */opt/lampp/htdocs* 
---
```java
1. git pull upstream master 
2. git checkout -b <branch_name_for_commit>
3. git add . 
4. git commit -m "commit message"
5. git push origin <branch_name>
6. Go to github repository and check whether this branch has any merge conflicts with master branch.
   If no conflicts then generate a pull request
```
---
```java
To configure phpmyadmin into your android project, follow these steps: (First time in a device)
1. sudo apt update
2. sudo apt-get install phpmyadmin php-mbstring php-gettext -y (choose apache as your server and create a password)
3. sudo mysql
4. grant all on *.* to 'phpmyadmin'@'localhost';
5. flush privileges;
6. sudo service apache2 start
7. systemctl start apache2.service
8. type localhost/phpmyadmin as url in your web browser
9. username would be phpmyadmin and password would be what was set during installation

```
---
```java
To install XAMPP in ubuntu follow these steps:
1. Download the executable files from https://www.apachefriends.org/download.html
2. Make the installation package executable
   i) cd /home/[username]/Downloads
   ii) chmod 755 [package name] (ex: chmod 755 xampp-linux-x64-7.2.10-0-installer.run)
3. Confirm execute permission
   ls -l [package name] (ex: ls -l xampp-linux-x64-7.2.10-0-installer.run)
4. Launch the Setup Wizard
   sudo ./[package name] (ex: sudo ./xampp-linux-7.2.10-0-installer.run)
5. Complete the process of installation in the set-up wizard
6. Launch XAMPP through the Terminal
   sudo /opt/lampp/lampp start
7. In order to install Net Tools, run the following command as root:
   sudo apt install net-tools
8. Verify Installation
   http://localhost
   http://localhost/phpmyadmin
9. Configure the port number of MySQL Database = 3307
10. To make apache2 server running follow these steps:
   i) sudo apachectl stop
   ii) Start apache web server through XAMPP
   
To uninstall XAMPP
1. cd /opt/lampp
2. sudo ./uninstall
3. sudo rm -r /opt/lampp
```
---
```java
To setup the environment required for the php files to run on the localhost follow these steps:
1. sudo apachectl stop (to stop apache server)
2. sudo /opt/lampp/lampp stop (to stop XAMPP if it is running and not working fine)
3. sudo /opt/lampp/lampp start (to start XAMPP)
4. Now if all the three processes are running on XAMPP control panel, you can go ahead with php otherwise repeat these steps.
5. localhost/<address of php file inside htdocs directory> (to run a php file)
6. localhost/phpmyadmin (to run phpmyadmin for running MySQL database server)
```
---
```java
refer all these links on stackoverflow
1. https://stackoverflow.com/questions/4779963/how-can-i-access-my-localhost-from-my-android-device?page=1&tab=votes#tab-top
2. https://stackoverflow.com/questions/23382627/wamp-cannot-access-on-local-network-403-forbidden/23385021#23385021
3. https://stackoverflow.com/questions/16608466/connect-to-a-locally-built-jekyll-server-using-mobile-devices-in-the-lan/16608698#16608698
4. https://stackoverflow.com/questions/21011279/android-volley-checking-internet-state
```
---
![GV Mascot](https://www.pinclipart.com/picdir/middle/3-31209_jazz-cliparts-border-transparent-background-music-notes-png.png "Grey Vibrant")
