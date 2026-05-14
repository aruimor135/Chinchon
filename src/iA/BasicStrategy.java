package iA;

import java.util.ArrayList;

import modelCards.Card;
import modelCombinations.CombinationChecker;

/**
 * Estrategia sencilla para la IA: comparar mano muerta al robar, decidir cierre y elegir descarte mínimo.
 */
public class BasicStrategy implements AIStrategy {

	private final CombinationChecker checker;

	/**
	 * Crea la estrategia ligada al verificador de combinaciones compartido con la partida.
	 *
	 * @param checker motor de evaluación de manos
	 */
	public BasicStrategy(CombinationChecker checker) {
		this.checker = checker;
	}

	/**
	 * No utilizado en el flujo actual: {@link modelGame.Round} invoca directamente los demás métodos de esta clase.
	 */
	@Override
	public void playTurn() {
	}

	/**
	 * Decide si conviene coger la carta visible del descarte comparando la mejor mano muerta
	 * tras robar del descarte frente a robar la cima del mazo (si ambas opciones existen).
	 *
	 * @param handSeven  mano con siete cartas antes de robar
	 * @param discardTop carta superior del descarte o {@code null}
	 * @param deckTop    siguiente carta del mazo o {@code null}
	 * @return {@code true} para tomar del descarte cuando mejora o es la única opción viable
	 */
	public boolean chooseTakeDiscard(ArrayList<Card> handSeven, Card discardTop, Card deckTop) {
		if (discardTop == null) {
			return false;
		}
		if (deckTop == null) {
			return true;
		}
		ArrayList<Card> withDiscard = new ArrayList<>(handSeven);
		withDiscard.add(discardTop);
		ArrayList<Card> withDeck = new ArrayList<>(handSeven);
		withDeck.add(deckTop);
		int deadDiscard = checker.bestDeadwoodWhenDiscardingOne(withDiscard);
		int deadDeck = checker.bestDeadwoodWhenDiscardingOne(withDeck);
		return deadDiscard < deadDeck;
	}

	/**
	 * Indica si la IA desea cerrar cuando las reglas lo permiten: cierra si la mano de ocho cartas ya admite cierre legal.
	 *
	 * @param eight          mano tras robar (ocho cartas)
	 * @param closingAllowed {@code false} fuerza no cerrar (por ejemplo primera vuelta de turnos)
	 * @return {@code true} si se puede y conviene cerrar según esta regla simple
	 */
	public boolean wantsToClose(ArrayList<Card> eight, boolean closingAllowed) {
		if (!closingAllowed) {
			return false;
		}
		return checker.canCloseAfterDraw(eight);
	}

	/**
	 * Elige qué carta descartar minimizando los puntos de mano muerta en las siete restantes.
	 *
	 * @param eight mano con ocho cartas antes de descartar
	 * @return índice de la carta a retirar (0–7)
	 */
	public int chooseDiscardIndex(ArrayList<Card> eight) {
		int bestIndex = 0;
		int bestDeadwood = Integer.MAX_VALUE;
		for (int i = 0; i < eight.size(); i++) {
			ArrayList<Card> seven = CombinationChecker.copyWithoutIndex(eight, i);
			int points = checker.deadwoodPoints(seven);
			if (points < bestDeadwood) {
				bestDeadwood = points;
				bestIndex = i;
			}
		}
		return bestIndex;
	}
}
