package org.ceni;


import org.ceni.repository.DataRetriever;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        long totalVotes = dataRetriever.countAllVotes();
        System.out.println("Total votes: " + totalVotes);

    }
}