/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import swingbai5.Event.LoginEventListenner;
import swingbai5.Event.UpdateEventListenner;
import swingbai5.databases.xulyDB;

/**
 *
 * @author Admin
 */
public class myFrame extends JFrame {

    // public static int i =1;
    public xulyDB xulydb;
    private JPanel panel;
    private JLabel lblID;
    private JPanel panelFlow;
    private JTextField txtID;
    private JLabel lblPASS;
    private JTextField txtPASS;
    private JPanel leftpanel;
    private JLabel lblTable;
    private JPanel rightpanel;
    private JLabel lbldata;
    private JButton btnUPDATE;
    private ArrayList<String> header;
    private ArrayList<String[]> data;
    private JList listTable;
    private DefaultListModel tblNAME;
    private TableModelData datatb;
    private int lastpick;
    private JPanel panelSAVE;
    private JLabel lblSAVE;
    private String post = "";
    private ResultSet res;
    private UpdateEventListenner updateTableEvent = new UpdateEventListenner() {
        // Tao doi tuong de xu ly chinh sua , them data
        @Override
        public void onUpdate(int row, String[] Old, String[] New) {
            post = "";
            if (Old == null || datatb.getrowState(row) == -2) {// Them du lieu moi vao database neu 
                // du lieu cua Old = null hoac trang thai cua dong` nhap du lieu la -2

                int i = datatb.getColumnCount();
                post += "INSERT INTO " + tblNAME.get(listTable.getSelectedIndex()) + " (";
                for (int m = 0; m < i; m++) {
                    post += "`" + datatb.getColumnName(m) + "`,";
                }
                post = post.substring(0, post.length() - 1);
                post += ") VALUES (";
                for (int k = 0; k < i; k++) {
                    post += "'" + New[k] + "'" + ",";
                }
                post = post.substring(0, post.length() - 1);
                post += ")";
            } else {// Chinh sua du lieu cu~ 
                int i = datatb.getColumnCount();
                post += "UPDATE " + tblNAME.get(listTable.getSelectedIndex()) + " set ";
                for (int m = 0; m < i; m++) {
                    post += "`" + datatb.getColumnName(m) + "` = '" + New[m] + "',";
                }
                post = post.substring(0, post.length() - 1);
                post += " WHERE ";

                for (int m = 0; m < i; m++) {
                    post += " `" + datatb.getColumnName(m) + "` = '" + Old[m] + "' and";
                }
                post = post.substring(0, post.length() - 3);
            }
            try {
                datatb.setrowState(row, 1);// trang thai 1
                xulydb.post(post);
                lblSAVE.setText("SAVED!");
            } catch (Exception e) {
                datatb.setrowState(row, -2);// trang thai -2
                lblSAVE.setText("ERROR!");

            }
        }
    };

    public myFrame() throws HeadlessException, SQLException {

        super("Bai 5");
        giaodien();// chay ham` giaodien() 

    }

