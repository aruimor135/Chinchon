package modelScore;

import java.util.ArrayList;

import modelCards.Card;
import modelCombinations.CombinationChecker;

/**
 * Calcula la puntuación de mano muerta delegando en {@link CombinationChecker}.
 */
public class ScoreCalculator {

	private final CombinationChecker combinationChecker;

	/**
	 * Asocia el calculador al motor de combinaciones que define los puntos de descarte.
	 *
	 * @param combinationChecker verificador de grupos y escaleras
	 */
	public ScoreCalculator(CombinationChecker combinationChecker) {
		this.combinationChecker = combinationChecker;
	}

	/**
	 * Puntos de penalización de una mano según la mejor forma de agrupar tríos y escaleras.
	 *
	 * @param hand cartas del jugador a puntuar
	 * @return suma de valores de cartas que quedan como mano muerta
	 */
	public int calculateDeadwoodPoints(ArrayList<Card> hand) {
		return combinationChecker.deadwoodPoints(hand);
	}
}
