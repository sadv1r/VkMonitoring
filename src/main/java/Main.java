import java.util.ArrayList;

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
            users.compareFriends(dbFriends, parserFriends);
            /*try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }*/
        //}

    }





}
