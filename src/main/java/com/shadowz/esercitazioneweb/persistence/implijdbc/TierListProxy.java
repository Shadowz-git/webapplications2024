package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.Elemento;
import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.DBManager;

import java.util.List;

public class TierListProxy extends TierList {

    public List<Elemento> getElements() {
        if (this.elementi == null) {
            this.elementi = DBManager.getInstance().getElementoDao().findAllByTierList(this.id);
        }
        return this.elementi;
    }
}
