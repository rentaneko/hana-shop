/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dtos.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoadPageController", urlPatterns = {"/LoadPageController"})
public class LoadPageController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "homepage.jsp";
    private final static int PAGE_SIZE = 15;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String pageIndex = request.getParameter("pageIndexAdmin");
            int index = 1;
            Object attribute = session.getAttribute("CURRENT_PAGE_ADMIN");
            if (attribute != null) {
                index = (int) attribute;
            }
            if (pageIndex != null) {
                index = Integer.parseInt(pageIndex);
            }
            List<ProductDTO> list = (List<ProductDTO>) session.getAttribute("LIST_PRODUCT_ADMIN");

            url = SUCCESS;
            int start = (index - 1) * PAGE_SIZE;
            int end = (index) * PAGE_SIZE;
            if (end > list.size()) {
                end = list.size();
            }

            List<ProductDTO> listPerPage = list.subList(start, end);
            request.setAttribute("LIST_PRODUCT_ADMIN", listPerPage);
            request.setAttribute("CURRENT_PAGE_ADMIN", index);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
