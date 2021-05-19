package ru.nsu.spirin.chess.model.server;

import ru.nsu.spirin.chess.model.server.message.Message;
import ru.nsu.spirin.chess.model.server.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class ConnectedPlayer {
    private final Socket socket;
    private final ObjectInputStream  objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private String playerName;

    public ConnectedPlayer(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.playerName = null;
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
}
