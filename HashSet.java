package jrJava.shortestPath_1;

import java.util.Iterator;
import java.util.Set;

public class HashSet<E> {

	private Entry<E>[] entries;
	
	
	public HashSet(int size) {
		entries = new Entry[size];
	}
	 
	public HashSet(){
		this(100);
	}
	
	
	public E add(E obj){
		// 1. create a new entry that contains the 'obj'.
		Entry<E> entry = new Entry(obj);
		// 2. Make it arrive at the a hashBucket.
		int index = obj.hashCode()%entries.length;
		Entry<E> p = entries[index];
		
		// 3. Traverse to the end of the hashBucket (linked list)
		//    While traversing, check any duplicate entry.
		
		if(p==null){
			entries[index] = entry;
			return null;
		}
		else if(p.obj.equals(obj)){
			entry.next = p.next;
			entries[index] = entry;
			return p.obj;
		}
		
		Entry<E> c = p.next;
		while(c!=null) {
			if(c.obj.equals(obj)){
				entry.next = c.next;
				p.next = entry;
				return c.obj;
			}
			p = c;
			c = c.next;
		}
		
		p.next = entry;
		return null;
	}
	
	
	
	public E get(E searchKey){
		int index = searchKey.hashCode()%entries.length;
		Entry<E> c = entries[index];
		
		while(c!=null){
			if(c.obj.equals(searchKey)) return c.obj;
			c = c.next;
		}
		return null;
	}
	
	
	public void display(){
	
		for(int i=0; i<entries.length; i++) {
			System.out.print(i + ": ");
			Entry<E> c = entries[i];
			
			while(c!=null){
				System.out.print(c.obj + " -> ");
				c = c.next;
			}
			System.out.println(); 
		}
	}
	
	
	public Iterator<E> iterator(){
		return new IterImpl();
	}
	
	
	
	private class IterImpl implements Iterator<E> {
		
		private int index = 0;
		private Entry<E> next = entries[index];
		
		public IterImpl(){
			while(next==null){
				index++;
				if(index>=entries.length) break;
				next = entries[index];
			}
		}
		
	
		public boolean hasNext(){ 
			while(next==null){
				index++;
				if(index>=entries.length) break;
				next = entries[index];
			}
			return next!=null;
		}
		
		
		public E next(){
			E toReturn = next.obj;
			next = next.next;
			return toReturn;
		}
		
	}
	
	
	
	
	private static class Entry<E> { // like LinkNode
		
		public E obj;
		public Entry<E> next;
		
		public Entry(E obj){
			this.obj = obj;
		}

	}
	
	
}
