
/**
 * This class will parse questions into a list through a JSON formatted record. 
 * 
 * JSON:
 * @see https://en.wikipedia.org/wiki/JSON
 * 
 *
 */
public class QuestionParser {
	
	public static void main(String[] args){
		String a = "abcbdbeb";
		
		String[] split = a.split("b", 2);
		
		for(int i = 0; i < split.length; i++){
			System.out.println(split[i]);
		}
	}

}