    private void giaodien() {
        new Login().setEventListenner(new LoginEventListenner() {
            //tao giao dien dang nhap vao SQL . Ghi de` phuong thuc onLogin cua interface LoginEventListenner
            // sr giai thich kho hieu qua !
            @Override
            public void onLogin(xulyDB db) {
                xulydb = db;
                try {
                    giaodien1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void xulybtn() {

        btnUPDATE.addActionListener(new ActionListener() {
            //Tao su kien (lenh) khi click vao nut "SELECT" 
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastpick != listTable.getSelectedIndex()) {
                    try {
                        data.clear();
                        header.clear();
                        res = xulydb.get("SELECT * FROM " + tblNAME.get(listTable.getSelectedIndex()));
                        // ResultSet res la bien' luu tru thong tin cua bang? 
                        int i = res.getMetaData().getColumnCount();// lay so cot
                        ArrayList<String> str = new ArrayList<>();
                        for (int m = 0; m < i; m++) {
                            header.add(res.getMetaData().getColumnName(m + 1));
                        }// them^ ten cot. cho bang?
                        while (res.next()) {// 1 hang` la 1 lan next 
                            for (int n = 0; n < i; n++) {
                                str.add(res.getString(res.getMetaData().getColumnName(n + 1)));
                            }// them^ du lieu cho cot
                            data.add(str.toArray(new String[str.size()]));
                            str.clear();
                        }
                        datatb.setHeader(header);
                        datatb.setData(data);
                        datatb.update();
                        System.out.println("update");
                        lastpick = listTable.getSelectedIndex();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void benphai(JScrollPane js) {
        rightpanel = new JPanel();
        rightpanel.setLayout(new BorderLayout());

        lbldata = new JLabel("DATA");
        lbldata.setFont(new Font("Consolas", Font.BOLD, 25));
        lbldata.setHorizontalAlignment(SwingConstants.CENTER);

        panelSAVE = new JPanel();
        panelSAVE.setLayout(new BorderLayout());
        lblSAVE = new JLabel();
        lblSAVE.setFont(new Font("Consolas", Font.PLAIN, 15));
        lblSAVE.setHorizontalAlignment(SwingConstants.CENTER);
        panelSAVE.add(lblSAVE, BorderLayout.CENTER);

        rightpanel.add(lbldata, BorderLayout.NORTH);
        rightpanel.add(js, BorderLayout.CENTER);
        rightpanel.add(panelSAVE, BorderLayout.SOUTH);

    }

    private void bentrai() {
        leftpanel = new JPanel();
        leftpanel.setPreferredSize(new Dimension(150, 600));
        leftpanel.setLayout(new BorderLayout());

        lblTable = new JLabel("TABLES");
        lblTable.setFont(new Font("Consolas", Font.BOLD, 25));
        lblTable.setHorizontalAlignment(SwingConstants.CENTER);
        leftpanel.add(lblTable, BorderLayout.NORTH);

        btnUPDATE = new JButton("SELECT");
        btnUPDATE.setFont(new Font("Consolas", Font.PLAIN, 15));
        xulybtn();
        leftpanel.add(btnUPDATE, BorderLayout.SOUTH);
    }

    private void giaodien1() throws SQLException {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(1000, 600);
        bentrai();

        tblNAME = new DefaultListModel();
        ResultSet rs = xulydb.get("SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA ='" + xulydb.getDb() + "'");
        // ResultSet rs dung de lay ten cua tat ca bang? trong database
        while (rs.next()) {
            tblNAME.addElement(rs.getString("TABLE_NAME"));// chuyen ten bang? vao cho DefaultListModel tblNAME
        }

        listTable = new JList(tblNAME);// chuyen tiep ten bang? vao JList listTable
        listTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);// chi chon 1 muc trong JList
        listTable.setSelectedIndex(0);// Mac dinh chay muc. dau tien trong JList truoc
        listTable.setVisibleRowCount(3);// cai nay ko ro~ lam :v thay tren mang. ho lam vay nen copy
        leftpanel.add(listTable, BorderLayout.CENTER);
        panel.add(leftpanel, BorderLayout.WEST);

        ResultSet res = xulydb.get("SELECT * FROM " + tblNAME.get(listTable.getSelectedIndex()));
        // ResultSet res lay toan bo du lieu cua bang? dang duoc chon trong JList
        lastpick = listTable.getSelectedIndex();
        header = new ArrayList<>();
        data = new ArrayList<>();

        int i = res.getMetaData().getColumnCount();
        ArrayList<String> str = new ArrayList<>();
        for (int m = 0; m < i; m++) {
            header.add(res.getMetaData().getColumnName(m + 1));
        }
        while (res.next()) {
            for (int n = 0; n < i; n++) {
                str.add(res.getString(res.getMetaData().getColumnName(n + 1)));
            }
            data.add(str.toArray(new String[str.size()]));
            str.clear();
        }

        datatb = new TableModelData();
        datatb.setHeader(header);
        datatb.setData(data);
        datatb.setonUpdateEvent(updateTableEvent);
        JTable tb = new JTable(datatb);
        JScrollPane sp = new JScrollPane(tb);

        benphai(sp);
        panel.add(rightpanel, BorderLayout.CENTER);
        this.getContentPane().setLayout(null);
        this.setSize(1020, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
        this.setVisible(true);// đoạn này để đây vì muốn login xong mới hiện ra panel lớn
    }
}
