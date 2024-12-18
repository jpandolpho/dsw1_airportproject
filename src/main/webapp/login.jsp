<%-- Página de login do administrador. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <main class="container-sm flex-grow-1  justify-content-center">
		<h1 style="text-align: center; margin: 30px;">Login</h1>
		<%-- Existe a possibilidade de uma mensagem ser exibida na página de login. --%>
		<%-- Os dois casos são quando o usuário insere informações incorretas para o login,
		e quando o usuário não autenticado tenta acessar páginas que apenas o administrador 
		consegue acessar. Cada caso tem uma mensagem própria.--%>
		<%
		String msg = (String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			<%=msg %>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%}%>
		<%-- Formulário para a envio de dados de login, com destino action=auth (authenticate). --%>
		<form class="bg-white p-4 rounded-3 shadow" action="airport.do?action=auth"
			method="post">
			<div class="mb-3">
				<label for="user" class="form-label">Usuário</label> <input type="text"
					class="form-control" id="user" name="textUser"
					placeholder="Digite o usuário." required="required">
			</div>
			<div class="mb-3">
				<label for="senha" class="form-label">Senha</label> <input
					type="password" class="form-control" id="senha" name="textSenha"
					placeholder="Digite a Senha." required="required">
			</div>

			<button type="submit" class="btn btn-primary"
				style="text-align: center;">Login</button>
		</form>
	</main>
  <jsp:include page="/includes/script.html"/>
</body>
</html>