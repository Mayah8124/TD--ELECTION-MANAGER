package org.ceni.repository;

import org.ceni.*;
import org.ceni.db.DBConnection;
import org.ceni.model.*;

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

    public VoteSummary computeVoteSummary () {
        String sql = """
                    select count((case when v.vote_type = 'VALID' then 1 end)) as valid_count,
                            count ((case when v.vote_type = 'BLANK' then 1 end)) as blank_count,
                            count((case when v.vote_type = 'NULL' then 1 end)) as null_count
                    from vote v
                """;
        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {
            if (rs.next()) {
                VoteSummary voteSummary = new VoteSummary();
                voteSummary.setValidCount(rs.getInt("valid_count"));
                voteSummary.setBlankCount(rs.getInt("blank_count"));
                voteSummary.setNullCount(rs.getInt("null_count"));
                return voteSummary;
            }
            throw new RuntimeException("Unable to compute vote summary");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double computeTurnoutRate() {
        String sql = """
                    select 100 * count(distinct voter_id) / (select count(id) from voter) as participation_rate
                    from vote
        """;

        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {
            if (rs.next()) {
                return rs.getDouble("participation_rate");
            }
            throw new RuntimeException("Unable to compute turnout rate");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while computing turnout rate", e);
        }
    }

    public ElectionResult findWinner() {
        String sql = """
                select c.name as candidate_name,
                       count(v.id) as valid_vote_count
                from candidate c
                join vote v on v.candidate_id = c.id
                where v.vote_type = 'VALID'
                group by c.id, c.name
                order by valid_vote_count desc
                limit 1
                """;

        try (
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {
            if (rs.next()) {
                ElectionResult electionResult = new ElectionResult();
                electionResult.setCandidateName(rs.getString("candidate_name"));
                electionResult.setValidVoteCount(rs.getInt("valid_vote_count"));
                return electionResult;
            }
            throw new RuntimeException("Unable to find winner");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while computing winner", e);
        }
    }
}
