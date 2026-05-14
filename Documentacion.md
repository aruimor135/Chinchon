# Documentación - Chinchón

## Índice por paquete

| Paquete | Clases |
|---------|--------|
| `main` | Main |
| `modelGame` | Game, Round, TurnManager, GameMode, RoundResult |
| `modelCards` | Card, Deck, DiscardPile, Suit, Rank |
| `modelPlayers` | Player, HumanPlayer, IAPlayer, PlayerAction |
| `modelCombinations` | Combination, CombinationChecker, SetCombination, RunCombination |
| `modelScore` | ScoreCalculator |
| `iA` | AIStrategy, BasicStrategy |
| `userInterface` | ConsoleInput, ConsoleManager, GameView, Messages |

---

## `main`

### Main.java
**Paquete:** `main`  
**Función:** Punto de entrada de la aplicación.  
**Métodos:**
- `main(String[] args)` - Inicializa Game y ejecuta la partida

---

## `modelGame`

### Game.java
**Paquete:** `modelGame`  
**Función:** Orquestador principal - gestiona configuración, rondas, puntuación y eliminaciones.  
**Métodos:**
- `startGame()` - Flujo completo de la partida
- `getActivePlayers()` - Retorna jugadores no eliminados
- `eliminatePlayersPastLimit()` - Elimina jugadores con ≥100 puntos
- `findWinnerAtEnd()` - Determina ganador final

### Round.java
**Paquete:** `modelGame`  
**Función:** Gestiona una sola ronda - reparto, turnos, cierre, Chinchón y reciclaje de mazo.  
**Métodos:**
- `play(ArrayList<Player> active, GameMode mode)` - Ejecuta la ronda completa
- `dealCards(ArrayList<Player> active)` - Reparte 3 cartas a cada jugador
- `manageTurns(ArrayList<Player> active)` - Loop de turnos
- `playerTurn(Player player, ArrayList<Player> active)` - Turno de un jugador
- `getChinchonWinner()` - Retorna ganador por Chinchón (o null)
- `getCloserPlayer()` - Retorna jugador que cerró (o null)

### TurnManager.java
**Paquete:** `modelGame`  
**Función:** Gestiona el índice del turno actual de forma circular.  
**Métodos:**
- `nextTurn(int totalPlayers)` - Avanza al siguiente jugador
- `getCurrentTurn()` - Retorna índice del turno actual

### GameMode.java
**Paquete:** `modelGame`  
**Función:** Enumerado - especifica si la partida usa 1 o 2 barajas.  
**Valores:** `ONE_DECK`, `TWO_DECKS`

### RoundResult.java
**Paquete:** `modelGame`  
**Función:** Clase de datos - almacena el nombre del ganador de una ronda.  
**Métodos:**
- `getWinner()` - Retorna nombre del ganador

---

## `modelCards`

### Card.java
**Paquete:** `modelCards`  
**Función:** Representa una carta individual (palo + rango inmutable).  
**Métodos:**
- `getSuit()` - Retorna palo
- `getRank()` - Retorna rango
- `getValue()` - Retorna valor numérico para puntuación
- `toCompactString()` - Formato breve (ej: "1G")
- `toString()` - Formato legible (ej: "1 de Oros")
- `printCard()` - Dibuja carta en ASCII art

### Deck.java
**Paquete:** `modelCards`  
**Función:** Mazo de cartas - se crea según modo, se baraja y permite robos.  
**Métodos:**
- `shuffle()` - Baraja aleatoriamente
- `drawCard()` - Retorna y elimina primera carta
- `hasCards()` - Verifica si hay cartas
- `isEmpty()` - ¿Mazo vacío?

### DiscardPile.java
**Paquete:** `modelCards`  
**Función:** Pila de descarte - la carta superior es visible para robar.  
**Métodos:**
- `addCard(Card card)` - Añade carta al tope
- `getTopCard()` - Retorna carta superior sin remover
- `removeTopCard()` - Retorna y elimina carta superior
- `isEmpty()` - ¿Pila vacía?

### Suit.java
**Paquete:** `modelCards`  
**Función:** Enumerado de los 4 palos españoles.  
**Valores:** `GOLDS` (Oros), `CUPS` (Copas), `SWORDS` (Espadas), `CLUBS` (Bastos)

### Rank.java
**Paquete:** `modelCards`  
**Función:** Enumerado de los 10 rangos válidos (1-7, 10-12).  
**Valores:** `ONE` a `SEVEN`, `JACK`, `KNIGHT`, `KING`

---

## `modelPlayers`

### Player.java
**Paquete:** `modelPlayers`  
**Función:** Clase abstracta base - define datos y comportamiento común (nombre, mano, puntuación).  
**Métodos:**
- `addCard(Card card)` - Añade carta a la mano
- `removeCard(int index)` - Elimina carta en índice
- `clearHand()` - Vacía la mano
- `getHand()` - Retorna lista de cartas
- `getName()` - Retorna nombre
- `getScore()` - Retorna puntuación acumulada
- `addScore(int points)` - Suma puntos
- `isEliminated()` - ¿Fue eliminado?

