/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import connection.DBConnection;
import dtos.AccountDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class AccountDAO implements Serializable {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    private final void closeConnection() throws SQLException {
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

    public AccountDTO checkLogin(String username, String password) throws SQLException, NamingException {
        AccountDTO result = null;
        try {
            String sql = "SELECT fullname, roleID FROM tbl_Accounts WHERE username = ?  AND password = ? AND status = 1";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                int role = rs.getInt("roleID");
                String fullname = rs.getString("fullname");
                result = new AccountDTO(username, role, fullname);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
