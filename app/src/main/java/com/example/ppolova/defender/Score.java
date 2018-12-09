package com.example.ppolova.defender;

public class Score {

    private int rank;
    private String name;
    private int score;

    public Score(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        if (rank < 10) {
            return rank + ".        " + name + ": " + score;
        } else {
            return rank + ".      " + name + ": " + score;
        }
    }
}
