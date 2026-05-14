package modelGame;

/**
 * Resultado simple de una ronda identificado por el nombre del ganador.
 * <p>
 * Clase de datos mínima para representar quién se impuso en la ronda.
 */
public class RoundResult {

	private String winner;

	/**
	 * Crea un resultado asociado al nombre del jugador ganador.
	 *
	 * @param winner nombre del ganador de la ronda
	 */
	public RoundResult(String winner) {
		this.winner = winner;
	}

	/**
	 * Devuelve el nombre almacenado del ganador.
	 *
	 * @return nombre del ganador
	 */
	public String getWinner() {
		return winner;
	}
}
