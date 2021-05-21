package ru.nsu.spirin.chess.model.match.server;

import ru.nsu.spirin.chess.model.match.server.message.Message;
import ru.nsu.spirin.chess.model.match.server.message.MessageType;
import ru.nsu.spirin.chess.model.player.Alliance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class ConnectedPlayer {
    private final Socket socket;
    private final ObjectInputStream  objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private String playerName;
    private Alliance playerAlliance;
    private volatile boolean isReady;
    private volatile boolean foundOpponent;

    public ConnectedPlayer(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.playerName = null;
        this.playerAlliance = null;
        this.isReady = false;
        this.foundOpponent = false;
        Message message = (Message) readData();
        if (message.getType() == MessageType.PLAYER_NAME) {
            this.playerName = (String) message.getContent();
        }
        else throw new IOException("Player sent invalid data on connection");
    }

    public Socket getSocket() {
        return this.socket;
    }

    public Object readData() throws IOException, ClassNotFoundException {
        return this.objectInputStream.readObject();
    }

    public void writeData(MessageType messageType, Object content) throws IOException {
        this.objectOutputStream.writeObject(new Message(messageType, content));
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Alliance getPlayerAlliance() {
        return this.playerAlliance;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public boolean foundOpponent() {
        return this.foundOpponent;
    }

    public void setPlayerAlliance(Alliance alliance) {
        this.playerAlliance = alliance;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void setFoundOpponent(boolean foundOpponent) {
        this.foundOpponent = foundOpponent;
    }
}
