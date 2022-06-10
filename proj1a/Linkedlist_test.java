public class Linkedlist_test {
    public static void main(String[] args) {
        LinkedListDeque<Integer> test =  new LinkedListDeque();
        test.addLast(8);
        test.printDeque();
        test.addLast(99);
        test.printDeque();
        System.out.println("add last check ");
        test.addLast(44);
        System.out.println(test.size());
        test.printDeque();
        test.addFirst(77);
        test.printDeque();
        test.addFirst(74);
        test.printDeque();
        test.removeFirst();
        test.printDeque();
        System.out.println(test.getRecursive(5));



    }
}
