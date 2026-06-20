package com.prescripto.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prescripto.dao.AppointmentDAO;
import com.prescripto.models.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/user/book-appointment", "/api/user/appointments", "/api/user/cancel-appointment", "/api/user/payment", "/api/user/select-payment-method"})
public class AppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.contains("/book-appointment")) {
            handleBook(req, resp);
        } else if (path.contains("/cancel-appointment")) {
            handleCancel(req, resp);
        } else if (path.contains("/payment")) {
            handlePayment(req, resp);
        } else if (path.contains("/select-payment-method")) {
            handleSelectPaymentMethod(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.contains("/user/appointments")) {
            handleUserAppointments(req, resp);
        }
    }

    private void handleBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("token");
        JsonObject jsonResponse = new JsonObject();

        if (token != null && token.startsWith("mock-token-")) {
            int userId = Integer.parseInt(token.replace("mock-token-", ""));
            String body = req.getReader().lines().collect(Collectors.joining());
            Appointment app = gson.fromJson(body, Appointment.class);
            app.setUserId(userId);
            
            boolean success = appointmentDAO.bookAppointment(app);
            jsonResponse.addProperty("success", success);
            if (success) {
                jsonResponse.addProperty("message", "Appointment booked successfully");
            } else {
                jsonResponse.addProperty("message", "Failed to book appointment");
            }
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid token");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
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

    private void handlePayment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
        int appointmentId = jsonBody.get("appointmentId").getAsInt();
        boolean success = appointmentDAO.updatePaymentStatus(appointmentId, true);
        if (success) {
            appointmentDAO.updatePaymentMethod(appointmentId, "Online");
        }

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleSelectPaymentMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonBody = gson.fromJson(body, JsonObject.class);
        int appointmentId = jsonBody.get("appointmentId").getAsInt();
        String method = jsonBody.get("method").getAsString();
        boolean success = appointmentDAO.updatePaymentMethod(appointmentId, method);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleUserAppointments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("token");
        if (token != null && token.startsWith("mock-token-")) {
            int userId = Integer.parseInt(token.replace("mock-token-", ""));
            List<Appointment> appointments = appointmentDAO.getAppointmentsByUser(userId);
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("appointments", gson.toJsonTree(appointments));
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }
}
