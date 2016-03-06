<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@tag description="Results Tag" pageEncoding="UTF-8"%>
<br/>
<p>Quantum used for Round Robin Scheduling: <c:out value="${Quantum}"/></p>
<c:forEach items="${SchedulingAlgorithm}" var="SchedulingAlgorithm">
	<h2>${SchedulingAlgorithm.getName()}</h2>
	<table class="pure-table">
		<tr>
			<th>Process ID</th>
			<th>Arrival Time</th>
			<th>Burst Time</th>
			<th>Priority</th>
			<th>Waiting Time</th>
			<th>Turnaround Time</th>
		</tr>
		<c:forEach items="${SchedulingAlgorithm.getResults()}" var="process">
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
	<p>Average waiting time: ${SchedulingAlgorithm.getAverageWaitingTime()}</p>
	<p>Average turnaround time: ${SchedulingAlgorithm.getAverageTurnaroundTime()}</p>
	<c:forEach items="${SchedulingAlgorithm.getProcessTimeline()}" var="process">
		<div class="process"
			style="width:${process.getLength()/2}em; background-color: rgb(${process.getProcessId() + 100}, ${process.getProcessId() * 25}, ${process.getProcessId() * 12})">P${process.getProcessId()}</div>
		<sub>${process.getEndTime()}</sub>
	</c:forEach>
	<br />
	<br />
	<br />
	<br />
</c:forEach>