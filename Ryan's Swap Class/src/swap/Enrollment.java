package swap;

import java.util.*;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class Enrollment
{
	private List<ClassData> myClasses;
	
	public Enrollment()
	{
		myClasses = new ArrayList<>();
	}
	
	public void add(ClassData toAdd) throws ClassException
	{
		if(toAdd.availableSeats < 1)
		{
			throw new ClassException(toAdd.className + "-" + toAdd.section + " is full.");
		}else if(myClasses.contains(toAdd))
		{
			throw new ClassException("Already enrolled in " + toAdd.className);
		}else{
			toAdd.enrolled = true;
			toAdd.availableSeats--;
			myClasses.add(toAdd);
		}
	}
	
	public void drop(ClassData toDrop) throws ClassException
	{
		if(toDrop.enrolled == true)
		{
			toDrop.enrolled = false;
			toDrop.availableSeats++;
			myClasses.remove(toDrop);
		}else{
			throw new ClassException("Not enrolled in " + toDrop.className + "-" + toDrop.section);
		}
	}
		
	public void swap(ClassData original, ClassData toSwap) throws ClassException
	{
		if(toSwap.availableSeats > 0)
		{
			original.enrolled = false;
			original.availableSeats++;
			myClasses.remove(original);
			toSwap.enrolled = true;
			toSwap.availableSeats--;
			myClasses.remove(toSwap);
			
			System.out.println("Successfully enrolled in " + toSwap.className + "-" + toSwap.section + "!");
		}else{
			throw new ClassException(toSwap.className + "-" + toSwap.section + " is full.");
		}
	}
	
	public void printSchedule()
	{
		for(ClassData cd : myClasses)
			System.out.println(cd.className + "-" + cd.section);
	}
	
	static class ClassData
	{
		String className;
		int section;
		int availableSeats;
		boolean enrolled;
		
		ClassData(String className, int section, int availableSeats)
		{
			this.className = className;
			this.section = section;
			this.availableSeats = availableSeats;
			this.enrolled = false;
		}
		
		ClassData() throws FileNotFoundException, IOException
		{
//			File classData = new File("data/MOCK_DATA.json");
//			
//			FileReader fr = new FileReader(classData);
//			BufferedReader br = new BufferedReader(fr);
//			
//			br.close();
//			fr.close();
		}
	}
	
	public static void main(String[] args) throws ClassException
	{
		Enrollment e = new Enrollment();
		
		ClassData class1 = new ClassData("46A", 1, 10);
		ClassData class2 = new ClassData("46A", 2, 5);
		ClassData class3 = new ClassData("46B",	1, 0);
		ClassData class4 = new ClassData("46B",	2, 1);
		ClassData class5 = new ClassData("COMM20", 1, 2);
		ClassData class6 = new ClassData("COMM20", 2, 0);
		ClassData class7 = new ClassData("COMM20", 3, 15);
		ClassData class8 = new ClassData("COMM20", 4, 12);
		ClassData class9 = new ClassData("PHYS50", 1, 20);
		ClassData class10 = new ClassData("ENGL1A", 1, 6);
		ClassData class11 = new ClassData("ENGL1A", 2, 4);
		ClassData class12 = new ClassData("ENGL1A", 3, 9);
		ClassData class13 = new ClassData("AMS1A", 1, 1);
		ClassData class14 = new ClassData("AMS1A", 2, 0);
		ClassData class15 = new ClassData("BIOL30", 1, 13);
		ClassData class16 = new ClassData("BIOL30", 2, 12);
		ClassData class17 = new ClassData("BIOL30", 3, 2);
		
		e.add(class1);
		e.add(class4);
		e.add(class17);
		
		
		e.printSchedule();
	}
}
