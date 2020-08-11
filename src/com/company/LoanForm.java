/*
 * Created by JFormDesigner on Tue Aug 11 12:35:19 PDT 2020
 */

package com.company;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.ResultSet;

/**
 * @author Phan Vo
 */
public class LoanForm extends JFrame {
    public LoanForm() {
        initComponents();
        initLoanTypeCb();
        initTable();
    }

    private void addBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        String clientNumber = clientNumberTf.getText().trim();
        String clientName = clientNameTf.getText().trim();
        String loanAmount = loanAmountTf.getText().trim();
        String years = yearsTf.getText().trim();
        String loanType = loanTypeCb.getSelectedItem().toString().trim();

        boolean isValid = isValidInputs(clientNumber, clientName, loanAmount, years, loanType);
        if (!isValid){
            return;
        }

        LoanP newLoan = new LoanP(clientNumber, clientName, Double.parseDouble(loanAmount),
                Integer.parseInt(years), loanType);

        MySQLAccess mySQLAccess = null;
        try {
            mySQLAccess = new MySQLAccess("loan");

            if (isExisting(mySQLAccess, clientNumber)){
                JOptionPane.showMessageDialog(null, "Client number cannot be duplicate!");
                return;
            }

            // insert query
            mySQLAccess.executeUpdate("insert into loantable values (?, ?, ?, ?, ?)",
                    new String[]{newLoan.getClientNumber(), newLoan.getClientName(),
                            String.valueOf(newLoan.getLoanAmount()), String.valueOf(newLoan.getYears()), loanType});

            JOptionPane.showMessageDialog(null, "Record added");

            refreshTable(mySQLAccess);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            if(mySQLAccess != null){
                mySQLAccess.closeConnection();
            }
        }
    }

    private boolean isValidInputs(String clientNumber, String clientName, String loanAmount,
                                  String years, String loanType){
        if(clientNumber.trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Client number cannot be empty!");
            return false;
        } else if (!isIntegerNum(clientNumber.trim())){
            JOptionPane.showMessageDialog(null, "Client number must be a number!");
            return false;
        }

        if(clientName.trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Client name cannot be empty!");
            return false;
        }

        if(loanAmount.trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Loan amount cannot be empty!");
            return false;
        } else if (!isDoubleNum(loanAmount.trim())){
            JOptionPane.showMessageDialog(null, "Loan amount must be a number!");
            return false;
        }

        if(years.trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Years cannot be empty!");
            return false;
        } else if (!isIntegerNum(years.trim())){
            JOptionPane.showMessageDialog(null, "Years must be a number!");
            return false;
        } else if (Integer.parseInt(years.trim()) < 1){
            JOptionPane.showMessageDialog(null, "Years must be larger than 1!");
            return false;
        }

        return true;
    }

    public boolean isDoubleNum(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean isIntegerNum(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private void editBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        String clientNumber = clientNumberTf.getText().trim();
        String clientName = clientNameTf.getText().trim();
        String loanAmount = loanAmountTf.getText().trim();
        String years = yearsTf.getText().trim();
        String loanType = loanTypeCb.getSelectedItem().toString().trim();

        boolean isValid = isValidInputs(clientNumber, clientName, loanAmount, years, loanType);
        if (!isValid){
            return;
        }

        LoanP newLoan = new LoanP(clientNumber, clientName, Double.parseDouble(loanAmount),
                Integer.parseInt(years), loanType);

        MySQLAccess mySQLAccess = null;
        try {
            mySQLAccess = new MySQLAccess("loan");

            if (!isExisting(mySQLAccess, clientNumber)){
                JOptionPane.showMessageDialog(null, "Client number does not exist!");
                return;
            }

            // update query
            mySQLAccess.executeUpdate("update loantable set clientname = ?, loanamount = ?, years = ?, " +
                            "loantype = ? where clientno = ?", new String[]{newLoan.getClientName(),
                    String.valueOf(newLoan.getLoanAmount()), String.valueOf(newLoan.getYears()),
                    loanType, newLoan.getClientNumber()});

            JOptionPane.showMessageDialog(null, "Record edited");

            refreshTable(mySQLAccess);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            if(mySQLAccess != null){
                mySQLAccess.closeConnection();
            }
        }
    }

    private void loanTableMouseClicked(MouseEvent e) {
        // TODO add your code here
        DefaultTableModel model = (DefaultTableModel) loanTable.getModel();

        int index = loanTable.getSelectedRow();

        clientNumberTf.setText(model.getValueAt(index, 0).toString());
        clientNameTf.setText(model.getValueAt(index, 1).toString());
        loanAmountTf.setText(model.getValueAt(index, 2).toString());
        yearsTf.setText(model.getValueAt(index, 3).toString());
        loanTypeCb.setSelectedItem(model.getValueAt(index, 4).toString());

        if(loanTypeCb.getSelectedItem().toString().equalsIgnoreCase("business")){
            Business selectedItem = new Business(clientNumberTf.getText(), clientNameTf.getText(),
                    Double.parseDouble(loanAmountTf.getText()),
                    Integer.parseInt(yearsTf.getText()), loanTypeCb.getSelectedItem().toString());
            selectedItem.generateTable(scheduleTable);
            monthlyPaymentTf.setText(String.format("%.2f", selectedItem.computeMonthlyPayment()));
        } else if(loanTypeCb.getSelectedItem().toString().equalsIgnoreCase("personal")){
            Personal selectedItem = new Personal(clientNumberTf.getText(), clientNameTf.getText(),
                    Double.parseDouble(loanAmountTf.getText()),
                    Integer.parseInt(yearsTf.getText()), loanTypeCb.getSelectedItem().toString());
            selectedItem.generateTable(scheduleTable);
            monthlyPaymentTf.setText(String.format("%.2f", selectedItem.computeMonthlyPayment()));
        }

    }

    private void deleteBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        String clientNumber = clientNumberTf.getText().trim();

        MySQLAccess mySQLAccess = null;
        try {
            mySQLAccess = new MySQLAccess("loan");

            if (!isExisting(mySQLAccess, clientNumber)){
                JOptionPane.showMessageDialog(null, "Client number does not exist!");
                return;
            }

            int input = JOptionPane.showConfirmDialog(null,
                    "Do you really want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);

            // 0=yes, 1=no
            if(input == 0){
                // delete query
                mySQLAccess.executeUpdate("delete from loantable where clientno = ?",
                        new String[]{clientNumber});

                JOptionPane.showMessageDialog(null, "Record deleted");

                refreshTable(mySQLAccess);
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            if(mySQLAccess != null){
                mySQLAccess.closeConnection();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - jack
        label1 = new JLabel();
        clientNumberTf = new JTextField();
        label2 = new JLabel();
        clientNameTf = new JTextField();
        label3 = new JLabel();
        loanAmountTf = new JTextField();
        label4 = new JLabel();
        yearsTf = new JTextField();
        label5 = new JLabel();
        loanTypeCb = new JComboBox();
        scrollPane1 = new JScrollPane();
        loanTable = new JTable();
        scrollPane2 = new JScrollPane();
        scheduleTable = new JTable();
        addBtn = new JButton();
        editBtn = new JButton();
        deleteBtn = new JButton();
        label6 = new JLabel();
        monthlyPaymentTf = new JTextField();

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

        //---- clientNumberTf ----
        clientNumberTf.setColumns(20);
        contentPane.add(clientNumberTf, "cell 1 0");

        //---- label2 ----
        label2.setText("Enter the client name:");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(clientNameTf, "cell 1 1");

        //---- label3 ----
        label3.setText("Enter the customer loan amount:");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(loanAmountTf, "cell 1 2");

        //---- label4 ----
        label4.setText("Enter the number of years to pay");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(yearsTf, "cell 1 3");

        //---- label5 ----
        label5.setText("Enter the loan type:");
        contentPane.add(label5, "cell 0 4");
        contentPane.add(loanTypeCb, "cell 1 4");

        //======== scrollPane1 ========
        {

            //---- loanTable ----
            loanTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loanTableMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(loanTable);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(scheduleTable);
        }
        contentPane.add(scrollPane2, "cell 1 5");

        //---- addBtn ----
        addBtn.setText("Add");
        addBtn.addActionListener(e -> addBtnActionPerformed(e));
        contentPane.add(addBtn, "cell 0 6");

        //---- editBtn ----
        editBtn.setText("Edit");
        editBtn.addActionListener(e -> editBtnActionPerformed(e));
        contentPane.add(editBtn, "cell 0 6");

        //---- deleteBtn ----
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(e -> deleteBtnActionPerformed(e));
        contentPane.add(deleteBtn, "cell 0 6");

        //---- label6 ----
        label6.setText("Monthly Payment");
        contentPane.add(label6, "cell 1 6");

        //---- monthlyPaymentTf ----
        monthlyPaymentTf.setColumns(10);
        monthlyPaymentTf.setEnabled(false);
        contentPane.add(monthlyPaymentTf, "cell 1 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - jack
    private JLabel label1;
    private JTextField clientNumberTf;
    private JLabel label2;
    private JTextField clientNameTf;
    private JLabel label3;
    private JTextField loanAmountTf;
    private JLabel label4;
    private JTextField yearsTf;
    private JLabel label5;
    private JComboBox loanTypeCb;
    private JScrollPane scrollPane1;
    private JTable loanTable;
    private JScrollPane scrollPane2;
    private JTable scheduleTable;
    private JButton addBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JLabel label6;
    private JTextField monthlyPaymentTf;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private void initLoanTypeCb(){
        loanTypeCb.addItem("Business");
        loanTypeCb.addItem("Personal");
    }

    private void initTable(){
        String[] cols = {"Number", "Name", "Amount", "Years", "Type of Loan"};
        String[][] data = {{}};

        DefaultTableModel model = new DefaultTableModel(data, cols);
        loanTable.setModel(model);

        // populate data into the table
        MySQLAccess mySQLAccess = null;
        try {
            mySQLAccess = new MySQLAccess("loan");

            refreshTable(mySQLAccess);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            if(mySQLAccess != null){
                mySQLAccess.closeConnection();
            }
        }
    }

    private void refreshTable(MySQLAccess mySQLAccess) throws Exception{
        ResultSet resultSet = mySQLAccess.executeQuery("select * from loantable");

        DefaultTableModel model = (DefaultTableModel) loanTable.getModel();
        model.setRowCount(0);

        while (resultSet.next()){
            model.addRow(new String[]{resultSet.getString("clientno"),
                    resultSet.getString("clientname"), resultSet.getString("loanamount"),
                    resultSet.getString("years"),resultSet.getString("loantype")});
        }

        resetTextFields();
    }

    private boolean isExisting(MySQLAccess mySQLAccess, String str) throws Exception{
        ResultSet resultSet = mySQLAccess.executeQuery("select * from loantable");

        while (resultSet.next()){
            if(str.equalsIgnoreCase(resultSet.getString("clientno"))){
                return true;
            }
        }

        return false;
    }

    public void resetTextFields(){
        clientNumberTf.setText("");
        clientNameTf.setText("");
        loanAmountTf.setText("");
        yearsTf.setText("");
        monthlyPaymentTf.setText("");
    }
}
