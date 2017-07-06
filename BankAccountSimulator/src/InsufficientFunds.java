/**
 * File Name: InsufficientFunds.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: Programmer created exception that is thrown when
 * 	a bank account has insufficient funds for withdrawals and transfers
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
