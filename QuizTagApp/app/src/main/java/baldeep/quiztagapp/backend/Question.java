package baldeep.quiztagapp.backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This class incorporates everything within a question record
 * @question The question to be asked
 * @hints An array holding multiple string values which are possible answers to the question
 * @answer The correct answer to the question, if this is not in the hints array, it will be added
 */
public class Question implements Serializable{
	String question;
	List<String> hints;
	String answer;
	Random r = new Random();

	public Question(String question, List<String> hints, String answer){

		this.question = question;
		this.answer = answer;
		this.hints = hints;
		if(!hints.contains(answer)){
			hints.add(answer);
		}

		// no need to test hints as it can only be null if answer is also null
		assert(question != null && answer != null);
	}

	public boolean checkAnswer(String choice){
		if(choice!=null && choice.equals(answer)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object other){
		if (other == this) {
			return true;
		}

		if (other instanceof Question) {
			Question q = (Question) other; // cast if it is an instance of Question

			// Check hashcodes, always check hashcodes
			if (hashCode() != other.hashCode()) {
				return false;
			}

			// Check the strings, i.e. the question and the answer
			// A differently worded question will not return true
			if (!q.getQuestion().equals(question)){
				return false;
			}
			
			// making this separate for to have less branches for unit tests
			if(!q.getAnswer().equals(answer)){
				return false;
			}

			// Check hints arrays are the same size
			
			if(hints.size() != q.getHints().size()){
				return false;
			}
			// Check the same answers are in each array
			for (int i = 0; i < hints.size(); ++i) {
				if (!q.getHints().contains(hints.get(i))||!hints.contains(q.getHints().get(i))) {
					return false;
				}
			}
			
			// all tests passed, return true
			return true;
		} else {
			return false; // not an instance of, return false
		}
	}


	@Override
	public int hashCode() {
		return (1993 * (42 + getQuestion().length()) + 7 * getAnswer().length());
	}

	public String getQuestion(){
		return question;
	}

	public List<String> getHints(){
		// if there's more than 4 hints available
		if(hints.size() > 4){
			// pick 3 random hints
			List<String> newHints = new ArrayList<String>();
			newHints.add(answer);

			int limiter = 0; // this is basically a "regulator" in case there
			// are a lot of repeated answers and the array is never filled
			while(newHints.size() < 4 && limiter < 20)
			{
				String random = hints.get(r.nextInt(hints.size()));

				if(!newHints.contains(random)){
					newHints.add(random);
				}
				limiter++;
			}

			// If we filled the array successfully, return it other wise just
			// pick every fourth answer and return it
			if(newHints.size() == 4){
				return newHints;
			} else {
				int sizeReached = newHints.size(); // Save for stats later

				newHints.clear(); // refresh array
				newHints.add(answer);

				int fourth = hints.size()/4;
				for(int i = fourth; i < hints.size()-1; i += fourth){
					newHints.add(hints.get(i));
				}

//				System.out.println("Hints are every fourth hint from list "
//						+ "provided as the random selection hint was hit, please"
//						+ " adjust the list of hints for a better selection");
//				System.out.println("Hints available: " + hints.size());
//				System.out.println("Hints found before limit: " + sizeReached);
//				System.out.println("Hints returned: " + newHints.size());

				return newHints;
			}
		}

		return hints;
	}

	public String getAnswer(){
		return answer;
	}
	
	public void setHints(List<String> newHints){
		hints = newHints;
	}

}