package database;

import model.AdminUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserDAO {

    public int size() {
        Connection connection = DataBase.getConnection();
        int r = 0;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT email FROM ADMIN_USERS");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
                r++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public boolean isEmpty() {
        Connection connection = DataBase.getConnection();
        boolean r = true;
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM ADMIN_USERS");
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
            PreparedStatement pst = connection.prepareStatement("SELECT email FROM ADMIN_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            ResultSet rs = pst.executeQuery();
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public AdminUser get(Object key) {
        Connection connection = DataBase.getConnection();
        AdminUser a = null;
        try {
            PreparedStatement pst = connection
                    .prepareStatement("SELECT email, name, password, salt FROM ADMIN_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                a = new AdminUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void put(String key, Object value) {
        Connection connection = DataBase.getConnection();
        AdminUser a = (AdminUser) value;
        try {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM ADMIN_USERS WHERE email = ?");
            pst.setString(1, (String) key);
            pst.executeUpdate();
            pst = connection
                    .prepareStatement("INSERT INTO ADMIN_USERS (email, name, password, salt) VALUES (?, ?, ?, ?)");
            pst.setString(1, a.getEmail());
            pst.setString(2, a.getName());
            pst.setString(3, a.getPassword());
            pst.setString(4, a.getSalt());
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

    public AdminUser remove(Object key) {
        Connection connection = DataBase.getConnection();
        AdminUser a = null;
        try {
            a = this.get(key);
            if (a != null) {
                PreparedStatement pst = connection.prepareStatement("DELETE FROM ADMIN_USERS WHERE email = ?");
                pst.setString(1, (String) key);
                pst.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return a;
    }

    public void clear() {
        Connection connection = DataBase.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("TRUNCATE TABLE ADMIN_USERS");
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
}
