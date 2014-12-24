import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by sadvr on 12/10/14.
 */
public class Main {
    public static void main(String[] args) {
        Mysql mysql = new Mysql("localhost", "3306", "root", "", "vk_spy");
        Parser parser = new Parser();

        ArrayList<Integer> dbFriends = mysql.getFriends(Integer.parseInt(args[0]));
        ArrayList<Integer> parserFriends = parser.getFriends(Integer.parseInt(args[0]));

        Users users = new Users();

        //while (true) {
            Map<Integer, Boolean> friendsChanges = users.compareFriends(dbFriends, parserFriends);
            ArrayList<Integer> addedFriends = new ArrayList<>();
            ArrayList<Integer> removedFriends = new ArrayList<>();

            for (Entry<Integer, Boolean> entry : friendsChanges.entrySet()) {
                if (entry.getValue()) {
                    addedFriends.add(entry.getKey());
                } else {
                    removedFriends.add(entry.getKey());
                }
            }

            if (addedFriends.size() != 0)
                mysql.addFriends(Integer.parseInt(args[0]), addedFriends);

            if (removedFriends.size() != 0)
                mysql.removeFriends(Integer.parseInt(args[0]), removedFriends);

            /*try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }*/
        //}

    }





}
