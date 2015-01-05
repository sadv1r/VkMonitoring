import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Mysql {
    private static final Logger LOGGER = Logger.getLogger(Mysql.class);
    private Connection connection = null;
    private static final String CHARSET = "UTF-8";

    public Mysql(String serverName, String portNumber, String userName, String password, String database) {
        LOGGER.debug("Конструктор запущен");

        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + database + "?characterEncoding=" + CHARSET, connectionProps);
            LOGGER.trace("Подключение: " + connection);
            LOGGER.info("Подключение получено");
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }
        LOGGER.debug("Конструктор завершил работу");
    }

    public ArrayList<Integer> getFriends(int targetId) {
        LOGGER.debug("Метод getFriends запущен");

        ArrayList<Integer> friends = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT friend_id FROM friends WHERE target_id = " + targetId);
            LOGGER.trace("Запрос \"SELECT friend_id FROM friends WHERE target_id = " + targetId + "\" прошел успешно");

            while (resultSet.next()) {
                friends.add(resultSet.getInt(1));
            }
            LOGGER.info("Получено " + friends.size() + " друзей");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }

        LOGGER.debug("Метод getFriends завершил работу");
        return friends;
    }

    public void addFriends(int targetId, ArrayList<Integer> friends) {
        LOGGER.debug("Метод addFriends запущен");

        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO friends (target_id, friend_id) VALUES (?, ?)");
            LOGGER.trace("PreparedStatement \"INSERT INTO friends (target_id, friend_id) VALUES (?, ?)\" готов к использованию");
            pst.setInt(1, targetId);
            PreparedStatement pstFriendsChanges = connection.prepareStatement("INSERT INTO friendsChanges (target_id, friend_id, operation) VALUES (?, ?, true)");
            LOGGER.trace("PreparedStatement \"INSERT INTO friendsChanges (target_id, friend_id, operation) VALUES (?, ?, true)\" готов к использованию");
            pstFriendsChanges.setInt(1, targetId);

            for (int friendId : friends) {
                pst.setInt(2, friendId);
                pstFriendsChanges.setInt(2, friendId);

                pst.executeUpdate();
                pstFriendsChanges.executeUpdate();
            }
            LOGGER.info("Новые друзья добавлены в базу");

            pst.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }

        LOGGER.debug("Метод addFriends завершил работу");
    }

    public void removeFriends(int targetId, ArrayList<Integer> friends) {
        LOGGER.debug("Метод removeFriends запущен");

        try {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM friends WHERE target_id = (?) AND friend_id = (?)");
            LOGGER.trace("PreparedStatement \"DELETE FROM friends WHERE target_id = (?) AND friend_id = (?)\" готов к использованию");
            pst.setInt(1, targetId);
            PreparedStatement pstFriendsChanges = connection.prepareStatement("INSERT INTO friendsChanges (target_id, friend_id, operation) VALUES (?, ?, false)");
            LOGGER.trace("PreparedStatement \"INSERT INTO friendsChanges (target_id, friend_id, operation) VALUES (?, ?, false)\" готов к использованию");
            pstFriendsChanges.setInt(1, targetId);

            for (int friendId : friends) {
                pst.setInt(2, friendId);
                pstFriendsChanges.setInt(2, friendId);

                pst.executeUpdate();
                pstFriendsChanges.executeUpdate();
            }
            LOGGER.info("Удаленные друзья удалены из базы");

            pst.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }

        LOGGER.debug("Метод removeFriends завершил работу");
    }

    public Map<Integer, int[]> getUsersOnline() {
        LOGGER.debug("Метод getUsersOnline запущен");

        Map<Integer, int[]> usersOnline = new HashMap<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_id, online, time, platform FROM users");
            LOGGER.trace("Запрос \"SELECT user_id, online, time, platform FROM users\" прошел успешно");

            while (resultSet.next()) {
                usersOnline.put(resultSet.getInt(1), new int[] {resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)});
            }
            LOGGER.info("Получена информация о " + usersOnline.size() + " пользователях");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }

        LOGGER.debug("Метод getUsersOnline завершил работу");
        return usersOnline;
    }
}
