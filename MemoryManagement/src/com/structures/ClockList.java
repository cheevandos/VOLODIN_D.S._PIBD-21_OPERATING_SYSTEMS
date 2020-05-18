package com.structures;

public class ClockList<T> {
	
	private Node<T> head;
	private int size;
	
	public ClockList() {
		head = null;
		size = 0;
	}

	public void add(T element) {
		if (head == null) {
			head = new Node<T>(element);
			head.getNext().setNext(head);
			size++;
		} else {
			Node<T> currentNode = head;
			while (currentNode.getNext() != head) {
				currentNode = currentNode.getNext();
			}
			Node<T> nextNode = new Node<T>(element);
			nextNode.setNext(head);
			currentNode.setNext(nextNode);	
			size++;
		}
	}
	
	public void replace(int index, T element) {
		if (index > 0) {
			Node<T> oldNode = getNode(index);
			Node<T> prevNode = getNode(index - 1);
			Node<T> newNode = new Node<T>(element);
			prevNode.setNext(newNode);
			newNode.setNext(oldNode.getNext());
			oldNode = null;
		}
	}
	
	public T getHead() {
		return head.getData();
	}
	
	public T getElement(int index) {
		return getNode(index).getData();
	}
	
	public Node<T> getNode(int index) {
		if (index < size && index >= 0) {
			int currentIndex = 0;
			Node<T> currentNode = head;
			while (currentIndex < index) {
				currentIndex++;
				currentNode = currentNode.getNext();
			}
			return currentNode;
		} else {
			throw new IllegalArgumentException("Index out of bounds");
		}
	}
}
