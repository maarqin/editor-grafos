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

import javax.swing.event.MouseInputAdapter;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import Grafos.*;

class DesenhistaGrafo extends JPanel {	

    /**
     * Campos privados
     */
    private static final Integer COMP_MARGEM_TXT = 10;
    private static final Integer ALT_LINHA_TXT = 15;
    private static final Integer VERT_DIAMETRO = 12;
    private static final Integer VERT_RAIO = VERT_DIAMETRO / 2;
    private static final BasicStroke VERT_PERIMETRO = new BasicStroke(3);

    private Grafo<VAtribs, AAtribs> grafo = null;
    private ModoEdicao modoEdt = ModoEdicao.NENHUM;
    private Vertice<VAtribs> vertSobMouse;
    private Aresta<AAtribs, VAtribs> arestaSobMouse;
    
    private Point fPLine1 = new Point();
    private Point fPLine2 = new Point();
    
    public enum ModoEdicao {
        NENHUM, ADICIONAR_VERTICE, MOVER_VERTICE, MOVENDO_VERTICE, LIGAR_VERTICES, LIGANDO_VERTICES, REMOVER_ITENS, EDITAR_DADOS
    }
    
    public DesenhistaGrafo() {
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(Color.WHITE);
        
        MouseDesenhista mouse = new MouseDesenhista();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }
    
