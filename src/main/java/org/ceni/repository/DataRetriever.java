package org.ceni.repository;

import org.ceni.*;
import org.ceni.db.DBConnection;
import org.ceni.model.Candidate;
import org.ceni.model.CandidateVoteCount;
import org.ceni.model.VoteType;
import org.ceni.model.VoteTypeCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    DBConnection dbConnection = new DBConnection();

    public long countAllVotes() {
        String sql = "select count(id) as total_votes from vote";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("total_votes");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VoteTypeCount> countVotesByType() {
        String sql = """
                        select vote_type, count(vote.id) as total_vote from vote
                        group by vote_type
                        order by vote_type asc                            
                """;

        List<VoteTypeCount> voteTypeCounts = new ArrayList<>();

        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {
            while (rs.next()) {
                String voteTypeStr = rs.getString("vote_type");

                VoteType voteType = voteTypeStr != null ? VoteType.valueOf(voteTypeStr) : VoteType.NULL;
                long count = rs.getLong("total_vote");

                voteTypeCounts.add(new VoteTypeCount(voteType, count));
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return voteTypeCounts;
    }

    public List<CandidateVoteCount> countValidVotesByCandidate() {
        String sql = """
                    select
                        c.name,
                        (
                            select count(v.id)
                            from vote v
                            where v.candidate_id = c.id
                              and v.vote_type = 'VALID'
                        ) as total_valid_votes
                    from candidate c
                    order by c.id;
                """;

        List<CandidateVoteCount> candidateVoteCounts = new ArrayList<>();

        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {
            while (rs.next()) {
                String name = rs.getString("name");

                Candidate candidate = new Candidate();
                candidate.setName(name);

                long totalValid = rs.getLong("total_valid_votes");

                VoteTypeCount validVoteCount = new VoteTypeCount();
                validVoteCount.setVoteType(VoteType.VALID);
                validVoteCount.setCount(totalValid);

                CandidateVoteCount candidateVoteCount = new CandidateVoteCount( candidate, validVoteCount);

                candidateVoteCounts.add(candidateVoteCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candidateVoteCounts;
    }
}
