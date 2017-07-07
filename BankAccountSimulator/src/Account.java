/**
 * File Name: Account.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: This class holds all the calculations to perform tasks
 * 	requested by the buttons
 */

public class Account{
	
	//holds balance
	private double balance;
	
	//holds serviceCharge amount
	private static double serviceCharge = 0;
	
	//holds the current number of withdrawals
	private static int withdrawalAmount = 0;
	
	//holds account name
	private String name;
	
	//constructor: assigns balance a value
	public Account(String name, double balance) {
		this.name = name;
		this.balance = balance;
	}
	
	//withdraw method: throws insufficient funds exception
	public double withdrawFromAccount(double withdrawAmount) throws InsufficientFunds {
		
		//validates that the withdrawal amount is less than the balance in the account
		if(withdrawAmount <= balance) {
			
			//increments the current number of withdrawals
			withdrawalAmount++;
			
			//calls checkWithdrawLimit method to see if service charge is incurred
			checkWithdrawLimit(withdrawalAmount);
			
			//makes sure that there is enough money in the account to withdraw from
			if((withdrawAmount + serviceCharge) > balance)
				throw new InsufficientFunds(withdrawAmount);
			else
				balance = (balance - withdrawAmount) - serviceCharge;
		}
		else
			//throws exception if there is not enough money in account
			throw new InsufficientFunds(withdrawAmount);
		
		return balance;
	}
	
	//deposit method
	public double depositToAccount(double depositAmount) {
		balance += depositAmount; 
		return balance;
	}
	
	//transfer method
	public void transfer(Account to, Account from, double amount) throws InsufficientFunds {
		
		//makes sure there is enough money in account to complete transfer
		if(amount <= from.balance) {
			from.balance = (from.balance - amount);
			to.balance = (to.balance + amount);
		}
		
		else
			//throws exception if there is not enough money in account
			throw new InsufficientFunds(amount);
	
	}
	
	//this method adds the service charge if the user attempts to withdraw more than 4 times
	public void checkWithdrawLimit(int withdrawTimes) {
		if(withdrawTimes > 4)
			serviceCharge = 1.5;
		else
			serviceCharge = 0;
	}
	
	//this method returns the service charge
	public double getServiceCharge() {
		return serviceCharge;
	}
	
	//this method returns the account balance
	public double getBalance() {
		return balance;
	}
	
	//this method returns the name of the account
	public String getName(){
		return name;
	}
	
}
