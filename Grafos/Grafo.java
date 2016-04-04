package Grafos;

import java.util.List;

/**
 * Interface para TAD Grafo.
 * 
 * @author Ricardo I. A. e Silva
 * @version 151020
 */
public interface Grafo<V, A> {
	
    List<Vertice<V>>        vertices();
    List<Aresta<A, V>>      arestas();
    List<Aresta<A, V>>      arestasIncidentes(Vertice<V> v);
    Vertice<V>              oposto(Vertice<V> v, Aresta<A, V> e);
    List<Vertice<V>>        verticesFim(Aresta<A, V> e);
    boolean                 saoAdjacentes(Vertice<V> v, Vertice<V> w);
    V                       mudarPeso(Vertice<V> v, V x);
    A                       mudarPeso(Aresta<A, V> e, A x);
    Vertice<V>              inserirVertice(V x);
    Aresta<A, V>            inserirAresta(Vertice<V> v, Vertice<V> w, A x);
    V                       removerVertice(Vertice<V> v);
    A                       removerAresta(Aresta<A, V> e);
}
