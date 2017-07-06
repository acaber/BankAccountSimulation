/**
 * File Name: InsufficientFunds.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: Simulates a bank account with a GUI that has the capabilities of 
 * 	withdrawing funds from a selected account, depositing funds to a selected account,
 * 	transferring funds to a selected account from the other account, and displaying
 * 	the balance of the selected account.
 */

//user created exception named InsufficientFunds
public class InsufficientFunds extends Exception {

	//field to hold withdrawal amount
	private double amount;
	
	//constructor: assigns amount a value
	public InsufficientFunds(double amount) {
		this.amount = amount;
	}
	
	//returns the withdrawal amount
	public double getAmount() {
		return amount;
	}
}
