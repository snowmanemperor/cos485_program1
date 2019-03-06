package student;
/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method:
 *   formatParagraph - which formats one paragraph
 */
import java.util.ArrayList;

public class TextFormatting {

    public static void main(String[] args) {
        String[] words = {"Tushar","Roy","Likes","To","Code"};
        int width = 10;
        ArrayList<String> result = new ArrayList<>();
        formatParagraph(words,width,result);
    }

    public static int formatParagraph(String[] words, int width, ArrayList<String> result)  {   

        int[][] cost = new int[words.length][words.length];
        int[] minCost = new int[words.length];
        int[] resultIndexes = new int[words.length];


        /*
         * These for loops will move through and fill the
         * cost matrix with values, negative values and the integer
         * values will be cubed in the next set of for loops.
         */
        for (int i = 0; i < words.length; i++) {
            cost[i][i] = width - words[i].length();
            for (int j = i + 1; j < words.length; j++) {
                cost[i][j] = cost[i][j-1] - words[j].length() - 1;             
            }
        }

        /*
         * These for loops will cube positive numbers or shift negative
         * integer values into Integer.MAX_VALUE for comparison purposes.
         */
        for(int i = 0; i < words.length; i++) {
            for (int j = i; j < words.length; j++) {
                if (cost[i][j] < 0) {
                    cost[i][j] = Integer.MAX_VALUE;
                }
                else {
                    cost[i][j] = (int) Math.pow(cost[i][j], 3);
                }
            }
        }

        /*
         * Compute the minimum costs based on previous answers
         * and populate a matrix of the result indices showing
         * where to break the lines at which words.
         */

        for(int i = words.length-1; i >= 0 ; i--){
            minCost[i] = cost[i][words.length-1];
            resultIndexes[i] = words.length;
            for(int j = words.length-1; j > i; j--) {
                if(cost[i][j-1] == Integer.MAX_VALUE) {
                    continue;
                }
                if(minCost[i] > minCost[j] + cost[i][j-1]) {
                    minCost[i] = minCost[j] + cost[i][j-1];
                    resultIndexes[i] = j;
                }
            }
        }

        /*
         * Append words to a stringbuilder and finally populate
         * the results array.
         */

        int pastIndex = 0;
        int currIndex = 0;


        while (currIndex < words.length) {
            StringBuilder sb = new StringBuilder();
            currIndex = resultIndexes[pastIndex];
            for (int i = pastIndex; i < currIndex; i++) {
                sb.append(words[i] + " ");
            }
            pastIndex = currIndex;
            result.add(sb.toString());
        }

        return minCost[0];
    }

    // extra credit paragraph formatting that has no penalty for the last line of the paragraph
    public static int xc_formatParagraph(String[] words, int width, ArrayList<String> result) {
        return -1;      // not implemented
    }

}


