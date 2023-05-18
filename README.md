# The big diabetes Game
## Introduction
Food is a important part of our daily live, but most people have no idea what the nutrition values of the food on their plate are. Additionally, there are some people with diabetis or some other illness, where it is crucial to know how many carbs you are eating, to dose your medication correctly. With this web application we wanted to create a possibility for interested people to learn something about the nutrition values of food in a playful way.

## Getting started with Spring Boot
-   Documentation: https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
-   Guides: http://spring.io/guides
    -   Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
    -   Building REST services with Spring: https://spring.io/guides/tutorials/rest/

## Setup this Template with your IDE of choice
Download your IDE of choice (e.g., [IntelliJ](https://www.jetbrains.com/idea/download/), [Visual Studio Code](https://code.visualstudio.com/), or [Eclipse](http://www.eclipse.org/downloads/)). Make sure Java 17 is installed on your system (for Windows, please make sure your `JAVA_HOME` environment variable is set to the correct version of Java).

### IntelliJ
1. File -> Open... -> SoPra server template
2. Accept to import the project as a `gradle project`
3. To build right click the `build.gradle` file and choose `Run Build`

### VS Code
The following extensions can help you get started more easily:
-   `vmware.vscode-spring-boot`
-   `vscjava.vscode-spring-initializr`
-   `vscjava.vscode-spring-boot-dashboard`
-   `vscjava.vscode-java-pack`

**Note:** You'll need to build the project first with Gradle, just click on the `build` command in the _Gradle Tasks_ extension. Then check the _Spring Boot Dashboard_ extension if it already shows `soprafs23` and hit the play button to start the server. If it doesn't show up, restart VS Code and check again.

## Building with Gradle
You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

You can verify that the server is running by visiting `localhost:8080` in your browser.

### Test

```bash
./gradlew test
```

### Development Mode
You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

## API Endpoint Testing with Postman
We recommend using [Postman](https://www.getpostman.com) to test your API Endpoints.

## Debugging
If something is not working and/or you don't know what is going on. We recommend using a debugger and step-through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command), do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug "Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

## Testing
Have a look here: https://www.baeldung.com/spring-boot-testing
## High-level components
### model
Game logic is in the model compoent. Its responsibility is to organize the game flow of the whole application.
### controller
We have several controler classes. Together they have the responsibility to organize the communication between the front and backend. They are split according to their parts, that they are organizing. As an example the class UserController controlls the rest communication of the user* endpoint of our application 
### services
In our project we have three different service classes. They provide functionality to their corresponding entitys. As an example the UserService provides amongst other functionalitys the possibility to create, login, logout, update or authenticate a user.
## Illustrations

## Roadmap
For future additions we have some ideas, what one could implement. The following mentionings are not sorted by any means. But are more ment as inspiration for you.
Firstly there would be the possibility to implement a new game mode. The first additional game mode could be a higher lower implementation of a game. Before starting the game you would choose your food category, like "Fruits" as well as the nutrition value, you would compare the food items. Lets assume one would choose "Fruits" and "carbs". Then you would get as first item an apple. Then a picture of the second item would appear. Lets assume, it is a pineapple. No you hae to decide, if an pineapple has more or less carbs then an apple. If you guessed correctly, you get a positive feedback and an nex picture of an food item. If you guessed incorrectly, there will be an end of game screen and you can restart.    
Another new game mode would be , that you gett four pictures of different food Items and additionally the correct nutrition values of one of them. Then you have to guess to wich food item the displayed nutrition value fits. If you guessed correctly, you will get new pictures and nutrition values. If not, it is game over.
Another feature for the original game would be a possibility at the end of a game to see all the different questions and answers again, so that one can internalise the different nutrition values of the food items.    
Inspection mode, to be alble to go through the different food items after the game to internalise the different values.

## Authors
- [Nico Manzoni](https://www.github.com/nizonic) - *frontend*
- [André Seidenglanz](https://www.github.com/sugar-free55) - *frontend*
- [Nataell Cornu](https://www.github.com/nataell95) - *frontend/backend*
- [Maurice Hess](https://www.github.com/mauhess) - *backend*
- [Elias Suter](https://www.github.com/Bye-B) - *backend*

## License
[MIT]
(https://choosealicense.com/)
