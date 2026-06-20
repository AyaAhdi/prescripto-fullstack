package com.prescripto.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prescripto.dao.DoctorDAO;
import com.prescripto.dao.AppointmentDAO;
import com.prescripto.models.Doctor;
import com.prescripto.models.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/doctor/list", "/api/doctor/change-availability", "/api/doctor/login", "/api/doctor/profile", "/api/doctor/update-profile", "/api/doctor/dashboard", "/api/doctor/cancel-appointment", "/api/doctor/complete-appointment", "/api/doctor/appointments"})
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/list")) {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("doctors", gson.toJsonTree(doctors));
            resp.getWriter().write(gson.toJson(jsonResponse));
        } else if (path.endsWith("/profile")) {
            handleProfile(req, resp);
        } else if (path.endsWith("/dashboard")) {
            handleDashboard(req, resp);
        } else if (path.endsWith("/appointments")) {
            handleAppointments(req, resp);
        }
    }

    private void handleAppointments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("dtoken");
        if (token != null && token.startsWith("mock-token-doc-")) {
            int docId = Integer.parseInt(token.replace("mock-token-doc-", ""));
            List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctor(docId);
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("appointments", gson.toJsonTree(appointments));
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/change-availability")) {
            String body = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
            int docId = jsonBody.get("docId").getAsInt();
            boolean success = doctorDAO.changeAvailability(docId);

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", success);
            resp.getWriter().write(gson.toJson(jsonResponse));
        } else if (path.endsWith("/login")) {
            handleLogin(req, resp);
        } else if (path.endsWith("/update-profile")) {
            handleUpdateProfile(req, resp);
        } else if (path.endsWith("/cancel-appointment")) {
            handleCancel(req, resp);
        } else if (path.endsWith("/complete-appointment")) {
            handleComplete(req, resp);
        }
    }

    private void handleCancel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
        int appointmentId = jsonBody.get("appointmentId").getAsInt();
        boolean success = appointmentDAO.cancelAppointment(appointmentId);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleComplete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
        int appointmentId = jsonBody.get("appointmentId").getAsInt();
        boolean success = appointmentDAO.completeAppointment(appointmentId);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        Doctor loginData = gson.fromJson(body, Doctor.class);
        Doctor doc = doctorDAO.login(loginData.getEmail(), loginData.getPassword());

        JsonObject jsonResponse = new JsonObject();
        if (doc != null) {
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("token", "mock-token-doc-" + doc.getId());
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid credentials");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("dtoken");
        if (token != null && token.startsWith("mock-token-doc-")) {
            int docId = Integer.parseInt(token.replace("mock-token-doc-", ""));
            Doctor doc = doctorDAO.getDoctorById(docId);
            JsonObject jsonResponse = new JsonObject();
            if (doc != null) {
                jsonResponse.addProperty("success", true);
                jsonResponse.add("profileData", gson.toJsonTree(doc));
            } else {
                jsonResponse.addProperty("success", false);
            }
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        Doctor doc = gson.fromJson(body, Doctor.class);
        boolean success = doctorDAO.updateProfile(doc);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleDashboard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("dtoken");
        if (token != null && token.startsWith("mock-token-doc-")) {
            int docId = Integer.parseInt(token.replace("mock-token-doc-", ""));
            List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctor(docId);
            
            int earnings = 0;
            for (Appointment app : appointments) {
                if (app.isCompleted() || app.isPayment()) {
                    earnings += app.getAmount();
                }
            }

            JsonObject dashData = new JsonObject();
            dashData.addProperty("earnings", earnings);
            dashData.addProperty("appointments", appointments.size());
            dashData.addProperty("patients", appointments.stream().map(Appointment::getUserId).distinct().count());
            dashData.add("latestAppointments", gson.toJsonTree(appointments));

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("dashData", dashData);
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }
}
