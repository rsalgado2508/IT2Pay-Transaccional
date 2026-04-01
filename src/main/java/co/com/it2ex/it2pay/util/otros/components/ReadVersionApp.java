package co.com.it2ex.it2pay.util.otros.components;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ReadVersionApp {
	
	private String versionFile;

	
	public String readVersion(String versionBD){
		InputStream inputStream;
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			versionBD = prop.getProperty("version");
 
		} catch (Exception e) {
			versionFile = "";
		}
		
		if(versionBD == null){
			versionBD = "0.0";
		}

		return versionBD;
	}

	public ReadVersionApp() {
		super();
	}

	public String getVersion() {
		return versionFile;
	}

	public void setVersion(String version) {
		this.versionFile = version;
	}
	

}