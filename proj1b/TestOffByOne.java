import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your
    CharacterComparator interface and OffByOne class. **/
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testequalChars() {



        boolean x = offByOne.equalChars('b', 'a');
        boolean y = offByOne.equalChars('a', 'e');
        boolean z = offByOne.equalChars('9', ':');
        boolean l = offByOne.equalChars('\\', ']');
        boolean m = offByOne.equalChars('b', 'A');
        boolean x2 = offByOne.equalChars('a', 'b');
        boolean cc = offByOne.equalChars('a', 'a');
        boolean bb = offByOne.equalChars('%', '&');
        boolean mn = offByOne.equalChars('A', 'b');


        assertFalse("input: a and e, output:true, expect: false ", y);
        assertTrue("input: b and a, output:false, expect: true ", x);
        assertTrue("input: b and a, output:false, expect: true ", z);
        assertTrue("input: \\ and ], output:false, expect: true ", l);
        assertFalse("input: b and A, output:true, expect: false ", m);
        assertTrue("input: a and b, output:false, expect: true ", x2);
        assertFalse("input: a and a, output:true, expect: false ", cc);
        assertTrue("input: % and &, output:false, expect: true ", bb);
        assertFalse("input: a and e, output:true, expect: false ", mn);


    }
}
