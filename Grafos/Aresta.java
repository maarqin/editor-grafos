package Grafos;


/**
 * Write a description of class Aresta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aresta<A, V> {
	
    public Aresta(Vertice<V> a, Vertice<V> b, A atributos) {
        this.atributos = atributos;
        this.a = a;
        this.b = b;
    }
    
    public A getAtributos() { return this.atributos; }
    public void setAtributos(A atributos) { this.atributos = atributos; }
    public Vertice<V> getVerticeA() { return this.a; }
    public Vertice<V> getVerticeB() { return this.b; }
    
    private A atributos;
    private Vertice<V> b;
    private Vertice<V> a;
    
}
