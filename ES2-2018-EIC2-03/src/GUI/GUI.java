package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import optimization.DataType;
import optimization.Variable;

public class GUI {
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel tudo = new JPanel(new GridLayout(5,1));
	private JPanel Email = new JPanel(new FlowLayout());
	private JPanel Problem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JPanel MediumRow = new JPanel(new FlowLayout());
	private JPanel Variables = new JPanel(new FlowLayout());
	private JTextField EmailField = new JTextField(44);
	private JButton ValidateEmail = new JButton("Validate Email");
	private JTextField ProblemField = new JTextField(34);
	private JButton ValidateProblem = new JButton("Validate Problem");
	private JTextArea DescriptionField = new JTextArea(2,71);
	private String[] deadlines = {"1 Day","2 Days","3 Days","4 Days","5 Days","6 Days","7 Days","8 Days","9 Days","10 Days"};
	private JComboBox DeadlineField = new JComboBox(deadlines);
	private JButton FAQ = new JButton("F.A.Q.");
	private JButton Ajuda = new JButton("Ask for Help");
	private JComboBox decisionVariables = new JComboBox();

	private String EMAIL="";

	public void start(){
		initializeFields();
		initializeActionListeners();
		frame.add(tudo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/*Add the fields to the FillOutForm*/
	private void initializeFields(){
		/*EMAIL*/
		Email.add(new JLabel("Insert your E-Mail for contact purposes:"));
		Email.add(EmailField);
		ValidateEmail.setBackground(Color.RED);
		ValidateEmail.setForeground(Color.WHITE);
		ValidateEmail.setFont(new Font("Cambria", Font.BOLD, 20));
		Email.add(ValidateEmail);
		tudo.add(Email);
		/*PROBLEM*/
		Problem.add(new JLabel("Name of your Problem:"));
		Problem.add(ProblemField);
		ValidateProblem.setBackground(Color.RED);
		ValidateProblem.setForeground(Color.WHITE);
		ValidateProblem.setFont(new Font("Cambria", Font.BOLD, 20));
		Problem.add(ValidateProblem);
		Problem.add(new JLabel("Set a deadline:"));
		Problem.add(DeadlineField);
		tudo.add(Problem);
		/*DESCRIPTION*/
		Description.add(new JLabel("Describe the Problem:"));
		Description.add(DescriptionField);
		tudo.add(Description);
		/*MEDIUMROW*/
		MediumRow.add(new JLabel("Email us if you don't understand how this platform works or if you're having trouble using it:"));
		MediumRow.add(Ajuda);
		MediumRow.add(new JLabel("Check our F.A.Q. first:"));
		MediumRow.add(FAQ);
		tudo.add(MediumRow);
		/*VARIABLES*/
		setDecisionVariablesRange();
		Variables.add(decisionVariables);
		tudo.add(Variables);
	}
	
	private void setDecisionVariablesRange() {
		for(int i=1;i<=1000;i++) {
			decisionVariables.addItem(i);
		}
	}
	
	private void initializeActionListeners(){
		/*Allows to re-enable the Email Validator upon re-interacting with the Field*/
		EmailField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent key) {
				ValidateEmail.setBackground(Color.RED);
				ValidateEmail.setEnabled(true);
			}
		});
		/*Allows to re-enable the Problem Name Validator upon re-interacting with the Field*/
		ProblemField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent key) {
				ValidateProblem.setBackground(Color.RED);
				ValidateProblem.setEnabled(true);
			}
		});
		/*Add Listener to Email Validation Button*/
		ValidateEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!(isValidEmail(EmailField.getText()))){
					new JOptionPane().showMessageDialog(frame, "Invalid Email. Suggestion: abcde@gmail.com","WARNING",JOptionPane.ERROR_MESSAGE);;
				}else {
					ValidateEmail.setBackground(Color.GREEN);
					ValidateEmail.setEnabled(false);
				}
			}
		});
		/*Add Listener to Problem Validation Button*/
		ValidateProblem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!(isValidProblem(ProblemField.getText()))){
					new JOptionPane().showMessageDialog(frame, "Invalid Problem name. Should start with a letter a-z or A-Z, currency symbols or underscore"
							+ "\n and can only be composed of letters, numbers, underscores '_' or currency symbols like '$'","WARNING",JOptionPane.ERROR_MESSAGE);;
				}else {
					ValidateProblem.setBackground(Color.GREEN);
					ValidateProblem.setEnabled(false);
				}
			}
		});
//		decisionVariables.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent choice) {
//				int howMany = (int) decisionVariables.getSelectedItem();
//				JOptionPane decision = new JOptionPane();
//				int chosen = decision.showConfirmDialog(frame,"Are you sure you want to procceed with "+howMany+" variables?", "Confirm Window", JOptionPane.OK_CANCEL_OPTION);
//				if(chosen==decision.OK_OPTION) {
//					String inputVariableName = decision.showInputDialog("Name your variable: ");
//					String[] variableTypes = {"Integer", "Decimal", "Binary"};
//					JComboBox variables = new JComboBox(variableTypes);
//					String inputVariableType = decision.showInputDialog("Choose the data type you wish to work with:", variables);
//					switch(inputVariableType){
//					case "Integer": Variables.add(new Variable(inputVariableName, DataType.INTEGER));
//					case "Decimal": Variables.add(new Variable(inputVariableName, DataType.DOUBLE));
//					case "Binary": Variables.add(new Variable(inputVariablesName, DataType.BINARY));
//					}
//					
//					System.out.println(howMany);
//				}
//			}
//	 	});
	}

	public static boolean isValidEmail(String email){
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static boolean isValidProblem(String problem) {
		String problemRegex ="^[a-zA-Z\\p{Sc}_]+([a-zA-Z0-9\\p{Sc}_]*)$";
		Pattern pat = Pattern.compile(problemRegex);
		if (problem == null)
			return false;
		return pat.matcher(problem).matches();

	}



	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.start();
	}	
}


