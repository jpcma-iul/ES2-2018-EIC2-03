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

import objects.DataType;
import objects.Variable;
import objects.VariableTable;

public class GUI {
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel tudo = new JPanel(new GridLayout(5, 1));
	private JPanel EmailProblem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JPanel Deadlines = new JPanel(new FlowLayout());
	private JPanel Variables = new JPanel(new FlowLayout());
	private JPanel Help = new JPanel(new FlowLayout());
	private JTextField EmailField = new JTextField(26);
	private JButton ValidateEmail = new JButton("Validate Email");
	private JTextField ProblemField = new JTextField(20);
	private JButton ValidateProblem = new JButton("Validate Problem");
	private JTextArea DescriptionField = new JTextArea(2, 100);
	private String[] deadlines = { "1 Day", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "7 Days", "8 Days",
			"9 Days", "10 Days" };
	private JComboBox IdealDeadline = new JComboBox(deadlines);
	private JComboBox MaximumDeadline = new JComboBox(deadlines);
	private JComboBox<Integer> decisionVariables = new JComboBox<>();
	private JComboBox variablesName;
	private JSlider variablesWeight = new JSlider(-50, 50, 0);
	private JLabel weightValue = new JLabel("0");
	private JButton ChooseVariableRange = new JButton("Choose Variable Range");
	private JButton FAQ = new JButton("F.A.Q.");
	private JButton Ajuda = new JButton("Ask for Help");

	private VariableTable variableTable;

