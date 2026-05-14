package modelPlayers;

/**
 * Jugador controlado por la máquina; la lógica de turno la aplica {@code Round} con una estrategia de IA.
 */
public class IAPlayer extends Player {

	/**
	 * Crea un jugador IA con el nombre indicado (suele ser un nombre por defecto tipo {@code IA-1}).
	 *
	 * @param name etiqueta mostrada en mensajes de turno
	 */
	public IAPlayer(String name) {
		super(name);
	}
}
