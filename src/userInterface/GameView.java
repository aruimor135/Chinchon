package userInterface;

import java.util.ArrayList;

import modelCards.Card;

/**
 * Presentación por consola: mano con dibujo ASCII de cartas, mensajes sueltos y títulos de sección.
 */
public class GameView {

	/**
	 * Muestra cada carta de la mano con posición (desde 1), texto compacto, descripción y bloque gráfico.
	 *
	 * @param hand lista ordenada de cartas en mano
	 */
	public void showHand(ArrayList<Card> hand) {
		int position = 1;
		for (Card card : hand) {
			System.out.println(String.format("%d: %s  (%s)", position, card.toCompactString(), card.toString()));
			card.printCard();
			System.out.println();
			position++;
		}
	}

	/**
	 * Imprime una línea de texto (mensaje informativo o de error amistoso).
	 *
	 * @param message texto a mostrar
	 */
	public void showMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Imprime un título destacado con separadores visuales.
	 *
	 * @param title texto del bloque (por ejemplo nombre de fase de juego)
	 */
	public void showTitle(String title) {
		System.out.println();
		System.out.println("--- " + title + " ---");
	}
}
