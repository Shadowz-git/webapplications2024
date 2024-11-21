package com.shadowz.esercitazioneweb.model;

import java.util.List;

public class TierList {
    protected String id;
    protected String tierName;
    protected String tierDescription;
    protected List<String> grades;
    protected List<Elemento> elementi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public String getTierDescription() {
        return tierDescription;
    }

    public void setTierDescription(String tierDescription) {
        this.tierDescription = tierDescription;
    }

    public List<String> getGrades() {
        return grades;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

    public List<Elemento> getElements() {
        return elementi;
    }

    public void setElements(List<Elemento> elementi) {
        this.elementi = elementi;
    }
}
