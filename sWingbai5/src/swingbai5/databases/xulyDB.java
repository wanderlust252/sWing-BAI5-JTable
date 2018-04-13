/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.databases;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class xulyDB {

    private Connection con;
    private String db = null;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public xulyDB(String url, String id, String pass) throws SQLException {
        con = (Connection) DriverManager.getConnection(url, id, pass);
        // tao ket noi vao SQL
        this.setDb(url.split("6/")[1]);
        // url se co dang jdbc:mysql://localhost:3306/ nen minh lay chuoi~ ben phai cua chuoi~ "6/" de lay duoc ten database
        // ~~ cach nay hoi ngu ngoc nhung chua nghi ra cach khac
    }

    public ResultSet get(String sql) throws SQLException {
        //muc dich cai nay la de chay lenh SELECT trong SQL va lay ket qua tra ve
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();

    }

    public void post(String sql) throws SQLException {
        // muc dich la de chay lenh vao SQL va dung` de chay lenh thoi (chinh sua , them , xoa' ....)
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();

    }

}
