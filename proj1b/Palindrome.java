public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> temp = new ArrayDeque<>();
        char[] chr = word.toCharArray();
        for (char i : chr) {
            temp.addLast(i);

        }
        return temp;
    }

    /*
    public boolean isPalindrome(String word) {

        char[] chr = word.toCharArray();
        int j = chr.length - 1;
        for (int i = 0 ; i <= chr.length / 2 ; i++) {
            if (chr[i] != chr[j]) {
                return false;
            }else {
                j--;
            }

        }
        return true;
    }
*/
    public Boolean isPalindrome(String word) {
        Deque d = this.wordToDeque(word);
        while (!(d.size() == 0 || d.size() == 1)) {

            if (d.removeFirst() == d.removeLast()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = this.wordToDeque(word);
        while (!(d.size() == 0 || d.size() == 1)) {

            if (cc.equalChars((Character) d.removeFirst(), (Character) d.removeLast())) {
                continue;
            } else {
                return false;
            }
        }
        return true;

    }
}
