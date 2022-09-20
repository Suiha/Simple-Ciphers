import java.util.*;

public class SimpleSub
{
    public static void main(String[] args)
    {
        // GBSXUCGSZQGKGSQPKQKGLSKASPCGBGBKGUKGCEUKUZKGGBSQ EICACGKGCEUERWKLKUPKQQGCIICUAEUVSHQKGCEUPCGBCGQOE VSHUNSUGKUZCGQSNLSHEHIEEDCUOGEPKHZGBSNKCUGSUKUASE RLSKASCUGBSLKACRCACUZSSZEUSBEXHKRGSHWKLKUSQSKCHQT XKZHEUQBKZAENNSUASZFENFCUOCUEKBXGBSWKLKUSQSKNFKQQ KZEHGEGBSXUCGSZQGKGSQKUZBCQAEIISKOXSZSICVSHSZGEGB SQSAHSGKHMERQGKGSKREHNKIHSLIMGEKHSASUGKNSHCAKUNSQ QKOSPBCISGBCQHSLIMQGKGSZGBKGCGQSSNSZXQSISQQGEAEUG CUXSGBSSJCQGCUOZCLIENKGCAUSOEGCKGCEUQCGAEUGKCUSZU EGBHSKGEHBCUGERPKHEHKHNSZKGGKAD
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the cipher to decode: ");
        SimpleSub s = new SimpleSub(scan.nextLine());
    }

    private String cipher;

    public SimpleSub(String c)
    {
        cipher = c;
        this.displayLetterFrequency();
        this.guess();
    }

    /**
     * When a SimpleSub is created, calculate and display the letter frequency
     * of its cipher.
     */
    public void displayLetterFrequency()
    {
        // Calculate letter frequency
        HashMap<String, Integer> l = new HashMap<>();
        String c = cipher.replaceAll("\\s", "");
        for (char character : c.toCharArray())
        {
            String s = Character.toString(character);
            if (!l.containsKey(s))
            {
                l.put(s, 1);
            } else {
                int val = l.get(s);
                val++;
                l.put(s, val);
            }
        }

        // Dsiplay letter frequency
        System.out.println("Letter Frequency:");
        for (String key : l.keySet())
        {
            System.out.println("\t" + key + ":\t" + l.get(key));
        }
    }

    /**
     * Prompts the user to enter a guess for the key.
     * The result of the guess is displayed and the user can continue guessing.
     * If not, the final key is displayed.
     */
    public void guess()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Guess a key: ");
        String key = scan.nextLine();
;
        String result = sub(cipher, key);

        System.out.println("Your guess: " + result);
        System.out.print("Try again (y/n)? ");
        if (scan.next().equals("y"))
        {
            this.guess();
        } else {
            System.out.println("Final Key: " + key);
        }
    }

    /**
     * Use the substitution cipher to encode/decode a given string based on a key
     * @param s: the string being substituted
     * @param key: the letters we're substituting with
     * @return: the substituted message
     */
    public String sub(String s, String key) {
        String result = "";

        Map<Character, Character> cipherMap = new HashMap<>();
        char alpha = 'A';

        for (int i = 0; i < key.length(); i++)
        {
            // System.out.println(alpha);
            cipherMap.put(alpha, key.charAt(i));
            alpha++;
        }

        for (int i = 0; i < s.length(); i++)
        {
            char n = s.charAt(i);
            if (s.charAt(i) != ' ')
            {
                char tmp = n;
                n = cipherMap.get(tmp);
                System.out.println(tmp + ", " + n);
            }
            result += n;
        }

        return result;
    }
}
