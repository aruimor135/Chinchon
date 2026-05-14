package userInterface;

import java.util.Scanner;

/**
 * Lectura robusta desde la entrada estándar: enteros, enteros acotados y cadenas con valores por defecto.
 * <p>
 * Punto único de acceso a la entrada por consola (patrón Singleton).
 */
public class ConsoleInput {

	private static final ConsoleInput INSTANCE = new ConsoleInput();

	private final Scanner scanner;

	/**
	 * Abre un {@link Scanner} sobre {@link System#in} para toda la sesión de consola.
	 */
	private ConsoleInput() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Devuelve la única instancia de lector de consola de la aplicación.
	 *
	 * @return el singleton {@link ConsoleInput}
	 */
	public static ConsoleInput getInstance() {
		return INSTANCE;
	}

	/**
	 * Lee un entero válido reintentando mientras el texto no sea numérico.
	 *
	 * @return número entero introducido por el usuario
	 */
	public int readInt() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Introduce un número válido.");
			}
		}
	}

	/**
	 * Lee enteros hasta que el valor caiga dentro del rango inclusive {@code [min, max]}.
	 *
	 * @param min límite inferior permitido
	 * @param max límite superior permitido
	 * @return valor válido dentro del rango
	 */
	public int readIntInRange(int min, int max) {
		while (true) {
			int value = readInt();
			if (value >= min && value <= max) {
				return value;
			}
			System.out.println(String.format("El valor debe estar entre %d y %d.", min, max));
		}
	}

	/**
	 * Lee una línea completa recortada de espacios iniciales y finales.
	 *
	 * @return texto introducido (puede ser cadena vacía)
	 */
	public String readLine() {
		return scanner.nextLine().trim();
	}

	/**
	 * Lee un nombre o etiqueta no vacía: si el usuario deja la línea en blanco, se usa un nombre por defecto.
	 *
	 * @return cadena no vacía o el nombre por defecto {@code "Jugador"} si la línea está vacía
	 */
	public String readString() {
		String line = scanner.nextLine().trim();
		if (line.isEmpty()) {
			return "Jugador";
		}
		return line;
	}
}
