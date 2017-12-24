package graphvizJavaPloting;

import java.io.IOException;

public class Tests {

	public static void main(String[] args) throws IOException {
		String[] arrayOfNodes = new String[3];
		arrayOfNodes[0] = "chA";
		arrayOfNodes[1] = "chB";
		arrayOfNodes[2] = "chA";
		GraphvizJava gv = new GraphvizJava();
		gv.ploting(arrayOfNodes);
	}

}
