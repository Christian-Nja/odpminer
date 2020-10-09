package ficlit.unibo.odpminer;

import java.util.Iterator;

class PermutationsIterator implements Iterator<String> {
	private int count;
	private int i=0; 
	private String current;
	private PermutationsIterator suffix;
	private String theString;

	public PermutationsIterator(String s){
		if(s!=null && s.length()>0) init(s);
	}

	private void init(String s){
		theString=s;
		count=0;
		if(s.length()==1){
			current = s;
			return;
		}
		
		suffix = new PermutationsIterator(s.substring(1));
		current = suffix.next();
	}


	public boolean hasNext(){
		return (current!=null&&i<=current.length())||(suffix!=null&&suffix.hasNext());
	}

	public String next(){
		try{
			// if theString is just one char
			if(current==theString) {
				current = null;// we set current to null, so that hasNext() returns false
				return theString; // and we return theString as it's the only permutation we have
			}
			// otherwise, we get each permutation of theString.substring(1) via the suffix iterator  
			if(i>current.length()){
				current = suffix.next();
				i = 0;
			}
			// and we shift theString[0] from the first to the last position
			return current.substring(0, i) + theString.charAt(0) + current.substring(i);
		} finally {
			++count;
			++i;
		}
	
	}

	@Override
	public void remove() {
		
	}
}
