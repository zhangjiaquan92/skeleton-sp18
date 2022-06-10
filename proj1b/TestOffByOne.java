import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testequalChars() {


        OffByOne obo = new OffByOne();
        boolean x = obo.equalChars('b', 'a');
        boolean y = obo.equalChars('a', 'e');
        System.out.println(x);


    }
}
