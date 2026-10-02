import java.sql.*;

public class Login {
    public void login(String username) throws Exception {
        String query = "SELECT * FROM users WHERE name='" + username + "'";
        Connection con = DriverManager.getConnection("db");
        Statement st = con.createStatement();
        st.executeQuery(query);
    }
}