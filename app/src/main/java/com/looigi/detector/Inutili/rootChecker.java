/* package com.looigi.spiatore;

import java.io.DataOutputStream;
import java.io.IOException;

public class rootChecker {
	
	public boolean ControllaRoot() {
		Process p;   
		boolean Ritorno;
		
		try {   
		   // Preform su to get root privledges  
		   p = Runtime.getRuntime().exec("su");   
		     
		   // Attempt to write a file to a root-only   
		   DataOutputStream os = new DataOutputStream(p.getOutputStream());   
		   os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n");  
		     
		   // Close the terminal  
		   os.writeBytes("exit\n");   
		   os.flush();   
		   try {   
		      p.waitFor();   
		           if (p.exitValue() != 255) {   
		              // TODO Code to run on success  
		        	   Ritorno=true;
		           }   
		           else {   
		               // TODO Code to run on unsuccessful  
		        	   Ritorno=false;
		           }   
		   } catch (InterruptedException e) {   
		      // TODO Code to run in interrupted exception  
        	   Ritorno=false;
		   }   
		} catch (IOException e) {   
		   // TODO Code to run in input/output exception  
     	   Ritorno=false;
		}  
		
		return Ritorno;
	}

	public void RinominaFilesRoot(String Primo, String Secondo) {
		Process p;   
		
		try {   
			p = Runtime.getRuntime().exec("su");   
			DataOutputStream out = new DataOutputStream(p.getOutputStream());
			out.writeBytes("mount -o remount,rw /system\n");
			out.writeBytes("mv "+Primo+" "+Secondo+"\n");
			out.writeBytes("exit\n");  
			out.flush();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		} catch (IOException e) {   
			e.printStackTrace();
		}  
	}
}
*/