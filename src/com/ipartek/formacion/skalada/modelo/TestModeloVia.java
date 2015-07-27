package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.Grado;
import com.ipartek.formacion.skalada.bean.Via;

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
		
		Via v1 = new Via("agarra lo que puedas");
		v1.setLongitud(30);
		v1.setGrado(Grado.DIFICIL);
		v1.setDescripcion(LOREM_IPSUM_1);
		
		
		assertTrue ("No se ha podido guardar", -1 < modelo.save(v1) );
		
		
		
	}

}
