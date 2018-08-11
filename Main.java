import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;


public class Main 
{ 
	static File file;
	static BufferedReader reader;
	
	
    public static void main(String[] args) 
    {
    	 String path = "src\\cnfclauses.txt";
         CNFClause KB = readFile(path);
         
         System.out.println("Knowledge base: ");
         KB.print();
         System.out.println();
         
    	 System.out.println("Please give the negation of the statement to prove in CNF: ");
    	 Scanner input = new Scanner(System.in);
         String cnf = input.next();
         
         input.close();
         
        
         CNFClause a = stringToCNF(cnf);
         

         
         
    	 boolean b = PL_Resolution(KB, a);
    	
    
    	 if(b)
    		 System.out.println("Statement proved.");
    	 else 
    		 System.out.println("Couldn't infer the negation of the given statement from the knowledge base.");
    }
    
    private static CNFClause readFile(String filepath){

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
            
            return stringToCNF(line.replaceAll("\\s+","")); // remove whitespace

        } catch (IOException e) {
        }


        return null;
    }

    public static CNFClause stringToCNF(String x){

        CNFClause clause = new CNFClause();
        CNFSubClause subCl;

        String subclauses[] = x.split(",+(?![^\\(]*\\))", -1);

        for (String c:subclauses){
            c = c.replaceAll("[()]", "");
            String tokens [] = c.split(",");

            subCl = new CNFSubClause();

            Literal l;

            for (String s:tokens){
                if (s.startsWith("NOT_")){
                    l = new Literal(s.replace("NOT_",""),true);
                }else
                    l = new Literal(s,false);

                subCl.getLiterals().add(l);
            }

            clause.theClauses.add(subCl);
        }

        return clause;
    }
    
    
    

    //The resolution algorithm
    // a is the negation of the statement we want to prove in cnf
    public static boolean PL_Resolution(CNFClause KB, CNFClause a)
    {
        //We create a CNFClause that contains all the clauses of our Knowledge Base
        CNFClause clauses = new CNFClause();
        clauses.getSubclauses().addAll(KB.getSubclauses());
        
        //a is the negation of the type we want to prove in cnf
       // add a to the knowledge base 
        for(int i = 0; i < a.getSubclauses().size(); i++) { // for each subclause of the given statement a
        	//a new subclause
	        CNFSubClause aClause = new CNFSubClause();
	        Iterator<Literal> iter = a.getSubclauses().get(i).getLiteralsList();
	        
	        while(iter.hasNext()) {  // add a copy of the next literal in the subclause 
	        	Literal next = iter.next();
		        aClause.getLiterals().add(new Literal(next.getName(), next.getNeg()));
		       
		        
	        }
	        // add the new subclause to the knowledge base
	        clauses.getSubclauses().add(aClause);
	        
	        
        }
        
        
        System.out.println("We want to prove the negation of...");
        a.print();

        boolean stop = false;
        int step = 1;
        //We will try resolution till either we reach a contradiction or cannot produce any new clauses
        while(!stop)
        {
            Vector<CNFSubClause> newsubclauses = new Vector<CNFSubClause>();
            Vector<CNFSubClause> subclauses = clauses.getSubclauses();

            System.out.println("Step:" + step);
            step++;
            //For every pair of clauses Ci, Cj...
            for(int i = 0; i < subclauses.size(); i++)
            {
                CNFSubClause Ci = subclauses.get(i);

                for(int j = i+1; j < subclauses.size(); j++)
                {
                    CNFSubClause Cj = subclauses.get(j);

                    //...we try to apply resolution and we collect any new clauses
                    Vector<CNFSubClause> new_subclauses_for_ci_cj = CNFSubClause.resolution(Ci, Cj);

                    //We check the new subclauses...
                    for(int k = 0; k < new_subclauses_for_ci_cj.size(); k++)
                    {
                        CNFSubClause newsubclause = new_subclauses_for_ci_cj.get(k);

                        //...and if an empty subclause has been generated we have reached contradiction; and the literal has been proved
                        if(newsubclause.isEmpty()) 
                        {   
                            System.out.println("----------------------------------");
                            System.out.println("Resolution between");
                            Ci.print();
                            System.out.println("and");
                            Cj.print();
                            System.out.println("produced:");
                            System.out.println("Empty subclause!!!");
                            System.out.println("----------------------------------");
                            return true;
                        }
                        
                        //All clauses produced that don't exist already are added
                        if(!newsubclauses.contains(newsubclause) && !clauses.contains(newsubclause))
                        {
                            System.out.println("----------------------------------");
                            System.out.println("Resolution between");
                            Ci.print();
                            System.out.println("and");
                            Cj.print();
                            System.out.println("produced:");
                            newsubclause.print();
                            newsubclauses.add(newsubclause);
                            System.out.println("----------------------------------");
                        }
                    }                           
                }
            }
            
            boolean newClauseFound = false;

            //Check if any new clauses were produced in this loop
            for(int i = 0; i < newsubclauses.size(); i++)
            {
                if(!clauses.contains(newsubclauses.get(i)))
                {
                    clauses.getSubclauses().addAll(newsubclauses);                    
                    newClauseFound = true;
                }                        
            }

            //If not then Knowledge Base does not logically infer the literal we wanted to prove
            if(!newClauseFound)
            {
                System.out.println("Not found new clauses");
                stop = true;
            }   
        }
        return false;
    }    

}
