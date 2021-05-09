package ru.nsu.spirin.chess.communication;

import ru.nsu.spirin.chess.communication.message.MessageType;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class Client extends NetworkEntity {
    private Socket socket;
    private int errors;

    public Client(String ipAddress, int port, String playerName) throws IOException {
        super(playerName);
        ThreadUtils.submitThread(
                new Thread(() -> {
                    do {
                        try {
                            socket = new Socket(ipAddress, port);
                            setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
                            new Thread(new ConnectionHandler(socket)).start();
                            sendMessage(MessageType.PLAYER_NAME, getPlayerName());
                        }
                        catch (IOException e) {
                            errors++;
                            socket = null;
                            e.printStackTrace();
                        }
                    } while (errors < 5 && socket == null);
                })
        );
    }

    @Override
    public ConnectionStatus connected() {
        return errors >= 5 ? ConnectionStatus.FAILED : socket != null ? ConnectionStatus.CONNECTED : ConnectionStatus.NOT_CONNECTED;
    }

    @Override
    public void closeConnection() {
        try {
            if (socket != null) socket.close();
        }
        catch (IOException ignored) {}
    }
}
