package testJunit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.*;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Categoria;
import exceptions.EventAlreadyExist;
import exceptions.FechaPasada;
import test.businessLogic.TestFacadeImplementation;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateEventDataAccessTest {
	private DataAccess sut=  new DataAccess(false);
	private TestFacadeImplementation testBL = new TestFacadeImplementation();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String s1="Real-Barsa";
	private Date d1;
	//private Categoria ca= new Categoria ("Futbol");
	
	@BeforeEach
	public void setUp() {
		sut.open(false);
	}
	
	@AfterEach
	public void closes() {
		sut.close();
	}
	
	@Test
	@DisplayName("EventoAnterior")
	public void test1() {
		try {
			d1 = sdf.parse("21/05/2019");
			Categoria ca = sut.obtenerCategoria("Futbol");
			assertThrows(FechaPasada.class, () -> sut.createEvent(s1, d1, ca));
		} catch (ParseException e) {
			fail("Error con el parse");
		}
	}
	
	@Test
	@DisplayName("NoEventosEventDate")
	public void test2() {
		Event ev=null;
		try {
			d1=sdf.parse("21/12/2020");
			Categoria ca = sut.obtenerCategoria("Futbol");
			ev= sut.createEvent(s1, d1, ca);
			assertEquals(ev.getDescription(), s1);
			assertEquals(ev.getEventDate(), d1);
			assertEquals(ev.getCat(), ca);
		} catch (ParseException | FechaPasada | EventAlreadyExist e) {
			fail();
		}catch (Exception e2){
			e2.printStackTrace();
		}finally {
			testBL.removeEvent(ev);
		}
		
	}
	@Test
	@DisplayName("EventoAlreadyExist")
	public void test3() {
		try {
			s1="Getafe-Celta";
			d1=sdf.parse("17/11/2020");
			Categoria ca = sut.obtenerCategoria("Futbol");
			//Event e = sut.createEvent(s1, d1, ca);
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(s1, d1, ca));
		}catch (ParseException e) {
			fail("Error con el parse");
		}
		
	}
}
