import org.junit.Test;
//import org.junit.internal.runners.model.EachTestNotifier;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {

    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> studout = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> soluout = new ArrayDequeSolution<>();
        int range = 10;
        int check = 2;
        String out = "";
        for (int i = 0; i < 10; i++) {
            int randomten = StdRandom.uniform(range);
            int chk = StdRandom.uniform(check);
            if (chk == 1) {
                studout.addFirst(randomten);
                soluout.addFirst(randomten);
                Integer actual = studout.get(0);
                Integer expected = soluout.get(0);
                out = out + "addFirst(" + randomten + ")\n";
                assertEquals(out, expected, actual);


            } else {
                studout.addLast(randomten);
                soluout.addLast(randomten);
                Integer len = soluout.size() - 1;
                Integer actual = studout.get(len);
                Integer expected = soluout.get(len);
                out = out + "addLast(" + randomten + ")\n";
                assertEquals(out, expected, actual);
            }


        }
        for (int i = 0; i < 10; i++) {
            int chkk = StdRandom.uniform(check);
            if (chkk == 1) {
                Integer actual = studout.removeLast();
                Integer expected = soluout.removeLast();
                out = out + "removeLast()";
                assertEquals(out + ", student was " + actual + ", correct was " + expected,
                        expected, actual);
                out = out + "\n";
            } else {
                Integer actual = studout.removeFirst();
                Integer expected = soluout.removeFirst();
                out = out + "removeFirst()";
                assertEquals(out + ", student was " + actual + ", correct was " + expected,
                        expected, actual);
                out = out + "\n";

            }


        }






    }


}
