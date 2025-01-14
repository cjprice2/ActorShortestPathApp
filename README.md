# ActorShortestPathApp

ActorShortestPathApp is a JavaFX application for calculating the shortest path between actors in a movie database. Built with Java and JavaFX 23.0.1, it provides an intuitive graphical interface for users to explore actor connections.

## Features
- **Interactive GUI**: Modern, easy-to-use interface built with JavaFX.
- **Shortest Path Calculation**: Find connections between actors efficiently.
- **Cross-Platform**: Build and run the application on any Java-supported platform.

## Requirements
### For Running from Source
- **Java 23 or Later**: Download and install from:
  - [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Maven**: Ensure Maven is installed and configured on your system.

### For Using the Installer
- No additional software is required! The installer bundles Java 23 and the JavaFX runtime for ease of use.

## Installation

### Using the Installer
1. Download the `.exe` installer from the [Releases](https://github.com/cjprice2/ActorShortestPathApp/releases).
2. Run the installer and follow the on-screen instructions.
3. Launch the application from the Start Menu or desktop shortcut.

### Running from Source
1. Clone this repository:
   ```bash
   git clone https://github.com/cjprice2/ActorShortestPathApp.git
   cd ActorShortestPathApp
2. Build the project using Maven:
   mvn clean package
3. Run the application:
   java --module-path "path_to_javafx_libs" --add-modules javafx.controls,javafx.fxml -jar target/ActorShortestPathApp-1.0-SNAPSHOT.jar
   (Replace path_to_javafx_libs with the location of your JavaFX modules.)

### Credits 
Condensed databases from: https://www.kaggle.com/datasets/ashirwadsangwan/imdb-dataset

### License
This project is licensed under the MIT License. See LICENSE for more details.