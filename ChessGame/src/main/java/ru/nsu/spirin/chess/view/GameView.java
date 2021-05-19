package ru.nsu.spirin.chess.view;

import com.google.common.collect.Lists;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.player.Player;
import ru.nsu.spirin.chess.utils.ScoresFile;
import ru.nsu.spirin.chess.model.scene.Scene;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class GameView {
    private final Scene      scene;
    private final Controller controller;

    private final ViewChangeWatcher viewChangeWatcher;

    protected GameView(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;
        this.viewChangeWatcher = new ViewChangeWatcher();
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
        for (Entry<String, Integer> score : calculateScores()) {
            sb.append(score.getKey()).append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String getAbout() {
        return "This is a chess match\nCreated by Vitaly Spirin\n";
    }

    public String getRoundResult() {
        Player alliancePlayer = scene.getActiveGame().getPlayerAlliance().choosePlayer(scene.getActiveGame().getBoard().getWhitePlayer(), scene.getActiveGame().getBoard().getBlackPlayer());
        Player currentPlayer = scene.getActiveGame().getBoard().getCurrentPlayer();
        if (currentPlayer.isInStaleMate()) {
            return "DRAW";
        }
        else if (currentPlayer.isInCheckMate()) {
            if (currentPlayer.getAlliance() == alliancePlayer.getAlliance()) {
                return "YOU LOST!";
            }
            else {
                return "YOU WON!";
            }
        }
        else if (scene.getActiveGame().getBoard().getWhitePlayer().isResigned()) {
            if (alliancePlayer.getAlliance() == Alliance.WHITE) {
                return "YOU LOST!";
            }
            else {
                return "YOU WON!";
            }
        }
        else if (scene.getActiveGame().getBoard().getBlackPlayer().isResigned()) {
            if (alliancePlayer.getAlliance() == Alliance.BLACK) {
                return "YOU LOST!";
            }
            else {
                return "YOU WON!";
            }
        }
        return "";
    }

    public String getResultScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your score:\n\n");
        for (Entry<String, Integer> score : scene.getActiveGame().getScoreTexts()) {
            sb.append(score.getKey()).append(":").append(" ".repeat(20 - score.getKey().length())).append("\t").append(score.getValue()).append("\n");
        }
        sb.append("-".repeat(20)).append("\n");
        sb.append("Total").append(" ".repeat(20 - 5)).append("\t").append(ScoresFile.calculateTotalScore(scene)).append("\n");
        return sb.toString();
    }

    protected boolean viewChanged() {
        if (viewChangeWatcher.anyDifference(getScene())) {
            viewChangeWatcher.updateContent(getScene());
            return true;
        }
        return false;
    }

    private List<Entry<String, Integer>> calculateScores() {
        Properties scores = ScoresFile.getScores();
        List<Entry<String, Integer>> scoresList = new ArrayList<>();
        for (String score : scores.stringPropertyNames()) {
            scoresList.add(new AbstractMap.SimpleEntry<>(score, Integer.parseInt((String) scores.get(score))));
        }
        scoresList.sort(Entry.comparingByValue());
        return Lists.reverse(scoresList.stream().skip(Math.max(0, scoresList.size() - 10)).collect(Collectors.toList()));
    }
}
