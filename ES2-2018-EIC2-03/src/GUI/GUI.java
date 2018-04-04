package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import objects.DataType;
import objects.SendMail;
import objects.Variable;
import objects.VariableTable;
import xml.XMLParser;

@SuppressWarnings("all")
public class GUI {
	/* Containers */
	private JFrame varsetup = new JFrame("New Variable Group Configuration");
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel master = new JPanel(new GridLayout(6, 1));
	private JPanel EmailProblem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JPanel Deadlines = new JPanel(new FlowLayout());
	private JPanel Variables = new JPanel(new FlowLayout());
	private JPanel Help = new JPanel(new FlowLayout());
	private JPanel Optimization = new JPanel(new FlowLayout());
	/* All Conditions */
	private boolean CONFIGURATION_WINDOW_ALREADY_ONGOING = false;
	private boolean SETUP_IN_MEMORY = false;
	private boolean FAQ_WINDOW_ALREADY_ONGOING = false;
	private boolean HELP_WINDOW_ALREADY_ONGOING = false;
	/* Email & Problem Definition Elements */
	private JTextField EmailField = new JTextField(26);
	private JButton ValidateEmail = new JButton("Validate Email");
	private JTextField ProblemField = new JTextField(20);
	private JButton ValidateProblem = new JButton("Validate Problem");
	/* Problem Description Element */
	private JTextArea DescriptionField = new JTextArea(2, 100);
	/* Deadline Setting Elements */
	private String[] deadlines = { "1 Day", "2 Days", "3 Days", "4 Days", "5 Days", "6 Days", "7 Days", "8 Days",
			"9 Days", "10 Days" };
	private JComboBox IdealDeadline = new JComboBox(deadlines);
	private JComboBox MaximumDeadline = new JComboBox(deadlines);
	/* Variables Setup Elements */
	private JButton newConfig = new JButton("Create New Setup");
	private JButton openConfig = new JButton("Open Current Setup");
	private JButton loadConfig = new JButton("Load Setup from XML");
	private JButton saveConfig = new JButton("Save Setup to XML");
	private Font VariableFont = new Font("Cambria", Font.BOLD, 20);
	/* Help Elements */
	private JButton faq = new JButton("F.A.Q.");
	private JButton help = new JButton("Ask for Help");
	/* Memory Data */
	private DefaultTableModel tableModel = new DefaultTableModel(0, 5);
	private JTable configurationTable;
	private VariableTable variableTable;
	/* XML Parser */
	private XMLParser XML = new XMLParser();
	/* Optimization Buttons */
	private JButton start = new JButton("START THE OPTIMIZATION PROCESS");

	public void start() {
		initializeFields();
		initializeActionListeners();
		XML.openConfiguration("config.xml");
		frame.add(master);
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
		master.add(EmailProblem);
		/* DESCRIPTION */
		Description.add(new JLabel("Describe the Problem:"));
		Description.add(DescriptionField);
		master.add(Description);
		/* DEADLINES */
		Deadlines.add(new JLabel("Set an ideal deadline:"));
		Deadlines.add(IdealDeadline);
		Deadlines.add(new JLabel("Set a maximum deadline:"));
		Deadlines.add(MaximumDeadline);
		master.add(Deadlines);
		/* VARIABLES */
		newConfig.setFont(VariableFont);
		openConfig.setFont(VariableFont);
		loadConfig.setFont(VariableFont);
		saveConfig.setFont(VariableFont);
		Variables.add(newConfig);
		Variables.add(openConfig);
		Variables.add(loadConfig);
		Variables.add(saveConfig);
		master.add(Variables);
		/* HELP */
		Help.add(new JLabel(
				"Email us if you don't understand how this platform works or if you're having trouble using it:"));
		Help.add(help);
		Help.add(new JLabel("Check our F.A.Q. first:"));
		Help.add(faq);
		master.add(Help);
		/* OPTIMIZATION */
		start.setFont(VariableFont);
		start.setEnabled(false);
		start.setBackground(Color.RED);

		start.setToolTipText("Won't work until both the Email and the Problem Names are validated.");
		Optimization.add(start);
		master.add(Optimization);
	}

