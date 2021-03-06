package baldeep.quiztagapp.backend;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable{

    @SerializedName("questionString")
	private String question;
    @SerializedName("hints")
	private List<String> hintPool;
    @SerializedName("hintlist")
	private List<String> hints;
	private String answer;

	private int hintArraySize = 4;

    /**
     * This class acts as a Data Structure which provides all the data required to be held by a
     * question in the quiz.
     * @param question The question being asked. Cannot be null.
     * @param hintPool The list of available hints for the above question
     * @param answer The answer to the question. Cannot be null.
     */
	public Question(String question, List<String> hintPool, String answer){
		this.question = question;
		this.answer = answer;
		this.hintPool = hintPool;


		if(this.question == null){
			this.question = "Question is null";
		}

		if(this.answer == null){
			this.answer = "Answer is null";
		}

		if(this.hintPool == null){
            System.out.println("Question, hint pool is null");
			this.hintPool = new ArrayList<>();
		}

        this.hints = findHints();
	}

    /**
     * Checks whether the given answer matches the answer for this question
     * @param choice The answer to be checked
     * @return True if the answer given matched the stored answer, false otherwise
     */
	public boolean checkAnswer(String choice){
		// for some reason Android Studio really likes to have little lines of code...
		return choice!=null && choice.equals(answer);
	}

    /**
     * Getter for the question field
     * @return A string containing the question held
     */
	public String getQuestion(){
		return question;
	}

    /**
     * Getter fot the answer field
     * @return A string containing the answer field
     */
	public String getAnswer(){
		return answer;
	}

    /**
     * Getter for the hints
     * @return An array of a maximum default size of 4 or one set by the setHintsArraySize() method
     */
	public List<String> getHints(){
		return hints;
	}

    /**
     * Changed the max size of the array which stores the number of hints. If the number of hints
     * available is less than the max size, then no extra hints will be shown.
     */
    public void setHintsArraySize(int newMaxSize) {
        this.hintArraySize = newMaxSize;
    }

    /**
     * Changes the pool of hints available for the question
     * @param newHints The new pool of hints to pick from.
     * @return True if the hints were changed, false if the new hint pool was null.
     */
	public boolean setHints(List<String> newHints){
        if(newHints != null) {
            this.hintPool = newHints;
            hints = findHints();
            return true;
        }
        return false;
	}

    /**
     * Only four hints will be returned at any one time, this method shuffles the hint pool and
     * finds a new set of hints from the hint pool than the ones selected at the creation of the
     * question.
     * @return returns an array of hints taken from the hintpool of a default size of 4 unless
     * specified by the Constants.HINTS_ARRAY_SIZE integer.
     */
	public List<String> resetHints(){
		hints = findHints();
		return hints;
	}

    /**
     * This method selects a number of hints specified by the maxArraySize variable. If the number
     * of questions available is less than the max number, it will return all available hints.
     * To change the number of hints returned call then Question#setHintsArraySize()
     * @return A list of strings, taken from the hint pool
     */
	private List<String> findHints(){
		List<String> newHints = new ArrayList<>();

        if(!hintPool.contains(answer)){
            hintPool.add(answer);
        }

		if(hintPool.size() <= hintArraySize){
            Collections.shuffle(hintPool);
            return hintPool;
		} else {
			newHints.add(answer);

			int i = 0;
			while(i < hintPool.size() && newHints.size() < hintArraySize){
				if(!newHints.contains(hintPool.get(i))){
					newHints.add(hintPool.get(i));
				} else if(Collections.frequency(hintPool, hintPool.get(i)) > 1) {
                    newHints.add(hintPool.get(i));
                }
				i++;
			}

			Collections.shuffle(newHints);
			return newHints;
		}
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
			if (!q.getQuestion().equals(question)){
				return false; // A differently worded question will not return true
			}
			if(!q.getAnswer().equals(answer)){
				return false;
			}

			// Check hints arrays are the same
			if(hintPool.size() != q.getHints().size()){
				return false;
			}
			// Check the same answers are in each array
			for (int i = 0; i < hintPool.size(); ++i) {
				if (!q.getHints().contains(hintPool.get(i))
                        ||!hintPool.contains(q.getHints().get(i))) {
					return false;
				} else if(Collections.frequency(hintPool, hintPool.get(i))
                        != Collections.frequency(q.getHints(), hintPool.get(i))){
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
		return (1993 * (42 + getQuestion().length()) + ((7 + hintPool.size()) * getAnswer().length()));
	}
}