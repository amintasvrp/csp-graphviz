package graphvizJavaPloting;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraphvizJava {
	
	public GraphvizJava() {}

	public void ploting(String[] nodes) throws IOException {
		//Arquivo com o código DOT
        String arquivoDot = "Outputs/contraExemplo.dot";
        
        //Comando dot
      	String graphDir = "dot.exe -Tpng ";
              
        //Arquivo de saída
        String arquivoPNG = "Outputs/contraExemplo.png"; 
        
        // Iterando sobre a lista de processos, gerando nodos e arestas
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arquivoDot));
		buffWrite.append("digraph {"+"\n");
		for (String node : nodes) {
			if (node.equalsIgnoreCase("skip")) {
				buffWrite.append(node + " [label=\"SKIP\", style=filled, fillcolor=blue]"+"\n");								
			} else if (node.equalsIgnoreCase("stop")) {
				buffWrite.append(node + " [label=\"STOP\", style=filled, fillcolor=red]"+"\n");
			} else {
				buffWrite.append(node + " [label=\"" + node + "\"]"+"\n");
			}
		}
		
		for (int i = 0; i < nodes.length - 1; i++) {
			buffWrite.append(nodes[i] + " -> " + nodes[i+1] + "\n");
		}
		buffWrite.append("}"+"\n");
		buffWrite.close();
		
		//Gerando grafo
		Runtime rt = Runtime.getRuntime();
        
        String cmdString = graphDir + arquivoDot + " -o " + arquivoPNG;
        rt.exec(cmdString);
	}

}
