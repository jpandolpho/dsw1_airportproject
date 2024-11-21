package br.edu.ifsp.dsw1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import br.edu.ifsp.dsw1.model.entity.FlightData;
import br.edu.ifsp.dsw1.model.entity.FlightDataCollection;
import br.edu.ifsp.dsw1.model.flightstates.Arriving;
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
    	flights.register(arriving);
    	flights.register(boarding);
    	flights.register(takingOff);
    	flights.register(tookOff);
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
		}else if("newFlight".equals(action)){
			view = handleNewFlight(request, response);
		}else if("illegalAccess".equals(action)){
			view = handleIllegal(request,response);
		}else if("addFlight".equals(action)){
			view = handleAddFlight(request,response);
		}else if("update".equals(action)){
			view = handleUpdate(request,response);
		}else{
			view = "index.jsp";
		}
		
		var dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
		var numero = Long.parseLong(request.getParameter("number"));
		flights.updateFlight(numero);
		setMessageRequest(request, response, 3);
		fetchAllFlights(request, response);
		return "flights.jsp";
	}

	private String handleAddFlight(HttpServletRequest request, HttpServletResponse response) {
		var numero = Long.parseLong(request.getParameter("textFlightNumber"));
		var name = request.getParameter("textCompanhia");
		var chegada = request.getParameter("textChegada");
		
		FlightData flight = new FlightData(numero,name,chegada);
		flight.setState(Arriving.getIntance());
		
		flights.insertFlight(flight);
		
		setMessageRequest(request, response, 2);
		fetchAllFlights(request,response);
		
		return "flights.jsp";
	}

	private String handleIllegal(HttpServletRequest request, HttpServletResponse response) {
		setMessageRequest(request,response,0);
		return "login.jsp";
	}

	private String handleNewFlight(HttpServletRequest request, HttpServletResponse response) {
		return "flight_form.jsp";
	}

	private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sessao = request.getSession();
		sessao.invalidate();
		return "index.jsp";
	}

	private String handleNotAuth(HttpServletRequest request, HttpServletResponse response) {
		setMessageRequest(request,response,1);
		return "login.jsp";
	}

	private String handleFlights(HttpServletRequest request, HttpServletResponse response) {
		String target;
		HttpSession sessao = request.getSession(false);
		if(sessao == null || sessao.getAttribute("user") == null){
			setMessageRequest(request,response,0);
			target = "login.jsp";
		}else {
			fetchAllFlights(request,response);
			target = "flights.jsp";
		}
		return target;
	}

	private void setMessageRequest(HttpServletRequest request, HttpServletResponse response, int i) {
		String message;
		switch(i) {
			case 0:
				message = "Faça login para acessar informações de administração.";
				break;
			case 1:
				message = "Login incorreto.";
				break;
			case 2:
				message = "Voo adicionado ao sistema.";
				break;
			case 3:
				message = "Voo atualizado.";
				break;
			default:
				message = null;
				break;
		}
		request.setAttribute("msg", message);
	}
	
	private String handleAuth(HttpServletRequest request, HttpServletResponse response) {
		String usuario = request.getParameter("textUser");
		String senha = request.getParameter("textSenha");
		if(autenticar(usuario, senha)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", usuario);
			session.setMaxInactiveInterval(5 * 60);
		}
		fetchAllFlights(request, response);
		return "flights.jsp";
	}

	private void fetchAllFlights(HttpServletRequest request, HttpServletResponse response) {
		List<FlightData> voos = flights.getAllFligthts();
		request.setAttribute("flights", voos);
	}

	private boolean autenticar(String usuario, String senha) {
		return ("admin".equals(usuario) && "admin".equals(senha));
	}

	private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
		return "login.jsp";
	}

}
