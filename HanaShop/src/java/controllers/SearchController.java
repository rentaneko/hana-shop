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

/**
 *
 * @author DELL
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private static final String SUCCESS = "homepage.jsp";
    private static final int PAGE_SIZE = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            String search = request.getParameter("txtSearch");
            String category = request.getParameter("txtCategory");
            String fromPrice = request.getParameter("txtFromPrice");
            String toPrice = request.getParameter("txtToPrice");
            int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> list;
            //làm chỗ showcontroller ấy, search chưa xài
            // k seảch dc sao show, 
            // show sản phẩm cho admin full hế
            // kiểu lấy full dtb ra luôn á m, t get all luôn
            // ờ, 0 điểm nha con
            // ủa đụ kì v, ừ, k được get all
            // thấy cái dòng đầu tiên đó k
            // à thấy r v thì sửa lại dòng sql tí thôi
            // miễn sao ra đc 20 sản phẩm đầu là được chứ gì,
            // mới vô vẫn phải show ra trc mà. đúng, nhưng chỉ show 20
            // còn lại để những trang sau
            // thì cái m cần là cái đó đó
            // mỗi trang 20 đó
            // haiz, để tao lướt 1 dòng code mày coi đã
            int totalRecord = dao.getTotalSearch(search, fromPrice, toPrice, category);
            System.out.println("Total Record: " + totalRecord);
            int totalPages = 0;
            if (totalRecord % PAGE_SIZE > 0) {
                totalPages = totalRecord / PAGE_SIZE + 1;
            } else {
                totalPages = totalRecord / PAGE_SIZE;
            }
            int pageIndexParam = pageIndex;
            if (pageIndex < 0) {
                pageIndexParam = totalPages;
            }
            if (pageIndex > totalPages) {
                pageIndexParam = 0;
            }
            System.out.println("IndexParam: " + pageIndexParam);
            list = dao.searchProductV2(search, fromPrice, toPrice, category, pageIndexParam);

            if (list != null) {
                request.setAttribute("INFO", list);
            }

        } catch (Exception e) {
            log("ERROR at SearchController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(SUCCESS).forward(request, response);
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
