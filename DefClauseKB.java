import java.util.Iterator;
import java.util.Vector;

/*
 * a knowledge base that consists of definite clauses
 *
 */
public class DefClauseKB 
{
    public Vector<DefiniteClause> theClauses = new Vector<DefiniteClause>();
    
    public Vector<DefiniteClause> getDefClauses()
    {
        return theClauses;
    }
    
    public Iterator<DefiniteClause> getIter() {
    	return theClauses.iterator();
    }
    
    public void print() {
    	Iterator<DefiniteClause> iter = this.getIter();
    	System.out.println("Knowledge base: ");
    	while(iter.hasNext()) {
    		DefiniteClause next = iter.next();
    		
    		next.print();
    		System.out.println();
    		
    		
    
    	}
    	
    	
    	
    }
//    public boolean contains(CNFSubClause newS)
//    {
//        for(int i = 0; i < theClauses.size(); i ++)
//        {
//            if(theClauses.get(i).getLiterals().equals(newS.getLiterals()))
//            {
//                return true;
//            }
//        }
//        return false;
//    }
}
