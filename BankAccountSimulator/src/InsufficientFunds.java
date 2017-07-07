import javax.swing.JOptionPane;

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
	
	
	//this method displays the checking withdrawal error
	public void displayWithdrawalError(Account type, String name) {
			
		//opens JOptionPane to output the error
		JOptionPane.showMessageDialog(null, String.format(	
				"Error: Insufficient funds available to complete withdrawal. "
						+ "%n%nAvaiable %s account balance: $%,.2f "
						+ "%nWithdrawal amount request: $%,.2f "
						+ "%nService fee: $%.2f"
						+ "%nTotal withdrawal amount: $%,.2f",
						name, type.getBalance(), amount, type.getServiceCharge(), 
						(amount + type.getServiceCharge())));
		}
	
		
		//this method displays the checking transfer error
		public void displayTransferError(Account type, String name) {
			
			//opens JOptionPane to display the error
			JOptionPane.showMessageDialog(null, String.format(
					"Error: Insufficient funds available in savings account to complete transfer. "
							+ "%n%nAvaiable %s account balance: $%,.2f "
							+ "%nWithdrawal amount request: $%,.2f ", 
							name, type.getBalance(), amount));
		}
		
			
		
		//this method displays the non numeric error
		public void displayNonNumericError() {
			
			//opens JOptionPane to display the error
			JOptionPane.showMessageDialog(null, "Error: input is not a numeric value.");
		}
		
		//this method displays the non incremented by 20 error
		public void displayNotIncrementedBy20Error() {
			
			//opens JOptionPane to display the error
			JOptionPane.showMessageDialog(null, "Error: input is not an increment of 20");
		}
	
		//returns the withdrawal amount
		public double getAmount() {
			return amount;
		}
}
