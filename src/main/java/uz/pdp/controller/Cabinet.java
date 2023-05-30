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

@WebServlet(value = "/cabinet")
public class Cabinet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        String username="";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authApp")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        DbService dbService = new DbService();
        User user = dbService.loadUserByCookie(username);
        if (user == null) {
            Cookie cookie = new Cookie("authApp", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        }

        if (user != null) {
            printWriter.write("<h1>Welcome to system "+user.getFirstName()+ " "+ user.getLastName()+"</h1>");
            printWriter.write("<h1>Your phone number: "+user.getPhoneNumber()+"</h1>");
        }
    }
}
