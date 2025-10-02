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

## How to Play

1. Launch the game using `KaataZero.jar` or `KaataZero.exe`
2. Click "Play" to start a new game
3. The computer makes the first move (places a "0")
4. Click on any empty square to place your "X"
5. First to get three in a row (horizontally, vertically, or diagonally) wins
6. Play again or exit when the game ends

## Technical Details

### Architecture
- **callGUI.java**: Main application frame and UI components
- **Board.java**: Game board logic, mouse handling, and graphics rendering
- **KaataZero.java**: Core game logic, matrix operations, and win detection

### Requirements
- Java Runtime Environment (JRE)
- Java Swing support

### Build Information
- Built with NetBeans IDE
- Java Swing for GUI components
- Custom graphics and animations
- Executable JAR and Windows executable included

## Installation & Running

### Option 1: JAR File
```bash
java -jar KaataZero.jar
```

### Option 2: Windows Executable
Double-click `KaataZero.exe` to run directly on Windows.

## Author

**Paarth Batra**
- Email: paarth_batra@yahoo.co.in, paarthh2@rediffmail.com
- Website: www.versionpb.co.in
- Version: 1.0.0.0
- Release Date: December 7, 2015

## License

This project appears to be a personal/educational implementation. Please contact the author for licensing information.

---

*KaataZero - The best way to pass time!*
