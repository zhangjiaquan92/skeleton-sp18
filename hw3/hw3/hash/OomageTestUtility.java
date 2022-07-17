package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        double upLim = oomages.size() / 2.5;
        double lowLim = oomages.size() / 50.0;

        int[] buc = new int[M];
        for (int c = 0; c < M; c++) {
            buc[c] = 0;
        }

        int i = 0;
        for (Oomage temp : oomages) {
            int bukN = (temp.hashCode() & 0x7FFFFFFF) % M;
            buc[bukN] = buc[bukN] + 1;
            i++;
        }
        for (int j : buc) {
            if ((j > upLim) || (j < lowLim)) {
                return false;
            }
        }
        return true;
    }
}
