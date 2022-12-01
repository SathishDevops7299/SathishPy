## Build backend
Note :-Run `ijppackage.bat` to build the project. The build artifacts will be stored in the `target/` directory.

## Procedure
1. Use any IDE having java installed in the system v15 or above

2. Lets take an example of Eclipse IDE.

3. In eclipse,install the Spring-boot tool suite by:-
  a. Go to Eclipse Marketplace in Help section on the top.
  b. Search for Spring Boot and Press Enter.
  c. Install the Spring Boot tool suite 4.

4. After installing Spring Boot, 
  a.go to "File".
  b.go to "open project from File System".
  c.Select the Directory where backend application is stored.

5. Now we need the dataBase:
  a.Open postgres database application/server.
  b.Right click on Databases->Create->database.
  c.Set name="ijp"

6. After Database Setup :
  a.Go to Eclipse Backend src/main/resources.
  b.Open application.yml file.
  c.Set username name and password of your postgres "ijp" database.
  d.Then set name and email of the superuser i.e administrator.
  e.Then set the callback url referring to the frontend server like "http://localhost:4200/callback"

7. At last right click on ijp_backend main application file name and Run As->Spring Boot App.

8. Now we can check the end point of the application
   "http://localhost:8080/ijpservices/hello".
   Screen output will be "Hello! IJP web services are running."


## Deploy backend
1. To deploy the application on Apache tomcat server:
   a. First copy the path of apache webapps address and replace that address in ijpdeploy.bat file.
   b. goto src/main/java/ijp.controllers and comment all the "@RequestMapping("ijpservices")".
   c. Open terminal in the referring to that backend application.
   d. Run the command 'ijppackage.bat'(to build the application).
   e. Then run the command 'ijpdeploy.bat' (to deploy the application i.e the process of copying that ijpservices.war file in target folder to webapps folder in Tomcat).