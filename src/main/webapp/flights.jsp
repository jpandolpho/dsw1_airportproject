<%-- Página de controle do administrador. --%>
<%-- Nela são exibidos os voos existentes no sistema e um botão para adicionar novos voos. --%>
<%@page import="br.edu.ifsp.dsw1.model.entity.FlightData"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <%-- Verificação para checar se o usuário esta autenticado. Caso não esteja, ele é
  redirecionado para o action=notAuth, que o jogará de volta para a página de login. --%>
  <%
  HttpSession sessao = request.getSession(false);
  if(sessao == null || sessao.getAttribute("user") == null){
   	response.sendRedirect("airport.do?action=notAuth");	
  }else{
  %>
  <main class="container-sm flex-grow-1  justify-content-center">
    <div class="container-sm">
      <div class="row">
        <div class="col-8">
          <h1>Voos</h1>
        </div>
        <div class="col">
          <a class="btn btn-primary" href="airport.do?action=newFlight">Cadastrar Voo</a>
        </div>
      </div>
    </div>
    <%-- Existe a possibilidade de uma mensagem ser exibida na página. --%>
	<%-- Os dois casos são quando o usuário insere um novo voo no sistema, e quando ele
	atualiza o estado de algum voo. Cada caso tem sua própria mensagem.--%>
    <%
		String msg = (String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<%=msg %>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%
		}
		//Recuperação dos dados dos voos recebidos através da requisição.
		var voos = (List<FlightData>)request.getAttribute("flights");
		%>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">Número do Voo</th>
          <th scope="col">Companhia</th>
          <th scope="col">Horário de Chegada</th>
          <th scope="col">Estado</th>
          <th scope="col">Atualizar</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
      <%-- Para o administrador, são exibidas todas as informações do voo, além de um
      botão para atualização do estado do voo. --%>
      <%-- Caso a lista de voo recebida através da requisição esteja vazia, é exibida uma
      mensagem informando que nenhum voo foi encontrado. --%>
        <%
        if(!voos.isEmpty()){
        	for(var voo : voos){
        %>
        <tr>
          <th scope="row"><%=voo.getFlightNumber() %></th>
          <td><%=voo.getCompany() %></td>
          <td><%=voo.getTime() %></td>
          <td><%=voo.getState().getClass().getSimpleName() %></td>
          <%-- O número do voo a ser atualizado é passado como parametro através do link,
          junto com o action=update. --%>
          <td><a class="btn btn-primary" href="airport.do?action=update&number=<%=voo.getFlightNumber()%>">Atualizar</a></td>
        </tr>
        <%
        	}
        }else{
        %>
		<tr>
	    	<th colspan="5">Nenhum voo encontrado.</th>
	    </tr>  
  		<%}%>
      </tbody>
    </table>
  </main>
  <%}%>
  <jsp:include page="/includes/script.html"/>
</body>
</html>