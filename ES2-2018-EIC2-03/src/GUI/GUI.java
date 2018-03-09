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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import optimization.DataType;
import optimization.Variable;
import optimization.VariableTable;

public class GUI {
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel tudo = new JPanel(new GridLayout(5,1));
	private JPanel Email = new JPanel(new FlowLayout());
	private JPanel Problem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JPanel Variables = new JPanel(new FlowLayout());
	private JPanel Help = new JPanel(new FlowLayout());
	private JTextField EmailField = new JTextField(65);
	private JButton ValidateEmail = new JButton("Validate Email");
	private JTextField ProblemField = new JTextField(71);
	private JButton ValidateProblem = new JButton("Validate Problem");
	private JTextArea DescriptionField = new JTextArea(2,90);
	private String[] deadlines = {"1 Day","2 Days","3 Days","4 Days","5 Days","6 Days","7 Days","8 Days","9 Days","10 Days"};
	private JComboBox IdealDeadline = new JComboBox(deadlines);
	private JComboBox MaximumDeadline = new JComboBox(deadlines);
	private JComboBox<Integer> decisionVariables = new JComboBox<>();
	private JComboBox variablesName;
	private JSlider variablesWeight = new JSlider(-50,50,0);
	private JLabel weightValue = new JLabel("0");
	private JButton FAQ = new JButton("F.A.Q.");
	private JButton Ajuda = new JButton("Ask for Help");

	private VariableTable variableTable;

	public void start(){
		initializeFields();
		initializeActionListeners();
		frame.add(tudo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/*Add the fields to tudo*/
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
		tudo.add(Problem);

		/*DESCRIPTION*/
		Description.add(new JLabel("Describe the Problem:"));
		Description.add(DescriptionField);
		tudo.add(Description);

		/*VARIABLES*/
		Variables.add(new JLabel("Set an ideal deadline:"));
		Variables.add(IdealDeadline);
		Variables.add(new JLabel("Set a maximum deadline:"));
		Variables.add(MaximumDeadline);
		setDecisionVariablesRange();
		Variables.add(new JLabel("Choose the number of variables:"));
		Variables.add(decisionVariables);
		tudo.add(Variables);

		/*HELP*/
		Help.add(new JLabel("Email us if you don't understand how this platform works or if you're having trouble using it:"));
		Help.add(Ajuda);
		Help.add(new JLabel("Check our F.A.Q. first:"));
		Help.add(FAQ);
		tudo.add(Help);
	}

	private void setDecisionVariablesRange() {
		for(int i=1;i<=100;i++) {
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
							+ "\n and can only be composed of letters, numbers, underscores '_' or currency symbols like '$'"
							+ "\nStarting with a number, or any other special character is forbidden. Don't include any spaces in the Problem name","WARNING",JOptionPane.ERROR_MESSAGE);;
				}else {
					ValidateProblem.setBackground(Color.GREEN);
					ValidateProblem.setEnabled(false);
				}
			}
		});
		/*Add Listener to the variable quantity JComboBox, which will then trigger the whole variable naming and typesetting*/
		decisionVariables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent choice) {
				int howMany = (int) decisionVariables.getSelectedItem();
				JOptionPane decision = new JOptionPane();
				int chosen = decision.showConfirmDialog(frame,"Are you sure you want to procceed with "+howMany+" variable(s)?\nAfter you confirm the number you will be asked to config each variable name, do you still wish to continue?", "Confirm Window", JOptionPane.OK_CANCEL_OPTION);
				if(chosen==decision.OK_OPTION) {
					/*Ask the user for the name of the variable group e.g. "E-mail SPAM Filter"*/
					String inputVariableGroupName = decision.showInputDialog("What name do you wish to call this variable group\nfor this specific problem:");
					variableTable = new VariableTable(inputVariableGroupName);
					for(int i=0;i<howMany;i++) {
						String inputVariableName = decision.showInputDialog("Name your variable: ");
						String[] variableTypes = {"Integer", "Real", "Binary"};
						JComboBox variables = new JComboBox(variableTypes);
						decision.showMessageDialog(decisionVariables, variables, "Choose the data type you wish to work with:",JOptionPane.QUESTION_MESSAGE);
						if(variables.getSelectedItem().equals("Integer")) variableTable.addVariable(new Variable(inputVariableName,DataType.INTEGER));
						else if(variables.getSelectedItem().equals("Real")) variableTable.addVariable(new Variable(inputVariableName,DataType.REAL));
						else if(variables.getSelectedItem().equals("Binary")) variableTable.addVariable(new Variable(inputVariableName,DataType.BINARY));
					}
					/*Disable the JComboBox related to the number of variables and create the JComboBox with all the variables*/
					decisionVariables.setEnabled(false);
					String[] decisionVariables = new String[howMany];
					int count = 0;
					for(Variable v:variableTable.getVariables()) {
						decisionVariables[count]=v.getVarName();
						count++;
					}
					variablesName = new JComboBox(decisionVariables);
					Variables.add(variablesName);
					Variables.add(variablesWeight);
					Variables.add(weightValue);
					frame.setVisible(true);
				}
			}
		});
		/*Represent the JSlider's immediate value in a JLabel*/
		variablesWeight.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(String.valueOf(variablesWeight.getValue()).length()>2) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 2)+"."+String.valueOf(variablesWeight.getValue()).substring(2,3));
				}
				else if(String.valueOf(variablesWeight.getValue()).length()==2){
					/*Check if it's negative or not*/
					if(String.valueOf(variablesWeight.getValue()).substring(0, 1).equals("-")) {
						weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 1)+"0."+String.valueOf(variablesWeight.getValue()).substring(1, 2));
					}else{
						weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 1)+"."+String.valueOf(variablesWeight.getValue()).substring(1, 2));
					}
				}else {
					weightValue.setText("0."+variablesWeight.getValue());
				}
				Double d = Double.valueOf(weightValue.getText());
				System.out.println(d);
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent click) {
			}
		});
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


