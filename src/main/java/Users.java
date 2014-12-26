import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadvr on 12/10/14.
 */
public class Users {
    // online: public Map<Integer, Boolean>

    public void friendsMonitoring(int vkId, Mysql mysql, Parser parser) {
        ArrayList<Integer> dbFriends = mysql.getFriends(vkId);
        ArrayList<Integer> parserFriends = parser.getFriends(vkId);

        Map<Integer, Boolean> friendsChanges = compareFriends(dbFriends, parserFriends);

        ArrayList<Integer> addedFriends = new ArrayList<>();
        ArrayList<Integer> removedFriends = new ArrayList<>();

        for (Map.Entry<Integer, Boolean> entry : friendsChanges.entrySet()) {
            if (entry.getValue()) {
                addedFriends.add(entry.getKey());
            } else {
                removedFriends.add(entry.getKey());
            }
        }

        if (addedFriends.size() != 0)
            mysql.addFriends(vkId, addedFriends);

        if (removedFriends.size() != 0)
            mysql.removeFriends(vkId, removedFriends);
    }

    public Map<Integer, Boolean> compareFriends(ArrayList<Integer> dbFriends, ArrayList<Integer> parserFriends) {
        Map<Integer, Boolean> friendsChanges = new HashMap<>();
        if (!parserFriends.equals(dbFriends)) {
            for (int a : parserFriends)
                if (!dbFriends.contains(a))
                    //System.out.println("Новый друг " + a);
                    friendsChanges.put(a, true);
            for (int a : dbFriends)
                if (!parserFriends.contains(a))
                    //System.out.println("Удален друг " + a);
                    friendsChanges.put(a, false);
        }
        return friendsChanges;
    }
}
