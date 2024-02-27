import java.util.*;
import java.io.*;
class NeedlemanWunschAlgorithm
{
    public static void main(String Args[])
    {
        if(Args.length != 2)
        {   
            if(Args.length < 2)
                System.out.println("Not Enough Arguments Given");
            else
                System.out.println("Too Many Arguments Given");
        }
        else
        {
            //Assuming Addresses Are Passed As Command Line Arguments
            String path1 = Args[0];
            String path2 = Args[1];
            String seq1 = "";
            String seq2 = "";
            try 
            {
                File file1 = new File(path1);
                Scanner sc1 = new Scanner(file1);
                while(sc1.hasNextLine())
                {
                    seq1 = seq1 + sc1.nextLine();
                }
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            try
            {
                File file2 = new File(path2);
                Scanner sc2 = new Scanner(file2);
                while(sc2.hasNextLine())
                {
                    seq2 = seq2 + sc2.nextLine();
                }
            }
            catch (Exception e2)
            {   
                e2.printStackTrace();
            }
            NeedlemanWunschAlgorithm(seq1, seq2);
        }
    }
    static void NeedlemanWunschAlgorithm(String seq1, String seq2)
    {
        //Initializations
        int match_score = 1;
        int mismatch_penalty = -1;
        int gap_penalty = -1;
        int matrix[][] = new int[seq1.length() + 1][seq2.length() + 1];
        int match = 0;
        int insert = 0;
        int delete = 0;
        int current_score = 0;
        int diagonal_score = 0;
        int up_score = 0;
        int left_score = 0;
        String align_seq1 = "";
        String align_seq2 = "";
        double negative_infinity = Double.NEGATIVE_INFINITY;
        
        //Matrix Filling
        for(int i = 1; i < seq1.length() + 1; i++)
        {
            for(int j = 1; j < seq2.length() + 1; j++)
            {
                if(seq1.charAt(i - 1) == seq2.charAt(j -1))
                    match = matrix[i - 1][j - 1] + match_score;
                else
                    match = matrix[i - 1][j - 1] + mismatch_penalty;
                insert = matrix[i - 1][j] + gap_penalty;
                delete = matrix[i][j - 1] + gap_penalty;
                matrix[i][j] = Math.max(Math.max(match, insert), delete);
            }
        }
        
        //Traceback & Evaluating Aligned Sequences
        int i = seq1.length();
        int j = seq2.length();
        while(i > 0 || j > 0)
        {
            current_score = matrix[i][j];
            if(i > 0 && j > 0)
                diagonal_score = matrix[i - 1][j - 1];
            else
                diagonal_score = (int)negative_infinity;
            if(i > 0)
                left_score = matrix[i - 1][j];
            else
                left_score = (int)negative_infinity;
            if(j > 0)
                up_score = matrix[i][j - 1];
            else
                up_score = (int)negative_infinity;
            
            if(Math.max(Math.max(diagonal_score, left_score), up_score) == diagonal_score)
            {
                align_seq1 = seq1.charAt(i - 1) + align_seq1;
                align_seq2 = seq2.charAt(j - 1) + align_seq2;
                i -= 1;
                j -= 1;
            }
            else if(Math.max(Math.max(diagonal_score, left_score), up_score) == left_score)
            {
                align_seq1 = seq1.charAt(i - 1) + align_seq1;
                align_seq2 = "-" + align_seq2;
                i -= 1;
            }
            else
            {
                align_seq1 = "-" + align_seq1;
                align_seq2 = seq2.charAt(j - 1) + align_seq2;
                j -= 1;
            }
        }
        System.out.println("First Sequence After Alignment Is:  " + align_seq1);
        System.out.println("Second Sequence After Alignment Is: " + align_seq2);
    }
}