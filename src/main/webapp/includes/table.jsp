<%-- Include para a tabela exibida nas páginas do totens. --%>
<%-- Como todos funcionam da mesma forma, recebendo apenas uma lista diferente de dados,
foi decidido criar o include, para facilitar o desenvolvimento e possível expansão. --%>
<%@page import="br.edu.ifsp.dsw1.model.entity.FlightData"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- Recuperação dos voos recebidos através da requisição. --%>
<%
var voos = (List<FlightData>)request.getAttribute("flights");
%>
<table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">Número do Voo</th>
      <th scope="col">Companhia</th>
      <th scope="col">Horário de Chegada</th>
    </tr>
  </thead>
  <tbody class="table-group-divider">
  <%-- Nas páginas dos totens, foi decidido exibir apenas número do voo, a companhia
  e o horário de chegada do voo. --%>
    <%
    if(!voos.isEmpty()){
    	for(var voo : voos){
    %>
    <tr>
      <th scope="row"><%=voo.getFlightNumber() %></th>
      <td><%=voo.getCompany() %></td>
      <td><%=voo.getTime() %></td>
    </tr>
    <%
    	}
    }else{
   	%>
    <tr>
    	<th colspan="3">Nenhum voo encontrado.</th>
    </tr>	
    <%}%>
  </tbody>
</table>