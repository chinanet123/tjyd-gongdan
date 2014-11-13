<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta charset="utf8">
		<script type="text/javascript">

		</script>
	</head>
	<body>
		<form method="POST" action="<c:url value='/receiveTbTicket.do'/>">
			<textarea cols="50" rows="30" name="xml"></textarea>
			<input type="submit" value="提交"/>
		</form>
	</body>
</html>