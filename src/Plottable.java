
import java.io.IOException;

public interface Plottable {
	public void plot(String[] nodes, boolean deadlock) throws IOException;
}
