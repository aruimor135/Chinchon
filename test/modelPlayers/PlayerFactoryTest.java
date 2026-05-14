package modelPlayers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests unitarios básicos de {@link PlayerFactory}.
 */
class PlayerFactoryTest {

	@Test
	void createHumanPlayer_getName_returnsGivenName() {
		// Preparación
		PlayerFactory factory = new PlayerFactory();
		// Ejecución
		Player player = factory.createHumanPlayer("Ana");
		String name = player.getName();
		// Comprobación
		assertEquals("Ana", name);
	}

	@Test
	void createIAPlayer_getName_returnsGivenName() {
		// Preparación
		PlayerFactory factory = new PlayerFactory();
		// Ejecución
		Player player = factory.createIAPlayer("CPU-1");
		String name = player.getName();
		// Comprobación
		assertEquals("CPU-1", name);
	}
}
