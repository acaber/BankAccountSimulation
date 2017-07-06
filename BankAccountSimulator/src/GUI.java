/**
 * File Name: GUI.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: Sets up the GUI and assigns the buttons event handlers
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

	//holds height and weight for JFrame
	private static final int WIDTH = 285;
	private static final int HEIGHT = 160;
	
	//declares the four required buttons
	private static JButton withdrawBtn;
	private static JButton depositBtn;
	private static JButton transferToBtn;
	private static JButton balanceBtn;
	
	//declares the two account type radio buttons
	private static JRadioButton checkingRadioBtn;
	private static JRadioButton savingsRadioBtn;
	
	//declares the text box
	private static JTextField inputText;
	
	//fields to hold the balance of each account type
	private static double checkingBalance;
	private static double savingsBalance;
	
	//creates a checking Account object
	private Account checking;
		
	//creates a savings Account object
	private Account savings;
		
	//constructor that builds the GUI
	public GUI() {
		
		//sets title of JFrame
		super("ATM Machine");
		
		//lays out the basic specifications of the frame
		setFrame(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);
		
		//initializes the checking account balance
		checking = new Account(1000);

		//initializes the savings account balance
		savings = new Account(5000);
	
		//initializes the checking and savings account balance variables
		checkingBalance = checking.getBalance();
		savingsBalance = savings.getBalance();
		
		//creates a Jpanel
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(panel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		
		//withdraw button specifications
		withdrawBtn = new JButton("Withdraw");
		panel.add(withdrawBtn);
		withdrawBtn.setToolTipText("Withdraw money from account.");
		
		//withdraw button event handler
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
					
				//will execute if the input is a numeric value
				if(isNumericValue(inputText.getText())) {
					
					//will execute if the input is incremented by $20
					if(isIncrementedBy20(inputText.getText())){
						
						//will execute if the checking radio button is selected
						if(checkingRadioBtn.isSelected()){
							
							try {
								setCheckingWithdrawAmount();
							} catch (InsufficientFunds f) {
								displayCheckingWithdrawalError(f);
							}
						}				
							
						//will execute if the savings radio button is selected
						else if(savingsRadioBtn.isSelected()) {
							
							try {
								setSavingsWithdrawAmount();
							} catch(InsufficientFunds f) {
								displaySavingsWithdrawalError(f);
							}
						}
					}
						
					else
						displayNotIncrementedBy20Error();
				}	
				else 
					displayNonNumericError();	
		}
	});
		
		//deposit button specifications
		depositBtn = new JButton("Deposit");
		panel.add(depositBtn);
		depositBtn.setToolTipText("Deposit money into account.");
		
		//deposit button event handler
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				//will execute if the input is a numeric value
				if(isNumericValue(inputText.getText())) {

					//will execute if the checking radio button is selected
					if(checkingRadioBtn.isSelected())
						setCheckingDepositAmount();
					
					//will execute if the savings radio button is selected
					else if(savingsRadioBtn.isSelected()) 
						setSavingsDepositAmount();	
				else 
					displayNonNumericError();
				}
		      }
		});
		
		
		//transferTo button specifications
		transferToBtn = new JButton("Transfer to");
		panel.add(transferToBtn);
		transferToBtn.setToolTipText("Transfer money to selected "
				+ "account from other account.");
		
		//transferTo button event handler
		transferToBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
				//will execute if the input is a numeric value
				if(isNumericValue(inputText.getText())) {
					
					//will execute if the checking radio button is selected
					if(checkingRadioBtn.isSelected()) {
						try {
							setCheckingTransferBalance();
							displayTransferAccountBalance();
						} catch (InsufficientFunds f) {
							displayCheckingTransferError(f);
						}
					}
					
					//will execute if the savings radio button is selected
					else if(savingsRadioBtn.isSelected()) {
						try {	
							setSavingsTransferBalance();
							displayTransferAccountBalance();
						} catch (InsufficientFunds f) {
							displaySavingsTransferError(f);
						}		
					}
				}
				else 
					displayNonNumericError();
		   }
		});
		
		//balance button specifications
		balanceBtn = new JButton("Balance");
		panel.add(balanceBtn);
		balanceBtn.setToolTipText("Display current balance in account.");
		
		//balance button event handler
		balanceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				//will execute if the checking radio button is selected
				if(checkingRadioBtn.isSelected()) 
					displayCheckingAccountBalance();
				//will execute if the savings radio button is selected
				else if(savingsRadioBtn.isSelected())
					displaySavingsAccountBalance();
			}
		});
		
		//creates a button group for checking and savings
		ButtonGroup group = new ButtonGroup();

		//checking radio button specifications
		checkingRadioBtn = new JRadioButton("Checking");
		group.add(checkingRadioBtn);
		panel.add(checkingRadioBtn);
		checkingRadioBtn.setSelected(true);
				
		//savings radio button specifications
		savingsRadioBtn = new JRadioButton("Savings");
		group.add(savingsRadioBtn);
		panel.add(savingsRadioBtn);
				
		//textPanel specifications
		JPanel textPanel = new JPanel();
		inputText = new JTextField(15);
		textPanel.add(inputText);
		add(textPanel, BorderLayout.SOUTH);
		inputText.setEditable(true);
	}
	
	//this method checks if the user's input is a number
	public boolean isNumericValue(String input) {
		
		try {
			Double.parseDouble(input);
		} catch(NumberFormatException ex) {
			return false;
		}
	
		return true;
	}
	
	//this method checks if the user's input is incremented by 20
	public boolean isIncrementedBy20(String input) {
		
		//declares and initializes the incrementedBy20 variable
		boolean incrementedBy20 = false;
		
		//checks if the user's input is evenly divisible by 20
		if(Double.parseDouble(input) % 20 == 0)
			incrementedBy20 = true;
		
		//returns results
		return incrementedBy20;
	}
	
	//this method sets the new checking account balance after a successful withdrawal
	public void setCheckingWithdrawAmount() throws InsufficientFunds {
			
		//calls withdrawAccount method and sets the return amount to checkingBalance
		checkingBalance = checking.withdrawAccount(Double.parseDouble(inputText.getText()));
				
		displayCheckingBalance();							
	}
	
	//this method sets the new savings account balance after a successful withdrawal
	public void setSavingsWithdrawAmount() throws InsufficientFunds {
			
		//calls the withdrawAmount method and sets the return amount to savingsBalance
		savingsBalance = savings.withdrawAccount(Double.parseDouble(inputText.getText()));
			
		//displays results
		displaySavingsBalance();
	}
	
	
	//this method sets the new checking account balance after a successful deposit
	public void setCheckingDepositAmount() {
			
		//calls the depositAccount method and sets the return value to checkingBalance
		checkingBalance = checking.depositAccount(Double.parseDouble(inputText.getText()));
			
		displayCheckingBalance();
	}
		
	//this method sets the new savings account balance after a successful deposit
	public void setSavingsDepositAmount() {
			
		//calls the depositAccount method and sets the return value to savingsBalance
		savingsBalance = savings.depositAccount(Double.parseDouble(inputText.getText()));
			
		displaySavingsBalance();		
	}
		
	
	//this method sets the new checking account balance after completing a transfer
	public void setCheckingTransferBalance() throws InsufficientFunds {
		
		//calls the transfer method
		checking.transfer(checking, savings, Double.parseDouble(inputText.getText())); 
		
		//sets checkingBalance to its new balance
		checkingBalance = checking.getBalance();
		
		//sets savings balance to its new balance
		savingsBalance = savings.getBalance();
	}
	
	//this method sets the new savings account balance after completing a transfer
	public void setSavingsTransferBalance() throws InsufficientFunds {
		
		//calls the transfer method
		savings.transfer(savings, checking, Double.parseDouble(inputText.getText())); 
		
		//sets savingsBalance to its new balance
		savingsBalance = savings.getBalance();
		
		//sets checkingBalance to its new balance
		checkingBalance = checking.getBalance();
	}
	
	//this method displays the new account balances after a successful transfer
	public void displayTransferAccountBalance() {
		
		//opens JOptionPane to output results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new checking account balance is: $%,.2f"
				+ "%nYour new savings account balance is: $%,.2f", 
				checkingBalance, savingsBalance));
	}
	
	//this method displays the current checking account balance
	public void displayCheckingAccountBalance() {
		
		//displays the current checking account balance
		JOptionPane.showMessageDialog(null, 
				String.format("Current checking account balance: $%,.2f", checking.getBalance()));
	}
	
	//this method displays the current savings account balance
	public void displaySavingsAccountBalance() {
		
		//displays the current savings account balance
		JOptionPane.showMessageDialog(null, 
				String.format("Current savings account balance: $%,.2f", savings.getBalance()));
	}
	
	//this method displays the new balance of the checking account after a withdrawal or deposit
	public void displayCheckingBalance() {
		
		//displays results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new checking account balance is: $%,.2f", checkingBalance));
	}
	
	//this method displays the new balance of the savings account after a withdrawal or deposit
	public void displaySavingsBalance() {
		
		//displays results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new savings account balance is: $%,.2f", savingsBalance));
	}
	
	//this method displays the checking withdrawal error
	public void displayCheckingWithdrawalError(InsufficientFunds f) {
		
		//opens JOptionPane to output the error
		JOptionPane.showMessageDialog(null, String.format(	
				"Error: Insufficient funds available to complete withdrawal. "
				+ "%n%nAvaiable checking account balance: $%,.2f "
				+ "%nWithdrawal amount request: $%,.2f "
				+ "%nService fee: $%.2f"
				+ "%nTotal withdrawal amount: $%,.2f", 
				checkingBalance, f.getAmount(), checking.getServiceCharge(), 
				(f.getAmount() + checking.getServiceCharge())));
	}
	
	//this method displays the savings withdrawal error
	public void displaySavingsWithdrawalError(InsufficientFunds f) {

		//opens JOptionPane to output the error
		JOptionPane.showMessageDialog(null, String.format(
				"Error: Insufficient funds available to complete withdrawal. "
				+ "%n%nAvaiable savings account balance: $%,.2f "
				+ "%nWithdrawal amount request: $%,.2f "
				+ "%nService fee: $%.2f"
				+ "%nTotal withdrawal amount: $%,.2f", savingsBalance, 
				f.getAmount(), savings.getServiceCharge(), 
				(f.getAmount() +  savings.getServiceCharge())));
	}
	
	//this method displays the checking transfer error
	public void displayCheckingTransferError(InsufficientFunds f) {
		
		//opens JOptionPane to display the error
		JOptionPane.showMessageDialog(null, String.format(
				"Error: Insufficient funds available in savings account to complete transfer. "
				+ "%n%nAvaiable savings account balance: $%,.2f "
				+ "%nWithdrawal amount request: $%,.2f ", 
				savingsBalance, f.getAmount()));
	}
	
	//this method displays the savings transfer error
	public void displaySavingsTransferError(InsufficientFunds f) {
		
		//opens JOptionPane to display the error
		JOptionPane.showMessageDialog(null, String.format(
				"Error: Insufficient funds available in checking account to complete transfer. "
				+ "%n%nAvaiable checking account balance: $%,.2f "
				+ "%nWithdrawal amount request: $%,.2f ", 
				checkingBalance, f.getAmount()));
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
	
	//needed to implement the ActionListener class
	@Override
	public void actionPerformed(ActionEvent a){}
	
	//method that creates the frame
	private void setFrame(int width, int height) {
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//this method displays the GUI
	public void display() {
		setVisible(true);
	}
	
	//main method
	public static void main(String[] args) {		
		GUI g = new GUI();
		
		//displays the entire JFrame
		g.display();	
	}
}
