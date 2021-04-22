# Simple example TODO list
it's organized in tasks with A) Text B) Due-Date
You can create new task, update/delete values - changes submitted immediately

This application was created as a demo with KISS and minimal dependencies in mind.

Usage: 
**mvn clean package** will export a jar in .../target folder
**java -jar <FILENAME>.jar** run it

To test it with CURL do following steps:
**curl http://localhost:8080/tasks**
**curl -X PUT http://localhost:8080/tasks**
**curl -X POST http://localhost:8080/tasks/0?text="My%20text"**

or browse to **http://localhost:8080/main.html**




**TODO's:**
* real JSON on server
* Jasmine or Selenium tests on Frontend
* move on to client side MVC... some day
* convert to JEE Project (with CDI)
* add persistence (SQLite ?)
* add user management, multiuser-capabilities

