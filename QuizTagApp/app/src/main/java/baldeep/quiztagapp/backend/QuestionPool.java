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
    private List<Question> questionsAsked;
    private Question currentQuestion;
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
        for(Question q : this.questionPool){
            if(q == null)
                this.questionPool.remove(q);
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
        if(random && questionPool.isEmpty()) {
            System.out.println("Questionpool empty **********");
            questionPool.addAll(questionsAsked);
            questionsAsked.clear();
        } else if(!random && questionPool.isEmpty()){
            return null;
        }

        // Pick out a question
        if(!questionPool.isEmpty()) {

            if (random) {
                Collections.shuffle(questionPool);
            }
            q = questionPool.get(0);

            // mark it as asked
            currentQuestion = q;
            questionsAsked.add(q);
            questionPool.remove(q);
        }
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
     *
     * @param currentQuestion
     */
    public void setCurrentQuestionNo(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
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



    public boolean isRandom(){
        return random;
    }

    public void setRandom(boolean random){
        this.random = random;
    }

    public int getQuestionPoolSize(){
        return questionPool.size();
    }

}

