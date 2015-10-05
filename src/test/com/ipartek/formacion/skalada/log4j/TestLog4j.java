package com.ipartek.formacion.skalada.log4j;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLog4j {

	private static final String PATH = "path";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//Configuración básica
		BasicConfigurator.configure();

		Logger log = Logger.getLogger("Logger de Test");
		log.warn("un warning");
		log.error("un error");
		assertTrue("No funciona log4j BasicConfigurator", true);
	}
		
	@Test
	public void testFicheroPropiedades() {
		try{
			//Cargar properties. Fichero configuracion de Log4j
			Properties props = new Properties();
			props.load( getClass().getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(props);
			
			//Escribir línea
			Logger log = Logger.getLogger("Logger de Test");
			log.info("un info");
			
			assertTrue("No encontrado fichero de propiedades log4j.properties", true);
		}catch(Exception e){
			fail("No encontrado fichero de propiedades log4j.properties" + PATH);
		}
		
	}

}
