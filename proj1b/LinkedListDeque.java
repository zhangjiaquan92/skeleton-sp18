public class LinkedListDeque<T> implements Deque<T> {

    private class Addnode {


        private T item;
        private Addnode next;
        private Addnode prev;
        Addnode(T i, Addnode p, Addnode n) {

            item = i;
            next = n;
            prev = p;

        }
    }
    private Addnode sentinel;
    private int size;

    public LinkedListDeque() {

        sentinel = new Addnode(null, null,  null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }
    /*public LinkedListDeque(T x) {
        sentinel = new Addnode(null, null, null);
        sentinel.next = new Addnode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }
*/


    @Override
    public void addFirst(T item) {


        Addnode temp = new Addnode(item, sentinel, sentinel.next);
        sentinel.next = temp;
        sentinel.next.next.prev = temp;
        size++;


    }

    @Override
    public void addLast(T item) {

        Addnode temp = new Addnode(item, sentinel.prev, sentinel);

        sentinel.prev.next = temp;
        sentinel.prev = temp;



        size++;
    }

    @Override
    public boolean isEmpty() {

        if (size == 0) {

            return true;
        }
        return false;

    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void printDeque() {

        Addnode temp = sentinel.next;
        for (int i = 0; i < size; i++) {

            System.out.print(temp.item);
            System.out.print(" ");
            temp = temp.next;
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {

        if (size == 0) {

            return null;
        } else {


            T temp = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;

            return temp;
        }

    }

    @Override
    public T removeLast() {

        if (size == 0) {

            return null;
        } else {

            T temp = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return temp;
        }

    }

    @Override
    public T get(int index) {

        if (index > size - 1 || index < 0) {

            return null;
        }
        Addnode temp = sentinel.next;
        for (int i = 0; i < index; i++) {


            temp = temp.next;


        }
        return temp.item;

    }
    public T getRecursive(int index) {

        if (index > size - 1 || index < 0) {

            return null;
        }
        return gethelper(index, sentinel.next);
    }
    private T gethelper(int index, Addnode a) {

        T temp;
        if (index == 0) {

            temp = a.item;
            return temp;
        } else {

            temp = gethelper(index - 1, a.next);
        }
        return temp;
    }



}
