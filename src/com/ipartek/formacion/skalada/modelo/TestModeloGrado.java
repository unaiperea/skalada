package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.bean.Grado;

public class TestModeloGrado {

	String nombreGrado1 = "Mock1";
	Grado grado1 = new Grado(nombreGrado1);
	ModeloGrado modeloGrado = new ModeloGrado();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//Antes de arrancar cada test
		assertNotEquals("No insertado " + grado1.toString(),
						-1,
						modeloGrado.save(grado1)
						); //-1 en caso de fallo
		
	}

	@After
	public void tearDown() throws Exception {
		//Cuando termine cada test
		assertTrue("No eliminado " + grado1.toString(),
					modeloGrado.delete(grado1.getId())
					);
	}

	@Test
	public void testCrud() {
		//int total = modeloGrado.getAll().size();
		d
		
	}

}
