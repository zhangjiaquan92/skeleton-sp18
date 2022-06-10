import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {

        boolean d = palindrome.isPalindrome("racecar");
        //boolean f = palindrome.isPalindrome("cart");
        //boolean e = palindrome.isPalindrome("horse");
        //boolean o = palindrome.isPalindrome("a");
        assertTrue("input: racecar, output:false, expect: true ", d);
       // assertFalse("input: horse, output:true, expect: false ",e);
       // assertFalse("input: cart, output:true, expect: false ",f);
        //assertTrue("input: a, output:false, expect: true ",o);
        //System.out.println("test cases passed: racecar, horse, cart, a");




    }

    @Test
    public void testnewPalindrome() {
        OffByN offBy5 = new OffByN(5);
        boolean d = offBy5.equalChars('a', 'f');
        boolean e = offBy5.equalChars('f', 'h');
        boolean f = offBy5.equalChars('f', 'a');

        assertTrue("input: a and f, output:false, expect: true ", d);
        assertFalse("input: f and h, output:true, expect: false ", e);
        assertTrue("input: f and a, output:false, expect: true ", f);





    }
}
