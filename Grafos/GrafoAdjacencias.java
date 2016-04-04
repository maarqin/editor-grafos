package Grafos;

import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class GrafoAdjacencias here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrafoAdjacencias<V, A> implements Grafo<V, A>
{
    /**
     * Constructor for objects of class GrafoAdjacencias
     */
    public GrafoAdjacencias() {
        this.vertices = new ArrayList<Vertice<V>>();
        this.arestas = new ArrayList<Aresta<A, V>>();
    }
    
    @Override
    public List<Vertice<V>> vertices() {
        return this.vertices;
    }
    
    @Override
    public List<Aresta<A, V>> arestas() {
    	return this.arestas;
    }
    
    @Override
    public List<Aresta<A, V>> arestasIncidentes(Vertice<V> v) {
    	List<Aresta<A, V>> incidente = new ArrayList<>();
    	for (Aresta<A, V> aresta : arestas) {
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
    	for (Aresta<A, V> aresta : this.arestas) {
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
        Vertice<V> ins = new Vertice<V>(x);
        vertices.add(ins);
        return ins;
    }
    
    @Override
    public Aresta<A, V> inserirAresta(Vertice<V> v, Vertice<V> w, A x) {
    	Aresta<A, V> ins = new Aresta<A, V>(v, w, x);
    	arestas.add(ins);
    	return ins;
    }
    
    @Override
    public V removerVertice(Vertice<V> v) {
    	V b = v.getAtributos();
    	
    	List<Aresta<A, V>> toRemove = new ArrayList<>();
    	for (Aresta<A, V> aresta : this.arestas) { 
			if( aresta.getVerticeA().equals(v) || 
					aresta.getVerticeB().equals(v) ){
				toRemove.add(aresta);
			}
		}
    	for (Aresta<A, V> aresta : toRemove) {
			removerAresta(aresta);
		}
    	vertices.remove(v);
    	return b;
    }
    
    @Override
    public A removerAresta(Aresta<A, V> e) {
    	A b = e.getAtributos();
    	
    	List<Aresta<A, V>> arestas = this.arestas;
    	arestas.remove(e);
    	return b;
    }
    
    
    private ArrayList<Vertice<V>> vertices;
    private ArrayList<Aresta<A, V>> arestas;
}
