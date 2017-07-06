/**
 * File Name: Account.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: Simulates a bank account with a GUI that has the capabilities of 
 * 	withdrawing funds from a selected account, depositing funds to a selected account,
 * 	transferring funds to a selected account from the other account, and displaying
 * 	the balance of the selected account.
 */

public class Account{
	
	//holds balance
	private double balance;
	
	//holds serviceCharge amount
	private static double serviceCharge = 0;
	
	//holds the current number of withdrawals
	private static int withdrawalAmount = 0;
	
	//constructor: assigns balance a value
	public Account(double balance) {
		this.balance = balance;
	}
	
	//withdraw method: throws insufficient funds exception
	public double withdrawAccount(double withdrawAmount) throws InsufficientFunds {
		
		//validates that the withdrawal amount is less than the balance in the account
		if(withdrawAmount <= balance) {
			
			//increments the current number of withdrawals
			withdrawalAmount++;
			
			//calls checkWithdrawLimit method to see if service charge is incurred
			checkWithdrawLimit(withdrawalAmount);
			
			//makes sure that there is enough money in account to withdraw from
			if((withdrawAmount + serviceCharge) > balance)
				throw new InsufficientFunds(withdrawAmount);
			else
				balance = (balance - withdrawAmount) - serviceCharge;
		}
		else
			throw new InsufficientFunds(withdrawAmount);
		
		return balance;
	}
	
	//deposit method
	public double depositAccount(double depositAmount) {
		balance += depositAmount; 
		return balance;
	}
	
	//transfer method
	public void transfer(Account to, Account from, double amount) throws InsufficientFunds {
		
		//will throw insufficient funds exception if the withdrawal amount exceeds the balance
		if(amount <= from.balance) {
			from.balance = transferWithdraw(from, amount);
			to.balance = transferDeposit(to, amount);
		}
		
		else
			throw new InsufficientFunds(amount);
	
	}
	
	//transfer method for depositing money into selected account 
	public double transferDeposit(Account to, double amount) {
		return(to.balance + amount);
	}
	
	//transfer method for withdrawing money out of other account
	public double transferWithdraw(Account from, double amount) {
		return (from.balance - amount);
	}
	
	//adds service charge if user attempts to withdraw more than 4 times
	public void checkWithdrawLimit(int withdrawTimes) {
		if(withdrawTimes > 4)
			serviceCharge = 1.5;
		else
			serviceCharge = 0;
	}
	
	//get service charge method
	public double getServiceCharge() {
		return serviceCharge;
	}
	
	//get balance method
	public double getBalance() {
		return balance;
	}

}
