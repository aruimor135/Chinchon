package modelCards;

import java.util.ArrayList;

/**
 * Pila de descarte: la carta superior es la visible para robar; el resto queda bajo ella.
 */
public class DiscardPile {

	private final ArrayList<Card> cards;

	/**
	 * Crea una pila de descarte vacía.
	 */
	public DiscardPile() {
		cards = new ArrayList<>();
	}

	/**
	 * Añade una carta encima de la pila (queda como nueva carta superior).
	 *
	 * @param card carta a descartar
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * Indica si no hay cartas en el descarte.
	 *
	 * @return {@code true} si la pila está vacía
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * Número de cartas acumuladas en el descarte.
	 *
	 * @return tamaño de la pila
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Devuelve la carta visible (la última añadida) sin retirarla.
	 *
	 * @return carta superior
	 * @throws IndexOutOfBoundsException si la pila está vacía
	 */
	public Card getTopCard() {
		return cards.get(cards.size() - 1);
	}

	/**
	 * Retira y devuelve la carta superior del descarte.
	 *
	 * @return la carta que estaba visible
	 * @throws IndexOutOfBoundsException si la pila está vacía
	 */
	public Card takeTopCard() {
		return cards.remove(cards.size() - 1);
	}

	/**
	 * Vacía el descarte por debajo de la carta superior, devolviendo esas cartas en una lista.
	 * <p>
	 * Sirve para rellenar el mazo dejando visible solo la carta de tapa.
	 *
	 * @return cartas extraídas de la base de la pila (todas menos la superior)
	 */
	public ArrayList<Card> drainExceptTop() {
		ArrayList<Card> bottom = new ArrayList<>();
		while (cards.size() > 1) {
			bottom.add(cards.remove(0));
		}
		return bottom;
	}
}
