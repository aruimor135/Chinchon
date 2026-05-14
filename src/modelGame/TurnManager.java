package modelGame;

/**
 * Gestiona el índice del jugador cuyo turno corresponde dentro de una ronda.
 * <p>
 * Avanza de forma circular entre {@code 0} y {@code totalPlayers - 1}.
 */
public class TurnManager {

	private int currentTurn;

	/**
	 * Inicia el gestor con el turno en el primer jugador (índice 0).
	 */
	public TurnManager() {
		currentTurn = 0;
	}

	/**
	 * Pasa al siguiente jugador; al llegar al último, vuelve al primero.
	 *
	 * @param totalPlayers número de jugadores activos en la ronda (debe ser positivo)
	 */
	public void nextTurn(int totalPlayers) {
		currentTurn++;

		if (currentTurn >= totalPlayers) {
			currentTurn = 0;
		}
	}

	/**
	 * Índice del jugador al que le toca actuar ahora.
	 *
	 * @return posición entre 0 y {@code totalPlayers - 1} (según la última llamada a {@link #nextTurn(int)})
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}
}
