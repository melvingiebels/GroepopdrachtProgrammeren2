package Database;

import java.sql.*;

abstract class DatabaseConnection {
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
}
