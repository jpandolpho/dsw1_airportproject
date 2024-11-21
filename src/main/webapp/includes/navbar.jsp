<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <nav class="navbar navbar-expand-lg bg-secondary-subtle">
    <div class="container-fluid">
      <a class="navbar-brand" href="index.jsp">Airport DSW1</a>
      <button class="navbar-toggler" type="button"
        data-bs-toggle="collapse" data-bs-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><a class="nav-link" href="airport.do?action=flights">
              Administração</a></li>
          <li class="nav-item"><a class="nav-link" href="#"> Sala
              de Embarque</a></li>
          <li class="nav-item"><a class="nav-link" href="#"> Sala
              de Desembarque</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Embarcando</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Decolando</a></li>
        </ul>
        <ul class="nav">
        <%
        HttpSession sessao = request.getSession(false);
        if(sessao == null || sessao.getAttribute("user") == null){
        %>
          <li class="nav-item"><a href="airport.do?action=login" class="nav-link link-body-emphasis px-2">Login</a></li>
        <%
        }else{
        %>
       	  <li class="nav-item"><a href="#" class="nav-link link-body-emphasis px-2">Logout</a></li>
      	<%}%>
        </ul>
      </div>
    </div>
  </nav>