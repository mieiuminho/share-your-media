package database;

import model.MediaFile;

import java.sql.*;

public class MediaFileDAO {

    // TODO: estes métodos assumem a existência de uma tabela MEDIAFILES onde estão os mediafiles, pelo que estarão
    // comentados. Assim que a tabela estiver criada podemos "descomentar".

    /*
     * public int size() { Connection connection = DataBase.getConnection(); int r = 0; try { PreparedStatement pst =
     * connection.prepareStatement("SELECT name FROM MEDIAFILES"); ResultSet rs = pst.executeQuery(); for(; rs.next();
     * r++); } catch(SQLException e) { e.printStackTrace(); } return r; }
     */

    /*
     * public boolean isEmpty() { Connection connection = DataBase.getConnection(); boolean r = true; try {
     * PreparedStatement pst = connection.prepareStatement("SELECT * FROM MEDIAFILES"); ResultSet rs =
     * pst.executeQuery(); r = !rs.next(); } catch(SQLException e) { e.printStackTrace(); }
     * 
     * return r; }
     */

    /*
     * public boolean containsKey(Object key) { Connection connection = DataBase.getConnection(); boolean r = false; try
     * { PreparedStatement pst = connection.prepareStatement("SELECT username FROM MEDIAFILES WHERE name = ?");
     * pst.setString(1, (String) key); ResultSet rs = pst.executeQuery(); r = rs.next(); } catch(SQLException e) {
     * e.printStackTrace(); } return r; }
     */

    /*
     * public MediaFile get(Object key) { Connection connection = DataBase.getConnection(); MediaFile mf = null; try {
     * PreparedStatement pst =
     * connection.prepareStatement("SELECT name, artist, categories, uploaders FROM MEDIAFILES WHERE name = ?");
     * pst.setString(1, (String) key); ResultSet rs = pst.executeQuery(); if (rs.next()) { // TODO: temos de ver como é
     * que vamos fazer. Tem aqui listas de categories e uploaders mf = new MediaFile(rs.getString(1), rs.getString(2) ,
     * rs.getString(3), rs.getString(4)); } } catch (SQLException e) { e.printStackTrace(); } return mf; }
     */

    // not even gonna bother com o put (vai ter o mesmo problema que o anterior)

    /*
     * public MediaFile remove(Object key) { Connection connection = DataBase.getConnection(); MediaFile mf = null; try
     * { mf = this.get(key); if (mf != null) { PreparedStatement pst =
     * connection.prepareStatement("DELETE FROM MEDIAFILES WHERE name = ?"); pst.setString(1, (String) key);
     * pst.executeUpdate(); } } catch (SQLException e) { e.printStackTrace(); }
     * 
     * return mf; }
     */

    /*
     * public void clear() { Connection connection = DataBase.getConnection(); try { PreparedStatement pst =
     * connection.prepareStatement("TRUNCATE TABLE MEDIAFILES"); pst.executeUpdate(); } catch(SQLException e) {
     * e.printStackTrace(); } }
     */

}
