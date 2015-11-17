package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class HomeController
 * 
 * @author Curso
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpSession session = null;
	private ModeloSector modeloSector = null;
	private Usuario usuario = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;

	/**
	 * @param config
	 *          configuracion del servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloSector = new ModeloSector();
		this.modeloUsuario = new ModeloUsuario();
		this.modeloZona = new ModeloZona();
	}

	/**
	 * Se puentea al doPost()
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuperar las ultimas 6 sectores del modelo
		// TODO usar LIMIT en la select y order bu id desc
		this.usuario = (Usuario) this.modeloUsuario.getByEmail("admin@admin.com");
		this.session = request.getSession(true);
		this.session.setAttribute("admin", this.usuario);
		ArrayList<Zona> zonas = this.modeloZona.getAll(this.usuario);

		// enviarlas como atributo en la request
		request.setAttribute("todo_zonas", zonas);
		// ir a index
		request.getRequestDispatcher(Constantes.VIEW_PUBLIC_INDEX).forward(request,
				response);

	}
}
