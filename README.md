# KaataZero

A Java-based Tic-Tac-Toe game featuring player vs computer gameplay with a graphical user interface.

## Game Overview

KaataZero is a classic Tic-Tac-Toe implementation where players compete against an AI computer opponent on a 3x3 grid. The computer uses "0" symbols while the player uses "X" symbols.

## Features

- **Player vs Computer**: Human player competes against AI opponent
- **Scoring System**: 
  - Win: +100 points
  - Loss: Score resets to 0
  - Draw: -100 points
- **Visual Interface**: Clean Java Swing GUI with custom graphics
- **Animated Board**: Smooth drawing animations for game board
- **Menu System**: File and Help menus with game controls
- **Status Tracking**: Real-time display of current move and score
- **Java 7 Compatible**: Code updated to work with JDK 1.7 (no lambdas; inner classes used)
- **Code Clean-up**: Magic numbers replaced with constants for readability and maintainability

## How to Play

1. Launch the game using `KaataZero.jar` or `KaataZero.exe`
2. Click "Play" to start a new game
3. The computer makes the first move (places a "0")
4. Click on any empty square to place your "X"
5. First to get three in a row (horizontally, vertically, or diagonally) wins
6. Play again or exit when the game ends

## Technical Details

### Architecture
- `CallGUI.java`: Main application frame and UI components
- `ComputerPlayer.java`: Computer move selection for Easy, Medium, and Hard difficulty
- `Board.java`: Game board logic, mouse handling, and graphics rendering
- `KaataZero.java`: Core game logic, matrix operations, and win detection

### Requirements
- Java Runtime Environment (JRE) 1.7+
- Java Swing support

### Build Information
- Built with NetBeans/Ant or javac/jar
- Java Swing for GUI components
- Custom graphics and animations
- Executable JAR and Windows installer included

## Installation & Running

### Option 1: JAR File
```bash
java -jar KaataZero.jar
```

### Option 2: Windows Installer
- Use the generated installer under `GeneratedEXE/KaataZero.exe`
- Installer built with NSIS and points to `dist/KaataZero.jar`

## Author

**Paarth Batra**
- Email: paarth_batra@yahoo.co.in, paarthh2@rediffmail.com
- Website: www.versionpb.co.in
- Version: 1.0.0.0
- Release Date: December 7, 2015

## Changelog Highlights
- Java 7 compatibility (removed lambdas; fixed inner class variable scope)
- Constants introduced for board size, symbols, and status codes
- Installer updated to use new domain and correct resource paths
- Reverted difficulty-level changes to keep stable classic gameplay

## License

This project appears to be a personal/educational implementation. Please contact the author for licensing information.

---

*KaataZero - The best way to pass time!*
