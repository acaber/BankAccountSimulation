/**
 * File Name: GUI.java
 * Author: Rebecca Johnson
 * Date: July 6, 2017
 * Purpose: Simulates a bank account with a GUI that has the capabilities of 
 * 	withdrawing funds from a selected account, depositing funds to a selected account,
 * 	transferring funds to a selected account from the other account, and displaying
 * 	the balance of the selected account.
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
	
	//constructor that builds the GUI
	public GUI() {
		
		//sets title of JFrame
		super("ATM Machine");
		
		//lays out the basic specifications of the frame
		setFrame(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);
		
		//creates a checking Account object
		Account checking;
		//initializes the checking account balance
		checking = new Account(1000);

		//creates a savings Account object
		Account savings;
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
					
				//calls the isNumericValue method to make sure the input is a number
				if(isNumericValue(inputText.getText())) {
					
					//calls the isIncrementedBy20 method to make sure the input is an increment of $20
					if(isIncrementedBy20(inputText.getText())){
						
						//will execute if the checking radio button is selected
						if(checkingRadioBtn.isSelected()){
							
							//try statement
							try {
								//calls withdrawAccount method and sets the return amount to checkingBalance
								checkingBalance = checking.withdrawAccount(Double.parseDouble(inputText.getText()));
								
								//opens JOptionPane to show the results
								JOptionPane.showMessageDialog(null, String.format("Success! "
										+ "%nYour new checking account balance is: $%.2f", checkingBalance));
							}
							
							//catch statement
							catch(InsufficientFunds f) {
								
								//opens JOptionPane to output the error
								JOptionPane.showMessageDialog(null, String.format(	
										"Error: Insufficient funds available to complete withdrawal. "
										+ "%n%nAvaiable checking account balance: $%.2f "
										+ "%nWithdrawal amount request: $%.2f "
										+ "%nService fee: $%.2f"
										+ "%nTotal withdrawal amount: $%.2f", 
										checkingBalance, f.getAmount(), checking.getServiceCharge(), 
										(f.getAmount() + checking.getServiceCharge())));
							}				
						}
							
						//will execute if the savings radio button is selected
						else if(savingsRadioBtn.isSelected()) {
							
							//try statement
							try {
								
								//calls the withdrawAmount method and sets the return amount to savingsBalance
								savingsBalance = savings.withdrawAccount(Double.parseDouble(inputText.getText()));
								
								//opens JOptionPane to output the results
								JOptionPane.showMessageDialog(null, String.format("Success! "
										+ "%nYour new savings account balance is: $%.2f", savingsBalance));
								}
							
							//catch statement
							catch(InsufficientFunds f) {
								
								//opens JOptionPane to output the error
								JOptionPane.showMessageDialog(null, String.format(
										"Error: Insufficient funds available to complete withdrawal. "
												+ "%n%nAvaiable savings account balance: $%.2f "
												+ "%nWithdrawal amount request: $%.2f "
												+ "%nService fee: $%.2f"
												+ "%nTotal withdrawal amount: $%.2f", savingsBalance, 
												f.getAmount(), savings.getServiceCharge(), (f.getAmount() 
														+ savings.getServiceCharge())));
							}
						}
					}
						
					else
						JOptionPane.showMessageDialog(null, "Error: input is not an increment of 20");
				}
					
				else 
					JOptionPane.showMessageDialog(null, "Error: input is not a numeric value.");	
		}
	});
		
		//deposit button specifications
		depositBtn = new JButton("Deposit");
		panel.add(depositBtn);
		depositBtn.setToolTipText("Deposit money into account.");
		
		//deposit button event handler
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				//calls the isNumericValue method to make sure the input is a number
				if(isNumericValue(inputText.getText())) {

					//will execute if the checking radio button is selected
					if(checkingRadioBtn.isSelected()){
						
						//calls the depositAccount method and sets the return value to checkingBalance
						checkingBalance = checking.depositAccount(Double.parseDouble(inputText.getText()));
						
						//opens the JOptionPane to output the results
						JOptionPane.showMessageDialog(null, String.format("Success! "
								+ "%nYour new checking account balance is: $%.2f", checkingBalance));
					}
					
					//will execute if the savings radio button is selected
					else if(savingsRadioBtn.isSelected()) {
						
						//calls the depositAccount method and sets the return value to savingsBalance
						savingsBalance = savings.depositAccount(Double.parseDouble(inputText.getText()));
						
						//opens the JOptionPane to output the results
						JOptionPane.showMessageDialog(null, String.format("Success! "
								+ "%nYour new savings account balance is: $%.2f", savingsBalance));
					}
				else 
					JOptionPane.showMessageDialog(null, "Error: input is not a numeric value.");
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
		
				//calls the isNumericValue method to make sure the input is a number
				if(isNumericValue(inputText.getText())) {
					
					//will execute if the checking radio button is selected
					if(checkingRadioBtn.isSelected()){
						
						//try statement
						try {
							
							//calls the transfer method
							checking.transfer(checking, savings, Double.parseDouble(inputText.getText())); 
							
							//sets checkingBalance to its new balance
							checkingBalance = checking.getBalance();
							
							//sets savings balance to its new balance
							savingsBalance = savings.getBalance();
							
							//opens JOptionPane to output results
							JOptionPane.showMessageDialog(null, String.format("Success! "
									+ "%nYour new checking account balance is: $%.2f"
									+ "%nYour new savings account balance is: $%.2f", checkingBalance, savingsBalance));
						} 
						
						//catch statement
						catch (InsufficientFunds f) {
							
							//opens JOptionPane to display the error
							JOptionPane.showMessageDialog(null, String.format(
									"Error: Insufficient funds available in savings account to complete transfer. "
											+ "%n%nAvaiable savings account balance: $%.2f "
											+ "%nWithdrawal amount request: $%.2f ", 
											savingsBalance, f.getAmount()));
						}
					}
					
					//will execute if the savings radio button is selected
					else if(savingsRadioBtn.isSelected()) {
						
						//try statement
						try {
							
							//calls the transfer method
							savings.transfer(savings, checking, Double.parseDouble(inputText.getText())); 
							
							//sets savingsBalance to its new balance
							savingsBalance = savings.getBalance();
							
							//sets checkingBalance to its new balance
							checkingBalance = checking.getBalance();
							
							//opens JOptionPane to output results
							JOptionPane.showMessageDialog(null, String.format("Success! "
									+ "%nYour new savings account balance is: $%.2f"
									+ "%nYour new checking account balance is: $%.2f", savingsBalance, checkingBalance));
						} 
						
						//catch statement
						catch (InsufficientFunds f) {
							
							//opens JOptionPane to display the error
							JOptionPane.showMessageDialog(null, String.format(
									"Error: Insufficient funds available in checking account to complete transfer. "
											+ "%n%nAvaiable checking account balance: $%.2f "
											+ "%nWithdrawal amount request: $%.2f ", 
											checkingBalance, f.getAmount()));
						}
						
					}
				else 
					JOptionPane.showMessageDialog(null, "Error: input is not a numeric value.");
				}
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
					JOptionPane.showMessageDialog(null, String.format("Current checking account balance: $%.2f", checking.getBalance()));
				
				//will execute if the savings radio button is selected
				else if(savingsRadioBtn.isSelected())
					JOptionPane.showMessageDialog(null,  String.format("Current savings account balance: $%.2f", savings.getBalance()));
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
	
	//method that checks if the user's input is a number
	public boolean isNumericValue(String input) {
		
		//try statement
		try {
			
			//tries to convert the input to a double
			Double.parseDouble(input);
		}
		
		//catch statement
		catch(NumberFormatException ex) {
			return false;
		}
	
		return true;
	}
	
	//checks if input is incremented by 20
	public boolean isIncrementedBy20(String input) {
		
		//declares and initializes the incrementedBy20 variable
		boolean incrementedBy20 = false;
		
		//checks if the user's input is evenly divisible by 20
		if(Double.parseDouble(input) % 20 == 0)
			incrementedBy20 = true;
		
		//returns results
		return incrementedBy20;
	}
	
	//method displays the GUI
	public void display() {
		setVisible(true);
		
	}
	
	//method that creates the frame
	private void setFrame(int width, int height) {
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	
	//needed to implement the ActionListener class
	@Override
	public void actionPerformed(ActionEvent a){}
	
	//main method
	public static void main(String[] args) {		
		GUI g = new GUI();
		
		//displays the entire JFrame
		g.display();	
	}
}
