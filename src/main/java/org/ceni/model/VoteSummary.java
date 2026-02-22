package org.ceni.model;

import java.util.Objects;

public class VoteSummary {
    private Integer validCount;
    private Integer blankCount;
    private Integer nullCount;

    public VoteSummary() {
    }

    public VoteSummary(Integer validCount, Integer blankCount, Integer nullCount) {
        this.validCount = validCount;
        this.blankCount = blankCount;
        this.nullCount = nullCount;
    }

    public Integer getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(Integer blankCount) {
        this.blankCount = blankCount;
    }

    public Integer getValidCount() {
        return validCount;
    }

    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
    }

    public Integer getNullCount() {
        return nullCount;
    }

    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteSummary that = (VoteSummary) o;
        return Objects.equals(validCount, that.validCount) && Objects.equals(blankCount, that.blankCount) && Objects.equals(nullCount, that.nullCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validCount, blankCount, nullCount);
    }

    @Override
    public String toString() {
        return "VoteSummary{" +
                "validCount=" + validCount +
                ", blankCount=" + blankCount +
                ", nullCount=" + nullCount +
                '}';
    }
}

