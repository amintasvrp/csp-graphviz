package graphvizJavaPloting;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraphvizJava implements Plottable {
	
	public GraphvizJava() {}
	
	@Override
	public void ploting(String[] nodes) throws IOException {
		//Arquivo JS com o código do grafo
        String arquivoJson = "Outputs/counterExampleGraph.json";
              
        // Iterando sobre a lista de processos, gerando os nodos:
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arquivoJson));
		
		buffWrite.append("{   \"nodes\":[" + "\n");
		
		int penultimateIndex = nodes.length - 2;
		int lastIndex = nodes.length - 1;
		
		for (int i = 0; i < lastIndex; i++) {
			String node = destacarSkipStop(nodes[i]);
			buffWrite.append("    {\"name\":\"" + node + "\",\"id\":"+ i +"}," + "\n");
		}
		
		String lastNode = destacarSkipStop(nodes[lastIndex]);
		buffWrite.append("    {\"name\":\"" + lastNode + "\",\"id\":"+ lastIndex +"}" + "\n");
		buffWrite.append("    ]," + "\n");
		
		// Iterando sobre a lista de processos, gerando as arestas:		
		buffWrite.append("    \"links\":[" + "\n");
		
		for (int i = 0; i < penultimateIndex; i++) {
			int actualIndex = i;
			int nextIndex = i + 1;
			buffWrite.append("    {\"source\":" + actualIndex + ",\"target\":" + nextIndex + "},"+ "\n");
		}
				
		buffWrite.append("    {\"source\":" + penultimateIndex + ",\"target\":" + lastIndex + "}"+ "\n");
		
		
		buffWrite.append("    ]"+"\n");
		buffWrite.append("}");
		buffWrite.close();
		
	}
	
	private String destacarSkipStop(String node) {
		String result = node;
		if (node.equalsIgnoreCase("SKIP")) {
			result = "SKIP";
		} else if (node.equalsIgnoreCase("STOP")) {
			result = "STOP";
		}
		return result;
	}
	

}
