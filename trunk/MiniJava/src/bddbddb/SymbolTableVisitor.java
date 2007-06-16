//
// Generated by JTB 1.3.2
//

package bddbddb;

import syntaxtree.AllocationExpression;
import syntaxtree.ArrayAllocationExpression;
import syntaxtree.ArrayType;
import syntaxtree.BooleanType;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.FormalParameter;
import syntaxtree.Identifier;
import syntaxtree.IntegerType;
import syntaxtree.MethodDeclaration;
import syntaxtree.ThisExpression;
import syntaxtree.VarDeclaration;
import visitor.DepthFirstVisitor;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order. Your visitors may extend this class.
 */
public class SymbolTableVisitor extends DepthFirstVisitor {

	private class State {
		boolean allocate;
		String currClass;
		String currMethod;
		String lastIdentifier;
		boolean messageSend;
		Method method;
		boolean thisTerm;
	}
	
	private State state;
	private SymbolTable table;
	
	public SymbolTableVisitor(SymbolTable table) {
		this.table = table;
		this.state = new State();
	}

	//
	// User-generated visitor methods below
	//

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> ( VarDeclaration() )*
	 * f4 -> ( * MethodDeclaration() )*
	 * f5 -> "}"
	 */
	public void visit(ClassDeclaration n) {
		state.currClass = n.f1.f0.tokenImage;
		state.currMethod = "";
		
		n.f3.accept(this);
		n.f4.accept(this);
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends" 
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
	public void visit(ClassExtendsDeclaration n) {
		state.currClass = n.f1.f0.tokenImage;
		state.currMethod = "";
		
		table.add(state.currClass, new Class(n.f3.f0.tokenImage));
		
		n.f5.accept(this);
		n.f6.accept(this);
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public void visit(VarDeclaration n) {
		n.f0.accept(this);
		String type = state.lastIdentifier;
		
		String name = state.currClass + "_" + state.currMethod + n.f1.f0.tokenImage;
		table.add(name, new Variable(type, name));
	}

	/**
	 * f0 -> "public"
	 * f1 -> Type()
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( * FormalParameterList() )?
	 * f5 -> ")"
	 * f6 -> "{"
	 * f7 -> ( VarDeclaration() )*
	 * f8 -> ( Statement() )*
	 * f9 -> "return"
	 * f10 -> Expression()
	 * f11 -> ";"
	 * f12 -> "}"
	 */
	public void visit(MethodDeclaration n) {
		state.currMethod = n.f2.f0.tokenImage + "_";

		n.f1.accept(this);
		state.method = new Method(state.lastIdentifier);
		table.add(state.currClass + "_" + n.f2.f0.tokenImage, state.method);
		
		n.f4.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		
		state.allocate = false;
		state.messageSend = false;
		state.thisTerm = false;
		n.f10.accept(this);
		if (state.allocate || state.messageSend) {
			state.method.setReturnLabel(state.currClass + "_" + state.currMethod + "return");
		} else if (state.thisTerm) {
			state.method.setReturnLabel(state.currClass);
		} else {
			state.method.setReturnLabel(state.currClass + "_" + state.currMethod + state.lastIdentifier);
		}
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 */
	public void visit(FormalParameter n) {
		String name = state.currClass + "_" + state.currMethod + n.f1.f0.tokenImage;
		state.method.addParameter(name);
		
		n.f0.accept(this);
		table.add(name, new Variable(state.lastIdentifier, name));
	}

	/**
	 * f0 -> "int"
	 * f1 -> "["
	 * f2 -> "]"
	 */
	public void visit(ArrayType n) {
		state.lastIdentifier = "int[]";
	}

	/**
	 * f0 -> "boolean"
	 */
	public void visit(BooleanType n) {
		state.lastIdentifier = "boolean";
	}

	/**
	 * f0 -> "int"
	 */
	public void visit(IntegerType n) {
		state.lastIdentifier = "int";
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public void visit(Identifier n) {
		state.lastIdentifier = n.f0.tokenImage;
	}

	/**
	 * f0 -> "new"
	 * f1 -> "int"
	 * f2 -> "["
	 * f3 -> Expression()
	 * f4 -> "]"
	 */
	public void visit(ArrayAllocationExpression n) {
		state.allocate = true;
		n.f3.accept(this);
	}

	/**
	 * f0 -> "new"
	 * f1 -> Identifier()
	 * f2 -> "("
	 * f3 -> ")"
	 */
	public void visit(AllocationExpression n) {
		state.allocate = true;
	}
	
	/**
	 * f0 -> "this"
	 */
	public void visit(ThisExpression n) {
		state.thisTerm = true;
		n.f0.accept(this);
	}
}