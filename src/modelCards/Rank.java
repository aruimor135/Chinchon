package modelCards;

/**
 * Rangos de la baraja española del juego (as, figuras y valores intermedios).
 * <p>
 * Cada rango tiene un valor numérico para puntuación y un índice de secuencia para escaleras.
 */
public enum Rank {

	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	JACK(10),
	KNIGHT(11),
	KING(12);

	private final int value;

	Rank(int value) {
		this.value = value;
	}

	/**
	 * Valor numérico de la carta según las reglas de puntuación del juego.
	 *
	 * @return valor asociado al rango
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Posición dentro de la escalera española (el 10 sigue al 7; sota y caballo encajan después).
	 *
	 * @return índice de 0 a 9 para ordenar cartas consecutivas del mismo palo
	 */
	public int getSequenceIndex() {
		if (value <= 7) {
			return value - 1;
		}
		if (value == 10) {
			return 7;
		}
		if (value == 11) {
			return 8;
		}
		return 9;
	}

	/**
	 * Etiqueta mostrada en consola (el valor numérico como texto).
	 *
	 * @return representación visible del rango
	 */
	public String getDisplayLabel() {
		return Integer.toString(value);
	}
}
