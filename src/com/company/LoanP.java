package com.company;

public class LoanP {
    private String clientNumber;
    private String clientName;
    private double loanAmount;
    private int years;
    private String loanType;

    public LoanP(String clientNumber, String clientName, double loanAmount, int years, String loanType){
        this.clientNumber = clientNumber;
        this.clientName = clientName;
        this.loanAmount = loanAmount;
        this.years = years;
        this.loanType = loanType;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getYears() {
        return years;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
