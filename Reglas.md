# Reglas del Juego — Chinchón
Este documento describe las reglas y funcionamiento del juego **Chinchón**, implementado en el proyecto Java.

# Objetivo del juego
El objetivo principal del juego es terminar la partida con la **menor cantidad de puntos posible**.

Para conseguirlo, los jugadores deben intentar formar combinaciones válidas con sus cartas y minimizar las cartas que quedan fuera de dichas combinaciones.

Existen dos formas de ganar la partida:
* Realizando un **Chinchón**.
* Siendo el **último jugador activo** que no ha alcanzado el límite máximo de puntos.

Durante toda la partida los jugadores deberán equilibrar riesgo y estrategia para decidir cuándo cerrar una ronda y cuándo seguir jugando para mejorar su mano.


# Baraja utilizada
El juego utiliza la **baraja española**.

Características de la baraja:
* 40 cartas por baraja.
* Posibilidad de jugar con una o varias barajas según configuración.
* Los valores 8 y 9 no se utilizan.

**Palos disponibles**

La baraja está formada por cuatro palos:
* 🪙 Oros
* 🍷 Copas
* ⚔️ Espadas
* 🪵 Bastos

**Valores de las cartas**

Las cartas disponibles son: 1, 2, 3, 4, 5, 6, 7, 10, 11, 12

# Jugadores
Las partidas pueden jugarse entre: **2 y 5 jugadores**.

El sistema permite combinar:
* Jugadores humanos
* Jugadores controlados por IA

Antes de comenzar la partida se realiza una configuración inicial donde se determina:
* número total de jugadores
* número de jugadores humanos
* número de jugadores IA

Cada jugador dispone de:
* una mano de cartas
* una puntuación acumulada
* participación activa hasta quedar eliminado

# Desarrollo general de una ronda
Cada partida se divide en varias rondas independientes.

El funcionamiento básico de una ronda sigue el siguiente flujo:
1. Reparto inicial.
2. Turnos de los jugadores.
3. Formación de combinaciones.
4. Posible cierre de ronda.
5. Cálculo de puntuaciones.
6. Eliminación de jugadores.
7. Inicio de nueva ronda.

## 1. Reparto inicial
Al comenzar una ronda:

cada jugador **recibe 7 cartas**
se genera el **mazo principal**
se crea una **pila de descarte**

La primera carta del descarte se muestra boca arriba para permitir robos desde el descarte.


## 2. Turno del jugador

Durante su turno, el jugador realiza una secuencia concreta de acciones.

### Robar carta

El jugador debe robar una carta obligatoriamente.

Puede elegir entre:

**- Robar del mazo**

Obtiene una carta aleatoria del mazo principal.

**- Robar del descarte**

Obtiene la carta visible situada en la parte superior de la pila de descarte.

La elección depende de la estrategia del jugador y de las combinaciones que intente formar.

### Revisar la mano

Después del robo, el jugador analiza su mano.

El objetivo consiste en:
* crear nuevas combinaciones
* mejorar combinaciones existentes
* reducir puntos futuros

### Descartar carta

Tras evaluar la mano, el jugador debe descartar una carta.

La carta descartada pasa a la pila de descarte.

Al finalizar el turno: **el jugador siempre debe quedarse con exactamente 7 cartas.**

# Combinaciones válidas
Durante la partida los jugadores intentan formar combinaciones de cartas.

El sistema reconoce tres tipos principales.

## Trío
Una combinación de iguales consiste en: **3 o más cartas del mismo valor numérico.**

Ejemplo: 3 Oros — 3 Copas — 3 Espadas

Ejemplo válido: 7 Bastos — 7 Copas — 7 Oros

Las cartas pueden pertenecer a distintos palos.

## Escalera
Una escalera consiste en: **3 o más cartas consecutivas del mismo palo.**

Ejemplo: 5 Copas — 6 Copas — 7 Copas

Todas las cartas deben cumplir dos condiciones:
* mismo palo
* valores consecutivos

## Chinchon
El Chinchón representa la combinación más importante del juego.

Consiste en: **7 cartas consecutivas del mismo palo.**

Ejemplo: 4 Oros — 5 Oros — 6 Oros — 7 Oros — 10 Oros — 11 Oros — 12 Oros

Cuando un jugador consigue un Chinchón válido puede ganar automáticamente la partida.

# Cierre de ronda
Un jugador puede intentar cerrar una ronda si cumple determinadas condiciones.

El cierre se realiza: **después de robar y antes de descartar.**

La carta descartada será la carta utilizada para cerrar.

## Condiciones de cierre
El jugador puede cerrar si:
* no se encuentra en el primer turno
* posee 6 o 7 cartas combinadas

## Restricciones de cierre

No está permitido cerrar cuando:
* el jugador se encuentra en el primer turno.
* supera el límite permitido de puntos tras el conteo.

# Sistema de puntuación
La puntuación se calcula al finalizar cada ronda.

Cuando un jugador cierra: todos los jugadores deben contar los puntos de sus cartas **no combinadas**.

Las cartas utilizadas dentro de combinaciones válidas no suman puntuación.

# Fin de la partida
La partida finaliza cuando ocurre alguna de las siguientes situaciones.

**- Victoria por Chinchón**

Si un jugador consigue un **Chinchón válido**, puede ganar automáticamente la partida.

Esta es la condición de victoria más fuerte del juego.

**- Eliminación por puntos**

El juego establece un **límite máximo de puntos acumulados**.

Ejemplo: 100 puntos

Cuando un jugador:
* alcanza el límite
* o supera el límite

queda eliminado de la partida.

**- Último jugador activo**

Si únicamente queda un jugador sin eliminar, dicho jugador es declarado ganador.

# Resumen rápido de reglas
* 2–5 jugadores.
* 7 cartas por jugador.
* Robar → revisar → descartar.
* Formar combinaciones válidas.
* Cerrar ronda cuando sea posible.
* Las cartas no combinadas suman puntos.
* Eliminación por límite de puntuación.
* Victoria por Chinchón o último jugador activo.