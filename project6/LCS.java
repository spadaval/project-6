package project6;

class StringUtils {
  static boolean contains(String s, char c) {
    return s.contains(String.valueOf(c));
  }

  static String reversed(String s) {
    return new StringBuilder(s).reverse().toString();
  }
}

class Debug {
  static boolean DEBUG = false;

  static void printHeader(String a, String b) {
    if (!DEBUG)
      return;
    Debug.printf("\nTable for '%s' x '%s'\n", a, b);
    System.out.print("  ");
    for (int i = 0; i < b.length(); i++) {
      System.out.print(b.charAt(i) + " ");
    }
    System.out.println();
  }

  static void printRow(int[][] k) {
    if (!DEBUG)
      return;
    for (int i = 0; i < k[0].length; i++) {
      System.out.print(k[1][i] + " ");
    }
    System.out.println();
  }

  static void printf(String format, Object... args) {
    if (DEBUG)
      System.out.printf(format, args);
  }

}

public class LCS {

  void copyLineUp(int[][] k) {
    for (int i = 0; i < k[0].length; i++) {
      k[0][i] = k[1][i];
    }
  }

  int[] ll(String a, String b) {
    // Debug.printf("Finding ll for '%s' and '%s'\n", a, b);
    int[][] k = new int[2][b.length() + 1];

    Debug.printHeader(a, b);

    for (int posInA = 0; posInA < a.length(); posInA++) {
      copyLineUp(k);
      for (int posInB = 0; posInB < b.length(); posInB++) {
        if (a.charAt(posInA) == b.charAt(posInB)) {
          k[1][posInB + 1] = k[0][posInB] + 1;
        } else {
          k[1][posInB + 1] = Integer.max(k[0][posInB + 1], k[1][posInB]);
        }
      }
      Debug.printRow(k);

    }
    return k[1];
  }

  String lcs(String a, String b) {
    // Debug.printf("Finding lcs for '%s' and '%s'\n", a, b);

    if (b.length() == 0) {
      return "";
    }
    if (a.length() == 0) {
      return "";
    }
    if (a.length() == 1) {
      if (StringUtils.contains(b, a.charAt(0))) {
        return String.valueOf(a.charAt(0));
      } else
        return "";
    }

    int i = a.length() / 2;

    Debug.printf("\n\n\n");
    Debug.printf("Split '%s'(a) into '%s':'%s'", a, a.substring(0, i),
        StringUtils.reversed(a).substring(0, a.length() - i));

    int[] l1 = ll(a.substring(0, i), b);
    int[] l2 = ll(StringUtils.reversed(a).substring(0, a.length() - i), StringUtils.reversed(b));

    int max = Integer.MIN_VALUE;
    int maxIndex = 0;
    for (int j = 0; j < l1.length; j++) {
      int candidate = l1[j] + l2[b.length() - j];
      if (candidate > max) {
        max = candidate;
        maxIndex = j;
      }
    }
    Debug.printf("Max LCS length is %d at index %d\n", max, maxIndex);

    int splitBAt = maxIndex;

    Debug.printf("\n'%s':'%s'\n'%s':'%s'\n",
        a.substring(0, i),
        b.substring(0, splitBAt),
        a.substring(i, a.length()),
        b.substring(splitBAt, b.length()));

    Debug.printf("\n\n\n");
    String c1 = lcs(a.substring(0, i), b.substring(0, splitBAt));
    String c2 = lcs(a.substring(i, a.length()), b.substring(splitBAt, b.length()));

    Debug.printf("\n\n'%s':'%s' ---> '%s'\n'%s':'%s' ---> '%s'\n",
        a.substring(0, i),
        b.substring(0, splitBAt),
        c1,
        a.substring(i, a.length()),
        b.substring(splitBAt, b.length()),
        c2);

    Debug.printf("LCS['%s', '%s']=%s \n",
        a.toString(),
        b.toString(),
        c1 + c2);

    return c1 + c2;
  }

  public String find(String A, String B) {
    return lcs(A, B);
    // Implement the Hirschberg LCS algorithm
  }

}
