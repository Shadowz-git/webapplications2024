package com.shadowz.esercitazioneweb.model;

public class Elemento {
    private String id;
    private String nome;
    private String grade;
    private String source;
    private TierList tierList;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    private String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public TierList getTierList() {
        return tierList;
    }
    public void setTierList(TierList tierList) {
        this.tierList = tierList;
    }
}
