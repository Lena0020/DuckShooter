import javax.swing.*;


public class ListOfHighscoresModel extends AbstractListModel<HighScore> {

    @Override
    public int getSize() {
        return HighScore.getHighScores().size();
    }

    @Override
    public HighScore getElementAt(int index) {
        return HighScore.getHighScores().get(index);
    }

}

