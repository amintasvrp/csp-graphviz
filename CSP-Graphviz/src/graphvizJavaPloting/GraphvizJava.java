package graphvizJavaPloting;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphvizJava implements Plottable {
	
	private String diretorioJson;

	public GraphvizJava() {
		this.diretorioJson = "";
	}
	
	@Override
	public void ploting(String[] nodes) throws IOException {
		//JSON with the graph:
        String arquivoJson = getDiretorioJson();
        
        File grafo = new File(arquivoJson);
        if(!grafo.exists()){
        	grafo.createNewFile();
        }
              
        // Iterating over the list of processes, generating the nodes:
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
		
		// Iterating over the list of processes, generating edges:		
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
	
	public void createhtmlpage(String directoryName,String fileName) throws IOException {
		  File f = new File(directoryName+fileName);
		  if(!f.exists()){
		  f.createNewFile();
		  BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
		  
		  setDiretorioJson(directoryName);
		  
		  String htmlContent = htmlContent(directoryName); // observar o nome do json no htmlContent();
		  buffWrite.append(htmlContent);
		  buffWrite.close();
		  }
		}

		public String htmlContent(String directoryJson) {
		  return "<!DOCTYPE html>\r\n" + 
						"<html lang=\"en\">\r\n" + 
						"<head>\r\n" + 
						"    <meta charset=\"utf-8\">\r\n" + 
						"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
						"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
						"    <style type=\"text/css\">\r\n" + 
						"        .node {}\r\n" + 
						"\r\n" + 
						"        .link { stroke: #999; stroke-opacity: .6; stroke-width: 1px; }\r\n" + 
						"    </style>\r\n" + 
						"</head>\r\n" + 
						"<body>\r\n" + 
						"<svg width=\"960\" height=\"600\"></svg>\r\n" + 
						"\r\n" + 
						"<script src=\"http://d3js.org/d3.v4.min.js\" type=\"text/javascript\"></script>\r\n" + 
						"<script src=\"http://d3js.org/d3-selection-multi.v1.js\"></script>\r\n" + 
						"\r\n" + 
						"<script type=\"text/javascript\"> \r\n"+
		   
		                                "    var svg = d3.select(\"svg\"),\r\n" + 
						"        width = +svg.attr(\"width\"),\r\n" + 
						"        height = +svg.attr(\"height\"),\r\n" + 
						"        node,\r\n" + 
						"        link;\r\n" + 
						"\r\n" + 
						"    svg.append('defs').append('marker')\r\n" + 
						"        .attrs({'id':'arrowhead',\r\n" + 
						"            'viewBox':'-0 -5 10 10',\r\n" + 
						"            'refX':13,\r\n" + 
						"            'refY':0,\r\n" + 
						"            'orient':'auto',\r\n" + 
						"            'markerWidth':13,\r\n" + 
						"            'markerHeight':13,\r\n" + 
						"            'xoverflow':'visible'})\r\n" + 
						"        .append('svg:path')\r\n" + 
						"        .attr('d', 'M 0,-5 L 10 ,0 L 0,5')\r\n" + 
						"        .attr('fill', '#999')\r\n" + 
						"        .style('stroke','none');\r\n" + 
						"\r\n" + 
						"    var simulation = d3.forceSimulation()\r\n" + 
						"        .force(\"link\", d3.forceLink().id(function (d) {return d.id;}).distance(100).strength(1))\r\n" + 
						"        .force(\"charge\", d3.forceManyBody())\r\n" + 
						"        .force(\"center\", d3.forceCenter(width / 2, height / 2));\r\n" + 
						"\r\n" + 
		                                "    d3.json("+ directoryJson +", function (error, graph) {\r\n" +
		                                "    if (error) throw error;\r\n" +
		                                "    update(graph.links, graph.nodes);\r\n" +
		                                "})\r\n" +
		                                "    function update(links, nodes) {\r\n" + 
						"        link = svg.selectAll(\".link\")\r\n" + 
						"            .data(links)\r\n" + 
						"            .enter()\r\n" + 
						"            .append(\"line\")\r\n" + 
						"            .attr(\"class\", \"link\")\r\n" + 
						"            .attr('marker-end','url(#arrowhead)')\r\n" + 
						"\r\n" + 
						"        link.append(\"title\")\r\n" + 
						"            .text(function (d) {return d.type;});\r\n" + 
						"\r\n" + 
						"        edgepaths = svg.selectAll(\".edgepath\")\r\n" + 
						"            .data(links)\r\n" + 
						"            .enter()\r\n" + 
						"            .append('path')\r\n" + 
						"            .attrs({\r\n" + 
						"                'class': 'edgepath',\r\n" + 
						"                'fill-opacity': 0,\r\n" + 
						"                'stroke-opacity': 0,\r\n" + 
						"                'id': function (d, i) {return 'edgepath' + i}\r\n" + 
						"            })\r\n" + 
						"            .style(\"pointer-events\", \"none\");\r\n" + 
						"\r\n" + 
						"        edgelabels = svg.selectAll(\".edgelabel\")\r\n" + 
						"            .data(links)\r\n" + 
						"            .enter()\r\n" + 
						"            .append('text')\r\n" + 
						"            .style(\"pointer-events\", \"none\")\r\n" + 
						"            .attrs({\r\n" + 
						"                'class': 'edgelabel',\r\n" + 
						"                'id': function (d, i) {return 'edgelabel' + i},\r\n" + 
						"                'font-size': 16,\r\n" + 
						"                'fill': '#aaa'\r\n" + 
						"            });\r\n" + 
						"\r\n" + 
						"        edgelabels.append('textPath')\r\n" + 
						"            .attr('xlink:href', function (d, i) {return '#edgepath' + i})\r\n" + 
						"            .style(\"text-anchor\", \"middle\")\r\n" + 
						"            .style(\"pointer-events\", \"none\")\r\n" + 
						"            .attr(\"startOffset\", \"50%\")\r\n" + 
						"            .text(function (d) {\r\n" + 
						"                var sourceName = d.source.substring(0,(d.source.length -1));\r\n" + 
						"                if (sourceName == \"SKIP\") {\r\n" + 
						"                    return \"tick\";\r\n" + 
						"                } else {\r\n" + 
						"                    return sourceName;\r\n" + 
						"                }\r\n" + 
						"            });\r\n" + 
						"\r\n" + 
						"        node = svg.selectAll(\".node\")\r\n" + 
						"            .data(nodes)\r\n" + 
						"            .enter()\r\n" + 
						"            .append(\"g\")\r\n" + 
						"            .attr(\"class\", \"node\")\r\n" + 
						"            .call(d3.drag()\r\n" + 
						"                    .on(\"start\", dragstarted)\r\n" + 
						"                    .on(\"drag\", dragged)\r\n" + 
						"                    //.on(\"end\", dragended)\r\n" + 
						"            );\r\n" + 
						"\r\n" + 
						"        node.append(\"circle\")\r\n" + 
						"            .attr(\"r\", 5)\r\n" + 
						"            .style(\"fill\", function (d) {\r\n" + 
						"            	if (d.name == \"STOP\") {\r\n" + 
						" 					return \"red\";\r\n" + 
						"            	} else {\r\n" + 
						"            		return \"grey\";\r\n" + 
						"            	}\r\n" + 
						"            });\r\n" + 
						"\r\n" + 
						"        node.append(\"text\")\r\n" + 
						"            .attr(\"dy\", -3)\r\n" + 
						"            .text(function (d) {\r\n" + 
						"                if (d.name == \"STOP\") {\r\n" + 
						"                    return \"STOP\";\r\n" + 
						"                } else if(d.name == \"SKIP\"){\r\n" + 
						"                    return \"SKIP\";\r\n" + 
						"                } else {\r\n" + 
						"                    return \"\";\r\n" + 
						"                }\r\n" + 
						"            });\r\n" + 
						"\r\n" + 
						"        simulation\r\n" + 
						"            .nodes(nodes)\r\n" + 
						"            .on(\"tick\", ticked);\r\n" + 
						"\r\n" + 
						"        simulation.force(\"link\")\r\n" + 
						"            .links(links);\r\n" + 
						"    }\r\n" + 
						"\r\n" + 
						"    function ticked() {\r\n" + 
						"        link\r\n" + 
						"            .attr(\"x1\", function (d) {return d.source.x;})\r\n" + 
						"            .attr(\"y1\", function (d) {return d.source.y;})\r\n" + 
						"            .attr(\"x2\", function (d) {return d.target.x;})\r\n" + 
						"            .attr(\"y2\", function (d) {return d.target.y;});\r\n" + 
						"\r\n" + 
						"        node\r\n" + 
						"            .attr(\"transform\", function (d) {return \"translate(\" + d.x + \", \" + d.y + \")\";});\r\n" + 
						"\r\n" + 
						"        edgepaths.attr('d', function (d) {\r\n" + 
						"            return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;\r\n" + 
						"        });\r\n" + 
						"\r\n" + 
						"        edgelabels.attr('transform', function (d) {\r\n" + 
						"            if (d.target.x < d.source.x) {\r\n" + 
						"                var bbox = this.getBBox();\r\n" + 
						"\r\n" + 
						"                rx = bbox.x + bbox.width / 2;\r\n" + 
						"                ry = bbox.y + bbox.height / 2;\r\n" + 
						"                return 'rotate(180 ' + rx + ' ' + ry + ')';\r\n" + 
						"            }\r\n" + 
						"            else {\r\n" + 
						"                return 'rotate(0)';\r\n" + 
						"            }\r\n" + 
						"        });\r\n" + 
						"    }\r\n" + 
						"\r\n" + 
						"    function dragstarted(d) {\r\n" + 
						"        if (!d3.event.active) simulation.alphaTarget(0.3).restart()\r\n" + 
						"        d.fx = d.x;\r\n" + 
						"        d.fy = d.y;\r\n" + 
						"    }\r\n" + 
						"\r\n" + 
						"    function dragged(d) {\r\n" + 
						"        d.fx = d3.event.x;\r\n" + 
						"        d.fy = d3.event.y;\r\n" + 
						"    }\r\n" + 
						"\r\n" + 
						"</script>\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>";
		}
		
		public String getDiretorioJson() {
			return diretorioJson;
		}

		public void setDiretorioJson(String diretorioJson) {
			this.diretorioJson = diretorioJson + "/counterExampleGraph.json";
		}

}
