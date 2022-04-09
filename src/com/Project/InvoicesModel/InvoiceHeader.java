package com.Project.InvoicesModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class InvoiceHeader {
    private int invoiceNum;
    private String CustomerName;
    private Date date;
    private ArrayList<InvoiceLines> Lines;
    public InvoiceHeader(int invoiceNum, String customerName, Date date) {
        this.invoiceNum = invoiceNum;
        this.CustomerName = customerName;
        this.date = date;
        //this.Lines= new ArrayList<>();//eager creation
    }
    public ArrayList<InvoiceLines> getLines() {
        if (Lines==null)
            Lines=new ArrayList<>();
        return Lines;
    }
    public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoiceNum() + "," + df.format(getDate()) + "," + getCustomerName();
    }
    public void setLines(ArrayList<InvoiceLines> lines) {
        Lines = lines;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {

        this.invoiceNum = invoiceNum;
    }



    @Override
    public String toString() {
        String str= "InvoiceHeader{" + "invoiceNum=" + invoiceNum + ", CustomerName='"
                + CustomerName + '\'' + ", date=" + date + '}';
        for (InvoiceLines line : getLines()) {
            str += "\n\t" + line;
        }
        return str;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public double getInvoiceTotal(){
       double total= 0.0;
       for(InvoiceLines lines:getLines()){
           total+=lines.getLinesTotal();
       }
    return total;
    }
    public void AddInvoiceLine(InvoiceLines line){
       getLines().add(line);
    }

}
