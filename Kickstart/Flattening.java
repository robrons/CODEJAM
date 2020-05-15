import java.util.*;
import java.io.*;

// Answer to Question: https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edc/000000000018666c

public class Flattening {

  
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\CISER\\Desktop\\Git_Files\\Google-Contests\\Kickstart\\test.txt"); 
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        //Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner in = new Scanner(br);
        int t = in.nextInt();
        
        for (int z = 1; z <= t; ++z) {
            int m = in.nextInt();
            int n = in.nextInt();
            int[] A = new int[m]; 
            for(int i = 0; i < A.length; ++i) A[i] = in.nextInt(); 
            int[][] memo = new int[m + 1][n + 1];
            for(int[] row : memo) Arrays.fill(row, -1);
            int res = dfs(0, n, A, memo); 
            System.out.println("Case #" + z + ": " + res);
        }
    } 

    private static int dfs(int pos, int k, int[] A, int[][] memo) {
        if(pos == A.length) return 0;
        int res = Integer.MAX_VALUE, majElmCnt = 0; 
        if(memo[pos][k] != -1) return memo[pos][k];
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = pos; i < A.length; ++i) {
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
            majElmCnt = Math.max(majElmCnt, map.get(A[i]));
            if(k == 0) continue;
            res = Math.min(res, i - pos + 1 - majElmCnt + dfs(i + 1, k - 1, A, memo)); 
        }
        memo[pos][k] =  k == 0 ? A.length - pos - majElmCnt : res;
        return memo[pos][k];
    }
          
}            