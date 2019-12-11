package ru.geekbrains.chat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DatabaseHandler {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/chat_users?" +
                    "user=chat-server&password=XgVbEF4vTzP!R&serverTimezone=Europe/Moscow");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String password, String nick) throws BadCredentialException {
        if (!Pattern.matches("^[\\d\\p{Lower}\\p{Upper}]{3,20}$", login))
            throw new BadCredentialException("login", "Логин должен состоять из латинских букв и цифр и быть от 3 до 20 символов длиной");
        if (!Pattern.matches("^[\\d\\p{Lower}\\p{Upper}]{5,32}$", password))
            throw new BadCredentialException("password", "Пароль должен состоять из латинских букв и цифр и быть от 5 до 32 символов длиной");
        if (!Pattern.matches("^[\\d\\p{Lower}\\p{Upper}]{3,20}$", nick))
            throw new BadCredentialException("password", "Никнейм должен состоять из латинских букв и цифр и быть от 3 до 20 символов длиной");

        try {
            String queryCheckIfOccupied = "SELECT login, nickname FROM users WHERE login = ? OR nickname = ?;";
            PreparedStatement ps = connection.prepareStatement(queryCheckIfOccupied);
            ps.setString(1, login);
            ps.setString(2, nick);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                String query = "INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);";
                ps = connection.prepareStatement(query);
                ps.setString(1, login);
                ps.setString(2, password);
                ps.setString(3, nick);
                ps.executeUpdate();
            } else {
                if(login.equals(rs.getString("login")))
                    throw new BadCredentialException("login", "Этот логин уже используется");
                else
                    throw new BadCredentialException("nickname", "Этот никнейм уже используется");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname FROM users WHERE login = '" + login + "' AND password = '" + pass + "';");
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean toggleNickInClientsBlacklistInDatabase(ClientHandler client, String blockedNick) throws NoSuchUserInDBException {
        /* возвращает, блокируется ли теперь пользователь */
        boolean result = false;
        try {
            ResultSet rs1 = stmt.executeQuery("SELECT id FROM users WHERE nickname = \'" + blockedNick + "\';");
            if (rs1.next()) {
                // такой ник есть
                int blocked_id = rs1.getInt(1);
                ResultSet rs2 = stmt.executeQuery("SELECT id FROM users WHERE nickname = \'" + client.getNick() + "\';");
                rs2.next();
                int user_id = rs2.getInt(1);
                ResultSet rs3 = stmt.executeQuery("SELECT * FROM blacklist WHERE (user_id = " + user_id + ") AND (blocked_id = " + blocked_id + ");");
                if (rs3.next()) {
                    // пользователь уже заблокирован, разблокируем
                    String query = "DELETE FROM blacklist WHERE (user_id = ?) AND (blocked_id = ?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, user_id);
                    ps.setInt(2, blocked_id);
                    ps.executeUpdate();
                    System.out.println("BL: " + client.getNick() + " добавил " + blockedNick);
                    result = false;
                } else {
                    // пользователь не заблокирован, блокируем
                    String query = "INSERT INTO blacklist (user_id, blocked_id) VALUES (?, ?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, user_id);
                    ps.setInt(2, blocked_id);
                    ps.executeUpdate();
                    System.out.println("BL: " + client.getNick() + " удалил " + blockedNick);
                    result = true;
                }
            } else {
                // такого ника нет
                throw new NoSuchUserInDBException("Не найден запрашиваемый ник");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> fetchBlacklistForNick(String nick) throws NoSuchUserInDBException {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet rs1 = stmt.executeQuery("SELECT id FROM users WHERE nickname = \'" + nick + "\';");
            if (rs1.next()) {
                int user_id = rs1.getInt(1);
                ResultSet rs2 = stmt.executeQuery("SELECT nickname FROM blacklist " +
                        "INNER JOIN users ON blacklist.blocked_id = users.id " +
                        "WHERE blacklist.user_id = " + user_id);
                while (rs2.next()) {
                    list.add(rs2.getString("nickname"));
                }
            } else {
                throw new NoSuchUserInDBException("No such nickname in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}