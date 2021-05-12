package ru.nsu.spirin.chess.view;

import com.google.common.collect.Lists;
import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.communication.ConnectionStatus;
import ru.nsu.spirin.chess.communication.GameEntity;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.properties.ScoresFile;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class GameView {
    private final Scene scene;
    private final Controller controller;

    private SceneState prevSceneState;
    private GameEntity prevGameEntity;

    private ConnectionStatus prevConnected;

    private String prevOpponentName;
    private Alliance prevOpponentTeam;
    private Boolean prevOpponentReady;

    private String prevPlayerName;
    private Alliance prevPlayerTeam;
    private Boolean prevPlayerReady;

    private Board prevBoard;

    protected GameView(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;

        this.prevSceneState = null;
        this.prevGameEntity = null;

        this.prevConnected = null;

        this.prevOpponentName = null;
        this.prevOpponentTeam = null;
        this.prevOpponentReady = null;

        this.prevPlayerName = null;
        this.prevPlayerTeam = null;
        this.prevPlayerReady = null;

        this.prevBoard = null;
    }

    public abstract void render();

    public abstract void close();

    protected Scene getScene() {
        return this.scene;
    }

    protected Controller getController() {
        return this.controller;
    }

    public String getHighScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of top 10 high scores:\n\n");
        for (Map.Entry<String, Integer> score : calculateScores()) {
            sb.append(score.getKey()).append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String getAbout() {
        return "This is a chess game\nCreated by Vitaly Spirin\n";
    }

    public String getRoundResult() {
        if (scene.getActiveGame().getBoard().getCurrentPlayer().isInStaleMate()) {
            return "DRAW";
        }
        else {
            Player alliancePlayer = scene.getActiveGame().getPlayerAlliance().choosePlayer(scene.getActiveGame().getBoard().getWhitePlayer(), scene.getActiveGame().getBoard().getBlackPlayer());
            Player currentPlayer = scene.getActiveGame().getBoard().getCurrentPlayer();
            if (currentPlayer.isInCheckMate() || currentPlayer.isResigned()) {
                if (currentPlayer.getAlliance() == alliancePlayer.getAlliance()) {
                    return "YOU LOST!";
                }
                else {
                    return "YOU WON!";
                }
            }
        }
        return "";
    }

    public String getResultScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your score:\n\n");
        for (Map.Entry<String, Integer> score : scene.getActiveGame().getScoreTexts()) {
            sb.append(score.getKey()).append(":").append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
        }
        sb.append("-".repeat(20)).append("\n");
        sb.append("Total").append(" ".repeat(20 - 5)).append("\t").append(calculateTotal()).append("\n");
        return sb.toString();
    }

    public void saveResults() {
        ScoresFile.saveScore(scene.getActiveGame().getPlayerName(), calculateTotal());
    }

    private List<Map.Entry<String, Integer>> calculateScores() {
        Properties scores = ScoresFile.getScores();
        List<Map.Entry<String, Integer>> scoresList = new ArrayList<>();
        for (String score : scores.stringPropertyNames()) {
            scoresList.add(new AbstractMap.SimpleEntry<>(score, Integer.parseInt((String) scores.get(score))));
        }
        scoresList.sort(Map.Entry.comparingByValue());
        return Lists.reverse(scoresList).stream().skip(Math.max(0, scoresList.size() - 10)).collect(Collectors.toList());
    }

    private int calculateTotal() {
        int total = 0;
        for (Map.Entry<String, Integer> score : scene.getActiveGame().getScoreTexts()) {
            total += score.getValue();
        }
        return total;
    }

    //TODO: validate view changing
    protected boolean viewChanged() {
        boolean changed = activeGameChanged() || sceneStateChanged();
        if (!changed) return false;
        this.prevGameEntity = getScene().getActiveGame();
        this.prevSceneState = getScene().getSceneState();
        if (scene.getActiveGame() == null) {
            this.prevConnected = null;

            this.prevOpponentName = null;
            this.prevOpponentTeam = null;
            this.prevOpponentReady = null;

            this.prevPlayerName = null;
            this.prevPlayerTeam = null;
            this.prevPlayerReady = null;
        }
        else {
            this.prevBoard = scene.getActiveGame().getBoard();
            if (scene.getActiveGame() instanceof NetworkEntity) {
                NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
                this.prevConnected = networkEntity.connected();

                this.prevOpponentTeam = networkEntity.getOpponentAlliance();
                this.prevOpponentName = networkEntity.getOpponentName();
                this.prevOpponentReady = networkEntity.isOpponentReady();

                this.prevPlayerTeam = networkEntity.getPlayerAlliance();
                this.prevPlayerName = networkEntity.getPlayerName();
                this.prevPlayerReady = networkEntity.isPlayerReady();
            }
            else {
                this.prevPlayerTeam = scene.getActiveGame().getPlayerAlliance();
                this.prevPlayerName = scene.getActiveGame().getPlayerName();
            }
        }
        return true;
    }

    private boolean activeGameChanged() {
        if (this.prevGameEntity != scene.getActiveGame()) return true;
        if (this.prevGameEntity == null) return false;
        if (this.prevGameEntity.getPlayerAlliance() != scene.getActiveGame().getPlayerAlliance()) return true;
        if (this.prevGameEntity instanceof NetworkEntity) {
            NetworkEntity networkEntity = (NetworkEntity) scene.getActiveGame();
            if (this.prevConnected != networkEntity.connected()) return true;

            if (this.prevOpponentTeam != networkEntity.getOpponentAlliance()) return true;
            if(!this.prevOpponentName.equals(networkEntity.getOpponentName())) return true;
            if (this.prevOpponentReady != networkEntity.isOpponentReady()) return true;

            if (this.prevPlayerTeam != networkEntity.getPlayerAlliance()) return true;
            if(!this.prevPlayerName.equals(networkEntity.getPlayerName())) return true;
            if (this.prevPlayerReady != networkEntity.isPlayerReady()) return true;
        }
        if (this.prevGameEntity.getBoard() == null && scene.getActiveGame().getBoard() == null) return false;
        return this.prevBoard != scene.getActiveGame().getBoard();
    }

    private boolean sceneStateChanged() {
        return this.prevSceneState != getScene().getSceneState();
    }
}
