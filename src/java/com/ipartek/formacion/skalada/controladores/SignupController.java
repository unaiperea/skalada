package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloRol;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.EnviarEmails;

/**
 * Servlet implementation class RegistroController
 */
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;

	// Por Post
	private String pNombre;
	private String pEmail;
	private String pPass;

	// Valor por defecto de un nuevo usuario (Rol = Usuario)
	private int pIdRol = 2; // Deberíamos ponerlo en Constantes por si cambia un
							// día el id

	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloRol modeloRol = null;
	private Mensaje msg = null;

	// Por Get
	private Usuario usuarioParaValidar = null;
	private String pEmailParaValidar = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloUsuario = new ModeloUsuario();
		this.modeloRol = new ModeloRol();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			this.msg.setTexto("Error sin definir");
			this.msg.setTipo(Mensaje.MSG_DANGER);

			// Recibimos la validación
			this.pEmailParaValidar = request.getParameter("email");

			// Accedemos al usuario por el email. No comprobamos nada ya que el
			// email es único en la tabla usuarios
			this.usuarioParaValidar = this.modeloUsuario
					.getByEmail(this.pEmailParaValidar);
			if (this.usuarioParaValidar == null) {
				this.msg = new Mensaje(Mensaje.MSG_WARNING,
						"Ha habido un error al validar el usuario "
								+ this.pEmailParaValidar);
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				// Usuario encontrado
			} else {
				if (this.usuario.getValidado() == Constantes.USER_VALIDATE) {
					this.msg.setTexto("Ya estabas registrado");
					this.msg.setTipo(Mensaje.MSG_WARNING);
				} else {
					this.usuario.setValidado(Constantes.USER_VALIDATE);
					if (this.modeloUsuario.update(this.usuario)) {
						this.msg.setTexto("Eskerrik asko por registrarte");
						this.msg.setTipo(Mensaje.MSG_SUCCESS);
					}
				}
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
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
		try {
			request.setCharacterEncoding("UTF-8");

			this.pNombre = request.getParameter("nombre");
			this.pEmail = request.getParameter("email");
			this.pPass = request.getParameter("password");

			// Comprobamos si existe el usuario
			if (!this.modeloUsuario.checkUser(this.pNombre, this.pEmail)) {
				// Creamos el objeto Usuario
				this.rol = this.modeloRol.getById(this.pIdRol);
				this.usuario = new Usuario(this.pNombre, this.pEmail,
						this.pPass, this.rol);
				// Guardar en bbdd
				if (this.modeloUsuario.save(this.usuario) == -1) {
					this.msg = new Mensaje(Mensaje.MSG_WARNING,
							"Ha habido un error al guardar el usuario");
					this.dispatcher = request
							.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				} else {
					// Enviar email de validación
					if (this.enviarEmail()) {
						this.msg = new Mensaje(Mensaje.MSG_SUCCESS,
								"Por favor revisa tu email para validar tu registro");
					} else {
						this.msg = new Mensaje(Mensaje.MSG_DANGER,
								"Error al enviar email, por favor ponte en contacto con nosotros "
										+ EnviarEmails.DIRECCION_ORIGEN);
					}
					this.dispatcher = request
							.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			} else {
				request.setAttribute("msg", "El usuario o email ya existe");
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);

			}

		} catch (Exception e) {
			e.printStackTrace();
			this.msg = new Mensaje(Mensaje.MSG_WARNING,
					"Ha habido un error al guardar el usuario. "
							+ e.getMessage());
			request.setAttribute("msg", this.msg);
		} finally {
			request.setAttribute("msg", this.msg);
			this.dispatcher.forward(request, response);
		}
	}

	private boolean enviarEmail() {
		boolean resul = false;
		// url para validar el registro del usuario. Llamará a este mismo
		// controlador por GET pasando el email del usuario
		String url = Constantes.SERVER + Constantes.CONTROLLER_SIGNUP
				+ "?email=" + this.usuario.getEmail();
		String contenido = "Gracias por registrarte. Para activar el usuario y verificar el email, clica en el enlace de debajo.";
		String submitButtonText = "Activa tu cuenta y logeate";

		try {
			EnviarEmails correo = new EnviarEmails();

			// Parámetros para la plantilla
			HashMap<String, String> parametros = new HashMap<String, String>();
			parametros.put("{usuario}", this.usuario.getNombre());
			parametros.put("{url}", url);
			parametros.put("{contenido}", contenido);
			parametros.put("{btn_submit_text}", submitButtonText);

			// Configurar correo electrónico
			correo.setDireccionFrom("skalada.ipartek@gmail.com"); // Sin
																	// espacios
			correo.setDireccionDestino(this.usuario.getEmail()); // unaiperea@gmail.com
			correo.setMessageSubject("Confirmar registro usuario");
			correo.setMessageContent(correo.generarPlantilla(
					Constantes.EMAIL_TEMPLATE_REGISTRO, parametros)); // Le paso
																		// la
																		// ruta
																		// de la
																		// plantilla
																		// con
																		// formato
																		// HTML
																		// y un
																		// HashMap
																		// y me
																		// lo
																		// devuelve
																		// ya
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

			// Enviar correo correo electrónico
			resul = correo.enviar();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resul;
	}

}