package Lesson_6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServ {
    private Vector<ClientHandler> clients;

    public MainServ() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }

    public void privateMsg(ClientHandler clientFrom, String nickTo, String msg) {
        if (nickTo.equals(clientFrom.nick)) {
            clientFrom.sendMsg("Server: \'" + nickTo + "\' is you.");
        } else {
            boolean result = false;
            for (ClientHandler candidate : clients) {
                if (candidate.nick.equals(nickTo)) {
                    candidate.sendMsg("(p) " + clientFrom.nick + ": " + msg);
                    clientFrom.sendMsg("(to " + nickTo + "): " + msg);
                    result = true;
                    break;
                }
            }

            if (!result) {
                clientFrom.sendMsg("Server: no such user \'" + nickTo + "\' online.");
            }
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
}
