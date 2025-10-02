# Changelog

All notable changes to the KaataZero project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0.0] - 2015-12-07

### Added
- Initial release of KaataZero Tic-Tac-Toe game
- Player vs Computer gameplay on 3x3 grid
- Java Swing graphical user interface
- Scoring system with points tracking
- Animated game board with smooth drawing effects
- Menu system with File and Help options
- Custom splash screen and game icons
- Mouse-based interaction for player moves
- Win detection for rows, columns, and diagonals
- Game state management (win/loss/draw detection)
- Random computer move generation
- Status bar showing current move and score
- About dialog with author information
- Executable JAR and Windows executable builds

### Technical Features
- Core game logic in `KaataZero.java`
- GUI framework in `callGUI.java`
- Board rendering and mouse handling in `Board.java`
- Matrix-based game state management
- Thread-based animation system
- Custom graphics rendering with Graphics2D
- Resource management for images and icons

### Game Mechanics
- Computer makes first move (places "0")
- Player alternates with computer (places "X")
- Win conditions: three in a row (horizontal, vertical, diagonal)
- Draw condition: all squares filled with no winner
- Score system: +100 for win, -100 for draw, reset to 0 for loss
- Game restart functionality after each match

### Author Information
- **Developer**: Paarth Batra
- **Contact**: paarth_batra@yahoo.co.in, paarthh2@rediffmail.com
- **Website**: www.versionpb.co.in
- **Serial Number**: 201512070000007

---

## Future Versions

### Planned Features
- Difficulty levels (Easy/Medium/Hard)
- Multiplayer mode (Player vs Player)
- Sound effects and background music
- Enhanced graphics and themes
- Tournament mode with multiple rounds
- Statistics tracking
- Save/load game functionality

### Known Issues
- Computer AI uses random moves (no strategic intelligence)
- No undo functionality
- Limited customization options
- No network multiplayer support

---

*For more information about KaataZero, visit www.versionpb.co.in*
