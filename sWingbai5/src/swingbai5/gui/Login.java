/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.gui;

import swingbai5.Event.LoginEventListenner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import swingbai5.databases.xulyDB;

/**
 *
 * @author Admin
 */
public class Login extends JFrame {

    private JLabel lblID;
    private JLabel lblPASS;
    private JTextField txtID;
    private JTextField txtPASS;
    private JLabel lblDB;
    private JTextField txtDB;
    private JPanel panel0;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel;
    private JButton btnOK;
    private LoginEventListenner listenner = null; // cai nay la interface ne!!
    private JLabel lblERROR;

    //private Login instance ;
    public Login() throws HeadlessException {
        super("Login");
        this.setSize(300, 400);
        this.getContentPane().setLayout(null);
        // binh thuong class ke thua JFrame thi no se lay panel dau tien no add lam panel chinh . 
        // Nhu vay kho can chinh panel do' . Nen dung lenh ben tren :D
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        giaodien();
        xuly();
        this.setVisible(true);
    }

    private void giaodien() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setLocation(15, 50);
        panel.setSize(250, 250);// panel dau tien ma minh de cap o tren day ne.

        panel0 = new JPanel();
        panel0.setLayout(new BorderLayout());
        panel0.setPreferredSize(new Dimension(300, 40));
        lblID = new JLabel("ID:");
        lblID.setFont(new Font("Consolas", Font.PLAIN, 25));
        txtID = new JTextField("");
        panel0.add(lblID, BorderLayout.NORTH);
        panel0.add(txtID, BorderLayout.CENTER);
        panel.add(panel0);

        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        lblPASS = new JLabel("Pass:");
        lblPASS.setFont(new Font("Consolas", Font.PLAIN, 25));
        txtPASS = new JTextField("");
        panel1.add(lblPASS, BorderLayout.NORTH);
        panel1.add(txtPASS, BorderLayout.CENTER);
        panel.add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        lblDB = new JLabel("Database:");
        lblDB.setFont(new Font("Consolas", Font.PLAIN, 25));
        txtDB = new JTextField("demodb");// @@ viet truoc cai ten database minh hay xai` . Bo "demodb" cung duoc
        panel2.add(lblDB, BorderLayout.NORTH);
        panel2.add(txtDB, BorderLayout.CENTER);
        panel.add(panel2);

        btnOK = new JButton("LOGIN");
        //btnOK.setPreferredSize(new Dimension(40,40));
        panel.add(btnOK);

        lblERROR = new JLabel(" ");
        panel.add(lblERROR);
        this.getContentPane().add(panel);// code nhu nay thi se thoai mai dieu chinh panel . Nho chinh size cho panel nhe !
        //this.setFocusable(true);
    }

    private void xuly() {
        btnOK.addActionListener(new ActionListener() {
            //them su kien khi click vao nut "LOGIN" 
            @Override
            public void actionPerformed(ActionEvent e) {
                xulyDB db = null;// Tao doi tuong nay muc dich la de ket noi SQL
                try {
                    db = new xulyDB("jdbc:mysql://localhost:3306/" + txtDB.getText(), txtID.getText(), txtPASS.getText());
                    // jdbc:mysql://localhost:3306/ la dia chi mac dinh cua localhost , 3306 la port mac dinh cua localhost
                    if (listenner != null) {
                        listenner.onLogin(db);
                    }
                    cLOSE();// an? cua so Login sau khi ket noi xong
                } catch (SQLException ex) {// neu nhap sai id hoac pass hoac database thi se chay lenh ben duoi
                    btnOK.setText("Try again!");// cho nut Login thanh Try Again
                    lblERROR.setText("                 Sai ID hoặc Password!");// bao chi tiet loi~
                }

            }
        });
        eventKey evenenter = new eventKey()  ;// Tao doi tuong su kien nut enter
        txtID.addKeyListener(evenenter);// Enter khi con tro dang o phan nhap ID
        txtPASS.addKeyListener( evenenter);// Enter khi con tro dang o phan nhap pass
        txtDB.addKeyListener(evenenter);// Enter khi con tro dang o phan nhap ten database
        this.addKeyListener(evenenter);// Enter khi vua moi' mo giao dien login . Hiem khi xai cai nay nhung van nen cho zo
    }

    private void cLOSE() {
        this.setVisible(false);
    }

    public void setEventListenner(LoginEventListenner listenner) {
        this.listenner = listenner;
    }

    public class eventKey implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {// Tao su kien nut Enter
            if (e.getKeyCode() == 10) {// KeyCode cua nut Enter la 10 nen ktra nhu vay
                xulyDB db = null;
                try {
                    db = new xulyDB("jdbc:mysql://localhost:3306/" + txtDB.getText(), txtID.getText(), txtPASS.getText());
                    if (listenner != null) {
                        listenner.onLogin(db);
                    }
                    cLOSE();
                } catch (SQLException ex) {
                    btnOK.setText("Try again!");
                    lblERROR.setText("                 Sai ID hoặc Password!");
                }
            }// su kien giong nhu nut LOGIN thoi!
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}
