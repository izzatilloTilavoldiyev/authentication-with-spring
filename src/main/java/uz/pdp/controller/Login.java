package uz.pdp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.model.User;
import uz.pdp.service.DbService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        DbService dbService = new DbService();
        User user = dbService.login(username, password);
        PrintWriter printWriter = resp.getWriter();
        if (user == null) {
            printWriter.write("<h1>Password or login error</h1>");
        }else {
            Cookie cookie = new Cookie("authApp", user.getUsername());
            cookie.setMaxAge(60);
            resp.addCookie(cookie);
            resp.sendRedirect("/cabinet");
        }
    }
}
