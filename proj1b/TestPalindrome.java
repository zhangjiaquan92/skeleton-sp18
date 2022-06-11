import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offBy5 = new OffByN(5);
    static CharacterComparator offBy1 = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("abcdetrv");
        String actual = "";
        for (int i = 0; i < "abcdetrv".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("abcdetrv", actual);
    }



    @Test
    public void testisPalindrome() {

        boolean d = palindrome.isPalindrome("racecar");
        boolean f = palindrome.isPalindrome("cart");
        boolean e = palindrome.isPalindrome("");
        boolean o = palindrome.isPalindrome("a");
        boolean p = palindrome.isPalindrome("abcba");
        System.out.println(p);
        assertTrue("input: racecar, output:false, expect: true ", d);
        assertFalse("input: , output:true, expect: false ", e);
        assertFalse("input: cart, output:true, expect: false ", f);
        assertTrue("input: a, output:false, expect: true ", o);
        assertFalse("input: abcba, output:true, expect: false ", p);
        //System.out.println("test cases passed: racecar, horse, cart, a");




    }

    @Test
    public void testnewPalindrome() {
        //OffByN offBy5 = new OffByN(5);
        boolean d = offBy5.equalChars('a', 'f');
        boolean e = offBy5.equalChars('f', 'h');
        boolean f = offBy5.equalChars('f', 'a');
        boolean g = offBy5.equalChars('F', 'a');

        assertTrue("input: a and f, output:false, expect: true ", d);
        assertFalse("input: f and h, output:true, expect: false ", e);
        assertTrue("input: f and a, output:false, expect: true ", f);
        assertFalse("input: F and a, output:true, expect: false ", g);




    }

    @Test
    public void testIsPalindrome2() {
        //OffByOne offBy1 = new OffByOne();
        assertTrue(palindrome.isPalindrome("aba"));
        assertFalse(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("bbc"));
        assertTrue(palindrome.isPalindrome("m"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertFalse(palindrome.isPalindrome("abd"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("bdcdb"));
    }

    @Test
    public void testIsPalindrome3() {
        //OffByN offBy5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("totty", offBy5));
        assertFalse(palindrome.isPalindrome("", offBy5));
        assertTrue(palindrome.isPalindrome("cafh", offBy5));
        assertTrue(palindrome.isPalindrome("c", offBy5));
        assertFalse(palindrome.isPalindrome("abcd", offBy5));
    }

}
