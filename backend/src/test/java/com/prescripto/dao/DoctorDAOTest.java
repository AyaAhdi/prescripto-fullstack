package com.prescripto.dao;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DoctorDAOTest {

    @Test
    public void testGetDoctorById() {
        DoctorDAO doctorDAO = new DoctorDAO();
        Object doctor = doctorDAO.getDoctorById(1);
        assertNotNull(doctor, "Le docteur avec l'ID 1 ne devrait pas être nul");
    }

    @Test
    public void testGetAllDoctors() {
        DoctorDAO doctorDAO = new DoctorDAO();
        List<?> doctors = doctorDAO.getAllDoctors();
        assertNotNull(doctors, "La liste des docteurs ne devrait pas être nulle");
        assertFalse(doctors.isEmpty(), "La liste des docteurs ne devrait pas être vide");
    }
}
