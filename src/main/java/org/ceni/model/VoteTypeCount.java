package org.ceni.model;

import java.util.Objects;

public class VoteTypeCount {
    private VoteType voteType;
    private long count;

    public VoteTypeCount() {
    }

    public VoteTypeCount(VoteType voteType, long count) {
        this.voteType = voteType;
        this.count = count;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteTypeCount that = (VoteTypeCount) o;
        return count == that.count && voteType == that.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteType, count);
    }

    @Override
    public String toString() {
        return "VoteTypeCount{" +
                "voteType=" + voteType +
                ", count=" + count +
                '}';
    }
}
