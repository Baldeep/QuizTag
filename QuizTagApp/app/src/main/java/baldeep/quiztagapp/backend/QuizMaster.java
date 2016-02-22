package baldeep.quiztagapp.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class QuizMaster extends Observable implements Serializable, Observer{

    private final int POINTS = 20;
    private PowerUps powerUps;
    private boolean hintsRevealed;

    private int currentQuestionNumber;
    private QuestionPool qp;
    private Question currentQuestion;

    private List<Observer> observers = new ArrayList<>();

    /**
     * The default instantiation of QuizMaster starts the game with no powerups or points.
     *
     * The QuizMaster class wraps around the QuestionPool class in a way as it uses the basic
     * methods defined in that class to incorporate the use of power ups.
     * @param qp The name QuizPool for the QuizMaster to Control
     */
    public QuizMaster(QuestionPool qp){
        this.powerUps = new PowerUps(0, 0, 0);
        currentQuestionNumber = 0;
        this.qp = qp;
        setNextQuestion();
        powerUps.attach(this);
    }

    /**
     * The QuizMaster class wraps around the QuestionPool class in a way as it uses the basic
     * methods defined in that class to incorporate the use of power ups.
     * @param qp The name QuizPool for the QuizMaster to Control
     */
    public QuizMaster(QuestionPool qp, PowerUps powerUps){
        this.powerUps = powerUps;
        currentQuestionNumber = 0;
        this.qp = qp;
        if(qp == null){
            System.out.println("QUESTION POOL IS NULL");
        } else
            setNextQuestion();
    }

    /**
     * This method picks a new question from the QuestionPool and increments the question number
     */
    public void setNextQuestion(){
        currentQuestion = qp.askQuestion();
        hintsRevealed = false;
        currentQuestionNumber++;
        notifyAllObservers();
    }

    /**
     * Returns the number of the current question
     * @return Returns the number of the current question
     */
    public int getCurrentQuestionNumber(){
        return currentQuestionNumber;
    }

    /**
     * Returns the question from the currently selected question
     * @return Returns the question from the currently selected question
     */
    public String getQuestionString(){
        return currentQuestion.getQuestion();
    }

    /**
     * Checks the answer against the one provided by the QuestionPool and increments the points
     * @see QuestionPool#checkAnswer(String);
     * @param answer The answer given to check
     * @return Returns
     */
    public boolean checkAnswer(String answer){
        if(qp.checkAnswer(answer)) {
            powerUps.setPoints(powerUps.getPoints() + POINTS);
            notifyAllObservers();
            return true;
        } else
            return false;
    }

    /**
     * Selects the next question however it removes one from the total number of skips
     * @return Return the new value for skips
     */
    public int skipQuestion(){
        if(powerUps.getSkips() > 0) {
            setNextQuestion();
            powerUps.setSkips(powerUps.getSkips() - 1);
            notifyAllObservers();
        }
        return powerUps.getSkips();
    }

    /**
     * Sets the hints to be revelaled and removes one from the total number of skips
     * @return Return the new value for hints
     */
    public int revealHints(){

        if(powerUps.getHints() > 0 && !hintsRevealed) {
            powerUps.setHints(powerUps.getHints()-1);
            hintsRevealed = true;
            notifyAllObservers();
        }
        return powerUps.getHints();
    }

    /**
     * Shows whether hints should be available or not
     * @return True if reveal hints has been called, false otherwise
     */
    public boolean hintsAvailable(){
        return hintsRevealed;
    }


    /**
     * Resets the question counter and clears the questions asked from the QuestionPool
     * @return True if the questions asked have been cleared successfully, false otherwise
     */
    public boolean resetQuiz(){
        currentQuestionNumber = 0;
        return qp.clearAskedQuestions();
    }

    public List<String> getHints(){
        return currentQuestion.getHints();
    }

    public String getAnswer(){
        return currentQuestion.getAnswer();
    }

    public PowerUps getPowerUps(){
        return powerUps;
    }

    public String getQuizName(){
        return qp.getQuizName();
    }


    public void setNewQuiz(String quizName, String fileName){
        qp = new QuestionPool(quizName, new FileParser().extractQuestions(fileName));

    }

    public void setPowerUps(PowerUps powerUps){
        this.powerUps = powerUps;
    }


    public void attach(Observer o){
        observers.add(o);
    }

    public void notifyAllObservers(){
        for(Observer o : observers){
            o.update(this, null);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        notifyAllObservers();
    }
}
