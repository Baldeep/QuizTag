package baldeep.quiztagapp.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.Exceptions.NullObjectException;


public class QuizMaster extends Observable implements Serializable, Observer{

    private final int POINTS = 20;

    private PowerUps powerUps;
    private QuestionPool questionPool;

    private boolean hintsRevealed;
    private int wrongChoices = 0;

    private List<Observer> observers = new ArrayList<>();

    /**
     * The default instantiation of QuizMaster starts the game with no powerups or points.
     *
     * The QuizMaster class wraps around the QuestionPool class in a way as it uses the basic
     * methods defined in that class to incorporate the use of power ups.
     * @param qp The name QuizPool for the QuizMaster to Control
     */
    public QuizMaster(QuestionPool qp) throws NullObjectException{

        if(qp == null){
            throw new NullObjectException("QuestionPool is null");
        }
        this.powerUps = new PowerUps(0, 0, 0);
        this.questionPool = qp;
        powerUps.attach(this);
    }

    /**
     * The QuizMaster class wraps around the QuestionPool class in a way as it uses the basic
     * methods defined in that class to incorporate the use of power ups.
     * @param qp The name QuizPool for the QuizMaster to Control
     */
    public QuizMaster(QuestionPool qp, PowerUps powerUps) throws NullObjectException{
        if(qp == null){
            throw new NullObjectException("QuizMaster: QuestionPool is null");
        }
        if(powerUps == null){
            throw new NullObjectException("QuizMaster: PowerUps is null");
        }

        this.powerUps = powerUps;
        this.questionPool = qp;
    }

    /**
     * This method picks a new question from the QuestionPool and increments the question number
     */
    public void setNextQuestion(){
        if(questionPool.askQuestion() != null) {
            System.out.println("QuizMaster setNextQuestion: " + questionPool.getCurrentQuestion().getQuestion());
            hintsRevealed = false;
            wrongChoices = 0;
        }
        notifyAllObservers();
    }

    /**
     * Returns the number of the current question
     * @return Returns the number of the current question
     */
    public int getCurrentQuestionNumber(){
        return questionPool.getCurrentQuestionNumber();
    }

    /**
     * Returns the question from the currently selected question
     * @return Returns the question from the currently selected question
     */
    public String getQuestionString(){
        return questionPool.getCurrentQuestion().getQuestion();
    }

    /**
     * Checks the answer against the one provided by the QuestionPool and increments the points
     * @see QuestionPool#checkAnswer(String);
     * @param answer The answer given to check
     * @return Returns
     */
    public boolean checkAnswer(String answer){
        if(questionPool.checkAnswer(answer)) {
            powerUps.setPoints(powerUps.getPoints() + POINTS);
            notifyAllObservers();
            return true;
        } else {
            wrongAnswer();
            return false;
        }
    }

    /**
     * This is a little additional feature which revels the hints if the user gets the answer
     * wrong 5 times. This counter is reset when a new question is picked
     */
    private void wrongAnswer(){
        wrongChoices++;
        if(wrongChoices == 4){
            hintsRevealed = true;
            notifyAllObservers();
        }
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
     * Exchanges points for skip power ups
     * @return Returns true if the skips were sucessfully added, false otherwise
     */
    public boolean buySkips(){
        if(powerUps.getPoints() >= powerUps.getSkipsCost()) {
            powerUps.setPoints(powerUps.getPoints() - powerUps.getSkipsCost());
            powerUps.setSkips(powerUps.getSkips() + 1);
            notifyAllObservers();
            return true;
        }
        return false;
    }

    /**
     * Sets the hints to be revealed if there are enough hints avalable to do so
     * @return The new value for the hints available
     */
    public int revealHints(){
        if (powerUps.getHints() > 0) {
            hintsRevealed = true;
            powerUps.setHints(powerUps.getHints() - 1);

            notifyAllObservers();
        }
        return powerUps.getSkips();
    }

    /**
     * Sets the hints to be revelaled and removes one from the total number of skips
     * @return Returns true if the skips were sucessfully added, false otherwise
     */
    public boolean buyHints(){
        if(powerUps.getPoints() >= powerUps.getHintsCost()) {
            powerUps.setPoints(powerUps.getPoints() - powerUps.getHintsCost());
            powerUps.setHints(powerUps.getHints() + 1);
            notifyAllObservers();
            return true;
        }
        return false;
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
    public void resetQuiz(){
        hintsRevealed = false;
        wrongChoices = 0;
        questionPool.clearAskedQuestions();
        notifyAllObservers();
    }

    /**
     * Returns the hints available to the current question
     * @return An array holding the hints available for the current question
     */
    public List<String> getHints(){
        return questionPool.getCurrentQuestion().getHints();
    }

    /**
     * Gets the PowerUps currently held by the QuizMaster. The QuizMaster doesn't provide additional
     * methods to set or get the powerups, while that would be safer, hints and skips may need to be
     * set outwith the QuizMaster
     * @return The current PowerUps class held by the quizmaster
     */
    public PowerUps getPowerUps(){
        return powerUps;
    }

    /**
     * Returns the name of the quiz held by the QuizMaster
     * @return A string holding the name of the quiz currently being played
     */
    public String getQuizName(){
        return questionPool.getQuizName();
    }


    public QuestionPool getQuestionPool(){
        return questionPool;
    }

    public void setNewQuiz(QuestionPool qp){
        this.questionPool = qp;
        resetQuiz();
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

    /**
     * Returns the number of points earned each time a question is answered correctly
     * @return The number of points earned by a correct answer
     */
    public int getPointsPerQuestion(){
        return POINTS;
    }

    @Override
    public void update(Observable observable, Object data) {
        notifyAllObservers();
    }


    /**
     * Starts the quiz at a specified question number
     * @param questionNo the number to start the quiz from
     * @return An integer representing the new question number. If everything the quiz jumps to the
     * question number correctly, then it should return the same number as was input
     */
    public int goToQuestion(int questionNo) {
        // check just in case this method was called in random mode
        questionPool.goToQuestion(questionNo);
        return questionPool.getCurrentQuestionNumber();
    }

    /**
     * Returns a boolean value representing whether the questionpool plays a random quiz or not
     * @return True if the quiz held is random, false if it's a story quiz
     */
    public boolean isRandomQuiz(){
        return questionPool.isRandom();
    }

    public void cheat() {
        powerUps.setHints(10);
        powerUps.setSkips(10);
        powerUps.setPoints(150);
        notifyAllObservers();
    }
}
