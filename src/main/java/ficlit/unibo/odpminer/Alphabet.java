package ficlit.unibo.odpminer;

import java.util.stream.IntStream; 

class Alphabet {
	
	private String alphabet;
	
	public Alphabet(IntStream asciiRange) {
		this.alphabet = "";
		asciiRange.forEach(n -> {
			this.alphabet = this.alphabet + Character.toString((char) n);
		});
	}
	public String getAlphabet() {
		return this.alphabet;
	}
}
