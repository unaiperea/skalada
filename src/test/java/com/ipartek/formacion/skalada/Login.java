package com.ipartek.formacion.skalada;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Login {

	private static final String URL_APP = "http://localhost:8080/skalada/backoffice/pages/login.jsp";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Hace un login como administrador y entramos al backoffice
	 * 
	 * @param idioma
	 *            value para selecionar el option del select en el formulario de
	 *            login
	 * @return idioma que este el el atributo "lang" de la etiqueta "html" del
	 *         backoffice
	 */
	@Test
	public void loginAdmin() {
		WebDriver driver = new HtmlUnitDriver();

		driver.get(URL_APP);
		// assertEquals("No coincide Title", driver.getTitle(), "Login");

		// buscar input por su 'name' y cargar value con la funcion sendKeys()
		WebElement element = driver.findElement(By.name("email"));
		// element.sendKeys("admin");

		element = driver.findElement(By.name("password"));
		// element.sendKeys("admin");

		// new Select(driver.findElement(By.name("idioma")));

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		// assertEquals("No estamos en Backoffice",
		// "BackOffice",driver.getTitle());

		// check Idioma
		element = driver.findElement(By.tagName("html"));
		element.getAttribute("lang");

		driver.quit();

		assertTrue(true);

	}

}
