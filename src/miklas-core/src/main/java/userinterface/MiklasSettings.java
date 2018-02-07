package userinterface;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.ConfigLoader;
import gameengine.GameEngineImpl;

public class MiklasSettings extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final ConfigLoader conf;
	private String confFilePath;
	private JTextArea fcontent;
	
	private static final Logger log = LoggerFactory.getLogger(MiklasSettings.class);
	
	public MiklasSettings(JFrame parent) {
		super(parent, "Game Properties", true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		
		conf = ConfigLoader.getConfig();
		confFilePath = conf.getModIniPath();
		init();
	}
	
	public void init() {
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		// add label
		add(new JLabel("Config file - " + confFilePath + ":"));
		
		// add text area (/w scroll pane) pre-filled with file contents
		try {
			FileReader fr = new FileReader(confFilePath);
			fcontent = new JTextArea(25,80);
			fcontent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
			fcontent.read(fr, null);
			fr.close();
			add(new JScrollPane(fcontent));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		// add save and cancel buttons
		JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton bSave = new JButton("Save changes");
		JButton bCancel = new JButton("Cancel");
		bSave.setActionCommand("save");
		bCancel.setActionCommand("cancel");
		bSave.addActionListener(this);
		bCancel.addActionListener(this);
		p.add(bSave);
		p.add(bCancel);
		add(p);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "save":
			try {
				// rename original version of config file as backup (.bak file)
				Path from = FileSystems.getDefault().getPath(confFilePath);
				Path to = FileSystems.getDefault().getPath(confFilePath + ".bak");
				Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
				
				// save text area contents into file
				FileWriter fw = new FileWriter(confFilePath);
				fcontent.write(fw);
				fw.close();
			} catch (IOException exc) {
				log.error(exc.getMessage());
			}
		case "cancel":
			dispose();
			break;
		}
	}

}
