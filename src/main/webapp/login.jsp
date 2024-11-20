<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <main class="container-sm flex-grow-1  justify-content-center">
		<h1 style="text-align: center; margin: 30px;">Login</h1>
		<form class="bg-white p-4 rounded-3 shadow" action="#"
			method="post">
			<div class="mb-3">
				<label for="user" class="form-label">Usuário</label> <input type="text"
					class="form-control" id="user" name="textUser"
					placeholder="Digite o usuário.">
			</div>
			<div class="mb-3">
				<label for="senha" class="form-label">Senha</label> <input
					type="password" class="form-control" id="senha" name="textSenha"
					placeholder="Digite a Senha.">
			</div>

			<button type="submit" class="btn btn-primary"
				style="text-align: center;">Salvar Contato</button>
		</form>
	</main>
  <jsp:include page="/includes/script.html"/>
</body>
</html>