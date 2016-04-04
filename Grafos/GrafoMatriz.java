package Grafos;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class GrafoMatriz here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrafoMatriz<V, A> implements Grafo<V, A> {
    /**
     * Constructor for objects of class GrafoMatriz
     */
    public GrafoMatriz() {
        this.vertices = new ArrayList<Vertice<V>>();
    	matrizArestas = new Aresta[TAMANHO][TAMANHO];
    }
    
    @Override
    public List<Vertice<V>> vertices() {
    	return vertices;
    }
    
    @Override
    public List<Aresta<A, V>> arestas() {
    	List<Aresta<A, V>> a = new ArrayList<>();
    	
    	for (int i = 1; i < matrizArestas.length; i++) {
			for (int j = 0; j < matrizArestas[i].length - (matrizArestas.length - i); j++) {
				if( matrizArestas[i][j] != null ){
					a.add(matrizArestas[i][j]);
				}
			}
		}
    	return a;
    }
    
    @Override
    public List<Aresta<A, V>> arestasIncidentes(Vertice<V> v) {
    	List<Aresta<A, V>> incidente = new ArrayList<>();
    	for (Aresta<A, V> aresta : arestas()) {
			if( aresta.getVerticeA().equals(v) || aresta.getVerticeB().equals(v) ){
				incidente.add(aresta);
			}
		}
    	return incidente;
    }
    
    @Override
    public Vertice<V> oposto(Vertice<V> v, Aresta<A, V> e) {
    	return ( e.getVerticeA() == v ) ? e.getVerticeB() : e.getVerticeA();
    }
    
    @Override
    public List<Vertice<V>> verticesFim(Aresta<A, V> e) {
    	List<Vertice<V>> vertice = new ArrayList<>();
    	vertice.add(e.getVerticeA());
    	vertice.add(e.getVerticeB());
    	return vertice;
    }
    
    @Override
    public boolean saoAdjacentes(Vertice<V> v, Vertice<V> w) {
    	for (Aresta<A, V> aresta : arestas()) {
			if( (aresta.getVerticeA().equals(v) || aresta.getVerticeB().equals(v)) &&
					(aresta.getVerticeA().equals(w) || aresta.getVerticeB().equals(w)) ) return true;
		}
    	return false;
    }
    
    @Override
    public V mudarPeso(Vertice<V> v, V x) {
    	v.setAtributos(x);
    	return x;
    }
    
    @Override
    public A mudarPeso(Aresta<A, V> e, A x) {
    	e.setAtributos(x);
		return x;
	}
    
    @Override
    public Vertice<V> inserirVertice(V x) {
    	Vertice<V> v = new Vertice<V>(x);
    	vertices.add(v);
    	return v;
    }
    
	@Override
	public Aresta<A, V> inserirAresta(Vertice<V> v, Vertice<V> w, A x) {
		Aresta<A, V> a = new Aresta<A, V>(v, w, x);
		
		int i = getIndex(v);
		int j = getIndex(w);
		
		if( i != j ) {
			matrizArestas[i][j] = a;
			matrizArestas[j][i] = a;
		}
		return a;
	}
    
    private int getIndex(Vertice<V> v) {
       for (int i = 0; i < vertices.size(); i++)
          if ( vertices.get(i).equals(v) )
             return i;
       return -1;
    }
	
    @Override
    public V removerVertice(Vertice<V> v) {
    	V b = v.getAtributos();
    	
    	for (Aresta<A, V> aresta : arestas()) {
			if( aresta.getVerticeA().equals(v) || 
					aresta.getVerticeB().equals(v) ){
				removerAresta(aresta);
			}
		}
    	vertices.remove(v);
    	return b;
    }
    
    @Override
    public A removerAresta(Aresta<A, V> e) {
    	A b = e.getAtributos();
		int i = getIndex(e.getVerticeA());
		int j = getIndex(e.getVerticeB());
    	
		if( i != j ) {
			matrizArestas[i][j] = null;
			matrizArestas[j][i] = null;
		}
    	return b;
    }
    
    private static final int TAMANHO = 50;
    private ArrayList<Vertice<V>> vertices;
    private Aresta<A, V>[][] matrizArestas;
}
