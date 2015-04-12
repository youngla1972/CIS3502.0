package CIS350;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * Creates the main GUI of the program.
 * @author Patrick Dishaw, Laura Young, Viet Duong, Nicholas Bushen
 *
 */
public class ProjectGUI extends JFrame implements ActionListener {

	/**
	 * serialVersionUID. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main frame of the GUI.
	 */
	private JFrame frame;
	private JScrollPane scroll;
	private JPanel special;

	/**
	 * Button for the one week sort.
	 * (To be implemented later)
	 */
	private JButton week1Button;

	/**
	 * Button for the two week sort.
	 * (To be implemented later)
	 */
	private JButton week2Button; 

	/**
	 * Button for the four week sort.
	 * (To be implemented later)
	 */
	private JButton week4Button;

	/**
	 * Button for displaying all projects.
	 * (To be implemented later)
	 */
	private JButton allButton;

	/**
	 * Button for sorting projects by date.
	 */
	private JButton dateButton;

	/**
	 * Button to add a new project.
	 */
	private JButton newItem;

	/**
	 * Button to delete a project.
	 */
	private JButton deleteItem;

	/**
	 * Button to edit a project.
	 */
	private JButton editItem;

	/**
	 * Button to display sub-projects of a project.
	 */
	private JButton subItem;


	/**
	 * Button to declare a project complete or not complete.
	 */
	private JButton doneItem;

	/**
	 * Panel for buttons at the top of the GUI.
	 */
	private JPanel buttonPanel;

	/**
	 * Panel for buttons at the bottom of the GUI.
	 */
	private JPanel southPanel;
	private JPanel centralPanel;

	/**
	 * Menu for containing exit, save, and load.
	 */
	private JMenu fileMenu; 

	/**
	 * Menu for containing about.
	 */
	private JMenu helpMenu;

	/**
	 * Exit MenuItem. Closes program.
	 */
	private JMenuItem exitItem;

	/**
	 * Displays information about the program.
	 */
	private JMenuItem aboutItem;

	/**
	 * Allows user to save current data as a file.
	 */
	private JMenuItem saveItem;

	/**
	 * Creates the ProjectModel for the program.
	 */
	private ProjectModel model;

	/**
	 * Creates a newProject for the program.
	 */
	private CreateGUI newProject;
	private JButton[] projects = new JButton[100];
	private JButton[] completed = new JButton[100];
	private ArrayList<Project> temp;
	private int number = 0;
	private JLabel one, two, three, four, five;

	/**
	 * Instantiates the GUI.
	 */
	public ProjectGUI() {
		setupFrame();
		model = new ProjectModel();
		model.load(new File("src/package1/file.ser"));
	}

