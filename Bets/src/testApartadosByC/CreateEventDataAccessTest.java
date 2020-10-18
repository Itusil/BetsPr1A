package testApartadosByC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateEventDataAccessTest {
	private DataAccess sut=  new DataAccess(false);
	private TestFacadeImplementation testBL = new TestFacadeImplementation();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String s1="Getafe-Celta";
	private String s2="Real-Barsa";
	private Date d1;
	
	@BeforeEach
	public void setUp() {
		sut.open(false);
	}
	
	@AfterEach
	public void closes() {
		sut.close();
	}
	
	@Test
	@DisplayName("Fecha anterior a hoy	")
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
	@DisplayName("No hay eventos en la fecha de eventDate")
	public void test2() {
		Event ev=null;
		try {
			d1=sdf.parse("21/12/2020");
			Categoria ca = sut.obtenerCategoria("Futbol");
			ev= sut.createEvent(s2, d1, ca);
			assertEquals(ev.getDescription(), s2);
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
			d1=sdf.parse("17/11/2020");
			Categoria ca = sut.obtenerCategoria("Futbol");
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(s1, d1, ca));
		}catch (ParseException e) {
			fail("Error con el parse");
		}
		
	}
	
	@Test
	@DisplayName("No hay eventos en la fecha de eventDate con el mismo nombre que description")
	public void test4() {
		Event ev=null;
		try {
			d1=sdf.parse("21/12/2021");
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
}
