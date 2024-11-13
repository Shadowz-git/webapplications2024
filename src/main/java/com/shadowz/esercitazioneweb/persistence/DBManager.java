package com.shadowz.esercitazioneweb.persistence;

import com.shadowz.esercitazioneweb.persistence.dao.ElementoDao;
import com.shadowz.esercitazioneweb.persistence.dao.TierListDao;
import com.shadowz.esercitazioneweb.persistence.implijdbc.ElementoDaoJDBC;
import com.shadowz.esercitazioneweb.persistence.implijdbc.TierListDaoJDBC;

import java.sql.*;

public class DBManager {
    private static DBManager instance = null;

    private DBManager() {}
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    Connection connection = null;

    private ElementoDao elementoDao = null;
    private TierListDao tierListDao = null;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql:/localhost:5432/TierList", "postgres",
                        "postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
    public ElementoDao getElementoDao() {
        if (elementoDao == null) {
            elementoDao = new ElementoDaoJDBC(getConnection());
        }
        return elementoDao;
    }

    public TierListDao getTierListDao() {
        if (tierListDao == null) {
            tierListDao = new TierListDaoJDBC(getConnection());
        }
        return tierListDao;
    }

    public static void main(String[] args) {
        Connection con = DBManager.getInstance().getConnection();

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM elementi");

            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
