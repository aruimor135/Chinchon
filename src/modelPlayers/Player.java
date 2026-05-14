package modelPlayers;

import java.util.ArrayList;

import modelCards.Card;

/**
 * Jugador de la partida: mano actual, puntuación acumulada y estado de eliminación.
 * <p>
 * Las subclases distinguen control humano frente a IA sin alterar el modelo de datos.
 */
public abstract class Player {

	protected String name;
	protected ArrayList<Card> hand;
	protected int score;
	protected boolean eliminated;

	/**
	 * Inicializa el jugador con nombre, mano vacía, puntuación cero y no eliminado.
	 *
	 * @param name nombre mostrado en la interfaz
	 */
	public Player(String name) {
		this.name = name;
		hand = new ArrayList<>();
		score = 0;
		eliminated = false;
	}

	/**
	 * Añade una carta al final de la mano (tras robar).
	 *
	 * @param card carta recibida
	 */
	public void addCard(Card card) {
		hand.add(card);
	}

	/**
	 * Elimina la carta situada en el índice dado de la mano.
	 *
	 * @param index posición de la carta a retirar
	 */
	public void removeCard(int index) {
		hand.remove(index);
	}

	/**
	 * Vacía la mano para comenzar una nueva ronda.
	 */
	public void clearHand() {
		hand.clear();
	}

	/**
	 * Lista mutable de la mano actual (misma instancia interna que usa el juego).
	 *
	 * @return cartas en mano
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * Nombre del jugador.
	 *
	 * @return nombre configurado o generado
	 */
	public String getName() {
		return name;
	}

	/**
	 * Puntos acumulados en la partida.
	 *
	 * @return puntuación total
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Suma puntos a la puntuación acumulada (por ejemplo mano muerta al cerrar otra persona).
	 *
	 * @param points cantidad a sumar (no negativa en el flujo normal del juego)
	 */
	public void addScore(int points) {
		score += points;
	}

	/**
	 * Indica si el jugador ha sido eliminado por superar el límite de puntos.
	 *
	 * @return {@code true} si ya no participa en nuevas rondas
	 */
	public boolean isEliminated() {
		return eliminated;
	}

	/**
	 * Marca o desmarca al jugador como eliminado de la partida.
	 *
	 * @param eliminated {@code true} para excluirlo del juego activo
	 */
	public void setEliminated(boolean eliminated) {
		this.eliminated = eliminated;
	}
}
