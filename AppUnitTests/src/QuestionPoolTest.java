import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.junit.Test;

public class QuestionPoolTest {
	
	@Test
	public void testSettingName() {
		List<Question> qs = new QuestionPoolTestHelper().exampleQuestions();
		QuestionPool qp = new QuestionPool("example", qs);
		QuestionPool qpNoName = new QuestionPool(qs);
		
		System.out.print("Testing quiz name is set in constructor: ");
		assertEquals("example", qp.getQuizName());
		System.out.println("passed\n");
		
		
		System.out.print("Testing quiz name is empty string when not set in constructor: ");
		assertEquals("", qpNoName.getQuizName());
		System.out.println("passed\n");
		
		System.out.print("Testing quiz name is set correctly: ");
		qpNoName.setQuizName("New Name Set");
		assertEquals("New Name Set", qpNoName.getQuizName());
		System.out.println("passed\n");
	}
	
	@Test
	public void testSettingQuestionPool(){
		List<Question> qs = new QuestionPoolTestHelper().exampleQuestions();
		QuestionPool qp = new QuestionPool("example", qs);
		
		System.out.print("Testing list of questions is set in constructor: ");
		assertEquals(qs, qp.getQuestionPool());
		assertEquals(qs.size(), qp.getQuestionPool().size()); // ensure no questions are lost
		System.out.println("passed\n");
	}
	
	@Test
	public void testAskedQuestion(){
		List<Question> qs = new QuestionPoolTestHelper().exampleQuestions();
		QuestionPool qp = new QuestionPool("example", qs);
		
		
		System.out.print("Testing if a question is returned: ");
		Question q = qp.askQuestion();
		assertNotNull(q);
		assertEquals(1, qp.getAskedQuestions().size());
		System.out.println("passed\n");
		
		System.out.print("Testing question asked was removed from question pool");
		assertFalse(qp.getQuestionPool().contains(q));
		assertTrue(qp.getAskedQuestions().contains(q));
		System.out.println("passed\n");
		
		qp.askQuestion(); // 2 out of 5 asked
		qp.askQuestion(); // 3 out of 5 asked
		qp.askQuestion(); // 4 out of 5 asked
		qp.askQuestion(); // 5 out of 5 asked
		
		System.out.println("Test question pool is reset when it's empty");
		
		
		
	}

}
