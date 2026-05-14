package modelScore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modelCards.Card;
import modelCards.Rank;
import modelCards.Suit;
import modelCombinations.CombinationChecker;

/**
 * Tests unitarios básicos de {@link ScoreCalculator}.
 */
class ScoreCalculatorTest {

	@Test
	void emptyHand_calculateDeadwoodPoints_returnsZero() {
		// Preparación
		ScoreCalculator calculator = new ScoreCalculator(new CombinationChecker());
		ArrayList<Card> hand = new ArrayList<>();
		// Ejecución
		int points = calculator.calculateDeadwoodPoints(hand);
		// Comprobación
		assertEquals(0, points);
	}

	@Test
	void singleCard_calculateDeadwoodPoints_matchesRankValue() {
		// Preparación
		ScoreCalculator calculator = new ScoreCalculator(new CombinationChecker());
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card(Suit.SWORDS, Rank.FIVE));
		// Ejecución
		int points = calculator.calculateDeadwoodPoints(hand);
		// Comprobación
		assertEquals(5, points);
	}
}
