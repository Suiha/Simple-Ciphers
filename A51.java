import java.util.*;

public class A51
{
    public static void main(String[] args)
    {
        // X: 1010101010101010101
        // Y: 1100110011001100110011
        // Z: 11100001111000011110000

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the value in register X: ");
        String x = scan.next();
        System.out.print("Enter the value in register Y: ");
        String y = scan.next();
        System.out.print("Enter the value in register Z: ");
        String z = scan.next();
        A51 a = new A51(x, y, z);

        // Choose a keystream length and print the keystream
        System.out.print("Enter your desired keystream length: ");
        int l = scan.nextInt();
        a.printKeystream(l);

        // Print updated registers X, Y, Z
        a.printRegisters();

    }

    private int[] x;
    private int[] xFeedback = {13, 16, 17, 18};
    private int[] y;
    private int[] yFeedback = {20, 21};
    private int[] z;
    private int[] zFeedback = {7, 20, 21, 22};

    public A51(String x, String y, String z)
    {
        this.x = convertToArray(x);
        this.y = convertToArray(y);
        this.z = convertToArray(z);
    }

    /**
     * Converts a string of 0 or 1 to an int
     * @param a: "0" or "1"
     * @return: 0 or 1
     */
    private int toInt(String a)
    {
        int result;
        if (a.equals("0")) { result = 0; } else { result = 1;}
        return result;
    }

    /**
     * Converts the inputted string for a register to an int[]
     * @param a: inputted register value
     * @return: integer array of register
     */
    private int[] convertToArray(String a)
    {
        int[] result = new int[String.valueOf(a).length()];
        for (int i = 0; i < a.length(); i++)
        {
            result[i] = toInt(a.substring(i, i+1));
        }

        return result;
    }

    /**
     * Prints the current value of registers X, Y, and Z
     */
    public void printRegisters()
    {
        System.out.print("X: ");
        for (int s : this.x)
        {
            System.out.print(s);
        }
        System.out.println();

        System.out.print("Y: ");
        for (int s : this.y)
        {
            System.out.print(s);
        }
        System.out.println();

        System.out.print("Z: ");
        for (int s : this.z)
        {
            System.out.print(s);
        }
        System.out.println();
    }

    /**
     * Prints the keystream of a given length
     * @param l: the given length
     */
    public void printKeystream(int l)
    {
        System.out.print("Generated keystream: ");
        for (int i = 0; i < l; i++)
        {
            System.out.print(this.keystreamBit());
        }
        System.out.println();
    }

    /**
     * Prints one keystream bit based on the A5/1 algorithm
     * @return: a keystream bit
     */
    public int keystreamBit()
    {
        // Majority Function
        int maj = this.majority();

        // Shift based on majority
        this.shift(maj);

        // Generate keystream bit based on last bit in each register
        return this.x[18] ^ this.y[21] & this.z[22];
    }

    /**
     * Determines the majority bit based on given indexes for each register
     * @return: 0 or 1
     */
    private int majority()
    {
        int maj = 0;

        // Count for amount of 0's
        int c0 = 0;
        // Count for amount of 1's
        int c1 = 0;

        if (this.x[8] == 0)  { c0++; } else { c1++; }
        if (this.y[10] == 0) { c0++; } else { c1++; }
        if (this.z[10] == 0) { c0++; } else { c1++; }

        // If there are more 1's than 0's, the majority is 1
        if (c1 > c0) { maj = 1; }

        return maj;
    }

    /**
     * Shifts each register after the majority is calculated,
     * then calculates the feedback function to be shifted into the
     * left position
     * @param maj: the result of the majority function (0 or 1)
     */
    private void shift(int maj)
    {
        // Shifting X
        int[] result = new int[this.x.length];
        if (this.x[8] == maj)
        {
            for (int i = 1; i < this.x.length; i++)
            {
                result[i] = x[i - 1];
            }
            // Calculating P for X
            result[0] = this.x[xFeedback[0]];
            for (int i = 1; i < xFeedback.length; i++)
            {
                result[0] = result[0] ^ this.x[xFeedback[i]];
            }
            this.x = result;
        }

        // Shifting Y
        if (this.y[10] == maj)
        {
            result = new int[this.y.length];
            for (int i = 1; i < this.y.length; i++)
            {
                result[i] = y[i - 1];
            }
            // Calculating P for Y
            result[0] = this.y[yFeedback[0]];
            for (int i = 1; i < yFeedback.length; i++)
            {
                result[0] = result[0] ^ this.y[yFeedback[i]];
            }
            this.y = result;
        }

        // Shifting Z
        if (this.z[10] == maj) {
            result = new int[this.z.length];
            for (int i = 1; i < this.z.length; i++)
            {
                result[i] = z[i - 1];
            }
            // Calculating P for Z
            result[0] = this.z[zFeedback[0]];
            for (int i = 1; i < zFeedback.length; i++)
            {
                result[0] = result[0] ^ this.z[zFeedback[i]];
            }
            this.z = result;
        }
    }
}
