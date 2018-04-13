/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingbai5.gui;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import swingbai5.Event.UpdateEventListenner;

/**
 *
 * @author Admin
 */
public class TableModelData extends DefaultTableModel {

    private ArrayList<String> header;
    private HashMap<Integer,Integer> rowState = new HashMap<Integer,Integer>();
    // Su dung HashMap de kiem tra trang thai 
    // cua hang xem no co dang duoc chinh sua ko
    private ArrayList<String[]> data;
    private UpdateEventListenner event;
    public int getrowState(int row)// tra ve trang thai cua hang` 
    {
        Integer save= this.rowState.get(row);
        if (save==null) save = 1;
        return save;
    }
    public void setrowState(int row,int state)// dat. trang thai cho hang`
    {
        rowState.put(row, state);
    }
    public TableModelData() {// Constructor mac dinh :v binh` voi^ thoi
        
    }
    
    public void setonUpdateEvent(UpdateEventListenner event){
        this.event = event; 
    }
    
    @Override
    public void addColumn(Object columnName) {
        super.addColumn(columnName);
    }

    @Override
    public String getColumnName(int column) {
        return header.get(column);
    }

    @Override
    public int getColumnCount() {
        return header.size();
    }

//
//    public void setHeader(String[] header) {
//        this.header = header;
//        for (String string : header) {
//            addColumn(string);
//        }
//    }
    // cai ben tren viet linh tinh thui :<
    public ArrayList<String> getHeader() {
        return header;
    }

    public void setHeader(ArrayList<String> header) {// dat. ten cot cho bang?
        this.header = header;
        for (String string : header) {
            addColumn(string);
        }
    }

    public void update() {
        this.fireTableDataChanged();// load lai bang? @@ 
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    @Override
    public Object getValueAt(int row, int column) {// lay gia tri tai hang` row va cot column
        if(row>=this.data.size()){
            return "";
        }
        else
        return data.get(row)[column];  
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        String[] oldata = new String[this.getColumnCount()];
        if(row>=this.data.size()){// khi row >= size hien tai cua bang? co nghia chung ta dang them du lieu 
            // vao hang` cuoi cung va cung~ la hang` de them du lieu moi'
           String[] newarr =new String[this.getColumnCount()];
           newarr[column] = aValue+"";
           oldata = null;
           this.data.add(newarr);
        }
        else 
        {
            // Truong hop nay la khi chung ta dang chinh sua du lieu cu~ . Ta can` luu tam thoi du lieu cu~ cua hang` 
            // dang duoc chinh sua . Va chi sua lai phan` can` thiet
            String[] rowcur = this.data.get(row);
            for (int i = 0; i < this.getColumnCount(); i++) oldata[i] = rowcur[i];
            this.data.get(row)[column] = aValue.toString();
        }
        if (this.event!=null) this.event.onUpdate(row,oldata, this.data.get(row));
    }
    

    @Override
    public int getRowCount() {// tra ve so hang`
        if (data == null) {
            return 0;
        }
        return data.size()+1; // +1 vi ta luon luon tao 1 hang` trong' de them du lieu moi'
    }

}
