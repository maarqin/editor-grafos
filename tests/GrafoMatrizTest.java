package tests;

import java.awt.Point;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import Grafos.Aresta;
import Grafos.GrafoMatriz;
import Grafos.Vertice;
import src.AAtribs;
import src.VAtribs;


public class GrafoMatrizTest {
	
	private GrafoMatriz<VAtribs, AAtribs> matriz = new GrafoMatriz<>();
	
	@Test
	public void testClass() {
		VAtribs v1 = new VAtribs(new Point(100, 200), "A1");
		VAtribs v2 = new VAtribs(new Point(200, 300), "A2");
		VAtribs v3 = new VAtribs(new Point(300, 400), "A3");
		VAtribs v4 = new VAtribs(new Point(500, 600), "A4");
		
		matriz.inserirVertice(v1);
		matriz.inserirVertice(v2);
		matriz.inserirVertice(v3);
		matriz.inserirVertice(v4);

		List<Vertice<VAtribs>> vertices = matriz.vertices();
		
		Assert.assertEquals(vertices.get(0).getAtributos().getPosicao(), v1.getPosicao());
		Assert.assertEquals(vertices.get(1).getAtributos().getPosicao(), v2.getPosicao());
		Assert.assertEquals(vertices.get(2).getAtributos().getPosicao(), v3.getPosicao());
		Assert.assertEquals(vertices.get(3).getAtributos().getPosicao(), v4.getPosicao());
		
		AAtribs a1 = new AAtribs(new Integer(10));
		AAtribs a2 = new AAtribs(new Integer(20));
		AAtribs a3 = new AAtribs(new Integer(30));
		AAtribs a4 = new AAtribs(new Integer(40));
		
		matriz.inserirAresta(vertices.get(0), vertices.get(1), a1);
		matriz.inserirAresta(vertices.get(1), vertices.get(2), a2);
		matriz.inserirAresta(vertices.get(2), vertices.get(3), a3);
		matriz.inserirAresta(vertices.get(3), vertices.get(0), a4);
		
		Assert.assertEquals(matriz.saoAdjacentes(vertices.get(0), vertices.get(1)), true);
		Assert.assertEquals(matriz.saoAdjacentes(vertices.get(0), vertices.get(2)), false);
		
	}

}
