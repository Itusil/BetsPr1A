package testJunit;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Categoria;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.FechaPasada;
import exceptions.QuestionAlreadyExist;

public class CreateEventTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	//Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	@DisplayName("Fecha pasada")
	public void test1() {
		Date d1;
		try {
			String s1 = "Real-Barsa";
			d1 = sdf.parse("21/05/2019");
			Categoria ca= new Categoria("Futbol");
			Mockito.doThrow(FechaPasada.class).when(dataAccess).createEvent(s1, d1, ca);
			assertThrows(FechaPasada.class,() -> sut.createEvent(s1, d1, ca));
		} catch (ParseException | FechaPasada | EventAlreadyExist e) {
			fail("Error con el parse");
		}
	}
	
	@Test
	@DisplayName("Evento existente")
	public void test2() {
		String s1 = "Getafe-Celta";
		try {
			Date d1 = sdf.parse("17/11/2020");
			Categoria ca= new Categoria("Futbol");
			Mockito.doThrow(EventAlreadyExist.class).when(dataAccess).createEvent(s1, d1,ca);
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(s1, d1, ca));
		} catch (ParseException | FechaPasada | EventAlreadyExist e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Todo correcto")
	public void test3() {
		String s1 = "Real-Barsa";
		try {
			Date d1 = sdf.parse("21/12/2020");
			Categoria ca= new Categoria("Futbol");
			Event e = new Event(s1,d1,ca);
			Mockito.doReturn(e).when(dataAccess).createEvent(s1, d1, ca);
			assertEquals(e, sut.createEvent(s1, d1, ca));
			}catch(Exception e) {
				fail();
			}
}
}
