package cs125.cpuss.gonzalvo_marasigan.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.Part;

public class Upload {
	
	private Part filePart;
	private PrintWriter writer;
	private String fileName;
	
	public Upload(Part filePart, PrintWriter writer){
		this.filePart = filePart;
		this.writer = writer;
	}
	
	public void start() throws IOException{
		this.fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            out = new FileOutputStream(new File(fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
	}
	
	public ArrayList<Process> getProcesses() throws NumberFormatException, IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(fileName));
		String line = null;
		String splitter = ",";
		ArrayList<Process> processes = new ArrayList<Process>();
		while((line = br.readLine()) != null){
			String[] row = line.split(splitter);
			Process process = new Process(Integer.parseInt(row[0]), 
					Integer.parseInt(row[1]), 
					Integer.parseInt(row[2]), 
					Integer.parseInt(row[3]));
			processes.add(process);
		}
		br.close();
		return processes;
	}
	
	private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
