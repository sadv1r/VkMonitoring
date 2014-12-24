import java.sql.*;
import java.util.ArrayList;
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
            LOGGER.trace("PreparedStatement готов к использованию");
            pst.setInt(1, targetId);

            for (int friendId : friends) {
                pst.setInt(2, friendId);

                pst.executeUpdate();
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
            LOGGER.trace("PreparedStatement готов к использованию");
            pst.setInt(1, targetId);

            for (int friendId : friends) {
                pst.setInt(2, friendId);

                pst.executeUpdate();
            }
            LOGGER.info("Удаленные друзья удалены из базы");

            pst.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            //e.printStackTrace();
        }

        LOGGER.debug("Метод removeFriends завершил работу");
    }

}
