package bddbddb;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Relation {

	enum Type {
		vP0, assign;
	}
	
	static class Tuple {
		final String v1;
		final String v2;
		
		Tuple(String v1, String v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
	}
	
	private List<Tuple> vP0 = new LinkedList<Tuple>();
	private List<Tuple> assign = new LinkedList<Tuple>();
	
	private Set<String> v = new TreeSet<String>();
	private Set<String> h = new TreeSet<String>();
	
	public void add(Type type, Tuple tuple) {
		if (type == Type.vP0) {
			v.add(tuple.v1);
			h.add(tuple.v2);
			vP0.add(tuple);
		} else if (type == Type.assign) {
			v.add(tuple.v1);
			v.add(tuple.v2);
			assign.add(tuple);
		}
	}
	
	public void print(PrintStream vMapStream, PrintStream hMapStream, PrintStream vP0Stream, PrintStream assignStream) {
		
		Map<String, Integer> vMap = new HashMap<String, Integer>();
		int i = 0;
		for (String s : v) {
			vMap.put(s, i);
			vMapStream.println(s);
			i++;
		}
		
		Map<String, Integer> hMap = new HashMap<String, Integer>();
		i = 0;
		for (String s : h) {
			hMap.put(s, i);
			hMapStream.println(s);
			i++;
		}
		
		vP0Stream.println("# V0:" + vP0.size() + " H0:" + vP0.size());
		assignStream.println("# V0:" + assign.size() + " V1:" + assign.size());
		
		for (Tuple t : vP0)
			vP0Stream.println(vMap.get(t.v1) + " " + hMap.get(t.v2));
		
		for (Tuple t : assign)
			assignStream.println(vMap.get(t.v1) + " " + vMap.get(t.v2));
	}
}
