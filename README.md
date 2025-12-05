# Texas Hold'em Game Prototype

[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://java.com)

> A highly modular Texas Hold'em game engine with clean separation of concerns and extensive design pattern applications.  
> This prototype demonstrates a poker system architecture suitable for AI development,
> multiplayer extensions, and educational purposes.

## Table of Contents
- [Architecture Overview](#architecture-overview)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
    - [Requirements](#requirements)
    - [Basic Usage](#basic-usage)
- [Customization](#customization)
    - [Decision Maker](#decision-maker)
    - [Replay Parser](#replay-parser)
    - [CardTable Configuration](#cardtable-configuration)
- [Testing](#testing)
- [Extension Points](#extension-points)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)
- [Contributing](#contributing)

## Architecture Overview

The system enforces strict information security through a layered architecture:

### Four-Layer Security Model

|     Layer     |   **CardTable**    |     **CardPlayer**      |  **GamePlayer**  | **PlayerController** |
|:-------------:|:------------------:|:-----------------------:|:----------------:|:--------------------:|
|   Authority   |       Global       |         Private         | Social Info Only |      Untrusted       |
| Functionality | Game Orchestration | Data storing, Filtering |  Social Related  |   Decision Making    |

### Security-First Data Flow
- **Downward flow (Table → Controller)**: Uses immutable Value Objects (VO) with deep copies
- **Upward flow (Controller → Table)**: All decisions validated by CardPlayer layer
- **Private data isolation**: Hole cards never leak beyond owning CardPlayer

## Key Features

### Complete Game Implementation
- Full Texas Hold'em rules (pre-flop, flop, turn, river betting rounds)
- Pot management with side pots for all-in scenarios
- Blind structure with dealer rotation
- Hand ranking and showdown resolution

### Robust Security Architecture
- **Information Hiding**: Players cannot access opponents' private data
- **Immutable Data Transfer**: Deep-copied VOs prevent unauthorized modifications
- **Input Validation**: All external decisions are sanitized before processing

### High Modularity & Extensibility
- **Pluggable Components**: Swap out `CardTable`, `PlayerController`, or `PotManager`
- **Easy AI Integration**: Implement `PlayerController` for custom AI strategies
- **Built-in Replay System**: Record and replay full game sessions
- **Minimal Dependencies**: Core logic is framework-agnostic

### Production-Ready Patterns
- **Factory Pattern**: Flexible object creation (`CardPlayerFactory`, `CardDeckFactory`)
- **Builder Pattern**: Complex VO construction with validation (`PlayerPublicVO.Builder`)
- **State Pattern**: Clear game phase separation (7 states including Init/End)
- **Iterator Pattern**: `PlayerCoil` for natural round-robin decision sequencing
- **Visitor Pattern**: Replay processing via `EntryProcessor` visitors
- **Strategy Pattern**: Interchangeable player decision algorithms
- **Observer Pattern**: Event notification system ready for GUI integration

## Getting Started

### Requirements
- Java 17+
- Maven
- Dependencies:

```xml
<dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.24.0</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

### Basic Usage
- Simply construct a table and start a round:

```java
import impl.control.game.player.RobotGamePlayer;
import impl.control.player.controller.RobotGamePlayerRandom;
import table.CardTable;
import table.impl.ClassicTable;

public class Main {
    public static void main(String[] args) {
        CardTable table = new ClassicTable();
        for (int i = 0; i < 5; i++) {
            table.playerJoin(new RobotGamePlayer(
                    new RobotGamePlayerRandom()));
            table.startRounds();
        }
    }
}
```
- This will create a table with 5 Random robots for 5 rounds.
Default args for the table: initial stack 24, basic bet 2,
no minimum raise limits.

## Customization

### Decision Maker

- To customize your own decision maker, realize a PlayerController:

```java
import control.player.controller.PlayerController;

public class AIPlayer implements PlayerController {

    @Override
    void updatePublicInfo(PublicVO publicVO) {
        //Let your player acquires public info
    }

    @Override
    void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        //Let your player acquires private info
    }

    @Override
    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        //Make decisions according to current info the player have
    }
}
```

- Then customize its PlayerIdentifier:

```java
import control.player.identifier.PlayerIdentifier;
import control.vo.PlayerPersonalVO;

public class AIIdentifier implements PlayerIdentifier {

    private final PlayerPersonalVO vo;

    public AIIdentifier() {
        this.vo = new PlayerPersonalVO();
        //Build your AI identification info VO
    }

    @Override
    public PlayerPersonalVO getPlayerPersonalVO() {
        //By cache form, simply return
        return vo;
    }
}
```

- Build GamePlayer by composition and use it in the game:

```java
import impl.control.game.player.ComposedGamePlayer;
import table.CardTable;
import table.impl.ClassicTable;

public class Main {
    public static void main(String[] args) {
        CardTable table = new ClassicTable();
        table.playerJoin(
                new ComposedGamePlayer(new AIPlayer(), new AIIdentifier()));
        //Your other game logic...
    }
}
```

### Replay Parser

- Currently, no replay parser is provided, but if you need the replay,
follow the instructions below:
- To get the replay:

```java
import table.impl.ClassicTable;
import table.record.reader.RecordReader;

public class GameTable {
    public List<RecordReader> gameWithRep() {
        CardTable table = new ClassicTable();
        //Other logic...
        return ct.startRounds();
    }
}
```

- Each RecordReader in the list contains
a certain player's view entry in the game,
the entry's recorded time, and the player's PlayerIdentifier.

- Realize your own parser, for example, parse to string to log on console:

```java
import table.record.entry.impl.PlayerPrivateInfoVOEntry;
import table.record.entry.impl.PublicInfoVOEntry;
import table.record.processor.EntryProcessor;

public class ReplayParser implements EntryProcessor<String> {
    
    @Override
    public String readEntry(PublicInfoVOEntry publicInfoVOEntry) {
        return publicInfoVOEntry.getTime().toString() +
                publicInfoVOEntry;
    }

    @Override
    public String readEntry(PlayerPrivateInfoVOEntry playerPrivateInfoVOEntry) {
        return playerPrivateInfoVOEntry.getTime().toString() +
                playerPrivateInfoVOEntry;
    }
}
```

- Log players by players:

```java
import lombok.extern.log4j.Log4j2;
import table.record.processor.EntryProcessor;
import table.record.reader.RecordReader;

import java.util.Map;

@Log4j2
public class Main {
    public static void main(String[] args) {
        EntryProcessor<String> parser = new ReplayParser();
        GameTable table = new GameTable();
        //...
        List<RecordReader> rep = table.gameWithRep();
        for (RecordReader reader : rep) {
            log.info(reader.getPlayerIdentifier());
            String entry = reader.readNext(parser);
            while (entry != null) {
                log.info(entry);
                entry = reader.readNext(parser);
            }
        }
    }
}
```

### CardTable Configuration
- Modify TableConfig to access customized settings:

```java
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        TableConfig quickGame = new TableConfig();
        quickGame.maxPlayers() = 4;
        quickGame.playRound() = 3;
        quickGame.initBet() = new BigDecimal(12);

        TableConfig mediumGame = new TableConfig();
        mediumGame.maxPlayers() = 12;
        mediumGame.playRound() = 6;
        mediumGame.initBet() = new BigDecimal(24);

        //Quick game
        CardTable table = new ClassicTable();
        table.setTableConfig(quickGame);
        //Player join and leave...
        table.startRounds();
        
        //Medium game
        //Clear players...
        table.setTableConfig(mediumGame);
        //Player join and leave...
        table.startRounds();
    }
}
```

## Testing

The project includes test coverage:

```bash
# Run unit tests
mvn test

# Run integration tests  
mvn verify

# Generate test coverage report
mvn jacoco:report
```

## Extension Points

### 1. AI Strategy Development
Implement `PlayerController` interface to create custom AI:
- Monte Carlo Tree Search
- Neural Network-based
- Rule-based systems
- Reinforcement Learning agents

### 2. Network Multiplayer
Extend `CardTable` for client-server architecture:
- WebSocket communication
- REST API endpoints
- Real-time synchronization

### 3. GUI Integration
Hook into event system:
- Unity frontend
- Web interface via REST
- Mobile applications

## Project Structure

```
src/main/java/
│
├── control/ # Outer pluggable player control
│   ├── player/
│   │   ├── impl/
│   │   │   ├── ComposedGamePlayer.java
│   │   │   └── RobotGamePlayer.java
│   │   ├── controller/ # Control strategy module
│   │   │   ├── impl/
│   │   │   │   ├── RobotGamePlayerCallOnly.java
│   │   │   │   ├── RobotGamePlayerDoPreset.java
│   │   │   │   └── RobotGamePlayerRandom.java
│   │   │   └── PlayerController.java
│   │   ├── identifier/ # Player identification
│   │   │   ├── impl/
│   │   │   │   └── RobotIdentifier.java
│   │   │   └── PlayerIdentifier.java
│   │   └── GamePlayer.java
│   └── vo/
│       └── PlayerPersonalVO.java
├── exception/
│   ├── IllegalCardException.java
│   └── IllegalOperationException.java
├── listener/
│   └── TableEventListener.java
├── table/
│   ├── card/
│   │   ├── impl/
│   │   │   ├── FixedPokerCard.java
│   │   │   ├── NoJokerDeckFactory.java
│   │   │   └── SimpleCardDeck.java
│   │   ├── CardDeck.java
│   │   ├── CardDeckFactory.java
│   │   ├── PokerCard.java
│   │   ├── Rank.java
│   │   └── Suit.java
│   ├── config/
│   │   └── TableConfig.java
│   ├── impl/
│   │   └── ClassicTable.java
│   ├── player/
│   │   ├── impl/
│   │   │   ├── PlayerCoil.java
│   │   │   ├── SimpleCardPlayer.java
│   │   │   └── SimpleCardPlayerFactory.java
│   │   ├── CardPlayer.java
│   │   ├── CardPlayerFactory.java
│   │   ├── PlayerIterator.java
│   │   └── PlayerList.java
│   ├── pot/
│   │   ├── impl/
│   │   │   └── PotManagerImpl.java
│   │   ├── PlayerRanking.java
│   │   └── PotManager.java
│   ├── record/
│   │   ├── entry/
│   │   │   ├── impl/
│   │   │   │   ├── PlayerPrivateInfoVOEntry.java
│   │   │   │   └── PublicInfoVOEntry.java
│   │   │   └── TimelineEntry.java
│   │   ├── processor/
│   │   │   └── EntryProcessor.java
│   │   ├── reader/
│   │   │   ├── impl/
│   │   │   │   └── SimpleRecordReader.java
│   │   │   └── RecordReader.java
│   │   └── recorder/
│   │       ├── impl/
│   │       │   └── SimpleGameRecorder.java
│   │       └── GameRecorder.java
│   ├── rule/
│   │   ├── decision/
│   │   │   ├── impl/
│   │   │   │   ├── CallDecision.java
│   │   │   │   ├── FoldDecision.java
│   │   │   │   └── RaiseDecision.java
│   │   │   ├── DecisionRequest.java
│   │   │   ├── DecisionType.java
│   │   │   ├── PlayerDecision.java
│   │   │   └── ResolvedAction.java
│   │   └── scoring/
│   │       ├── impl/
│   │       │   └── PokerScoreEvaluator.java
│   │       └── ScoreEvaluator.java
│   ├── state/
│   │   ├── impl/
│   │   │   ├── EndStateHandler.java
│   │   │   ├── FlopStateHandler.java
│   │   │   ├── InitStateHandler.java
│   │   │   ├── PreFlopStateHandler.java
│   │   │   ├── RiverStateHandler.java
│   │   │   ├── ShowdownStateHandler.java
│   │   │   └── TurnStateHandler.java
│   │   ├── GameState.java
│   │   ├── GameStateContext.java
│   │   └── GameStateHandler.java
│   ├── vo/
│   │   ├── privateinfo/
│   │   │   └── PlayerPrivateVO.java
│   │   └── publicinfo/
│   │       ├── builder/
│   │       │   ├── impl/
│   │       │   │   └── ClassicPlayerPublicVOBuilder.java
│   │       │   └── PlayerPublicVOBuilder.java
│   │       ├── PlayerPublicVO.java
│   │       ├── PotPublicVO.java
│   │       ├── PublicVO.java
│   │       └── TablePublicVO.java
│   └── CardTable.java
└── util/
    ├── ApplicationResult.java
    ├── MathUtil.java
    ├── MechanismUtil.java
    └── PlayerUtil.java
```