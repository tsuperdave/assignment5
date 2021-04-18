package com.meritamerica.assignment5.models;

import java.io.*;
import java.util.*;

public class MeritBank {
	
    private static CDOffering[] listOfCDOffers = new CDOffering[0];
    private static AccountHolder[] listOfAccountHolders = new AccountHolder[0];
    static FraudQueue fraudQueue = new FraudQueue();
    private static long nextAccountNumber = 1L;
    static int accountHolderCount = 0;

    public static void addAccountHolder(AccountHolder accountHolder) {
        AccountHolder[] tempArr = new AccountHolder[listOfAccountHolders.length +1];
        for(int i = 0; i < listOfAccountHolders.length; i++) {
            tempArr[i] = listOfAccountHolders[i];
        }
        tempArr[accountHolderCount] = accountHolder;
        listOfAccountHolders = tempArr;
        accountHolderCount++;
    }

    public static AccountHolder[] getAccountHolders() {
        return listOfAccountHolders;
    }
    
    public static AccountHolder getAccountHolder(long id) {
    	for(AccountHolder account: MeritBank.listOfAccountHolders) {
    		if(account == null) return null;
    		if(account.getId() == id) return account;
    	}
    	return null;
    }

    public static CDOffering[] getCDOfferings() {
        return listOfCDOffers;
    }

    public static CDOffering getBestCDOffering(double depositAmount) {
        if(listOfCDOffers == null) return null;
        double stored = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = recursiveFutureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
            if(tempStored > stored)
            {
                stored = tempStored;
                indexBiggest = i;
            }
        }
        return listOfCDOffers[indexBiggest];
    }

    public static CDOffering getSecondBestCDOffering(double depositAmount) {
        if(listOfCDOffers == null) return null;
        double biggest = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        double secondBiggest = recursiveFutureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
        int indexBiggest = 0;
        int indexSecondBiggest = 0;
        for(int i = 1; i < listOfCDOffers.length; i++)
        {
            double tempStored = recursiveFutureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
            if(tempStored > biggest)
            {
                indexSecondBiggest = indexBiggest;
                indexBiggest = i;
            }
            if(tempStored > secondBiggest && tempStored != biggest)
            {
                indexSecondBiggest = i;
            }
        }
        return listOfCDOffers[indexSecondBiggest];
    }

    public static void clearCDOfferings() {
        listOfCDOffers = null;
    }

    public static void setCDOfferings(CDOffering offering) {
    	CDOffering[] offerings = Arrays.copyOf(MeritBank.listOfCDOffers, MeritBank.listOfCDOffers.length + 1);
    	offerings[offerings.length - 1] = offering;
        listOfCDOffers = offerings;
    }

    public static long getNextAccountNumber()
    {
        return nextAccountNumber++;
    }

    public static double totalBalances() {
        double total = 0;
        for(AccountHolder ah: listOfAccountHolders) {
            total += ah.getCombinedBalance();
        }
        return total;
    }

    public static boolean readFromFile(String fileName) {
        Set<String> transactions = new HashSet<String>();
        AccountHolder[] accountHolders = new AccountHolder[0];

        try(Scanner sc = new Scanner(new FileReader(fileName))) {
            setNextAccountNumber(0); // need to reset acct num and base off file
            setNextAccountNumber(Long.parseLong(sc.next()));
            // System.out.println(nextAccountNumber);

            // --- CD OFFERS --- //
            int numOfCDOffers = sc.nextInt();
            listOfCDOffers = new CDOffering[numOfCDOffers];
            // System.out.println(newCDarr.length);

            for(int i = 0; i < numOfCDOffers; i++) {
                listOfCDOffers[i] = CDOffering.readFromString(sc.next());
            }
            // setCDOfferings(listOfCDOffers);

            // --- ACCOUNT HOLDER --- //
            int numOfAHs = Integer.parseInt(sc.next());
            // System.out.println(numOfAHs);

            for(int i = 0; i < numOfAHs; i++) {
                AccountHolder tempAcct = AccountHolder.readFromString(sc.next());
                accountHolders = Arrays.copyOf(accountHolders, accountHolders.length +1);
                accountHolders[accountHolders.length - 1] = tempAcct;
                listOfAccountHolders = accountHolders;

                // --- CHECK ACCT + TXNs --- //
                int numOfCheckAccts = sc.nextInt();
                //System.out.println(numOfCheckAccts);

                for(int j = 0; j < numOfCheckAccts; j++) {
                    CheckingAccount newChk = CheckingAccount.readFromString(sc.next());
                    listOfAccountHolders[i].addCheckingAccount(newChk);

                    int numOfTxns = sc.nextInt();
                    //System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++) {
                        transactions.add(sc.next());
                    }
                }

                // --- SAVINGS ACCT + TXNs --- //
                int numOfSavAccts = sc.nextInt();
                // System.out.println(numOfSavAccts);

                for(int j = 0; j < numOfSavAccts; j++) {
                    SavingsAccount newSav = SavingsAccount.readFromString(sc.next());
                    listOfAccountHolders[i].addSavingsAccount(newSav);

                    int numOfTxns = sc.nextInt();
                    // System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++) {
                        transactions.add(sc.next());
                    }
                }

                // --- CD ACCT + TXNs ---//
                int numOfCDAccts = sc.nextInt();
                // System.out.println(numOfCDAccts);

                for(int j = 0; j < numOfCDAccts; j++) {
                    CDAccount newCD = CDAccount.readFromString(sc.next());
                    listOfAccountHolders[i].addCDAccount(newCD);

                    int numOfTxns = sc.nextInt();
                    // System.out.println(numOfTxns);

                    for(int k = 0; k < numOfTxns; k++) {
                        // Transaction newTxns = Transaction.readFromString(sc.next());
                        transactions.add(sc.next());
                    }
                }

            }
            // --- FRAUD TXNs ---//
            int numInFraudQueue = sc.nextInt();
            // System.out.println(numInFraudQueue);

            for (int j = 0; j < numInFraudQueue; j++) {
                Transaction newFraudTxn = Transaction.readFromString(sc.next());
                fraudQueue.addTransaction(newFraudTxn);
            }

            // System.out.println(transactions.size());
