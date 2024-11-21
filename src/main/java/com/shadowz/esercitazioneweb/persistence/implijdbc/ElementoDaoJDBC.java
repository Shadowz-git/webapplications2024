package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.Elemento;
import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.DBManager;
import com.shadowz.esercitazioneweb.persistence.dao.ElementoDao;
import com.shadowz.esercitazioneweb.persistence.dao.TierListDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementoDaoJDBC implements ElementoDao {
    private final Connection connection;

    public ElementoDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Elemento> findAll() {
        List<Elemento> elementi = new ArrayList<>();
        String query = "SELECT * FROM elementi";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Elemento elemento = extractElementoFromResultSet(resultSet);
                elementi.add(elemento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementi;
    }

    @Override
    public List<Elemento> findAllByTierList(String id) {
        List<Elemento> elementi = new ArrayList<>();
        String query = """
            SELECT e.* FROM elementi e
            INNER JOIN elemento_tierlist et ON e.id = et.elemento_id
            WHERE et.tierlist_id = ?
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Elemento elemento = extractElementoFromResultSet(resultSet);
                    elementi.add(elemento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementi;
    }

    @Override
    public Elemento findById(String id) {
        String query = "SELECT * FROM elementi WHERE id = ?";
        Elemento elemento = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    elemento = extractElementoFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elemento;
    }

    @Override
    public void save(Elemento elemento) {
        String query = "INSERT INTO elementi (id, nome, grade, source) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, elemento.getId());
            statement.setString(2, elemento.getNome());
            statement.setString(3, elemento.getGrade());
            statement.setString(4, elemento.getSource());
            statement.executeUpdate();

            // Salvataggio della relazione con TierList, se presente
            if (elemento.getTierList() != null) {
                saveElementoTierListRelation(elemento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Elemento elemento) {
        String query = "DELETE FROM elementi WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, elemento.getId());
            statement.executeUpdate();

            // Eliminazione della relazione con TierList
            deleteElementoTierListRelation(elemento.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per estrarre un oggetto Elemento da un ResultSet.
     */
    private Elemento extractElementoFromResultSet(ResultSet resultSet) throws SQLException {
        Elemento elemento = new Elemento();
        elemento.setId(resultSet.getString("id"));
        elemento.setNome(resultSet.getString("nome"));
        elemento.setGrade(resultSet.getString("grade"));
        elemento.setSource(resultSet.getString("source"));

        // Recupera la TierList associata, se esiste
        String tierListIdQuery = """
            SELECT tierlist_id FROM elemento_tierlist
            WHERE elemento_id = ?
        """;
        try (PreparedStatement tierListStatement = connection.prepareStatement(tierListIdQuery)) {
            tierListStatement.setString(1, elemento.getId());
            try (ResultSet tierListResult = tierListStatement.executeQuery()) {
                if (tierListResult.next()) {
                    String tierListId = tierListResult.getString("tierlist_id");
                    TierListDao tierListDao = DBManager.getInstance().getTierListDao();
                    TierList tierList = tierListDao.findById(tierListId);
                    elemento.setTierList(tierList);
                }
            }
        }
        return elemento;
    }

    /**
     * Salva la relazione tra Elemento e TierList.
     */
    private void saveElementoTierListRelation(Elemento elemento) throws SQLException {
        String relationQuery = "INSERT INTO elemento_tierlist (elemento_id, tierlist_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(relationQuery)) {
            statement.setString(1, elemento.getId());
            statement.setString(2, elemento.getTierList().getId());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina la relazione tra Elemento e TierList.
     */
    private void deleteElementoTierListRelation(String elementoId) throws SQLException {
        String relationQuery = "DELETE FROM elemento_tierlist WHERE elemento_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(relationQuery)) {
            statement.setString(1, elementoId);
            statement.executeUpdate();
        }
    }
}
