package baldeep.quiztagapp.backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuestionPool implements Serializable{

    private String quizName;
    private List<Question> questionPool;
    private List<Question> questionsAsked;

    private Question currentQuestion;

    /**
     * The question pool holds a non-empty list of questions and helps with the question handling
     * tasks, without dealing with any of the power-ups etc
     * @param quizName The name for the quiz
     * @param questionPool The pool of questions
     */
    public QuestionPool(String quizName, List<Question> questionPool){

        assert(questionPool.size()>=1);

        this.questionPool = questionPool;
        this.quizName = quizName;
        questionsAsked = new ArrayList<>();
    }

    /**
     * The question pool holds a list of questions and helps with the question handling tasks,
     * without dealing with any of the power-ups etc.
     * @param questionPool The pool of questions
     */
    public QuestionPool(List<Question> questionPool){

        assert(questionPool.size()>=1);

        this.questionPool = questionPool;
        this.quizName = "";
        questionsAsked = new ArrayList<>();
    }

    /**
     * This question picks a random question from the question pool and returns it.
     * @return A randomly selected Question
     */
    public Question askQuestion(){
        Question q;

        // make sure it's not empty
        if(questionPool.isEmpty()) {
            System.out.println("Questionpool empty **********");
            questionPool.addAll(questionsAsked);
            questionsAsked.clear();
        }

        Collections.shuffle(questionPool);
        q = questionPool.get(0);

        /* Redacting the following as a check is made to ensure the list isn't empty at the start
         *of the method, therefore it's simpler to just shuffle the array and return the first
         * element found
         */
        /*
        System.out.println("Selecting a random question from pool size: " + questionPool.size());
        if(questionPool.size() == 1){
            q = questionPool.get(0);
        } else {
            // Pick a random question
            int randomNo = new Random().nextInt(questionPool.size() - 1);
            System.out.println("Looking for another question " + randomNo + " **************");
            q = questionPool.get(randomNo);

            // has it been asked before? pick another one!
            while (questionsAsked.contains(q)) {
                randomNo = new Random().nextInt(questionPool.size() - 1);
                System.out.println("Looking for another question " + randomNo + " **************");
                q = questionPool.get(randomNo);
            }
        }
        */

        // mark it as asked
        currentQuestion = q;
        questionsAsked.add(q);
        questionPool.remove(q);

        return q;
    }

    /**
     * This class resets the array of questions asked.
     * @return True if the questions asked were sucessfully cleared, false otherwise
     */
    public boolean clearAskedQuestions(){
        questionsAsked.clear();
        return questionsAsked.isEmpty();
    }

    public boolean checkAnswer(String answer){
        return currentQuestion.checkAnswer(answer);
    }

    public String getQuizName(){
        return quizName;
    }

    public void setQuizName(String newName){
        this.quizName = newName;
    }

    public List<Question> getQuestionPool(){
        return questionPool;
    }

    public void setQuestionPool(List<Question> newQuestions){
        this.questionPool = newQuestions;
    }

    public List<Question> getAskedQuestions(){
        return questionsAsked;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

}

