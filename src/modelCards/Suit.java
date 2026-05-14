package modelCards;

/**
 * Palos de la baraja española usados en el juego.
 * <p>
 * Cada constante incluye un código corto para salida compacta y el nombre en español.
 */
public enum Suit {
	GOLDS("G", "Oros"),
	CUPS("C", "Copas"),
	SWORDS("S", "Espadas"),
	CLUBS("B", "Bastos");

	private final String shortCode;
	private final String spanishName;

	Suit(String shortCode, String spanishName) {
		this.shortCode = shortCode;
		this.spanishName = spanishName;
	}

	/**
	 * Código de una letra usado en representaciones compactas.
	 *
	 * @return letra del palo (G, C, S, B)
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * Nombre del palo en español para mensajes al usuario.
	 *
	 * @return nombre completo del palo
	 */
	public String getSpanishName() {
		return spanishName;
	}

	/**
	 * Símbolo mostrado junto al rango en salida compacta por consola.
	 * <p>
	 * Coincide con el código corto para compatibilidad con todas las consolas.
	 *
	 * @return misma cadena que {@link #getShortCode()}
	 */
	public String getSymbol() {
		return shortCode;
	}
}
