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
  </main>
  <%}%>
  <jsp:include page="/includes/script.html"/>
</body>
</html>