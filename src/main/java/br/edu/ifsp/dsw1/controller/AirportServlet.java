package br.edu.ifsp.dsw1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import br.edu.ifsp.dsw1.model.entity.FlightDataCollection;
import br.edu.ifsp.dsw1.model.totens.ArrivingTotem;
import br.edu.ifsp.dsw1.model.totens.BoardingTotem;
import br.edu.ifsp.dsw1.model.totens.TakingOffTotem;
import br.edu.ifsp.dsw1.model.totens.TookOffTotem;

@WebServlet("/airport.do")
public class AirportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FlightDataCollection flights;
    private ArrivingTotem arriving;
    private BoardingTotem boarding;
    private TakingOffTotem takingOff;
    private TookOffTotem tookOff;
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	flights = new FlightDataCollection();
    	arriving = new ArrivingTotem();
    	boarding = new BoardingTotem();
    	takingOff = new TakingOffTotem();
    	tookOff = new TookOffTotem();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String view;
		
		if("login".equals(action)) {
			view = handleLogin(request, response);
		}else if("auth".equals(action)){
			view = handleAuth(request,response);
		}else if("flights".equals(action)){
			view = handleFlights(request, response);
		}else if("notAuth".equals(action)){
			view = handleNotAuth(request, response);
		}else if("logout".equals(action)){
			view = handleLogout(request, response);
		}else{
			view = "index.jsp";
		}
		
		var dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sessao = request.getSession();
		sessao.invalidate();
		return "index.jsp";
	}

	private String handleNotAuth(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "Login incorreto.");
		return "login.jsp";
	}

	private String handleFlights(HttpServletRequest request, HttpServletResponse response) {
		String target;
		HttpSession sessao = request.getSession(false);
		if(sessao == null || sessao.getAttribute("user") == null){
			request.setAttribute("msg", "Faça login para acessar informações de administração.");
			target = "login.jsp";
		}else {
			target = "flights.jsp";
		}
		return target;
	}

	private String handleAuth(HttpServletRequest request, HttpServletResponse response) {
		String usuario = request.getParameter("textUser");
		String senha = request.getParameter("textSenha");
		if(autenticar(usuario, senha)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", usuario);
			session.setMaxInactiveInterval(5 * 60);
		}
		return "flights.jsp";
	}

	private boolean autenticar(String usuario, String senha) {
		return ("admin".equals(usuario) && "admin".equals(senha));
	}

	private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
		return "login.jsp";
	}

}
