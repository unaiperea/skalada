package com.ipartek.formacion.skalada.modelo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
	@Ignore
	public void test() {
		

		
		
	}

}