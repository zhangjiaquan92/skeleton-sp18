public class OffByN implements CharacterComparator {
    private int temp;

    public OffByN(int i) {
        temp = i;
    }


    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == temp || y - x == temp) {
            return true;
        }
        return false;
    }
}
