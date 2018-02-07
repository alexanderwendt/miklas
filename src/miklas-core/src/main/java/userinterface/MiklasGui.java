package userinterface;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import java.awt.CardLayout;

import javax.swing.JPanel;

import ch.aplu.jgamegrid.GameGrid;


public class MiklasGui {

	private JFrame frmMiklasGame;
	
	private JPanel panelAfterGame;
	private JPanel panelMenu;
	private JPanel panelGame;
	
	//The game itself
	private GameGrid game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiklasGui window = new MiklasGui();
					window.frmMiklasGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MiklasGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMiklasGame = new JFrame();
		frmMiklasGame.setTitle("MIKLAS GAME");
		frmMiklasGame.setBounds(1, 1, 1100, 600);
		frmMiklasGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMiklasGame.getContentPane().setLayout(new CardLayout(0, 0));
		
		initializeMainMenu();
		
		initializeGame();
		
		initializeAfterMath();
	}

	private void initializeAfterMath() {
		panelAfterGame = new JPanel();
		frmMiklasGame.getContentPane().add(panelAfterGame, "name_6966124071147");
		panelAfterGame.setLayout(null);
		this.panelAfterGame.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("After Game Layout");
		lblNewLabel.setBounds(270, 61, 121, 46);
		panelAfterGame.add(lblNewLabel);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(265, 402, 89, 23);
		panelAfterGame.add(btnMainMenu);
	}
	
	private void initializeNewGame() {
		//Visualization gameGrid = new Visualization(null);
		game = new GameGrid();
		game.setCellSize(40);
		game.setNbHorzCells(20);
		game.setNbVertCells(20);
	}

	private void initializeGame() {
		//Game panel
		panelGame = new JPanel();
		panelGame.setBorder(BorderFactory.createTitledBorder("MIKLAS GAME"));
		
		this.initializeNewGame();
		
	    GroupLayout jPanelGameLayout = new GroupLayout(panelGame);
		panelGame.setLayout(jPanelGameLayout);
		
		jPanelGameLayout.setHorizontalGroup(
		jPanelGameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		      .addGroup(jPanelGameLayout.createSequentialGroup()
		        .addGap(28, 28, 28)
		        .addComponent(game, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        .addGap(28, 28, 28))
		    );
		jPanelGameLayout.setVerticalGroup(
			jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			      .addGroup(jPanelGameLayout.createSequentialGroup()
			        .addComponent(game, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			        .addContainerGap(20, Short.MAX_VALUE))
			    );
		
		frmMiklasGame.getContentPane().add(panelGame, "Game");
		//panelGame.setLayout(null);
		this.panelGame.setVisible(false);
		
		JButton btnGoBack = new JButton("Go back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGame.setVisible(false);
				panelMenu.setVisible(true);
			}
		});
		btnGoBack.setBounds(275, 402, 89, 23);
		panelGame.add(btnGoBack);
		
		
		JLabel lblGame = new JLabel("Game");
		lblGame.setBounds(266, 11, 46, 14);
		panelGame.add(lblGame);
	}

	private void initializeMainMenu() {
		//Panel Menu
		panelMenu = new JPanel();
		frmMiklasGame.getContentPane().add(panelMenu, "Menupane");
		panelMenu.setLayout(null);
		this.panelMenu.setVisible(true);
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGame.setVisible(true);
				panelMenu.setVisible(false);
				
				//frame.dispose();
				//Visualization vis = new Visualization(null);
				//vis.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(407, 340, 149, 23);
		panelMenu.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Quit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(407, 374, 149, 23);
		panelMenu.add(btnNewButton_1);
		
		JLabel lblMiklas = new JLabel("Miklas Main Menu");
		lblMiklas.setForeground(new Color(255, 20, 147));
		lblMiklas.setFont(new Font("Vani", Font.BOLD, 50));
		lblMiklas.setBounds(211, 22, 491, 72);
		panelMenu.add(lblMiklas);
		
		JLabel introPicturePlayer1 = new JLabel(new ImageIcon("resources/AriVerp/graphics/ari_0.gif"));
		introPicturePlayer1.setBounds(300, 75, 80, 80);
		panelMenu.add(introPicturePlayer1);
		
		JLabel introPicturePlayer2 = new JLabel(new ImageIcon("resources/AriVerp/graphics/verp_0.gif"));
		introPicturePlayer2.setBounds(400, 75, 80, 80);
		panelMenu.add(introPicturePlayer2);
		
		JLabel introPictureGame = new JLabel(new ImageIcon("resources/AriVerp/graphics/DotaBackground.jpg"));
		introPictureGame.setBounds(10, 10, 1000, 800);
		panelMenu.add(introPictureGame);
	}
	
}
