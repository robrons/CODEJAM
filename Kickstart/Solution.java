import java.util.*;
import java.io.*;
public class Solution {

    public static class SparseTable { 
       private final int[][] sparse_lo;
       private final int[][] sparse_hi;
       private final int[] input;
       private final int n;

       public SparseTable(int[] input) {
           this.n = input.length; 
           this.input = input; 
           this.sparse_lo = preprocess_lo(input, this.n);
           this.sparse_hi = preprocess_hi(input, this.n);
       } 

        private int[][] preprocess_lo(int[] input, int n) {
            int[][] sparse = new int[n][log2(n) + 1];
            for(int i = 0; i < n; ++i) sparse[i][0] = i;
            for(int j = 1; 1 << j <= n; ++j) {
                for(int i = 0; i + (1 << j) <= n; ++i) {
                    if(input[sparse[i][j - 1]] < input[sparse[i + (1 << (j - 1))][j - 1]])
                        sparse[i][j] = sparse[i][j - 1];
                    else
                        sparse[i][j] = sparse[i + (1 << (j - 1))][j - 1];
                }
            }
            return sparse;
       }

       private int[][] preprocess_hi(int[] input, int n) {
            int[][] sparse = new int[n][log2(n) + 1];
            for(int i = 0; i < n; ++i) sparse[i][0] = i;
            for(int j = 1; 1 << j <= n; ++j) {
                for(int i = 0; i + (1 << j) <= n; ++i) {
                    if(input[sparse[i][j - 1]] > input[sparse[i + (1 << (j - 1))][j - 1]])
                        sparse[i][j] = sparse[i][j - 1];
                    else
                        sparse[i][j] = sparse[i + (1 <<(j - 1))][j - 1];
                }
            }
            return sparse;
       }

       public int query_lo(int lo, int hi) {
           int l = hi - lo + 1;
           int k = log2(l);
           if(input[sparse_lo[lo][k]] <= input[sparse_lo[hi - (1 << k) + 1][k]])
            return input[sparse_lo[lo][k]];
           else
            return input[sparse_lo[hi - (1 << k) + 1][k]];
       }

       public int query_hi(int lo, int hi) {
           int l = hi - lo + 1;
           int k = log2(l);
           if(input[sparse_hi[lo][k]] > input[sparse_hi[hi - (1 << k) + 1][k]])
            return input[sparse_hi[lo][k]];
           else
            return input[sparse_hi[hi - (1 << k) + 1][k]];
       }

       private int log2(int n) {
           if(n < 0) throw new IllegalArgumentException();
           return 31 - Integer.numberOfLeadingZeros(n);
       }

    }

    public static void main(String[] args) throws Exception {
        File file = new File("test.txt"); 
        BufferedReader br = new BufferedReader(new FileReader(file)); 
           //Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner in = new Scanner(br);
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        
        for (int z = 1; z <= t; ++z) {
            int m = in.nextInt();
            int n = in.nextInt();
            int k = in.nextInt();
            int[][] M = new int[m][n];
            for(int i = 0; i < m; ++i)
                for(int j = 0; j < n; ++j)
                    M[i][j] = in.nextInt();
            int res = 0;
            for(int i = 0; i < m; ++i) {
                SparseTable table = new SparseTable(M[i]);
                for(int j = 0; j < n; ++j) 
                    M[i][j] = binSearch(M[i], j, table, k) - j + 1; 
            }
            for(int j = 0; j < n; ++j) {
                int[] col = new int[m];
                for(int i = 0; i < m; ++i) col[i] = M[i][j];
                res = Math.max(res, largestRectangleHist(col));
            }
            System.out.println("Case #" + z + ": " + res);
        }
    } 

    private static int binSearch(int[] row, int start, SparseTable table, int K) {
        int lo = start, hi = row.length - 1;
        for(int i = lo; i <= hi; ++i) {
            int qrLo = table.query_lo(start, i);
            int qrHi = table.query_hi(start, i);
        }
        while(lo <= hi) {
            int mid = lo + ((hi - lo) >> 1);
            int qrLo = table.query_lo(start, mid);
            int qrHi = table.query_hi(start, mid);
            if(qrHi - qrLo <= K) 
                lo = mid + 1;
            else 
                hi = mid - 1;
        }
        return hi;
    }

    private static int largestRectangleHist(int[] heights) {
        Stack<Integer> stk = new Stack<>();
        stk.push(-1);
        int res = 0;
        for(int i = 0; i < heights.length; ++i) {
           while(stk.peek() != -1 && heights[stk.peek()] >= heights[i])
            res = Math.max(res, heights[stk.pop()] * (i - stk.peek() - 1)); 
           stk.push(i);
        }
        while(stk.peek() != -1)
          res = Math.max(res, heights[stk.pop()] * (heights.length - stk.peek() - 1)); 
        return res;
    }
          
}            