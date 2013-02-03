package info.chanwook.helper;

import java.io.DataInputStream;
import java.io.FileInputStream;

import org.springframework.core.io.ClassPathResource;

public class FileUtils {

	public static String getContents(String filePath) {
		ClassPathResource r = new ClassPathResource(filePath);
		byte[] b = null;
		try {
			b = new byte[(int) r.contentLength()];
			new DataInputStream(new FileInputStream(r.getFile())).readFully(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(b);
	}
}
