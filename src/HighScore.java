import java.io.*;
import java.util.LinkedList;

public class HighScore implements Serializable {
    private String nick;
    private int score;
    private static LinkedList<HighScore> highScores = new LinkedList<>();

    public HighScore(String nick, int score){
        this.nick = nick;
        this.score = score;

//        for (int i = 0; i < highScores.size(); i++) {
//            if(highScores.get(i).score < score){
//                highScores.add(i,this);
//                save();
//                return;
//            }
//        }
//        highScores.add(this);
//        save();
        int index = 0;
        for ( HighScore s : highScores ) {
            if(s.score > score) index++;
            else break;
        }
        highScores.add(index, this);
        save();
    }

    @Override
    public String toString() {
        return "Player - " +nick + "'s score - "+score;
    }
    public static void save(){
        try{
            FileOutputStream save1 = new FileOutputStream("file1.txt");
            ObjectOutputStream save2 = new ObjectOutputStream(save1);
            save2.writeObject(new DataScores());
            save2.flush();
            save2.close();
        }catch (IOException e){
            System.out.println("*could not save a file*");
        }
    }
    public static void load(){
        DataScores dataScore = null;
        try{
            FileInputStream load1 = new FileInputStream("file1.txt");
            ObjectInputStream load2 = new ObjectInputStream(load1);
            dataScore =(DataScores) load2.readObject();
            HighScore.highScores = dataScore.highScores;

            load2.close();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("*could not load a file*");
        }
    }

    public static LinkedList<HighScore> getHighScores() {
        return highScores;
    }
}
class DataScores implements Serializable{
    LinkedList<HighScore> highScores = new LinkedList<>();
    public DataScores(){
        this.highScores = HighScore.getHighScores();
    }
}

