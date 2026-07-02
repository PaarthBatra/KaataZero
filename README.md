# KaataZero

A Java-based Tic-Tac-Toe game featuring player vs computer gameplay with a graphical user interface.

## Game Overview

KaataZero is a classic Tic-Tac-Toe implementation where players compete against an AI computer opponent on a 3x3 grid. The computer uses "0" symbols while the player uses "X" symbols.

## Features

- **Player vs Computer**: Human player competes against AI opponent
- **2 Player Mode**: Two local players can take turns on the same board
- **Difficulty Modes**: Easy uses random moves, Medium mixes tactical and random moves, and Hard uses minimax
- **Start Control**: Choose whether the player or computer starts the next computer match
- **Scoring System**: 
  - Easy win: +50 points
  - Medium win: +100 points
  - Hard win: +200 points
  - Loss: Score resets to 0
  - Draw: -50 points
- **Visual Interface**: Clean Java Swing GUI with custom graphics
- **Winning Highlight**: The winning row, column, or diagonal is highlighted
- **Restart Button**: Restart the current setup without closing the game
- **Mute Option**: Turn game beeps on or off from the controls
- **Session History**: Track player wins, opponent wins, and draws while the app is open
- **Undo and Reset**: Undo the latest move and reset the score/history counters
- **Keyboard Shortcuts**: Use `R` to restart, `U` to undo, `S` to reset score/history, `Esc` to quit, and `1`-`9` to play board cells
- **In-Window Results**: Game results appear in the status area instead of interrupting play with popups
- **Difficulty Help**: The UI explains the selected difficulty mode
- **Animated Board**: Smooth drawing animations for game board
- **Menu System**: File and Help menus with game controls
- **Status Tracking**: Real-time display of current move, mode, difficulty, starter, and score
- **Saved Settings**: Last selected mode, difficulty, and starter are restored on launch
- **Java 7 Compatible**: Code updated to work with JDK 1.7 (no lambdas; inner classes used)
- **Code Clean-up**: Magic numbers replaced with constants for readability and maintainability

## How to Play

1. Launch the game using `KaataZero.jar` or `KaataZero.exe`
2. Click "Play" to start a new game
3. Select Computer or 2 Player mode from the controls
4. Pick a difficulty and starter for computer games
5. Click on any empty square to place your mark
6. First to get three in a row (horizontally, vertically, or diagonally) wins
7. Use Restart, Undo, Reset, or the matching keyboard shortcuts while playing

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
- Runnable JAR can be built from the compiled classes
- Installer script installs the EXE launcher and bundled JAR
- Plain Java logic tests are available under `test/`

## Installation & Running

### Option 1: JAR File
```bash
java -jar dist/KaataZero.jar
```

Build it manually with:
```bash
javac -d build/classes src/kaatazero/*.java
jar cfe dist/KaataZero.jar kaatazero.CallGUI -C build/classes .
```

Run logic tests with:
```powershell
javac -cp build/classes -d build/test/classes test/kaatazero/*.java
java -cp "build/classes;build/test/classes" kaatazero.KaataZeroLogicTest
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
- Added difficulty modes, local 2 Player mode, restart control, saved settings, and winning-line highlighting
- Added mute control, session history, keyboard shortcuts, in-window results, and runnable JAR packaging
- Added undo/reset controls, difficulty help text, installer launcher updates, resource fallbacks, and logic tests

## License

This project appears to be a personal/educational implementation. Please contact the author for licensing information.

---

*KaataZero - The best way to pass time!*
