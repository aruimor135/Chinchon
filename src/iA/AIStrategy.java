package iA;

/**
 * Contrato genérico de estrategia de IA; en este proyecto el turno detallado lo resuelve {@code Round}
 * llamando a métodos auxiliares concretos de la implementación.
 */
public interface AIStrategy {

	/**
	 * Punto de extensión reservado; la partida usa en la práctica otros métodos de la estrategia concreta.
	 */
	void playTurn();
}