### HumanPlayer.java
**Paquete:** `modelPlayers`  
**Función:** Jugador controlado por humano (decisiones por consola).  
**Métodos:**
- `HumanPlayer(String name)` - Constructor

### IAPlayer.java
**Paquete:** `modelPlayers`  
**Función:** Jugador controlado por máquina (decisiones automáticas).  
**Métodos:**
- `IAPlayer(String name)` - Constructor

### PlayerAction.java
**Paquete:** `modelPlayers`  
**Función:** Enumerado de acciones posibles en un turno.  
**Valores:** `DRAW`, `DISCARD`, `CLOSE`

---

## `modelCombinations`

### Combination.java
**Paquete:** `modelCombinations`  
**Función:** Interfaz - define contrato para una combinación válida.  
**Métodos:**
- `isValid()` - ¿Es combinación válida?

### CombinationChecker.java
**Paquete:** `modelCombinations`  
**Función:** Motor de detección de tríos, escaleras, Chinchón y mano muerta (usa máscaras de bits).  
**Métodos:**
- `hasAnyValidCombination(ArrayList<Card> hand)` - ¿Hay combinaciones?
- `calculateDeadwood(ArrayList<Card> hand)` - Retorna cartas sin combinar
- `isChinchon(ArrayList<Card> hand)` - ¿Todas forman combinaciones?
- `canClose(ArrayList<Card> hand)` - ¿Puede cerrar?

### SetCombination.java
**Paquete:** `modelCombinations`  
**Función:** Marcador de tipo para combinación de trío (grupo del mismo rango).  
**Métodos:**
- `isValid()` - Siempre true (validación en CombinationChecker)

### RunCombination.java
**Paquete:** `modelCombinations`  
**Función:** Marcador de tipo para combinación de escalera (secuencia del mismo palo).  
**Métodos:**
- `isValid()` - Siempre true (validación en CombinationChecker)

---

## `modelScore`

### ScoreCalculator.java
**Paquete:** `modelScore`  
**Función:** Calcula puntuación de mano muerta (penalización).  
**Métodos:**
- `calculateDeadwoodPoints(ArrayList<Card> hand)` - Retorna puntos de penalización

---

## `iA`

### AIStrategy.java
**Paquete:** `iA`  
**Función:** Interfaz - define contrato genérico de una estrategia de IA.  
**Métodos:**
- `playTurn()` - Punto de extensión reservado

### BasicStrategy.java
**Paquete:** `iA`  
**Función:** Estrategia básica de IA - comparar mano, descartar mínimo, decidir cierre.  
**Métodos:**
- `shouldKeepDrawnCard(ArrayList<Card> hand, Card newCard, ArrayList<Card> handAfterDiscard)` - ¿Mantener carta robada?
- `selectCardToDiscard(ArrayList<Card> hand)` - Selecciona carta de mínimo valor
- `shouldClose(ArrayList<Card> hand)` - ¿Cerrar la ronda?

---

## `userInterface`

### ConsoleInput.java
**Paquete:** `userInterface`  
**Función:** Lectura robusta de entrada estándar (números, rangos, cadenas).  
**Métodos:**
- `readInt()` - Lee entero (reintenta si es inválido)
- `readIntInRange(int min, int max)` - Lee entero dentro de rango
- `readLine()` - Lee línea completa recortada
- `readString()` - Lee nombre (usa default si vacío)

### ConsoleManager.java
**Paquete:** `userInterface`  
**Función:** Coordinador de entrada para configuración inicial (modo, jugadores, nombres).  
**Métodos:**
- `askGameMode()` - Pregunta modo (1 o 2 barajas)
- `askPlayerCount()` - Pregunta número de jugadores (2-5)
- `askIsHuman(int playerIndex)` - ¿Jugador humano o IA?
- `askPlayerName()` - Solicita nombre
- `getInput()` - Expone acceso al lector

### GameView.java
**Paquete:** `userInterface`  
**Función:** Presentación visual por consola (manos, mensajes, títulos).  
**Métodos:**
- `showHand(ArrayList<Card> hand)` - Muestra mano con ASCII art e índices
- `showMessage(String message)` - Imprime mensaje
- `showTitle(String title)` - Imprime título centrado
- `showStandings(ArrayList<Player> players)` - Muestra clasificación

### Messages.java
**Paquete:** `userInterface`  
**Función:** Centraliza textos de interfaz en español.  
**Métodos:**
- Constantes: `ASK_PLAYER_COUNT`, `ASK_PLAYER_NAME`, `GAME_TITLE`, `ERR_INVALID_NUMBER`, etc.
- Fábricas: `handLine()`, `playerScoreLine()`, `winsWithChinchon()`, etc.

---


## Flujo de ejecución

1. `Main.main()` crea `Game`
2. `Game.startGame()` inicia
3. Configuración: modo y jugadores
4. **Bucle rondas:** mientras haya 2+ jugadores activos
   - `Round.play()` ejecuta ronda
   - Verifica Chinchón → fin de partida
   - Verifica cierre → aplica puntuación
   - Elimina jugadores con ≥100 puntos
5. Determina ganador (menor puntuación)
