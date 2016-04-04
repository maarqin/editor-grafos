package src;
/**
 * Autor: Ricardo I. A. e Silva (ricardoinacio@me.com)
 * Data: 23/10/2015
 * Descrição: Editor de grafos para disciplina de laboratório de programação II
 * Licença: Este código fonte é livre e segue a licença BSD, disponivel em 
 *          http://pt.wikipedia.org/wiki/Licen%C3%A7a_BSD
 *          De maneira geral, voce é livre pra fazer o que quiser, mas deve 
 *          gentilmente dar os créditos aos autores originais.
 */

import java.awt.Point;

/**
 * Write a description of class AtribsVertice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VAtribs {
	
    public VAtribs(Point posicao, String nome) {
        this.posicao = posicao;
        this.nome = nome;
    }
    
    public Point getPosicao() { return this.posicao; }
    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    private Point posicao;
    private String nome;
}
