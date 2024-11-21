<%@page import="br.edu.ifsp.dsw1.model.entity.FlightData"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
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
        <%
        for(var voo : voos){
        %>
        <tr>
          <th scope="row"><%=voo.getFlightNumber() %></th>
          <td><%=voo.getCompany() %></td>
          <td><%=voo.getTime() %></td>
          <td><%=voo.getState().getClass().getSimpleName() %></td>
          <td><a class="btn btn-primary" href="airport.do?action=update&number=<%=voo.getFlightNumber()%>">Atualizar</a></td>
        </tr>
        <%}%>
      </tbody>
    </table>
  </main>
  <%}%>
  <jsp:include page="/includes/script.html"/>
</body>
</html>