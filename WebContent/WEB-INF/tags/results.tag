<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@tag description="Results Tag" pageEncoding="UTF-8"%>
<c:forEach items="${results}" var="result">
	<h2>${result.getName()}</h2>
	<table class="pure-table">
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
				<td>${process.getProcessId()}</td>
				<td>${process.getArrivalTime()}</td>
				<td>${process.getBurstTime()}</td>
				<td>${process.getPriority()}</td>
				<td>${process.getWaitingTime()}</td>
				<td>${process.getTurnaroundTime()}</td>
			</tr>
		</c:forEach>
	</table>
	<p>Average waiting time: ${result.getAverageWaitingTime()}</p>
	<p>Average turnaround time: ${result.getAverageTurnaroundTime()}</p>
	<c:if test="${result.getQuantum() != 0}">
		<p>Quantum: ${result.getQuantum()}</p>
	</c:if>
	<c:forEach items="${result.getTimeline()}" var="process">
		<div class="process"
			style="width:${process.getLength()/2}em; background-color: rgb(${process.getProcessId() + 100}, ${process.getProcessId() * 25}, ${process.getProcessId() * 12})">P${process.getProcessId()}</div>
		<sub>${process.getEndTime()}</sub>
	</c:forEach>
	<br />
	<br />
	<br />
	<br />
</c:forEach>