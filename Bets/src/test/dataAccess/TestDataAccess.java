package test.dataAccess;
/**
 * Auxiliary DataAccess class for testing 
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Categoria;
import domain.Event;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEvent(String desc, Date d) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		
		/**
		 * se añade un evento con su categoría
		 * @param desc descripción del evento
		 * @param d fecha del evento
		 * @param ca categoría del evento
		 * @return el evento añadido
		 */
		public Event addEventWithCategory(String desc, Date d, Categoria ca) {
			System.out.println(">> DataAccessTest: addEventWithCategory");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d,ca);
					db.persist(ca);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		
		/**
		 * se elimina la categoría pasada como parámetro
		 * @param ca la categoría a eliminar
		 * @return devuelve true si se ha eliminado y false si no
		 */
		public boolean removeCategory(Categoria ca) {
			System.out.println(">> DataAccessTest: removeCategory");
			Categoria c = db.find(Categoria.class, ca.getCatNumber());
			if (c!=null) {
				db.getTransaction().begin();
				db.remove(c);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
		}
}