	/*
	 * This is the functional part of the GUI, where all the listeners are made to
	 * react to user input
	 */
	private void initializeActionListeners() {
		/*
		 * Allows to re-enable the Email Validator upon re-interacting with the Field
		 */
		EmailField.addKeyListener(new KeyAdapter() {
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
		ProblemField.addKeyListener(new KeyAdapter() {
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
					if(ValidateProblem.getBackground().equals(Color.GREEN)&&ValidateEmail.getBackground().equals(Color.GREEN)) {
						start.setBackground(Color.GREEN);
						start.setEnabled(true);
					}
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
					if(ValidateProblem.getBackground().equals(Color.GREEN)&&ValidateEmail.getBackground().equals(Color.GREEN)) {
						start.setBackground(Color.GREEN);
						start.setEnabled(true);
					}
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
				if (!(varsetup.isActive()) && !(SETUP_IN_MEMORY)) {
					if(!CONFIGURATION_WINDOW_ALREADY_ONGOING) throwVariableTableConfigWindow(null);
				} else {
					int choice = JOptionPane.showConfirmDialog(null,
							"There's already data stored, if you don't want to lose that data then cancel the operation. Do you wish to continue?");
					if (choice == JOptionPane.OK_OPTION) {
						if(!CONFIGURATION_WINDOW_ALREADY_ONGOING) {
							varsetup.setTitle("New Variable Group Configuration");
							variableTable.clear();
							throwVariableTableConfigWindow(null);
						}
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
				if (!(varsetup.isActive()) && SETUP_IN_MEMORY)
					if(!CONFIGURATION_WINDOW_ALREADY_ONGOING) throwVariableTableConfigWindow(null);
			}
		});


		/*
		 * Loads a Setup fron a XML file
		 */
		loadConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser definePath = new JFileChooser(new File("../Problems/"));
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				/*
				 * Without this you'd have to click the left icon of a file to select it,
				 * clicking on it's text would edit it.
				 */
				definePath.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				definePath.setFileFilter(xmlfilter);
				int result = definePath.showOpenDialog(null);
				String pathname;
				if (result == JFileChooser.CANCEL_OPTION)
					JOptionPane.showMessageDialog(null, "Operation Canceled!");
				else {
					try {
						pathname = definePath.getSelectedFile().getCanonicalPath();
						if(!CONFIGURATION_WINDOW_ALREADY_ONGOING) throwVariableTableConfigWindow(pathname);
					} catch (IOException e) {
					}
				}
			};
		});
		/* Saves setup into a XML file */
		saveConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!SETUP_IN_MEMORY || ValidateProblem.isEnabled()) {
					JOptionPane.showMessageDialog(null, "The Problem Name is not validated yet OR There is no setup currently in memory");
				}
				else {
					XML.saveProblem(ProblemField.getText(), variableTable);

				}
			}
		});
		/* Sends an e-mail to the staff to clarify any doubts with the usage of the application */
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!HELP_WINDOW_ALREADY_ONGOING) throwHelp();
			}
		});
		/* Opens the FAQ */
		faq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!FAQ_WINDOW_ALREADY_ONGOING) throwFAQ();
			}
		});
		/* Initiates the Optimization process*/
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(SETUP_IN_MEMORY)	throwOptimizationProcess();
				else JOptionPane.showMessageDialog(start, "There is no setup currently in memory!\n Either create a new one or load one from a XML File");
			}
		});
	}

	/**
	 * Utility code -- used to convert an array of <code> char </code> to
	 * <code> String </code>
	 */
	private String charArrayToString(char[] array) {
		String string = "";
		for (int i = 0; i < array.length; i++) {
			string += array[i];
		}
		return string;
	}

	/**
	 * Utility code -- used to trim the password input from the user, to make sure
	 * it's not fully composed of white spaces.
	 */
	private char[] trimCharArray(char[] toTrim) {
		String array = "";
		for (int i = 0; i < toTrim.length; i++) {
			if (toTrim[i] != ' ')
				array += toTrim[i];
		}

		return array.toCharArray();
	}

	/**
	 * A JTable containing all the database of the variables and their relevant
	 * information. Also allows users to save the data on memory and access it again
	 * and again, without needing to export it into XML immediately, meaning that if
	 * the user changes his mind all he has to do is not save as XML. In case the
	 * user loaded from an XML, not to worry as well, even if he changes the values
	 * withing our application it won't affect the original file unless the user
	 * chooses so.
	 * 
	 * @param pathToXML
	 *            - The path leading to the XML. If null will use the application's
	 *            internal memory to keep temporary data.
	 */
	public void throwVariableTableConfigWindow(String pathToXML) {
		JPanel insideFrame = new JPanel();
		JPanel forEditSaveButtons = new JPanel(new GridLayout(12, 1));
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
		 * Defining all the listeners for the SWING Components of the Configuration
		 * Window
		 */
		addRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] options = { "Binary", "Integer", "Real" };
				JComboBox choice = new JComboBox(options);
				JOptionPane.showMessageDialog(null, choice, "Choose Data Type for this Variable",
						JOptionPane.QUESTION_MESSAGE);

				if(choice.getSelectedItem().equals("Binary")) {
					Object[] newRow = {"", choice.getSelectedItem(), "", 0, 1 };
					tableModel.addRow(newRow);
				}
				else {
					Object[] newRow = { "", choice.getSelectedItem(), "", "", "" };
					tableModel.addRow(newRow);
				}

			}
		});
		removeRow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				while (configurationTable.getSelectedRows().length != 0) {
					tableModel.removeRow(configurationTable.getSelectedRows()[0]);
				}
			}
		});
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(
						"Select a new Name for this Variable Group Name. Can't be \"New Variable Group Configuration\"");
				if (input != null && !input.equals("") && !input.equals("New Variable Group Name")) {
					varsetup.setTitle(input);
					if (variableTable == null)
						variableTable = new VariableTable(input);
					else
						variableTable.setVariableGroupName(input);
				} else
					JOptionPane.showMessageDialog(null, "Operation Canceled!", "", JOptionPane.WARNING_MESSAGE);
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> nomes = new ArrayList<>();
				for (int i = 0; i < configurationTable.getRowCount(); i++) {
					for(String uniqueName : nomes) {
						if(uniqueName.equals(configurationTable.getValueAt(i, 0))){
							JOptionPane.showMessageDialog(null, "REPEATED VARIABLE NAME: "+uniqueName+"\n Please change in order to save the configuration!", "", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					nomes.add((String) configurationTable.getValueAt(i, 0));
				}
				if (varsetup.getTitle().equals("New Variable Group Configuration")) {
					String input = JOptionPane.showInputDialog(
							"Choose a name for this Group of Variables\nExample: Spam Filter Configuration\nLeaving this field blank cancels the operation.");
					if (input == null || input.equals("")) {
						JOptionPane.showMessageDialog(null, "Operation Canceled!", "", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						varsetup.setTitle(input);
						variableTable = new VariableTable(input);
					}
				}
				variableTable.getVariables().clear();
				for (int i = 0; i < configurationTable.getRowCount(); i++) {
					Variable toAdd;
					if (configurationTable.getValueAt(i, 1).equals("Binary")) {
						toAdd = new Variable((String) (configurationTable.getValueAt(i, 0)), DataType.BINARY);
						if(String.valueOf(configurationTable.getValueAt(i, 2)).equals("") || !isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)), DataType.BINARY) ||
								Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))<toAdd.getLowestBinaryLimit() ||
								Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))>toAdd.getHighestBinaryLimit()){
							toAdd.setValue(toAdd.getLowestBinaryLimit());
						}else {
							toAdd.setValue(Double.valueOf(String.valueOf((configurationTable.getValueAt(i, 2)))));	
						}
						variableTable.addVariable(toAdd);
					}
					if (configurationTable.getValueAt(i, 1).equals("Integer")) {
						toAdd = new Variable((String) (configurationTable.getValueAt(i, 0)), DataType.INTEGER);
						if (isValidValue(String.valueOf(configurationTable.getValueAt(i, 3)), DataType.INTEGER)) {
							toAdd.setLowestLimit(Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 3))));
						}
						if (isValidValue(String.valueOf(configurationTable.getValueAt(i, 4)), DataType.INTEGER)) {
							toAdd.setHighestLimit(Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 4))));
							if(toAdd.getHighestLimit()<toAdd.getLowestLimit()) {
								toAdd.setHighestLimit(toAdd.getLowestLimit()+1);
							}
						}
						if(String.valueOf(configurationTable.getValueAt(i, 2)).equals("") || !isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)), DataType.INTEGER) ||
								Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))<toAdd.getLowestLimit() ||
								Integer.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))>toAdd.getHighestLimit()){
							toAdd.setValue(toAdd.getLowestLimit());
						}else {
							toAdd.setValue(Double.valueOf(String.valueOf((configurationTable.getValueAt(i, 2)))));	
						}
						variableTable.addVariable(toAdd);
					}
					if (configurationTable.getValueAt(i, 1).equals("Real")) {
						toAdd = new Variable((String) (configurationTable.getValueAt(i, 0)), DataType.REAL);
						if (isValidValue(String.valueOf(configurationTable.getValueAt(i, 3)), DataType.REAL)) {
							toAdd.setLowestRealLimit(Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 3))));
						}
						if (isValidValue(String.valueOf(configurationTable.getValueAt(i, 4)), DataType.REAL)) {
							toAdd.setHighestRealLimit(Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 4))));
							if(toAdd.getHighestRealLimit()<toAdd.getLowestRealLimit()) {
								toAdd.setHighestRealLimit(toAdd.getLowestRealLimit()+1);
							}
						}
						if(String.valueOf(configurationTable.getValueAt(i, 2)).equals("") || !isValidValue(String.valueOf(configurationTable.getValueAt(i, 2)), DataType.REAL) ||
								Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))<toAdd.getLowestRealLimit() ||
								Double.valueOf(String.valueOf(configurationTable.getValueAt(i, 2)))>toAdd.getHighestRealLimit()){
							toAdd.setValue(toAdd.getLowestRealLimit());
						}else {
							toAdd.setValue(Double.valueOf(String.valueOf((configurationTable.getValueAt(i, 2)))));	
						}
						variableTable.addVariable(toAdd);
					}
				}
				SETUP_IN_MEMORY = true;
			}
		});
		/*
		 * Here is where it is validated if either we are creating a new Config, if we
		 * are loading a config from a XML or just loading from memory.
		 */
		while (tableModel.getRowCount() > 0)
			tableModel.removeRow(0);
		if (pathToXML == null && !SETUP_IN_MEMORY) {
			varsetup = new JFrame("New Variable Group Configuration");
			String[] ColumnIdentifiers = { "Name", "Data Type", "Best Solution Value", "Lowest Limit", "Highest Limit" };
			tableModel.setColumnIdentifiers(ColumnIdentifiers);
		} else if (pathToXML == null && SETUP_IN_MEMORY) {
			varsetup = new JFrame(varsetup.getTitle());
			for (Variable variable : variableTable.getVariables()) {
				tableModel.addRow(fromVariableTableIntoJTable(variable));
			}
		} else {
			String[] ColumnIdentifiers = { "Name", "Data Type", "Best Solution Value", "Lowest Limit", "Highest Limit" };
			tableModel.setColumnIdentifiers(ColumnIdentifiers);
			varsetup = new JFrame(fromXMLIntoJTable(pathToXML));
			ProblemField.setText(varsetup.getTitle());
			ValidateProblem.doClick();
			for (Variable variable : variableTable.getVariables()) {
				tableModel.addRow(fromVariableTableIntoJTable(variable));
			}

		}
		/*
		 * Now that we know which kind of situation we're in we prepare to show our
		 * Configuration Table
		 */
		configurationTable = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1)
					return false;
				return true;
			}
		};
		configurationTable.setBackground(Color.pink);
		configurationTable.setFont(new Font("Cambria", Font.TRUETYPE_FONT, 15));
		configurationTable.getTableHeader().setResizingAllowed(false);
		configurationTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scroller = new JScrollPane(configurationTable);
		scroller.setPreferredSize(new Dimension(1000, 400));
		insideFrame.add(scroller);
		insideFrame.add(forEditSaveButtons);
		varsetup.add(insideFrame);
		varsetup.setResizable(false);
		varsetup.setDefaultCloseOperation(varsetup.DISPOSE_ON_CLOSE);
		varsetup.pack();
		varsetup.setLocationRelativeTo(frame);
		varsetup.setLocation(varsetup.getX(), varsetup.getY() + frame.getHeight());
		varsetup.setVisible(true);
		CONFIGURATION_WINDOW_ALREADY_ONGOING = true;
		varsetup.addWindowListener(new java.awt.event.WindowAdapter(){
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				CONFIGURATION_WINDOW_ALREADY_ONGOING = false;
			}
		});
	}

	
	/**
	 * Allows user to connect to his e-mail account by typing his password and sends the e-mail through smtp with a secure connection.
	 * The user's credentials are safe and won't be saved by the application.
	 */
	private void throwHelp() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter a password:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancel" };
		int option = JOptionPane.showOptionDialog(null, panel, "Insert your e-mail account password",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (option == 1)
			return;
		while (option == 0 && trimCharArray(pass.getPassword()).length == 0) {
			pass.setText(null);
			option = JOptionPane.showOptionDialog(null, panel, "Insert your e-mail account password",
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		}
		char[] password = pass.getPassword();
		if (password.length > 0) {
			JFrame frame = new JFrame("Help - Send Email to Administrator");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setSize(new Dimension(450, 400));
			frame.setLocationRelativeTo(help);
			frame.setResizable(false);
			JPanel infoPanel = new JPanel();
			JTextField subjectField = new JTextField();
			subjectField.setPreferredSize(new Dimension(250, 25));
			JTextArea textArea = new JTextArea();
			JScrollPane textPane = new JScrollPane(textArea);
			JLabel subjectLabel = new JLabel("Subject: ");
			JButton sendButton = new JButton("Send e-mail");
			sendButton.setFocusable(false);
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isValidEmail(EmailField.getText()))
						JOptionPane.showMessageDialog(null, "You need to validate your email first!", "WARNING",
								JOptionPane.WARNING_MESSAGE);
					if (subjectField.getText().trim().length() == 0)
						JOptionPane.showMessageDialog(null, "You need to fill in the subject of the e-mail!",
								"WARNING", JOptionPane.WARNING_MESSAGE);
					if (textArea.getText().trim().length() == 0)
						JOptionPane.showMessageDialog(null, "You need to fill in the content of e-mail!", "WARNING",
								JOptionPane.WARNING_MESSAGE);

					// Sending e-mail to Admin's email contained in config.xml
					System.out.println("EMAIL TO: "+XML.getEmailAdministrator());
					SendMail sm = new SendMail(EmailField.getText(), password, XML.getEmailAdministrator());
					sm.setSubject(subjectField.getText());
					sm.setContent(textArea.getText());
					sm.send();

					// E-mail credentials verification -- Work In Progress
					//
					int port = 587;
					String host = "smtp.gmail.com";
					if(EmailField.getText().contains("hotmail")){
						host = "smtp.live.com";
					}
					Properties props = new Properties();
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host", "smtp.live.com");
					props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.smtp.auth", "true");
					// or use getDefaultInstance instance if desired...
					Session session = Session.getInstance(props, null);
					try {
						Transport transport = session.getTransport("smtp");
						transport.connect(host, port, EmailField.getText(),
								charArrayToString(password));
						transport.close();
						System.out.println("success");
						JOptionPane.showMessageDialog(null, "Message Successfully Sent!","", JOptionPane.INFORMATION_MESSAGE);
					}catch(AuthenticationFailedException afe) {
						JOptionPane.showMessageDialog(null, "Incorrect Password!","", JOptionPane.ERROR_MESSAGE);
					}catch(MessagingException me) {
						me.printStackTrace();
					}
				}
			});
			infoPanel.add(subjectLabel);
			infoPanel.add(subjectField);
			infoPanel.add(sendButton, BorderLayout.EAST);
			frame.add(infoPanel, BorderLayout.NORTH);
			frame.add(textPane, BorderLayout.CENTER);
			frame.setVisible(true);
			HELP_WINDOW_ALREADY_ONGOING = true;
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					HELP_WINDOW_ALREADY_ONGOING = false;
				}
			});
		}
	}

	/**
	 * Shows the Frequently Asked Questions users might have when using the application.
	 */
	private void throwFAQ() {
		JFrame frame = new JFrame("F.A.Q.");
		
		
		JPanel panel = new JPanel();
		JEditorPane faqtext = new JEditorPane("text/html", "<h1>Do I need to save the problem as a XML file before starting the optimization process?</h1>"
				+ "<h2 color=RED>Once you start the Optimization Process it creates automatically a XML File of the problem, which will be mailed to both the Administrator and the User.</h2>"
				+ "<h1>Will I receive the setup with the values updated by the algorithm once the optimization is done?</h1>"
				+ "<h2 color=RED>Every e-mail comes with a copy of the variables setup (XML) attached. Since the algorithm will impact the XML itself at the end, you will receive the updated setup in the last e-mail you receive.</h2>"
				+ "<h1>If I load a setup from a XML and then I change it, will it affect my XML?</h1>"
				+ "<h2 color=RED>Absolutely not. Even if you save it, it will always be saved as a different filename. It's impossible to change a XML file you load.</h2>");
		JScrollPane scrollPane = new JScrollPane(faqtext);
		scrollPane.setPreferredSize(new Dimension(1100,200));
		panel.add(scrollPane);
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(faq);
		frame.setVisible(true);
		FAQ_WINDOW_ALREADY_ONGOING = true;
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				FAQ_WINDOW_ALREADY_ONGOING = false;
			}
		});
	}

	
	
	private void throwOptimizationProcess() {
		JFrame frame = new JFrame("Choose an algorithm");
		JPanel chooseAlgorithm = new JPanel();
		//Will open the path to jMetal and get all the different algorithms and make a JComboBox out of them!
		JButton startButton = new JButton("Start");
		chooseAlgorithm.add(startButton);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendOptimizationStageEmail(0);
				//when progress==25% sendOptimizationStageEmail(25);
				//when progress==50% sendOptimizationStageEmail(50);
				//when progress==75% sendOptimizationStageEmail(75);
				//when progress==100% sendOptimizationStageEmail(100);
				frame.dispose();
			}
		});

		frame.add(chooseAlgorithm);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(help);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Sends an e-mail to both user and administrator with the XML file of the problem setup.
	 * The e-mail is sent automatically through the administrator's e-mail account.
	 * @param percentagedone
	 */
	private void sendOptimizationStageEmail(int percentagedone) {
		Date day = new Date();
		String dateToString = " "+(day.getYear()+1900)+"-"+(day.getMonth()+1)+"-"+day.getDate()+" "+day.getHours()+":"+day.getMinutes();
		String title="";
		String text="";
		switch(percentagedone) {
		case 0:
			XML.saveProblem(ProblemField.getText(), variableTable);
			title = "Otimização em curso:"+ProblemField.getText()+dateToString;
			text = "Muito obrigado por usar esta plataforma de otimização. Será informado por email " + 
					"sobre o progresso do processo de otimização, quando o processo de otimização tiver atingido 25%, 50%, " + 
					"75% do total do tempo estimado, e também quando o processo tiver terminado, com sucesso ou devido " + 
					"à ocorrência de erros.";
			break;
		case 25:
			title = "Atualização da otimização em curso: "+ProblemField.getText()+" 25%";
			break;
		case 50:
			title = "Atualização da otimização em curso: "+ProblemField.getText()+" 50%";
			break;
		case 75:
			title = "Atualização da otimização em curso: "+ProblemField.getText()+" 75%";
			break;
		case 100:
			title = "Atualização concluida: "+ProblemField.getText();
			text = "Sucesso.";
			break;
		}
		String username = "jpcma@iscte-iul.pt";
		String password = getSecurePassword();

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.port", 587);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.gmail.com");
		
		Session session = Session.getDefaultInstance(props, 
			    new Authenticator(){
					@Override
			        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			            return new javax.mail.PasswordAuthentication("jpcma@iscte-iul.pt", password);
			        }
			});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(EmailField.getText()+","+username));
			message.setSubject(title);
			message.setText(text);
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(text);
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			String file = "../Problems/"+XML.getNameOfLastProblemSaved()+".xml";
			String fileName = ProblemField.getText()+".xml";
			DataSource source = new FileDataSource(file);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(fileName);
			multipart.addBodyPart(attachmentBodyPart);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			System.out.println("Sending Message...");
			Transport.send(message);
			System.out.println("Message successfully sent!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Basically turns the password encrypted in binary back into ASCII.
	 * It's an ASCII converter.
	 * @return password
	 */
	private String getSecurePassword(){
		String pass="";
		String[] secure = {"01000001", "01101110", "01101001", "01001011", "01110010", "01101001", "01110011", "01110100", "01100101", "01101110", "00110001", "00111001", "00111001", "00110101"};
		for(int i=0;i<secure.length;i++) {
			int charcode = Integer.parseInt(secure[i],2);
			String add = new Character((char)charcode).toString();
			pass += add;
		}
		return pass;
	}
	
	/**
	 * Transforms the attributes of a Variable in a Object[] so it can be loaded into the JTable
	 * @param v
	 * @return
	 */
	private Object[] fromVariableTableIntoJTable(Variable v) {
		switch (v.getType()) {
		case REAL:
			Object[] newReal = { v.getVarName(), "Real", v.getRealValue(), v.getLowestRealLimit(),
					v.getHighestRealLimit() };
			return newReal;
		case INTEGER:
			Object[] newInteger = { v.getVarName(), "Integer", v.getIntegerValue(), v.getLowestLimit(),
					v.getHighestLimit() };
			return newInteger;
		case BINARY:
			Object[] newBinary = { v.getVarName(), "Binary", v.getBinaryValue(), v.getLowestBinaryLimit(),
					v.getHighestBinaryLimit() };
			return newBinary;
		}
		return null;
	}

	/**
	 * Create the JTable based on a XML file from the path provided.
	 * @param pathname - The path that leads to the XML File
	 */
	private String fromXMLIntoJTable(String pathname) {
		variableTable = XML.openProblem(pathname);
		return variableTable.getVariableGroupName();
	}

	/**
	 * @param email
	 * @return Validates a generic e-mail address
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
	 *            The problem name we need to validate.
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
	 * @return Validates if the variable name is composed by a-zA-Z0-9 characters
	 *         only
	 */
	private boolean isValidVariableName(String variable) {
		String problemRegex = "^[\\w]+$";
		Pattern pat = Pattern.compile(problemRegex);
		if (variable == null)
			return false;
		return pat.matcher(variable).matches();
	}

	/**
	 * @param input
	 * @return Validates the input of MinimumRange and MaximumRange a
	 *         Weight(Variable Value) can have (-999 to 999 or -99.9 to 99.9 in case
	 *         of Real)
	 */
	private boolean isValidValue(String input, DataType datatype) {
		String problemRegex = "";
		switch (datatype) {
		case BINARY:
			problemRegex = "^[0-1]?$";
			break;
		case INTEGER:
			problemRegex = "^-?[0-9]+$";
			break;
		case REAL:
			problemRegex = "^-?[0-9]+\\.?[0-9]*$";
		}
		Pattern pat = Pattern.compile(problemRegex);
		if (input == null)
			return false;
		return pat.matcher(input).matches();
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.start();
		gui.getSecurePassword();
		System.out.println(gui.getSecurePassword());
		
	}
}
