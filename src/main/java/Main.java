import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by sadvr on 12/10/14.
 */
public class Main {
    public static void main(String[] args) {
        //Mysql mysql = new Mysql("188.166.49.139", "3306", "root", "", "");
        Mysql mysql = new Mysql("localhost", "3306", "root", "", "vk_spy");
        Parser parser = new Parser();

        Users users = new Users();

        while (true) {
            ArrayList<Integer> friendsOfMainAccount = parser.getFriends(Integer.parseInt(args[0]));
            for (int vkId : friendsOfMainAccount)
                users.friendsMonitoring(vkId, mysql, parser);

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //break;
            }
        }

        //for (Entry<Integer, int[]> l : parser.getOnline("1,2,5,6").entrySet())
        //    System.out.println(Arrays.toString(l.getValue()));

    }

}
