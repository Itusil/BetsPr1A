package gui;

import java.util.Locale;

import javax.swing.UIManager;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class ApplicationLauncher {

	public static void main(String[] args) {
		ConfigXML c = ConfigXML.getInstance();
		System.out.println(c.getLocale());
		Locale.setDefault(new Locale(c.getLocale()));
		System.out.println("Locale: " + Locale.getDefault());
		LoginGUI b = new LoginGUI();
		b.setVisible(true);

		try {
			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//if (c.isBusinessLogicLocal()) {// Lo comento porque siempre trabajaremos con local
				DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				appFacadeInterface=new BLFacadeImplementation(da);
			//	}
			LoginGUI.setBusinessLogic(appFacadeInterface);

		} catch (Exception e) {
			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
	}
}
