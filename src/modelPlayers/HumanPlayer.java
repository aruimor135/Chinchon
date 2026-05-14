package modelPlayers;

/**
 * Jugador controlado por una persona; las decisiones de turno se leen por consola.
 */
public class HumanPlayer extends Player {

	/**
	 * Crea un jugador humano con el nombre indicado.
	 *
	 * @param name nombre mostrado durante la partida
	 */
	public HumanPlayer(String name) {
		super(name);
	}
}
