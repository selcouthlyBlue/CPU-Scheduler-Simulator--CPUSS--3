package cs125.cpuss.gonzalvo_marasigan.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cs125.cpuss.gonzalvo_marasigan.models.FCFS;
import cs125.cpuss.gonzalvo_marasigan.models.Process;
import cs125.cpuss.gonzalvo_marasigan.models.SJF_P;
import cs125.cpuss.gonzalvo_marasigan.models.SchedulingAlgorithm;
import cs125.cpuss.gonzalvo_marasigan.models.Upload;

public class SchedulerServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html;charset=UTF-8");
        final Part filePart = request.getPart("file");
        final PrintWriter writer = response.getWriter();
        Upload upload = new Upload(filePart, writer);
        upload.start();
        ArrayList<Process> processes = new ArrayList<Process>(upload.getProcesses());
        ArrayList<SchedulingAlgorithm> results = new ArrayList<SchedulingAlgorithm>();
        SchedulingAlgorithm fcfs = new FCFS(processes);
        fcfs.performScheduling();
        results.add(fcfs);
        SchedulingAlgorithm sjfp = new SJF_P(processes);
        sjfp.performScheduling();
        results.add(sjfp);
        request.setAttribute("results", results);
        RequestDispatcher view =
        		request.getRequestDispatcher("upload_form.jsp");
        view.forward(request, response);
	}
	
	
}