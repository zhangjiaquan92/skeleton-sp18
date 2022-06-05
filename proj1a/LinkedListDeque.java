public class LinkedListDeque<Dung> {
    private class Addnode{

        public Dung item;
        private Addnode next;
        private Addnode prev;
        public Addnode(Dung i, Addnode p, Addnode n) {
            item = i;
            next = n;
            prev = p;

        }
    }
    private Addnode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new Addnode(null,null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }
    public LinkedListDeque(Dung x) {
        sentinel = new Addnode(null, null, null);
        sentinel.next = new Addnode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }



    public void addFirst(Dung item){

        Addnode temp = new Addnode(item, sentinel, sentinel.next);
        sentinel.next = temp;
        sentinel.next.next.prev = temp;
        size++;


    }
    public void addLast(Dung item){
        Addnode temp = new Addnode(item, sentinel.prev, sentinel);

        sentinel.prev.next = temp;
        sentinel.prev = temp;



        size++;
    }
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;

    }

    public int size(){
        return size;
    }
    public void printDeque(){
        Addnode temp = sentinel.next;
        for (int i = 0; i < size; i++){
            System.out.print(temp.item);
            System.out.print(" ");
            temp = temp.next;
        }
        System.out.println("");
    }
    public Dung removeFirst(){
        Dung temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;

        return temp;



    }
    public Dung removeLast(){
        Dung temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;

    }
    public Dung get(int index){
        if (index > size - 1 || index < 0){
            return null;
        }
        Addnode temp = sentinel.next;
        for (int i = 0; i < index; i++){

            temp = temp.next;


        }
        return temp.item;

    }
    public Dung getRecursive(int index){
        if (index > size - 1 || index < 0){
            return null;
        }
        return GetHelper(index, sentinel.next);
    }
    private Dung GetHelper(int index, Addnode a){
        Dung temp;
        if (index == 0){
            temp = a.item;
            return temp;
        }
        else {
            temp = GetHelper(index - 1, a.next);
        }
        return temp;
    }



}
