/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import connection.DBConnection;
import dtos.ProductDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ProductDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private final int PAGE_SIZE = 5;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<ProductDTO> getListProduct() throws Exception {
        List<ProductDTO> list = new ArrayList<>();
        try {
            String sql = "Select * from tbl_Product WHERE isActive = 1 "
                    + "ORDER BY IdProduct DESC ";
//                    + "OFFSET 0 ROW "
//                    + "FETCH NEXT 30 ROWS ONLY";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductID(rs.getInt("IdProduct"));
                dto.setCategoryID(rs.getString("IdCategory"));
                dto.setCreateDate(rs.getString("CreateDate"));
                dto.setPrice(rs.getFloat("Price"));
                dto.setProductID(rs.getInt("IdProduct"));
                dto.setProductName(rs.getString("ProductName"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setUrlImg(rs.getString("Image"));
                dto.setIsActive(rs.getBoolean("isActive"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<ProductDTO> searchByCategory(String category) throws Exception {
        List<ProductDTO> list = new ArrayList<>();
        try {
            String sql = "Select * from tbl_Product WHERE isActive = 1 AND IdCategory LIKE ? "
                    + "ORDER BY CreateDate DESC ";
//                    + "OFFSET ? ROW "
//                    + "FETCH NEXT ? ROWS ONLY";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, category);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductID(rs.getInt("IdProduct"));
                dto.setCategoryID(rs.getString("IdCategory"));
                dto.setCreateDate(rs.getString("CreateDate"));
                dto.setPrice(rs.getFloat("Price"));
                dto.setProductID(rs.getInt("IdProduct"));
                dto.setProductName(rs.getString("ProductName"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setUrlImg(rs.getString("Image"));
                dto.setIsActive(rs.getBoolean("isActive"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<ProductDTO> searchProductV2(String search, String fromPrice, String toPrice, String category, int pageIndex) throws Exception {
        List<ProductDTO> list = new ArrayList<>();
        String sql;
        try {
            conn = DBConnection.getDBConnection();
            if (!fromPrice.isEmpty() && !toPrice.isEmpty()) {
                sql = "Select * from tbl_Product "
                        + "where isActive = 1 AND quantity > 0 AND ProductName LIKE ? OR (Price >= fromPrice AND Price <= toPrice) "
                        + "ORDER BY CreateDate DESC "
                        + "OFFSET ? ROW "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%N'" + search + "%");
                stm.setFloat(2, Float.parseFloat(fromPrice));
                stm.setFloat(3, Float.parseFloat(toPrice));
                stm.setInt(4, (pageIndex - 1) * PAGE_SIZE);
                stm.setInt(5, PAGE_SIZE);
            } else if (!fromPrice.isEmpty()) {
                sql = "Select * from tbl_Product "
                        + "where isActive = 1 AND quantity > 0 AND ProductName LIKE ? OR Price >= fromPrice "
                        + "ORDER BY CreateDate DESC "
                        + "OFFSET ? ROW "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setFloat(2, Float.parseFloat(fromPrice));
                stm.setInt(3, (pageIndex - 1) * PAGE_SIZE);
                stm.setInt(4, PAGE_SIZE);
            } else if (!toPrice.isEmpty()) {
                sql = "Select * from tbl_Product "
                        + "where isActive = 1 AND quantity > 0 AND ProductName LIKE ? OR Price <= toPrice "
                        + "ORDER BY CreateDate DESC "
                        + "OFFSET ? ROW "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setFloat(2, Float.parseFloat(toPrice));
                stm.setInt(3, (pageIndex - 1) * PAGE_SIZE);
                stm.setInt(4, PAGE_SIZE);
            } else {
                sql = "Select * from tbl_Product "
                        + "where isActive = 1 AND quantity > 0 AND ProductName LIKE ? "
                        + "ORDER BY CreateDate DESC "
                        + "OFFSET ? ROW "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setInt(2, (pageIndex - 1) * PAGE_SIZE);
                stm.setInt(3, PAGE_SIZE);
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setCategoryID(rs.getString("IdCategory"));
                dto.setCreateDate(rs.getString("CreateDate"));
                dto.setPrice(rs.getFloat("Price"));
                dto.setProductID(rs.getInt("IdProduct"));
                dto.setProductName(rs.getString("ProductName"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setUrlImg(rs.getString("Image"));
                dto.setIsActive(rs.getBoolean("isActive"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getTotalSearch(String search, String fromPrice, String toPrice, String category) throws Exception {
        int result = 0;
        String sql;
        try {
            conn = DBConnection.getDBConnection();
            if (!fromPrice.isEmpty() && !toPrice.isEmpty()) {
                sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 AND IdCategory = ? OR ProductName LIKE ? OR (Price >= fromPrice AND Price <= toPrice)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setFloat(2, Float.parseFloat(fromPrice));
                stm.setFloat(3, Float.parseFloat(toPrice));
            } else if (!fromPrice.isEmpty()) {
                sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 AND ProductName LIKE ? OR Price >= fromPrice";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setFloat(2, Float.parseFloat(fromPrice));
            } else if (!toPrice.isEmpty()) {
                sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 AND ProductName LIKE ? OR Price <= toPrice";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setFloat(2, Float.parseFloat(toPrice));
            } else {
                sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 AND ProductName LIKE ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalShow() throws Exception {
        int result = 0;
        try {
            String sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 ";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalCategory(String category) throws Exception {
        int result = 0;
        try {
            String sql = "Select Count(IdProduct) as total from tbl_Product where isActive = 1 AND IdCategory = ? ";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, category);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
