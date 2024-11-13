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
    Connection connection;

    public ElementoDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Elemento> findAll() {
        List<Elemento> elementi = new ArrayList<Elemento>();
        String query = "SELECT * FROM elementi";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Elemento elemento = new Elemento();

                elemento.setNome(resultSet.getString("nome"));
                elemento.setGrade(resultSet.getString("grade"));
                elemento.setSource(resultSet.getString("source"));

                TierListDao tierListDao = DBManager.getInstance().getTierListDao();
                String key = resultSet.getString("tierlist");

                TierList tierList = tierListDao.findById(key);
                elemento.setTierList(tierList);
                elementi.add(elemento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elementi;
    }

    @Override
    public Elemento findById(String id) {
        return null;
    }

    @Override
    public void save(Elemento elemento) {

    }

    @Override
    public void delete(Elemento elemento) {

    }

    @Override
    public List<Elemento> findAllByTierList(String id) {

        List<Elemento> elementi = new ArrayList<>();
        String query = "SELECT * FROM elementi WHERE tierlist = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Elemento elemento = new Elemento();
                elemento.setNome(resultSet.getString("nome"));
                elemento.setGrade(resultSet.getString("grade"));
                elemento.setSource(resultSet.getString("source"));
                elementi.add(elemento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementi;
    }
}
