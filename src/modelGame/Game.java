package modelGame;

import java.util.ArrayList;

import iA.BasicStrategy;
import modelCombinations.CombinationChecker;
import modelPlayers.Player;
import modelPlayers.PlayerFactory;
import modelScore.ScoreCalculator;
import userInterface.ConsoleManager;
import userInterface.GameView;

/**
 * Orquesta una partida completa de Chinchón: configuración, rondas sucesivas,
 * puntuación, eliminaciones y determinación del ganador final.
 */
public class Game {

	private static final int SCORE_ELIMINATION_LIMIT = 100;

	private final ArrayList<Player> players;
	private final PlayerFactory playerFactory;
	private final ConsoleManager console;
	private final GameView view;
	private final CombinationChecker combinationChecker;
	private final ScoreCalculator scoreCalculator;
	private final BasicStrategy basicStrategy;

	/**
	 * Prepara los componentes de la partida (vista, consola, motor de combinaciones,
	 * puntuación e IA básica) y una lista de jugadores vacía hasta el arranque.
	 */
	public Game() {
		players = new ArrayList<>();
		playerFactory = new PlayerFactory();
		console = new ConsoleManager();
		view = new GameView();
		combinationChecker = new CombinationChecker();
		scoreCalculator = new ScoreCalculator(combinationChecker);
		basicStrategy = new BasicStrategy(combinationChecker);
	}

	/**
	 * Ejecuta el flujo principal: título, elección de modo y jugadores, bucle de rondas
	 * hasta Chinchón, abandono por rondas sin cerrar o quedar un solo jugador activo,
	 * y mensaje final de ganador o empate según reglas.
	 */
	public void startGame() {
		view.showTitle("Chinchón");
		GameMode mode = console.askGameMode();
		int count = console.askPlayerCount();

		for (int i = 0; i < count; i++) {
			boolean human = console.askIsHuman(i);
			String name;
			if (human) {
				name = console.askPlayerName();
				players.add(playerFactory.createHumanPlayer(name));
			} else {
				name = "IA-" + (i + 1);
				players.add(playerFactory.createIAPlayer(name));
			}
		}

		int unfinishedRounds = 0;
		while (countActivePlayers() > 1) {
			ArrayList<Player> active = getActivePlayers();
			if (active.size() <= 1) {
				break;
			}

			Round round = new Round(combinationChecker, basicStrategy, console, view);
			round.play(active, mode);

			if (round.getChinchonWinner() != null) {
				view.showTitle("Fin de la partida");
				view.showMessage(String.format("%s gana con Chinchón.", round.getChinchonWinner().getName()));
				return;
			}

			if (round.getCloserPlayer() != null) {
				unfinishedRounds = 0;
				applyScoresAfterClose(round.getCloserPlayer(), active);
			} else {
				view.showMessage("La ronda termina sin cerrador.");
				unfinishedRounds++;
				if (unfinishedRounds >= 5) {
					view.showMessage("Demasiadas rondas sin cerrar. Fin de la partida por menor puntuación.");
					break;
				}
			}

			eliminatePlayersPastLimit();
			printStandings();
		}

		Player winner = findWinnerAtEnd();
		view.showTitle("Fin de la partida");
		if (winner != null) {
			view.showMessage(String.format("Ganador: %s (puntos: %d)", winner.getName(), winner.getScore()));
		} else {
			view.showMessage("No hay ganador.");
		}
	}

	private void applyScoresAfterClose(Player closer, ArrayList<Player> active) {
		view.showTitle("Puntuación de la ronda");
		for (Player player : active) {
			if (player == closer) {
				view.showMessage(String.format("%s ha cerrado: 0 puntos en esta ronda.", player.getName()));
				continue;
			}
			int points = scoreCalculator.calculateDeadwoodPoints(player.getHand());
			player.addScore(points);
			view.showMessage(String.format("%s suma %d puntos de mano muerta.", player.getName(), points));
		}
	}

	private void eliminatePlayersPastLimit() {
		for (Player player : players) {
			if (!player.isEliminated() && player.getScore() > SCORE_ELIMINATION_LIMIT) {
				player.setEliminated(true);
				view.showMessage(String.format("%s queda eliminado (puntuación > %d).", player.getName(), SCORE_ELIMINATION_LIMIT));
			}
		}
	}

	private void printStandings() {
		view.showTitle("Marcador");
		for (Player player : players) {
			if (player.isEliminated()) {
				view.showMessage(String.format("%s: eliminado (última puntuación %d)", player.getName(), player.getScore()));
			} else {
				view.showMessage(String.format("%s: %d", player.getName(), player.getScore()));
			}
		}
	}

	private int countActivePlayers() {
		int count = 0;
		for (Player player : players) {
			if (!player.isEliminated()) {
				count++;
			}
		}
		return count;
	}

	private ArrayList<Player> getActivePlayers() {
		ArrayList<Player> active = new ArrayList<>();
		for (Player player : players) {
			if (!player.isEliminated()) {
				active.add(player);
			}
		}
		return active;
	}

	private Player findWinnerAtEnd() {
		ArrayList<Player> active = getActivePlayers();
		if (active.isEmpty()) {
			return null;
		}
		Player best = active.get(0);
		for (Player player : active) {
			if (player.getScore() < best.getScore()) {
				best = player;
			}
		}
		return best;
	}
}
