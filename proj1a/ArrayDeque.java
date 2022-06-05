public class ArrayDeque<T> {

     private T[] item;
    private int size;
    private int next;
    private int prev;

    /** Creates an empty list. */
    public ArrayDeque() {

         item = (T[]) new Object[8];
        size = 0;
        next = 0;
        prev = 7;

    }

    /** Inserts X into the back of the list.
    public void insertBack(int x) {
        item[prev] = x;
        prev--;
        size++;
    }*/

    /** Returns the item from the back of the list.
    public int getBack() {
        return item[prev-1];
    }*/
    /** Gets the ith item in the list (0 is the front). */
    public T get(int i) {

        if (i < 0 || i >= size){

             return null;
        } else {

             if (prev > next){

                 if (i + prev + 1 >= item.length){

                      return item[prev + i + 1 - item.length];
                }
                return item[prev + i + 1];
            }
            return item[prev + 1 + i];
        }
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    /*public Dung deleteBack() {
        if(isEmpty()){
            return null;
        }else if(next == 0){
            next = item.length;
            Dung temp = item[item.length];
            item[next] = null;
            size--;
            return temp;

        }
        size--;
        Dung t = item[next-1];
        next--;
        item[next] = null;

        return t;
    }*/

    /** Returns the number of items in the list. */
    public int size() {

         return size;
    }

    public void addFirst(T t){


        item[prev] = t;
        size++;
        if (prev == 0){
            prev = item.length - 1;
        } else {
            prev--;
        }

        resizechk();
    }

    public void addLast(T t){


        item[next] = t;
        size++;
        if (next == item.length - 1){

            next = 0;
        } else {
            next++;
        }
        resizechk();

    }

    public boolean isEmpty() {

        /*if (next - prev == 1 || (prev == item.length - 1 && next == 0)){
            return true;
        }
        return false;

    }*/

        if (size == 0) {

            return true;
        }
            return false;
        }



    public void printDeque(){

        int t = prev + 1;
        int b = 0;
        if (prev > next){

            while (t < item.length){

                System.out.print(item[t]);
                System.out.print(" ");
                t++;
            }
            while (b < next){

                System.out.print(item[b]);
                System.out.print(" ");
                b++;
            }
        } else {

            for (b = 0; b < size; b++){

                System.out.print(item[b + prev + 1]);
                System.out.print(" ");
            }
        }
        System.out.println("");
    }
    public T removeFirst(){

        T t;
        if (size == 0){

            return null;
        } else {

            if (prev == item.length - 1){

                prev = 0;
                t = item[0];
            } else {

                prev++;
                t = item[prev];
            }
            size--;
            resizechk();
            return t;
        }
    }

    public T removeLast(){

        T t;
        if (size == 0){

            return null;
        } else {

            if (next == 0) {

                next = item.length - 1;
                t = item[next];
            } else {

                next--;
                t = item[next];
            }
            size--;
            resizechk();
            return t;
        }
    }

    private void resizechk(){

        /* true means list size up, false means list size down.*/
        if (size > 0.75 * item.length && item.length >= 16){

            this.resize(true);
        } else if (size < 0.25 * item.length && item.length >= 32){

            this.resize(false);
        }
        if (next + 1 == prev || (prev == 0 && next == item.length - 1)){

            this.resize(true);
        }

    }

    private void resize(boolean check){

        /*check == true, the array needs resize up*/

        if (check){

            T[] te = (T[])new Object[item.length * 2];
            if (prev < next){

                System.arraycopy(item, prev + 1, te, prev + 1, size);
                item = te;
            } else {

                System.arraycopy(item, 0, te, 0, next);
                System.arraycopy(item, prev + 1, te, te.length - (item.length - prev - 1),
                        item.length - prev - 1);
                prev = te.length - (item.length - prev);
                item = te;
            }


        } else {

            /*down side the array*/
            T[] te = (T[])new Object[item.length / 2];
            if (prev < next) {


                System.arraycopy(item, prev + 1, te, 0, size);
                prev = te.length - 1;
                next = size;
                item = te;
            } else {

                System.arraycopy(item, 0, te, 0, next);
                System.arraycopy(item, prev + 1, te, te.length - (item.length - prev - 1), item.length - prev - 1);
                prev = te.length - (item.length - prev);
                item = te;
            }


        }

    }

}
