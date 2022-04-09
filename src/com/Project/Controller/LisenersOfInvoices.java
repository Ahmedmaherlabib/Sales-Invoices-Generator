package com.Project.Controller;
import com.Project.InvoicesModel.HeaderTableModel;
import com.Project.InvoicesModel.InvoiceHeader;
import com.Project.InvoicesModel.InvoiceLines;
import com.Project.InvoicesModel.LinesTableModel;
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
    public LisenersOfInvoices(Fr frame){
        this.frame=frame;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        switch (actionEvent.getActionCommand()) {
            case "L":
                try{ LoadFile();}
                catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Wrong File",
                            "ERROR ",JOptionPane.INFORMATION_MESSAGE);}
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
                SaveFile();
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

    private void LoadFile() throws Exception {
        JOptionPane.showMessageDialog(null,"Select Header File",
                "ERROR ",JOptionPane.INFORMATION_MESSAGE);
        //Read The First File
        JFileChooser fc=new JFileChooser();
        int result =fc.showOpenDialog(frame);
        if (result==JFileChooser.APPROVE_OPTION){
            String path=fc.getSelectedFile().getPath();
            if(!path.contains(".csv")){throw new Exception() ;}
            System.out.println("Path selected is " +path);
            FileInputStream fis=null;
            Reader r=null;
            BufferedReader pr=null;

            try {
                fis= new FileInputStream(path);
                r=new InputStreamReader(fis, StandardCharsets.UTF_8);
                pr=new BufferedReader(r);
                String st1= null;//st1 is the data of HeaderTable
                while ((st1= pr.readLine())!=null){
                    String[] Header=st1.split(",");
                    String InvoiceNumber=Header[0];
                    String InvoiceDate=Header[1];
                    String CustomerName=Header[2];
                    int InvNum=Integer.parseInt(InvoiceNumber);
                    Date InvDate =df.parse(InvoiceDate);
                    InvoiceHeader invoiceHeader=new InvoiceHeader(InvNum,CustomerName,InvDate);
                    frame.getInvoiceHeadersList().add(invoiceHeader);
                }

                JOptionPane.showMessageDialog(null,"select the Lines file",
                        "ERROR ",JOptionPane.INFORMATION_MESSAGE);

                //Read the Second File
                JFileChooser fc1=new JFileChooser();
                int result1 =fc.showOpenDialog(frame);
                if (result==JFileChooser.APPROVE_OPTION){
                    String path1=fc.getSelectedFile().getPath();
                    if(!path.contains(".csv")){throw new Exception() ;}
                    System.out.println("Path selected is " +path1);
                    FileInputStream fis1=null;
                    Reader r1=null;
                    BufferedReader pr1=null;
                    try {
                        fis1= new FileInputStream(path1);
                        r1=new InputStreamReader(fis1, StandardCharsets.UTF_8);
                        pr1=new BufferedReader(r1);
                        String st11= null;  //st11 is data of LinesTable
                        String fg1=",";
                        while ((st11= pr1.readLine())!=null){
                            String[] lines=st11.split(",");
                            String InvNumLinesText =lines[0];
                            String ItemName =lines[1];
                            String ItemPriceText=lines[2];
                            String ItemCountPrice=lines[3];
                            int InvNum=Integer.parseInt(InvNumLinesText);
                            double InvPrice=Double.parseDouble(ItemPriceText);
                            int ItemCount=Integer.parseInt(ItemCountPrice);
                            InvoiceHeader Header=FindNumberOFInvoice(InvNum);
                            InvoiceLines invoiceLines=new InvoiceLines(ItemName,InvPrice,ItemCount,Header);
                            Header.getLines().add(invoiceLines);
                        }
                    }catch (FileNotFoundException e) {e.printStackTrace();}
                    catch (IOException e) { e.printStackTrace();}
                    finally {
                        try {
                            fis1.close();
                        }catch (IOException e){}}
                frame.setModel(new HeaderTableModel(frame.getInvoiceHeadersList()));
                  frame.getTable1().setModel(frame.getModel());
                    frame.getTable1().validate();
                }
            }catch (FileNotFoundException e) {e.printStackTrace();}
            catch (IOException e) { e.printStackTrace();}
            finally {
                try {
                    fis.close();
                }catch (IOException e){}}
            displayInvoices();
        }}

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

    private InvoiceHeader FindNumberOFInvoice(int InvNum){
        InvoiceHeader Header=null;
        for(InvoiceHeader inv: frame.getInvoiceHeadersList()){
            if(InvNum==inv.getInvoiceNum()){
                Header=inv;
                break;}}
        return Header;}

    private void SaveFile()  {
        String headers = "";
        String lines = "";
        for (InvoiceHeader header : frame.getInvoiceHeadersList()) {
            headers += header.getDataAsCSV();
            headers += "\n";
            for (InvoiceLines line : header.getLines()) {
                lines += line.getDataAsCSV();
                lines += "\n";
            }
        }
        JOptionPane.showMessageDialog(frame, "Please select file to save Header File"
                , "WARNING", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter wri = new FileWriter(headerFile);
                wri.write(headers);
                wri.flush();
                wri.close();

                JOptionPane.showMessageDialog(frame, "Please select file to save lines File",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter wri2 = new FileWriter(linesFile);
                    wri2.write(lines);
                    wri2.flush();
                    wri2.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: "
                        + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }}
    }

    private InvoiceHeader findInvoiceByNum(int invNum) {
        InvoiceHeader header = null;
        for (InvoiceHeader inv : frame.getInvoiceHeadersList()) {
            if (invNum == inv.getInvoiceNum()) {
                header = inv;
                break;
            }
        }
        return header;


    }
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
