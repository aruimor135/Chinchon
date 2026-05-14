package modelCombinations;

/**
 * Marcador de combinación por trío o grupo del mismo rango (implementación mínima reservada al motor actual).
 * <p>
 * La validez concreta de grupos se calcula en {@link CombinationChecker}; esta clase actúa como tipo de combinación.
 */
public class SetCombination implements Combination {

	/**
	 * En el diseño actual siempre devuelve {@code true}; la lógica real está centralizada en el verificador.
	 *
	 * @return {@code true}
	 */
	@Override
	public boolean isValid() {
		return true;
	}
}
