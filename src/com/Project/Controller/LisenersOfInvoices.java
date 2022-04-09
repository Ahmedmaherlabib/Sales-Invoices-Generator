package com.Project.Controller;
import com.Project.InvoicesModel.*;
import com.Project.Views.Fr;
import com.Project.Views.HeaderDialog;
import com.Project.Views.LinesDialog;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class LisenersOfInvoices implements ActionListener, ListSelectionListener {
    private Fr frame;
    private DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    private FileOperations fileOperations=new FileOperations(frame);
    public LisenersOfInvoices(Fr frame){
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        switch (actionEvent.getActionCommand()) {
            case "L":
                try {
                    fileOperations.LoadFile(frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "Create Invoice":
                ShowNewInvoiceDialog();
                break;
            case  "Delete Invoice":
                DeleteInvoice();
                break;
            case "Create Line":
                displayNewLineDialog();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;

            case "Cancel":
                DeletLine();
                break;
            case "S":
               fileOperations.SaveFile(frame);
                break;
            case "CreateInvoiceOk":
                createInoviceOk();
                break;
            case "CreateInvoiceCancel":
                createInoviceCancel();
                break;
        }
    }

    private void DeletLine() {
        int  LineIndex=frame.getTable2().getSelectedRow();
        InvoiceLines lines=frame.getModel2().getInvoiceLines().get(LineIndex);
        frame.getModel2().getInvoiceLines().remove(LineIndex);
        frame.getModel2().fireTableDataChanged();
        frame.getModel().fireTableDataChanged();
        frame.getNotEditableTex4().setText(""+lines.getHeader().getInvoiceTotal());
        displayInvoices();

    }

    private void createInoviceOk() {
        String CustName =frame.getDialog1().getCustomerName().getText();
        String invDateStr = frame.getDialog1().getInvoiceDate().getText();
        frame.getDialog1().setVisible(false);
        frame.getDialog1().dispose();
        frame.setDialog1(  null);
        try {
            Date invDate = df.parse(invDateStr);
            int invNum = getNextInvoiceNum();
            InvoiceHeader invoiceHeader = new InvoiceHeader(invNum, CustName, invDate);
            frame.getInvoiceHeadersList().add(invoiceHeader);
            frame.getModel().fireTableDataChanged();

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private int getNextInvoiceNum() {
        int max = 0;
        for (InvoiceHeader header : frame.getInvoiceHeadersList()) {
            if (header.getInvoiceNum() > max) {
                max = header.getInvoiceNum();
            }
        }
        return max + 1;
    }

    private void createInoviceCancel() {
        frame.getDialog1().setVisible(false);
        frame.getDialog1().dispose();
      frame.setDialog1(null);
    }

    private void ShowNewInvoiceDialog() {
        frame.setDialog1(new HeaderDialog(frame));
        frame.getDialog1().setVisible(true);

    }
    public void DeleteInvoice(){
        int invIndex = frame.getTable1().getSelectedRow();
        InvoiceHeader header = frame.getModel().getInvoicesList().get(invIndex);
        frame.getModel().getInvoicesList().remove(invIndex);
        frame.getModel().fireTableDataChanged();
        frame.setModel2( new LinesTableModel(new ArrayList<InvoiceLines>()));
        frame.getTable2().setModel(frame.getModel2());
        frame.getModel2().fireTableDataChanged();
        frame.getTex2().setText("");
        frame.getTex3().setText("");
        frame.getNotEditableTex1().setText("");
        frame.getNotEditableTex4().setText("");
        displayInvoices();}



    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        invoiceTableSelectedRow();
    }
    private void createLineCancel() {
      frame.getDialog2().setVisible(false);
        frame.getDialog2().dispose();
        frame.setDialog2(null);
    }
    private void createLineOK() {
        String itemName = frame.getDialog2().getItemNameField().getText();
        String itemCountStr = frame.getDialog2().getItemCountField().getText();
        String itemPriceStr = frame.getDialog2().getItemPriceField().getText();
        frame.getDialog2().setVisible(false);
        frame.getDialog2().dispose();
        frame.setDialog2(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = frame.getTable1().getSelectedRow();
        InvoiceHeader invoice = frame.getModel().getInvoiceHeadersList().get(headerIndex);

        InvoiceLines invoiceLine = new InvoiceLines(itemName, itemPrice, itemCount, invoice);
        invoice.AddInvoiceLine(invoiceLine);
        frame.getModel2().fireTableDataChanged();
        frame.getModel().fireTableDataChanged();
        frame.getNotEditableTex4().setText(""+invoice.getInvoiceTotal());
    }
    private void displayNewLineDialog() {
        frame.setDialog2(new LinesDialog(frame));
        frame.getDialog2().setVisible(true);
    }
    private void displayInvoices() {
        System.out.println("-------------------------");
        for (InvoiceHeader header : frame.getInvoiceHeadersList()) {
            System.out.println(header);
        }
        System.out.println("-------------------------");
    }



    private  void invoiceTableSelectedRow(){
        int RowSelectedIndex=frame.getTable1().getSelectedRow();
        if(RowSelectedIndex>=0){
            InvoiceHeader row=HeaderTableModel.getInvoiceHeadersList().get(RowSelectedIndex);
            frame.getTex2().setText(row.getCustomerName());
            frame.getTex3().setText(df.format(row.getDate()));
            frame.getNotEditableTex1().setText(""+row.getInvoiceNum());
            frame.getNotEditableTex4().setText(""+row.getInvoiceTotal());
            ArrayList<InvoiceLines> Lines=row.getLines();
            frame.setModel2(new LinesTableModel(Lines));
            frame.getTable2().setModel(frame.getModel2());
            frame.getModel2().fireTableDataChanged();
        }
    }
}
