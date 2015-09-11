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
import com.ipartek.formacion.skalada.bean.TipoEscalada;


public class TestModeloTipoEscalada {
	
	static ModeloTipoEscalada modelo = null;
	
	static String nombreTipoEscalada = "gradoMock";
	static String descripcionTipoEscalada = "Lorem Ipsum Ñ, Á";
	static String nombreTipoEscalada_updated = "updated";
	static String descripcionTipoEscalada_updated = "Lorem Ipsum Ñ, Á";
	static int id;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelo = new ModeloTipoEscalada();
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
		TipoEscalada tp_insert = new TipoEscalada(nombreTipoEscalada);
		tp_insert.setDescripcion(descripcionTipoEscalada);
		
		total = modelo.getAll().size();
		id = modelo.save(tp_insert);
		
		assertTrue("No se ha insertado o id mal generado",
				(id != -1) && (id != 0));
		
		total_despues = modelo.getAll().size();	// total = total_despues + 1
		assertTrue("No se ha insertado o getAll() no funciona",
					total == total_despues-1);
		
		//Test para comprobar que al obtener un registro por su ID lo devuelve correctamente
		TipoEscalada tp_get;	
		tp_get = (TipoEscalada)modelo.getById(id);		
		assertEquals(nombreTipoEscalada, tp_get.getNombre());
		assertEquals(descripcionTipoEscalada, tp_get.getDescripcion());
		assertEquals(id, tp_get.getId());
		
		//Test para comprobar que actualiza el registro indentificado por su ID
		Grado tp_update = new Grado(nombreTipoEscalada_updated);
		tp_update.setDescripcion(descripcionTipoEscalada_updated);
		tp_update.setId(id);
		
		//Modificamos bbdd
		assertTrue(modelo.update(tp_update));		
		tp_get = (TipoEscalada)modelo.getById(tp_update.getId());		
		assertEquals(nombreTipoEscalada_updated, tp_get.getNombre());
		assertEquals(descripcionTipoEscalada_updated, tp_get.getDescripcion());
		assertEquals(tp_update.getId(), tp_get.getId());
		
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
		TipoEscalada tp = new TipoEscalada(nombreTipoEscalada);
		tp.setId(id);
		assertTrue(!modelo.update(tp));
		assertTrue(!modelo.update(null));
		
		//Intentar insertar un null
		assertTrue("Intentar insertar un null" , -1 == modelo.save(null));
		
		//"Eliminar id inexistente"
		assertTrue("Eliminar id inexistente" , !modelo.delete(id));
		
		
		//Intentar insertar un grado con un id existente
		TipoEscalada tp_1 = new TipoEscalada(nombreTipoEscalada);
		id = modelo.save(tp_1);
		TipoEscalada tp_2 = new TipoEscalada(nombreTipoEscalada);
		tp_2.setId(id);
		
		assertTrue((id+1) == modelo.save(tp_2));
		
		assertTrue(modelo.delete(id));
		assertTrue(modelo.delete(id+1));
		
		
		
	}

}