package com.Project.Controller;
import com.Project.Controller.LisenersOfInvoices;
import com.Project.InvoicesModel.HeaderTableModel;
import com.Project.InvoicesModel.InvoiceHeader;
import com.Project.InvoicesModel.InvoiceLines;
import com.Project.Views.Fr;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FileOperations{
    private Fr frame;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
private LisenersOfInvoices lisenersOfInvoices;

    public FileOperations(Fr frame) {
        this.frame = frame;
    }
       public void SaveFile(Fr frame)  {
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


    public void LoadFile(Fr frame) throws Exception {JOptionPane.showMessageDialog(
            null,"Select Header File",
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
                if (result1==JFileChooser.APPROVE_OPTION){
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
                        while ((st11= pr1.readLine())!=null){
                            String[] lines=st11.split(",");
                            String InvNumLinesText =lines[0];
                            String ItemName =lines[1];
                            String ItemPriceText=lines[2];
                            String ItemCountPrice=lines[3];
                            int InvNum=Integer.parseInt(InvNumLinesText);
                            double InvPrice=Double.parseDouble(ItemPriceText);
                            int ItemCount=Integer.parseInt(ItemCountPrice);
                            InvoiceHeader Header=findInvoiceByNum(InvNum,frame);
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
            catch (Exception e) { e.printStackTrace();}
            finally {
                try {
                    fis.close();
                }catch (IOException e){}}
            displayInvoices1(frame);


    }}
    private InvoiceHeader findInvoiceByNum(int invNum,Fr frame) {
        InvoiceHeader header = null;
        for (InvoiceHeader inv : frame.getInvoiceHeadersList()) {
            if (invNum == inv.getInvoiceNum()) {
                header = inv;
                break;
            }
        }
        return header;


    }
    private void displayInvoices1(Fr frame) {
        System.out.println("-------------------------");
        for (InvoiceHeader header : frame.getInvoiceHeadersList()) {
            System.out.println(header);
        }
        System.out.println("-------------------------");
    }
}
