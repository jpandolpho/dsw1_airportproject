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
		}else{
			view = "index.jsp";
		}
		
		var dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
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
