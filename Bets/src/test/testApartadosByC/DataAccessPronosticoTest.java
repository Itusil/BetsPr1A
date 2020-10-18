package test.testApartadosByC;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

class DataAccessPronosticoTest {

	private DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	private TestFacadeImplementation testBL = new TestFacadeImplementation();

	private String queryText = "A question";
	private Float betMinimum = 2.0f;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Event ev;	
	
	
	@BeforeEach
	public void setUp() throws ParseException, QuestionAlreadyExist {
		Date oneDate = sdf.parse("05/10/2022");
		ev = testBL.addEvent(queryText, oneDate);
		
		
		
		// configure the state of the system (create object in the dabatase)
		
		
		
	}
	
	@Test
	void test() throws QuestionAlreadyExist {
		Question q = sut.createQuestion(ev, queryText, betMinimum);
	
		assertNull(sut.obtenerPronostico(0));

	}
	
	@Test
	void test2() throws QuestionAlreadyExist {
		Question q = sut.createQuestion(ev, queryText, betMinimum);
		Pronostico pr1 = new Pronostico("Gana la real",23, q);
		Pronostico pr2 = new Pronostico("Empata la real",40, q);
		Pronostico pr3 = new Pronostico("Pierde la real",50, q);
		Pronostico pr4 = new Pronostico("Marca la real",60, q);
		
		q.addPron(pr1);
		q.addPron(pr2);
		q.addPron(pr3);
		q.addPron(pr4);
	
		assertNull(sut.obtenerPronostico(5));
	}
	
	
	@Test
	void test3() throws QuestionAlreadyExist {

		Question q = sut.createQuestion(ev, queryText, betMinimum);
		
		Pronostico pr1 = new Pronostico("Gana la real",23, q);
		Pronostico pr2 = new Pronostico("Empata la real",40, q);
		Pronostico pr3 = new Pronostico("Pierde la real",50, q);
		Pronostico pr4 = new Pronostico("Marca la real",60, q);
		
		q.addPron(pr1);
		q.addPron(pr2);
		q.addPron(pr3);
		q.addPron(pr4);
		
		assertEquals(pr3,sut.obtenerPronostico(q.getQuestionNumber()).get(2));
	}

}
