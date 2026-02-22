package org.ceni;


import org.ceni.model.VoteTypeCount;
import org.ceni.repository.DataRetriever;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

//        long totalVotes = dataRetriever.countAllVotes();
//        System.out.println("Total votes: " + totalVotes);

        List<VoteTypeCount> results = dataRetriever.countVotesByType();
        for (VoteTypeCount v : results) {
            System.out.println(v.getVoteType() + " | " + v.getCount());
        }
        System.out.println(results);
    }
}