	/**
	 * Sets up the panel for the parent GUI.
	 */
	public final void setupFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Project Management");

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);
		
		fileMenu.add(exitItem);
		fileMenu.addActionListener(this);
		//helpMenu.add(aboutItem);
		menuBar.add(fileMenu);
				
		JPanel panel;
		
		special = new JPanel(new GridLayout(30,1));
		
		String name = "Serif"; //"Serif", "SansSerif", "Monospaced", or a font name
		
		int style = Font.BOLD; //Font.ITALIC, Font.BOLD, or Font.BOLD | Font.ITALIC
		
		int size = 18; //any number size
	
		newItem = new JButton("+");
		newItem.setFont(new Font(name, style, size));
		newItem.setHorizontalAlignment(SwingConstants.CENTER);
		newItem.addActionListener(this);
		special.add(newItem);
		
		scroll = new JScrollPane(special);
		scroll.setViewportView(special);
		special.setPreferredSize(new Dimension(950, 800));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		frame.add(scroll, BorderLayout.CENTER);
		
		panel = setupNorthPanel();
		frame.add(panel, BorderLayout.NORTH);

		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(1000, 500);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Setting up top panel.
	 * @return buttonPanel
	 */
	public final JPanel setupNorthPanel() {
		buttonPanel = new JPanel(new FlowLayout());
		//add(buttonPanel, BorderLayout.NORTH);

		week1Button = new JButton("1 Week");
		week1Button.addActionListener(this);

		week2Button = new JButton("2 Week");
		week2Button.addActionListener(this);

		week4Button = new JButton("4 Week");
		week4Button.addActionListener(this);

		allButton = new JButton("All Projects");
		allButton.addActionListener(this);

		dateButton = new JButton("Sort by Date");
		dateButton.addActionListener(this);

		buttonPanel.add(allButton);
		buttonPanel.add(week1Button);
		buttonPanel.add(week2Button);
		buttonPanel.add(week4Button);
		buttonPanel.add(dateButton);

		return buttonPanel;
	}

	/**
	 * Assigns actions to buttons and JMenuItems.
	 * @param e determines what is clicked and what to do
	 */
	public final void actionPerformed(final ActionEvent e) {
		if (e.getSource() == exitItem) {
			model.save(new File("src/package1/file.ser"));
			System.exit(0);
		}
		//		if (e.getSource() == aboutItem) {
		//			JOptionPane.showMessageDialog(null, "Hello!");
		//		}
		if (e.getSource() == newItem) {
			newProject = new CreateGUI(this);

			if (newProject.isOkPressed()) {
				Project p = new Project(newProject.getName(), null, newProject.getDueDate(), 
						newProject.getReminder(), newProject.getNotes(),  false);
				//				Project p = newProject.whatProject();
				model.add(p);
				addingProject(newProject.getName(), null, newProject.getDueDate(), 
						newProject.getReminder(), newProject.getNotes());
				model.sortByName();
			}
		}
		if (e.getSource() == deleteItem) {
//			int index = table.getSelectedRow();
//			if (index != -1) {
//				if (JOptionPane.showConfirmDialog(null, 
//						"Are you sure you would like to delete this project?"
//						, null, JOptionPane.OK_CANCEL_OPTION) != 0) {
//					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//				} else {
//					model.remove(model.get(index));
//				}
//			}
		}
		
			//if (e.getSource()) {
				//if()

//				newProject = new CreateGUI(this, 
//						model.get(i).getName(), 
//						model.get(i).getSubName(),
//						model.get(i).getDueDate(), 
//						model.get(i).getReminder(),
//						model.get(i).getNotes());
				//model.remove(model.get(index));

//				if (newProject.isOkPressed()) {
//					if(newProject.getName() != model.get(i).getName()){
//						one.setText(newProject.getName());
//						model.upDate(model.get(i).getName(), 
//								newProject.getName());
//					}
//					if(newProject.getSub() != model.get(i).getSubName()){
//						two.setText(newProject.getSub());
//						model.get(i).setSubName(newProject.getName());
//					}
//					if(newProject.getDueDate() != model.get(i).getDueDate()){
//						three.setText(Utilities.gToString(newProject.getDueDate()));
//						model.get(i).setDueDate(newProject.getDueDate());
//					}
//					if(newProject.getReminder() != model.get(i).getReminder()){
//						four.setText(String.valueOf(newProject.getReminder()));
//						model.get(i).setReminder(newProject.getReminder());
//					}
//					if(newProject.getNotes() != model.get(i).getNotes()){
//						five.setText(newProject.getNotes());
//						model.get(i).setNotes(newProject.getNotes());
//					}
					
//					Project s = new Project(newProject.getName(), 
//							newProject.getSub(), newProject.getDueDate(), 
//							newProject.getReminder(), newProject.getNotes(), 
//							false);

					//model.remove(model.get(i));
					//remove(i);
					//model.add(s);
//					addingProject(newProject.getName(), null, newProject.getDueDate(), 
//							newProject.getReminder(), newProject.getNotes());
					//model.sortByName();
//				}
//
//			}

		

		if (e.getSource() == doneItem) {
			//			if (model.getSize() == 0) {
			//				JOptionPane.showMessageDialog(null, "No project selected.");
//			} else {
//				int index = table.getSelectedRow();
//				if (index != -1) {
//					if (model.get(index).getDone() == false) {
//						if (JOptionPane.showConfirmDialog(null, 
//								"Have you completed the project?"
//								, null, JOptionPane.OK_CANCEL_OPTION) != 0) {
//							setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//
//
//						} else {
//							model.get(index).setDone(true);
//						}
//					} else {
//						model.get(index).setDone(false);
//					}
//				} else {
//					JOptionPane.showMessageDialog(null, "No project selected.");
//				}
//				//model.refresh();
//			}
		}
		
		if (e.getSource() == subItem) {
//			int index = table.getSelectedRow();
//			if (index != -1) {
//				String name = model.get(index).getName();
//				newProject = new CreateGUI(this, name);
//				//subGroup = new SubCreateGUI(this);
//
//				if (newProject.isOkPressed()) {
//					Project p = new Project(newProject.getName(), newProject.getSub(), newProject.getDueDate(), 
//							newProject.getReminder(), newProject.getNotes(), false);
//					//Project p = newProject.whatProject();
//					model.add(p);
//					model.sortByName();
//
//				}
//			} else {
//				JOptionPane.showMessageDialog(this, "Please pick a project.");
//			}
		}
		if (e.getSource() == week1Button) {
			model.sortByWeek(1);
		}
		if (e.getSource() == week2Button) {
			model.sortByWeek(2);
		}
		if (e.getSource() == week4Button) {
			model.sortByWeek(4);
		}
		if (e.getSource() == allButton) {
			model.sortByName();
		}
		if (e.getSource() == dateButton) {
			model.sortByDate();
		}

	}
	public void addingProject(String n, String s, GregorianCalendar d, 
			int r, String notes){
		
		centralPanel = new JPanel(new GridLayout(1,10));
		projects[number] = new JButton();
		//projects[number].setActionCommand(""+number);
		projects[number].addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent event){
						System.out.println("x");
						edit(number);
					}
				});
		projects[number].setLayout(new GridLayout(1,5));
		//now.setSize(5,950);
		//now.setText("");
		one = new JLabel(n);
		one.setHorizontalAlignment(SwingConstants.CENTER);
		two = new JLabel(s);
		two.setHorizontalAlignment(SwingConstants.CENTER);
		three = new JLabel(Utilities.gToString(d));
		three.setSize(three.getPreferredSize());
		three.setHorizontalAlignment(SwingConstants.CENTER);
		four = new JLabel(String.valueOf(r));
		four.setSize(four.getPreferredSize());
		four.setHorizontalAlignment(SwingConstants.CENTER);
		five = new JLabel(notes);
		five.setHorizontalAlignment(SwingConstants.CENTER);
		
		projects[number].add(one);
		projects[number].add(two);
		projects[number].add(three);
		projects[number].add(four);
		projects[number].add(five);
		
		int temp = number;
		number++;
		//System.out.println(temp);
		special.add(projects[temp]);
	}
	
	public void remove(int i){
		projects[i] = null;
		special.remove(projects[i]);
		//special.invalidate();
	}

	public void edit(int i){
		newProject = new CreateGUI(this, 
				model.get(i).getName(), 
				model.get(i).getSubName(),
				model.get(i).getDueDate(), 
				model.get(i).getReminder(),
				model.get(i).getNotes());
		//model.remove(model.get(index));

		if (newProject.isOkPressed()) {
			if(newProject.getName() != model.get(i).getName()){
				one.setText(newProject.getName());
				model.upDate(model.get(i).getName(), 
						newProject.getName());
			}
			if(newProject.getSub() != model.get(i).getSubName()){
				two.setText(newProject.getSub());
				model.get(i).setSubName(newProject.getName());
			}
			if(newProject.getDueDate() != model.get(i).getDueDate()){
				three.setText(Utilities.gToString(newProject.getDueDate()));
				model.get(i).setDueDate(newProject.getDueDate());
			}
			if(newProject.getReminder() != model.get(i).getReminder()){
				four.setText(String.valueOf(newProject.getReminder()));
				model.get(i).setReminder(newProject.getReminder());
			}
			if(newProject.getNotes() != model.get(i).getNotes()){
				five.setText(newProject.getNotes());
				model.get(i).setNotes(newProject.getNotes());
			}

			//Project s = new Project(newProject.getName(), 
			//		newProject.getSub(), newProject.getDueDate(), 
			//		newProject.getReminder(), newProject.getNotes(), 
			//		false);

			//model.remove(model.get(i));
			//remove(i);
			//model.add(s);
			//addingProject(newProject.getName(), null, newProject.getDueDate(), 
			//		newProject.getReminder(), newProject.getNotes());
			//model.sortByName();
			//}
			//
			//}
		}
	}
	
}
