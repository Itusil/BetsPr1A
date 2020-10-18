package test.junitMock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Categoria;
import exceptions.NoExistCategory;

class GetEventsCategoryMonthMock {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	
	private BLFacadeImplementation bl = new BLFacadeImplementation(dataAccess);
	
	int month;
	int year;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Calendar today = Calendar.getInstance();
		month = today.get(Calendar.MONTH);
		month += 1;
		year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
	}
	
	@Test
	@DisplayName("Test Categoría en BD")
	public void testCategoriaBD() {
		Categoria categoria1 = new Categoria("MotoGP");
		Vector<Date> fechas = new Vector<Date>();
		fechas.add(UtilDate.newDate(year, month, 7));
		
		Mockito.doReturn(categoria1).when(dataAccess).obtenerCategoriaPorDescripcion(Mockito.anyString());
		
		Mockito.doReturn(fechas).when(dataAccess).getEventsCategoryMonth(Mockito.any(Date.class), Mockito.any(Categoria.class));
		
		try {
			assertEquals(fechas, bl.getEventsCategoryMonth(UtilDate.newDate(year, month, 7), categoria1));
		} catch (NoExistCategory e) {
			e.printStackTrace();
		}
		
		Mockito.verify(dataAccess,Mockito.times(2)).open(false);
		Mockito.verify(dataAccess,Mockito.times(2)).close();	
	}
	
	@Test
	@DisplayName("Test Categoría no in BD")
	public void testCategoriaNoInBD() {
		Categoria categoria2 = new Categoria("Natación");
		
		Mockito.doReturn(null).when(dataAccess).obtenerCategoriaPorDescripcion(Mockito.anyString());
		
		assertThrows(NoExistCategory.class, () -> bl.getEventsCategoryMonth(null, categoria2));
		
		Mockito.verify(dataAccess,Mockito.times(2)).open(false);
	}

}
