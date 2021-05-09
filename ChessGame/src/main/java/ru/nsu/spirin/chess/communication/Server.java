package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.communication.message.MessageType;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server extends NetworkEntity {
    private final ServerSocket     serverSocket;
    private boolean accepted;
    private int errors;

    public Server(String ipAddress, int port, String playerName) throws IOException {
        super(playerName);
        this.serverSocket = new ServerSocket(port, 8 , InetAddress.getByName(ipAddress));
        this.accepted = false;

        ThreadUtils.submitThread(
                new Thread(() -> {
                    do {
                        try {
                            Socket socket = serverSocket.accept();
                            setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                            accepted = true;
                            new Thread(new ConnectionHandler(socket)).start();
                            sendMessage(MessageType.PLAYER_NAME, getPlayerName());
                        }
                        catch (IOException e) {
                            errors++;
                            e.printStackTrace();
                        }
                    } while (!accepted && errors < 10);
                })
        );
    }

    @Override
    public ConnectionStatus connected() {
        return errors >= 10 ? ConnectionStatus.FAILED : accepted ? ConnectionStatus.CONNECTED : ConnectionStatus.NOT_CONNECTED;
    }

    @Override
    public void closeConnection() {
        try {
            if (serverSocket != null) serverSocket.close();
        }
        catch (IOException ignored) {}
    }
}
