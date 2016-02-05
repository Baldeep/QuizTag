import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class implements the quiz master. Given a set of questions, it will 
 * return randomly picked questions out of the pool. 
 */
public class QuizMaster {

	String quizName;
	List<Question> questionPool;
	List<Question> questionsAsked;
	
	/*
	 * The QuizMaster is the class which holds all the questions once they have 
	 * been parsed into a list. Additionally a name for the quiz can be saved 
	 * here for later printing etc.
	 */
	public QuizMaster(String quizName, List<Question> questionPool){
		this.questionPool = questionPool;
		this.quizName = quizName;
		questionsAsked = new ArrayList<Question>();
	}
	
	/*
	 * Picks a question out of the pool of questions given and returns it. It is
	 * left up to the class using the QuizMaster to extract the data from the 
	 * Question
	 */
	
	public Question getQuestion(){
		// make sure it's not empty
		if(questionPool.isEmpty())
			return null;
		
		// Pick a random question
		int randomNo = new Random().nextInt(questionPool.size());
		Question q =  questionPool.get(randomNo);
		
		// has it been asked before? pick another one!
		while(!questionsAsked.contains(q)){
			randomNo = new Random().nextInt(questionPool.size());
			q =  questionPool.get(randomNo);
		}
		
		// mark it as asked
		questionsAsked.add(q);
		
		return q;
	}
	
	/*
	 * This function clears the questions asked so far with a little bit of 
	 * confirmation that it has cleared properly.
	 */
	public boolean clearAskedQuestions(){
		questionsAsked.clear();
		return questionsAsked.isEmpty();
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
	
	

}
