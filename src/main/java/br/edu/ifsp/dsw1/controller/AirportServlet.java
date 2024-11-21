//Servlet da aplicação, utilizando o padrão Front-Controller.
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
	//As listas de armazenamento de dados são declaradas 
	//como atributos do servlet.
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
    	//Como os totens são observadores, é preciso registrar eles.
    	flights.register(arriving);
    	flights.register(boarding);
    	flights.register(takingOff);
    	flights.register(tookOff);
    }

    //Ambos métodos doGet e doPost, no front-controller, redirecionam
    //para a mesma função, que controla todas as requisições.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Obtendo valor do parametro action, que irá controlar para onde esta requisição vai.
		String action = request.getParameter("action");
		String view;
		
		//Ifs encadeados para controle da requisição.
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
		}else if("totem".equals(action)) {
			view = handleTotem(request,response);
		}else{
			//caso o usuario insira uma action não existente,
			//ele é encaminhado para a index.
			view = "index.jsp";
		}
		
		//redirecionando o usuário para o local apropriado.
		var dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	//Handler para lidar com os totens. Todos os totens chegam
	//nesta função, para então serem tratados apropriadamente
	//na função fetchTotemFlights.
	private String handleTotem(HttpServletRequest request, HttpServletResponse response) {
		return fetchTotemFlights(request,response);
	}

	//Dado o totem que iniciou a requisição, é recuperada a lista
	//de voos apropriada. O paramentro target chega pela requisição.
	//Este método foi criado após a identificação que todos os totens
	//agiriam de forma semelhante, sempre recuperando sua lista e então
	//retornando o destino.
	private String fetchTotemFlights(HttpServletRequest request, HttpServletResponse response) {
		String target = request.getParameter("target");
		//É recuperada a lista apropriada de voos para o totem.
		List<FlightData> voos;
		switch(target) {
			case "arriving":
				voos = arriving.getAllFlights();
				break;
			case "boarding":
				voos = boarding.getAllFlights();
				break;
			case "takingOff":
				voos = takingOff.getAllFlights();
				break;
			case "tookOff":
				voos = tookOff.getAllFlights();
				break;
			default:
				voos = null;
				break;
		}
		//A lista é então adicionada como atributo da requisição.
		request.setAttribute("flights", voos);
		//É adicionado a extensão ".jsp" ao target, criando assim
		//o link apropriado.
		target+=".jsp";
		return target;
	}

	//Handler para o action de update. Ele recebe o número do voo por
	//parametro, e atualiza o voo. 
	private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
		var numero = Long.parseLong(request.getParameter("number"));
		flights.updateFlight(numero);
		//É criada uma mensagem informando o usuário que o voo foi atualizado.
		setMessageRequest(request, response, 3);
		//O retorno da função é feito através da função fetchAllFlights,
		//que retorna "flights.jsp".
		return fetchAllFlights(request, response);
	}

	//Handler para o action de cadastrar um novo voo. Ele recebe por parametro
	//as informações do formulário de cadastro e cria um novo voo, que então é
	//adicionado a lista de voos.
	private String handleAddFlight(HttpServletRequest request, HttpServletResponse response) {
		var numero = Long.parseLong(request.getParameter("textFlightNumber"));
		var name = request.getParameter("textCompanhia");
		var chegada = request.getParameter("textChegada");
		
		FlightData flight = new FlightData(numero,name,chegada);
		//O construtor da classe não cria o objeto com um estado inicial,
		//portanto é necessário sempre iniciar ele separadamente.
		flight.setState(Arriving.getIntance());
		
		//Ao inserir um voo na lista, ele notifica os observers que um novo
		//voo foi adicionado ao sistema.
		flights.insertFlight(flight);
		
		//É criada uma mensagem informando o usuário que o voo foi cadastrado.
		setMessageRequest(request, response, 2);
		
		//O formulário de cadastro te leva, após o processamento, de volta
		//para a página de administração, o que é feito através da fetchAllFlights.
		return fetchAllFlights(request, response);
	}

	//Handler para caso o usuário não autenticado tente acessar, por meio da url,
	//a página de cadastro de voos, o que não é permitido.
	//Assim, o usuário não autenticado, ao tentar realizar esta ação, é jogado
	//para a página de login.
	private String handleIllegal(HttpServletRequest request, HttpServletResponse response) {
		//É criada uma mensagem para o usuário o avisando que precisa de permissão
		//de admin para acessar aquela página.
		setMessageRequest(request,response,0);
		return "login.jsp";
	}

	//Handler para a action newFlight. Usado para redirecionar o usuário quando
	//ele aperta o botão de "Cadastrar Voo" na tela do administrador.
	private String handleNewFlight(HttpServletRequest request, HttpServletResponse response) {
		return "flight_form.jsp";
	}

	//Handler para a action logout. Invalida a sessão do usuário, deslogando ele como admin. 
	private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
		//É recuperada a sessão existente
		HttpSession sessao = request.getSession(false);
		//Que é então invalidada.
		sessao.invalidate();
		return "index.jsp";
	}

	//Handler para a action notAuth. Usada quando o usuário tenta fazer login,
	//mas coloca dados inválidos. Redireciona para a login.
	private String handleNotAuth(HttpServletRequest request, HttpServletResponse response) {
		//É criada uma mensagem informando o usuário que ele utilizou
		//credenciais inválidas.
		setMessageRequest(request,response,1);
		return "login.jsp";
	}

	//Handler para a action flights, que é o link "Administração" na navbar.
	//É feita uma análise para verificar se o usuário está autenticado, e
	//caso esteja, leva ele até a página de administração de fato. Caso não
	//esteja autenticado, leva ele até a página de login.
	private String handleFlights(HttpServletRequest request, HttpServletResponse response) {
		String target;
		HttpSession sessao = request.getSession(false);
		if(sessao == null || sessao.getAttribute("user") == null){
			//É criada uma mensagem para o usuário o avisando que precisa de permissão
			//de admin para acessar aquela página.
			setMessageRequest(request,response,0);
			target = "login.jsp";
		}else {
			//Se o usuário está autenticado, ele deve ser levado até a página de admin,
			//o que é feito com o auxílio do método fetchAllFlights.
			target = fetchAllFlights(request, response);
		}
		return target;
	}

	//Método auxiliar para criação de mensagens. Recebe por parametro request, response e um inteiro
	//que sinaliza qual mensagem deve ser criada.
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
		//É criado o atributo msg com a mensagem apropriada, que foi alocada dentro do switch.
		request.setAttribute("msg", message);
	}
	
	//Handler para a action auth (authenticate). Faz a autenticação do administrador do sistema.
	private String handleAuth(HttpServletRequest request, HttpServletResponse response) {
		String usuario = request.getParameter("textUser");
		String senha = request.getParameter("textSenha");
		//Caso a autenticação seja válida, cria uma nova sessão e adiciona o atributo user nela,
		//para identificar o usuário. Foi colocado um tempo de 5 minutos para invalidação do user.
		if(autenticar(usuario, senha)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", usuario);
			session.setMaxInactiveInterval(5 * 60);
		}
		//Leva o usuário para a página flights.jsp
		return fetchAllFlights(request, response);
	}

	//Método auxiliar que leva o usuário para a página flights.jsp.
	//Criada quando foi identificado que, sempre que o usuário acessasse a página flights,
	//seria necessário recuperar os dados de todos os voos cadastrados. Os dados são
	//encaminhados através da requisição.
	private String fetchAllFlights(HttpServletRequest request, HttpServletResponse response) {
		//Recupera os dados de todos os voos cadastrados no sistema
		List<FlightData> voos = flights.getAllFligthts();
		//e os adiciona a requisição.
		request.setAttribute("flights", voos);
		//é retornado então o endereço da página, necessário para a view
		//no método processRequest.
		return "flights.jsp";
	}

	//Método auxiliar para autenticar o admin.
	private boolean autenticar(String usuario, String senha) {
		return ("admin".equals(usuario) && "admin".equals(senha));
	}

	//Handler para a action login. Redireciona o usuário para a página
	//de login do sistema.
	private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
		return "login.jsp";
	}

}
