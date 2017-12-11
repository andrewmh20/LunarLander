package game;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HighScorePanel extends JPanel {
    
    HighScoreHandler scoreHandler; 

    public HighScorePanel(HighScoreHandler scoreHandler) throws IOException {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        JLabel title = new JLabel ("High Scores--Maximum fuel left");
        add(new JLabel(""));
        
        scoreHandler.readFile();
        //System.out.println("here");
        
        for (int i = 0; i < 6; i++) {
            HighScore hs = scoreHandler.getOrderedPlayers().get(i);
            JLabel score = new JLabel(hs.getPlayer()+ ": " + hs.getScore());
            add(score);

        }



    
    }

    @Override
    public Dimension getPreferredSize() {
        // TODO Auto-generated method stub
        return new Dimension(300,300);
    }
    
    

}
