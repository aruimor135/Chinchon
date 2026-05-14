package modelPlayers;

/**
 * Fábrica de jugadores: centraliza la creación de instancias humanas y de IA para la partida.
 */
public class PlayerFactory {

	/**
	 * Crea un jugador controlado por persona.
	 *
	 * @param name nombre mostrado durante la partida
	 * @return instancia de {@link HumanPlayer}
	 */
	public Player createHumanPlayer(String name) {
		return new HumanPlayer(name);
	}

	/**
	 * Crea un jugador controlado por la máquina.
	 *
	 * @param name etiqueta mostrada en mensajes de turno
	 * @return instancia de {@link IAPlayer}
	 */
	public Player createIAPlayer(String name) {
		return new IAPlayer(name);
	}
}
