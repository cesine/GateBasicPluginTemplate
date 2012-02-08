package ca.ilanguage.gate.app;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;

import gate.*;
import gate.creole.*;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.gui.*;
import gate.util.GateRuntimeException;

@CreoleResource(name = "Wordle Preprocessor", comment = "Preprocess text in any language to remove stopwords, mostly used to paste text into Wordle.net")
public class WordlePreprocessor extends AbstractLanguageAnalyser implements
		ProcessingResource {
	private int totalTokens;
	private String tokenToCount;
	private String inputASName;

	private List<Action> actions;

	public Resource init() throws ResourceInstantiationException {
		System.out.print("Resource initialized");
		fireStatusChanged("Creating a stemmer");
		fireProgressChanged(0);
		/*
		 * Try to prepare other resources jars etc 
		 */
		fireProgressChanged(100);
	    fireProcessFinished();
		return this;
	}

	public void execute() throws ExecutionException {
		System.out.print("Resource executing");
		super.interrupted = false;
		if (super.document == null)
			throw new GateRuntimeException("No document to process!");
		
		fireProgressChanged(0);
	    fireStatusChanged("Preprocessing " + document.getName() + "...");
		if (annotationSetName != null && annotationSetName.equals(""))
			annotationSetName = null;
		AnnotationSet inputAS = (annotationSetName == null || annotationSetName
				.trim().length() == 0) ? document.getAnnotations() : document
				.getAnnotations(annotationSetName);
		AnnotationSet tokensAS = inputAS.get(annotationType);
		if (tokensAS == null) {
			throw new GateRuntimeException(
					"No annotations to process!\n"
					+ "Please run a Tokeniser first, if using default features!");
		}

		Iterator<Annotation> iter = tokensAS.iterator();
	    int allTokens = tokensAS.size();
	    int processedTokens = 0;
	    int lastReport = 0;
	    while(iter.hasNext()){
	        if(isInterrupted()){
	          throw new ExecutionInterruptedException(String
	                  .valueOf(String.valueOf((new StringBuffer(
	                          "The execution of the \"")).append(getName()).append(
	                          "\" stemmer has been abruptly interrupted!"))));
	        }
	        Annotation token = (Annotation)iter.next();
	        FeatureMap allFeatures = token.getFeatures();
	        String tokenString = (String)allFeatures.get(annotationFeature);
	        System.out.println(tokenString);
	        /*
	         * TODO process token string
	         */
	        
	        
	        allFeatures.put("placeholder", "");
	        if(++processedTokens - lastReport > 100) {
	          lastReport = processedTokens;
	          fireProgressChanged((processedTokens * 100) / allTokens);
	        }
	    }
	    fireProcessFinished();
	  
		System.out.print("Execution complete");

	}

	

	public String getInputASName() {
		return inputASName;
	}

	@RunTime
	@Optional
	@CreoleParameter(defaultValue = "", comment = "The annotation set which contains the annotation types to work on.")
	public void setInputASName(String inputAS) {
		inputASName = inputAS;
	}

	private String language;

	private String annotationSetName;

	private String annotationType;

	private String annotationFeature;
	
	private double functionalVery = 0.01;
	private String functionalVeryLabel = "FunctionalVery";
	@RunTime
	@Optional
	@CreoleParameter(defaultValue = ".01", comment = "The precentile to cut off for very functional words (commonly refered to as Stop Words).")
	public void setfunctionalVery(String functVery) {
		this.functionalVery = Double.valueOf(functVery.trim()).doubleValue();
	}
	public double getFunctionalVery(){
		return functionalVery;
	}

	private double functional = 5;
	private String functionalLabel = "Functional";
	public double getFunctional(){
		return functional;
	}

	private double functionalMaybe = 3;
	private String functionalMaybeLabel = "FunctionalMaybe";
	public double getfunctionalMaybe(){
		return functional;
	}

	private double frequent = 1;
	private String frequentLabel = "Frequent";
	public double getFrequent(){
		return frequent;
	}

	//else content 
	private String contentLabel = "Content";
	
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setAnnotationSetName(String annotationSetName) {
		this.annotationSetName = annotationSetName;
	}

	public String getAnnotationSetName() {
		return annotationSetName;
	}

	public void setAnnotationType(String annotationType) {
		this.annotationType = annotationType;
	}

	public String getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationFeature(String annotationFeature) {
		this.annotationFeature = annotationFeature;
	}

	public String getAnnotationFeature() {
		return annotationFeature;
	}
}
