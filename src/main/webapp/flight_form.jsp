<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <%
  HttpSession sessao = request.getSession(false);
  if(sessao == null || sessao.getAttribute("user") == null){
   	response.sendRedirect("airport.do?action=illegalAccess");	
  }else{
  %>
  <main class="container-sm flex-grow-1  justify-content-center">
		<h1 style="text-align: center; margin: 30px;">Adicionar Voo</h1>
		<form class="bg-white p-4 rounded-3 shadow" action="airport.do?action=addFlight"
			method="post">
			<div class="mb-3">
				<label for="flightNumber" class="form-label">Número do Voo</label><input type="number"
					class="form-control" id="flightNumber" name="textFlightNumber"
					placeholder="Digite o Número do Voo." required="required">
			</div>
			<div class="mb-3">
				<label for="companhia" class="form-label">Senha</label> <input
					type="text" class="form-control" id="companhia" name="textCompanhia"
					placeholder="Digite a Companhia." required="required">
			</div>
      <div class="mb-3">
				<label for="chegada" class="form-label">Horário de Chegada</label> <input
					type="text" class="form-control" id="chegada" name="textChegada"
					placeholder="Digite o Horário de Chegada (por exemplo, 15:30)." required="required">
			</div>

			<button type="submit" class="btn btn-primary"
				style="text-align: center;">Cadastrar</button>
		</form>
	</main>
  <%}%>
  <jsp:include page="/includes/script.html"/>
</body>
</html>