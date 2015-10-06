package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.EnviarEmails;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class ForgotPassController
 */
public class ForgotPassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_CADENA = 250;

	private RequestDispatcher dispatcher = null;

	String pEmail;
	String pPass;
	private String pToken;

	private Usuario usuario = null;
	private ModeloUsuario modeloUsuario = null;
	private Mensaje msg = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgotPassController() {
		super();
		this.modeloUsuario = new ModeloUsuario();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Recibo parámetros del mail para reenviarle al formulario de 'Cambio
		// de Pass'
		this.msg.setTipo(Mensaje.MSG_DANGER);
		this.msg.setTexto("Error sin definir");

		try {
			this.pEmail = request.getParameter("email-forget");

			// Accedo al usuario mediante el email recibido
			this.usuario = this.modeloUsuario.getByEmail(this.pEmail);
			if (this.usuario != null) {
				// Generar token para el usuario
				String token = Utilidades.getCadenaAlfanumAleatoria(MAX_CADENA);
				this.usuario.setToken(token);
				this.modeloUsuario.update(this.usuario);
				if (this.enviarEmail()) {
					// Le envío un email de validación para vaya al formulario
					// de cambiar la contraseña
					this.msg = new Mensaje(Mensaje.MSG_SUCCESS,
							"Por favor revisa tu Email para reestablecer la contraseña");
					this.dispatcher = request
							.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER,
							"Error al enviar email, por favor ponte en contacto con nosotros "
									+ EnviarEmails.DIRECCION_ORIGEN);
					this.dispatcher = request
							.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			} else { // Si no existe
				this.msg.setTexto("Email no registrado: " + this.pEmail);
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("msg", this.msg);
			this.dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Recibo la Pass del formulario recuperar.jsp y la cambio en la bbdd

		this.msg = new Mensaje(Mensaje.MSG_DANGER, "Error sin definir");

		try {
			this.pEmail = request.getParameter("email");
			this.pPass = request.getParameter("password");
			this.pToken = request.getParameter("token");

			// Accedo al usuario mediante el email recibido
			this.usuario = this.modeloUsuario.getByEmail(this.pEmail);

			if (this.usuario != null) {
				// Comprobar no se haya cambiado el email. Luego se podría coger
				// la IP del usuario y guardarla, etc, ...
				if (this.pToken.equals(this.usuario.getToken())) {
					// Guardo Pass y modifico en la bbdd
					this.usuario.setPassword(this.pPass);
					if (this.modeloUsuario.update(this.usuario)) {
						// Enviar email de validación de pass
						this.msg.setTexto("Contraseñas modificadas correctamente");
						this.msg.setTipo(Mensaje.MSG_SUCCESS);
					} else {
						// usuario no existe
						this.msg.setTexto("usuario no existe");
						this.msg.setTipo(Mensaje.MSG_WARNING);
					}
				}
			} else {
				// usuario no existe
				this.msg.setTexto("usuario no existe");
				this.msg.setTipo(Mensaje.MSG_WARNING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("msg", this.msg);
			this.dispatcher = request
					.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			this.dispatcher.forward(request, response);
		}

	}

	private boolean enviarEmail() {
		boolean resul = false;
		// url para validar el registro del usuario. Llamará a este mismo
		// controlador por GET pasando el email del usuario y el churro de token
		String url = Constantes.SERVER + Constantes.ROOT_BACK
				+ Constantes.VIEW_BACK_RECUPERAR_PASS + "?email="
				+ this.usuario.getEmail() + "&token=" + this.usuario.getToken();
		String contenido = "Has solicitado cambiar la contraseña, si no ha sido Ud por favor póngase en contacto con nosotros, clica en el enlace de debajo.";
		String submitButtonText = "Cambiar contraseña";

		try {
			EnviarEmails correo = new EnviarEmails();

			// Parámetros para la plantilla
			HashMap<String, String> parametros = new HashMap<String, String>(); // Creamos
																				// un
																				// hashmap
																				// para
																				// sustituir
																				// todos
																				// los
																				// campos
																				// que
																				// queramos.
																				// Un
																				// array
																				// de
																				// dos
																				// dimensiones
																				// para
																				// guardar
																				// todos
																				// los
																				// items
																				// de
																				// la
																				// p�gina
																				// con
																				// la
																				// key
																				// su
																				// valor

			parametros.put("{usuario}", this.usuario.getNombre());
			parametros.put("{url}", url);
			parametros.put("{contenido}", contenido);
			parametros.put("{btn_submit_text}", submitButtonText);

			// Configurar correo electrónico
			correo.setDireccionFrom("skalada.ipartek@gmail.com"); // Sin
																	// espacios
			correo.setDireccionDestino(this.usuario.getEmail()); // unaiperea@gmail.com
			correo.setMessageSubject("Confirmar registro usuario");
			correo.setMessageContent( // Para HTML y texto plano
			correo.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO,
					parametros)); // Le paso la ruta de la plantilla con formato
									// HTML y un HashMap y me lo devuelve ya
									// montado
			// correo.setMessageText(cuerpo); //Para texto plano

			/*
			 * O DE ESTA FORMA leyendo el fichero directamente desde una
			 * ubicación dentro de Webcontent archivo = new File
			 * (Constantes.EMAIL_TEMPLATE_REGISTRO); cuerpo =
			 * FileUtils.readFileToString(archivo, "UTF-8"); cuerpo =
			 * cuerpo.replace("{usuario}", usuario); //Los {} pueden ser $ &,
			 * cualquier símbolo cuerpo = cuerpo.replace("{url}", url);
			 */

			// Enviar correo correo electrónico. Va por GET
			resul = correo.enviar();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resul;
	}

}
