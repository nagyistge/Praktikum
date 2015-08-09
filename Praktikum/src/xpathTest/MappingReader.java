package xpathTest;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

public class MappingReader {

	private String cdaBasePath = "/";
	
	private List<Map<String,String>> mappings;
	

	public List<Map<String,String>> readMappings()  {
		
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		try {
			String f = readFile("mappings.json");
			
			JSONArray arr = new JSONArray(f);
			
			for(int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);

				String cdaPath = json.getString("cda");
				String fhirPath = json.getString("fhir");
				
				
				Map<String,String> mapping = new HashMap<String,String>();
				mapping.put("CDA_PATH", cdaPath);
				mapping.put("FHIR_PATH", fhirPath);
				
				result.add(mapping);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mappings = result;
		
		
		return result;
	}
	
	
	public void enumerateWithEnumerator(MappingEnumerator obj) {
		for (Map<String,String> map :mappings) {
			String cdaPath = map.get("CDA_PATH");
			cdaPath = cdaBasePath+cdaPath;
			
			String fhirPath = map.get("FHIR_PATH");
			
			obj.enumerate(cdaPath, fhirPath);
		}
	}
	
	
	
	
	private static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    reader.close();
	    
	    return stringBuilder.toString();
	}
	
}
