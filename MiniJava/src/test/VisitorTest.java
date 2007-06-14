package test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import bddbddb.SymbolTableVisitor;

import parser.MiniJavaParser;
import parser.ParseException;
import syntaxtree.Goal;

public class VisitorTest {

	/**
	 * @param args
	 * @throws ParseException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws FileNotFoundException,
			ParseException {
		Goal program = new MiniJavaParser(new FileReader(args[0])).Goal();

		new SymbolTableVisitor().visit(program);
	}
}
