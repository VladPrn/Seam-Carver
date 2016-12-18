package edu.seamcarving.java;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/** \mainpage Seam Carver
* \section intro_sec Introduction
* The program Seam Carver it is course project of students of Ulyanovsk State Technical University: Pecherkin Vladislav and Elena Kyznetsova.
* This program compresses images without loss content by algorithm Seam Carving.
*/

/**
 * The GUI of Seam Carving application.
 * @author Vladislav Pecherkin
 * @author Elena Kyznetsova
 */
public class Application {
	private JFrame frame;
	private ResizableImage rpanel;
	private JRadioButtonMenuItem radioButtonPecherkin;
	private JRadioButtonMenuItem radioButtonKyznetsova;
	private JRadioButtonMenuItem radioButtonGradient;
	private JRadioButtonMenuItem radioButtonNeighbors;
	private JRadioButtonMenuItem radioButtonChangedGradient;
	private JRadioButtonMenuItem radioButtonSobels;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
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
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new ConstantlyDecreasingFrame();
		frame.setTitle("Seam Carver");
		frame.setBounds(20, 20, 392, 267);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(140, 60));
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Файл");
		menuBar.add(menuFile);
		
		JMenuItem mntmOpen = new JMenuItem("Открыть");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Открыть файл");                
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File file = fileopen.getSelectedFile();
					try {
						if (rpanel != null) {
							frame.getContentPane().remove(rpanel);
						}
						
						SeamCarvingVersion version;
						MetricsVersion mVersion;
						
						if (radioButtonPecherkin.isSelected()) {
							version = SeamCarvingVersion.pecherkin;
						} else {
							version = SeamCarvingVersion.kyznetsova;
						}
						
						if (radioButtonGradient.isSelected()) {
							mVersion = MetricsVersion.gradient;
						} else if(radioButtonChangedGradient.isSelected()) {
							mVersion = MetricsVersion.changedGradient;
						} else if(radioButtonSobels.isSelected()) {
							mVersion = MetricsVersion.sobels;
						} else{
							mVersion = MetricsVersion.neighbors;
						}
						rpanel = new ResizableImage(file.getPath(), version, mVersion);
						frame.getContentPane().add(rpanel, BorderLayout.CENTER);
						rpanel.setPreferredSize(new Dimension(rpanel.getBeginWidth(), rpanel.getBeginHeight()));
						frame.pack();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Ошибка чтения");
					}
				}
			}
		});
		menuFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Сохранить");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rpanel == null) {
					JOptionPane.showMessageDialog(null, "Нет открытого файла");
				}
				JFileChooser fileOpen = new JFileChooser();
				int ret = fileOpen.showDialog(null, "Сохранить файл");                
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File file = fileOpen.getSelectedFile();
					try {
						rpanel.save(file.getPath());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Ошибка записи");
					}
				}
			}
		});
		menuFile.add(mntmSave);
		
		JMenu menuSettings = new JMenu("Настройки");
		menuBar.add(menuSettings);
		
		ButtonGroup group = new ButtonGroup();
		
		JMenu menuRealization = new JMenu("Реализация");
		menuSettings.add(menuRealization);
		radioButtonPecherkin = new JRadioButtonMenuItem("Печеркин");
		menuRealization.add(radioButtonPecherkin);
		group.add(radioButtonPecherkin);
		radioButtonPecherkin.setSelected(true);
		
		radioButtonKyznetsova = new JRadioButtonMenuItem("Кузнецова");
		menuRealization.add(radioButtonKyznetsova);
		group.add(radioButtonKyznetsova);
		
		JMenu menuMetrics = new JMenu("Метрика");
		menuSettings.add(menuMetrics);
		radioButtonGradient = new JRadioButtonMenuItem("Градиент");
		ButtonGroup metricGroup = new ButtonGroup();
		metricGroup.add(radioButtonGradient);
		menuMetrics.add(radioButtonGradient);
		radioButtonGradient.setSelected(true);
		
		radioButtonNeighbors = new JRadioButtonMenuItem("Метод ближайших соседей");
		metricGroup.add(radioButtonNeighbors);
		menuMetrics.add(radioButtonNeighbors);
		
		radioButtonChangedGradient = new JRadioButtonMenuItem("Измененный градиент");
		metricGroup.add(radioButtonChangedGradient);
		menuMetrics.add(radioButtonChangedGradient);
		
		radioButtonSobels = new JRadioButtonMenuItem("Метод Собеля");
		metricGroup.add(radioButtonSobels);
		menuMetrics.add(radioButtonSobels);
		
		rpanel = null;
	}
}