package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public abstract class DataAcessObject<K, O> {
    private String table;
    private List<String> columns;
    private DataClass<K> token;

    private DataAcessObject() {
    }

    public DataAcessObject(final O token, final String table, final List<String> columns) {
        this.token = (DataClass<K>) token;
        this.table = table;
        this.columns = columns;
    }

    private Map<String, StatementCall<K>> sets = Map.ofEntries(
            entry("java.lang.String", (pst, id, value) -> pst.setString(id, (String) value)),
            entry("java.lang.Integer", (pst, id, value) -> pst.setInt(id, (Integer) value)),
            entry("java.lang.Double", (pst, id, value) -> pst.setDouble(id, (Double) value)));

    private void setValue(final PreparedStatement pst, final int id, final Object value) throws SQLException {
        this.sets.get(value.getClass().getName()).setParameter(pst, id, (K) value);
    }

    /**
     * @return
     */
    public int size() {
        Connection connection = DataBase.getConnection();
        int r = 0;

        try {
            PreparedStatement pst = connection
                    .prepareStatement("SELECT " + this.columns.get(0) + " FROM " + this.table);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
                r++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        Connection connection = DataBase.getConnection();
        boolean r = true;

        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM " + this.table);
            ResultSet rs = pst.executeQuery();
            r = !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @param key
     * @return
     */
    public boolean containsKey(final Object key) {
        Connection connection = DataBase.getConnection();
        boolean r = false;

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT " + this.columns.get(0) + " FROM " + this.table + " WHERE " + this.columns.get(0) + " = ?");
            this.setValue(pst, 1, key);
            ResultSet rs = pst.executeQuery();
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @param key
     * @return
     */
    public O get(final Object key) {
        Connection connection = DataBase.getConnection();
        O o = null;

        try {
            PreparedStatement pst = connection
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE " + this.columns.get(0) + " = ?");
            this.setValue(pst, 1, key);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int length = rs.getMetaData().getColumnCount();
                List<String> row = new ArrayList<>(length);

                for (int i = 1; i <= length; i++)
                    row.add(rs.getString(i));

                o = (O) token.fromRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return o;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public O put(final Object key, final Object value) {
        Connection connection = DataBase.getConnection();
        try {
            O result = this.get(key);
            PreparedStatement pst = connection
                    .prepareStatement("DELETE FROM " + this.table + " WHERE " + this.columns.get(0) + " = ?");
            this.setValue(pst, 1, key);
            pst.executeUpdate();

            String stm = "INSERT INTO " + this.table + " VALUES (";

            int i;
            DataClass<K> dataClass = (DataClass<K>) value;
            List<String> row = dataClass.toRow();
            for (i = 0; i < row.size() - 1; i++) {
                stm += " '" + row.get(i) + "', ";
            }
            stm += "'" + row.get(i) + "' ) ";

            pst = connection.prepareStatement(stm);
            pst.executeUpdate();
            connection.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public O remove(final Object key) {
        Connection connection = DataBase.getConnection();
        O o = null;

        try {
            o = this.get(key);
            if (o != null) {
                PreparedStatement pst = connection
                        .prepareStatement("DELETE FROM " + this.table + " WHERE " + this.columns.get(0) + " = ?");
                this.setValue(pst, 1, key);
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

        return o;
    }

    /**
     *
     */
    public void clear() {
        Connection connection = DataBase.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("TRUNCATE TABLE " + this.table);
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
