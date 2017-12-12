package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HighScoreHandler {

    private PrintWriter out;
    private BufferedReader in;
    private List<HighScore> highScores;

    HighScoreHandler(String filename)
            throws FileNotFoundException, IOException {

        File file = new File(filename);
        out = new PrintWriter(new FileWriter(file, true));
        in = new BufferedReader(new FileReader(file));
        highScores = new LinkedList<HighScore>();

    }

    public void writeScore(HighScore highscore) {
        if (highscore.getPlayer().contains(";")) {
            throw new IllegalArgumentException("Illegal seperator character!");
        }

        else {
            out.println(highscore.getPlayer() + ";"
                    + String.format("%1$.2f", highscore.getScore()));
            out.flush();
        }

    }

    public void readFile() throws IOException {
        String lineIn = in.readLine();
        while (lineIn != null) {
            if (!lineIn.contains(";")) {
                throw new IOException("Corrupted File--not enough seperators");
            }
            char[] temp = new char[lineIn.length()];
            String player = "";
            String score = "";
            boolean seperated = false;
            lineIn.getChars(0, lineIn.length(), temp, 0);
            for (int i = 0; i < temp.length; i++) {
                if (seperated == false && temp[i] != ';') {
                    player += temp[i];
                } else if (temp[i] == ';') {
                    if (seperated == true) {
                        throw new IOException(
                                "Corrupted File--too many seperators");
                    } else {
                        seperated = true;
                    }
                } else if (seperated == true) {
                    score += temp[i];

                }
            }
            try {
                highScores.add(new HighScore(player, Float.parseFloat(score)));
            } catch (NumberFormatException e) {
                throw new IOException("Corrupted File--bad score format");
            }
            lineIn = in.readLine();

        }

    }

    public LinkedList<HighScore> getOrderedPlayers() {
        // Return players in sorted order

        LinkedList<HighScore> sortedScores =
                new LinkedList<HighScore>(highScores);

        Collections.sort(sortedScores);
        Collections.reverse(sortedScores);

        return new LinkedList<HighScore>(sortedScores);

    }

    public static LinkedList<HighScore>
            getOrderedPlayers(List<HighScore> highScores) {
        // Return players in sorted order, linked to input list

        Collections.sort(highScores);
        Collections.reverse(highScores);

        return new LinkedList<HighScore>(highScores);

    }

}
