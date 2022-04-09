package com.Project.InvoicesModel;

public class InvoiceLines {
    private String ItemName;
    private double ItemPrice;
    private int ItemCount;
    private InvoiceHeader header;
    public InvoiceLines(String itemName, double itemPrice, int itemCount, InvoiceHeader header) {
        this.ItemName = itemName;
        this.ItemPrice = itemPrice;
        this.ItemCount = itemCount;
        this.header = header;
    }
    public InvoiceHeader getHeader() {
        return header;
    }
    public String getDataAsCSV() {
        return "" + getHeader().getInvoiceNum() + "," + getItemName() + "," + getItemPrice() + "," + getItemCount();
    }
    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }
    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public double getItemPrice() {
        return ItemPrice;
    }
    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }
    public int getItemCount() {
        return ItemCount;
    }
    public void setItemCount(int itemCount) {
        ItemCount = itemCount;
    }
    @Override
    public String toString() {
        return "InvoiceLines{" +
                "ItemName='" + ItemName + '\'' +
                ", ItemPrice=" + ItemPrice +
                ", ItemCount=" + ItemCount +
                '}';
    }
    public double getLinesTotal(){
        return ItemCount*ItemPrice;
    }
}
