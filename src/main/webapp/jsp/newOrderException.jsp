<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Order</title>
</head>
<body>
	<h1>Error Creating the following Order</h1>
	<h2>Quantity too large: Product stock = ${exception.pStock}</h2>
	 <table border="1">
	   <tr>
	     <td>Product ID:</td>
	     <td>Customer ID:</td>
	     <td>Quantity:</td>
	   </tr>
	   <tr>
			<td>${exception.pId}</td>
			<td>${exception.cId}</td>
			<td>${exception.oQty}</td>
	   </tr>
	 </table>
	<a href="/index.html">Home</a>
    <a href="/showCustomers.html">List Customers</a>
    <a href="/showProducts.html">List Products</a>
</body>
</html>