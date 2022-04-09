package com.Project.Views;
import com.Project.Controller.LisenersOfInvoices;
import com.Project.Controller.FileOperations;
import com.Project.InvoicesModel.HeaderTableModel;
import com.Project.InvoicesModel.InvoiceHeader;
import com.Project.InvoicesModel.LinesTableModel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.JTable;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Fr extends JFrame  {
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
    private LisenersOfInvoices liseners=new LisenersOfInvoices(this);
    private FileOperations operations=new FileOperations(this);

    public void setDialog1(HeaderDialog dialog1) {
        this.dialog1 = dialog1;
    }

    public void setDialog2(LinesDialog dialog2) {
        this.dialog2 = dialog2;
    }

    public void setModel(HeaderTableModel model) {
        this.model = model;
    }

    public void setModel2(LinesTableModel model2) {
        this.model2 = model2;
    }

    private HeaderDialog dialog1;
    private LinesDialog dialog2;

    public JScrollPane getScroll1() {
        return scroll1;
    }

    public JScrollPane getScroll2() {
        return scroll2;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTable getTable2() {
        return table2;
    }

    public JLabel getLbl1() {
        return lbl1;
    }

    public JLabel getLbl2() {
        return lbl2;
    }

    public JLabel getLbl3() {
        return lbl3;
    }

    public JLabel getLbl4() {
        return lbl4;
    }

    public JLabel getLbl5() {
        return lbl5;
    }

    public JLabel getLbl6() {
        return lbl6;
    }

    public JLabel getNotEditableTex1() {
        return NotEditableTex1;
    }

    public JLabel getNotEditableTex4() {
        return NotEditableTex4;
    }

    public JTextField getTex2() {
        return tex2;
    }

    public JTextField getTex3() {
        return tex3;
    }

    public JButton getBtn1() {
        return btn1;
    }

    public JButton getBtn2() {
        return btn2;
    }

    public JButton getBtn3() {
        return btn3;
    }

    public JButton getBtn4() {
        return btn4;
    }

    public JMenuItem getLoadFile() {
        return loadFile;
    }

    public JMenuItem getSaveFile() {
        return saveFile;
    }

    public JMenu getMenu() {
        return menu;
    }

    public DateFormat getDf() {
        return df;
    }

    public List<InvoiceHeader> getInvoiceHeadersList() {
        return InvoiceHeadersList;
    }

    public HeaderTableModel getModel() {
        return model;
    }

    public LinesTableModel getModel2() {
        return model2;
    }

    public HeaderDialog getDialog1() {
        return dialog1;
    }

    public LinesDialog getDialog2() {
        return dialog2;
    }

    public LisenersOfInvoices getLiseners() {
        return liseners;
    }

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
        loadFile.addActionListener(liseners);
        saveFile.addActionListener(liseners);

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
        btn3.addActionListener(liseners);
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
        table1.getSelectionModel().addListSelectionListener(liseners);
        scroll1=new JScrollPane(table1);
        scroll1.setBounds(20,40,400,300);
        table2=new JTable();
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
        btn1.addActionListener(liseners);
        btn1.setActionCommand("Create Invoice");
        btn2.addActionListener(liseners);
        btn2.setActionCommand("Delete Invoice");
        btn4.addActionListener(liseners);
        btn4.setActionCommand("Cancel");
    }

}

