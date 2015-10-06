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
	static String nombreTipoEscaladaUpdated = "updated";
	static String descripcionTipoEscaladaUpdated = "Updated Lorem impsun ÑñéÁ";

	static int id;
	static TipoEscalada teGet;
	static TipoEscalada teInsert;
	static TipoEscalada teUpdate;

	static int total; // Cantidad de registros iniciales en la tabla
	// `tipo_escalada`
	static int totalDespues; // Cantidad de registros en la tabla
	// `tipo_escalada` tras insertar uno nuevo

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
		teInsert = new TipoEscalada(nombreTipoEscalada);
		teInsert.setDescripcion(descripcionTipoEscalada);
		id = modelo.save(teInsert);
	}
	@After
	public void tearDown() throws Exception {
		teInsert = null;
		modelo.delete(id);
	}

	@Test
	public void testGetAll() {
		assertTrue("getAll() devuelve siempre un numero positivo", total > 0);
	}

	@Test
	public void testInsertar() {
		// Test para comprobar que al insertar un nuevo registro aumenta el ID
		totalDespues = modelo.getAll().size(); // total = total_despues + 1

		assertTrue("al insertar un nuevo registro aumenta el ID" + total
				+ totalDespues, total == totalDespues - 1);
		assertTrue("save(o) devuelve siempre un id positivo", (id != -1)
				&& (id > 0));

		// Intentar insertar un null
		assertTrue(-1 == modelo.save(null));

		// Intentar insertar un TipoEscalada con un id existente
		TipoEscalada te_2 = new TipoEscalada(nombreTipoEscalada);
		te_2.setId(id);

		assertTrue((id + 1) == modelo.save(te_2));

		modelo.delete(id + 1);
	}

	@Test
	public void testGetById() {
		// Test para comprobar que al obtener un registro por su ID lo devuelve
		// correctamente
		teGet = modelo.getById(id);
		assertTrue(id == teGet.getId());
		assertEquals(nombreTipoEscalada, teGet.getNombre());
		assertEquals(descripcionTipoEscalada, teGet.getDescripcion());

		// Intentar obtener un TipoEscalada cuyo identificador no exista en la
		// base de datos
		assertNull(modelo.getById(-1));
		assertNull(modelo.getById(0));
	}

	@Test
	public void testUpdate() {
		// Test para comprobar que actualiza el registro indentificado por su ID
		teUpdate = new TipoEscalada(nombreTipoEscaladaUpdated);
		teUpdate.setDescripcion(descripcionTipoEscaladaUpdated);
		teUpdate.setId(id);

		assertTrue(modelo.update(teUpdate));

		teGet = modelo.getById(teUpdate.getId());
		assertTrue(id == teGet.getId());
		assertEquals(nombreTipoEscaladaUpdated, teGet.getNombre());
		assertEquals(descripcionTipoEscaladaUpdated, teGet.getDescripcion());

		// Intentar actualizar un TipoEscalada inexistente en la base de datos
		TipoEscalada te = new TipoEscalada(nombreTipoEscalada);
		te.setId(-1);
		assertTrue(!modelo.update(te));
		// Intentar actualizar un null
		assertTrue(!modelo.update(null));
	}

	@Test
	public void testdelete() {
		// Test para comprobar que elimina el registro
		TipoEscalada te_2 = new TipoEscalada(nombreTipoEscalada);
		assertTrue(modelo.delete(modelo.save(te_2)));

		assertTrue(total + 1 == modelo.getAll().size()); // (total + 1) Porque
		// en el set app se
		// inserta un
		// registro

		// Intentar eliminar un TipoEscalada con un id inexistente
		assertTrue(!modelo.delete(0));
	}

}
