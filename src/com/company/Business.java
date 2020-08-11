package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class Business extends LoanP implements Generate{
    public Business(String clientNumber, String clientName, double loanAmount, int years, String loanType) {
        super(clientNumber, clientName, loanAmount, years, loanType);
    }

    public double computeMonthlyPayment(){
        double monthlyRate = (9 / 100.0) / 12;
        int termInMonths = getYears() * 12;
        double monthlyPayment = (getLoanAmount() * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -termInMonths));
        return  monthlyPayment;
    }

    @Override
    public void generateTable(JTable scheduleTable) {
        String[] cols = {"Payment", "Principal", "Interest", "Monthly Payment", "Balance"};
        String[][] data = {{}};

        DefaultTableModel model = new DefaultTableModel(data, cols);
        scheduleTable.setModel(model);

        model.setRowCount(0);

        int termInMonths = getYears() * 12;

        double payment = 0, principal = 0, interest = 0, monthlyPayment = 0, balance = getLoanAmount();
        for (int i = 0; i <= termInMonths; i++) {
            if(i > 0){
                payment = i;
                monthlyPayment = computeMonthlyPayment();
                interest = ((9 / 100.0) / 12) * balance;
                principal = monthlyPayment - interest;
                balance = balance - principal;
            }

            model.addRow(new String[]{String.format("%.2f", payment), String.format("%.2f", principal),
                    String.format("%.2f", interest), String.format("%.2f", monthlyPayment),
                    String.format("%.2f", balance)});
        }

    }
}
