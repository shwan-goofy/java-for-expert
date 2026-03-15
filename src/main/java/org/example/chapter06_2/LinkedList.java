package org.example.chapter06_2;

import java.util.NoSuchElementException;

/**
 * 정적 중첩 클래스로 노드를 구현한 단순 연결 리스트.
 *
 * <p>{@link Node}를 {@code static}으로 선언해 LinkedList 인스턴스에 대한
 * 숨겨진 참조를 제거했다. 노드 수가 많아도 불필요한 외부 참조가 없다.
 */
public class LinkedList<T> {

    /**
     * 정적 중첩 클래스 — LinkedList 인스턴스 없이 독립적으로 생성 가능하다.
     *
     * <p>static이 없다면 모든 Node 인스턴스가 LinkedList.this를 보유해
     * 리스트가 GC 대상이 되어도 살아있는 노드가 리스트를 붙잡아두는 메모리 누수가 생긴다.
     */
    private static class Node<T> {
        private final T data;
        private Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /** 리스트 앞에 요소를 추가한다. */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);  // static 중첩: LinkedList 인스턴스 불필요
        newNode.next = head;
        head = newNode;
        size++;
    }

    /** 리스트 끝에 요소를 추가한다. */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /** 리스트 앞에서 요소를 꺼낸다. */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("리스트가 비어 있습니다.");
        }
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    /** 리스트의 모든 요소를 출력한다. */
    public void printAll() {
        Node<T> current = head;
        StringBuilder sb = new StringBuilder("[");
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" -> ");
            current = current.next;
        }
        sb.append("]");
        System.out.println(sb);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
