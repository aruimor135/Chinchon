package modelCombinations;

/**
 * Representa una combinación de cartas (trío, escalera u otra agrupación válida) evaluable por el motor.
 */
public interface Combination {

	/**
	 * Indica si la combinación cumple las reglas del juego.
	 *
	 * @return {@code true} si la agrupación es legal
	 */
	boolean isValid();
}
