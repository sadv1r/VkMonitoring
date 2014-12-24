import java.util.ArrayList;

/**
 * Created by sadvr on 12/10/14.
 */
public class Users {
    // online: public Map<Integer, Boolean>

    public void compareFriends(ArrayList<Integer> dbFriends, ArrayList<Integer> parserFriends) {
        if (!parserFriends.equals(dbFriends)) {
            for (int a : parserFriends)
                if (!dbFriends.contains(a))
                    System.out.println("Новый друг " + a);
            for (int a : dbFriends)
                if (!parserFriends.contains(a))
                    System.out.println("Удален друг " + a);
        }
    }
}
