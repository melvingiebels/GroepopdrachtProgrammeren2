package Database;

import java.sql.*;

abstract class GenericDAO {
    // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien
    // deze voor jou anders is.
    protected String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;";
    // Connection beheert informatie over de connectie met de database.
    protected Connection con = null;
    // Statement zorgt dat we een SQL query kunnen uitvoeren.
    protected Statement stmt = null;
    // ResultSet is de tabel die we van de database terugkrijgen.
    // We kunnen door de rows heen stappen en iedere kolom lezen.
    protected ResultSet rs = null;

    public GenericDAO() {
        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Maak de verbinding met de database.
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
        }
    }

    protected void excecuteQuery(String SQL) {
        System.out.println(SQL);
        try {
            stmt = con.createStatement();
            stmt.executeQuery(SQL);
        } catch (Exception e) {
        }
    }
}
