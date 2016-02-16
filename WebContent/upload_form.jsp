<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CPU Scheduler Simulator (CPUSS)</title>
</head>
<body>
	<form method="POST" action="Scheduler" enctype="multipart/form-data">
		Test file: <input type="file" name="file" id="file" required/>
		 <input type="submit" value="Upload" name="upload" id="upload" />
	</form>
	<c:forEach items="${results}" var="result">
		<h2>${result.getName()}</h2>
		<table>
			<tr>
				<th>Process ID</th>
				<th>Arrival Time</th>
				<th>Burst Time</th>
				<th>Priority</th>
				<th>Waiting Time</th>
				<th>Turnaround Time</th>
			</tr>
			<c:forEach items="${result.getProcesses()}" var="process">
				<tr>
					<td><c:out value="${process.getProcessId()}"></c:out></td>
					<td><c:out value="${process.getArrivalTime()}"></c:out></td>
					<td><c:out value="${process.getBurstTime()}"></c:out></td>
					<td><c:out value="${process.getPriority()}"></c:out></td>
					<td><c:out value="${process.getWaitingTime()}"></c:out></td>
					<td><c:out value="${process.getTurnaroundTime()}"></c:out></td>
				</tr>
			</c:forEach>
		</table>
		<p>Average waiting time : <c:out value="${result.getAverageWaitingTime()}"></c:out></p>
		<p>Average turnaround time : <c:out value="${result.getAverageTurnaroundTime()}"></c:out></p>
	</c:forEach>
</body>
</html>