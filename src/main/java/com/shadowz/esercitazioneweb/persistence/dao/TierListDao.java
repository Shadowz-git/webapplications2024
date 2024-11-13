package com.shadowz.esercitazioneweb.persistence.dao;

import com.shadowz.esercitazioneweb.model.TierList;

import java.util.List;

public interface TierListDao {
    public List<TierList> findAll();
    public TierList findById(String id);
    public void save(TierList tierList);
    public void update(TierList tierList);
    public void delete(TierList tierList);
}
