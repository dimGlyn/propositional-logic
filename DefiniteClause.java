import java.util.HashSet;
import java.util.Iterator;

/**
 * propositional logic
 * A definite clause(special case of horn clause) consists of a list of
 *  premises(positive literals) and a head
 */
public class DefiniteClause {
	/** the premises of the definite clause*/
	private HashSet<Literal> premises;
	
	/** head of the definite clause */
	private Literal head;
	
	/** number of premises inferred by pl fc entails*/
	private int count;
	/**
	 * constructor
	 */
    public DefiniteClause()
    {
    	this.count = 0;
        premises = new HashSet<Literal>();
        
    }
    
    /**
	 * constructor
	 */
    public DefiniteClause(Literal head)
    {
    	this();
    	this.head = head;
    	
    }
         
    public  HashSet<Literal> getPremises()            
    {
        return premises;
    }
    
    public Iterator<Literal> getPremisesList()
    {
        return premises.iterator();
    }
         
   
    
    public int getCount() {
    	return this.count;
    }
    
    public void initCount() {
    	this.count = this.premises.size();
    
    }
    
	public void decrCount() {
		this.count--;
	}
	
	public boolean isFact() {
		return this.premises.isEmpty() && this.head != null;
	}
	
	public Literal getHead() {
		return this.head;
	}

	
	public void print() {
		
		Iterator<Literal> iter = this.getPremisesList();
		while(iter.hasNext()) {
			Literal next = iter.next();
			
			next.print();
			
			if(iter.hasNext())
				System.out.print(" /\\ ");
			else
				System.out.print(" => ");
			
		}
		
		
		this.head.print();
		
	}
}
