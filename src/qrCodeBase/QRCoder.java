package qrCodeBase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.esponce.webservice.QRCodeClient;

public class QRCoder {
	private String defaultPathString = "1.png";
	private String defaultForeColorString = "black";
	
	public void generateQR(String _contentString){
		generateQR(_contentString, defaultPathString);
	}
	
	public void generateQR(String _contentString, String _filePathString){			
			try {
				QRCodeClient client = new QRCodeClient();
				BufferedInputStream ins = client.generate(_contentString, "png", 1, 10, 4, "byte", "m", defaultForeColorString, "white", null);
				//Save image to file
				FileOutputStream fos;
				fos = new FileOutputStream(_filePathString);
				BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

				int length = 0;
				byte[] data = new byte[1024];
				while ((length = ins.read(data, 0, 1024)) > 0)
				{
				  bos.write(data, 0, length);
				}

				bos.close();
				fos.close();
				ins.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}
	
	public String readQR(String _filePathString){
		String contentString = "";
		QRCodeClient client = new QRCodeClient();
		FileInputStream fis;
		try {
			fis = new FileInputStream(_filePathString);
			BufferedInputStream bis = new BufferedInputStream(fis);
			contentString = client.decode(bis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentString;
	}
}
