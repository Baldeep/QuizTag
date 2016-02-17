import java.util.ArrayList;
import java.util.List;

public class QuestionPoolTestHelper {
	
	public static void main(String args[]){
		List<String> hints = new ArrayList<String>();
		hints.add("a");
		hints.add("b");
		hints.add("c");
		hints.add("d");
		hints.add("e");
		hints.add("f");
		hints.add("g");
		hints.add("h");
		hints.add("i");
		
		Question qa = new Question("what letter comes after b and before d?", 
							hints, "c");
		
		List<Question> qs = exampleQuestions();
		QuestionPool qp = new QuestionPool(qs);
		
		Question q = qp.askQuestion();
		
		System.out.println(q.getQuestion());
		
		
		List<String> hintsGiven = q.getHints();
		for(int i=0;i<hintsGiven.size(); i++)
			System.out.println(hintsGiven.get(i));
		
		System.out.println("Resetting");
		q.resetHints();
		hintsGiven = q.getHints();
		for(int i=0;i<hintsGiven.size(); i++)
			System.out.println(hintsGiven.get(i));
		
		
	}
    /**
     * This method is for testing purposes only
     * @return a list containing 5 questions
     */
    public static List<Question> exampleQuestions() {
        List<Question> qs = new ArrayList<Question>();

        List<String> hints = new ArrayList<String>();
        hints.add("Yes");
        hints.add("No");
        hints.add("Maybe");
        hints.add("None of the selected");

        List<String> hints2 = new ArrayList<String>();
        hints.add("yes");
        hints.add("yes");
        hints.add("yes");
        hints.add("yes"); 

        List<String> hints3 = new ArrayList<String>();
        hints.add("3");
        hints.add("4");
        hints.add("5");
        hints.add("6");

        qs.add(new Question("Are you happy?", hints, "yes"));
        qs.add(new Question("Is this going to be the best game ever?", hints2, "yes"));
        qs.add(new Question("How many hints are shown?", hints3, "4"));
        qs.add(new Question("How many days in a week?", hints3, "7"));
        qs.add(new Question("How many fingers on your hand?", hints3, "5"));

        return qs;
    }
}
