import java.util.*;
import myutil.*;

public class Leet {

    // @param: string array
    // problem: find all unique sentences in array
    // "hey buddy man" not unique to "Buddy heY man"
    // but is unique to "he ybuddy man" and "this is a random sentence"
    public static String[] getUniqueSentences(String[] in) {
        ArrayList<String> output = new ArrayList<String>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int i = 0; // index of output list
        
        for (String sentence : in) {
            String[] sentenceArr = sentence.toLowerCase().split(" ");
            Arrays.sort(sentenceArr);

            Integer ind = map.get(Arrays.hashCode(sentenceArr)); // must be integer to match hashmap type
            // https://stackoverflow.com/questions/1780385/java-hashmapstring-int-not-working#1780390

            // if ind is null, we have never seen the sentence before so it is unique
            if (ind == null) {
                map.put(Arrays.hashCode(sentenceArr), i); // put into map for quick check if string is unique
                output.add(sentence); // add to output
                i++;
            } else {
                map.put(Arrays.hashCode(sentenceArr), i);
                output.set(ind, sentence);
            }
        }
        String[] ret = new String[output.size()];
        ret = output.toArray(ret);
        return ret;
    }

    public static boolean isPalindrome(String s) {
        int beg = 0;
        int end = s.length()-1;

        if (end == 0) return true;
        
        while (beg < end) {
            if (s.charAt(beg) != s.charAt(end)) return false;
            beg++;
            end--;
        }
        return true;
    }

    public static String longestPalindromeSubstringRec(String s) {
        // isPalindrome serves as basecase since a string of 1 is a palindrome 
        if (isPalindrome(s)) {
            return s;
        } else {
            String one = longestPalindromeSubstringRec(s.substring(0, s.length()-1));
            String two = longestPalindromeSubstringRec(s.substring(1, s.length()));
            if (one.length() > two.length()) 
                return one; 
                else return two;
        }
    }

    public static String longestPalindromeSubstringDP(String s) {
        if (s.length() == 0) return ""; // trivial check
        int beg = 0, end = 0; // indicies of the lps
        // 2D array of the boolean where each p[i][j] = true means substring(i, j+1) is a palindrome
        boolean[][] pal = new boolean[s.length()][s.length()];

        // fill in base cases 
        // single letters are pals ie [i][i] is true
        // fill in two letter palindromes (double letters)
        for (int i = 0; i < pal.length; i++) {
            pal[i][i] = true;
            if (i+1 < pal.length) {
                if (s.charAt(i) == s.charAt(i+1)) {
                    pal[i][i+1] =  true;
                    beg = i;
                    end = i+1;
                }
            }
        }

        // fill in palindrome dp matrix, starting from bottom right
        // this is because we want to fill starting from the diagonal, ie shortest strings
        for (int i = pal.length-1; i >= 0; i--) {
            for (int j = i+2; j < pal.length; j++) {
                pal[i][j] = (pal[i+1][j-1] == true) && (s.charAt(i) == s.charAt(j));
                if (pal[i][j]==true && (j-i > end-beg)) {
                    beg = i; 
                    end = j;
                }
            }
        }

        return s.substring(beg, end+1);
    }

