package baldeep.quiztagapp.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class QuizMaster implements Serializable{

    private final int POINTS = 20;
    private int points;
    private int hints;
    private int skips;
    private boolean hintsRevealed;

    private int currentQuestionNumber;
    private String quizName;
    private String fileName;
    private QuestionPool qp;
    private Question currentQuestion;

    List<IObserver> observers = new ArrayList<>();

    public QuizMaster(String quizName, String fileName){
        this.quizName = quizName;
        this.fileName = fileName;
        points = 0;
        hints = 200;
        skips = 200;
        currentQuestionNumber = 0;
        qp = new QuestionPool(quizName, new FileParser().extractQuestions(fileName));
        setNextQuestion();
    }

    public void setNextQuestion(){
        currentQuestion = qp.askQuestion();
        hintsRevealed = false;
        currentQuestionNumber++;
        notifyAllObservers();
    }

    public int getCurrentQuestionNumber(){
        return currentQuestionNumber;
    }

    public String getQuestionString(){
        return currentQuestion.getQuestion();
    }

    public boolean checkAnswer(String answer){
        if(currentQuestion.checkAnswer(answer)) {
            points += POINTS;
            return true;
        } else
            return false;
    }

    public int skipQuestion(){
        if(skips > 1) {
            setNextQuestion();
            skips--;
        }
        return skips;
    }

    public List<String> revealHints(){
        if(hints > 1) {
            hints--;
            hintsRevealed = true;
            notifyAllObservers();
            return currentQuestion.getHints();
        } else
            return null;
    }

    public boolean hintsAvailable(){
        return hintsRevealed;
    }

    public List<String> getHintsToCurrentQuestion(){
        return currentQuestion.getHints();
    }

    public String getAnswer(){
        return currentQuestion.getAnswer();
    }

    public int getHintCount(){
        return hints;
    }

    public int getSkipCount(){
        return skips;
    }

    public int getPoints(){
        return points;
    }

    public String getQuizName(){
        return qp.getQuizName();
    }

    public boolean resetQuiz(){
        currentQuestionNumber = 0;
        return qp.clearAskedQuestions();
    }

    public void setNewQuiz(){
        qp = new QuestionPool(quizName, new FileParser().extractQuestions(fileName));

    }

    public void setHintCount(int hintCount){
        hints = hintCount;
    }

    public void setSkipCount(int skipCount){
        skips = skipCount;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void attach(IObserver o){
        observers.add(o);
    }

    public void notifyAllObservers(){
        for(IObserver o : observers){
            o.update();
        }
    }
}
