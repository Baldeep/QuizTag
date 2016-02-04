
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JSON_Parser {

	public static void main(String[] args){
		String data = parseFile("JSON_example.txt");
		List<Question> questions = extractQuestions(data);
	}
	
	private static List<Question> extractQuestions(String data) {
		List<Question> qs = ArrayList<Question>();
		
		// Read the input in, so now to chop it up
		String[] inputSplit = data.split(":", 2);
		System.out.println(inputSplit[0]);
		System.out.println(inputSplit[1] + "\n*******************************");
		
		// make sure it's read the right thing
		if(inputSplit[0].contains("Quiz")){
			String[] data = inputSplit[1].split(":", 2);
			
			for(int i = 0; i < data.length; i++){
				System.out.println(data[i]);
			}
			// Make sure we're goign to make a question class
			if(data[0].contains("Question")){
				
			}
		}
		return null;
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
			e.printStackTrace();
			System.out.println("Parsing JSON file: File not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Parsing JSON file: Buffered Reader IO exception");
		}
		
		return input;
	}
}
