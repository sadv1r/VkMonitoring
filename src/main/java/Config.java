import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Sadv1r
 * Date: 8/21/14
 * Time: 21:06
 */
public class Config {
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/config.properties";
    private String serverAddress;
    private int portNumber;
    private String databaseName;

    public String getServerAddress() {
        return serverAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    /*public Config() {
        Properties properties = load();

        if (properties.getProperty("debug").equals("on")) {
            Debug.turnOn();
            Debug.print("Режим отладки активирован...\n\nКонфигурация:");
        }

        if (properties.getProperty("serverAddress").isEmpty()) {
            Debug.print("Адрес сервера отсутствует в конфигурационном файле!");
        } else {
            serverAddress = properties.getProperty("serverAddress");
            Debug.print("Адрес сервера найден: " + serverAddress);
        }

        if (properties.getProperty("portNumber").isEmpty()) {
            Debug.print("Порт отсутствует в конфигурационном файле!");
        } else {
            portNumber = Integer.parseInt(properties.getProperty("portNumber"));
            Debug.print("Порт найден: " + portNumber);
        }

        if (properties.getProperty("databaseName").isEmpty()) {
            Debug.print("Название базы данных отсутствует в конфигурационном файле!");
        } else {
            databaseName = properties.getProperty("databaseName");
            Debug.print("Название базы данных найдено: " + databaseName);
        }

        Debug.print("Конфигурация прошла успешно.\n");
    }


    private Properties load() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }*/

}
