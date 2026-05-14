package modelPlayers;

/**
 * Acciones conceptuales que un jugador puede realizar en un turno (modelo de alto nivel).
 */
public enum PlayerAction {
	/** Robar una carta del mazo o del descarte. */
	DRAW,
	/** Dejar una carta en la pila de descarte. */
	DISCARD,
	/** Intentar cerrar la ronda con la mano actual. */
	CLOSE
}
