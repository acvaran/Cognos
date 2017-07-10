package wall_avoider.Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextField;

import cognos.GeneticAlgorithm.Individual;
import wall_avoider.Game.Game;
import wall_avoider.Game.Population;
import wall_avoider.Game.Character;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class WallAvoiderGui {

	private JFrame frame;
	private JTextField numberOfIndividuals, ConnMChance, AddMChance, txtNumberOfGenerations, txtGenNumber;
	private JPanel Menu, InfoPanel;
	private Population p;
	private JButton btnIncreaseGeneration, btnStartTest, btnSeeGeneration;
	private JLabel lblGenerationNumber, lblMaxFitness, lblLowFitness, lblAvgFitness;
	private JPanel GamePanel, GamePlatform;
	private JList<String> listOfIndividuals;
	private JLabel lblGame, lblInfo_1, IndivName, IndivFitness;
	private JButton btnChooseIndividual, btnBack;
	private Thread mapRead;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WallAvoiderGui window = new WallAvoiderGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WallAvoiderGui() {
		initialize();
		initializePanel();
		initializeGenerationPanel();
		initializeGamePanel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		Menu = new JPanel();
		Menu.setBackground(Color.ORANGE);
		frame.getContentPane().add(Menu, "Menu");
		Menu.setLayout(new BoxLayout(Menu, BoxLayout.LINE_AXIS));
		GamePanel = new JPanel();
		GamePanel.setBackground(Color.ORANGE);
		frame.getContentPane().add(GamePanel, "GamePanel");
		GamePanel.setLayout(null);

	}

	public void initializePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		Menu.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel StartPanel = new JPanel();
		StartPanel.setBackground(Color.ORANGE);
		panel.add(StartPanel);
		StartPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		StartPanel.setLayout(null);

		JLabel lblPopulationCreation = new JLabel("Population Creation:");
		lblPopulationCreation.setFont(new Font("Arial Black", Font.PLAIN, 18));
		lblPopulationCreation.setBounds(10, 11, 230, 29);
		StartPanel.add(lblPopulationCreation);

		JLabel lblNumberOfIndividuals = new JLabel("Number of Individuals:");
		lblNumberOfIndividuals.setFont(new Font("Arial", Font.BOLD, 12));
		lblNumberOfIndividuals.setBounds(10, 51, 180, 21);
		StartPanel.add(lblNumberOfIndividuals);

		JLabel lblConnectionMutationChance = new JLabel("Connection Mutation Chance:");
		lblConnectionMutationChance.setFont(new Font("Arial", Font.BOLD, 12));
		lblConnectionMutationChance.setBounds(10, 83, 180, 14);
		StartPanel.add(lblConnectionMutationChance);

		JLabel lblAddMutationChance = new JLabel("Add Mutation Chance:");
		lblAddMutationChance.setFont(new Font("Arial", Font.BOLD, 12));
		lblAddMutationChance.setBounds(10, 108, 180, 21);
		StartPanel.add(lblAddMutationChance);

		numberOfIndividuals = new JTextField();
		numberOfIndividuals.setBounds(200, 52, 86, 20);
		StartPanel.add(numberOfIndividuals);
		numberOfIndividuals.setColumns(10);

		ConnMChance = new JTextField();
		ConnMChance.setBounds(200, 81, 86, 20);
		StartPanel.add(ConnMChance);
		ConnMChance.setColumns(10);

		AddMChance = new JTextField();
		AddMChance.setBounds(200, 109, 86, 20);
		StartPanel.add(AddMChance);
		AddMChance.setColumns(10);

		JLabel lblInfo = new JLabel(
				"<html>\r\n<body>\r\n<h1 style=\"font-size:100%;\">Info:</h1>\r\n<p style=\"font-size:90%;\">-Enter only integers to the Number of Individuals section.\r\n<br>-Enter only fractions to other two sections.</p>\r\n</body>\r\n</html>\r\n");
		lblInfo.setBounds(10, 140, 311, 62);
		StartPanel.add(lblInfo);

		JButton btnGeneratePopulation = new JButton("Generate");
		btnGeneratePopulation.setBackground(new Color(248, 248, 255));
		btnGeneratePopulation.setBounds(197, 140, 89, 23);
		StartPanel.add(btnGeneratePopulation);
		btnGeneratePopulation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				p = new Population((int) Integer.parseInt(numberOfIndividuals.getText()), 5, 2, 50,
						(double) Double.parseDouble(ConnMChance.getText()),
						(double) Double.parseDouble(AddMChance.getText()), new Game());
				btnIncreaseGeneration.setEnabled(true);
				btnStartTest.setEnabled(true);
			}
		});

		InfoPanel = new JPanel();
		InfoPanel.setBackground(Color.ORANGE);
		panel.add(InfoPanel);
		InfoPanel.setLayout(null);

		JLabel lblGeneration = new JLabel("Generation:");
		lblGeneration.setFont(new Font("Arial", Font.BOLD, 12));
		lblGeneration.setBounds(10, 11, 100, 14);
		InfoPanel.add(lblGeneration);

		lblGenerationNumber = new JLabel("---");
		lblGenerationNumber.setFont(new Font("Arial", Font.BOLD, 12));
		lblGenerationNumber.setBounds(200, 12, 100, 14);
		InfoPanel.add(lblGenerationNumber);

		JLabel lblMaximumFitness = new JLabel("Maximum Fitness:");
		lblMaximumFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblMaximumFitness.setBounds(10, 36, 130, 14);
		InfoPanel.add(lblMaximumFitness);

		lblMaxFitness = new JLabel("---");
		lblMaxFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblMaxFitness.setBounds(200, 37, 100, 14);
		InfoPanel.add(lblMaxFitness);

		JLabel lblLowestFitness = new JLabel("Lowest Fitness:");
		lblLowestFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblLowestFitness.setBounds(10, 61, 130, 14);
		InfoPanel.add(lblLowestFitness);

		lblLowFitness = new JLabel("---");
		lblLowFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblLowFitness.setBounds(200, 62, 100, 14);
		InfoPanel.add(lblLowFitness);

		JLabel lblAverageFitness = new JLabel("Average Fitness:");
		lblAverageFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblAverageFitness.setBounds(10, 86, 130, 14);
		InfoPanel.add(lblAverageFitness);

		lblAvgFitness = new JLabel("---");
		lblAvgFitness.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvgFitness.setBounds(200, 87, 46, 14);
		InfoPanel.add(lblAvgFitness);
	}

	public void initializeGenerationPanel() {
		JPanel GenerationPanel = new JPanel();
		GenerationPanel.setBackground(Color.ORANGE);
		Menu.add(GenerationPanel);
		GenerationPanel.setLayout(null);

		btnIncreaseGeneration = new JButton("Increase 1 Generation");
		btnIncreaseGeneration.setBackground(new Color(248, 248, 255));
		btnIncreaseGeneration.setFont(new Font("Arial", Font.BOLD, 14));
		btnIncreaseGeneration.setBounds(10, 11, 322, 48);
		btnIncreaseGeneration.setEnabled(false);
		GenerationPanel.add(btnIncreaseGeneration);
		btnIncreaseGeneration.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						btnIncreaseGeneration.setEnabled(false);
						btnStartTest.setEnabled(false);
						btnSeeGeneration.setEnabled(false);
						p.run();
						lblGenerationNumber.setText("" + p.getGeneration());
						lblLowFitness.setText("" + p.getLowFitness());
						lblMaxFitness.setText("" + p.getMaxFitness());
						lblAvgFitness.setText("" + p.getAvgFitness());
						p.selection();
						p.mutate();
						btnIncreaseGeneration.setEnabled(true);
						btnStartTest.setEnabled(true);
						btnSeeGeneration.setEnabled(true);
					}
				});
				thread.start();
			}
		});

		txtNumberOfGenerations = new JTextField();
		txtNumberOfGenerations.setBounds(10, 80, 150, 20);
		GenerationPanel.add(txtNumberOfGenerations);
		txtNumberOfGenerations.setColumns(10);

		btnStartTest = new JButton("Start Test");
		btnStartTest.setBackground(new Color(248, 248, 255));
		btnStartTest.setFont(new Font("Arial", Font.BOLD, 14));
		btnStartTest.setBounds(170, 79, 162, 23);
		btnStartTest.setEnabled(false);
		btnStartTest.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {

					public void run() {
						for (int i = 0; i < (int) Integer.parseInt(txtNumberOfGenerations.getText()); i++) {
							btnIncreaseGeneration.setEnabled(false);
							btnStartTest.setEnabled(false);
							btnSeeGeneration.setEnabled(false);
							p.run();
							lblGenerationNumber.setText("" + p.getGeneration());
							lblLowFitness.setText("" + p.getLowFitness());
							lblMaxFitness.setText("" + p.getMaxFitness());
							lblAvgFitness.setText("" + p.getAvgFitness());
							p.selection();
							p.mutate();
							btnIncreaseGeneration.setEnabled(true);
							btnStartTest.setEnabled(true);
							btnSeeGeneration.setEnabled(true);
						}
					}
				});
				thread.start();
			}
		});
		GenerationPanel.add(btnStartTest);

		btnSeeGeneration = new JButton("See Generation");
		btnSeeGeneration.setBackground(new Color(248, 248, 255));
		btnSeeGeneration.setFont(new Font("Arial", Font.BOLD, 14));
		btnSeeGeneration.setBounds(170, 111, 162, 23);
		btnSeeGeneration.setEnabled(false);
		btnSeeGeneration.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) frame.getContentPane().getLayout();
				c1.show(frame.getContentPane(), "GamePanel");
				ArrayList<Individual> population = p.getSelectedPopulation();
				String[] pNames = new String[population.size()];
				for (int i = 0; i < pNames.length; i++)
					pNames[i] = population.get(i).toString();
				listOfIndividuals.setListData(pNames);
			}
		});
		GenerationPanel.add(btnSeeGeneration);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBackground(Color.WHITE);
		progressBar.setBounds(10, 145, 322, 20);
		GenerationPanel.add(progressBar);

		txtGenNumber = new JTextField();
		txtGenNumber.setBounds(10, 111, 150, 20);
		GenerationPanel.add(txtGenNumber);
		txtGenNumber.setColumns(10);
	}

	public void initializeGamePanel() {

		GamePlatform = new JPanel();
		GamePlatform.setBackground(Color.WHITE);
		GamePlatform.setBounds(394, 50, 280, 400);
		GamePanel.add(GamePlatform);
		GamePlatform.setLayout(new GridLayout(10, 7, 0, 0));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				JPanel tempPanel = new JPanel();
				tempPanel.setBackground(Color.RED);
				tempPanel.setPreferredSize(new Dimension(40, 40));
				GridBagConstraints tempGrid = new GridBagConstraints();
				tempGrid.fill = GridBagConstraints.BOTH;
				tempGrid.gridx = i;
				tempGrid.gridy = j;
				GamePlatform.add(tempPanel, tempGrid);
			}
		}

		lblGame = new JLabel("Game:");
		lblGame.setFont(new Font("Arial", Font.BOLD, 16));
		lblGame.setBounds(394, 12, 81, 27);
		GamePanel.add(lblGame);

		lblInfo_1 = new JLabel("Info:");
		lblInfo_1.setBounds(220, 50, 46, 14);
		GamePanel.add(lblInfo_1);

		IndivName = new JLabel("---");
		IndivName.setBounds(220, 75, 46, 14);
		GamePanel.add(IndivName);

		IndivFitness = new JLabel("---");
		IndivFitness.setBounds(220, 100, 46, 14);
		GamePanel.add(IndivFitness);

		btnChooseIndividual = new JButton("Choose Individual");
		btnChooseIndividual.setBackground(Color.WHITE);
		btnChooseIndividual.setFont(new Font("Arial", Font.BOLD, 14));
		btnChooseIndividual.setBounds(220, 125, 164, 23);
		btnChooseIndividual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Individual> population = p.getSelectedPopulation();
				Individual indiv = population.get(listOfIndividuals.getSelectedIndex());
				readMovements(indiv);
			}
		});
		GamePanel.add(btnChooseIndividual);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.BOLD, 12));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(220, 427, 164, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout) frame.getContentPane().getLayout();
				c.show(frame.getContentPane(), "Menu");
			}
		});
		GamePanel.add(btnBack);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 12, 200, 439);
		GamePanel.add(scrollPane);
		listOfIndividuals = new JList<String>();
		scrollPane.setViewportView(listOfIndividuals);
	}

	public void readMovements(Individual indiv) {
		mapRead = new Thread(new Runnable() {
			public void run() {
				Scanner scanMap = null;
				try {
					scanMap = new Scanner(new File("C:\\Wall Avoider Populations\\Generation" + p.getGeneration() + "\\"
							+ ((Character) indiv).getName() + "\\map.txt"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String line = "", map = "";
				while (scanMap.hasNextLine()) {
					line = scanMap.nextLine();
					if (!line.equals(""))
						map += line + "\n";
					else {
						updateGamePlatform(map);
						map = "";
					}
				}
			}
		});
		mapRead.start();

	}

	public void updateGamePlatform(String map) {
		int[][] mapArray = new int[10][7];
		map = map.replace("\n", "");
		int k = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				mapArray[i][j] = (int) Integer.parseInt("" + map.charAt(k));
				k++;
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				GamePlatform.repaint();
				JPanel tempPanel = (JPanel) GamePlatform.getComponentAt((40 * j + 20), (40 * i));
				if (tempPanel == null)
					System.out.println("hfjadhsþf");
				if (mapArray[i][j] == 0)
					tempPanel.setBackground(Color.WHITE);
				else if (mapArray[i][j] == 1)
					tempPanel.setBackground(Color.BLACK);
				else if (mapArray[i][j] == 2)
					tempPanel.setBackground(Color.YELLOW);
				else if (mapArray[i][j] == 3)
					tempPanel.setBackground(Color.RED);
			}
		}
		GamePlatform.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
