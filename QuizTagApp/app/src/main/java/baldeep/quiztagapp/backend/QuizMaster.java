package baldeep.quiztagapp.backend;

import java.io.Serializable;
import java.util.List;


public class QuizMaster implements Serializable{


    private final int POINTS = 20;
    private int points;
    private int hints;
    private int skips;

    private int currentQuestionNumber;

    private String quizName;
    private String fileName;
    private QuestionPool qp;
    private Question currentQuestion;

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

    public void setNewQuiz(){
        qp = new QuestionPool(quizName, new FileParser().extractQuestions(fileName));
    }

    public void setNextQuestion(){
        currentQuestion = qp.getQuestion();
        currentQuestionNumber++;
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

    public List<String> getHints(){
        if(hints > 1) {
            hints--;
            return currentQuestion.getHints();
        } else
            return null;
    }

    public boolean resetQuiz(){
        currentQuestionNumber = 0;
        return qp.clearAskedQuestions();
    }

    public int getCurrentQuestionNumber(){
        return currentQuestionNumber;
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

    public void setHintCount(int hintCount){
        hints = hintCount;
    }

    public void setSkipCount(int skipCount){
        skips = skipCount;
    }

    public void setPoints(int points){
        this.points = points;
    }
}
