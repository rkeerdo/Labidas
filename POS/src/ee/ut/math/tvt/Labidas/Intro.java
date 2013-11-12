package ee.ut.math.tvt.Labidas;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;


public class Intro {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(Intro.class);
	private static final String MODE = "console";
	public static HibernateDataService service;

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		service = new HibernateDataService();
		final SalesDomainController domainController = new SalesDomainControllerImpl();

		if (args.length == 1 && args[0].equals(MODE)) {
			logger.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			IntroUI introUI = new IntroUI();

			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

