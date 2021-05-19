package ru.nsu.spirin.chess.game;

import ru.nsu.spirin.chess.model.match.server.ServerMatch;
import ru.nsu.spirin.chess.model.match.server.ConnectedPlayer;
import ru.nsu.spirin.chess.utils.ThreadPool;

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

        ThreadPool.submitThread(new Thread(() -> {
            while (isWorking) {
                try {
                    Socket socket = serverSocket.accept();
                    ConnectedPlayer connectedPlayer = new ConnectedPlayer(socket);
                    String playerName = connectedPlayer.getPlayerName();
                    connectedPlayerList.add(connectedPlayer);
                    System.out.printf("Player '%s' connected\n", playerName);
                }
                catch (Exception e) {
                    System.out.println("Error in server: " + e.getLocalizedMessage());
                }
            }
        }));

        ThreadPool.submitThread(new Thread(() -> {
            while (isWorking) {
                try {
                    if (connectedPlayerList.size() >= 2) {
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
}
