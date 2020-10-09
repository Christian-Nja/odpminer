package ficlit.unibo.odpminer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDFS;


class PatternModel {
	 private OntModel rdf;
	 private String modelURI;

	/**
	 * @param rdfURL
	 */
	public PatternModel(String rdfURL) {
		super();
		this.modelURI = rdfURL; // TODO: are they always coincide ? URL and URI of the pattern in the model
		this.rdf = ModelFactory.createOntologyModel();
		this.rdf.read(rdfURL);
		this.makeQueryBody(this.getLocalDefinedProperties());
	}
	
	private Set<ObjectProperty> getLocalDefinedProperties() {
		Set<ObjectProperty> properties = new HashSet<ObjectProperty>(); 
		ExtendedIterator<ObjectProperty> iter = this.rdf.listObjectProperties();
		if (iter.hasNext()) {
		    while (iter.hasNext()) {
		    	// check if the property it's defined inside of the pattern
		    	ObjectProperty p = iter.next();
		    	System.out.println(String.format("%s -> %s",p.toString(), this.rdf.isInBaseModel(p)));
		    	if (p.hasProperty(RDFS.isDefinedBy, this.rdf.getResource(this.modelURI)))
		    	{
		    		properties.add(p);
		    	}
		    }
		} else {
		    System.out.println("No triples were found in the database");
		}		
		return properties;
	}
	
	
	
	private void getImportedPatterns() {
		//this.rdf.
	}
	
	private void makeQueryBody(Set<ObjectProperty> localDefinedProperties) {
		
		Iterator<ObjectProperty> props = localDefinedProperties.iterator();
		PermutationsIterator variables = new PermutationsIterator(new Alphabet(IntStream.range(97, 104)).getAlphabet()); // 97 ASCII char for 'a'
		String query = "";

		while (props.hasNext()) {
			String s = variables.next();
			String o = variables.next();
			ObjectProperty p = props.next();
			String domainClass = p.getDomain().toString();
			String rangeClass = p.getRange().toString();
			String queryStatement = String.format("?%s <%s> ?%s .", s, p, o);
			query = String.format("%s \n %s", query, queryStatement);
			query = String.format("%s \n ?%s a <%s> . \n ?%s a <%s> .", query, s, domainClass, o, rangeClass);
		}
		System.out.println(query);		
	}
 }