public class LinkedListDeque<Dung> {
    private class Addnode{

        public Dung item;
        public Addnode next;
        public Addnode prev;
        public Addnode(Dung i, Addnode p,Addnode n) {
            item = i;
            next = n;
            prev = p;

        }
    }
    private Addnode sentinel;
    public int size;

    public LinkedListDeque(){
        sentinel = new Addnode(null,null, null);
        sentinel.next = sentinel.prev;
        size = 0;

    }
    public LinkedListDeque(Dung x) {
        sentinel = new Addnode(null, null, null);
        sentinel.next = new Addnode(x,sentinel,sentinel);
        size = 1;
    }



    public void addFirst(Dung item){
        sentinel.next = new Addnode(item,sentinel,sentinel.next);
        size++;

    }
    public void addLast(Dung item){
        sentinel.prev = new Addnode(item,sentinel.prev,sentinel);
        size++;
    }
    public boolean isEmpty(){
        if (sentinel.next == sentinel){
            return true;
        }
        return false;

    }

    public int size(){
        return size;
    }
    public void printDeque(){
        Addnode temp = sentinel.next;
        for(int i=0; i<size;i++){
            System.out.print(temp.item);
            System.out.print(" ");
            temp = temp.next;
        }

    }
    public Dung removeFirst(){
        Dung temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        return temp;



    }
    public Dung removeLast(){
        Dung temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;

        return temp;

    }
    public Dung get(int index){
        if(index > size -1){
            return null;
        }
        Addnode temp = sentinel.next;
        for(int i = 0;i<index; i++){

            temp = temp.next;


        }
        return temp.item;

    }
    public Dung getRecursive(int index){
        if(index > size -1){
            return null;
        }
        return GetHelper(index,sentinel.next);
    }
    private Dung GetHelper(int index, Addnode a){
        if(index==0){
            return a.item;
        }else{
            GetHelper(index-1,a.next);
        }
        return null;
    }



}
