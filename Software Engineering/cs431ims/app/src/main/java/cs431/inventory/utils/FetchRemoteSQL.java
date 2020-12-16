package cs431.inventory.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.AsyncTask;


public class FetchRemoteSQL extends AsyncTask<Void,Void,String> {
    @Override
    protected String doInBackground(Void... params) {
        String retval = "";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            retval = e.toString();
        }
        String url = "jdbc:postgresql://10.0.2.2/dbname?user=username&password=pass";
        Connection conn;
        try {
            DriverManager.setLoginTimeout(5);
            conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
            String sql;
            sql = "SELECT 1";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                retval = rs.getString(1);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            retval = e.toString();
        }
        return retval;
    }
}
