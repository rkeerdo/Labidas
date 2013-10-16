package ee.ut.math.tvt.Labidas;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
		Properties application = new Properties();
		Properties version=new Properties();
		String info[]=new String[6];
		try{
			application.load(new FileInputStream("application.properties"));
			info[0]="Course: "+application.getProperty("course");
			info[1]="Team name: "+application.getProperty("team.name");
			info[2]="Team leader: "+application.getProperty("team.leader.name");
			info[3]="Team leader's email: "+application.getProperty("team.leader.email");
			info[4]="Team members: "+application.getProperty("team.members");
			version.load(new FileInputStream("version.properties"));
			info[5]="Version number: "+version.getProperty("build.number");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		creditBox = new JTextArea(info[0]+"\n"
				+ info[1]+"\n"
				+ info[2]+"\n"
				+ info[3]+"\n"
				+ info[4]+"\n"
				+ info[5]);
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
