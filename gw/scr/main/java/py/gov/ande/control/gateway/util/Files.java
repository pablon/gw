package py.gov.ande.control.gateway.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Files {

	private BufferedWriter writer = null;
	private String file = "files/";
	
	public Files(){
        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog);

            // This will output the full path where the file will be written to...
            System.out.println(file+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file+logFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public Files(String name, String ext){
        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(name+"-"+timeLog+"."+ext);

            System.out.println(file+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file+logFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
	
	public Files(String name, String ext, String text){
        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(name+"-"+timeLog+"."+ext);

            System.out.println(file+logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file+logFile));
            writer.write(text+ "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}	
/*	public BufferedWriter createFile(){
	
		return writer;
	}
*/	
	public void closeFile() throws IOException{
		writer.close();
		System.out.println("File closed");
	}
	
	public Boolean writeLine(String string){
        
        try {
			writer.write(string+ "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
