package modelCards;

import java.util.Objects;

/**
 * Representa una carta de la baraja española con palo y valor fijos.
 * <p>
 * Dos cartas son iguales si coinciden palo y rango.
 */
public class Card {

	private final Suit suit;
	private final Rank rank;

	/**
	 * Construye una carta con el palo y el rango indicados.
	 *
	 * @param suit palo de la carta
	 * @param rank rango o figura de la carta
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * Devuelve el palo de esta carta.
	 *
	 * @return el palo
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * Devuelve el rango (valor numérico o figura) de esta carta.
	 *
	 * @return el rango
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Representación compacta para listados en consola (rango + símbolo de palo).
	 *
	 * @return texto corto entre corchetes, por ejemplo {@code [7O]}
	 */
	public String toCompactString() {
		return "[" + getRank().getDisplayLabel() + suit.getSymbol() + "]";
	}

	/**
	 * Descripción legible en español: valor seguido de “de” y nombre del palo.
	 *
	 * @return cadena descriptiva para el jugador
	 */
	@Override
	public String toString() {
		return getRank().getDisplayLabel() + " de " + suit.getSpanishName();
	}

	/**
	 * Imprime en consola una carta como bloque ASCII con bordes Unicode.
	 * <p>
	 * El interior usa el nombre del palo en bloque fijo para alineación visual.
	 */
	public void printCard() {
		final int innerWidth = 9;
		String rankLabel = getRank().getDisplayLabel();
		String topLine = padRight(rankLabel, innerWidth);
		String bottomLine = padLeft(rankLabel, innerWidth);
		String emptyLine = repeat(' ', innerWidth);
		String suitLine = suitAsciiBlock(suit);

		String horizontal = "\u2500".repeat(innerWidth);
		System.out.println("\u250C" + horizontal + "\u2510");
		System.out.println("\u2502" + topLine + "\u2502");
		System.out.println("\u2502" + emptyLine + "\u2502");
		System.out.println("\u2502" + suitLine + "\u2502");
		System.out.println("\u2502" + emptyLine + "\u2502");
		System.out.println("\u2502" + bottomLine + "\u2502");
		System.out.println("\u2514" + horizontal + "\u2518");
	}

	private static String padRight(String s, int width) {
		if (s.length() >= width) {
			return s.substring(0, width);
		}
		return s + repeat(' ', width - s.length());
	}

	private static String padLeft(String s, int width) {
		if (s.length() >= width) {
			return s.substring(0, width);
		}
		return repeat(' ', width - s.length()) + s;
	}

	private static String repeat(char c, int n) {
		StringBuilder b = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			b.append(c);
		}
		return b.toString();
	}

	private static String suitAsciiBlock(Suit suit) {
		String label;
		switch (suit) {
		case GOLDS:
			label = "ORO";
			break;
		case CUPS:
			label = "COPAS";
			break;
		case SWORDS:
			label = "ESPADAS";
			break;
		case CLUBS:
			label = "BASTOS";
			break;
		default:
			label = "?";
			break;
		}
		return center(label, 9);
	}

	private static String center(String text, int width) {
		if (text.length() >= width) {
			return text.substring(0, width);
		}
		int pad = width - text.length();
		int left = pad / 2;
		int right = pad - left;
		return repeat(' ', left) + text + repeat(' ', right);
	}

	/**
	 * Compara esta carta con otro objeto por palo y rango.
	 *
	 * @param o objeto a comparar
	 * @return {@code true} si es la misma carta (mismo palo y rango)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Card)) {
			return false;
		}
		Card card = (Card) o;
		return suit == card.suit && rank == card.rank;
	}

	/**
	 * Código hash coherente con {@link #equals(Object)}.
	 *
	 * @return hash basado en palo y rango
	 */
	@Override
	public int hashCode() {
		return Objects.hash(suit, rank);
	}
}
