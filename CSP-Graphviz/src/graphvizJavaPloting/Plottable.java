package graphvizJavaPloting;

import java.io.IOException;

public interface Plottable {
	public void ploting(String[] nodes, boolean deadlock) throws IOException;
}
