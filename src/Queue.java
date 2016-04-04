package src;

import java.util.LinkedList;

/**
 * @author thomaz
 * @version 2015-06-09
 * @param <E>
 */
class Queue<E> {
	
	private LinkedList<E> list = new LinkedList<E>();
	
	public void enqueue(E e) {
		list.addLast(e);
	}
	
	public E dequeue() {
		return list.poll();
	}

	public boolean hasItems() {
		return !list.isEmpty();
	}

	public int size() {
		return list.size();
	}
}
