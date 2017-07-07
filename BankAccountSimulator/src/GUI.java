/** File Name: GUI.java
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
	
	//this method checks if the user's input is a number
	public static boolean isNumericValue(String input) throws InsufficientFunds {
			
		try {
			Double.parseDouble(input);
		} catch(NumberFormatException ex) {
			return false;
		}
			
		return true;
	}
		
	//this method checks if the user's input is incremented by 20
	public static boolean isIncrementedBy20(String input) throws InsufficientFunds {
			
		//declares and initializes the incrementedBy20 variable
		boolean incrementedBy20 = false;
			
		//checks if the user's input is evenly divisible by 20
		if(Double.parseDouble(input) % 20 == 0)
			incrementedBy20 = true;
			
		//returns results
		return incrementedBy20;
	}
		
	//this method sets the new checking account balance after a successful withdrawal
	public static void setWithdrawAmount(Account type, String name) throws InsufficientFunds {
			
		//calls withdrawAccount method and sets the return amount to checkingBalance
		type.withdrawAccount(Double.parseDouble(inputText.getText()));
				
		displayNewBalance(type, name);							
	}
	
	
	
	//this method sets the new checking account balance after a successful deposit
	public static void setDepositAmount(Account type, String name) {
			
		//calls the depositAccount method and sets the return value to checkingBalance
		type.depositAccount(Double.parseDouble(inputText.getText()));
			
		displayNewBalance(type, name);
	}
	
		
	
	//this method sets the new checking account balance after completing a transfer
	public static void setTransferBalance(Account to, Account from) throws InsufficientFunds {
		
		//calls the transfer method
		to.transfer(to, from, Double.parseDouble(inputText.getText())); 
		
	}
	
	
	//this method displays the new account balances after a successful transfer
	public static void displayTransferAccountBalance(Account to, Account from, String toName, String fromName) {
		
		//opens JOptionPane to output results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new %s account balance is: $%,.2f"
				+ "%nYour new %s account balance is: $%,.2f", 
				toName, to.getBalance(), fromName, from.getBalance()));
	}
	
	//this method displays the current checking account balance
	public static void displayBalance(Account type, String name) {
		
		//displays the current checking account balance
		JOptionPane.showMessageDialog(null, 
				String.format("Current %s account balance: $%,.2f", name, type.getBalance()));
	}
	
	
	//this method displays the new balance of the checking account after a withdrawal or deposit
	public static void displayNewBalance(Account type, String name) {
		
		//displays results
		JOptionPane.showMessageDialog(null, String.format("Success! "
				+ "%nYour new %s account balance is: $%,.2f", name, type.getBalance()));
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
	
	public static void performWithdrawActions(Account checking, Account savings) {
		//will execute if the checking radio button is selected
		if(checkingRadioBtn.isSelected()){
			
			try {
				setWithdrawAmount(checking, checking.getName());
			} catch (InsufficientFunds f) {
				f.displayWithdrawalError(checking, checking.getName());
			}
		}
			
		//will execute if the savings radio button is selected
		else if(savingsRadioBtn.isSelected()) {
			
			try {
				setWithdrawAmount(savings, savings.getName());
			} catch(InsufficientFunds f) {
				f.displayWithdrawalError(savings, savings.getName());
			}
		}
	}
	
	//main method
	public static void main(String[] args)  {		
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
					if(isNumericValue(inputText.getText())){
						try {
							if(isIncrementedBy20(inputText.getText()))
								performWithdrawActions(checking, savings);
						} catch(InsufficientFunds f) {
							f.displayNotIncrementedBy20Error();
						}	
					}
				} catch(InsufficientFunds f) {
					f.displayNonNumericError();
				}
				
			}
		});
		
		
		//deposit button event handler
		depositBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
						
				try {
					if(isNumericValue(inputText.getText()))
						//will execute if the checking radio button is selected
						if(checkingRadioBtn.isSelected())
							setDepositAmount(checking, checking.getName());
								
						//will execute if the savings radio button is selected
						else if(savingsRadioBtn.isSelected())
							setDepositAmount(savings, savings.getName());	
				} catch(InsufficientFunds f) {
					f.displayNonNumericError();
				}
				}
		});
		

		//transferTo button event handler
		transferToBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
				
				try {
					isNumericValue(inputText.getText());
				} catch(InsufficientFunds f) {
					f.displayNonNumericError();
				}

					//will execute if the checking radio button is selected
					if(checkingRadioBtn.isSelected()) {
						try {
							setTransferBalance(checking, savings);
							displayTransferAccountBalance(checking, savings, checking.getName(), savings.getName());
						} catch (InsufficientFunds f) {
							f.displayTransferError(checking, checking.getName());
						}
					}
					
					//will execute if the savings radio button is selected
					else if(savingsRadioBtn.isSelected()) {
						try {	
							setTransferBalance(savings, checking);
							displayTransferAccountBalance(savings, checking, savings.getName(), checking.getName());
						} catch (InsufficientFunds f) {
							f.displayTransferError(savings, savings.getName());
						}	
					}
				
		   }
		});
		
		
		//balance button event handler
				balanceBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						
						//will execute if the checking radio button is selected
						if(checkingRadioBtn.isSelected()) 
							displayBalance(checking, checking.getName());
						//will execute if the savings radio button is selected
						else if(savingsRadioBtn.isSelected())
							displayBalance(savings, savings.getName());
					}
				});
		
				
	}
}
