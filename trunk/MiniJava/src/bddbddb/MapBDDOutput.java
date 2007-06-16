package bddbddb;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

public class MapBDDOutput {

	private static Map<Integer, String> var = new TreeMap<Integer, String>();
	private static Map<Integer, String> heap = new TreeMap<Integer, String>();
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader varMap = new BufferedReader(new FileReader("algorithm/input/var.map"));
		String line;
		int i = 0;
		while ((line = varMap.readLine()) != null) {
			var.put(i, line);
			i++;
		}
		varMap.close();
		
		BufferedReader heapMap = new BufferedReader(new FileReader("algorithm/input/heap.map"));
		i = 0;
		while ((line = heapMap.readLine()) != null) {
			heap.put(i, line);
			i++;
		}
		heapMap.close();
		
		BufferedReader vP0 = new BufferedReader(new FileReader("algorithm/input/vP0.tuples"));
		PrintStream vP0Out = new PrintStream(new FileOutputStream("algorithm/output/vP0.out"));
		printRelation("vP0", vP0, vP0Out);
		vP0.close();
		vP0Out.close();
		
		BufferedReader assign = new BufferedReader(new FileReader("algorithm/input/assign.tuples"));
		PrintStream assignOut = new PrintStream(new FileOutputStream("algorithm/output/assign.out"));
		printRelation("assign", assign, assignOut);
		assign.close();
		assignOut.close();
		
		BufferedReader vP = new BufferedReader(new FileReader("algorithm/input/vP.tuples"));
		PrintStream vPOut = new PrintStream(new FileOutputStream("algorithm/output/vP.out"));
		printRelation("vP",	vP, vPOut);
		vP.close();
		vPOut.close();		
	}
	
	private static void printRelation(String relation, BufferedReader input, PrintStream output) throws IOException {
		String line = input.readLine();
		while ((line = input.readLine()) != null) {
			String[] token = line.split(" ");
			Integer v = Integer.parseInt(token[0]);
			Integer h = Integer.parseInt(token[1]);
			output.println(relation + " (" + var.get(v) + ", " + var.get(h) + ")");
		}
	}
}
