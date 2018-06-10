package graphvizJavaPloting;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) throws IOException {
		ArrayList<String> nodes = new ArrayList<>();
		nodes.add("a");
		//nodes.add("b");
		//nodes.add("c");
		//nodes.add("d");
		//nodes.add("tick");
		GraphvizJava gv = new GraphvizJava("Outputs", "/pagina.html");
		gv.ploting(nodes.toArray(new String[0]),false);
	}

}
