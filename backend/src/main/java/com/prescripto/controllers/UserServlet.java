package com.prescripto.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prescripto.dao.UserDAO;
import com.prescripto.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/user/login", "/api/user/register", "/api/user/get-profile", "/api/user/update-profile"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/login")) {
            handleLogin(req, resp);
        } else if (path.endsWith("/register")) {
            handleRegister(req, resp);
        } else if (path.endsWith("/update-profile")) {
            handleUpdateProfile(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json");

        if (path.endsWith("/get-profile")) {
            handleGetProfile(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        User loginData = gson.fromJson(body, User.class);
        User user = userDAO.login(loginData.getEmail(), loginData.getPassword());

        JsonObject jsonResponse = new JsonObject();
        if (user != null) {
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("token", "mock-token-" + user.getId()); // In real app, use JWT
            jsonResponse.add("userData", gson.toJsonTree(user));
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid credentials");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        User user = gson.fromJson(body, User.class);
        boolean success = userDAO.register(user);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        if (success) {
            jsonResponse.addProperty("message", "Registration successful");
        } else {
            jsonResponse.addProperty("message", "Registration failed or email already exists");
        }
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void handleGetProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getHeader("token");
        if (token != null && token.startsWith("mock-token-")) {
            int userId = Integer.parseInt(token.replace("mock-token-", ""));
            User user = userDAO.getUserById(userId);
            JsonObject jsonResponse = new JsonObject();
            if (user != null) {
                jsonResponse.addProperty("success", true);
                jsonResponse.add("userData", gson.toJsonTree(user));
            } else {
                jsonResponse.addProperty("success", false);
            }
            resp.getWriter().write(gson.toJson(jsonResponse));
        }
    }

    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        User user = gson.fromJson(body, User.class);
        boolean success = userDAO.updateProfile(user);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