//            for(String txn: transactions) {
//                Transaction newTxn = Transaction.readFromString(txn);
//                if(newTxn.getSourceAccount() == null) {
//                    newTxn.getTargetAccount().addTransaction(newTxn);
//                }
//                else {
//                    newTxn.getTargetAccount().addTransaction(newTxn);
//                    newTxn.getSourceAccount().addTransaction(newTxn);
//                }
//            }
            return true;

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean writeToFile(String fileName) {
        StringBuilder fileWriteStr = new StringBuilder();
        fileWriteStr.append("Next Account Number:").append(System.lineSeparator());
        fileWriteStr.append(nextAccountNumber).append(System.lineSeparator());

        fileWriteStr.append("Number of CD Offers and Offer Info:").append(System.lineSeparator());
        fileWriteStr.append(listOfCDOffers.length).append(System.lineSeparator());
        for(CDOffering cd: listOfCDOffers) fileWriteStr.append(cd.writeToString());

        fileWriteStr.append("Number of Accounts:").append(System.lineSeparator());
        fileWriteStr.append(listOfAccountHolders.length).append(System.lineSeparator());
       // for(AccountHolder ah: listOfAccountHolders) fileWriteStr.append(ah.writeToString());

        try(FileWriter fr = new FileWriter(fileName)) {
                fr.write(fileWriteStr.toString());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static AccountHolder[] sortAccountHolders() {
        Arrays.sort(listOfAccountHolders);

        for(int i = 0; i < listOfAccountHolders.length; i++) {
            System.out.println(listOfAccountHolders[i].getLastName() + ","
                    + listOfAccountHolders[i].getMiddleName() + ","
                    + listOfAccountHolders[i].getFirstName() + ","
                    + listOfAccountHolders[i].getSSN() + "\n" +
                    " has a combined accounts balance of " +
                    listOfAccountHolders[i].getCombinedBalance());
        }

        return listOfAccountHolders;
    }

    private static void setNextAccountNumber(long nextAccountNumber) {
        MeritBank.nextAccountNumber = nextAccountNumber;
    }

    public static double futureValue(double presentValue, double interestRate, int term) {
        return recursiveFutureValue(presentValue,term,interestRate);
    }

    public static double recursiveFutureValue(double amount, double years, double interestRate) {
        double futureVal = amount + (amount * years);
        if(years <= 1 || amount <= 0 || interestRate <= 0) return futureVal;
        return recursiveFutureValue(futureVal, --years, interestRate);
    }

    public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {


        BankAccount sourceAcct = transaction.getSourceAccount();
        BankAccount targetAcct = transaction.getTargetAccount();

        if (sourceAcct == null) {

            // Processing instances of a Withdrawal

            if (transaction instanceof WithdrawTransaction) {
                if (transaction.getAmount() < 0) {
                    throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
                }
                if (targetAcct.getBalance() + -(transaction.getAmount()) < targetAcct.getBalance()) {
                    throw new ExceedsAvailableBalanceException("Insufficient Funds");
                }
//                if (transaction.getAmount() > 1000) {
//                    fraudQueue.addTransaction(transaction);
//                    throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
//                }
                return true;
            }

               //  Processing instances of a Deposit

            if (transaction.getAmount() < 0) {
                throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
            }
//            if (transaction.getAmount() > 1000) {
//                fraudQueue.addTransaction(transaction);
//                throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
//            }
            return true;
        }


            // Processing instances of a Transfer

            if (sourceAcct.getBalance() < transaction.getAmount()) {
                throw new ExceedsAvailableBalanceException("Insufficient Funds");
            }
            if (transaction.getAmount() < 0) {
                throw new NegativeAmountException("Unable to process request. Transaction amount must be greater than $0");
            }
//            if (transaction.getAmount() > 1000) {
//                fraudQueue.addTransaction(transaction);
//                throw new ExceedsFraudSuspicionLimitException("Possible fraud detected. Transaction is being sent to fraud detection services for review");
//            }
            else
            {
                sourceAcct.withdraw(transaction.amount);
                targetAcct.deposit(transaction.amount);
            }
            return true;
    }
    
    public static FraudQueue getFraudQueue()
    {
        return fraudQueue;
    }
    
    public static BankAccount getBankAccount(long accountId) {
        for (AccountHolder ah: listOfAccountHolders) {
            // iterate over checking and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getCheckingAccounts().length; i++) {
                if (accountId == ah.getCheckingAccounts()[i].getAccountNumber()) {
                    return ah.getCheckingAccounts()[i];
                }
            }
            // iterate over savings and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getSavingsAccounts().length; i++) {
                if (accountId == ah.getSavingsAccounts()[i].getAccountNumber())
                {
                    return ah.getSavingsAccounts()[i];
                }
            }
            // iterate over CD accts and match ID to current iteration in any one of the iterations
            for(int i = 0; i < ah.getCDAccounts().length; i++) {
                if (accountId == ah.getCDAccounts()[i].getAccountNumber())
                {
                    return ah.getCDAccounts()[i];
                }
            }
        }
        // return null if acct not found
        return null;
    }
}