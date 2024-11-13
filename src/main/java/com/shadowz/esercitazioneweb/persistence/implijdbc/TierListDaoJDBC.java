package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.dao.TierListDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TierListDaoJDBC implements TierListDao {
    Connection connection;

    public TierListDaoJDBC(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<TierList> findAll() {
        List<TierList> list = new ArrayList<TierList>();
        String query = "SELECT * FROM tier_list";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TierList tierList = new TierList();

                tierList.setTierName(resultSet.getString("tier_name"));
                tierList.setTierDescription(resultSet.getString("tier_description"));
                List<String> grades = (List<String>) resultSet.getArray("tier_grades");
                tierList.setGrades(grades);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public TierList findById(String id) {
        return null;
    }

    @Override
    public void save(TierList tierList) {

    }

    @Override
    public void update(TierList tierList) {

    }

    @Override
    public void delete(TierList tierList) {

    }
}
