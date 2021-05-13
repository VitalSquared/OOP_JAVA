package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.game.message.MessageType;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server extends NetworkEntity {
    private ServerSocket serverSocket;
    private boolean      accepted;

    public Server(Scene scene, String ipAddress, int port, String playerName) throws IOException {
        super(scene, playerName);
        this.serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ipAddress));
        listenForConnections();
    }

    @Override
    public ConnectionStatus connected() {
        return accepted ?
                ConnectionStatus.CONNECTED :
                ConnectionStatus.NOT_CONNECTED;
    }

    @Override
    public void closeConnection() {
        try {
            if (serverSocket != null) serverSocket.close();
            this.serverSocket = null;
            accepted = false;
        }
        catch (IOException e) {
            System.out.println("Unable to close server socket: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void listenForConnections() {
        this.accepted = false;
        ThreadUtils.submitThread(new Thread(() -> {
            do {
                try {
                    Socket socket = serverSocket.accept();
                    setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                    accepted = true;
                    ThreadUtils.submitThread(new Thread(new ConnectionHandler(socket)));
                    sendMessage(MessageType.PLAYER_NAME, getPlayerName());
                }
                catch (IOException e) {
                    System.out.println("Error in server: " + e.getLocalizedMessage());
                }
            } while (!accepted && serverSocket != null);
        }));
    }
}
