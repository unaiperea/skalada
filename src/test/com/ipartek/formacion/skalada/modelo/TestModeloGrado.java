package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.bean.Grado;


public class TestModeloGrado {
	
	static ModeloGrado modelo = null;
	
	static String nombreGrado = "gradoMock";
	static String descripcionGrado = "Lorem Ipsum Ñ, Á";
	static String nombreGrado_updated = "updated";
	static String descripcionGrado_updated = "Lorem Ipsum Ñ, Á";
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

	@Test
	//@Ignore
	public void testCRUD() {
		int total;				//Cantidad de registros iniciales en la tabla `test`
		int total_despues;		//Cantidad de registros en la tabla `test` tras insertar uno nuevo	
			
		//Test para comprobar que al insertar un nuevo registro aumenta el ID
		Grado g_insert = new Grado(nombreGrado);
		g_insert.setDescripcion(descripcionGrado);
		
		total = modelo.getAll().size();
		id = modelo.save(g_insert);
		
		assertTrue("No se ha insertado o id mal generado",
				(id != -1) && (id != 0));
		
		total_despues = modelo.getAll().size();	// total = total_despues + 1
		assertTrue("No se ha insertado o getAll() no funciona",
					total == total_despues-1);
		
		//Test para comprobar que al obtener un registro por su ID lo devuelve correctamente
		Grado g_get;	
		g_get = (Grado)modelo.getById(id);		
		assertEquals(nombreGrado, g_get.getNombre());
		assertEquals(descripcionGrado, g_get.getDescripcion());
		assertEquals(id, g_get.getId());
		
		//Test para comprobar que actualiza el registro indentificado por su ID
		Grado g_update = new Grado(nombreGrado_updated);
		g_update.setDescripcion(descripcionGrado_updated);
		g_update.setId(id);
		
		//Modificamos bbdd
		assertTrue(modelo.update(g_update));		
		g_get = (Grado)modelo.getById(g_update.getId());		
		assertEquals(nombreGrado_updated, g_get.getNombre());
		assertEquals(descripcionGrado_updated, g_get.getDescripcion());
		assertEquals(g_update.getId(), g_get.getId());
		
		//Test para comprobar que elimina el registro
		assertTrue(modelo.delete(id));
		assertTrue(total == modelo.getAll().size());	
	}
	
	@Test
	public void testCasosError() {
		//Intentar obtener un grado cuyo identificador no exista en la base de datos 
		id = -1;
		assertNull(modelo.getById(id));
		assertNull(modelo.getById(0));
		
		//Intentar actualizar un grado inexistente en la base de datos
		Grado g = new Grado(nombreGrado);
		g.setId(id);
		assertTrue(!modelo.update(g));
		assertTrue(!modelo.update(null));
		
		//Intentar insertar un null
		assertTrue("Intentar insertar un null" , -1 == modelo.save(null));
		
		//"Eliminar id inexistente"
		assertTrue("Eliminar id inexistente" , !modelo.delete(id));
		
		
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