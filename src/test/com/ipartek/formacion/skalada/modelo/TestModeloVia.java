package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;

public class TestModeloVia {
	
	private static final String LOREM_IPSUM_1 = "Bacon ipsum dolor amet jowl meatloaf tenderloin beef ribs turducken pork belly boudin meatball picanha. Prosciutto frankfurter picanha, boudin ham capicola turkey shankle landjaeger salami bresaola pork biltong strip steak. Bresaola pastrami doner, short loin pig boudin alcatra swine rump short ribs hamburger bacon ham hock porchetta ribeye. Pork belly turkey ball tip ribeye spare ribs, sausage biltong cupim.";
	
	static ModeloVia modelo = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			
		modelo = new ModeloVia();
			
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		modelo = null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		String nombreVia = "agarra lo que puedas";
		Grado grado = new Grado("");
		int longitud = 0;
		//Zona
		String nombreZona = "Ogoño";
		Zona zona = new Zona(nombreZona); //Creamos una zona sin array de sectores
		//Sectores
		String nombreSector1 = "cara sur";
		Sector sector1 = new Sector(nombreSector1, zona);
		
		//Tipo Escalada
		String nombreTipoEscalada = "deportiva";
		TipoEscalada tipoEscalada = new TipoEscalada(nombreTipoEscalada);
		
		Via v1 = new Via(nombreVia, grado, longitud, tipoEscalada, sector1);
		v1.setDescripcion(LOREM_IPSUM_1);
		
		//Guardar Objeto
		int id = modelo.save(v1);		
		assertTrue ("No se ha podido guardar", -1 < id);
		
		//recuperar por su ID y comprobar que sea igual
		Via v2 = (Via)modelo.getById(id);
		assertEquals( nombreVia, v2.getNombre() );
		assertEquals( longitud , v2.getLongitud() );
		assertEquals( grado.getNombre(), v2.getGrado().getNombre() );
		assertEquals( LOREM_IPSUM_1, v2.getDescripcion() );
		
		//eliminar
		assertTrue("No se ha podido eliminar", modelo.delete(id));
		
	}

}
