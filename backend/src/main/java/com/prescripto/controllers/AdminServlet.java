package com.prescripto.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prescripto.dao.DoctorDAO;
import com.prescripto.dao.AppointmentDAO;
import com.prescripto.models.Doctor;
import com.prescripto.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/admin/login", "/api/admin/add-doctor", "/api/admin/dashboard", "/api/admin/appointments", "/api/admin/cancel-appointment"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/login")) {
            handleLogin(req, resp);
        } else if (path.endsWith("/add-doctor")) {
            handleAddDoctor(req, resp);
        } else if (path.endsWith("/cancel-appointment")) {
            handleCancel(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/dashboard")) {
            handleDashboard(req, resp);
        } else if (path.endsWith("/appointments")) {
            handleAppointments(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        User loginData = gson.fromJson(body, User.class);
        
        // Simple admin check for demo (in production use password hashing and DB check)
        if ("admin@prescripto.com".equals(loginData.getEmail()) && "admin123".equals(loginData.getPassword())) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("token", "mock-admin-token");
            resp.getWriter().write(gson.toJson(jsonResponse));
        } else {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid admin credentials");
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    private void handleAddDoctor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        Doctor doc = gson.fromJson(body, Doctor.class);
        boolean success = doctorDAO.addDoctor(doc);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        if (success) {
            jsonResponse.addProperty("message", "Doctor added successfully");
        } else {
            jsonResponse.addProperty("message", "Failed to add doctor");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleDashboard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject dashData = new JsonObject();
        dashData.addProperty("doctors", doctorDAO.getAllDoctors().size());
        dashData.addProperty("appointments", appointmentDAO.getAllAppointments().size());
        dashData.addProperty("patients", 10); // Mocked for now
        dashData.add("latestAppointments", gson.toJsonTree(appointmentDAO.getAllAppointments()));

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.add("dashData", dashData);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleAppointments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.add("appointments", gson.toJsonTree(appointmentDAO.getAllAppointments()));
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleCancel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
        int appointmentId = jsonBody.get("appointmentId").getAsInt();
        boolean success = appointmentDAO.cancelAppointment(appointmentId);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        if (success) {
            jsonResponse.addProperty("message", "Appointment cancelled successfully");
        } else {
            jsonResponse.addProperty("message", "Failed to cancel appointment");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