    /**
     * Método responsável por desenhar o arranjo, além de outros elementos gráficos secundários e
     * auxiliares. É aqui que os alunos deverão trabalhar. Porém devem evitar de fazerem um código
     * demasiadamente grande, devem separar em métodos auxiliares (subrotinas) sempre que possível.
     * 
     * @param g Pincel do desenhista.
     * @see java.awt.Graphics, java.swing.Graphics2D
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Dimension dim = getSize();
        StringBuilder txtDim = new StringBuilder("Dimensões da tela: ");
        txtDim.append(dim.width).append(" X ").append(dim.height); 
        g2d.drawString(txtDim.toString(), COMP_MARGEM_TXT, ALT_LINHA_TXT);  

        // Desenhar os vertices do grafo aqui
        for (Vertice<VAtribs> v : grafo.vertices()) {
            VAtribs av = v.getAtributos();
            Point pos = av.getPosicao();
            
            Stroke pontaPadrao = g2d.getStroke();
            g2d.setStroke(VERT_PERIMETRO);
            g2d.drawOval(pos.x-VERT_RAIO, pos.y-VERT_RAIO, VERT_DIAMETRO, VERT_DIAMETRO);
            if ( v.equals(vertSobMouse) ) {
                g2d.setColor(Color.YELLOW);
            }
            g2d.fillOval(pos.x-VERT_RAIO, pos.y-VERT_RAIO, VERT_DIAMETRO, VERT_DIAMETRO);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(pontaPadrao);
            
        	g2d.drawString(av.getNome(), pos.x, pos.y + VERT_DIAMETRO + ALT_LINHA_TXT);
        }
        
        g2d.setStroke(VERT_PERIMETRO);
        // Desenhar as arestas do grafo aqui
        for (Aresta<AAtribs, VAtribs> a : grafo.arestas()) {
        	Point p1 = a.getVerticeA().getAtributos().getPosicao();
        	Point p2 = a.getVerticeB().getAtributos().getPosicao();
        	
        	if( a.equals(arestaSobMouse) ){
                g2d.setColor(Color.RED);
        	}
        	g2d.drawLine(p1.x, p1.y, p2.x, p2.y);	
            g2d.setColor(Color.BLACK);
            g2d.drawString(a.getAtributos().getWeight().toString(),
            		((p2.x + p1.x) / 2) + ALT_LINHA_TXT, ((p2.y + p1.y) / 2) + ALT_LINHA_TXT);
		}
        
        // Sempre desenha a linha fake
    	g2d.drawLine(fPLine1.x, fPLine1.y, fPLine2.x, fPLine2.y);
    }

    public void adicionarVertice(Point posicao) {
        VAtribs novo = new VAtribs(posicao, "V" + (grafo.vertices().size() + 1));
        grafo.inserirVertice(novo);
        this.repaint();
    }
    
    public void moverVertice(Point posicao) {
        Point vPos = this.vertSobMouse.getAtributos().getPosicao();
        vPos.move(posicao.x, posicao.y);
        this.repaint();
    }

    private Vertice<VAtribs> sobreVertice(Point objPos) {
        for (Vertice<VAtribs> v : grafo.vertices()) {
            Point vPos = v.getAtributos().getPosicao();
            if (vPos.distance(objPos) <= VERT_RAIO) {
                return v;
            }
        }
        return null;
    }
    
    private Aresta<AAtribs, VAtribs> sobreAresta(Point point) {
    	for (Aresta<AAtribs, VAtribs> a : grafo.arestas()) {
			Point pArestaA = a.getVerticeA().getAtributos().getPosicao();
			Point pArestaB = a.getVerticeB().getAtributos().getPosicao();
			
			int dis = (int)Line2D.ptSegDist(pArestaA.x, pArestaA.y, pArestaB.x, pArestaB.y, point.x, point.y);
			if( dis <= 3 ) return a;
		}
    	return null;
    }
    
    private class MouseDesenhista extends MouseInputAdapter {
    	
    	private Vertice<VAtribs> v1;
    	
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                switch (modoEdt) {
                    case ADICIONAR_VERTICE:
                        adicionarVertice(e.getPoint());
                        break;
                    default:
                    	// Editar vertice ou aresta
                    	if( vertSobMouse != null  ) {
                    		if( modoEdt == ModoEdicao.REMOVER_ITENS ){
                    			removerVertice(vertSobMouse);
    							repaint();
                    		} else if ( modoEdt == ModoEdicao.EDITAR_DADOS ) {
	                    		String oldNome = vertSobMouse.getAtributos().getNome();
	            				String nome = JOptionPane.showInputDialog("Editando nome(vértice)", oldNome);	
	            				
	            				if( nome != null && 
	            						!nome.equals(oldNome) ){
	            					grafo.mudarPeso(vertSobMouse, new VAtribs(vertSobMouse.getAtributos().getPosicao(), nome));
	            					vertSobMouse.getAtributos().setNome(nome);
	    							repaint();
	            				}
                    		}
                    	} else if( arestaSobMouse != null ){
                    		if( modoEdt == ModoEdicao.REMOVER_ITENS ){
                    			removerAresta(arestaSobMouse);
    							repaint();
                    		} else if ( modoEdt == ModoEdicao.EDITAR_DADOS ) {
	                     		String oldPeso = String.valueOf(arestaSobMouse.getAtributos().getWeight());
	             				String peso = JOptionPane.showInputDialog("Editando peso(aresta)", oldPeso);	
	 							
	             				if( peso != null && 
	             						!peso.equals(oldPeso) ){
	             					grafo.mudarPeso(arestaSobMouse, new AAtribs(Integer.parseInt(peso)));
	     							repaint();
	             				}
                    		}
                     	}
                    
                }
            }
        }
        
        private void removerAresta(Aresta<AAtribs, VAtribs> arestaSobMouse) {
			grafo.removerAresta(arestaSobMouse);
		}

		private void removerVertice(Vertice<VAtribs> vertSobMouse) {
			grafo.removerVertice(vertSobMouse);
		}

		@Override
        public void mouseMoved(MouseEvent e) {
            Point mousePos = e.getPoint();
            
            Vertice<VAtribs> novoSob = sobreVertice(mousePos);
            if (vertSobMouse != novoSob) {
                vertSobMouse = novoSob;
                repaint();
            }
            
            Aresta<AAtribs, VAtribs> novaAresta = sobreAresta(mousePos);
            if( arestaSobMouse != novaAresta ){
            	arestaSobMouse = novaAresta;
            	repaint();
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
        	mouseMoved(e);        	
        	if( vertSobMouse != null ){
				switch (modoEdt) {
					case LIGANDO_VERTICES:
						String peso = JOptionPane.showInputDialog("Peso da aresta");	

						if( peso != null && !peso.equals("") ) {
							grafo.inserirAresta(v1, vertSobMouse, new AAtribs(Integer.parseInt(peso)));
						}
						repaint();
						break;
				}
        	}
        	fPLine1 = new Point();
        	fPLine2 = new Point();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {   	
            if( vertSobMouse != null ){
                switch(modoEdt){
                    case MOVER_VERTICE:
                        modoEdt = ModoEdicao.MOVENDO_VERTICE;
                        break;
                    case LIGAR_VERTICES:
                    	modoEdt = ModoEdicao.LIGANDO_VERTICES;
                    	break;
                }
            }
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
        	if( vertSobMouse != null ){
	            switch (modoEdt) {
	                case MOVENDO_VERTICE:
	                    moverVertice(e.getPoint());
	                    break;
	                case LIGANDO_VERTICES:
	                	v1 = vertSobMouse;
	                	
	                	fPLine1.setLocation(v1.getAtributos().getPosicao());
	                	fPLine2.setLocation(e.getPoint());
						repaint();
	                	break;
	            }
        	}
        }
    }
    
    private void BFS(Vertice<VAtribs> root, Aresta<AAtribs, VAtribs> aresta){	
    	
//		Queue<Vertice<VAtribs>> queue = new Queue<>();
//
//    	HashMap<Vertice<VAtribs>, Boolean> marked = new HashMap<>();
//		List<Integer> distTo = new ArrayList<>();
//		List<Aresta<AAtribs, VAtribs>> edgeTo = new ArrayList<>();
//        
//        for (Vertice<VAtribs> vertice : grafo.vertices() ) {
//        	marked.put(root, true);	
//        	distTo.add(0);
//            queue.enqueue(root);
//        }
//        
//        while ( queue.hasItems() ) {
//        	Vertice<VAtribs> v = queue.dequeue();
//        	
//            for ( Aresta<AAtribs, VAtribs> w : grafo.oposto(v, e)(v) ) {
//                if ( !marked.get(w) ) {
//                	edgeTo.add(w);
//                	distTo.add(w.getAtributos().getWeight() + 1);
//                	marked.put(w, true);
////                    marked[w] = true;
//                	queue.enqueue(w);
//                }
//            }
//        }
    }

    public void setGrafo(Grafo<VAtribs, AAtribs> grafo) { this.grafo = grafo; }
    public void setModoEdicao(ModoEdicao novoModo) { this.modoEdt = novoModo; }

}