package com.Project.Views;
import javax.swing.*;
import java.awt.*;
public class HeaderDialog extends JDialog {
private JTextField CustomerName;
private JTextField InvoiceDate;
private JLabel CustName;
private JLabel InvDate;
private JButton  Ok;
private JButton Cancel;
    public JTextField getCustomerName() {
        return CustomerName;
    }

    public JTextField getInvoiceDate() {
        return InvoiceDate;
    }

    public HeaderDialog(Fr frame) {
        CustomerName=  new JTextField(15);
        InvoiceDate= new JTextField (15);
        CustName=new JLabel ("Customer Name");
        InvDate =new JLabel("Invoice Date");
        Ok=new JButton("OK");
        Cancel=new JButton("Cancel");
        Ok.setActionCommand("CreateInvoiceOk");
        Ok.addActionListener(frame.getLiseners());
        Cancel.setActionCommand("CreateInvoiceCancel");
        Cancel.addActionListener(frame.getLiseners());
        setLocation(100,100);
        setLayout(new GridLayout(3,2));
        add(CustName);
        add(CustomerName);
        add(InvDate);
        add(InvoiceDate);
        add(Ok);
        add(Cancel);
        pack();


    }
}
