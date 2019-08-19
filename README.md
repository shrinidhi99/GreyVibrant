# GreyVibrant
---
## A music player app with android front-end and MySQL backend.
---
> Use **androidX** libraries wherever possible while adding project dependencies in app level build.gradle
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
To setup the environment required for the php files to run on the localhost follow these steps:
1. sudo apachectl stop (to stop apache server)
2. sudo /opt/lampp/lampp stop (to stop XAMPP if it is running and not working fine)
3. sudo /opt/lampp/lampp start (to start XAMPP)
4. Now if all the three processes are running on XAMPP control panel, you can go ahead with php otherwise repeat these steps.
5. localhost/<address of php file inside htdocs directory> (to run a php file)
6. localhost/phpmyadmin (to run phpadmin for running MySQL database server)
```
---
---
![LCO Mascot](https://www.pinclipart.com/picdir/middle/3-31209_jazz-cliparts-border-transparent-background-music-notes-png.png "Grey Vibrant")
