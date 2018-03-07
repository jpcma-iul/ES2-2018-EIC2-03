import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	private JFrame frame = new JFrame("Conflict-o-Minus");
	private JPanel tudo = new JPanel(new GridLayout(3,1));
	private JPanel Email = new JPanel(new FlowLayout());
	private JPanel Problem = new JPanel(new FlowLayout());
	private JPanel Description = new JPanel(new FlowLayout());
	private JTextField EmailField = new JTextField(60);
	private JTextField ProblemDesignation= new JTextField(70);
	private JTextField DescriptionField = new JTextField(71);
	
	public void start(){
		initializeFields();
		frame.add(tudo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	/*Add the fields to the FillOutForm*/
	public void initializeFields(){
		Email.add(new JLabel("Insert your E-Mail for contact purposes: "));
		Email.add(EmailField);
		tudo.add(Email);
		Problem.add(new JLabel("Name of your Problem: "));
		Problem.add(ProblemDesignation, 1);
		tudo.add(Problem);
		Description.add(new JLabel("Describe your issue: "));
		Description.add(DescriptionField);
		tudo.add(Description);
	}
	
	
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.start();
	}	
}


