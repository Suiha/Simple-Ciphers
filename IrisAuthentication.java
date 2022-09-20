import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class IrisAuthentication
{
    private HashMap<String, String> irisBank;
    private static Scanner scan;
    private static double HAMMING_ACCEPT = 0.32;

    public static void main(String[] args)
    {
        IrisAuthentication ia = new IrisAuthentication();
        ia.prompt();
    }

    public IrisAuthentication()
    {
        irisBank = new HashMap<>();
        scan = new Scanner(System.in);
    }

    /**
     * Convert a given hex value (stored as an int) to binary
     * @param hex: an integer representing a hex value
     * @return
     */
    public String hexToBinary(String hex)
    {
        BigInteger t1 = new BigInteger(hex, 16);
        int t2 = t1.intValue();
        String b = Integer.toBinaryString(t2);
        return b;
    }

    /**
     * Given a name and iris code, add them to the system.
     * @param name: person whose iris code we're adding
     * @param iris: iris code, in hexadecimal
     */
    public void addIris(String name, String iris)
    {
        irisBank.put(name, iris);
    }

    /**
     * Prompt user to enter the enrollment or recognition phase.
     * If anything other than 1 (enrollment) or 2 (recognition) is entered,
     * exit the program.
     */
    public void prompt()
    {
        System.out.println("Choose an option:\n" +
                "1. Enrollment Phase\t\t" +
                "2. Recognition Phase\t" +
                "3. Exit");
        int choice = scan.nextInt();

        if (choice == 1)
        {
            this.enroll();
        } else if (choice == 2)
        {
            this.authenticate();
        }
    }

    /**
     * Enroll a new user by entering a name and iris code.
     */
    public void enroll()
    {
        // Prompt for name and iris code.
        System.out.print("Enter a name: ");
        String name = scan.next();
        System.out.print("Enter an iris code for that user: ");
        String irisCode = scan.next();

        // Convert iris code to binary and enroll them.
        String iris = hexToBinary(irisCode);
        this.addIris(name, iris);
        System.out.println(name + " was enrolled.\n");

        // Return to prompt
        this.prompt();
    }

    /**
     * Authenticate a given user based on whether a scanned in iris code
     * matches the iris code in the database.
     */
    public void authenticate()
    {
        // Scanning user's iris
        System.out.print("Choose a user to authenticate: ");
        String name = scan.next();
        System.out.print("Scan in your iris code: ");
        String irisCode = scan.next();

        // If user doesn't exist in the database, return to prompt
        if (!irisBank.containsKey(name))
        {
            System.out.println("Error: User doesn't exit in database.\n");
            this.prompt();
        }

        // Retrieving (binary) iris codes of scanned iris and supposed iris
        String iris = hexToBinary(irisCode);
        String check = irisBank.get(name);

        // Calculating Hamming Distance
        double total = check.length();
        double nonMatching = 0.0;
        for (int i = 0; i < iris.length(); i++)
        {
            if (iris.charAt(i) != check.charAt(i)) { nonMatching++; }
        }
        double h1 = nonMatching / total;
        BigDecimal h2 = new BigDecimal(h1);
        h2 = h2.setScale(2, RoundingMode.HALF_DOWN);
        double hammingDistance = h2.doubleValue();
        System.out.println("Hamming Distance: " + hammingDistance);

        // Checking Hamming Distance
        if (hammingDistance < HAMMING_ACCEPT)
        {
            System.out.println(name + " has been authenticated.\n");
        } else {
            System.out.println(name + " has not been authenticated.\n");
        }

        // Return to prompt
        this.prompt();
    }
}
