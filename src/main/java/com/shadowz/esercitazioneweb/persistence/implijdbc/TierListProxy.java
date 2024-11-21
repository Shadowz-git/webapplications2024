package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.Elemento;
import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.DBManager;

import java.util.List;

public class TierListProxy extends TierList {

    /**
     * Recupera gli elementi associati a questa TierList.
     * Gli elementi vengono caricati con un approccio lazy, accedendo al database
     * solo la prima volta che il metodo viene chiamato.
     *
     * @return la lista di elementi associati a questa TierList.
     */
    @Override
    public List<Elemento> getElements() {
        if (this.elementi == null) {
            this.elementi = DBManager.getInstance().getElementoDao().findAllByTierList(this.id);
        }
        return this.elementi;
    }

    /**
     * Imposta una lista di elementi per questa TierList.
     * Questo metodo permette di evitare il lazy loading se la lista è già disponibile.
     *
     * @param elementi la lista di elementi da associare a questa TierList.
     */
    @Override
    public void setElements(List<Elemento> elementi) {
        this.elementi = elementi;
    }

    /**
     * Forza il ricaricamento degli elementi dal database.
     * Questo metodo invalida la cache degli elementi e li recupera di nuovo.
     *
     * @return la lista aggiornata di elementi.
     */
    public List<Elemento> reloadElements() {
        this.elementi = DBManager.getInstance().getElementoDao().findAllByTierList(this.id);
        return this.elementi;
    }

    /**
     * Verifica se gli elementi sono già stati caricati.
     *
     * @return true se gli elementi sono già caricati, false altrimenti.
     */
    public boolean areElementsLoaded() {
        return this.elementi != null;
    }
}
