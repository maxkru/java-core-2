package Lesson6.ConsoleChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ConsoleChatClient {
    public static void main(String[] args) {
        final String IP_ADDRESS = "localhost";
        final int PORT = 8190;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;

        try {
            System.out.println("Клиент запускается...");
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String srt = in.readUTF();
                            if (srt.equals("/serverClosed")){
                                break;
                            }
                            System.out.println(srt);
                        }
                    } catch (SocketException e) {
                        System.out.println("Сокет сервера был закрыт.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println("Подключились к серверу.");
            String outMsg;
            Scanner scanner = new Scanner(System.in);
            while (!(outMsg = scanner.nextLine()).toLowerCase().equals("/end") ) {
                out.writeUTF(outMsg);
            }

        } catch (ConnectException e) {
            System.out.println("Не удалось подключиться к серверу.");
        } catch (SocketException e) {
            System.out.println("Отключились от сервера.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Завершаем работу...");
            try {
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


