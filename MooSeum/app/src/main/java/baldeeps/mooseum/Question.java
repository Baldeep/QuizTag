package baldeeps.mooseum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This class incorporates everything within a question record
 * @question The question to be asked
 * @hints An array holding multiple string values which are possible answers to the question
 * @answer The correct answer to the question, if this is not in the hints array, it will be added
 */
public class Question {
    String question;
    List<String> hints;
    String answer;

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
            System.out.println(q.getQuestion());
            System.out.println(hashCode());
            System.out.println(q.hashCode());
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
            if(hints.size() != q.getHints().size())
                return false;
            // Check the same answers are in each array
            for (int i = 0; i < hints.size(); ++i) {
                if (!q.getHints().contains(hints.get(i))||!hints.contains(q.getHints().get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
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
        return hints;
    }

    public String getAnswer(){
        return answer;
    }

}
