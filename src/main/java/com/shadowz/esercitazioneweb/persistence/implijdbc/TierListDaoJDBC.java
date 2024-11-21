package com.shadowz.esercitazioneweb.persistence.implijdbc;

import com.shadowz.esercitazioneweb.model.TierList;
import com.shadowz.esercitazioneweb.persistence.dao.TierListDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TierListDaoJDBC implements TierListDao {
    private final Connection connection;

    public TierListDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<TierList> findAll() {
        List<TierList> tierLists = new ArrayList<>();
        String query = "SELECT * FROM tier_list";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                TierList tierList = extractTierListFromResultSet(resultSet);
                tierLists.add(tierList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tierLists;
    }

    @Override
    public TierList findById(String id) {
        String query = "SELECT * FROM tier_list WHERE id = ?";
        TierList tierList = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tierList = extractTierListFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tierList;
    }

    @Override
    public void save(TierList tierList) {
        String query = "INSERT INTO tier_list (id, tier_name, tier_description, tier_grades) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tierList.getId());
            statement.setString(2, tierList.getTierName());
            statement.setString(3, tierList.getTierDescription());
            Array gradesArray = connection.createArrayOf("TEXT", tierList.getGrades().toArray());
            statement.setArray(4, gradesArray);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(TierList tierList) {
        String query = """
            UPDATE tier_list 
            SET tier_name = ?, 
                tier_description = ?, 
                tier_grades = ? 
            WHERE id = ?
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tierList.getTierName());
            statement.setString(2, tierList.getTierDescription());
            Array gradesArray = connection.createArrayOf("TEXT", tierList.getGrades().toArray());
            statement.setArray(3, gradesArray);
            statement.setString(4, tierList.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(TierList tierList) {
        String query = "DELETE FROM tier_list WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tierList.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo ausiliario per estrarre un oggetto TierList da un ResultSet.
     */
    private TierList extractTierListFromResultSet(ResultSet resultSet) throws SQLException {
        TierList tierList = new TierList();
        tierList.setId(resultSet.getString("id"));
        tierList.setTierName(resultSet.getString("tier_name"));
        tierList.setTierDescription(resultSet.getString("tier_description"));

        Array gradesArray = resultSet.getArray("tier_grades");
        if (gradesArray != null) {
            String[] grades = (String[]) gradesArray.getArray();
            tierList.setGrades(List.of(grades));
        }
        return tierList;
    }
}
