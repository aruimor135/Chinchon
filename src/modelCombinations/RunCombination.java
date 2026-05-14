package modelCombinations;

/**
 * Marcador de combinación por escalera del mismo palo (implementación mínima usada como tipo simbólico).
 * <p>
 * Las escaleras se validan en {@link CombinationChecker}.
 */
public class RunCombination implements Combination {

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
