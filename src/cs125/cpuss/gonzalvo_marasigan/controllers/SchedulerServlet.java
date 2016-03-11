package cs125.cpuss.gonzalvo_marasigan.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cs125.cpuss.gonzalvo_marasigan.models.Process;
import cs125.cpuss.gonzalvo_marasigan.models.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
// 100 MB
public class SchedulerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		final Part filePart = request.getPart("file");
		if (!filePart.equals(null)) {
			Upload upload = new Upload(filePart);
			upload.start();
			ArrayList<Process> processes = new ArrayList<Process>(ProcessSetReader.getProcesses(upload.getFileName()));
			ArrayList<SchedulingAlgorithm> results = new ArrayList<SchedulingAlgorithm>();
			SchedulingAlgorithmFactory factory = new SchedulingAlgorithmFactory();
			for (SchedulingAlgorithmName name : SchedulingAlgorithmName.values()) {
				SchedulingAlgorithm schedulingAlgorithm = factory.getSchedulingAlgorithm(name, processes);
				if (schedulingAlgorithm == null) {
					schedulingAlgorithm = factory.getSchedulingAlgorithm(name,
							processes,
							Integer.parseInt(request.getParameter("quantum")));
				}
				schedulingAlgorithm.performScheduling();
				results.add(schedulingAlgorithm);
			}
			request.setAttribute("SchedulingAlgorithm", results);
			request.setAttribute("Quantum", request.getParameter("quantum"));
			RequestDispatcher view = request.getRequestDispatcher("results.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}

	/**
	 * Outputs results of the scheduling procedure to a file.
	 * 
	 * @param schedulingAlgorithm
	 *            the algorithm used
	 * @param sOutputFile
	 *            the test file
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void generateResult(final SchedulingAlgorithm schedulingAlgorithm,
			final String sOutputFile) throws IOException {
		String outputFile = schedulingAlgorithm.getName() + "_" + sOutputFile;
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(outputFile, false));
		PrintWriter pw = new PrintWriter(bw);
		ArrayList<Process> results = schedulingAlgorithm.getResults();
		for (Process process : results) {
			StringBuilder sb = new StringBuilder();
			sb.append(process.getProcessId());
			sb.append(",");
			sb.append(process.getArrivalTime());
			sb.append(",");
			sb.append(process.getBurstTime());
			sb.append(",");
			sb.append(process.getPriority());
			sb.append(",");
			sb.append(process.getWaitingTime());
			sb.append(",");
			sb.append(process.getTurnaroundTime());
			pw.println(sb.toString());
		}
		pw.println(schedulingAlgorithm.getAverageWaitingTime());
		pw.println(schedulingAlgorithm.getAverageTurnaroundTime());
		bw.close();
	}
}
