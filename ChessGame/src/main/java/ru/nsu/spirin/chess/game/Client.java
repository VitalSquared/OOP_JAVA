package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.game.message.MessageType;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class Client extends NetworkEntity {
    private Socket  socket;
    private int     errors;
    private boolean closed;

    private final String ipAddress;
    private final int    port;

    public Client(Scene scene, String ipAddress, int port, String playerName) throws IOException {
        super(scene, playerName);
        this.ipAddress = ipAddress;
        this.port = port;
        this.closed = false;
        listenForConnections();
    }

    @Override
    public ConnectionStatus connected() {
        return errors >= 5 ?
                ConnectionStatus.FAILED :
                socket != null ?
                        ConnectionStatus.CONNECTED :
                        ConnectionStatus.NOT_CONNECTED;
    }

    @Override
    public void closeConnection() {
        try {
            if (this.socket != null) this.socket.close();
            this.socket = null;
            this.closed = true;
        }
        catch (IOException e) {
            System.out.println("Unable to close socket: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void listenForConnections() {
        this.errors = 0;
        this.socket = null;
        ThreadUtils.submitThread(new Thread(() -> {
            do {
                try {
                    socket = new Socket(ipAddress, port);
                    setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                    ThreadUtils.submitThread((new ConnectionHandler(socket)));
                    sendMessage(MessageType.PLAYER_NAME, getPlayerName());
                }
                catch (IOException e) {
                    errors++;
                    socket = null;
                    System.out.println("Error in client: " + e.getLocalizedMessage());
                }
            } while (!closed && errors < 5 && socket == null);
        }));
    }
}
