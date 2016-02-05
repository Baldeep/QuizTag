
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON_Parser {

	public static void main(String[] args){
		String data = parseFile("Questions.txt");
		List<Question> questions = extractQuestions(data);
		System.out.println(5/4);
	}
	
	
	public static List<Question> extractQuestions(String input) {
		List<Question> qs = new ArrayList<Question>();
		
		List<String> hints = new ArrayList<String>();
		hints.add("yes");
		hints.add("No");
		hints.add("maybe");
		hints.add("meh");
		
		List<String> hints2 = new ArrayList<String>();
		hints.add("yes");
		hints.add("yes");
		hints.add("yes");
		hints.add("yes");
		return qs;
		
		/*
		if(input.isEmpty())
			return null;
		
		// Read the input in, so now to chop it up
		String[] inputSplit = input.split(":", 2);
		System.out.println(inputSplit[0]);
		System.out.println(inputSplit[1] + "\n*******************************");
		
		// make sure it's read the right thing
		if(inputSplit[0].contains("quiz")){
			String[] data = inputSplit[1].split(":", 2);
			
			// Make sure we're going to make a question class
			if(data[0].contains("question")){
				// Let's read the question
				String[] question = data[1].split(",", 2); 
				for(int i = 0; i < data.length; i++){
					System.out.println(question[i]);
				}
			}
		}
		return null;
		*/
	}

	public static String parseFile(String fileName){
		String line;
		String input = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while((line = br.readLine()) != null){
				input += line.trim();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Parsing JSON file: File not found");
		} catch (IOException e) {
			System.out.println("Parsing JSON file: Buffered Reader IO exception");
		}
		
		return input;
	}
}
