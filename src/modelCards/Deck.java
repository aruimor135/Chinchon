package modelCards;

import java.util.ArrayList;
import java.util.Collections;

import modelGame.GameMode;

/**
 * Mazo de cartas: construcción según el modo de partida, barajado y robo desde la cima lógica.
 */
public class Deck {

	private final ArrayList<Card> cards;

	/**
	 * Crea el mazo según el modo (una o dos barajas completas) y lo baraja.
	 *
	 * @param mode una baraja (40 cartas) o dos barajas (80 cartas)
	 */
	public Deck(GameMode mode) {
		cards = new ArrayList<>();
		int decks = mode == GameMode.TWO_DECKS ? 2 : 1;
		for (int d = 0; d < decks; d++) {
			for (Suit suit : Suit.values()) {
				for (Rank rank : Rank.values()) {
					cards.add(new Card(suit, rank));
				}
			}
		}
		shuffle();
	}

	/**
	 * Baraja el mazo de forma aleatoria.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * Indica si no quedan cartas en el mazo.
	 *
	 * @return {@code true} si el mazo está vacío
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * Número de cartas que quedan por robar.
	 *
	 * @return cantidad de cartas en el mazo
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Mira la siguiente carta a robar sin sacarla del mazo.
	 *
	 * @return la carta superior, o {@code null} si el mazo está vacío
	 */
	public Card peekTop() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.get(0);
	}

	/**
	 * Roba y elimina la carta superior del mazo.
	 *
	 * @return la carta robada, o {@code null} si no hay cartas
	 */
	public Card drawCard() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.remove(0);
	}

	/**
	 * Inserta un montón de cartas en el fondo del mazo (índice 0), manteniendo el orden del montón.
	 *
	 * @param pile lista de cartas a colocar bajo las que ya estaban
	 */
	public void addBottom(ArrayList<Card> pile) {
		cards.addAll(0, pile);
	}
}
