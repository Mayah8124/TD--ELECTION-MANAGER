package org.ceni;


import org.ceni.model.CandidateVoteCount;
import org.ceni.model.ElectionResult;
import org.ceni.model.VoteSummary;
import org.ceni.model.VoteTypeCount;
import org.ceni.repository.DataRetriever;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("========================================");

        long totalVotes = dataRetriever.countAllVotes();
        System.out.println("Total votes: " + totalVotes);

        System.out.println("========================================");

        List<VoteTypeCount> result = dataRetriever.countVotesByType();
        System.out.println(result);

        System.out.println("========================================");

        List<CandidateVoteCount> results = dataRetriever.countValidVotesByCandidate();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < results.size(); i++) {
            CandidateVoteCount candidateVoteCount = results.get(i);
            sb.append(candidateVoteCount.getCandidateName().getName())
                    .append(" = ")
                    .append(candidateVoteCount.getValidVoteCount().getCount());
            if (i != results.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb);

        System.out.println("========================================");

        VoteSummary voteSummary = dataRetriever.computeVoteSummary();
        System.out.println(voteSummary);

        System.out.println("========================================");

        Double computeTurnoutRate = dataRetriever.computeTurnoutRate();
        System.out.println("Taux de participation = " + computeTurnoutRate + "%");

        System.out.println("========================================");

        ElectionResult electionResult = dataRetriever.findWinner();
        System.out.println(electionResult);

        System.out.println("========================================");
    }
}