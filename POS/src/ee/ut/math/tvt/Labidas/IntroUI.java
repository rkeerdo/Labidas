package ee.ut.math.tvt.Labidas;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class IntroUI {
	JFrame window;
	JTextArea creditBox;
	JLabel logo;
	
	public IntroUI() {
		addWindowSettings();
		addCreditText();
		addLogo();
		window.repaint();
	}
	
	private void addWindowSettings() {
		window = new JFrame();
		window.setTitle("Intro");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(null);
		window.setVisible(true);
	}
	
	private void addCreditText() {
		creditBox = new JTextArea("Team LABIDAS:\n"
				+ "Joosep Heinmets (leader)\n"
				+ "Rainer Keerdo\n"
				+ "Kaspar Kesli\n"
				+ "Allan Kustavus\n\n"
				+ "Leader's e-mail: joosephe@gmail.com\n\n"
				+ "Version number: 1.0.0");
		creditBox.setBounds(0,0,250,145);
		creditBox.setEditable(false);
		window.add(creditBox);
	}
	
	private void addLogo() {
		BufferedImage tmpLogo = null;
		try {
			tmpLogo = ImageIO.read(new File("res/LABIDAS.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logo = new JLabel(new ImageIcon(tmpLogo));
		logo.setBounds(0,145,312,178);
		window.add(logo);
		logo.setVisible(true);
	}
}
