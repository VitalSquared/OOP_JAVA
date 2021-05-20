package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.model.match.server.ServerMatch;
import ru.nsu.spirin.chess.model.match.server.ConnectedPlayer;
import ru.nsu.spirin.chess.model.match.server.message.Message;
import ru.nsu.spirin.chess.model.match.server.message.MessageType;
import ru.nsu.spirin.chess.thread.ThreadPool;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ServerGame implements GameEntity {
    private final    Scanner      scanner;
    private          ServerSocket serverSocket;
    private volatile boolean      isWorking;

    private final List<ConnectedPlayer> connectedPlayerList;
    private final List<ServerMatch>     serverMatchList;

    public ServerGame() {
        this.isWorking = true;
        this.scanner = new Scanner(System.in);
        this.serverSocket = null;

        while (this.serverSocket == null) {
            try {
                System.out.print("Enter IP: ");
                String ip = scanner.nextLine();
                System.out.print("Enter port: ");
                int port = scanner.nextInt();

                this.serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
            }
            catch (Exception e) {
                this.serverSocket = null;
                System.out.println("Can't create server: " + e.getLocalizedMessage() + ". Try again.");
            }
        }

        System.out.println("Server created. Use 'close' to close server and exit application");
        this.connectedPlayerList = new ArrayList<>();
        this.serverMatchList = new ArrayList<>();

        ThreadPool.INSTANCE.submitTask(new Thread(() -> {
            while (isWorking) {
                try {
                    Socket socket = serverSocket.accept();
                    ConnectedPlayer connectedPlayer = new ConnectedPlayer(socket);
                    String playerName = connectedPlayer.getPlayerName();
                    connectedPlayerList.add(connectedPlayer);
                    ThreadPool.INSTANCE.submitTask(new PlayerThread(connectedPlayer));
                    System.out.printf("Player '%s' connected\n", playerName);
                }
                catch (Exception e) {
                    System.out.println("Error in server: " + e.getLocalizedMessage());
                }
            }
        }));

        ThreadPool.INSTANCE.submitTask(new Thread(() -> {
            while (isWorking) {
                try {
                    for (int i = 0; i < connectedPlayerList.size(); i++) {
                        if (connectedPlayerList.get(i).getSocket().isClosed()) {
                            System.out.println("Player " + connectedPlayerList.get(i).getPlayerName() + " disconnected");
                            connectedPlayerList.remove(i);
                            i--;
                        }
                    }
                    if (connectedPlayerList.size() >= 2) {
                        connectedPlayerList.get(0).writeData(MessageType.PLAYER_FOUND, true);
                        connectedPlayerList.get(1).writeData(MessageType.PLAYER_FOUND, true);
                        ServerMatch serverMatch = new ServerMatch(connectedPlayerList.get(0), connectedPlayerList.get(1));
                        connectedPlayerList.remove(0);
                        connectedPlayerList.remove(0);
                        serverMatchList.add(serverMatch);
                    }
                    for (var match : serverMatchList) {
                        if (match.isMatchOver()) {
                            serverMatchList.remove(match);
                            break;
                        }
                        if (match.needAnotherPlayer()) {
                            ConnectedPlayer player1 = match.getPlayer1();
                            if (player1.getSocket() != null && !player1.getSocket().isClosed() && player1.getSocket().isConnected()) {
                                connectedPlayerList.add(player1);
                                player1.setFoundOpponent(false);
                                ThreadPool.INSTANCE.submitTask(new PlayerThread(player1));
                            }
                            else {
                                System.out.println("Player " + player1.getPlayerName() + " disconnected");
                            }
                            ConnectedPlayer player2 = match.getPlayer2();
                            if (player2.getSocket() != null && !player2.getSocket().isClosed() && player2.getSocket().isConnected()) {
                                connectedPlayerList.add(player2);
                                player2.setFoundOpponent(false);
                                ThreadPool.INSTANCE.submitTask(new PlayerThread(player2));
                            }
                            else {
                                System.out.println("Player " + player2.getPlayerName() + " disconnected");
                            }
                            serverMatchList.remove(match);
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Error in server: " + e.getLocalizedMessage());
                }
            }
        }));
    }

    @Override
    public void run() {
        while (isWorking) {
            String cmd = scanner.nextLine();
            if (cmd.equalsIgnoreCase("close")) {
                isWorking = false;
            }
        }
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        isWorking = false;
    }

    private final class PlayerThread implements Runnable {
        private final ConnectedPlayer connectedPlayer;

        public PlayerThread(ConnectedPlayer connectedPlayer) {
            this.connectedPlayer = connectedPlayer;
        }

        @Override
        public void run() {
            while (!connectedPlayer.foundOpponent()) {
                try {
                    Message message = (Message) connectedPlayer.readData();
                    if (message == null) continue;
                    if (message.getType() == MessageType.CANCEL_WAITING) {
                        connectedPlayer.getSocket().close();
                        return;
                    }
                    if (message.getType() == MessageType.PLAYER_FOUND) {
                        return;
                    }
                }
                catch (Exception e) {
                    System.out.println("Error in server match: " + e.getLocalizedMessage());
                }
            }
        }
    }
}
