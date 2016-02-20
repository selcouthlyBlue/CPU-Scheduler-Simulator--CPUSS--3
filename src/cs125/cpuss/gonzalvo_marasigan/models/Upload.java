package cs125.cpuss.gonzalvo_marasigan.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.Part;

public class Upload {
	
	private Part filePart;
	private String fileName;
	
	public Upload(Part filePart){
		this.filePart = filePart;
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
            fne.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
	}
	
	public ArrayList<Process> getProcesses() throws NumberFormatException, IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(fileName));
		String line = null;
		String splitter = ",";
		ArrayList<Process> processes = new ArrayList<Process>();
		int iProcessId = 0;
		while((line = br.readLine()) != null){
			iProcessId++;
			String[] row = line.split(splitter);
			Process process = new Process(iProcessId, 
						Integer.parseInt(row[0]), 
						Integer.parseInt(row[1]), 
						(row.length == 2 ? 0 : Integer.parseInt(row[2])));
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
