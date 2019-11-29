package database;

import model.RegularUser;

import java.sql.*;

public class RegularUserDAO {

    public int size() {
        Connection connection = DataBase.getConnection();
        int r = 0;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT username FROM REGULAR_USERS");
            ResultSet rs = pst.executeQuery();
            for (; rs.next(); r++);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public boolean isEmpty() {
        Connection connection = DataBase.getConnection();
        boolean r = true;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM REGULAR_USERS");
            ResultSet rs = pst.executeQuery();
            r = !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public boolean containsKey(Object key) {
        Connection connection = DataBase.getConnection();
        boolean r = false;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT username FROM REGULAR_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            ResultSet rs = pst.executeQuery();
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public RegularUser get(Object key) {
        Connection connection = DataBase.getConnection();
        RegularUser ru = null;
        try {
            PreparedStatement pst = connection
                    .prepareStatement("SELECT email, name, password, salt FROM REGULAR_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ru = new RegularUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ru;
    }

    public void put(String key, Object value) {
        Connection connection = DataBase.getConnection();
        RegularUser ru = (RegularUser) value;
        try {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM REGULAR_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            pst.executeUpdate();
            pst = connection
                    .prepareStatement("INSERT INTO REGULAR_USERS (email, name, password, salt) VALUES (?, ?, ?, ?)");
            pst.setString(1, ru.getEmail());
            pst.setString(2, ru.getName());
            pst.setString(3, ru.getPassword());
            pst.setString(4, ru.getSalt());
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public RegularUser remove(Object key) {
        Connection connection = DataBase.getConnection();
        RegularUser ru = null;
        try {
            ru = this.get(key);
            if (ru != null) {
                PreparedStatement pst = connection.prepareStatement("DELETE FROM REGULAR_USERS WHERE email = ?");
                pst.setString(1, (String) key);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ru;
    }

    public void clear() {
        Connection connection = DataBase.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("TRUNCATE TABLE REGULAR_USERS");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
