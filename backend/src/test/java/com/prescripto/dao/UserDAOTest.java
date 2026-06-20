package com.prescripto.dao;

import com.prescripto.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    @Test
    public void testLogin_InvalidCredentials() {
        UserDAO userDAO = new UserDAO();
        // On teste avec des identifiants qui n'existent probablement pas
        User user = userDAO.login("fake_email_12345@test.com", "wrong_password");
        
        // On s'attend à ce que le résultat soit null car l'utilisateur n'existe pas
        assertNull(user, "Le login devrait échouer (retourner null) avec de mauvais identifiants");
    }

    @Test
    public void testGetUserById_InvalidId() {
        UserDAO userDAO = new UserDAO();
        // On teste avec un ID impossible (ex: -1)
        User user = userDAO.getUserById(-1);
        
        // On s'attend à ce que le résultat soit null
        assertNull(user, "La recherche avec un ID négatif ou inexistant devrait retourner null");
    }

    @Test
    public void testRegister_NewUser() {
        UserDAO userDAO = new UserDAO();
        
        // Création d'un utilisateur fictif
        User testUser = new User();
        testUser.setName("Test User");
        // On utilise l'heure actuelle pour générer un email unique et éviter les conflits dans la base
        testUser.setEmail("test_" + System.currentTimeMillis() + "@test.com");
        testUser.setPassword("password123");
        
        // On vérifie que l'insertion en base fonctionne (retourne true)
        boolean isRegistered = userDAO.register(testUser);
        assertTrue(isRegistered, "L'enregistrement d'un nouvel utilisateur devrait réussir (retourner true)");
    }
}
