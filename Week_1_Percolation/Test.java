import java.rmi.AccessException;

public class Test {
    public static void main(String[] args) throws IllegalAccessException{
        Percolation p = new Percolation(3);
        p.print();
        p.open(1,1);
        p.open(1,3);
        p.open(2,2);
        p.open(2,1);
        p.open(3,1);
        System.out.println(p.isFull(3,1));
        p.print();
        System.out.println(p.percolates());
        p.test();
        System.out.println(p.isOpen(3,1));
       PerlocationStats ps = new PerlocationStats(20,204);
   }

    public static void main(String[] args) throws IllegalAccessException {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean value                    = " + stats.mean());
        System.out.println("stddev value               = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
