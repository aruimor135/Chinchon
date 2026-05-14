package userInterface;

import modelGame.GameMode;

/**
 * Coordina las preguntas de configuración de la partida y expone el lector de consola compartido.
 */
public class ConsoleManager {

	private final ConsoleInput input;

	/**
	 * Crea el gestor usando el {@link ConsoleInput} singleton para leer desde la entrada estándar.
	 */
	public ConsoleManager() {
		input = ConsoleInput.getInstance();
	}

	/**
	 * Pregunta cuántas barajas usar y devuelve el modo correspondiente.
	 *
	 * @return {@link GameMode#ONE_DECK} o {@link GameMode#TWO_DECKS} según la elección (1 o 2)
	 */
	public GameMode askGameMode() {
		System.out.println("¿Cuántas barajas?");
		System.out.println("1 - Una baraja (40 cartas)");
		System.out.println("2 - Dos barajas (80 cartas)");
		int choice = input.readIntInRange(1, 2);
		return choice == 2 ? GameMode.TWO_DECKS : GameMode.ONE_DECK;
	}

	/**
	 * Solicita el número de jugadores humanos o IA que participarán.
	 *
	 * @return entero entre 2 y 5 inclusive
	 */
	public int askPlayerCount() {
		System.out.println("¿Cuántos jugadores? (2 - 5)");
		return input.readIntInRange(2, 5);
	}

	/**
	 * Pregunta si el jugador en la posición dada es humano o controlado por IA.
	 *
	 * @param playerIndex índice base cero del jugador en la configuración
	 * @return {@code true} si el usuario elige humano (entrada 1)
	 */
	public boolean askIsHuman(int playerIndex) {
		System.out.println(String.format("Jugador %d: ¿Humano? (1 = sí, 0 = IA)", playerIndex + 1));
		int choice = input.readIntInRange(0, 1);
		return choice == 1;
	}

	/**
	 * Lee el nombre del jugador humano actual.
	 *
	 * @return nombre no vacío tras recortar espacios, o un nombre por defecto si la línea está vacía
	 */
	public String askPlayerName() {
		System.out.println("Nombre del jugador:");
		return input.readString();
	}

	/**
	 * Acceso al lector de bajo nivel para el resto del flujo de turno (enteros en rango, etc.).
	 *
	 * @return instancia de {@link ConsoleInput} asociada a este gestor
	 */
	public ConsoleInput getInput() {
		return input;
	}
}
