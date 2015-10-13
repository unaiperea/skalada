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

	private static ModeloTipoEscalada modelo = null;

	static final String NOMBRETIPOESCALADA = "TipoEscaladaMock";
	static final String DESCRIPCIONTIPOESCALADA = "Lorem impsu ÑéÁ";
	static final String NOMBRETIPOESCALADA_UPDATED = "updated";
	static final String DESCRIPCIONTIPOESCALADA_UPDATED = "Updated Lorem impsun ÑñéÁ";

	private static int id;
	private static TipoEscalada teGet;
	private TipoEscalada teInsert;
	private TipoEscalada teUpdate;

	private static int total; // Cantidad de registros iniciales en la tabla
	// `tipo_escalada`
	private static int totalDespues; // Cantidad de registros en la tabla

	// `tipo_escalada` tras insertar uno
	// nuevo

	@BeforeClass()
	public static void setUpBeforeClass() throws Exception {
		modelo = new ModeloTipoEscalada();
		total = modelo.getAll(null).size();
	}

	@AfterClass()
	public static void tearDownAfterClass() throws Exception {
		assertTrue(total == modelo.getAll(null).size());
		modelo = null;
	}

	@Before()
	public void setUp() throws Exception {
		this.teInsert = new TipoEscalada(NOMBRETIPOESCALADA);
		this.teInsert.setDescripcion(DESCRIPCIONTIPOESCALADA);
		id = modelo.save(this.teInsert);
	}

	@After()
	public void tearDown() throws Exception {
		this.teInsert = null;
		modelo.delete(id);
	}

	@Test()
	public void testGetAll() {
		assertTrue("getAll() devuelve siempre un numero positivo", total > 0);
	}

	@Test()
	public void testInsertar() {
		// Test para comprobar que al insertar un nuevo registro aumenta el ID
		totalDespues = modelo.getAll(null).size(); // total = total_despues + 1

		assertTrue("al insertar un nuevo registro aumenta el ID" + total
				+ totalDespues, total == (totalDespues - 1));
		assertTrue("save(o) devuelve siempre un id positivo", (id != -1)
				&& (id > 0));

		// Intentar insertar un null
		assertTrue(-1 == modelo.save(null));

		// Intentar insertar un TipoEscalada con un id existente
		TipoEscalada te_2 = new TipoEscalada(NOMBRETIPOESCALADA);
		te_2.setId(id);

		assertTrue((id + 1) == modelo.save(te_2));

		modelo.delete(id + 1);
	}

	@Test()
	public void testGetById() {
		// Test para comprobar que al obtener un registro por su ID lo devuelve
		// correctamente
		teGet = (TipoEscalada) modelo.getById(id);
		assertTrue(id == teGet.getId());
		assertEquals(NOMBRETIPOESCALADA, teGet.getNombre());
		assertEquals(DESCRIPCIONTIPOESCALADA, teGet.getDescripcion());

		// Intentar obtener un TipoEscalada cuyo identificador no exista en la
		// base de datos
		assertNull(modelo.getById(-1));
		assertNull(modelo.getById(0));
	}

	@Test()
	public void testUpdate() {
		// Test para comprobar que actualiza el registro indentificado por su ID
		this.teUpdate = new TipoEscalada(NOMBRETIPOESCALADA_UPDATED);
		this.teUpdate.setDescripcion(DESCRIPCIONTIPOESCALADA_UPDATED);
		this.teUpdate.setId(id);

		assertTrue(modelo.update(this.teUpdate));

		teGet = (TipoEscalada) modelo.getById(this.teUpdate.getId());
		assertTrue(id == teGet.getId());
		assertEquals(NOMBRETIPOESCALADA_UPDATED, teGet.getNombre());
		assertEquals(DESCRIPCIONTIPOESCALADA_UPDATED, teGet.getDescripcion());

		// Intentar actualizar un TipoEscalada inexistente en la base de datos
		TipoEscalada te = new TipoEscalada(NOMBRETIPOESCALADA);
		te.setId(-1);
		assertTrue(!modelo.update(te));
		// Intentar actualizar un null
		assertTrue(!modelo.update(null));
	}

	@Test()
	public void testdelete() {
		// Test para comprobar que elimina el registro
		TipoEscalada te_2 = new TipoEscalada(NOMBRETIPOESCALADA);
		assertTrue(modelo.delete(modelo.save(te_2)));

		assertTrue((total + 1) == modelo.getAll(null).size()); // (total + 1)
																// Porque
		// en el set app se
		// inserta un
		// registro

		// Intentar eliminar un TipoEscalada con un id inexistente
		assertTrue(!modelo.delete(0));
	}

}
