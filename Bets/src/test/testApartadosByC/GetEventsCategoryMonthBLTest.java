package test.testApartadosByC;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Categoria;
import exceptions.NoExistCategory;

class GetEventsCategoryMonthBLTest {

	static BLFacadeImplementation bl;

	@BeforeAll
	public static void setUp() {
		DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		bl = new BLFacadeImplementation(da);
	}

	@Test
	@DisplayName("Test: Categoría es null")
	public void testCategoriaNull() {
		Categoria categoria3 = null;
		//le paso date como null ya que sabemos que no va a llegar
		assertThrows(NoExistCategory.class, () -> bl.getEventsCategoryMonth(null, categoria3));
	}

}
