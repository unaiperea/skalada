package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import com.ipartek.formacion.skalada.bean.Grado;


public class TestModeloGrado {
	
	static ModeloGrado modelo = null;
	
	static String nombreGrado = "gradoMock";
	static String nombreGrado_updated = "updated";
	static int id;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelo = new ModeloGrado();
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

//	@Test
	public void testCRUD() {
		int total;				//Cantidad de registros iniciales en la tabla `test`
		int total_despues;		//Cantidad de registros en la tabla `test` tras insertar uno nuevo	
			
		
		//Test para comprobar que al insertar un nuevo registro aumenta el ID
		Grado g_insert = new Grado(nombreGrado);
		
		total = modelo.getAll().size();
		id = modelo.save(g_insert);
		total_despues = modelo.getAll().size();	// total = total_despues + 1	
		
		assertTrue(total == total_despues-1);
		assertNotNull(id);
		
		
		//Test para comprobar que al obtener un registro por su ID lo devuelve correctamente
		Grado g_get;		
		g_get = (Grado)modelo.getById(id);		
		assertEquals(nombreGrado, g_get.getNombre());
		
		
		//Test para comprobar que actualiza el registro indentificado por su ID
		Grado g_update = new Grado(nombreGrado_updated);
		g_update.setId(id);
		assertTrue(modelo.update(g_update));		
		g_get = (Grado)modelo.getById(id);		
		assertEquals(nombreGrado_updated, g_get.getNombre());
		
		
		//Test para comprobar que elimina el registro
		assertTrue(modelo.delete(id));
		assertTrue(total == modelo.getAll().size());	
	}
	
	@Test
	public void test() {
		//Intentar obtener un grado cuyo identificador no exista en la base de datos 
		id = -1;
		assertNull(modelo.getById(id));
		
		//Intentar actualizar un grado inexistente en la base de datos
		Grado g = new Grado(nombreGrado);
		g.setId(id);
		assertTrue(!modelo.update(g));
		
		//Intentar insertar un null e id existente
		assertTrue(-1 == modelo.save(null));
		assertTrue(!modelo.delete(id));
		//Intentar eliminar un grado con un id inexistente
		
		
		//Intentar insertar un grado con un id existente
		Grado g_1 = new Grado(nombreGrado);
		id = modelo.save(g_1);
		Grado g_2 = new Grado(nombreGrado);
		g_2.setId(id);
		
		assertTrue((id+1) == modelo.save(g_2));
		
		assertTrue(modelo.delete(id));
		assertTrue(modelo.delete(id+1));
		
		
		
	}

}
