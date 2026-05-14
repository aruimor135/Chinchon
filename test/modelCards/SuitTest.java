package modelCards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests unitarios básicos de {@link Suit}: nombres y códigos de salida.
 */
class SuitTest {

	@Test
	void golds_isOros() {
		// Preparación
		Suit suit = Suit.GOLDS;
		// Ejecución
		String name = suit.getSpanishName();
		// Comprobación
		assertEquals("Oros", name);
	}

	@Test
	void cups_shortCode_isC() {
		// Preparación
		Suit suit = Suit.CUPS;
		// Ejecución
		String code = suit.getShortCode();
		// Comprobación
		assertEquals("C", code);
	}

	@Test
	void swords_compactSymbol_equalsShortCode() {
		// Preparación
		Suit suit = Suit.SWORDS;
		// Ejecución
		String symbol = suit.getSymbol();
		// Comprobación
		assertEquals("S", symbol);
	}

	@Test
	void clubs_isBastos() {
		// Preparación
		Suit suit = Suit.CLUBS;
		// Ejecución
		String name = suit.getSpanishName();
		// Comprobación
		assertEquals("Bastos", name);
	}
}
