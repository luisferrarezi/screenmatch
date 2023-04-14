package br.com.screenmatch;

@SuppressWarnings("serial")
public class ErroConversaoAnoException extends RuntimeException {
	
	private String message;
	
	public ErroConversaoAnoException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
