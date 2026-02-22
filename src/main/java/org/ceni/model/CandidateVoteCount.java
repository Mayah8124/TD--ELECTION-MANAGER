package org.ceni.model;

import java.util.Objects;

public class CandidateVoteCount {
    private Candidate candidateName;
    private VoteTypeCount validVoteCount;

    public CandidateVoteCount() {
    }

    public CandidateVoteCount(Candidate candidateName, VoteTypeCount validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public Candidate getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(Candidate candidateName) {
        this.candidateName = candidateName;
    }

    public VoteTypeCount getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(VoteTypeCount validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CandidateVoteCount that = (CandidateVoteCount) o;
        return Objects.equals(candidateName, that.candidateName) && Objects.equals(validVoteCount, that.validVoteCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateName, validVoteCount);
    }

    @Override
    public String toString() {
        return "CandidateVoteCount{" +
                "candidateName=" + candidateName +
                ", validVoteCount=" + validVoteCount +
                '}';
    }
}
