package br.senai.sp.odonto.upload;

public class UploadOutput {
	
	//Dados de retorno
	private String url;

	public UploadOutput() {
		
	}

	public UploadOutput(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
