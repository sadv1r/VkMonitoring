import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sadvr on 12/24/14.
 */
public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class);

    public ArrayList<Integer> getFriends(int vkId) {
        LOGGER.debug("Метод getFriends запущен");
        ArrayList<Integer> friends = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        String friendsString = getStringFromApi("https://api.vk.com/method/friends.get?v=5.24&user_id=" + vkId);
        try {
            JsonNode rootNode = mapper.readTree(friendsString).get("response").get("items");
            LOGGER.trace("\"items\" из \"response\" получены успешно");

            if (rootNode != null)
                for (JsonNode node : rootNode)
                    friends.add(node.asInt());
            LOGGER.info("Получено " + friends.size() + " друзей");

        } catch (IOException e) {
            LOGGER.error(e);
        }

        LOGGER.debug("Метод getFriends завершил работу");
        return friends;
    }

    private String getStringFromApi(String url) {
        LOGGER.debug("Метод getStringFromApi запущен");
        String str = "";
        try {
            Scanner scan = new Scanner(new URL(url).openStream());
            LOGGER.trace("Ответ на запрос \"" + url + "\" получен успешно");
            while (scan.hasNext())
                str += scan.nextLine();
            LOGGER.trace("Строка длиной " + str.length() + " символов сформирована");
            scan.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        LOGGER.debug("Метод getStringFromApi завершил работу");
        return str;
    }

}
