package com.ipartek.formacion.skalada.modelo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.Grado;
import com.ipartek.formacion.skalada.bean.Via;

public class TestModeloVia {
	
	private static final String LOREM_IPSUM_1 = "Bacon ipsum dolor amet swine boudin spare ribs, jerky flank pig landjaeger shoulder porchetta ham leberkas salami strip steak chicken. Bacon hamburger ham hock swine kevin pork belly strip steak tenderloin t-bone spare ribs. T-bone ham hock pancetta jowl ham pastrami shoulder meatball pork loin drumstick leberkas. Hamburger meatball salami flank chuck swine picanha filet mignon pork shankle meatloaf ribeye.";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		ModeloVia modelo = null;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		modelo = new ModeloVia();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
		modelo = null;
	}

	@Test
	public void test() {
		Via v1 = new Via("agarra lo que puedas");
		v1.setLongitud(30);;
		v1.setGrado(Grado.DIFICIL);
		v1.setDescripcion(LOREM_IPSUM_1);
		
		assertTrue("No se ha podido guardar", -1 < modelo.save(v1);
	}

}
