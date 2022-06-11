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


        OffByOne obo = new OffByOne();
        boolean x = obo.equalChars('b', 'a');
        boolean y = obo.equalChars('a', 'e');
        boolean z = obo.equalChars('9', ':');
        boolean l = obo.equalChars('\\', ']');

        int w = '0';

        System.out.println(w);

        //System.out.println(x);
        assertFalse("input: a and e, output:true, expect: false ", y);
        assertTrue("input: b and a, output:false, expect: true ", x);
        assertTrue("input: b and a, output:false, expect: true ", z);
        assertTrue("input: \\ and ], output:false, expect: true ", l);


    }
}
