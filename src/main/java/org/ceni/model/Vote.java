package org.ceni.model;

import java.util.Objects;

public class Vote {
    private Integer id;
    private Candidate candidate;
    private Voter voter;
    private VoteType voterType;

    public Vote() {
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public VoteType getVoterType() {
        return voterType;
    }

    public void setVoterType(VoteType voterType) {
        this.voterType = voterType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) && Objects.equals(candidate, vote.candidate) && Objects.equals(voter, vote.voter) && voterType == vote.voterType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidate, voter, voterType);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", candidate=" + candidate +
                ", voter=" + voter +
                ", voterType=" + voterType +
                '}';
    }
}
