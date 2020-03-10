import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	public static void main(String[] args) throws Exception {
		String pathName = "C:\\Coding\\Eclipse Workspace\\Notenspiegel\\src\\REM noten WS1920.txt";
		Path filePath =Paths.get(pathName); //file location
		String regex = "\\d\\.\\d"; //this regex needs to match some string representing a grade
		List<Double> allGrades = new ArrayList<>();
		List<Double> allGradesNoFive = new ArrayList<>();
				
		BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		String str;
		
		System.out.println("Parsing File \"" + filePath.getFileName() + "\"...");
		
		int[] gradeCount = new int[11]; // 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0 reversed
		String[] gradeNames = {"5.0","4.0","3.7","3.3","3.0","2.7","2.3","2.0","1.7","1.3","1.0"};
		while((str = reader.readLine()) != null) {
			matcher = pattern.matcher(str);
			if(matcher.find()) {
				String match = str.substring(matcher.start(), matcher.end());
				match = match.replace(',', '.'); // for decimal formats
				double current = Double.parseDouble(match);
				allGrades.add(current);
				if(current == 5.0) 
					gradeCount[0]++;
				else if(current == 4.0)
					gradeCount[1]++;
				else if(current == 3.7)
					gradeCount[2]++;
				else if(current == 3.3)
					gradeCount[3]++;
				else if(current == 3.0)
					gradeCount[4]++;
				else if(current == 2.7)
					gradeCount[5]++;
				else if(current == 2.3)
					gradeCount[6]++;
				else if(current == 2.0)
					gradeCount[7]++;
				else if(current == 1.7)
					gradeCount[8]++;
				else if(current == 1.3)
					gradeCount[9]++;
				else if(current == 1.0)
					gradeCount[10]++;
				if(current != 5.0)
					allGradesNoFive.add(current);
			}
		}
		
		reader.close();
		System.out.println("Done.");
		
		double average = 0.0;
		for(double d : allGrades) {
			average += d;
		}
		
		double averageNoFive = 0;
		for(double d : allGradesNoFive) {
			averageNoFive += d;
		}
		
		average = average / allGrades.size();
		averageNoFive = averageNoFive / allGradesNoFive.size();
		
		System.out.println("Found " + allGrades.size() + " grades.\n");
		double percentileTotal = 0;
		double percentilePassing = 0;
		System.out.println("Grade\tOccurences\tOf Total\tPercentile\tOf Passing\tPercentile");
		for(int i = 0; i < gradeCount.length; i++) {
			double percentTotal = gradeCount[i] / (double) allGrades.size() * 100;
			double percentNoFive = gradeCount[i] / (double) allGradesNoFive.size() * 100;
			percentileTotal += percentTotal;
			if(i != 0) {
				percentilePassing += percentNoFive;
			}
			System.out.println(gradeNames[i] + "\t" 
					+ gradeCount[i] + "\t\t" 
					+ Math.round(percentTotal * 100) / 100.0 + "%" + "\t\t"
					+ Math.round(percentileTotal) + "\t\t"
					+ (i == 0 ? "-" : Math.round(percentNoFive * 100) / 100.0 + "%") + "\t\t" // 0th is 5.0's. No point displaying 5.0's / all Grades w.o. 5.0's
					+ (i == 0 ? "-" : Math.round(percentilePassing))); 
		}
		System.out.println("\nThe average grade including 5.0's is: " + Math.round(average * 10) / 10.0);
		System.out.println("The average grade excluding 5.0's is: " + Math.round(averageNoFive * 10) / 10.0);
	}
}
