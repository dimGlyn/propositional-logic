import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;


public class ForwardChain {
	
	static File file;
	static BufferedReader reader;
	
	
	public static void main(String[] args) {
//		ForwardChain fc = new ForwardChain();
//		
//		Literal q = new Literal("Q",false);
//		DefClauseKB KB = new DefClauseKB();
//		
//		// add a clause to kb
//		DefiniteClause c1 = new DefiniteClause(new Literal("Q", false));
//		c1.getPremises().add(new Literal("P", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("P", false));
//		c1.getPremises().add(new Literal("L", false));
//		c1.getPremises().add(new Literal("M", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("M", false));
//		c1.getPremises().add(new Literal("B", false));
//		c1.getPremises().add(new Literal("L", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("L", false));
//		c1.getPremises().add(new Literal("A", false));
//		c1.getPremises().add(new Literal("P", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("L", false));
//		c1.getPremises().add(new Literal("A", false));
//		c1.getPremises().add(new Literal("B", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("A", false));
//		
//		KB.getDefClauses().add(c1);
//
//		// add a clause to kb
//		c1 = new DefiniteClause(new Literal("B", false));
//		
//		KB.getDefClauses().add(c1);
//		
//		KB.print();
//		System.out.print("We want to prove: ");
//		q.print();
//		System.out.println("\n");
//		
//		boolean entails = fc.PlFcEntails(KB, q);
//		
//		if(entails) {
//			System.out.print("Literal "); 
//		    q.print(); 
//		    System.out.print(" has been proven to be true.");
//		} else {
//			System.out.print("Couldn't infer ");
//		    q.print(); 
//		    System.out.print(" from the given knowledge base.");
//		}
		ForwardChain fc = new ForwardChain();
		
		DefClauseKB KB = readFile("src\\defClauses.txt");
		
		KB.print();
		
		System.out.println("Please give the literal to prove: ");
		Scanner input = new Scanner(System.in);

		Literal l = new Literal(input.nextLine(),false);

		input.close();
		
		
		
		
		boolean entails = fc.PlFcEntails(KB, l);
		
		if(entails) {
		System.out.print("Literal "); 
	    l.print(); 
		    System.out.print(" has been proven to be true.");
		} else {
			System.out.print("Couldn't infer ");
		    l.print(); 
		    System.out.print(" from the given knowledge base.");
		}
		
		
	}
	
	
	private static DefClauseKB readFile(String filepath){

		try{
			file = new File(filepath);
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String line = reader.readLine();

			return parse(line);

		} catch (IOException e) {
		}


		return null;
	}

	public static DefClauseKB parse(String s){
		DefClauseKB kb = new DefClauseKB();
		DefiniteClause defcl;

		String subclauses[] = s.split(",+(?![^\\(]*\\))", -1);

		for (String c:subclauses){
			c = c.replaceAll("[()]", "");
			String tokens [] = c.split("=>");
			if (tokens.length==1) {
				defcl = new DefiniteClause(new Literal(tokens[0],false));
			}else{
				defcl = new DefiniteClause(new Literal(tokens[1],false));
				String literals []= tokens[0].split(",");
				for(String x:literals){
					defcl.getPremises().add(new Literal(x,false));
				}
			}
			kb.getDefClauses().add(defcl);

		}

		return kb;
	}
	
	
	
	public Stack<Literal> initAgenda(DefClauseKB KB) {
		
		Stack<Literal> agenda = new Stack<Literal>();
		
		Iterator<DefiniteClause> iter = KB.getIter();
		
		
		while(iter.hasNext()) {
			DefiniteClause next = iter.next();
			next.initCount(); // initialize the count 
			if(next.isFact()) {
				agenda.push(next.getHead());
			}
			
		}
		
		return agenda;
		
	}
	
	/**
	 * propositional logic - forward chaining
	 * @return true if the knowledge base entails the literal q 
	 * false otherwise
	 */
	public boolean PlFcEntails(DefClauseKB KB, Literal q) {
		// initialize the agenda with the symbols that are known to be true
		Stack<Literal> agenda = initAgenda(KB);
		
		
		// if the literal to prove is already in the agenda
		if(agenda.contains(q)) {
			System.out.println("The given statement is a fact of the knowledge base.");
			return true;
		}
		
		while(!agenda.isEmpty()) {
			
			
			Literal p = agenda.pop();
			
			if(!p.isInferred()) { // if p is not inferred yet
				p.setInferred();
				
				// for each Horn clause in KB
				Iterator<DefiniteClause> iter = KB.getIter();
				while(iter.hasNext()) {
					
					
					DefiniteClause c = iter.next();
					
					if(c.getPremises().contains(p)) { // in whose p appears
						
						c.decrCount();
														
						if(c.getCount() == 0) { // rule trigger 
							// print
							System.out.println("Rule triggered: ");
							c.print();
							
							//print 
							System.out.print("\nNew fact: ");
							c.getHead().print();
							System.out.println();
							
							
							if(c.getHead().equals(q))  
								return true;
							
							agenda.push(c.getHead());
							
						}
						
						
					}
					
					
					
				}
				
				
			}
		
		
		}
		
		return false;
	}
	
	

}
