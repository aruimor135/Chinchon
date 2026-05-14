package modelGame;

import java.util.ArrayList;

import iA.BasicStrategy;
import modelCards.Card;
import modelCards.Deck;
import modelCards.DiscardPile;
import modelCombinations.CombinationChecker;
import modelPlayers.HumanPlayer;
import modelPlayers.IAPlayer;
import modelPlayers.Player;
import userInterface.ConsoleManager;
import userInterface.GameView;

/**
 * Una ronda de juego: reparto, turnos alternos humano/IA, cierre, Chinchón y reciclaje mazo/descarte.
 */
public class Round {

	private final CombinationChecker combinationChecker;
	private final BasicStrategy basicStrategy;
	private final ConsoleManager console;
	private final GameView view;

	private Deck deck;
	private DiscardPile discard;
	private Player chinchonWinner;
	private Player closerPlayer;

	/**
	 * Construye una ronda con las dependencias necesarias para reglas, IA y consola.
	 *
	 * @param combinationChecker evaluador de combinaciones y cierres
	 * @param basicStrategy      estrategia simple para la IA
	 * @param console            lectura de opciones del jugador humano
	 * @param view               salida de mensajes y mano por pantalla
	 */
	public Round(CombinationChecker combinationChecker, BasicStrategy basicStrategy, ConsoleManager console,
			GameView view) {
		this.combinationChecker = combinationChecker;
		this.basicStrategy = basicStrategy;
		this.console = console;
		this.view = view;
	}

	/**
	 * Si alguien ganó la partida con Chinchón en esta ronda, devuelve ese jugador.
	 *
	 * @return el ganador por Chinchón, o {@code null} si no hubo victoria inmediata
	 */
	public Player getChinchonWinner() {
		return chinchonWinner;
	}

	/**
	 * Jugador que cerró la ronda legalmente (si la ronda terminó por cierre).
	 *
	 * @return el cerrador, o {@code null} si la ronda acabó sin cierre válido
	 */
	public Player getCloserPlayer() {
		return closerPlayer;
	}

	/**
	 * Ejecuta la ronda completa para la lista de jugadores activos y el modo de baraja.
	 * <p>
	 * Reparte siete cartas, coloca carta inicial de descarte y alterna turnos hasta
	 * Chinchón, cierre o agotamiento imposible de robar, respetando un tope de seguridad de iteraciones.
	 *
	 * @param players lista de jugadores que participan (no eliminados de la partida)
	 * @param gameMode una o dos barajas para construir el {@link Deck}
	 */
	public void play(ArrayList<Player> players, GameMode gameMode) {
		chinchonWinner = null;
		closerPlayer = null;

		deck = new Deck(gameMode);
		discard = new DiscardPile();

		for (Player player : players) {
			player.clearHand();
		}

		dealSevenEach(players);

		Card starter = deck.drawCard();
		if (starter != null) {
			discard.addCard(starter);
		}

		TurnManager turnManager = new TurnManager();
		int playersInRound = players.size();
		int completedTurns = 0;
		int safetyTurns = 0;

		while (true) {
			if (safetyTurns++ > 10000) {
				view.showMessage("La ronda se detiene (límite de seguridad). No hay cerrador.");
				return;
			}
			Player current = players.get(turnManager.getCurrentTurn());

			view.showTitle(String.format("Turno: %s | Puntos: %d", current.getName(), current.getScore()));
			if (discard.isEmpty()) {
				view.showMessage("Descarte visible: (vacío)");
			} else {
				Card top = discard.getTopCard();
				view.showMessage(String.format("Descarte visible: %s %s", top.toCompactString(), top.toString()));
			}
			view.showMessage(String.format("Cartas en el mazo: %d", deck.size()));

			boolean canCloseNow = completedTurns >= playersInRound;

			boolean roundEnded = false;
			if (current instanceof HumanPlayer) {
				roundEnded = playHumanTurn(current, canCloseNow);
			} else if (current instanceof IAPlayer) {
				roundEnded = playAiTurn(current, canCloseNow);
			}

			if (chinchonWinner != null) {
				return;
			}
			if (roundEnded && closerPlayer != null) {
				return;
			}

			completedTurns++;
			turnManager.nextTurn(playersInRound);
		}
	}

	private void dealSevenEach(ArrayList<Player> players) {
		for (int i = 0; i < 7; i++) {
			for (Player player : players) {
				player.addCard(deck.drawCard());
			}
		}
	}