    public static String longestPalindromeSubstring(String s) {
        if (s.length() == 0) return ""; // trivial check
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = lenPalindromeFromCenter(s, i, i);
            int len2 = lenPalindromeFromCenter(s, i, i+1);
            int len = Math.max(len1, len2);
            if (len > end-start) {
                // update beg and end
                if (len % 2 == 0) {
                    start = i - len/2 + 1;
                    end = i + len/2;
                } else {
                    start = i - len/2;
                    end = i + len/2;
                }
            }
        }
        return s.substring(start, end+1);
    }

    // @helper: longestPalindromeSubstring
    private static int lenPalindromeFromCenter(String s, int centerA, int centerB) {
        while ( (centerA >= 0) && (centerB < s.length()) && (s.charAt(centerA) == s.charAt(centerB)) ) {
            centerA--;
            centerB++;
        }
        return centerB - centerA - 1;
    }

    // @param: int array
    // @return: return array where arr[i] is the product
    // of all elements in the array, excluding arr[i]
    // cannot use / symbole (dividor)
    public static int[] productArray(int[] arr) {
        int[] prod = new int[arr.length];
        int temp = 1;

        // init product array to 1
        for (int i = 0; i < prod.length; i++) {
            prod[i] = 1;
        }

        // generate product from left
        // temp is the product from left not including arr[i]
        for (int i = 0; i < prod.length; i++) {
            prod[i] = temp;
            temp *= arr[i];
        }

        temp = 1; // reset temp to 1

        // generate product from right 
        for (int i = prod.length-1; i >= 0; i--) {
            prod[i] *= temp;
            temp *= arr[i];
        }

        return prod;
    }

    // @param: int 
    // @return: int arraylist of prime numbers up to n
    // problem: generate all prime numbers up to n
    public static ArrayList<Integer> primeNumbersLessThanN(int n) {
        // bool array where prime[i] == true if i is prime
        boolean[] prime = new boolean[n+1];

        // init true
        for (int i = 0; i < prime.length; i++) {
            prime[i] = true;
        }

        int cur = 2; // start at 2, lowest prime number
        while (cur*2 <= n) {
            // set multiples to false
            for (int i = cur*2; i <= n; i+=cur) {
                prime[i] = false;
            }

            cur++;
            if (cur > n) break; // finished
            // get next prime number 
            while (prime[cur] == false) {
                cur++;
            }
        }

        // construct array of primes
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i < prime.length; i++) {
            if (prime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    // code from https://www.geeksforgeeks.org/sieve-of-eratosthenes/
    // used to compare against my implementation
    private static ArrayList<Integer> sieveOfEratosthenes(int n) {
        // Create a boolean array "prime[0..n]" and initialize 
        // all entries it as true. A value in prime[i] will 
        // finally be false if i is Not a prime, else true. 
        boolean prime[] = new boolean[n+1]; 
        for(int i=0;i<n;i++) 
            prime[i] = true; 
          
        for(int p = 2; p*p <=n; p++) 
        { 
            // If prime[p] is not changed, then it is a prime 
            if(prime[p] == true) 
            { 
                // Update all multiples of p 
                for(int i = p*p; i <= n; i += p) 
                    prime[i] = false; 
            } 
        } 
          
        // Print all prime numbers
        ArrayList<Integer> primes = new ArrayList<>(); 
        for(int i = 2; i <= n; i++) 
        { 
            if(prime[i] == true) 
                primes.add(i);
        }
        return primes;
    } 

    // @param: int n, that is even and > 2
    // @return: the pair of prime numbers that sum up to input int n
    // a solution always exist according to Goldbach’s conjecture
    public static int[] primePair(int n) {
        int[] primePair = new int[2];
        if (n<2 || n%2 != 0) return primePair; // n must be even and > 2

        // create bool array where prime[i] is true if i is prime
        boolean[] prime = new boolean[n+1];
        for (int i = 0; i < prime.length; i++)
            prime[i] = true;

        // set the multiples of primes to false, since they are not prime
        for (int i = 2; i*2 < prime.length; i++) {
            if (prime[i]) {
                for (int j = i*2; j < prime.length; j+=i) {
                    prime[j] = false;
                }
            }
        }

        for (int i = 2; i < prime.length; i++) {
            if (prime[i] && prime[n-i]) {
                primePair[0] = i;
                primePair[1] = n-i;
                break;
            }
        }
        return primePair;

        /*
        * alternate way, but less efficient(?) 
        * since we are creating the array of primes from the bool array
        * which we don't really need to do

        ArrayList<Integer> primes = primeNumbersLessThanN(n);
        int l = 0, r = primes.size()-1;
        int primeOne, primeTwo, sum;
        while (l < r) {
            primeOne = primes.get(l);
            primeTwo = primes.get(r);
            sum = primeOne + primeTwo;
            if (sum == n) {
                primePair[0] = primeOne;
                primePair[1] = primeTwo;
                break;
            } else if (sum < n) {
                l++;
            } else {
                r--;
            }
        }
        return primePair;
        */
    }

    public static void main(String []args) {
        Graph g = new Graph(5);
        System.out.println(g.nodes[0].children);
    }
}