<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <main class="container-sm flex-grow-1  justify-content-center">
		<%
		boolean msg = (boolean)request.getAttribute("msg");
		if(msg){
		%>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			Faça login para acessar informações de administração.
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%}%>
		<h1 style="text-align: center; margin: 30px;">Login</h1>
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