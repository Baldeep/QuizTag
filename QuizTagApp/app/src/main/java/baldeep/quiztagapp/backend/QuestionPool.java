package baldeep.quiztagapp.backend;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuestionPool implements Serializable{

    private String quizName;
    @SerializedName("QuestionPool")
    private List<Question> questionPool;
    private List<Question> questionsAsked = new ArrayList<>();;
    private Question currentQuestion;
    private int currentQuestionNo;
    private boolean random;

    /**
     * The question pool holds a non-empty list of questions and helps with the question handling
     * tasks, without dealing with any of the power-ups etc
     * @param quizName The name for the quiz
     * @param questionPool The pool of questions
     */
    public QuestionPool(String quizName, List<Question> questionPool, boolean random){

        assert(questionPool.size()>0);

        this.questionPool = questionPool;
        // remove null questions
        for(Question q : this.questionPool){
            if(q == null)
                this.questionPool.remove(q);
        }

        this.quizName = quizName;
        if(this.quizName == null){
            quizName = "Quiz Name Null";
        }

        questionsAsked = new ArrayList<>();
        this.random = random;
    }

    /**
     * The question pool holds a list of questions and helps with the question handling tasks,
     * without dealing with any of the power-ups etc. The questions selected will be picked at random
     * @param questionPool The pool of questions to store
     */
    public QuestionPool(List<Question> questionPool){

        assert(questionPool.size()>0);

        this.questionPool = questionPool;
        // remove null questions
        for(int i = 0; i < this.questionPool.size(); i++){
            if(this.questionPool.get(i) == null) {
                this.questionPool.remove(i);
            }
        }

        this.quizName = "Nameless Quiz";
        questionsAsked = new ArrayList<>();
        random = true;
    }

    /**
     * This question picks a random question from the question pool and returns it.
     * @return If the quiz is random, it will return a randomly selected question and restart once
     * all questions have been asked, however if it is a story mode, it will return null once all
     * questions have been asked. Will also return null for an error.
     */
    public Question askQuestion(){
        Question q = null;

        // Deal with the empty array
        if(questionPool.isEmpty()) {
            currentQuestionNo = -1;
            return null;
        }
       /* if(random && questionPool.isEmpty()) {
            System.out.println("Questionpool empty **********");
            questionPool.addAll(questionsAsked);
            questionsAsked.clear();
            currentQuestionNo++;
        } else if(!random && questionPool.isEmpty()){
            currentQuestionNo = -1;
            return null;
        }*/

        // Pick out a question
         else if(!questionPool.isEmpty()) {

            if (random) {
                Collections.shuffle(questionPool);
            }
            q = questionPool.get(0);

            // mark it as asked
            currentQuestion = q;
            questionsAsked.add(q);
            questionPool.remove(q);
            currentQuestionNo++;
        }
        return q;
    }

    /**
     * This class resets the array of questions asked.
     * @return True if the questions asked were sucessfully cleared, false otherwise
     */
    public boolean clearAskedQuestions(){
        questionsAsked.clear();
        currentQuestionNo = 0;
        return questionsAsked.isEmpty();
    }

    /**
     * This method checks the given string against the answer string held for the currently selected
     * question.
     * @param answer The string to check
     * @return Returns true if the string given matches the answer of the currently selected question,
     * otherwise it will return false.
     */
    public boolean checkAnswer(String answer){
        if(currentQuestion != null)
            return currentQuestion.checkAnswer(answer);
        return false;
    }

    /**
     *  For a non randomized quiz, this method will roll through the list of questions until it gets
     *  to the specified question number
     * @param questionNo
     */
    public void goToQuestion(int questionNo) {
        if(!random){
            while(currentQuestionNo < questionNo){
                askQuestion();
            }
        }
    }

    /**
     * Getter for the QuizName
     * @return A string containing the name of the quiz
     */
    public String getQuizName(){
        return quizName;
    }

    /**
     * Setter for the QuizName
     * @param newName A string containing the new name for the quiz
     */
    public void setQuizName(String newName){
        this.quizName = newName;
    }

    /**
     * Getter for the question pool
     * @return A List of all Questions held by the pool
     */
    public List<Question> getQuestionPool(){
        return questionPool;
    }

    /**
     * A setter for the question pool, to add questions, use getQuestionPool, add questions, and
     * then set the question pool
     * @param newQuestions A list of Questions to change the question pool to
     */
    public void setQuestionPool(List<Question> newQuestions){
        this.questionPool = newQuestions;
    }

    /**
     * Returns whether or not the quiz will select questions randomly or not
     * @return True if the questions are selceted randomly, false otherwise
     */
    public boolean isRandom(){
        return random;
    }

    /**
     * Allows the setting of whethe the questions seleceted need to be randomly picked or not
     * @param random
     */
    public void setRandom(boolean random){
        this.random = random;
    }

    /**
     * Returns the total number of questions left to be asked by this class.
     * @return
     */
    public int getQuestionLeftSize(){
        return questionPool.size();
    }

    /**
     * Returns the total number of questions held by the question pool
     * @return The total number of questions held by the question pool, including questions left
     * to ask, and questions which have already been asked
     */
    public int getQuestionPoolSize(){
        return questionPool.size();// + questionsAsked.size();
    }

    /**
     * Return the current Question number
     * @return The number of the current question, generally = (number of questions asked + 1)
     */
    public int getCurrentQuestionNumber() {
        return currentQuestionNo;
    }

    /**
     * Return the current question being asked
     * @return
     */
    public Question getCurrentQuestion(){
        return currentQuestion;
    }

    /**
     * Returns the answer to the current question
     * @return
     */
    public String getCurrentAnswer(){
        return currentQuestion.getAnswer();
    }
}

