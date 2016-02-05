package baldeeps.mooseum;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Question Class
 */
public class QuestionClassTest {

    // This test that the Question cannot be initialized with null values
    @Test
    public void testNullCase() throws Exception{
        boolean exceptionThrown = false;
        try {
            Question q = new Question("Are you happy?", null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    // Test the check answer method
    @Test
    public void testCheckAnswer(){
        List<String> hints1 = new ArrayList<String>();
        hints1.add("yes");
        hints1.add("no");
        hints1.add("maybe");
        Question q1 = new Question("Is this right?", hints1, "yes");

        String correct = "yes";
        String incorrect = "no";
        String nullTest = null;

        assertTrue("test with correct answer", q1.checkAnswer(correct));
        assertFalse("test with incorrect answer", q1.checkAnswer(incorrect));
        assertFalse("test with null input", q1.checkAnswer(nullTest));
    }

    // This tests that the correct answer is automatically added onto the array of hints
    @Test
    public void testAnswerMissingFromHints() {
        List<String> hints1 = new ArrayList<String>();
        hints1.add("no");
        hints1.add("maybe");
        Question q1 = new Question("is ans there?", hints1, "yes");

        List<String> hints2 = new ArrayList<String>();
        hints2.add("yes");
        hints2.add("no");
        hints2.add("maybe");
        Question qActual = new Question("is ans there?", hints2, "yes");

        // Test that the answer gets added if it isn't there
        //assertTrue("the answer was added on", q1.getHints().contains(q1.getAnswer()));
        System.out.println("checking answer got added");
        assertTrue("q1 is now the same as the correct format", q1.equals(qActual));
        System.out.println("Done checking");
    }


    //Test the equals method works correctly
    @Test
    public void testEquals(){
        List<String> hints1 = new ArrayList<String>();
        hints1.add("yes");
        hints1.add("no");
        hints1.add("maybe");
        Question q1 = new Question("Is this equal?", hints1, "yes");

        Question q2 = new Question("Is this equal?", hints1, "yes");

        Question q3 = new Question("Is this reall?", hints1, "yes");

        Question q4 = new Question("Invalid", hints1, "yes");

        List<String> hints2 = new ArrayList<String>();
        hints2.add("yes");
        hints2.add("no");
        hints2.add("yes");

        Question q5 = new Question("Is this equal?", hints2, "yes");

        List<String> hints3 = new ArrayList<String>();
        hints2.add("yes");
        hints2.add("no");

        Question q6 = new Question("Is this equal?", hints3, "yes");


        assertTrue("Q1 equals Q2", q1.equals(q2));
        assertTrue("Q2 equals Q1", q2.equals(q1));

        assertFalse("Q3 has a different question of the same size", q1.equals(q3));
        assertFalse("Q4 has a different question", q1.equals(q4));

        assertFalse("Q5 has repeated answers", q1.equals(q5));
        assertFalse("Q5 has repeated answers", q5.equals(q1));

        assertFalse("Q6 has fewer answers", q1.equals(q6));
        assertFalse("Q6 has fewer answers", q6.equals(q1));
    }

}