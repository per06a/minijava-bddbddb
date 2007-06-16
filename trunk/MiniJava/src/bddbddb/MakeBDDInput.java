package bddbddb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import parser.MiniJavaParser;
import parser.ParseException;
import syntaxtree.Goal;

public class MakeBDDInput {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws FileNotFoundException,
			ParseException {
		Goal program = new MiniJavaParser(new FileReader(args[0])).Goal();
		
		SymbolTable table = new SymbolTable();
		new SymbolTableVisitor(table).visit(program);
		//System.out.println(table);
		
		PrintStream vMap = new PrintStream(new FileOutputStream("algorithm/input/var.map"));
		PrintStream hMap = new PrintStream(new FileOutputStream("algorithm/input/heap.map"));
		PrintStream vP0 = new PrintStream(new FileOutputStream("algorithm/input/vP0.tuples"));
		PrintStream assign = new PrintStream(new FileOutputStream("algorithm/input/assign.tuples"));
		
		Relation relation = new Relation();
		new RelationVisitor(table, relation).visit(program);
		
		relation.print(vMap, hMap, vP0, assign);
		
		vMap.close();
		hMap.close();
		vP0.close();
		assign.close();
	}
}
