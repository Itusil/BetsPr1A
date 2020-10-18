package test.businessLogic;
/**
 * Auxiliary FacadeImplementation class for testing DataAccess
 */

import java.util.Date;

import configuration.ConfigXML;
import domain.Categoria;
import domain.Event;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;
 	
    
	   public TestFacadeImplementation()  {
			
			System.out.println("Creating TestFacadeImplementation instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeEvent(Event ev) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeEvent(ev);
			dbManagerTest.close();
			return b;

		}
		
		public Event addEvent(String desc, Date d) {
			dbManagerTest.open();
			Event o=dbManagerTest.addEvent(desc,d);
			dbManagerTest.close();
			return o;
		}
		
		/**
		 * se a�ade un evento con su categor�a
		 * @param desc descripci�n del evento
		 * @param d fecha del evento
		 * @param ca categor�a del evento
		 * @return el evento a�adido
		 */
		public Event addEventWithCategory(String desc, Date d, Categoria ca) {
			dbManagerTest.open();
			Event ev = dbManagerTest.addEventWithCategory(desc,d,ca);
			dbManagerTest.close();
			return ev;
	    }
		
		/**
		 * se elimina la categor�a pasada como par�metro
		 * @param ca la categor�a a eliminar
		 * @return devuelve true si se ha eliminado y false si no
		 */
		public boolean removeCategory(Categoria ca) {
			dbManagerTest.open();
			boolean b = dbManagerTest.removeCategory(ca);
			dbManagerTest.close();
			return b;
		}

}
