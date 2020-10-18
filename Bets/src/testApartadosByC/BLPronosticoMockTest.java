package testApartadosByC;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import exceptions.EventFinished;
import exceptions.NumPreguntaNegativo;
import exceptions.QuestionAlreadyExist;

class BLPronosticoMockTest {

	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	@Mock
	BLFacade sut = new BLFacadeImplementation(dataAccess);

	// sut.createQuestion: The event has one question with a queryText.

	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	
	@Test
	@DisplayName("Test 1")
	public void test1() throws NumPreguntaNegativo {
		Mockito.doThrow(NumPreguntaNegativo.class).when(sut).obtenerPronostico(-3);
		assertThrows(NumPreguntaNegativo.class,  ()-> sut.obtenerPronostico(-3));
	}
	
	
	@Test
	@DisplayName("Test2")
	public void test2() throws NumPreguntaNegativo, EventFinished, QuestionAlreadyExist {
		Question q = new Question( queryText, betMinimum,mockedEvent);
		Pronostico pr1 = new Pronostico("Gana la real",23, q);
		Pronostico pr2 = new Pronostico("Empata la real",40, q);
		Pronostico pr3 = new Pronostico("Pierde la real",50, q);
		Pronostico pr4 = new Pronostico("Marca la real",60, q);
		Vector<Pronostico> sol = new Vector<Pronostico>();
		sol.add(pr1);
	
		
		
		Mockito.doReturn(sol).when(sut).obtenerPronostico(0);
		
		
		assertNotNull(q);
		assertEquals(sol,sut.obtenerPronostico(0));
		
	}

}
