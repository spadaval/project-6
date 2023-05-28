package project6;

import java.util.Arrays;

class Slice {
  private String str;
  private int start;
  private int end;

  Slice(String str) {
    this.str = str;
    this.start = 0;
    this.end = str.length();
  }

  private Slice(String str, int start, int end) {
    assert end <= str.length();
    assert start >= 0;
    this.str = str;
    this.start = start;
    this.end = end;
  }

  void assertValid(int i) {
    assert i < length();
    assert i >= 0;
  }

  Slice slice(int start, int end) {
    assert end <= str.length();
    assert start >= 0;
    return new Slice(str, this.start + start, this.start + end);
  }

  Slice reverse() {
    return new Slice(new StringBuilder(str).reverse().toString(), start, end);
  }

  char charAt(int i) {
    return str.charAt(start + i);
  }

  int length() {
    return end - start;
  }

  boolean contains(char c) {
    return str.substring(start, end).contains(String.valueOf(c));
  }

  public String toString() {
    return str.substring(start, end);
  }
}

public class LCS {

  void copyLineUp(int[][] k) {
    // System.out.println(Arrays.toString(k[0]));
    for (int i = 0; i < k[0].length; i++) {
      k[0][i] = k[1][i];
    }
  }

  int[] ll(Slice a, Slice b) {
    // System.out.printf("Finding ll for '%s' and '%s'\n", a, b);
    // Basic LCS algorithm
    int[][] k = new int[2][b.length() + 1];
    for (int posInA = 0; posInA < a.length(); posInA++) {
      copyLineUp(k);
      for (int posInB = 0; posInB < b.length(); posInB++) {
        if (a.charAt(posInA) == b.charAt(posInB)) {
          k[1][posInB + 1] = k[0][posInB] + 1;
        } else {
          k[1][posInB + 1] = Integer.max(k[0][posInB + 1], k[1][posInB]);
        }
      }
    }
    // System.out.println(Arrays.toString(k[1]));
    return k[1];
  }

  String lcs(Slice a, Slice b) {
    // System.out.printf("Finding lcs for '%s' and '%s'\n", a, b);

    if (b.length() == 0) {
      // System.out.printf("LCS['%s', '%s']=%s \n", a.toString(), b.toString(), "");
      return "";
    }
    if (a.length() == 0) {
      // System.out.printf("LCS['%s', '%s']=%s \n", a.toString(), b.toString(), "");
      return "";
    }
    if (a.length() == 1) {
      // System.out.println("Single-char base case");
      if (b.contains(a.charAt(0))) {
        return String.valueOf(a.charAt(0));
      } else
        return "";
    }

    int i = a.length() / 2;

    int[] l1 = ll(a.slice(0, i), b);
    int[] l2 = ll(a.reverse().slice(0, a.length() - i), b.reverse());

    int max = Integer.MIN_VALUE;
    int maxIndex = 0;
    for (int j = 0; j < l1.length; j++) {
      int candidate = l1[j] + l2[b.length() - j];
      if (candidate > max) {
        max = candidate;
        maxIndex = j;
      }
    }

    int k = maxIndex;

    // System.out.printf("\nMatching:\n '%s':'%s' --- %s\n'%s':'%s' --- %s\n",
    // a.slice(0, i),
    // b.slice(0, k),
    // Arrays.toString(l1),
    // a.slice(i, a.length()),
    // b.slice(k, b.length()),
    // Arrays.toString(l2));

    String c1 = lcs(a.slice(0, i), b.slice(0, k));
    String c2 = lcs(a.slice(i, a.length()), b.slice(k, b.length()));

    // System.out.printf("LCS['%s', '%s']=%s \n", a.toString(), b.toString(), c1 +
    // c2);

    return c1 + c2;
  }

  public String find(String A, String B) {
    return lcs(new Slice(A), new Slice(B));
    // Implement the Hirschberg LCS algorithm
  }

}
