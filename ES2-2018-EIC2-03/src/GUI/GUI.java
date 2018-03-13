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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import objects.DataType;
import objects.Variable;
import objects.VariableTable;

public class GUI {
	/*Containers*/
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel tudo = new JPanel(new GridLayout(5, 1));
	private JPanel EmailProblem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JPanel Deadlines = new JPanel(new FlowLayout());
	private JPanel Variables = new JPanel(new FlowLayout());
	private JPanel Help = new JPanel(new FlowLayout());
	/*All Conditions*/
	private boolean EMAIL_IS_VALID = false;
	private boolean PROBLEM_IS_VALID = false;
	private boolean SETUP_IN_MEMORY = false;
	private boolean CHANGES_ARE_SAVED = false;
	/*Email & Problem Definition Elements*/
	private JTextField EmailField = new JTextField(26);
	private JButton ValidateEmail = new JButton("Validate Email");
	private JTextField ProblemField = new JTextField(20);
	private JButton ValidateProblem = new JButton("Validate Problem");
	/*Problem Description Element*/
	private JTextArea DescriptionField = new JTextArea(2, 100);
	/*Deadline Setting Elements*/
	private String[] deadlines = { "1 Day", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "7 Days", "8 Days",
			"9 Days", "10 Days" };
	private JComboBox IdealDeadline = new JComboBox(deadlines);
	private JComboBox MaximumDeadline = new JComboBox(deadlines);
	/*Variables Setup Elements*/
	private JButton newConfig = new JButton("Create New Setup");
	private JButton openConfig = new JButton("Open Current Setup");
	private JButton loadConfig = new JButton("Load Setup from XML");
	private JButton saveConfig = new JButton("Save Setup to XML");
	Font VariableFont = new Font("Cambria", Font.BOLD,20);
	JTable configurationTable;
	/*Help Elements*/
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
		frame.setLocation(frame.getX(), 0);
		frame.setVisible(true);
	}

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
		newConfig.setFont(VariableFont);
		openConfig.setFont(VariableFont);
		loadConfig.setFont(VariableFont);
		saveConfig.setFont(VariableFont);
		Variables.add(newConfig);
		Variables.add(openConfig);
		Variables.add(loadConfig);
		Variables.add(saveConfig);
		tudo.add(Variables);
		/* HELP */
		Help.add(new JLabel("Email us if you don't understand how this platform works or if you're having trouble using it:"));
		Help.add(Ajuda);
		Help.add(new JLabel("Check our F.A.Q. first:"));
		Help.add(FAQ);
		tudo.add(Help);
	}

	/*
	 * This is the functional part of the GUI, where all the listeners are made to
	 * react to user input
	 */
	private void initializeActionListeners() {
		/* 
		 * Allows to re-enable the Email Validator upon re-interacting with the Field 
		 */
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
		 * Add Listener to Email Validation Button with explanation of how the email
		 * should be written
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
		/* 
		 * Doesn't allow ideal deadline to be above the maximum deadline! 
		 */
		IdealDeadline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ideal = Integer.valueOf(String.valueOf(IdealDeadline.getSelectedItem()).split("\\s")[0]);
				int maximum = Integer.valueOf(String.valueOf(MaximumDeadline.getSelectedItem()).split("\\s")[0]);
				if (ideal > maximum)
					MaximumDeadline.setSelectedIndex(ideal - 1);
			}
		});
		/*
		 * Doesn't allow maximum deadline to be below ideal deadline!
		 */
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
		 * Throws the Setup Configuration when NEW is selected
		 */
		newConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				throwVariableTableConfigWindow(null);
			}
		});
		/*
		 * Throws the Setup Configuration when NEW is selected
		 */
		loadConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				throwVariableTableConfigWindow(null);
			}
		});


	}


	public void throwVariableTableConfigWindow(String pathToXML) {
		JFrame thrown = new JFrame("New Variable Group Configuration");
		JPanel insideFrame = new JPanel();
		JPanel forEditSaveButtons = new JPanel(new GridLayout(12,1));
		JButton saveButton = new JButton("Save");
		JButton editButton = new JButton("Edit");
		JButton addRow = new JButton("Add Variable");
		JButton removeRow = new JButton("Remove Selected Variable(s)");
		JButton ChooseVariableRange = new JButton("Choose Variable Range");

		editButton.setFont(VariableFont);
		saveButton.setFont(VariableFont);
		addRow.setFont(VariableFont);
		removeRow.setFont(VariableFont);
		ChooseVariableRange.setFont(VariableFont);

		forEditSaveButtons.add(editButton);
		forEditSaveButtons.add(saveButton);
		forEditSaveButtons.add(addRow);
		forEditSaveButtons.add(removeRow);
		forEditSaveButtons.add(ChooseVariableRange);

		DefaultTableModel tableModel = new DefaultTableModel(0, 5);
		String[] ColumnIdentifiers = {"Name", "Data Type", "Value", "Lowest Limit", "Highest Limit"};
		tableModel.setColumnIdentifiers(ColumnIdentifiers);

		/*
		 * Defining all the listeners for the SWING Components
		 */

		addRow.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] options= {"Binary","Integer","Real"};
				JComboBox choice = new JComboBox(options);
				JOptionPane.showMessageDialog( null, choice, "Choose Data Type for this Variable", JOptionPane.QUESTION_MESSAGE);
				Object[] newRow = {"",choice.getSelectedItem(),"","",""};
				tableModel.addRow(newRow);
			}
		});
		removeRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				while(configurationTable.getSelectedRows().length!=0)
					tableModel.removeRow(configurationTable.getSelectedRows()[0]);
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(thrown.getTitle().equals("New Variable Group Configuration")) {
					String input = JOptionPane.showInputDialog("Choose a name for this Group of Variables\nExample: Spam Filter Configuration");
					thrown.setTitle(input);
					variableTable = new VariableTable(input);
				}
				for(int i=0;i<configurationTable.getRowCount();i++) {
					if(configurationTable.getValueAt(i, 1).equals("Binary"))
						variableTable.addVariable(new Variable((String)(configurationTable.getValueAt(i, 0)), DataType.BINARY));
					if(configurationTable.getValueAt(i, 1).equals("Integer"))
						variableTable.addVariable(new Variable((String)configurationTable.getValueAt(i, 0),DataType.INTEGER));
					if(configurationTable.getValueAt(i, 1).equals("Real"))
						variableTable.addVariable(new Variable((String)configurationTable.getValueAt(i, 0),DataType.REAL));
				}
			}
		});


		if(pathToXML==null) {
			configurationTable = new JTable(tableModel) {
				@Override
				public boolean isCellEditable(int row,int column){  
					if(column==1||column==3||column==4) return false;  
					return true;
				}
			};
		}else {
			//To be Continued with a method loadFromXML(pathToXML, ) that will create the JTable based on a file and the path provided.
		}


		configurationTable.setBackground(Color.pink);
		configurationTable.setFont(new Font("Cambria",Font.TRUETYPE_FONT,15));
		configurationTable.getTableHeader().setResizingAllowed(false);
		configurationTable.getTableHeader().setReorderingAllowed(false);


		JScrollPane scroller = new JScrollPane(configurationTable);
		scroller.setPreferredSize(new Dimension(1000,400));
		insideFrame.add(scroller);
		insideFrame.add(forEditSaveButtons);
		thrown.add(insideFrame);
		thrown.setResizable(false);
		thrown.setDefaultCloseOperation(thrown.DISPOSE_ON_CLOSE);
		thrown.pack();
		thrown.setLocationRelativeTo(frame);
		thrown.setLocation(thrown.getX(), thrown.getY()+frame.getHeight());
		thrown.setVisible(true);
	}

	/**
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
	 * @param input
	 * @return
	 * Validates the input of MinimumRange and MaximumRange a Weight(Variable Value) 
	 * can have (-999 to 999 or -99.9 to 99.9 in case of Real)
	 */
	private boolean isValidLimit(String input, boolean isRealInput) {
		String problemRegex = "^-?[0-9]+$";
		if(isRealInput) problemRegex = "^-?[0-9]+\\.?[0-9]*$";
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
