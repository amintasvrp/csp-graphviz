package graphvizJavaPloting;

import java.io.IOException;

public class Tests {

	public static void main(String[] args) throws IOException {
		String[] arrayOfNodes = new String[3];
		arrayOfNodes[0] = "a";
		arrayOfNodes[1] = "b";
		arrayOfNodes[2] = "a";
		GraphvizJava gv = new GraphvizJava();
		gv.ploting(arrayOfNodes);
	}

}
