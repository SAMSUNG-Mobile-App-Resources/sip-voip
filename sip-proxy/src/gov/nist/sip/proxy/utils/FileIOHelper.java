package gov.nist.sip.proxy.utils;

import java.io.File;
import java.net.URL;

public class FileIOHelper {
	
	public File getFileInclasspath(String filename) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL fileUrl = classLoader.getResource(filename);
		File file = null;
		if (fileUrl!=null) {
			 file = new File(classLoader.getResource(filename).getFile());
		}
		return file;
	}

}