	public void start() {
		initializeFields();
		initializeActionListeners();
		frame.add(tudo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/* Add the fields to tudo */
	private void initializeFields() {
		/* EMAILPROBLEM */
		EmailProblem.add(new JLabel("Insert your E-Mail for contact purposes:"));
		EmailProblem.add(EmailField);
		ValidateEmail.setBackground(Color.RED);
		ValidateEmail.setForeground(Color.WHITE);
		ValidateEmail.setFont(new Font("Cambria", Font.BOLD, 15));
		EmailProblem.add(ValidateEmail);
		EmailProblem.add(new JLabel("Name of your Problem:"));
		EmailProblem.add(ProblemField);
		ValidateProblem.setBackground(Color.RED);
		ValidateProblem.setForeground(Color.WHITE);
		ValidateProblem.setFont(new Font("Cambria", Font.BOLD, 15));
		EmailProblem.add(ValidateProblem);
		tudo.add(EmailProblem);

		/* DESCRIPTION */
		Description.add(new JLabel("Describe the Problem:"));
		Description.add(DescriptionField);
		tudo.add(Description);

		/* DEADLINES */
		Deadlines.add(new JLabel("Set an ideal deadline:"));
		Deadlines.add(IdealDeadline);
		Deadlines.add(new JLabel("Set a maximum deadline:"));
		Deadlines.add(MaximumDeadline);
		tudo.add(Deadlines);

		/* VARIABLES */
		setDecisionVariablesRange();
		Variables.add(new JLabel("Choose the number of variables:"));
		Variables.add(decisionVariables);
		ChooseVariableRange.setEnabled(false);
		Variables.add(ChooseVariableRange);
		tudo.add(Variables);

		/* HELP */
		Help.add(new JLabel(
				"Email us if you don't understand how this platform works or if you're having trouble using it:"));
		Help.add(Ajuda);
		Help.add(new JLabel("Check our F.A.Q. first:"));
		Help.add(FAQ);
		tudo.add(Help);
	}

	private void setDecisionVariablesRange() {
		for (int i = 1; i <= 100; i++) {
			decisionVariables.addItem(i);
		}
	}

	private Variable getCurrentVariable() {
		Variable currentVariable = new Variable("", DataType.INTEGER);
		for (Variable v : variableTable.getVariables()) {
			if (v.getVarName().equals(variablesName.getSelectedItem())) {
				currentVariable = v;
			}
		}
		return currentVariable;
	};

	/*
	 * This is the functional part of the GUI, where all the listeners are made to
	 * react to user input
	 */
	private void initializeActionListeners() {
		/* Allows to re-enable the Email Validator upon re-interacting with the Field */
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
		/*
		 * Allows to re-enable the Problem Name Validator upon re-interacting with the
		 * Field
		 */
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
		/* 
		 * Add Listener to Email Validation Button 
		 */
		ValidateEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!(isValidEmail(EmailField.getText()))) {
					new JOptionPane().showMessageDialog(frame, "Invalid Email. Suggestion: abcde@gmail.com", "WARNING",
							JOptionPane.ERROR_MESSAGE);
					;
				} else {
					ValidateEmail.setBackground(Color.GREEN);
					ValidateEmail.setEnabled(false);
				}
			}
		});
		/*
		 * Add Listener to Problem Validation Button with explanation of how the problem
		 * name should be written
		 */
		ValidateProblem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!(isValidProblem(ProblemField.getText()))) {
					new JOptionPane().showMessageDialog(frame,
							"Invalid Problem name. Should start with a letter a-z or A-Z, currency symbols or underscore"
									+ "\n and can only be composed of letters, numbers, underscores '_' or currency symbols like '$'."
									+ "\nStarting with a number, or any other special character is forbidden. Don't include any spaces in the Problem name.",
									"WARNING", JOptionPane.ERROR_MESSAGE);
					;
				} else {
					ValidateProblem.setBackground(Color.GREEN);
					ValidateProblem.setEnabled(false);
				}
			}
		});
		/* Doesn't allow ideal deadline to be above the maximum deadline! */
		IdealDeadline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ideal = Integer.valueOf(String.valueOf(IdealDeadline.getSelectedItem()).split("\\s")[0]);
				int maximum = Integer.valueOf(String.valueOf(MaximumDeadline.getSelectedItem()).split("\\s")[0]);
				if (ideal > maximum)
					MaximumDeadline.setSelectedIndex(ideal - 1);
			}
		});
		MaximumDeadline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ideal = Integer.valueOf(String.valueOf(IdealDeadline.getSelectedItem()).split("\\s")[0]);
				int maximum = Integer.valueOf(String.valueOf(MaximumDeadline.getSelectedItem()).split("\\s")[0]);
				if (ideal > maximum)
					IdealDeadline.setSelectedIndex(maximum - 1);
			}
		});
		/*
		 * Add Listener to the variable quantity JComboBox, which will then trigger the
		 * whole variable naming and configuration as well as add a JComboBox, a JSlider
		 * and a JLabel respectively
		 */
		decisionVariables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent choice) {
				int howMany = (int) decisionVariables.getSelectedItem();
				JOptionPane decision = new JOptionPane();
				int chosen = decision.showConfirmDialog(frame, "Are you sure you want to procceed with " + howMany
						+ " variable(s)?\nAfter you confirm the number you will be asked to config each variable name, do you still wish to continue?",
						"Confirm Window", JOptionPane.OK_CANCEL_OPTION);
				if (chosen == decision.OK_OPTION) {
					/* Ask the user for the name of the variable group e.g. "E-mail SPAM Filter" */
					String inputVariableGroupName = decision.showInputDialog(
							"What name do you wish to call this variable group\nfor this specific problem:");
					variableTable = new VariableTable(inputVariableGroupName);
					/* For each variable, runs another iteration of that variable's configuration */
					for (int i = 0; i < howMany; i++) {
						boolean passedTheValidation = false;
						String inputVariableName = decision.showInputDialog("Name your variable: ");
						while (isRepeatedVariableName(inputVariableName) || !(isValidVariableName(inputVariableName))) {
							decision.showMessageDialog(frame,
									"The name you typed was already given to a different variable or is invalid. \nTry again as this is not allowed!",
									"ATTENTION", JOptionPane.ERROR_MESSAGE);
							inputVariableName = decision.showInputDialog("Name your variable: ");
						}
						String[] variableTypes = { "Binary", "Integer", "Real" };
						JComboBox variables = new JComboBox(variableTypes);
						decision.showMessageDialog(decisionVariables, variables,
								"Choose the data type you wish to work with:", JOptionPane.QUESTION_MESSAGE);
						if (variables.getSelectedItem().equals("Integer"))
							variableTable.addVariable(new Variable(inputVariableName, DataType.INTEGER));
						else if (variables.getSelectedItem().equals("Real"))
							variableTable.addVariable(new Variable(inputVariableName, DataType.REAL));
						else if (variables.getSelectedItem().equals("Binary"))
							variableTable.addVariable(new Variable(inputVariableName, DataType.BINARY));
					}
					/*
					 * Disable the JComboBox related to the number of variables and create the
					 * JComboBox with all the variables
					 */
					decisionVariables.setEnabled(false);
					String[] decisionVariables = new String[howMany];
					int count = 0;
					/*
					 * Create a String[] with all the Variable names in order to create the
					 * JComboBox
					 */
					for (Variable v : variableTable.getVariables()) {
						decisionVariables[count] = v.getVarName();
						count++;
					}
					variablesName = new JComboBox(decisionVariables);
					/*
					 * With the JComboBox created, we add to the framework the ComboBox of the
					 * variables, the JSlider and the JLabel containing the value on the JSlider.
					 * Also allows the user now to change the Variable Range from default to one of
					 * his choice.
					 */
					Variables.add(new JLabel("Edit your Variable(s):"));
					Variables.add(variablesName);
					variablesName.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent action) {
							variablesWeight.setMinimum(getCurrentVariable().getLowestLimit());
							variablesWeight.setMaximum(getCurrentVariable().getHighestLimit());
							variablesWeight.setValue(getCurrentVariable().getValue());
							updateWeightValue(getCurrentVariable());
						}
					});
					Variables.add(variablesWeight);
					Variables.add(weightValue);
					ChooseVariableRange.setEnabled(true);
					frame.setVisible(true);
				}
			}
		});
		/*
		 * Let's you set up the minimum and maximum value possible for that specific variable
		 */
		ChooseVariableRange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				throwVariableWeightRangeConfigWindow(getCurrentVariable());
			}
		});

		/*
		 * Represent the JSlider's immediate value in a JLabel. Allow value ranges
		 * between -99.9 and 99.9 maximum for double and between -999 and 999 for
		 * integer and binary
		 */
		variablesWeight.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				updateWeightValue(getCurrentVariable());
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
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	/**
	 * Throws a new window with 2 JComboBoxes to define both the minimum and maximum value
	 * that specific variable can have. It gets disposes on close, which means it won't impact main frame's functioning
	 */
	public void throwVariableWeightRangeConfigWindow(Variable whichVariable) {
		Font font = new Font("Cambria",Font.CENTER_BASELINE,20);
		JFrame thrown = new JFrame("Weight Range Configuration of "+"'"+whichVariable.getVarName()+"'");
		JPanel inFrame = new JPanel(new FlowLayout());
		JLabel currentConfig = new JLabel("(Current Minimum = "+whichVariable.getLowestLimit()+")");
		JLabel inPanelMinimum = new JLabel();
		JLabel inPanelMaximum = new JLabel();
		JTextField inputMinimum = new JTextField(4);
		JTextField inputMaximum = new JTextField(4);
		inputMinimum.setSize(4, 20);
		inputMaximum.setSize(4, 20);
		currentConfig.setFont(font);
		inPanelMinimum.setFont(font);
		inPanelMaximum.setFont(font);
		inFrame.add(currentConfig);
		inFrame.add(inPanelMinimum);
		inFrame.add(inputMinimum);
		DataType type = whichVariable.getType();
		switch (type) {
		case BINARY:
			inPanelMinimum.setText("Choose a Value between -999 and 999 and press 'ENTER' to define NEW Minimum:");
			inputMinimum.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!(isValidLimit(inputMinimum.getText(),false))) {
						new JOptionPane().showMessageDialog(thrown, "Only a number between -999 and 999 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
					}else {
						inFrame.removeAll();
						currentConfig.setText("(Current Maximum = "+whichVariable.getHighestLimit()+")");
						inPanelMaximum.setText("Choose a Value between "+inputMinimum.getText()+" and 999 and press 'ENTER' to define NEW Maximum:");
						inFrame.add(currentConfig);
						inFrame.add(inPanelMaximum);
						inFrame.add(inputMaximum);
						thrown.pack();
						thrown.setVisible(true);
						inputMaximum.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if(!(isValidLimit(inputMaximum.getText(),false))||Integer.parseInt(inputMaximum.getText())<Integer.parseInt(inputMinimum.getText())){
									new JOptionPane().showMessageDialog(thrown, "Only a number between "+inputMinimum.getText()+" and 999 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
								}else {
									whichVariable.setLowestLimit(Integer.parseInt(inputMinimum.getText()));
									whichVariable.setHighestLimit(Integer.parseInt(inputMaximum.getText()));
									variablesWeight.setMinimum(getCurrentVariable().getLowestLimit());
									variablesWeight.setMaximum(getCurrentVariable().getHighestLimit());
									/*If the value is still between range then it stays the same, else it gets reset to the average value immediately*/
									if(!(whichVariable.getValue()>=whichVariable.getLowestLimit()&&whichVariable.getValue()<=whichVariable.getHighestLimit())) {
										int average = (int)((whichVariable.getHighestLimit()+whichVariable.getLowestLimit())/2);
										whichVariable.setValue(Integer.toBinaryString(average));
										if(getCurrentVariable().equals(whichVariable)) variablesWeight.setValue(whichVariable.getValue());
									}
									new JOptionPane().showMessageDialog(thrown, "SUCCESSFULLY UPDATED VARIABLE'S RANGE!");
									thrown.dispose();
								}
							}
						});
					}
				}
			});
			break;
		case INTEGER:
			inPanelMinimum.setText("Choose a Value between -999 and 999 and press 'ENTER' to define NEW Minimum:");
			inputMinimum.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!(isValidLimit(inputMinimum.getText(),false))) {
						new JOptionPane().showMessageDialog(thrown, "Only a number between -999 and 999 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
					}else {
						inFrame.removeAll();
						currentConfig.setText("(Current Maximum = "+whichVariable.getHighestLimit()+")");
						inPanelMaximum.setText("Choose a Value between "+inputMinimum.getText()+" and 999 and press 'ENTER' to define NEW Maximum:");
						inFrame.add(currentConfig);
						inFrame.add(inPanelMaximum);
						inFrame.add(inputMaximum);
						thrown.pack();
						thrown.setVisible(true);
						inputMaximum.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if(!(isValidLimit(inputMaximum.getText(),false))||Integer.parseInt(inputMaximum.getText())<Integer.parseInt(inputMinimum.getText())){
									new JOptionPane().showMessageDialog(thrown, "Only a number between "+inputMinimum.getText()+" and 999 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
								}else {
									whichVariable.setLowestLimit(Integer.parseInt(inputMinimum.getText()));
									whichVariable.setHighestLimit(Integer.parseInt(inputMaximum.getText()));
									variablesWeight.setMinimum(getCurrentVariable().getLowestLimit());
									variablesWeight.setMaximum(getCurrentVariable().getHighestLimit());
									/*If the value is still between range then it stays the same, else it gets reset to the average value immediately*/
									if(!(whichVariable.getValue()>=whichVariable.getLowestLimit()&&whichVariable.getValue()<=whichVariable.getHighestLimit())) {
										int average = (int)((whichVariable.getHighestLimit()+whichVariable.getLowestLimit())/2);
										whichVariable.setValue(average);
										if(getCurrentVariable().equals(whichVariable)) variablesWeight.setValue(whichVariable.getValue());
									}
									new JOptionPane().showMessageDialog(thrown, "SUCCESSFULLY UPDATED VARIABLE'S RANGE!");
									thrown.dispose();
								}
							}
						});
					}
				}
			});
			break;
		case REAL:
			currentConfig.setText("(Current Minimum = "+whichVariable.getLowestLimit()/10+")");
			inPanelMinimum.setText("Choose a Value between -99.9 and 99.9 and press 'ENTER' to define NEW Minimum:");
			inputMinimum.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(!(isValidLimit(inputMinimum.getText(),true))) {
						new JOptionPane().showMessageDialog(thrown, "Only a number between -99.9 and 99.9 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
					}else {
						inFrame.removeAll();
						currentConfig.setText("(Current Maximum = "+whichVariable.getHighestLimit()/10+")");
						inPanelMaximum.setText("Choose a Value between "+inputMinimum.getText()+" and 99.9 and press 'ENTER' to define NEW Maximum:");
						inFrame.add(currentConfig);
						inFrame.add(inPanelMaximum);
						inFrame.add(inputMaximum);
						thrown.pack();
						thrown.setVisible(true);
						inputMaximum.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if(!(isValidLimit(inputMaximum.getText(),true))||Double.parseDouble(inputMaximum.getText())<Double.parseDouble(inputMinimum.getText())){
									new JOptionPane().showMessageDialog(thrown, "Only a number between "+inputMinimum.getText()+" and 99.9 allowed.","WARNING",JOptionPane.ERROR_MESSAGE);
								}else {
									whichVariable.setLowestLimit((int) (Double.parseDouble(inputMinimum.getText())*10));
									whichVariable.setHighestLimit((int) (Double.parseDouble(inputMaximum.getText())*10));
									variablesWeight.setMinimum(getCurrentVariable().getLowestLimit());
									variablesWeight.setMaximum(getCurrentVariable().getHighestLimit());
									/*If the value is still between range then it stays the same, else it gets reset to the average value immediately*/
									if(!(whichVariable.getValue()>=whichVariable.getLowestLimit()&&whichVariable.getValue()<=whichVariable.getHighestLimit())) {
										double average = ((whichVariable.getHighestLimit()+whichVariable.getLowestLimit())/2);
										whichVariable.setValue(average);
										if(getCurrentVariable().equals(whichVariable)) variablesWeight.setValue(whichVariable.getValue());
										updateWeightValue(whichVariable);
									}
									new JOptionPane().showMessageDialog(thrown, "SUCCESSFULLY UPDATED VARIABLE'S RANGE!");
									thrown.dispose();
								}
							}
						});
					}
				}
			});
		}
		thrown.add(inFrame);
		thrown.pack();
		thrown.setLocationRelativeTo(null);
		thrown.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		thrown.setVisible(true);
	}

	/**
	 * Sets the value of the Variable to the one the user set on the JSlider "variablesWeight"
	 */
	private void updateWeightValue(Variable var) {
		switch (var.getType()) {
		case BINARY:
			weightValue.setText(Integer.toBinaryString(variablesWeight.getValue()));
			var.setValue(weightValue.getText());
			break;
		case INTEGER:
			weightValue.setText(String.valueOf(variablesWeight.getValue()));
			Integer valueInteger = Integer.valueOf(weightValue.getText());
			var.setValue(valueInteger);
			break;
		case REAL:
			/* If it's a negative number */
			if (String.valueOf(variablesWeight.getValue()).substring(0, 1).equals("-")) {
				/* Example: -100 becomes -10.0 */
				if (String.valueOf(variablesWeight.getValue()).length() == 4) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 3) + "."
							+ String.valueOf(variablesWeight.getValue()).substring(3, 4));
				}
				/* Example: -10 becomes -1.0 */
				if (String.valueOf(variablesWeight.getValue()).length() == 3) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 2) + "."
							+ String.valueOf(variablesWeight.getValue()).substring(2, 3));
				}
				/* Example: -1 becomes -0.1 */
				if (String.valueOf(variablesWeight.getValue()).length() == 2) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 1) + "0."
							+ String.valueOf(variablesWeight.getValue()).substring(1, 2));
				}
			} else { /* If it's a positive number */
				/* Example: 100 becomes 10.0 */
				if (String.valueOf(variablesWeight.getValue()).length() == 3) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 2) + "."
							+ String.valueOf(variablesWeight.getValue()).substring(2, 3));
				}
				/* Example: 10 becomes 1.0 */
				if (String.valueOf(variablesWeight.getValue()).length() == 2) {
					weightValue.setText(String.valueOf(variablesWeight.getValue()).substring(0, 1) + "."
							+ String.valueOf(variablesWeight.getValue()).substring(1, 2));
				}
				/* Example: 1 becomes 0.1 */
				if (String.valueOf(variablesWeight.getValue()).length() == 1) {
					weightValue.setText("0." + String.valueOf(variablesWeight.getValue()).substring(0, 1));
				}
			}
			Double valueDouble = Double.valueOf(weightValue.getText());
			var.setValue(valueDouble);
		}
	}

	/**
	 * 
	 * @param email
	 * @return
	 * Validates a generic e-mail address
	 */
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	/**
	 * 
	 * @param problem
	 * @return
	 * Validates a Problem naming with JAVA Class restrictions 
	 */
	private boolean isValidProblem(String problem) {
		String problemRegex = "^[a-zA-Z\\p{Sc}_]+([a-zA-Z0-9\\p{Sc}_]*)$";
		Pattern pat = Pattern.compile(problemRegex);
		if (problem == null)
			return false;
		return pat.matcher(problem).matches();

	}

	/**
	 * 
	 * @param variable
	 * @return
	 * Validates if the variable name is composed by a-zA-Z0-9 characters only
	 */
	private boolean isValidVariableName(String variable) {
		String problemRegex = "^[\\w]+$";
		Pattern pat = Pattern.compile(problemRegex);
		if (variable == null)
			return false;
		return pat.matcher(variable).matches();
	}

	/**
	 * 
	 * @param nameToTest
	 * @return
	 * Tests out if a given variable name is REPEATED or UNIQUE 
	 */
	private boolean isRepeatedVariableName(String nameToTest) {
		int count = 0;
		for (Variable v : variableTable.getVariables()) {
			if (v.getVarName().equals(nameToTest))
				count++;
		}
		if (count == 0)
			return false;
		return true;
	}

	/**
	 * 
	 * @param input
	 * @return
	 * Validates the input of MinimumRange and MaximumRange a Weight(Variable Value) can have (-999 to 999 or -99.9 to 99.9 in case of Real)
	 */
	private boolean isValidLimit(String input, boolean isRealInput) {
		String problemRegex = "^-?[0-9]{1,3}$";
		if(isRealInput) problemRegex = "^-?[0-9]{1,2}\\.\\d?$";
		Pattern pat = Pattern.compile(problemRegex);
		if (input == null)
			return false;
		return pat.matcher(input).matches();
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.start();
	}
}
