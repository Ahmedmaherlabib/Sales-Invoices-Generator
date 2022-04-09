package com.Project.InvoicesModel;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
public class HeaderTableModel extends AbstractTableModel {
    private static List<InvoiceHeader>InvoiceHeadersList;
    private DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    public HeaderTableModel(List<InvoiceHeader> InvoiceHeadersList) {
          this.InvoiceHeadersList=InvoiceHeadersList;

    }
    public List<InvoiceHeader> getInvoicesList() {
        return InvoiceHeadersList;
    }
    public static List<InvoiceHeader> getInvoiceHeadersList() {
        return InvoiceHeadersList;
    }

    @Override
    public int getRowCount() {
        return InvoiceHeadersList.size();}
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public String getColumnName(int i) {
        switch (i){
            case 0:
                return "Invoice Number";
            case 1:
                return "Customer Name";
            case 2:
                return "Invoice Date";
            case 3:
                return "Invoice Total";
        }
        return "";
    }
    @Override
    public Class<?> getColumnClass(int i) {
        switch (i){
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
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
        InvoiceHeader ROW=InvoiceHeadersList.get(i);
        switch (i1){
            case 0:
                return ROW.getInvoiceNum() ;
            case 1:
                return ROW.getCustomerName();
            case 2:
                return df.format(ROW.getDate());
            case 3:
                return ROW.getInvoiceTotal();
        }
        return null;
    }
}
