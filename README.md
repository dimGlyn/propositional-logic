# propositional-logic
(1)Export conclusions with resolution (resolution) / (2)conclusions with forward chaining for (definite) Horn clauses of propositional logic

1 - Conjunctive normal form
-------------
For the implementation I use the classes: CNFClause, CNFSubClause, Literal and Main.

We read the knowledge base from cnfclauses.txt. The negation of the term that we want proved is given though the console
and can have the form of a CNF e.g: (L1,L2,…,Ln),( L1,L2,…,Lm),…,( L1,L2,…,Lk) <!-- NO SPACES --> where each parenthesis has a CNFSubClause and the Li Literals.

The form of the txt file is a single line and represents the knowledge base in CNF.

Implementation
-----------
- The class Literal implements a symbol.
- The class CNFSubClause implements a dissociative type, a set of symbols which we consider they are in dissociative form. Also in this class we implement the resolution between two dissociative types in the function: Vector<CNFSubClause> resolution(CNFSubClause CNF_SC_1, CNFSubClause CNF_SC_2).
- The class CNFClause implements the Conjunctive Normal Form, a set of dissociative types.
- In the class main I implement the resolution rule in the function: boolean PL_Resolution(CNFClause KB, CNFClause a)
  
2 - Forward Chain - Definite Clauses
--------------------------------
For the implementation I use the classes: DefiniteClause, DefClauseKB, Literal and ForwardChain.

We read the knowledge base from the defClause.txt file. The type that we want proved can only be a Literal and is given through the console.

The form of the .txt is :(L1,…,Ln=>H1),…,(L1,…,Lk=>Hm)
where each parenthesis is a definite Horn clause. (Can also be of the form of (Hi) that is, it is a fact). We consider that each Li is a set of symbols in conjugate form.

Implementation
---------------
- In the class DefiniteClause I implement a definite Horn clause.
- In the class DefClauseKB I implements a knowledge base, so a CNF that is consisted of definite horn clauses.
- In the class ForwardChain I implement the PlFcEntails(propositional logic forward chain entails) algorithm in the corresponding method. This class is also the main.
