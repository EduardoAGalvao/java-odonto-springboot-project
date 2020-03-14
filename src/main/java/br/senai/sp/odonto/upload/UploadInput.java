package br.senai.sp.odonto.upload;

public class UploadInput {
	
	//Armazena o nome do arquivo
	private String filename;
	
	//Armazena o tipo de arquivo que será enviado
	private String mimeType;
	
	//Armazena o arquivo em base 64, pois o HTML só irá transportar texto
	private String base64;
	
	public UploadInput() {
		
	}

	public UploadInput(String filename, String mimeType, String base64) {
		super();
		this.filename = filename;
		this.mimeType = mimeType;
		this.base64 = base64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	

}
