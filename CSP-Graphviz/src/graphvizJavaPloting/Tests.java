package graphvizJavaPloting;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) throws IOException {
		ArrayList<String> nodes = new ArrayList<>();
		nodes.add("A");
		nodes.add("tick");
		//nodes.add("C");
		//nodes.add("D");
		//nodes.add("E");
		GraphvizJava gv = new GraphvizJava("Outputs", "/pagina.html");
		gv.ploting(nodes.toArray(new String[0]));
	}

}
