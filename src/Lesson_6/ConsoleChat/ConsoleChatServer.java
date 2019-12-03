package Lesson_6.ConsoleChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ConsoleChatServer {
    private static final int PORT_NUMBER = 8190;


    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream inStream;
        DataOutputStream outStream;

        try {
            server = new ServerSocket(PORT_NUMBER);
            System.out.println("Сервер запущен, порт " + PORT_NUMBER + '.');
            System.out.println("Ожидаем клиента...");
            socket = server.accept();
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключился.");

            Socket finalSocket = socket;
            Thread readFromClientThread = new Thread(() -> {
                String inMsg;
                try {
                    while (!(inMsg = inStream.readUTF()).toLowerCase().equals("/end")) {
                        System.out.println(inMsg);
                    }
                    outStream.writeUTF("/serverClosed");
                } catch (SocketException | EOFException e) {
                    System.out.println("Сокет клиента был закрыт.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readFromClientThread.start();

            String outMsg;
            Scanner scanner = new Scanner(System.in);
            while (!(outMsg = scanner.nextLine()).toLowerCase().equals("/end")) {
                try {
                    outStream.writeUTF(outMsg);
                } catch (SocketException e) {
                    System.out.println("Сокет клиента был закрыт.");
                }
            }

            outStream.writeUTF("/serverClosed");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сервер завершает работу...");
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


