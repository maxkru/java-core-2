package Lesson_6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class ClientHandler {
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    MainServ serv;
    String nick;

    public ClientHandler(MainServ serv, Socket socket){
        try {
            this.socket = socket;
            this.serv = serv;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String msg = in.readUTF();
                            if (msg.startsWith("/auth")) {
                                String[] tokens = msg.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick == null) {
                                    sendMsg("Неверный логин/пароль");
                                } else if (!serv.isNickUsed(newNick)){
                                    sendMsg("/authok");
                                    nick = newNick;
                                    serv.subscribe(ClientHandler.this);
                                    break;
                                } else {
                                    sendMsg("\'" + newNick + "\' уже авторизован");
                                }
                            }
                        }


                        while (true) {
                            String msg = in.readUTF();
                            if (msg.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                break;
                            }
                            if (msg.startsWith("/w ")) {
                                String[] msgSplit = msg.split(" ", 3);
                                if ( msgSplit.length == 3 && !msgSplit[2].isEmpty() ) {
                                    serv.privateMsg(ClientHandler.this, msgSplit[1], msgSplit[2]);
                                } else {
                                    ClientHandler.this.sendMsg("Синтаксис команды /w: \'/w ник сообщение\'");
                                }
                            } else {
                                serv.broadcastMsg(nick + ": " + msg);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serv.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
