package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.util.EnviarEmails;

public class TestEnviarEmails {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private static final String TEST_EMAIL_TEMPLATE_REGISTRO = "C:\\Home\\Proyectos\\eclipse\\skalada\\WebContent\\emails\\registro.html";
	
	@Test
	public void testEnviar() {
		
		EnviarEmails correo = new EnviarEmails();
		
		correo.setDireccionFrom("skalada.ipartek@gmail.com"); //Sin espacios
		correo.setDireccionDestino("unaiperea@gmail.com");
		correo.setMessageSubject("Email de prueba enviado desde Java");
		
		
		
		correo.setMessageText("Cuerpo del mensaje de texto"); //Se pueden tener plantillas de html y enviarlas
		
		assertTrue(
				 "Email no enviado " + correo.toString() ,
				  correo.enviar()
				);
		
		
	}
	
	@Test
	public void testEnviarRegistro() {
		
		String email   = "unaiperea@gmail.com";
		String url     = Constantes.SERVER + Constantes.CONTROLLER_SIGNUP+"?accion="+Constantes.ACCION_VALIDAR+"&email="+email;
		String usuario = "Antton Gorriti";
		String cuerpo  = "";
		String linea   = "";
		
		File archivo = null;
		
		/*
		 * Variables a reemplazar en la plantilla:
		 * ${usuario}  =>  Usuario Refistrado
		 * ${url}      =>  Enlace para validar la cuenta del usuario  
		 * 
		 * */
		try {		
			archivo = new File (TEST_EMAIL_TEMPLATE_REGISTRO);
			//fr = new FileReader(archivo);
			//br = new BufferedReader(fr);
			
			cuerpo = FileUtils.readFileToString(archivo, "UTF-8");
			//while( (linea=br.readLine()) != null){
			//	cuerpo += linea;
			//}
			
			cuerpo = cuerpo.replace("{usuario}", usuario); //Los {} pueden ser $ &, cualquier símbolo
			cuerpo = cuerpo.replace("{url}", url);
			
			EnviarEmails correo = new EnviarEmails();
			
			correo.setDireccionFrom("skalada.ipartek@gmail.com");
			correo.setDireccionDestino("unaiperea@gmail.com");
			correo.setMessageSubject("Confirmar registro usuario");
			correo.setMessageContent( cuerpo );
			
			assertTrue(
					 "Email no enviado " + correo.toString() ,
					  correo.enviar()
				);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
			
			
			
			
}