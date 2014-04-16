package com.opticalcobra.storybear.main;

public class Ringbuffer<T> {
	private Element read ;
	private Element write;
	
	public Ringbuffer(int size){
		Element first = new Element(null);
		for(int i=1; i<size;i++){
			first.add();
		}
		read = first;
		write = first;	
	}
	
	public T read(){
		T result = read.getValue();
		read = read.getNext();
		return result;
	}
	
	public void write(T value){
		write.setValue(value);
		write = write.getNext();
	}
	
	private class Element{
		private Element next;
		private T value;
		
		public Element(T value){
			this.next = this;
			this.value = value;
		}

		public Element getNext() {
			return next;
		}

		public void setNext(Element next) {
			this.next = next;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
		public void add(){
			Element neu = new Element(null);
			neu.setNext(this.getNext());
			this.setNext(neu);
		}
	}
}
