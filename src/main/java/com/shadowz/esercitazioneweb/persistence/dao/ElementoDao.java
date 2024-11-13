package com.shadowz.esercitazioneweb.persistence.dao;

import com.shadowz.esercitazioneweb.model.Elemento;

import java.util.List;

public interface ElementoDao {
    public List<Elemento> findAll();
    public List<Elemento> findAllByTierList(String id);
    public Elemento findById(String id);
    public void save(Elemento elemento);
    public void delete(Elemento elemento);
}
