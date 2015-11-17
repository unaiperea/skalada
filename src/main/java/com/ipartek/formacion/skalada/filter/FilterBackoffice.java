package com.ipartek.formacion.skalada.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;

/**
 * Servlet Filter implementation class FilterBackoffice
 */
public class FilterBackoffice implements Filter {

	private static final Logger LOG = Logger.getLogger(FilterBackoffice.class);
	private FilterConfig config;

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	//@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {

			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String url = httpRequest.getRequestURL().toString();
			LOG.trace(" Filtrando " + url);

			// comprobar si esta loegado
			HttpSession session = httpRequest.getSession(true);
			Usuario user = (Usuario) session
					.getAttribute(Constantes.KEY_SESSION_USER);

			if (user == null) {
				// Enviar al login usuario no logeado
				Mensaje msj = new Mensaje(Mensaje.MSG_WARNING,
						"No estas logeado, por favor inicia session");

				session.setAttribute("msg", msj);

				((HttpServletResponse) response)
				.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				((HttpServletResponse) response).sendRedirect(Constantes.SERVER
						+ Constantes.ROOT_APP + Constantes.VIEW_LOGIN);
			} else {
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	//@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}

	/**
	 * @see Filter#destroy()
	 */
	//@Override
	public void destroy() {
		this.config = null;
	}
}
