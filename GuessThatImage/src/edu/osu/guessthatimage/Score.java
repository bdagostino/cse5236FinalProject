package edu.osu.guessthatimage;

/**
 * Put a short phrase describing the program here.
 * 
 * @author Put your name here
 * 
 */
public final class Score {

    private int score;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    public Score(int score) {
        this.score = score;
    }

    public void skipped() {
        this.score--;
    }

    public void correctAnswer() {
        this.score++;
        this.score++;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int s) {
        this.score = s;
    }

}
