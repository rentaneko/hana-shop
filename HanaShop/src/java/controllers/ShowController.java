/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ProductDAO;
import dtos.ProductDTO;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ShowController", urlPatterns = {"/ShowController"})
public class ShowController extends HttpServlet {

    private static final String SUCCESS = "homepage.jsp";
    private static final int PAGE_SIZE = 15;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list;
            list = dao.getListProduct();
            session.setAttribute("LIST_PRODUCT_ADMIN", list);
            int page = (int) Math.ceil((double) list.size() / PAGE_SIZE);
            session.setAttribute("PAGE_PRODUCT_ADMIN", page);
        } catch (Exception e) {
            log("ERROR at ShowController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(SUCCESS).forward(request, response);
        }
    }
// đề đâu
    //4 trang để sẵn đó
    // k phả code, cái pdf đề đâu
    // pdf gì ba?
    // ủa hana shop
    // ủa sao thế
    // =]]z bài này tao làm rồi, tao tưởng nó đổi bộ đề rồi chứ. này là bộ đề tao chơi block 2 tuần này
    //r thôi đưa sụt t nộp mẹ cho rồi :))
    // mơ đi, tao chỉ support paging thôi. chứ bài tao làm full chức năng lận
    // thì thôi cho t copy cho lẹ đi ba :v
    // anh em cô
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
