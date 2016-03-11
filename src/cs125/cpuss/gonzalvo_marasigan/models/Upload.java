package cs125.cpuss.gonzalvo_marasigan.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
	public String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

	public String getFileName() {
		return fileName;
	}
}
