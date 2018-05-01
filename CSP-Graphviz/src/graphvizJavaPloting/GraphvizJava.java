package graphvizJavaPloting;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphvizJava implements Plottable {
	
	public GraphvizJava() {}
	
	@Override
	public void ploting(String[] nodes) throws IOException {
		//Arquivo JS com o código do grafo
        String arquivoJson = "Outputs/counterExampleGraph.json";
        
        File grafo = new File(arquivoJson);
        if(!grafo.exists()){
        	grafo.createNewFile();
        }
              
        // Iterando sobre a lista de processos, gerando os nodos:
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arquivoJson));
		
		buffWrite.append("{   \"nodes\":[" + "\n");
		
		int penultimateIndex = nodes.length - 2;
		int lastIndex = nodes.length - 1;
		
		for (int i = 0; i < lastIndex; i++) {
			nodes[i] = destacarSkipStop(nodes[i]);
			buffWrite.append("    {\"name\":\"" + nodes[i] + "\",\"id\":\""+ nodes[i] + i +"\"}," + "\n");
		}
		
		nodes[lastIndex] = destacarSkipStop(nodes[lastIndex]);
		String lastNode = nodes[lastIndex];
		
		if (lastNode.equals("SKIP")) {
			buffWrite.append("    {\"name\":\"" + "SKIP" + "\",\"id\":\""+ "SKIP" + lastIndex +"\"}," + "\n");
			buffWrite.append("    {\"name\":\"" + "STOP" + "\",\"id\":\""+ "STOP" + lastIndex +"\"}" + "\n");
			buffWrite.append("    ]," + "\n");
		} else if (!lastNode.equals("STOP")) {
			buffWrite.append("    {\"name\":\"" + lastNode + "\",\"id\":\""+ lastNode + lastIndex +"\"}," + "\n");
			buffWrite.append("    {\"name\":\"" + "ENDLINE" + "\",\"id\":\""+ "ENDLINE" + lastIndex +"\"}" + "\n");
			buffWrite.append("    ]," + "\n");
		} else {
			buffWrite.append("    {\"name\":\"" + "STOP" + "\",\"id\":\""+ "STOP" + lastIndex +"\"}" + "\n");
			buffWrite.append("    ]," + "\n");
		}
		
		// Iterando sobre a lista de processos, gerando as arestas:		
		buffWrite.append("    \"links\":[" + "\n");
		
		for (int i = 0; i < penultimateIndex; i++) {
			int actualIndex = i;
			int nextIndex = i + 1;
			buffWrite.append("    {\"source\":\"" + nodes[actualIndex] + actualIndex + "\",\"target\":\"" + nodes[nextIndex] + nextIndex + "\"},"+ "\n");
		}
		
		String penultimateNode = nodes[penultimateIndex];
		
		if (lastNode.equals("SKIP")) {
			buffWrite.append("    {\"source\":\"" + penultimateNode + penultimateIndex + "\",\"target\":\"" + lastNode + lastIndex + "\"},"+ "\n");
			buffWrite.append("    {\"source\":\"" + "SKIP" + lastIndex + "\",\"target\":\"" + "STOP" + lastIndex + "\"}"+ "\n");
		} else if (!lastNode.equals("STOP")) {
			buffWrite.append("    {\"source\":\"" + penultimateNode + penultimateIndex + "\",\"target\":\"" + lastNode + lastIndex + "\"},"+ "\n");
			buffWrite.append("    {\"source\":\"" + lastNode + lastIndex + "\",\"target\":\"" + "ENDLINE" + lastIndex + "\"}"+ "\n");
		} else {
			buffWrite.append("    {\"source\":\"" + penultimateNode + penultimateIndex + "\",\"target\":\"" + lastNode + lastIndex + "\"}"+ "\n");
		}
		
		buffWrite.append("    ]"+"\n");
		buffWrite.append("}");
		buffWrite.close();
		
	}
	
	private String destacarSkipStop(String node) {
		String result = node;
		if (node.equalsIgnoreCase("tick")) {
			result = "SKIP";
		} else if (node.equalsIgnoreCase("STOP")) {
			result = "STOP";
		}
		return result;
	}
	

}
