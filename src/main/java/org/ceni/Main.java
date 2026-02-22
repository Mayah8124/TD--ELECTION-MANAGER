package org.ceni;


import org.ceni.model.CandidateVoteCount;
import org.ceni.model.VoteSummary;
import org.ceni.model.VoteTypeCount;
import org.ceni.repository.DataRetriever;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

          long totalVotes = dataRetriever.countAllVotes();
//        System.out.println("Total votes: " + totalVotes);

//        List<VoteTypeCount> results = dataRetriever.countVotesByType();
//        for (VoteTypeCount v : results) {
//            System.out.println(v.getVoteType() + " | " + v.getCount());
//        }
//        System.out.println(results);

        List<CandidateVoteCount> results = dataRetriever.countValidVotesByCandidate();
//        for (CandidateVoteCount candidateVoteCount : results) {
//            System.out.println("Candidat: " + candidateVoteCount.getCandidateName().getName()
//                    + " | valid vote: " + candidateVoteCount.getValidVoteCount().getCount());
//        }
//        StringBuilder sb = new StringBuilder("[");
//        for (int i = 0; i < results.size(); i++) {
//            CandidateVoteCount candidateVoteCount = results.get(i);
//            sb.append(candidateVoteCount.getCandidateName().getName())
//                    .append(" = ")
//                    .append(candidateVoteCount.getValidVoteCount().getCount());
//            if (i != results.size() - 1) {
//                sb.append(", ");
//            }
//        }
//        sb.append("]");
//        System.out.println(sb);

        VoteSummary voteSummary = dataRetriever.computeVoteSummary();
//        System.out.println(voteSummary);

        Double computeTurnoutRate = dataRetriever.computeTurnoutRate();
        System.out.println("Taux de participation = " + computeTurnoutRate + "%");
    }
}