package modelCombinations;

import java.util.ArrayList;
import java.util.Collections;

import modelCards.Card;
import modelCards.Rank;
import modelCards.Suit;

/**
 * Evalúa tríos, escaleras, cierres, Chinchón y mano muerta usando máscaras de bits,
 * aprovechando que la mano nunca supera siete u ocho cartas según el contexto.
 */
public class CombinationChecker {

	/**
	 * Copia la lista de cartas omitiendo exactamente la posición indicada.
	 *
	 * @param src       mano origen
	 * @param skipIndex índice a excluir (debe ser válido en {@code src})
	 * @return nueva lista con una carta menos
	 */
	public static ArrayList<Card> copyWithoutIndex(ArrayList<Card> src, int skipIndex) {
		ArrayList<Card> out = new ArrayList<>(src.size() - 1);
		for (int i = 0; i < src.size(); i++) {
			if (i != skipIndex) {
				out.add(src.get(i));
			}
		}
		return out;
	}

	/**
	 * Con ocho cartas (tras robar), indica si existe alguna carta descartable que deje una mano de cierre válida.
	 *
	 * @param eightCards mano con exactamente ocho cartas
	 * @return {@code true} si se puede cerrar descartando una carta concreta
	 */
	public boolean canCloseAfterDraw(ArrayList<Card> eightCards) {
		if (eightCards.size() != 8) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			if (canClose(copyWithoutIndex(eightCards, i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Mejor puntuación de mano muerta alcanzable descartando una de las ocho cartas.
	 *
	 * @param eightCards mano con ocho cartas
	 * @return mínimo de puntos de descarte posible; puede ser {@link Integer#MAX_VALUE} si la lista no tiene tamaño esperado en uso anómalo
	 */
	public int bestDeadwoodWhenDiscardingOne(ArrayList<Card> eightCards) {
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < eightCards.size(); i++) {
			ArrayList<Card> seven = copyWithoutIndex(eightCards, i);
			best = Math.min(best, deadwoodPoints(seven));
		}
		return best;
	}

	/**
	 * Comprueba si existe algún subconjunto de al menos tres cartas que forme trío o escalera válidos.
	 *
	 * @param hand cartas a analizar
	 * @return {@code true} si hay al menos una combinación legal
	 */
	public boolean hasCombination(ArrayList<Card> hand) {
		int n = hand.size();
		int fullMask = (1 << n) - 1;
		for (int sub = fullMask; sub > 0; sub = (sub - 1) & fullMask) {
			if (Integer.bitCount(sub) >= 3 && isValidGroup(subset(hand, sub))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Chinchón: siete cartas consecutivas del mismo palo según el orden español del mazo.
	 *
	 * @param hand exactamente siete cartas
	 * @return {@code true} si forman una escalera completa válida
	 */
	public boolean isChinchon(ArrayList<Card> hand) {
		return hand.size() == 7 && isValidRun(hand);
	}

	/**
	 * Cierre: siete cartas que se pueden particionar en combinaciones legales dejando una carta suelta permitida (valor 1–5).
	 *
	 * @param hand exactamente siete cartas
	 * @return {@code true} si el reparto en grupos cumple las reglas de cierre
	 */
	public boolean canClose(ArrayList<Card> hand) {
		if (hand.size() != 7) {
			return false;
		}
		int fullMask = (1 << 7) - 1;
		return canArrangeClose(fullMask, hand);
	}

	/**
	 * Puntos de mano muerta: suma mínima de valores de cartas que no encajan en tríos o escaleras.
	 *
	 * @param hand cartas a valorar (típicamente siete tras cerrar otro jugador)
	 * @return puntos de penalización según la mejor agrupación posible
	 */
	public int deadwoodPoints(ArrayList<Card> hand) {
		if (hand.isEmpty()) {
			return 0;
		}
		int fullMask = (1 << hand.size()) - 1;
		return minDeadwood(fullMask, hand);
	}

	private int minDeadwood(int mask, ArrayList<Card> hand) {
		if (mask == 0) {
			return 0;
		}
		int best = sumRankValues(mask, hand);
		for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
			if (Integer.bitCount(sub) < 3) {
				continue;
			}
			if (isValidGroup(subset(hand, sub))) {
				best = Math.min(best, minDeadwood(mask ^ sub, hand));
			}
		}
		return best;
	}

	private boolean canArrangeClose(int mask, ArrayList<Card> hand) {
		if (mask == 0) {
			return true;
		}
		int bits = Integer.bitCount(mask);
		if (bits == 1) {
			int idx = Integer.numberOfTrailingZeros(mask);
			int value = hand.get(idx).getRank().getValue();
			return value >= 1 && value <= 5;
		}
		for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
			if (Integer.bitCount(sub) < 3) {
				continue;
			}
			if (isValidGroup(subset(hand, sub))) {
				if (canArrangeClose(mask ^ sub, hand)) {
					return true;
				}
			}
		}
		return false;
	}

	private int sumRankValues(int mask, ArrayList<Card> hand) {
		int sum = 0;
		for (int i = 0; i < hand.size(); i++) {
			if ((mask & (1 << i)) != 0) {
				sum += hand.get(i).getRank().getValue();
			}
		}
		return sum;
	}

	private ArrayList<Card> subset(ArrayList<Card> hand, int mask) {
		ArrayList<Card> out = new ArrayList<>();
		for (int i = 0; i < hand.size(); i++) {
			if ((mask & (1 << i)) != 0) {
				out.add(hand.get(i));
			}
		}
		return out;
	}

	private boolean isValidGroup(ArrayList<Card> group) {
		return isValidSet(group) || isValidRun(group);
	}

	private boolean isValidSet(ArrayList<Card> group) {
		if (group.size() < 3) {
			return false;
		}
		Rank first = group.get(0).getRank();
		for (Card c : group) {
			if (c.getRank() != first) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidRun(ArrayList<Card> group) {
		if (group.size() < 3) {
			return false;
		}
		Suit suit = group.get(0).getSuit();
		ArrayList<Integer> positions = new ArrayList<>();
		for (Card c : group) {
			if (c.getSuit() != suit) {
				return false;
			}
			positions.add(c.getRank().getSequenceIndex());
		}
		Collections.sort(positions);
		for (int i = 1; i < positions.size(); i++) {
			int prev = positions.get(i - 1);
			int cur = positions.get(i);
			if (cur == prev) {
				return false;
			}
			if (cur != prev + 1) {
				return false;
			}
		}
		return true;
	}
}
