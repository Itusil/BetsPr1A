package test.testApartadosByC;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Categoria;
import domain.Event;
import test.businessLogic.TestFacadeImplementation;

class GetEventsCategoryMonthDATest {

	private DataAccess sut = new DataAccess(true);
	private TestFacadeImplementation testBL = new TestFacadeImplementation();
	
	@Test
	@DisplayName("Test: Encontramos Eventos")
	public void TestFoundEvents() {
		Date fecha = UtilDate.newDate(2020, 12, 7);
		Categoria category = new Categoria ("MotoGP");
		
		Event ev = testBL.addEventWithCategory("QNB Gran Prix of Qatar", fecha, category);

		Vector<Date> esperado = new Vector<Date>();
		esperado.add(fecha);
	
		assertEquals(esperado, sut.getEventsCategoryMonth(fecha, category));
		
		boolean b = testBL.removeEvent(ev);
		boolean c = testBL.removeCategory(category);
		assertTrue(b);
		assertTrue(c);
	}
	
	@Test
	@DisplayName("Test: No encontramos Eventos en esas fechas")
	public void TestNotFoundEventsDate() {
		Date fecha = UtilDate.newDate(2020, 12, 7);
		Date fecha2 = UtilDate.newDate(2020,10, 7);
		Categoria category = new Categoria ("MotoGP");
		Categoria category2 = new Categoria ("Boxeo");
		
		Event ev = testBL.addEventWithCategory("QNB Gran Prix of Qatar", fecha, category);
		Event ev2 = testBL.addEventWithCategory("Evento2", fecha2, category2);

		Vector<Date> esperado = new Vector<Date>();
		esperado.add(fecha);
	
		assertEquals(esperado, sut.getEventsCategoryMonth(fecha, category));
		
		boolean b = testBL.removeEvent(ev);
		assertTrue(b);
		b = testBL.removeEvent(ev2);
		assertTrue(b);
		boolean c = testBL.removeCategory(category);
		assertTrue(c);
		c = testBL.removeCategory(category2);
		assertTrue(c);
	}
	
	@Test
	@DisplayName("Test: No encontramos eventos con esa categoría")
	public void TestNotFoundEventsCategory() {
		Date fecha = UtilDate.newDate(2020, 12, 7);
		Categoria category = new Categoria ("MotoGP");
		Categoria category2 = new Categoria ("Boxeo");
		
		Event ev = testBL.addEventWithCategory("QNB Gran Prix of Qatar", fecha, category);

		Vector<Date> esperado = new Vector<Date>();
	
		assertEquals(esperado, sut.getEventsCategoryMonth(fecha, category2));
		
		boolean b = testBL.removeEvent(ev);
		boolean c = testBL.removeCategory(category);
		assertTrue(b);
		assertTrue(c);
	}

}
