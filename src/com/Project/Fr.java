package com.Project;
import com.Project.Dialogs.HeaderDialog;
import com.Project.Dialogs.LinesDialog;
import com.Project.Invoices.HeaderTableModel;
import com.Project.Invoices.InvoiceHeader;
import com.Project.Invoices.InvoiceLines;
import com.Project.Invoices.LinesTableModel;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fr extends JFrame implements ActionListener,ListSelectionListener {
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    private JTable table1;
    private JTable table2;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel NotEditableTex1;
    private JLabel NotEditableTex4;
    private JTextField tex2;
    private JTextField tex3;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JMenuItem loadFile;
    private JMenuItem saveFile;
    private  JMenuBar menuBar;
    private JMenu menu;
    private DateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    private List<InvoiceHeader>InvoiceHeadersList=new ArrayList<>();
    private HeaderTableModel model;
    private LinesTableModel model2;
    private HeaderDialog dialog1;
    private LinesDialog dialog2;
    public Fr(){
        super("Sales Invoices Generator");
        setLayout(null);
        setLocation(200,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,500);
        loadFile=new JMenuItem("Load File",'O');
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L',KeyEvent.ALT_DOWN_MASK));
        saveFile=new JMenuItem("Save file",'S');
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
        menu=new JMenu("File");
        menuBar=new JMenuBar();
        menu.add(loadFile);
        menu.addSeparator();
        menu.add(saveFile);
        menuBar.add(menu);
        add(menuBar);
        setJMenuBar(menuBar);
        loadFile.addActionListener(this);
        saveFile.addActionListener(this);

        loadFile.setActionCommand("L");
        saveFile.setActionCommand("S");

        NotEditableTex1=new JLabel();
        tex2=new JTextField(15);
        tex3=new JTextField(15);
        NotEditableTex4=new JLabel();
        NotEditableTex1.setBounds(140,10,60,20);
        tex2.setBounds(140,35,300,20);
        tex3.setBounds(140,60,300,20);
        NotEditableTex4.setBounds(140,85,60,20);
        btn1=new JButton("Create Invoice");
        btn2=new JButton("Delete Invoice");
        btn3=new JButton("Create Line");
        btn4=new JButton("Delete Line");
        btn3.addActionListener(this);
        btn3.setActionCommand("Create Line");
        btn1.setBounds(45,400,125,30);
        btn2.setBounds(300,400,125,30);
        btn3.setBounds(45,400,125,30);
        btn4.setBounds(300,400,125,30);
        lbl1=new JLabel("Invoices Tabel");
        lbl2=new JLabel("Invoice Number");
        lbl3=new JLabel("Invoice Date");
        lbl4=new JLabel("Customer Name");
        lbl5=new JLabel("Invoice Total");
        lbl6=new JLabel("Invoice Items");
        lbl1.setBounds(10,5,100,30);
        lbl2.setBounds(15,5,100,30);
        lbl3.setBounds(15,30,100,30);
        lbl4.setBounds(15,55,100,30);
        lbl5.setBounds(15,80,100,30);
        lbl6.setBounds(5,5,100,30);
        table1=new JTable();
        table1.getSelectionModel().addListSelectionListener(this);
        scroll1=new JScrollPane(table1);
        scroll1.setBounds(20,40,400,300);
        table2=new JTable();
        //table2.getSelectionModel().addListSelectionListener(this);
        scroll2=new JScrollPane(table2);
        scroll2.setBounds(20,40,400,150);
        JPanel p1=new JPanel(null);
        JPanel p2=new JPanel(null);
        JPanel p3=new JPanel(null);
        p1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p1.setBounds(0,0,500,500);
        p2.setBounds(500,0,480,500);
        p3.setBounds(0,120,480,250);
        p1.add(btn1);
        p1.add(btn2);
        p1.add(lbl1);
        p1.add(scroll1);
        p3.add(scroll2);
        p2.add(lbl2);
        p2.add(lbl3);
        p2.add(lbl4);
        p2.add(lbl5);
        p2.add(lbl6);
        p2.add(btn3);
        p2.add(btn4);
        p2.add(NotEditableTex1);
        p2.add(tex2);
        p2.add(tex3);
        p2.add(NotEditableTex4);
        p2.add(p3);
        p3.add(lbl6);
        add(p1);
        add(p2);
        btn1.addActionListener(this);
        btn1.setActionCommand("Create Invoice");
        btn2.addActionListener(this);
        btn2.setActionCommand("Delete Invoice");
        btn4.addActionListener(this);
        btn4.setActionCommand("Cancel");
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
        int  LineIndex=table2.getSelectedRow();
        InvoiceLines lines=model2.getInvoiceLines().get(LineIndex);
        model2.getInvoiceLines().remove(LineIndex);
        model2.fireTableDataChanged();
        model.fireTableDataChanged();
        NotEditableTex4.setText(""+lines.getHeader().getInvoiceTotal());
        displayInvoices();

    }

    private void createInoviceOk() {
        String CustName =dialog1.getCustomerName().getText();
        String invDateStr = dialog1.getInvoiceDate().getText();
        dialog1.setVisible(false);
        dialog1.dispose();
        dialog1 = null;
        try {
            Date invDate = df.parse(invDateStr);
            int invNum = getNextInvoiceNum();
            InvoiceHeader invoiceHeader = new InvoiceHeader(invNum, CustName, invDate);
            InvoiceHeadersList.add(invoiceHeader);
            model.fireTableDataChanged();

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private int getNextInvoiceNum() {
        int max = 0;
        for (InvoiceHeader header : InvoiceHeadersList) {
            if (header.getInvoiceNum() > max) {
                max = header.getInvoiceNum();
            }
        }
        return max + 1;
    }

    private void createInoviceCancel() {
        dialog1.setVisible(false);
        dialog1.dispose();
        dialog1=null;
    }

    private void ShowNewInvoiceDialog() {
        dialog1=new HeaderDialog(this);
        dialog1.setVisible(true);

    }

    private void LoadFile() throws Exception {
         JOptionPane.showMessageDialog(null,"Select Header File",
                 "ERROR ",JOptionPane.INFORMATION_MESSAGE);
         //Read The First File
        JFileChooser fc=new JFileChooser();
        int result =fc.showOpenDialog(this);
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
                    InvoiceHeadersList.add(invoiceHeader);
                }

                JOptionPane.showMessageDialog(null,"select the Lines file",
                        "ERROR ",JOptionPane.INFORMATION_MESSAGE);

                 //Read the Second File
                JFileChooser fc1=new JFileChooser();
                int result1 =fc.showOpenDialog(this);
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
                    model=new HeaderTableModel(InvoiceHeadersList);
                    table1.setModel(model);
                    table1.validate();
                }
            }catch (FileNotFoundException e) {e.printStackTrace();}
            catch (IOException e) { e.printStackTrace();}
            finally {
                try {
                    fis.close();
                }catch (IOException e){}}
    }}

    public void DeleteInvoice(){
        int invIndex = table1.getSelectedRow();
        InvoiceHeader header = model.getInvoicesList().get(invIndex);
        model.getInvoicesList().remove(invIndex);
        model.fireTableDataChanged();
        model2 = new LinesTableModel(new ArrayList<InvoiceLines>());
        table2.setModel(model2);
        model2.fireTableDataChanged();
        tex2.setText("");
        tex3.setText("");
        NotEditableTex1.setText("");
        NotEditableTex4.setText("");
        displayInvoices();}

    private InvoiceHeader FindNumberOFInvoice(int InvNum){
        InvoiceHeader Header=null;
        for(InvoiceHeader inv: InvoiceHeadersList){
            if(InvNum==inv.getInvoiceNum()){
                Header=inv;
                break;}}
        return Header;}

     private void SaveFile()  {
         String headers = "";
         String lines = "";
         for (InvoiceHeader header : InvoiceHeadersList) {
             headers += header.getDataAsCSV();
             headers += "\n";
             for (InvoiceLines line : header.getLines()) {
                 lines += line.getDataAsCSV();
                 lines += "\n";
             }
         }
         JOptionPane.showMessageDialog(this, "Please select file to save Header File"
                 , "WARNING", JOptionPane.WARNING_MESSAGE);
         JFileChooser fileChooser = new JFileChooser();
         int result = fileChooser.showSaveDialog(this);
         if (result == JFileChooser.APPROVE_OPTION) {
             File headerFile = fileChooser.getSelectedFile();
             try {
                 FileWriter wri = new FileWriter(headerFile);
                 wri.write(headers);
                 wri.flush();
                 wri.close();

                 JOptionPane.showMessageDialog(this, "Please select file to save lines File",
                         "WARNING", JOptionPane.WARNING_MESSAGE);
                 result = fileChooser.showSaveDialog(this);
                 if (result == JFileChooser.APPROVE_OPTION) {
                     File linesFile = fileChooser.getSelectedFile();
                     FileWriter wri2 = new FileWriter(linesFile);
                     wri2.write(lines);
                     wri2.flush();
                     wri2.close();
                 }
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             }}
     }

    private InvoiceHeader findInvoiceByNum(int invNum) {
        InvoiceHeader header = null;
        for (InvoiceHeader inv : InvoiceHeadersList) {
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
        dialog2.setVisible(false);
        dialog2.dispose();
        dialog2 = null;
    }
    private void createLineOK() {
        String itemName = dialog2.getItemNameField().getText();
        String itemCountStr = dialog2.getItemCountField().getText();
        String itemPriceStr = dialog2.getItemPriceField().getText();
        dialog2.setVisible(false);
        dialog2.dispose();
        dialog2 = null;
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = table1.getSelectedRow();
        InvoiceHeader invoice = model.getInvoiceHeadersList().get(headerIndex);

        InvoiceLines invoiceLine = new InvoiceLines(itemName, itemPrice, itemCount, invoice);
        invoice.AddInvoiceLine(invoiceLine);
        model2.fireTableDataChanged();
        model.fireTableDataChanged();
        NotEditableTex4.setText(""+invoice.getInvoiceTotal());
    }
    private void displayNewLineDialog() {
        dialog2=new LinesDialog(this);
        dialog2.setVisible(true);
    }
    private void displayInvoices() {
        System.out.println("-------------------------");
        for (InvoiceHeader header : InvoiceHeadersList) {
            System.out.println(header);
        }
        System.out.println("-------------------------");
    }
    private  void invoiceTableSelectedRow(){
            int RowSelectedIndex=table1.getSelectedRow();
            if(RowSelectedIndex>=0){
InvoiceHeader row=HeaderTableModel.getInvoiceHeadersList().get(RowSelectedIndex);
                tex2.setText(row.getCustomerName());
                tex3.setText(df.format(row.getDate()));
                NotEditableTex1.setText(""+row.getInvoiceNum());
                NotEditableTex4.setText(""+row.getInvoiceTotal());
                ArrayList<InvoiceLines> Lines=row.getLines();
                model2=new LinesTableModel(Lines);
                table2.setModel(model2);
                model2.fireTableDataChanged();

            }
    }
}

