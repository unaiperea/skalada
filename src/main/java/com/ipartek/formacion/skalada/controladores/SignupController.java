package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.SendMail;

/**
 * Servlet implementation class RegistroController
 *
 * @author Curso
 */
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//LOGS
	private static final Logger LOG = Logger.getLogger(SignupController.class);

	private RequestDispatcher dispatcher = null;

	private int pAccion = -1;

	private String pNombre;
	private String pEmail;
	private String pToken;
	private String pPass;
	private int pId;

	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private Mensaje msg = null;

	private HashMap<String, Integer> map = null;

	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloUsuario = new ModeloUsuario();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			this.msg = null;
			this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
			this.getParameters(request, response);

			switch (this.pAccion) {
			case Constantes.ACCION_CONFIRM:
				this.confirmar(request, response);
				break;
			case Constantes.ACCION_MOSTRAR_REGENERAR_PASS:
				this.mostrarRecuperarPass(request, response);
				break;
			default:
				this.signup(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = new Mensaje(Mensaje.MSG_WARNING, "Ha habido un error desconocido.");
			LOG.error("Error Desconocido");
		} finally {
			request.setAttribute("msg", this.msg);
			this.dispatcher.forward(request, response);
		}
	}

	private void mostrarRecuperarPass(HttpServletRequest request, HttpServletResponse response) {
		this.usuario = this.modeloUsuario.getByEmail(this.pEmail);
		request.setAttribute("email", this.pEmail);
		request.setAttribute("token", this.usuario.getToken());
		LOG.info("Usuario: " + usuario.getNombre() + " - Solicita recuperacion de contraseña");
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_RECUPERAR_PASS);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
			String accion = request.getParameter("action");
			if (accion != null) {
				this.pAccion = Integer.parseInt(accion);
			}
			if ((request.getParameter("id") != null) && !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pId = Integer.parseInt(request.getParameter("id"));
			}
			this.pEmail = request.getParameter("email");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void confirmar(HttpServletRequest request,
			HttpServletResponse response) {

		this.map = this.modeloUsuario.checkEmail(this.pEmail);
		if (this.map.get("id") != null) {
			if (this.map.get("id") != this.pId) {
				this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al comprobar su correo electronico.");
				LOG.error("Usuario con id: " + map.get("id") + " - Error al comprobar correo electronico: " + pEmail);
				this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
			} else {
				if (this.map.get("validado") != 0) {
					this.msg = new Mensaje(Mensaje.MSG_DANGER, "Su usuario ya esta validado.");
					LOG.warn("Usuario con id: " + map.get("id") + " - Ya esta validado.");
					this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
				} else {
					if (this.modeloUsuario.validate(this.map.get("id"))) {
						this.msg = new Mensaje(Mensaje.MSG_SUCCESS, "Su cuenta ha sido activada. Bienvenid@!");
						LOG.info("Usuario con id: " + map.get("id") + " - Validado.");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					} else {
						this.msg = new Mensaje(Mensaje.MSG_WARNING, "Ha ocurrido un error al validar el usuario, contacte con el administrador de la pagina.");
						LOG.error("Usuario con id: " + map.get("id") + " - Error al validar");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					}
				}
			}
		} else {
			this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al comprobar su correo electronico.");
			LOG.error("Error al comprobar correo electronico: " + pEmail);
			this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
		}

	}

	private void signup(HttpServletRequest request, HttpServletResponse response) {
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			this.msg = null;
			request.setCharacterEncoding("UTF-8");
			this.pAccion = Integer.parseInt(request.getParameter("action"));
			switch (this.pAccion) {
			case Constantes.ACCION_REGISTRAR_USUARIO:
				this.pNombre = request.getParameter("nombre");
				this.pEmail = request.getParameter("email");
				this.pPass = request.getParameter("password");
				this.pId = -1;

				if (!this.modeloUsuario.checkUser(this.pNombre, this.pEmail)) { // Comprobamos
					// si existe el usuario Guardar usuario
					this.crearUsuario(); // Creamos el objeto Usuario
					if ((this.pId = this.modeloUsuario.save(this.usuario)) == -1) {
						this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha habido un error al guardar el usuario");
						LOG.error("Usuario: " + usuario.getNombre() + " - Error al registrar.");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
					} else {
						SendMail mail = new SendMail();
						mail.setDestinatario(this.usuario.getEmail());
						mail.setAsunto("Confirmacion de registro");
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("{usuario}", this.usuario.getNombre());
						params.put("{url}", Constantes.URL_VALIDATE + this.pId + "&email=" + this.pEmail);
						params.put("{contenido}", "Gracias por registrarte. Para activar el usuario y verificar el email, clica en el enlace de debajo.");
						params.put("{txt_btn}", "Activa tu cuenta y logeate");
						mail.setMensaje(mail.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, params));
						mail.enviarMail();
						this.msg = new Mensaje(Mensaje.MSG_SUCCESS, "Por favor revise su correo electronico para validar su usuario");
						LOG.info("Usuario: " + usuario.getNombre() + " - Registrado, enviado email de confirmación.");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					}
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER, "El usuario ya existe");
					this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
				}
				break;
			case Constantes.ACCION_PASS_OLVIDADO:
				this.pEmail = request.getParameter("email-olvidado");
				this.usuario = this.modeloUsuario.getByEmail(this.pEmail);
				if (this.usuario != null) {
					this.usuario.setToken();
					this.modeloUsuario.update(this.usuario);
					if (this.usuario.isValidado()) {
						SendMail mail = new SendMail();
						mail.setDestinatario(this.pEmail);
						mail.setAsunto("Recuperacion de password");
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("{usuario}", this.pEmail);
						params.put("{url}", Constantes.URL_PASS_OLVIDADO + this.pEmail + "&tkn=" + this.usuario.getToken());
						params.put("{contenido}", "Si has olvidado tu contraseña haz click en el enlace de debajo para cambiarla.");
						params.put("{txt_btn}", "Recupera tu contraseña");
						mail.setMensaje(mail.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, params));
						mail.enviarMail();
						this.msg = new Mensaje(Mensaje.MSG_INFO, "Se ha enviado un mensaje a su cuenta de correo electronico en el que puede recuperar su contraseña");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					} else {
						this.msg = new Mensaje(Mensaje.MSG_DANGER,"El usuario no esta validado");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					}
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER,"El usuario no existe");
					this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
				}
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_LOGIN);
				break;
			case Constantes.ACCION_REGENERAR_PASS:
				this.pEmail = request.getParameter("email");
				this.pToken = request.getParameter("token");
				this.pPass = request.getParameter("password");

				this.usuario = this.modeloUsuario.getByEmail(this.pEmail);
				if (this.usuario.getToken().equals(this.pToken)) {
					if (this.modeloUsuario.resetPass(this.pEmail, this.pPass)) {
						this.msg = new Mensaje(Mensaje.MSG_SUCCESS, "Se ha cambiado su contraseña");
					} else {
						this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al cambiar su contraseña, contacte con el administrador");
					}
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error.");
				}
				this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
				break;
			default:
				this.msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error desconocido");
				this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.msg = new Mensaje(Mensaje.MSG_WARNING,
					"Ha habido un error desconocido.");
		} finally {
			request.setAttribute("msg", this.msg);
			this.dispatcher.forward(request, response);
		}
	}

	/**
	 * Crear nuevo Usuario con rol "usuario"
	 */
	private void crearUsuario() {
		this.rol = new Rol("Usuario");
		this.rol.setId(2);
		this.usuario = new Usuario(this.pNombre, this.pEmail, this.pPass, this.rol);
	}

}