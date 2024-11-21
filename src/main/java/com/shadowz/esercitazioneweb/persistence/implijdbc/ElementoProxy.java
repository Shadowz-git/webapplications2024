package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.Elemento;
import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.DBManager;

public class ElementoProxy extends Elemento {

    /**
     * Recupera la TierList associata a questo Elemento.
     * Il caricamento della TierList viene effettuato in modo lazy,
     * accedendo al database solo la prima volta che viene richiesta.
     *
     * @return la TierList associata a questo Elemento.
     */
    @Override
    public TierList getTierList() {
        if (super.getTierList() == null && getId() != null) {
            TierList tierList = DBManager.getInstance().getTierListDao().findById(super.getTierList().getId());
            super.setTierList(tierList);
        }
        return super.getTierList();
    }

    /**
     * Imposta una TierList per questo Elemento.
     * Questo metodo permette di evitare il lazy loading se la TierList è già disponibile.
     *
     * @param tierList la TierList da associare a questo Elemento.
     */
    @Override
    public void setTierList(TierList tierList) {
        super.setTierList(tierList);
    }

    /**
     * Forza il ricaricamento della TierList dal database.
     * Questo metodo invalida la cache della TierList e la recupera di nuovo.
     *
     * @return la TierList aggiornata.
     */
    public TierList reloadTierList() {
        if (getId() != null) {
            TierList tierList = DBManager.getInstance().getTierListDao().findById(super.getTierList().getId());
            super.setTierList(tierList);
        }
        return super.getTierList();
    }

    /**
     * Verifica se la TierList è già stata caricata.
     *
     * @return true se la TierList è già caricata, false altrimenti.
     */
    public boolean isTierListLoaded() {
        return super.getTierList() != null;
    }
}
