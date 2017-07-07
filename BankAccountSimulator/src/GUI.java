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
	
	//constructor that builds the GUI
	public GUI() {
		
		//sets title of JFrame
		super("ATM Machine");
		
		//lays out the basic specifications of the frame
		setFrame(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);		
		
		//creates a Jpanel
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(panel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		
		//withdraw button specifications
		withdrawBtn = new JButton("Withdraw");
		panel.add(withdrawBtn);
		withdrawBtn.setToolTipText("Withdraw money from account.");
			
		//deposit button specifications
		depositBtn = new JButton("Deposit");
		panel.add(depositBtn);
		depositBtn.setToolTipText("Deposit money into account.");
		
		//transferTo button specifications
		transferToBtn = new JButton("Transfer to");
		panel.add(transferToBtn);
		transferToBtn.setToolTipText("Transfer money to selected "
				+ "account from other account.");
		
		//balance button specifications
		balanceBtn = new JButton("Balance");
		panel.add(balanceBtn);
		balanceBtn.setToolTipText("Display current balance in account.");
		
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
	
	//needed to implement the ActionListener class
	@Override
	public void actionPerformed(ActionEvent a){}
		
	//this method creates the frame
	private void setFrame(int width, int height) {
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	//this method displays the GUI
	private void display() {
		setVisible(true);
	}
	
	//this method checks if the value in the text field is numeric
	public static boolean isNumericValue(String input) throws InsufficientFunds {
		
		//declares and initializes the numericValue variable
		boolean numericValue = false;
		
		try {
			//attempts to convert the input to a type double
			Double.parseDouble(input);
			
			//changes the numericValue to true
			numericValue = true;
			
		} catch(NumberFormatException ex) {
			
			//throws exception if input is not a numeric value
			throw new InsufficientFunds(numericValue);
		}
		
		//returns results
		return numericValue;
	}
		
	//this method checks if the value in the text field is an increment of $20
	public static boolean isIncrementedBy20(String input) throws InsufficientFunds {
			
		//declares and initializes the incrementedBy20 variable
		boolean incrementedBy20 = false;
			
		//checks if the user's input is evenly divisible by 20
		if(Double.parseDouble(input) % 20 == 0)
			incrementedBy20 = true;
		
		else
	
			//throws exception if input is not an increment of $20
			throw new InsufficientFunds(incrementedBy20);
			
		//returns results
		return incrementedBy20;
	}
	
	//this method performs the withdrawal actions when the withdraw button is clicked
	private static void performWithdrawActions(Account checking, Account savings) {
		
		//will execute if the checking radio button is selected
		if(checkingRadioBtn.isSelected()){
			
			try {
				//calls the withdrawalRequest method to perform withdrawal
				withdrawalRequest(checking, checking.getName());
				
			} catch (InsufficientFunds f) {
				
				//displays error within the InsufficientFunds class
				f.displayWithdrawalError(checking, checking.getName(), f);
			}
		}
			
		//will execute if the savings radio button is selected
		else if(savingsRadioBtn.isSelected()) {
			
			try {	
				//calls the withdrawalRequest method to perform withdrawal
				withdrawalRequest(savings, savings.getName());
				
			} catch(InsufficientFunds f) {
				
				//displays error within the InsufficientFunds class
				f.displayWithdrawalError(savings, savings.getName(), f);
			}
		}
	}
	
	//this method performs the deposit actions when the deposit button is clicked
	public static void performDepositActions(Account checking, Account savings) {
		
		//will execute if the checking radio button is selected
		if(checkingRadioBtn.isSelected())
			
			//calls the depositRequest method to perform deposit
			depositRequest(checking, checking.getName());
				
		//will execute if the savings radio button is selected
		else if(savingsRadioBtn.isSelected())
			
			//calls the depositRequest method to perform deposit
			depositRequest(savings, savings.getName());	
	}
	
	//this method performs the transfer actions when the transfer button is clicked
	private static void performTransferToActions(Account checking, Account savings) {
		
		//will execute if the checking radio button is selected
		if(checkingRadioBtn.isSelected()) {
			
			try {
				//calls the transferRequest method to perform transfer
				transferRequest(checking, savings);
				
				//if no error occurs, the displayNewTransferBalances method is called to display balances
				displayNewTransferBalances(checking, savings, checking.getName(), savings.getName());
				
				
			} catch (InsufficientFunds f) {
				
				//displays error within the InsufficientFunds class
				f.displayTransferError(savings, savings.getName(), f);
			}
		}
		
		//will execute if the savings radio button is selected
		else if(savingsRadioBtn.isSelected()) {
			
			try {	
				//calls the transferRequest method to perform transfer
				transferRequest(savings, checking);
				
				//if no error occurs, the displayNewTransferBalances method is called to display balances
				displayNewTransferBalances(savings, checking, savings.getName(), checking.getName());
				
			} catch (InsufficientFunds f) {
				
				//displays error within the InsufficientFunds class
				f.displayTransferError(checking, checking.getName(), f);
			}	
		}
	}
	
	//this method performs balance actions when the balance button is clicked
	private static void performBalanceActions(Account checking, Account savings) {
		
		//will execute if the checking radio button is selected
		if(checkingRadioBtn.isSelected()) 
			
			//calls displayCurrentBalance method to display results
			displayCurrentBalance(checking, checking.getName());
		
		//will execute if the savings radio button is selected
		else if(savingsRadioBtn.isSelected())
			
			//calls the displayCurrentBalace method to display results
			displayCurrentBalance(savings, savings.getName());
	}
	
	//this method calls the withdrawFromAccount method to perform the withdrawal
	public static void withdrawalRequest(Account type, String name) throws InsufficientFunds {
			
		//calls withdrawFromAccount method to perform withdrawal 
		type.withdrawFromAccount(Double.parseDouble(inputText.getText()));
		
		//displays new account balance
		displayNewBalance(type, name);							
	}	
	
	//this method calls the depositToAccount method to perform deposit
	public static void depositRequest(Account type, String name) {
			
		//calls the depositToAccount method to perform deposit
		type.depositToAccount(Double.parseDouble(inputText.getText()));
			
		//displays new account balance
		displayNewBalance(type, name);
	}	
	
	//this method displays the new account balance after successful transaction
	public static void displayNewBalance(Account type, String name) {
			
		//displays results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new %s account balance is: $%,.2f", name, type.getBalance()));
	}	
	
	//this method calls the transfer method to perform transfer
	public static void transferRequest(Account to, Account from) throws InsufficientFunds {
		
		//calls the transfer method to perform transfer
		to.transfer(to, from, Double.parseDouble(inputText.getText())); 	
	}
	
	//this method displays the new account balances after a successful transfer
	public static void displayNewTransferBalances(Account to, Account from, String toName, String fromName) {
		
		//opens JOptionPane to output results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new %s account balance is: $%,.2f"
				+ "%nYour new %s account balance is: $%,.2f", 
				toName, to.getBalance(), fromName, from.getBalance()));
	}
	
	//this method displays the current account balances
	public static void displayCurrentBalance(Account type, String name) {
		
		//opens JOptionPane to output results
		JOptionPane.showMessageDialog(null, 
				String.format("Current %s account balance: $%,.2f", name, type.getBalance()));
	}	
			
	//main method
	public static void main(String[] args) {		
		GUI g = new GUI();
		
		//displays the entire JFrame
		g.display();	
		
		//creates a checking Account object
		Account checking = new Account("checking", 1000);
		
		//creates a savings Account object
		Account savings = new Account("savings", 5000);
	
		//withdraw button event handler
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){ 
				
				try {
					//calls the isNumericValue method to check if the value in the text field is numeric
					isNumericValue(inputText.getText());
					
					try {
						//calls the isIncrementedBy20 method to check if the amount is an increment of $20
						isIncrementedBy20(inputText.getText());
						
						//if no errors occur, the performWithdrawActions method is called to complete withdrawal
						performWithdrawActions(checking, savings);
						
					} catch(InsufficientFunds f) {
						
						//displays error within the InsufficientFunds class
						f.displayNotIncrementedBy20Error();
					}
					
				} catch(InsufficientFunds f) {
					
					//displays error within the InsufficientFunds class
					f.displayNonNumericError();
				}	
			}
		});
		
		//deposit button event handler
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
						
				try {
					//calls the isNumericValue method to check if the value in the text field is numeric
					isNumericValue(inputText.getText());
					
					//if no error occurs, the performDepositActions method is called to complete deposit
					performDepositActions(checking, savings);	
					
				} catch(InsufficientFunds f) {
					
					//displays error within the InsufficientFunds class
					f.displayNonNumericError();
				}
			}
		});
		
		//transferTo button event handler
		transferToBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
				try {
					//calls the isNumericValue method to check if the value in the text field is numeric
					isNumericValue(inputText.getText());
					
					//if no error occurs, the performTransferToActions method is called to complete transfer
					performTransferToActions(checking, savings);
					
				} catch(InsufficientFunds f) {
					
					//displays error within the InsufficientFunds class
					f.displayNonNumericError();
				}
		   }
		});	
		
		//balance button event handler
		balanceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				//calls performBalanceActions method to display balance
				performBalanceActions(checking, savings);
			}
			
		});	
		
	}
	
}
