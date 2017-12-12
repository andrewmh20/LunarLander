package game;

public class HighScore implements Comparable<HighScore> {

    private String player;
    private float score;

    public HighScore(String player, float score) {
        this.player = player;
        this.score = score;
    }

    public float getScore() {
        return score;
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public int compareTo(HighScore hs) {
        return Math.round(score - hs.getScore());
    }

}
