package Grafos;


/**
 * Write a description of class Vertice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vertice<V> {
		
    public Vertice(V atributos) {
        this.atributos = atributos;
    }
    
    public V getAtributos() { return this.atributos; }
    public void setAtributos(V atributos) { this.atributos = atributos; }
    
    private V atributos;

}
