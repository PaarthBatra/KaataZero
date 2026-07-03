# KaataZero

A Java Swing Tic-Tac-Toe game with player vs computer and local 2-player modes, difficulty levels, skins, and saved settings.

**Version:** 2.0.0.0  
**Release Date:** July 4, 2026

## Game Overview

KaataZero is played on a 3x3 grid. In computer mode, the human plays **X** and the computer plays **O**. In 2-player mode, two people take turns as **X** and **O** on the same machine.

The game includes animated board drawing, difficulty-based AI, scoring, session statistics, undo, keyboard shortcuts, color skins, and a compact control area with settings in a menu dialog.

## Features

- **Player vs Computer**: Human player competes against AI with Easy, Medium, or Hard difficulty
- **2 Player Mode**: Two local players alternate turns on one board
- **Difficulty Modes**: Easy uses random moves, Medium mixes tactical and random play, Hard uses minimax
- **Start Control**: Choose whether the player or computer starts the next computer match
- **Scoring System**:
  - Easy win: +50 points
  - Medium win: +100 points
  - Hard win: +200 points
  - Loss: score resets to 0
  - Draw: -50 points
- **Winning Highlight**: Winning row, column, or diagonal is drawn over the board
- **Restart, Undo, Reset**: Restart the current match, undo the latest move, or reset score/history counters
- **Keyboard Shortcuts**: `R` restart, `U` undo, `S` reset score/history, `Esc` quit, `1`-`9` play cells
- **In-Window Results**: Match results appear in the status bar instead of popup dialogs
- **Session History**: Track wins, losses, and draws while the app is open
- **Persistent Statistics**: Win/loss/draw counts are restored across launches
- **Saved Settings**: Mode, difficulty, starter, sound, and skin preferences are remembered
- **Skins**: Classic, Midnight, Ocean, Forest, and Candy color themes
- **Settings Dialog**: `Settings > Game Settings` for mode, difficulty, starter, sound, and skin
- **Mute Option**: Turn move and game-over sounds on or off
- **Computer Move Delay**: Short pause before AI moves for a more natural feel
- **Website Link**: Click **www.versionpb.co.in** on the splash screen or bottom bar to open the site
- **Help Menu**: `Help > How to Play` explains rules, shortcuts, and difficulty behavior
- **Logic Tests**: Lightweight tests cover board position mapping, wins, draws, and invalid cells

## How to Play

1. Launch the game with `dist/KaataZero.jar` or `dist/KaataZero.exe`
2. On the splash screen, click **Play** to start
3. Open `Settings > Game Settings` to choose mode, difficulty, starter, skin, and sound
4. Click an empty cell to place your mark
5. Get three marks in a row horizontally, vertically, or diagonally to win
6. Use **Restart**, **Undo**, **Reset**, or the matching keyboard shortcuts during play
7. Click **www.versionpb.co.in** anytime to visit the project website

### Keyboard Shortcuts

| Key | Action |
|-----|--------|
| `1` - `9` | Play the matching board cell (top-left = 1, bottom-right = 9) |
| `R` | Restart the current game |
| `U` | Undo the latest move |
| `S` | Reset score and session history |
| `Esc` | Exit the application |

### Board Cell Layout

```text
1 | 2 | 3
---------
4 | 5 | 6
---------
7 | 8 | 9
```

## Project Structure

```text
src/kaatazero/
  CallGUI.java       Main window, menus, settings, scoring, and skins
  Board.java         Board rendering, input handling, and game flow
  KaataZero.java     Core board state, win/draw detection, and helpers
  ComputerPlayer.java AI move selection for Easy, Medium, and Hard
test/kaatazero/
  KaataZeroLogicTest.java  Plain Java tests for core game logic
Installer/
  KaataZero.nsi      NSIS installer script
dist/
  KaataZero.jar      Runnable application JAR
  KaataZero.exe      Windows launcher
  README.TXT         End-user readme bundled with the installer
```

## Technical Details

### Architecture

| Class | Responsibility |
|-------|----------------|
| `CallGUI.java` | Application frame, menus, controls, preferences, sounds, and themes |
| `Board.java` | Animated board panel, mouse/keyboard moves, undo, and match flow |
| `KaataZero.java` | Matrix operations, position mapping, win-line detection, and reset |
| `ComputerPlayer.java` | Random, tactical, and minimax AI strategies |

### Requirements

- Java Runtime Environment (JRE) 1.7 or newer
- Java Swing support

### Build and Run

Run the packaged JAR:

```bash
java -jar dist/KaataZero.jar
```

Build manually:

```bash
javac -d build/classes src/kaatazero/*.java
jar cfe dist/KaataZero.jar kaatazero.CallGUI -C build/classes .
```

Run from compiled classes:

```bash
java -cp build/classes kaatazero.CallGUI
```

Run logic tests:

```powershell
javac -cp build/classes -d build/test/classes test/kaatazero/*.java
java -cp "build/classes;build/test/classes" kaatazero.KaataZeroLogicTest
```

### Windows Installer

- Build the installer from `Installer/KaataZero.nsi` using NSIS
- Output is placed under `GeneratedEXE/`
- The installer bundles `KaataZero.exe`, `KaataZero.jar`, `README.TXT`, and the application icon

## Author

**Paarth Batra**
- Email: paarth.batra@gmail.com
- Website: [www.versionpb.co.in](http://www.versionpb.co.in)
- Version: 2.0.0.0
- Release Date: July 4, 2026

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for the full release history.

### 2.0.0.0 Highlights

- Added 2-player mode, AI difficulty levels, skins, settings dialog, and persistent stats
- Added undo/reset, keyboard shortcuts, in-window results, and website link
- Refactored `callGUI` to `CallGUI` and improved installer/launcher packaging

## License

This project appears to be a personal/educational implementation. Please contact the author for licensing information.

---

*KaataZero - The best way to pass time!*
