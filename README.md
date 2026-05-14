# 🃏 Chinchón - Juego en Java

Proyecto realizado en Java sobre el juego de cartas **Chinchón**.

El juego funciona completamente por consola y permite partidas entre jugadores humanos e IA.

---

## 📚 Documentación

Para una explicación detallada de cada clase y componente del proyecto, consulta [Documentacion.md](Documentacion.md).

---

## 📚 Reglas

[Reglas.md](Reglas.md)

---

## 📁 Estructura del proyecto

```
src/
├── main/
│   └── Main.java
├── modelGame/
│   ├── Game.java 
│   ├── Round.java     
│   ├── TurnManager.java   
│   ├── GameMode.java   
│   └── RoundResult.java    
├── modelCards/
│   ├── Card.java    
│   ├── Deck.java        
│   ├── DiscardPile.java      
│   ├── Suit.java   
│   └── Rank.java            
├── modelPlayers/
│   ├── Player.java      
│   ├── HumanPlayer.java    
│   ├── IAPlayer.java        
│   └── PlayerAction.java    
├── modelCombinations/
│   ├── Combination.java          
│   ├── CombinationChecker.java   
│   ├── SetCombination.java       
│   └── RunCombination.java     
├── modelScore/
│   └── ScoreCalculator.java   
├── iA/
│   ├── AIStrategy.java        
│   └── BasicStrategy.java    
└── userInterface/
    ├── ConsoleInput.java 
    ├── ConsoleManager.java   
    ├── GameView.java
    └── Messages.java
```

---

## Funcionamiento general

La partida se divide en rondas. Cada ronda:

1. **Reparto**: distribuye 3 cartas a cada jugador
2. **Turnos**: jugadores alternan robando y descartando
3. **Cierre**: un jugador puede cerrar cuando cree tener una buena mano
4. **Puntuación**: se calculan puntos basados en mano muerta
5. **Eliminación**: jugadores con ≥100 puntos son eliminados
6. **Repetición**: nueva ronda hasta Chinchón o un solo jugador activo