<%-- Página para exibição dos voos com estado Boarding. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/includes/head.html"/>

<body>
  <jsp:include page="/includes/navbar.jsp"/>
  <main class="container-sm flex-grow-1  justify-content-center">
		<h1 style="text-align: center; margin: 30px;">Sala de Embarque</h1>
		<jsp:include page="/includes/table.jsp"/>
	</main>
  <jsp:include page="/includes/script.html"/>
</body>
</html>