import java.util.*;

public class RSA
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter p, q, and e for RSA: ");
        int p = scan.nextInt();
        int q = scan.nextInt();
        int e = scan.nextInt();
        RSA r = new RSA(p, q, e);
        System.out.println("The smallest d is: " + r.findSmallestD());
    }

    private int p, q, e;

    public RSA (int p, int q, int e)
    {
        this.p = p;
        this.q = q;
        this.e = e;
    }

    public int findSmallestD()
    {
        int p = this.p;
        int q = this.q;
        int e = this.e;

        int Tn = (p - 1) * (q - 1);
        int d = 0;
        int de = d * e;
        // System.out.println(de % Tn);
        while (de % Tn != 1 || d == 0)
        {
            d++;
            de = d * e;
            // System.out.println(de % Tn);
        }

        return d;
    }
}
