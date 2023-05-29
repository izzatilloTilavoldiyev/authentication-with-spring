package uz.pdp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.model.Result;
import uz.pdp.model.User;
import uz.pdp.service.DbService;

import java.io.IOException;
import java.io.PrintWriter;

public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbService dbService = new DbService();
        PrintWriter printWriter = resp.getWriter();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");
        if (password.equals(prePassword)) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            Result result = dbService.registerUser(user);
            if (!result.isSuccess()) {
                printWriter.write("<h1 color = 'red'>" + result.getMessage() + "</h1>");
            }else {
                printWriter.write("<h1 color = 'green'>" + result.getMessage() + "</h1>");
            }
        }else {
            printWriter.write("<h1>Password not equal</h1>");
        }
    }
}
