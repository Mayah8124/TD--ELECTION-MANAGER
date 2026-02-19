package org.ceni.model;

import java.util.Objects;

public class Voter {
    private Integer id;
    private String name;

    public Voter(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Voter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(id, voter.id) && Objects.equals(name, voter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Voter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
