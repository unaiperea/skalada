package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.bean.TipoEscalada;


public class TestModeloTipoEscalada {
	
	static ModeloTipoEscalada modelo = null;
	
	static String nombreTipoEscalada = "TipoEscaladaMock";
	static String descripcionTipoEscalada = "Lorem impsu ÑéÁ";	
	static String nombreTipoEscalada_updated = "updated";
	static String descripcionTipoEscalada_updated = "Updated Lorem impsun ÑñéÁ";
	
	static int id;	
	static TipoEscalada te_get;
	static TipoEscalada te_insert;
	static TipoEscalada te_update;
	
	static int total;				//Cantidad de registros iniciales en la tabla `tipo_escalada`
	static int total_despues;		//Cantidad de registros en la tabla `tipo_escalada` tras insertar uno nuevo	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		modelo = new ModeloTipoEscalada();
		total = modelo.getAll().size();	
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		assertTrue(total == modelo.getAll().size());
		modelo = null;
	}
	

	@Before
	public void setUp() throws Exception {		
		te_insert = new TipoEscalada(nombreTipoEscalada);
		te_insert.setDescripcion(descripcionTipoEscalada);
		id = modelo.save(te_insert);
	}
	@After
	public void tearDown() throws Exception {
		te_insert = null;
		modelo.delete(id);
	}

	
	@Test
	public void testGetAll() {		
		assertTrue("getAll() devuelve siempre un numero positivo", total > 0);
	}
	
	@Test
	public void testInsertar() {
		//Test para comprobar que al insertar un nuevo registro aumenta el ID
		total_despues = modelo.getAll().size();	// total = total_despues + 1	
		
		assertTrue("al insertar un nuevo registro aumenta el ID"+ total + total_despues, total == total_despues-1);
		assertTrue("save(o) devuelve siempre un id positivo",(id != -1) && (id > 0));
		
		//Intentar insertar un null 
		assertTrue(-1 == modelo.save(null));
		
		//Intentar insertar un TipoEscalada con un id existente
		TipoEscalada te_2 = new TipoEscalada(nombreTipoEscalada);
		te_2.setId(id);
		
		assertTrue((id+1) == modelo.save(te_2));
		
		modelo.delete(id+1);
	}
		
	@Test
	public void testGetById() {	
		//Test para comprobar que al obtener un registro por su ID lo devuelve correctamente				
		te_get = (TipoEscalada)modelo.getById(id);	
		assertTrue(id==te_get.getId());
		assertEquals(nombreTipoEscalada, te_get.getNombre());
		assertEquals(descripcionTipoEscalada, te_get.getDescripcion());
		
		//Intentar obtener un TipoEscalada cuyo identificador no exista en la base de datos 
		assertNull(modelo.getById(-1));
		assertNull(modelo.getById(0));
	}
	
	@Test
	public void testUpdate() {		
		//Test para comprobar que actualiza el registro indentificado por su ID
		te_update = new TipoEscalada(nombreTipoEscalada_updated);
		te_update.setDescripcion(descripcionTipoEscalada_updated);
		te_update.setId(id);
		
		assertTrue(modelo.update(te_update));
		
		te_get = (TipoEscalada)modelo.getById(te_update.getId());	
		assertTrue(id==te_get.getId());
		assertEquals(nombreTipoEscalada_updated, te_get.getNombre());
		assertEquals(descripcionTipoEscalada_updated, te_get.getDescripcion());
		
		//Intentar actualizar un TipoEscalada inexistente en la base de datos
		TipoEscalada te = new TipoEscalada(nombreTipoEscalada);
		te.setId(-1);
		assertTrue(!modelo.update(te));
		//Intentar actualizar un null
		assertTrue(!modelo.update(null));
	}
	
	@Test
	public void testdelete() {
		//Test para comprobar que elimina el registro
		TipoEscalada te_2 = new TipoEscalada(nombreTipoEscalada);
		assertTrue(modelo.delete(modelo.save(te_2)));
		
		assertTrue(total + 1 == modelo.getAll().size());	// (total + 1) Porque en el set app se inserta un registro 
		
		//Intentar eliminar un TipoEscalada con un id inexistente
		assertTrue(!modelo.delete(0));
	}
	
}
