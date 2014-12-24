import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadvr on 12/10/14.
 */
public class Users {
    // online: public Map<Integer, Boolean>

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
