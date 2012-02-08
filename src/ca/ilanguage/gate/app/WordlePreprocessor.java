package ca.ilanguage.gate.app;


import java.awt.event.ActionEvent;
import java.util.HashMap;
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



@CreoleResource(name = "Wordle Preprocessor", comment = "Preprocess text in any language to remove stopwords, mostly used to paste text into Wordle.net")
public class WordlePreprocessor extends AbstractLanguageAnalyser implements ActionsPublisher {
	private int totalTokens;
	private String tokenToCount;
	private String inputASName;
	
	private List<Action> actions;
	
	public Resource init() throws ResourceInstantiationException{
		System.out.print("Resource initialized");
		return this; 
	}
	public void execute() throws ExecutionException {
		System.out.print("Resource executing");
		
		AnnotationSet as = document.getAnnotations("Token");
		Map<String,Integer> types = new HashMap<String,Integer>();
		for (Annotation a : as){
			String type = a.getType();
			System.out.print("This is the type: "+a.getType());
			if (types.containsKey(type)){
				//increase the count
				
			}else{
				//insert
			}
		}
		
	    int tokens = document.getAnnotations().get("Token").size(); 
	    document.getFeatures().put("token_count", tokens);
	    totalTokens += tokens;
	    
	    System.out.print("Execution complete");
		
	}
	@RunTime
	@CreoleParameter(defaultValue="Token", comment="The name for the annotation to count.")
	public void setTokenToCount(String tokenType){
		tokenToCount = tokenType;
	}
	public String getTokenToCount(){
		return tokenToCount;
	}
	public String getInputASName(){
		return inputASName;
	}
	
	@RunTime
	@Optional
	@CreoleParameter(defaultValue="",comment="The annotation set which contains the annotation types to work on.")
	public void setInputASName(String inputAS){
		inputASName=inputAS;
	}
	@Override
	public List<Action> getActions() {
		if(actions == null){
			actions.add(new AbstractAction("Reset counter") {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
			actions.add(new AbstractAction("Show current total") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return actions;
	}
}
