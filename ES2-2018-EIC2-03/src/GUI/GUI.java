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
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import objects.DataType;
import objects.Variable;
import objects.VariableTable;

public class GUI {
	/*Containers*/
	private JFrame varsetup = new JFrame("New Variable Group Configuration");
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
	private boolean CONFIGURATION_WINDOW_ALREADY_ONGOING = false;
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
	private Font VariableFont = new Font("Cambria", Font.BOLD,20);
	/*Help Elements*/
	private JButton FAQ = new JButton("F.A.Q.");
	private JButton Ajuda = new JButton("Ask for Help");
	/*Memory Data*/
	private DefaultTableModel tableModel = new DefaultTableModel(0, 5);
	private JTable configurationTable;
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
				if(!(varsetup.isActive()) && !(SETUP_IN_MEMORY)) {
					throwVariableTableConfigWindow(null);
				}else {
					int choice = JOptionPane.showConfirmDialog(null, "There's already data stored, if you don't want to lose that data then cancel the operation. Do you wish to continue?");
					if(choice==JOptionPane.OK_OPTION) {
						varsetup.setTitle("New Variable Group Configuration");
						variableTable.clear();
						throwVariableTableConfigWindow(null);
					}
				}

			}
		});
		/*
		 * Loads the Setup in current memory
		 */
		openConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!(varsetup.isActive()) && SETUP_IN_MEMORY) throwVariableTableConfigWindow(null);
			}
		});
		/*
		 * Loads a Setup fron a XML file
		 */
		loadConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser definePath = new JFileChooser(new File("../Saved Configuration/"));
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				/*Without this you'd have to click the left icon of a file to select it, clicking on it's text would edit it.*/
				definePath.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				definePath.setFileFilter(xmlfilter);
				int result = definePath.showOpenDialog(null);
				String pathname;
				if(result==JFileChooser.CANCEL_OPTION) JOptionPane.showMessageDialog(null, "Operation Canceled!");
				else {
					try {
						pathname = definePath.getSelectedFile().getCanonicalPath();
						System.out.println(pathname);
						throwVariableTableConfigWindow(pathname);
					} catch (IOException e) {
					}
				}
			};
		});


		}

		/**
		 * A JTable containing all the database of the variables and their relevant information.
		 * Also allows users to save the data on memory and access it again and again, without
		 * needing to export it into XML immediately, meaning that if the user changes his mind
		 * all he has to do is not save as XML.
		 * In case the user loaded from an XML, not to worry as well, even if he changes the values
		 * withing our application it won't affect the original file unless the user chooses so.
		 * @param pathToXML - The path leading to the XML. If null will use the application's internal memory to keep temporary data.
		 */
		public void throwVariableTableConfigWindow(String pathToXML) {
			JPanel insideFrame = new JPanel();
			JPanel forEditSaveButtons = new JPanel(new GridLayout(12,1));
			JButton editButton = new JButton("Edit Variable Group Name");
			JButton saveButton = new JButton("Save");
			JButton addRow = new JButton("Add Variable");
			JButton removeRow = new JButton("Remove Selected Variable(s)");

			editButton.setFont(VariableFont);
			saveButton.setFont(VariableFont);
			addRow.setFont(VariableFont);
			removeRow.setFont(VariableFont);

			forEditSaveButtons.add(editButton);
			forEditSaveButtons.add(saveButton);
			forEditSaveButtons.add(addRow);
			forEditSaveButtons.add(removeRow);
			/*
			 * Defining all the listeners for the SWING Components of the Configuration Window
			 */
			addRow.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] options= {"Binary","Integer","Real"};
					JComboBox choice = new JComboBox(options);
					JOptionPane.showMessageDialog(null, choice, "Choose Data Type for this Variable", JOptionPane.QUESTION_MESSAGE);
					Object[] newRow = {"",choice.getSelectedItem(),"","",""};
					tableModel.addRow(newRow);
				}
			});
			removeRow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					while(configurationTable.getSelectedRows().length!=0) {
						tableModel.removeRow(configurationTable.getSelectedRows()[0]);
					}
				}
			});
			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String input = JOptionPane.showInputDialog("Select a new Name for this Variable Group Name. Can't be \"New Variable Group Configuration\"");
					if(input != null && !input.equals("") && !input.equals("New Variable Group Name")) {
						varsetup.setTitle(input);
						if(variableTable==null)variableTable = new VariableTable(input);
						else variableTable.setVariableGroupName(input);
					}else JOptionPane.showMessageDialog(null, "Operation Canceled!","",JOptionPane.WARNING_MESSAGE);
				}
			});
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(varsetup.getTitle().equals("New Variable Group Configuration")) {
						String input = JOptionPane.showInputDialog("Choose a name for this Group of Variables\nExample: Spam Filter Configuration\nLeaving this field blank cancels the operation.");
						if(input == null || input.equals("")) {
							JOptionPane.showMessageDialog(null, "Operation Canceled!", "", JOptionPane.WARNING_MESSAGE);
							return;
						}else {
							varsetup.setTitle(input);
							variableTable = new VariableTable(input);
						}
					}
					variableTable.getVariables().clear();
					for(int i=0;i<configurationTable.getRowCount();i++) {
						Variable toAdd;
						if(configurationTable.getValueAt(i, 1).equals("Binary")) {
							toAdd=new Variable((String)(configurationTable.getValueAt(i, 0)), DataType.BINARY);
							if(!isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)),DataType.BINARY)) {
								JOptionPane.showMessageDialog(null,"Invalid Value on the Variable '"+configurationTable.getValueAt(i, 0)+"', please change to Binary.");
								return;
							}
							toAdd.setValue(String.valueOf(configurationTable.getValueAt(i, 2)));
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 3)), DataType.BINARY)) {
								toAdd.setLowestLimit((Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 3)))));
							}
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 4)), DataType.BINARY)) {
								toAdd.setHighestLimit((Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 4)))));
							}
							variableTable.addVariable(toAdd);
						}
						if(configurationTable.getValueAt(i, 1).equals("Integer")) {
							toAdd=new Variable((String)(configurationTable.getValueAt(i, 0)), DataType.INTEGER);
							if(!isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)),DataType.INTEGER)) {
								JOptionPane.showMessageDialog(null,"Invalid Value on the Variable '"+configurationTable.getValueAt(i, 0)+"', please change to Integer.");
								return;
							}
							toAdd.setValue(Integer.valueOf(String.valueOf((configurationTable.getValueAt(i, 2)))));
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 3)), DataType.INTEGER)) {
								toAdd.setLowestLimit(Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 3))));
							}
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 4)), DataType.INTEGER)) {
								toAdd.setHighestLimit(Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 4))));
							}
							variableTable.addVariable(toAdd);
						}
						if(configurationTable.getValueAt(i, 1).equals("Real")) {
							toAdd=new Variable((String)(configurationTable.getValueAt(i, 0)), DataType.REAL);
							if(!isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)),DataType.REAL)) {
								JOptionPane.showMessageDialog(null,"Invalid Value on the Variable '"+configurationTable.getValueAt(i, 0)+"', please change to Real.");
								return;
							}
							toAdd.setValue(Double.valueOf(String.valueOf((configurationTable.getValueAt(i, 2)))));
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 3)), DataType.REAL)) {
								toAdd.setLowestRealLimit(Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 3))));
							}
							if(isValidValue(String.valueOf(configurationTable.getValueAt(i, 4)), DataType.REAL)) {
								toAdd.setHighestRealLimit(Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 4))));
							}
							variableTable.addVariable(toAdd);
						}
					}
					SETUP_IN_MEMORY = true;
				}
			});
			/*
			 * Here is where it is validated if either we are creating a new Config,
			 * if we are loading a config from a XML or just loading from memory.
			 */
			while(tableModel.getRowCount()>0) tableModel.removeRow(0);
			if(pathToXML==null && !SETUP_IN_MEMORY) {
				varsetup = new JFrame("New Variable Group Configuration");
				String[] ColumnIdentifiers = {"Name", "Data Type", "Value", "Lowest Limit", "Highest Limit"};
				tableModel.setColumnIdentifiers(ColumnIdentifiers);
			}else if(pathToXML==null && SETUP_IN_MEMORY){
				varsetup = new JFrame(varsetup.getTitle());
				for(Variable variable:variableTable.getVariables()) {
					tableModel.addRow(fromVariableTableIntoJTable(variable));
				}
			}else {
				varsetup = new JFrame(varsetup.getTitle());
				fromXMLIntoJTable(pathToXML);

			}
			/*
			 * Now that we know which kind of situation we're in we prepare to show our
			 * Configuration Table
			 */
			configurationTable = new JTable(tableModel) {
				@Override
				public boolean isCellEditable(int row,int column){  
					if(column==1) return false;  
					return true;
				}
			};
			configurationTable.setBackground(Color.pink);
			configurationTable.setFont(new Font("Cambria",Font.TRUETYPE_FONT,15));
			configurationTable.getTableHeader().setResizingAllowed(false);
			configurationTable.getTableHeader().setReorderingAllowed(false);
			JScrollPane scroller = new JScrollPane(configurationTable);
			scroller.setPreferredSize(new Dimension(1000,400));
			insideFrame.add(scroller);
			insideFrame.add(forEditSaveButtons);
			varsetup.add(insideFrame);
			varsetup.setResizable(false);
			varsetup.setDefaultCloseOperation(varsetup.DISPOSE_ON_CLOSE);
			varsetup.pack();
			varsetup.setLocationRelativeTo(frame);
			varsetup.setLocation(varsetup.getX(), varsetup.getY()+frame.getHeight());
			varsetup.setVisible(true);
		}

		private Object[] fromVariableTableIntoJTable(Variable v) {
			switch(v.getType()) {
			case REAL: 
				Object[] newReal = {v.getVarName(),"Real",v.getRealValue(),v.getLowestRealLimit(),v.getHighestRealLimit()};
				return newReal;
			case INTEGER:
				Object[] newInteger = {v.getVarName(),"Integer",v.getIntegerValue(),v.getLowestLimit(),v.getHighestLimit()};
				return newInteger;
			case BINARY:
				Object[] newBinary = {v.getVarName(),"Binary", v.getBinaryValue(),v.getLowestLimit(),v.getHighestLimit()};
				return newBinary;
			}
			return null;
		}

		/**
		 * Create the JTable based on a XML file from the path provided.
		 * @param pathname - The path that leads to the XML File
		 */
		private void fromXMLIntoJTable(String pathname) {
			validateAllVariables();
			//Weekend work
		}

		/**
		 * Validates every single variable, making sure of 4 conditions:
		 * 1 - Name is Valid and not repeated. Is according to Java class name convention
		 * 2 - Value respects the DataType and exists between the Lowest Limit and Highest Limit of that Variable
		 * 3 - The Lowest Limit respects the DataType and is below the Highest Limit
		 * 4 - The Highest Limit respects the DataType and is above the Highest Limit
		 */
		private void validateAllVariables() {
			//Weekend work
		}

		/**
		 * @param email
		 * @return
		 * Validates a generic e-mail address
		 */
		private boolean isValidEmail(String email) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
				return false;
			return pat.matcher(email).matches();
		}

		/**
		 * @param problem The problem name we need to validate.
		 * @return returns a boolean validating(true) or not(false) the problem provided
		 */
		private boolean isValidProblem(String problem) {
			String problemRegex = "^[A-Z]+[a-zA-Z0-9]*$";
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
		private boolean isValidValue(String input, DataType datatype) {
			String problemRegex ="";
			switch(datatype) {
			case BINARY: problemRegex="^[0-1]+$";
			break;
			case INTEGER: problemRegex = "^-?[0-9]+$";
			break;
			case REAL: problemRegex = "^-?[0-9]+\\.?[0-9]*$";
			}
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
