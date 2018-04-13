/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5;

import swingbai5.databases.xulyDB;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;
import javax.swing.UIManager;
import swingbai5.gui.myFrame;

/**
 *
 * @author Admin
 */
public class SWingbai5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
            // cai ben tren giup giao dien Java bien thanh giao dien cua he dieu hanh dang dung (WIN 10). Xoa di cung duoc
            new myFrame();
        } catch (Exception e) {
        }

    }

}
