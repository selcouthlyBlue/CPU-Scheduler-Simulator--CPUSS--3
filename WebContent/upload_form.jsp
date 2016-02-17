<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel='stylesheet' type='text/css' href='assets/css/bootstrap.min.css'>
<link rel='stylesheet' type='text/css' href='assets/css/pure.css'>
<link rel='stylesheet' type='text/css' href='assets/css/style.css'>
<link href="//yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css" rel="stylesheet" type="text/css">
<title>Processing Unit Scheduling Simulator for Computers</title>
</head>
<body>
	<div class="content">
		<h1 class="content-subhead">Processing Unit Scheduling Simulator for Computers</h1>
		<form method="POST" action="Scheduler" enctype="multipart/form-data">
		<div class="file-upload-container pure-input-1">
			<div class="file-upload-override-button left pure-button">
				<input class="file-upload-button" type="file" name="file" id="file-upload-button" required/>
				Choose test file
			</div>
			<div class="file-upload-filename left" id="file-upload-filename" contenteditable="false">No file selected</div>
		</div>
		<input class="pure-input-1-1" name="quantum" placeholder="quantum" type="text" min="1" required><br/><br/>
		<input class="pure-button custom-button" type="submit" value="Upload" name="upload" id="upload" />
	</form>
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
		<p>Average waiting time : ${result.getAverageWaitingTime()}</p>
		<p>Average turnaround time : ${result.getAverageTurnaroundTime()}</p>
		<c:forEach items="${result.getTimeline()}" var="process">
			<div class="process" style="width:${process.getLength()}em">P${process.getProcessId()}</div>
			<sub>${process.getEndTime()}</sub>
		</c:forEach>
	</c:forEach>
	</div>
</body>
<script type="text/javascript" charset="utf8" src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" charset="utf8" src="assets/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" charset="utf8" src="assets/js/script.js"></script>
</html>