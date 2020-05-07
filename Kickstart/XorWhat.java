import java.util.*;
import java.io.*;

// Answer to Question: https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051061/0000000000161426 
// WRONG ANSWER !!! 

public class XorWhat {

  
    public static void main(String[] args) throws Exception {
        File file = new File("test.txt"); 
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        //Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner in = new Scanner(br);
        int t = in.nextInt();
        
        for (int z = 1; z <= t; ++z) {
            int n = in.nextInt();
            int q = in.nextInt();
            TreeSet<Integer> set = new TreeSet<>(); 

            for(int i = 0; i < n; ++i) if(!isEvenBits(in.nextInt())) set.add(i);
            int[] res = new int[q];
            int r = 0;

            for(int i = 0; i < q; ++i) {
                int pos = in.nextInt(), val = in.nextInt();

                if(!isEvenBits(val)) set.add(pos);
                else set.remove(pos);

                System.out.println(set);
                System.out.println(set.first());

                if(set.size() % 2 == 0) res[r++] = n;
                else res[r++] = Math.max(set.first(), n - set.first() - 1);
            }

            System.out.print("Case #" + z + ":");
            for(int x : res) System.out.print(" " + x);
            System.out.println();
        }
    } 

    private static boolean isEvenBits(int x) {
        return (Integer.bitCount(x) & 1) == 0;
    }
          
}            