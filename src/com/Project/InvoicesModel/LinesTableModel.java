package com.Project.InvoicesModel;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LinesTableModel extends AbstractTableModel {
    private static List<InvoiceLines> InvoiceLines;
    private DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    public LinesTableModel(List<InvoiceLines> InvoiceHeadersList) {
        this.InvoiceLines=InvoiceHeadersList;

    }


    public List<InvoiceLines> getInvoiceLines() {
        return InvoiceLines;
    }
    @Override
    public int getRowCount() {
        return InvoiceLines.size();}
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public String getColumnName(int i) {
        switch (i){
            case 0:
                return "Item Name";
            case 1:
                return "Item Price";
            case 2:
                return "Item Count";
            case 3:
                return "Line Total";
        }
        return "";
    }
    @Override
    public Class<?> getColumnClass(int i) {
        switch (i){
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
            case 3:
                return Double.class;
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        InvoiceLines ROW=InvoiceLines.get(i);
        switch (i1){
            case 0:
                return ROW.getItemName() ;
            case 1:
                return ROW.getItemPrice();
            case 2:
                return ROW.getItemCount();
            case 3:
                return ROW.getLinesTotal();
        }
        return null;
    }
}
