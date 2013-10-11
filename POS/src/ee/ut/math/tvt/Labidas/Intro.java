package ee.ut.math.tvt.Labidas;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Intro {
	private static org.apache.log4j.Logger logger = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		IntroUI mainWindow = new IntroUI();
		logger.info("Window has been opened.");
	}

}
