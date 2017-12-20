<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<c:url value="/mensagem/enviarMensagem"/>" method="post">
		<input type="text" name="mensagem">
		<input type="text" name="nomeUser">
		<input type="text" name="horaMsg">
		<input type="text" name="idDisciplina">
		<button type="submit">Submit</button>
    </form>
</body>
</html>