	private boolean playHumanTurn(Player player, boolean canCloseNow) {
		refillDeckIfNeeded();

		view.showMessage("Tu mano:");
		view.showHand(player.getHand());

		Card deckTop = deck.peekTop();
		Card discardTop = discard.isEmpty() ? null : discard.getTopCard();

		view.showMessage("Robar de:");
		view.showMessage("1 - Mazo");
		view.showMessage("2 - Descarte (carta superior)" + (discardTop == null ? " (no disponible)" : ""));
		int drawChoice = console.getInput().readIntInRange(1, 2);
		if (drawChoice == 2 && discardTop == null) {
			view.showMessage("No hay descarte. Robando del mazo.");
			drawChoice = 1;
		}

		Card drawn;
		if (drawChoice == 2) {
			drawn = discard.takeTopCard();
		} else {
			refillDeckIfNeeded();
			drawn = deck.drawCard();
			if (drawn == null && !discard.isEmpty()) {
				view.showMessage("Mazo vacío. Coges del descarte.");
				drawn = discard.takeTopCard();
			}
		}

		if (drawn == null) {
			view.showMessage("No quedan cartas para robar. La ronda termina sin cerrador.");
			return false;
		}

		player.addCard(drawn);
		view.showMessage(String.format("Has robado: %s", drawn.toCompactString()));

		ArrayList<Card> eight = player.getHand();
		view.showHand(eight);

		if (canCloseNow && combinationChecker.canCloseAfterDraw(eight)) {
			view.showMessage("Puedes cerrar la ronda. ¿Cerrar? (1 = sí, 0 = no)");
			int closeChoice = console.getInput().readIntInRange(0, 1);
			if (closeChoice == 1) {
				return handleClose(player, eight);
			}
		} else if (!canCloseNow && combinationChecker.canCloseAfterDraw(eight)) {
			view.showMessage("No se puede cerrar en la primera vuelta completa de turnos.");
		}

		view.showMessage("Elige el número de la carta a descartar (1 - 8):");
		int discardChoice = console.getInput().readIntInRange(1, 8);
		Card toDiscard = player.getHand().remove(discardChoice - 1);
		discard.addCard(toDiscard);
		view.showMessage(String.format("Descartas: %s", toDiscard.toCompactString()));

		checkChinchonAfterDiscard(player);
		return false;
	}

	private boolean playAiTurn(Player player, boolean canCloseNow) {
		refillDeckIfNeeded();

		ArrayList<Card> seven = new ArrayList<>(player.getHand());
		Card deckTop = deck.peekTop();
		Card discardTop = discard.isEmpty() ? null : discard.getTopCard();

		boolean takeDiscard = basicStrategy.chooseTakeDiscard(seven, discardTop, deckTop);
		if (takeDiscard && discardTop == null) {
			takeDiscard = false;
		}

		Card drawn;
		if (takeDiscard) {
			drawn = discard.takeTopCard();
			view.showMessage(String.format("%s coge del descarte %s", player.getName(), drawn.toCompactString()));
		} else {
			refillDeckIfNeeded();
			drawn = deck.drawCard();
			if (drawn == null && discardTop != null) {
				drawn = discard.takeTopCard();
				view.showMessage(String.format("%s coge del descarte (mazo vacío) %s", player.getName(), drawn.toCompactString()));
			} else {
				view.showMessage(String.format("%s roba del mazo.", player.getName()));
			}
		}

		if (drawn == null) {
			view.showMessage(String.format("%s no puede robar. La ronda termina sin cerrador.", player.getName()));
			return false;
		}

		player.addCard(drawn);
		ArrayList<Card> eight = player.getHand();

		if (canCloseNow && basicStrategy.wantsToClose(eight, true) && combinationChecker.canCloseAfterDraw(eight)) {
			view.showMessage(String.format("%s cierra la ronda.", player.getName()));
			return handleClose(player, eight);
		}

		int discardIndex = basicStrategy.chooseDiscardIndex(eight);
		Card toDiscard = player.getHand().remove(discardIndex);
		discard.addCard(toDiscard);
		view.showMessage(String.format("%s descarta %s", player.getName(), toDiscard.toCompactString()));

		checkChinchonAfterDiscard(player);
		return false;
	}

	private boolean handleClose(Player player, ArrayList<Card> eight) {
		int removeIdx = findClosingRemovalIndex(eight);
		if (removeIdx < 0) {
			view.showMessage("Cierre no válido.");
			int idx;
			if (player instanceof HumanPlayer) {
				view.showMessage("Elige el número de la carta a descartar (1 - 8):");
				idx = console.getInput().readIntInRange(1, 8) - 1;
			} else {
				idx = basicStrategy.chooseDiscardIndex(eight);
			}
			Card toDiscard = player.getHand().remove(idx);
			discard.addCard(toDiscard);
			return false;
		}

		Card extra = player.getHand().remove(removeIdx);
		discard.addCard(extra);

		closerPlayer = player;
		ArrayList<Card> sevenNow = player.getHand();
		if (combinationChecker.isChinchon(sevenNow)) {
			chinchonWinner = player;
			view.showMessage(String.format("%s gana con Chinchón.", player.getName()));
		} else {
			view.showMessage(String.format("%s ha cerrado la ronda.", player.getName()));
		}
		return true;
	}

	private int findClosingRemovalIndex(ArrayList<Card> eight) {
		for (int i = 0; i < eight.size(); i++) {
			if (combinationChecker.canClose(CombinationChecker.copyWithoutIndex(eight, i))) {
				return i;
			}
		}
		return -1;
	}

	private void checkChinchonAfterDiscard(Player player) {
		ArrayList<Card> hand = player.getHand();
		if (hand.size() == 7 && combinationChecker.isChinchon(hand)) {
			chinchonWinner = player;
			view.showMessage(String.format("%s completa un Chinchón y gana la partida.", player.getName()));
		}
	}

	private void refillDeckIfNeeded() {
		while (deck.isEmpty() && discard.size() > 1) {
			ArrayList<Card> buried = discard.drainExceptTop();
			deck.addBottom(buried);
			deck.shuffle();
			view.showMessage("Se ha rellenado el mazo con el descarte (excepto la carta superior visible).");
		}
		if (deck.isEmpty() && discard.size() == 1) {
			ArrayList<Card> one = new ArrayList<>();
			one.add(discard.takeTopCard());
			deck.addBottom(one);
			deck.shuffle();
			view.showMessage("La última carta del descarte pasa al mazo.");
		}
	}
}
