package ru.otus.generics;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

  private final int DefaultIncreaseByTheNumberOfElements = 10;
  private int size = 0;
  Object[] elementData;

  public DIYArrayList() {
    elementData = new Object[DefaultIncreaseByTheNumberOfElements];
  }

  public DIYArrayList(int size) {
    elementData = new Object[this.size = size];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  @Override
  public <T> T[] toArray(T[] a) {
    if (a.length < size)
      return (T[]) Arrays.copyOf(elementData, size, a.getClass());
    System.arraycopy(elementData, 0, a, 0, size);
    if (a.length > size)
      a[size] = null;
    return a;
  }

  @Override
  public boolean add(E e) {
    elementData = increase();
    elementData[size] = e;
    size += 1;

    return true;
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() { throw new UnsupportedOperationException(); }

  @Override
  public E get(int index) {
    if (index < 0 || index > size())
      throw new ArrayIndexOutOfBoundsException();
    return (E) elementData[index];
  }

  @Override
  public E set(int index, E element) {
    Objects.checkIndex(index, size);
    E oldValue = (E) elementData[index];
    elementData[index] = element;
    return oldValue;
  }

  @Override
  public void add(int index, E element) { throw new UnsupportedOperationException(); }

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator() {
    return new ListIter(0);
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return new ListIter(index);
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  private Object[] increase(){
    if (size == elementData.length)
      return Arrays.copyOf(elementData, size + DefaultIncreaseByTheNumberOfElements);

    return elementData;
  }

  private class Iter implements Iterator<E> {

    int cursor;
    int lastRet = -1;

    @Override
    public boolean hasNext() {
      return cursor != size;
    }

    @Override
    public E next() {
      if (cursor >= size)
        throw new NoSuchElementException();

      lastRet = cursor;
      cursor = cursor + 1;

      return (E) elementData[lastRet];
    }
  }

  private class ListIter extends Iter implements ListIterator<E> {

    public ListIter(int i) {
      super();
      cursor = i;
    }

    @Override
    public boolean hasPrevious() {
      return cursor != 0;
    }

    @Override
    public E previous() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int nextIndex() {
      return cursor;
    }

    @Override
    public int previousIndex() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {

    }

    @Override
    public void set(E e) {
      if (lastRet < 0)
        throw new IllegalStateException();

      try {
        DIYArrayList.this.set(lastRet, e);
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }

    @Override
    public void add(E e) {
      throw new UnsupportedOperationException();
    }
  }
}
