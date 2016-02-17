
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for Question Class
 */
public class QuestionClassTest {

	@Test
	public void testGetHints(){
		List<String> hints1 = new ArrayList<String>();
		hints1.add("yes");
		hints1.add("no");
		hints1.add("maybe");
		Question q1 = new Question("Is this right?", hints1, "yes");
		
		
		System.out.print("Testing the same 3 hints are returned: ");
		assertEquals(3, q1.getHints().size());
		System.out.println("passed\n");
		
		
		System.out.print("Testing a 4th hint returns an Index Out of Bounds Exception: ");
		boolean noMoreHints = false;
		try{
			q1.getHints().get(3);
		} catch (IndexOutOfBoundsException e){
			noMoreHints = true;
		}
		assertTrue(noMoreHints);
		System.out.println("passed\n");
		
		System.out.print("Testing repeated hints show up when there's less than 3 hints: ");	
		List<String> hints2 = new ArrayList<String>();
        hints2.add("yes");
        hints2.add("no");
        hints2.add("yes");
        
        q1.setHints(hints2); 
        
        assertEquals(3, q1.getHints().size());
		
		noMoreHints = false;
		try{
			q1.getHints().get(3);
		} catch (IndexOutOfBoundsException e){
			noMoreHints = true;
		}
		assertTrue(noMoreHints);	
		System.out.println("passed\n");
		
		
		System.out.print("Testing only 4 hints are returned with repeated hints: ");
		hints2.add("yes");
		hints2.add("yes");
		q1.setHints(hints2);
		
		assertEquals(4, q1.getHints().size());
		
		System.out.println("Passed\n");
		
		System.out.print("Testing only 4 hints are returned: ");
		hints1.add("sure");
		hints1.add("nah");
		q1.setHints(hints1);
		
		assertEquals(4, q1.getHints().size());
		System.out.println("passed\n");
	}

	
    // This test that the Question cannot be initialized with null values
    @Test
    public void testNullCase() throws Exception{
    	System.out.print("Testing an exception is thrown if any parameter passed is null: ");
        boolean exceptionThrown = false;
        try {
            Question q = new Question("Are you happy?", null, null);
        } catch(NullPointerException e){
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        System.out.println("passed\n");
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
        
        System.out.println("Test with non Question object");
        assertFalse(q1.equals("potato"));
        
        System.out.println("Test with same object");
        assertTrue(q1.equals(q1));


        assertTrue("Q1 equals Q2", q1.equals(q2));
        assertTrue("Q2 equals Q1", q2.equals(q1));

        System.out.println("Testing a = b and b = a, passed");

        assertFalse("Q3 has a different question of the same size", q1.equals(q3));
        assertFalse("Q4 has a different question", q1.equals(q4));

        System.out.println("Testing question lenght stuff, passed");

        System.out.println("Repeated answers test 1");
        assertFalse("Q5 has repeated answers", q1.equals(q5));
        System.out.println("Repeated answers test 2");
        assertFalse("Q5 has repeated answers", q5.equals(q1));

        System.out.println("Testing for repeated answers, passed");

        assertFalse("Q6 has fewer answers", q1.equals(q6));
        assertFalse("Q6 has fewer answers", q6.equals(q1));
    }
	 
}
