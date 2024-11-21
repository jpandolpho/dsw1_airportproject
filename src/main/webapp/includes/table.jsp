<%@page import="br.edu.ifsp.dsw1.model.entity.FlightData"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    <%
    for(var voo : voos){
    %>
    <tr>
      <th scope="row"><%=voo.getFlightNumber() %></th>
      <td><%=voo.getCompany() %></td>
      <td><%=voo.getTime() %></td>
    </tr>
    <%}%>
  </tbody>
</table>