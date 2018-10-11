package com.epam.lab.servlets;

import jdk.nashorn.internal.ir.Splittable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ServletHomework extends HttpServlet {
    private static List<String> largeCollection = new LinkedList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        String paramIdx = req.getParameter("idx");
        int idx = -1;
        if(paramIdx != null) {
            idx = Integer.valueOf(paramIdx);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/buttons.jsp");

        /*
        if((cmd != null) && cmd.equals("true")) {
            resp.setContentType("text/plain");
            if (idx >= 0 && idx < largeCollection.size()) {
                resp.getWriter().print(largeCollection.get(idx));
            } else {
                resp.getWriter().print("Fail with get!");
            }
        } else {
            requestDispatcher.forward(req, resp);
        }
        */

        req.setAttribute("list", largeCollection);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newStr = req.getParameter("str");
        resp.setContentType("text/plain");

        if (newStr != null) {
            largeCollection.add(newStr);
            resp.getWriter().print("Success with post!");
        } else {
            resp.getWriter().print("Fail with post!");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> dataMap = getParametersFromServletInputStream(req.getInputStream());

        String newStr = dataMap.get("str");
        String paramIdx = dataMap.get("idx");
        int idx = -1;
        if(paramIdx != null) {
            idx = Integer.valueOf(paramIdx);
        }

        resp.setContentType("text/plain");
        if((idx >= 0 && idx < largeCollection.size()) && (newStr != null)) {
            largeCollection.set(idx, newStr);
            resp.getWriter().print("Success with put!");
        } else {
            resp.getWriter().print("Fail with put!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> dataMap = getParametersFromServletInputStream(req.getInputStream());

        String paramIdx = dataMap.get("idx");
        int idx = -1;
        if(paramIdx != null) {
            idx = Integer.valueOf(paramIdx);
        }

        resp.setContentType("text/plain");
        if(idx >= 0 && idx < largeCollection.size()) {
            largeCollection.remove(idx);
            resp.getWriter().print("Success with delete!");
        } else {
            resp.getWriter().print("Fail with delete!");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("Destroy servlet");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Init servlet");
    }

    private Map<String, String> getParametersFromServletInputStream(ServletInputStream inStream) {
        InputStreamReader reader = new InputStreamReader(inStream);
        BufferedReader br = new BufferedReader(reader);

        Map<String, String> mapParameters = new HashMap<>();
        try {
            String data = br.readLine();
            for(String str : data.split("&")) {
                String[] keyVal = str.split("=");
                mapParameters.put(keyVal[0], keyVal[1]);
            }
        } catch (IOException e) {
        }
        return mapParameters;
    }
}
