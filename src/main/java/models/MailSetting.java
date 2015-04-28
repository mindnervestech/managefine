package models;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mnt.core.helper.SaveModel;



public class MailSetting {
	public String hostName;
	public String portNumber;
    public Boolean sslPort;
	
	public Boolean tlsPort;
	
	public String userName;
	public String password;
	
	public static MailSetting mailSetting;
	
	static {
		Properties prop = new Properties();
		String filename = "app.properties";
		InputStream input = SaveModel.class.getClassLoader().getResourceAsStream(filename);
		try {
			mailSetting = new MailSetting();
			prop.load(input);
			mailSetting.userName = prop.getProperty("mail.username");
			mailSetting.password = prop.getProperty("mail.password");
			mailSetting.hostName = prop.getProperty("mail.hostname");
			mailSetting.portNumber = prop.getProperty("mail.port");
			mailSetting.sslPort = Boolean.valueOf(prop.getProperty("mail.ssl"));
			mailSetting.tlsPort = Boolean.valueOf(prop.getProperty("mail.tls"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static MailSetting find() {
		
		return mailSetting;
	}
	
	
	
	

	public String getPortNumber() {
		return portNumber;
	}

	
	public Boolean getSslPort() {
		return sslPort;
	}

	
	public Boolean getTlsPort() {
		return tlsPort;
	}

	public String getUserName() {
		return userName;
	}

	
	public String getPassword() {
		return password;
	}

	

}
