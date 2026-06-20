package com.prescripto.utils;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    public void testConnectionNotNull() {
        try {
            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "La connexion ne devrait pas être nulle");
            conn.close();
        } catch (Exception e) {
            fail("Erreur lors de la récupération ou de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
