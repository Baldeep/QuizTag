package baldeep.quiztagapp.backend;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileParser implements Serializable{

    public static void main(String[] args){
        /*List<String> yolo = new ArrayList<String>();
        if(yolo.get(0) == null){
            System.out.println("null found");
        }*/
    }


    public List<Question> extractQuestions(String fileName) {
        //String input = parseFile(fileName);

        List<Question> qs = new ArrayList<Question>();

        List<String> hints = new ArrayList<String>();
        hints.add("Yes");
        hints.add("No");
        hints.add("Maybe");
        hints.add("None of the selected");

        List<String> hints2 = new ArrayList<String>();
        hints.add("yes");
        hints.add("yes");
        hints.add("yes");
        hints.add("yes");

        List<String> hints3 = new ArrayList<String>();
        hints.add("3");
        hints.add("4");
        hints.add("5");
        hints.add("6");

        Question q = new Question("Are you happy?", hints, "yes");
        qs.add(q);
         q = new Question("Is this going to be the best game ever?", hints2, "yes");
        qs.add(q);
         q = new Question("How many hints are shown?", hints3, "4");
        qs.add(q);
         q = new Question("How many days in a week?", hints3, "7");
        qs.add(q);
         q = new Question("How many fingers on your hand?", hints3, "5");
        qs.add(q);

/*        qs.add(new Question("Are you happy?", hints, "yes"));
        qs.add(new Question("Is this going to be the best game ever?", hints2, "yes"));
        qs.add(new Question("How many hints are shown?", hints3, "4"));
        qs.add(new Question("How many days in a week?", hints3, "7"));
        qs.add(new Question("How many fingers on your hand?", hints3, "5"));*/

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
            System.out.println("Parsing file: File not found");
        } catch (IOException e) {
            System.out.println("Parsing file: Buffered Reader IO exception");
        }

        return input;
    }
}
