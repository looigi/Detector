/* package com.looigi.spiatore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestioneFilesCartelle {
	
	public void CreaCartelleApplicazione(String Percorso) {
		int pos=0;
		String AppPerc=Percorso;
		String Perc="";
		
		pos=AppPerc.indexOf("/");
		while (pos>-1) {
			Perc+="/"+AppPerc.substring(0,pos);
			Perc=Perc.replace("//", "/");
			CreaCartella(Perc);

			AppPerc=AppPerc.substring(pos+1,AppPerc.length());
			pos=AppPerc.indexOf("/");
		}
		Perc+="/"+AppPerc;
		CreaCartella(Perc);
	}
	
    public void CreaFile(String PercorsoDIR) {
    	String NomeFile=".nomedia";
    	
	    File gpxfile = new File(PercorsoDIR, NomeFile);
	    FileWriter writer;
		try {
			writer = new FileWriter(gpxfile);
	        writer.append("");
	        writer.flush();
	        writer.close();
	        
	        Runtime.getRuntime().exec("chmod 777 " + PercorsoDIR + NomeFile);	        
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    }
    	
	public void CreaCartella(String Percorso) {
		try {
			File dDirectory = new File(Percorso);
			dDirectory.mkdirs();
		} catch (Exception e) {
			
		}  
	}

	public void EliminaFile(String Imma) {
		try {
        	File file = new File(Imma);
        	boolean deleted = file.delete();
    	} catch (Exception e) {
    		
    	}
	}

	public void RinominaFile(String Percorso, String VecchioNome, String NuovoNome) {
		try {
			File from = new File(Percorso, VecchioNome);
			File to = new File(Percorso, NuovoNome);
			from.renameTo(to);
    	} catch (Exception e) {
    		
    	}
	}
	
	public void EliminaCartella(String Percorso) {
		File root = new File(Percorso);
		File [] files = root.listFiles();
		int i;
		
		String NomeFile="";
		if (files!=null) {
	        for (File file : files){
	        	if (!file.isDirectory()) {
	        		NomeFile=file.getPath().toString();
	        		for (i=NomeFile.length()-1;i>0;i--) {
	        			if (NomeFile.substring(i,i+1).equals("/")) {
	        				NomeFile=NomeFile.substring(i+1,NomeFile.length());
	        				break;
	        			}
	        		}
	        		if (!NomeFile.trim().equals("")) {
	        			EliminaFile(Percorso+NomeFile);
	        		}
	        	}
	        }
		}
		try {
			File dDirectory = new File(Percorso);
			dDirectory.delete();
		} catch (Exception ignored) {
			
		}  
	}

	public List<String> OrdinaListaFiles(List<String> df) {
    	List<String> dfO=new ArrayList<String>();
		dfO=df;
		String Appoggio="";
		Boolean bAppoggio;
		
		String Prima="";
		String Seconda="";
		for (int i=0;i<dfO.size();i++) {
			Prima=dfO.get(i).toUpperCase().trim();
			if (!Prima.isEmpty()) {
				if (Prima.substring(0,1).equals(".")) {
					Prima=Prima.substring(1,Prima.length());
				}
			}
			for (int k=0;k<dfO.size();k++) {
				Seconda=dfO.get(k).toUpperCase().trim();
				if (!Seconda.isEmpty()) {
					if (Seconda.substring(0,1).equals(".")) {
						Seconda=Seconda.substring(1,Seconda.length());
					}
				}
				if (i!=k) {
					if (Prima.compareTo(Seconda)<0) {
		    			Appoggio=dfO.get(i);
		    			dfO.set(i, dfO.get(k));
		    			dfO.set(k, Appoggio);
					}
				}
			}
		}
		
		return dfO;
	}
	
}
*/