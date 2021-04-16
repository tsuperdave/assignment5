package com.meritamerica.assignment5.models;

import javax.validation.constraints.NotBlank;

public class AccountHolder implements Comparable<AccountHolder> {
	
    private static final double BALANCE_LIMIT = 250000;
    final double FRAUD_THRESHOLD = 1000;
    private static long ID = 1;
    
    private long id;
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotBlank(message = "SSN cannot be empty")
    private String ssn;
    
    private CheckingAccount[] checkingAccountList = new CheckingAccount[0];
    private SavingsAccount[] savingsAccountList = new SavingsAccount[0];
    private CDAccount[] cdAccountList = new CDAccount[0];

    AccountHolder() { 	
    	this.id = AccountHolder.ID;
    	AccountHolder.ID++;
        this.checkingAccountList = new CheckingAccount[0];
        this.savingsAccountList = new SavingsAccount[0];
        this.cdAccountList = new CDAccount[0];
    }

    AccountHolder(String firstName, String middleName, String lastName, String ssn) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ssn = ssn;       
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }
    
    /* CHECKING ACCOUNT */
    public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        return this.addCheckingAccount(new CheckingAccount(openingBalance));
    }

    public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        if ((this.getCheckingBalance() + (this.getCombinedBalance() - this.getCDBalance()) >= BALANCE_LIMIT)) {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        } else if(checkingAccount.getBalance() > FRAUD_THRESHOLD) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }

        // checkingAccount.addTransaction(new DepositTransaction(checkingAccount, checkingAccount.getBalance()));

        CheckingAccount[] tempArr = new CheckingAccount[this.checkingAccountList.length + 1];
        System.arraycopy(this.checkingAccountList, 0, tempArr, 0, this.checkingAccountList.length);
        tempArr[tempArr.length - 1] = checkingAccount;
        this.checkingAccountList = tempArr;

        return checkingAccount;
    }

    public CheckingAccount[] getCheckingAccounts() {
        return checkingAccountList;
    }

    public int getNumberOfCheckingAccounts() {
        int numOfAccounts = 0;
        for(CheckingAccount i: checkingAccountList) {
            if(i != null) {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    public double getCheckingBalance() {
        double sum = 0;
        for(CheckingAccount chkAcct: this.checkingAccountList) {
            sum += chkAcct.balance;
        }

        return sum;
    }

    /* SAVINGS ACCOUNT */
    public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        return this.addSavingsAccount(new SavingsAccount(openingBalance));
    }

    public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException {
        if (this.getSavingsBalance() + (this.getCombinedBalance() - this.getCDBalance()) >= BALANCE_LIMIT) {
            throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit. Unable to open new account at this time");
        } else if(savingsAccount.getBalance() > FRAUD_THRESHOLD){
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }

        // savingsAccount.addTransaction(new DepositTransaction(savingsAccount, savingsAccount.getBalance()));

        SavingsAccount[] tempArr = new SavingsAccount[this.savingsAccountList.length + 1];
        System.arraycopy(this.savingsAccountList, 0, tempArr, 0, this.savingsAccountList.length);
        tempArr[tempArr.length - 1] = savingsAccount;
        this.savingsAccountList = tempArr;
        return savingsAccount;

    }

    public SavingsAccount[] getSavingsAccounts() {
        return savingsAccountList;
    }

    public int getNumberOfSavingsAccounts() {
        int numOfAccounts = 0;
        for(SavingsAccount i: savingsAccountList)
        {
            if(i != null)
            {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    public double getSavingsBalance() {
        double sum = 0;
        for(SavingsAccount savAcct: savingsAccountList) {
            sum += savAcct.balance;
        }
        return sum;
    }

    /* CD ACCOUNT */
    public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException, NegativeAmountException {
        if(offering == null) return null;

        CDAccount newCDAccount = new CDAccount(offering, openingBalance);
        DepositTransaction transaction = new DepositTransaction(newCDAccount, openingBalance);

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        CDAccount[] tempArr = new CDAccount[this.cdAccountList.length + 1];
        System.arraycopy(this.cdAccountList, 0, tempArr, 0, this.cdAccountList.length + 1);
        tempArr[tempArr.length - 1] = newCDAccount;
        this.cdAccountList = tempArr;
        return newCDAccount;
    }

    public CDAccount addCDAccount(CDAccount cdAccount) throws ExceedsFraudSuspicionLimitException, NegativeAmountException {

        DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());

        try {
            MeritBank.processTransaction(transaction);
        }
        catch (ExceedsFraudSuspicionLimitException e) {
            throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
        }
        catch (NegativeAmountException e) {
            throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        CDAccount[] tempArr = new CDAccount[this.cdAccountList.length + 1];
        System.arraycopy(this.cdAccountList, 0, tempArr, 0, this.cdAccountList.length);
        tempArr[tempArr.length - 1] = cdAccount;
        this.cdAccountList = tempArr;
        return cdAccount;
    }

    public CDAccount[] getCDAccounts() {
        return cdAccountList;
    }

    public int getNumberOfCDAccounts() {
        int numOfAccounts = 0;
        for(CDAccount i: cdAccountList)
        {
            if(i != null)
            {
                numOfAccounts++;
            }
        }
        return numOfAccounts;
    }

    public double getCDBalance() {
        double sum = 0;
        for(CDAccount cdAcct: cdAccountList) {
            sum += cdAcct.balance;
        }
        return sum;
    }

    public static AccountHolder readFromString(String accountHolderData) {
        String[] tempArr = accountHolderData.split(",");
        String tempFirstName = "", tempMidName = "", TempLastName = "", tempSSN = "";

        tempFirstName += tempArr[2];
        tempMidName += tempArr[1];
        TempLastName += tempArr[0];
        tempSSN += tempArr[3];

        return new AccountHolder(tempFirstName, tempMidName, TempLastName, tempSSN);
    }

    // TODO -- fix file writer (txn's)
    // may need to swap loops to for i loops
//    public String writeToString() {
//        StringBuilder sb = new StringBuilder(this.lastName).append(",").append(this.middleName).append(",").append(this.firstName).append(",").append(this.ssn).append(System.lineSeparator());
//        sb.append(this.getNumberOfCheckingAccounts()).append(System.lineSeparator());
//
//        for(CheckingAccount chk: this.checkingAccountList){
//            sb.append(chk.writeToString()).append(System.lineSeparator());
//            sb.append(chk.getTransactions().size()).append(System.lineSeparator());
//            for(Transaction txn: chk.getTransactions()) sb.append(txn.writeToString()).append(System.lineSeparator());
//        }
//
//        sb.append(this.getNumberOfSavingsAccounts()).append(System.lineSeparator());
//        for(SavingsAccount sav: this.savingsAccountList){
//            sb.append(sav.getTransactions()).append(System.lineSeparator());
//            sb.append(sav.writeToString()).append(System.lineSeparator());
//            for(Transaction txn: sav.getTransactions()) sb.append(txn.writeToString()).append(System.lineSeparator());
//        }
//
//        sb.append(this.getNumberOfCDAccounts()).append(System.lineSeparator());
//        for(CDAccount cd: this.cdAccountList){
//            sb.append(cd.getTransactions()).append(System.lineSeparator());
//            sb.append(cd.writeToString()).append(System.lineSeparator());
//            for(Transaction txn: cd.getTransactions()) sb.append(txn.writeToString()).append(System.lineSeparator());
//        }
//
//        return sb.toString();
//    }

    public double getCombinedBalance() {
        return this.getCheckingBalance() + this.getSavingsBalance() + this.getCDBalance();
    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder) {
        return Double.compare(this.getCombinedBalance(), otherAccountHolder.getCombinedBalance());
        /*
         if(this.getCombinedBalance() == otherAccountHolder.getCombinedBalance()) return 0;
        else if(this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) return -1;
        else return 1;
         */
    }

}