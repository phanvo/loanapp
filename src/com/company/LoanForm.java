/*
 * Created by JFormDesigner on Tue Aug 11 12:35:19 PDT 2020
 */

package com.company;

import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Phan Vo
 */
public class LoanForm extends JFrame {
    public LoanForm() {
        initComponents();
        initLoanTypeCb();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - jack
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        label5 = new JLabel();
        loanTypeCb = new JComboBox();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        button3 = new JButton();
        button2 = new JButton();
        button1 = new JButton();
        label6 = new JLabel();
        textField5 = new JTextField();

        //======== this ========
        setTitle("Loan Projection");
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter the client number:");
        contentPane.add(label1, "cell 0 0");

        //---- textField1 ----
        textField1.setColumns(20);
        contentPane.add(textField1, "cell 1 0");

        //---- label2 ----
        label2.setText("Enter the client name:");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(textField2, "cell 1 1");

        //---- label3 ----
        label3.setText("Enter the customer loan amount:");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(textField3, "cell 1 2");

        //---- label4 ----
        label4.setText("Enter the number of years to pay");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(textField4, "cell 1 3");

        //---- label5 ----
        label5.setText("Enter the loan type:");
        contentPane.add(label5, "cell 0 4");
        contentPane.add(loanTypeCb, "cell 1 4");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, "cell 1 5");

        //---- button3 ----
        button3.setText("Add");
        contentPane.add(button3, "cell 0 6");

        //---- button2 ----
        button2.setText("Edit");
        contentPane.add(button2, "cell 0 6");

        //---- button1 ----
        button1.setText("Delete");
        contentPane.add(button1, "cell 0 6");

        //---- label6 ----
        label6.setText("Monthly Payment");
        contentPane.add(label6, "cell 1 6");

        //---- textField5 ----
        textField5.setColumns(10);
        contentPane.add(textField5, "cell 1 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - jack
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JLabel label5;
    private JComboBox loanTypeCb;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton button3;
    private JButton button2;
    private JButton button1;
    private JLabel label6;
    private JTextField textField5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initLoanTypeCb(){
        loanTypeCb.addItem("Business");
        loanTypeCb.addItem("Personal");
    }
}
