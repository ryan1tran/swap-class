package swap;

import java.util.*;
import java.io.*;

public class Enrollment
{
	private List<ClassData> myClasses;
	private List<ClassData> classOptions;
	
	public Enrollment() throws FileNotFoundException, IOException
	{
		myClasses = new ArrayList<>();
		
		classOptions = new ArrayList<ClassData>();
		
		File classData = new File("data/DATA.txt");
		
		FileReader fr = new FileReader(classData);
		BufferedReader br = new BufferedReader(fr);
		
		String s = br.readLine();
		while(s != null)
		{
			int atChar = s.indexOf('@');
			int hashChar = s.indexOf('#');
			classOptions.add(new ClassData(s.substring(0, atChar), Integer.parseInt(s.substring(atChar + 1, hashChar)), Integer.parseInt(s.substring(hashChar + 1))));
		
			s = br.readLine();
		}
		
		br.close();
		fr.close();
	}
	
	public ClassData findEnrolledClass(String className, int section)
	{
		ClassData found = findEnrolledClass(className, section, myClasses);
		
		if(found == null)
		{
			System.out.println("Class does not exist.");
			return null;
		}
		return found;
	}
	
	private ClassData findEnrolledClass(String className, int section, List<ClassData> myClasses)
	{
		for(ClassData cd : myClasses)
			if(cd.className.equals(className) && cd.section == section)
				return cd;
		return null;
	}
	
	public ClassData findClass(String className, int section)
	{
		ClassData found = findClass(className, section, classOptions);
		
		if(found == null)
		{
			System.out.println("Class does not exist.");
			return null;
		}
		return found;
	}
	
	private ClassData findClass(String className, int section, List<ClassData> myClasses)
	{
		for(ClassData cd : myClasses)
			if(cd.className.equals(className) && cd.section == section)
				return cd;
		return null;
	}
	
	public void add(String name)
	{
		String className = name.substring(0, name.indexOf('-'));
		int section = Integer.parseInt(name.substring(name.indexOf('-') + 1));
		
		ClassData toAdd = findClass(className, section);
		
		for(ClassData cd : myClasses)
			if(cd.className.equals(className) && cd.section != section)
				System.out.println("Cannot be enrolled in two sections of " + toAdd.className + ".");
		
		if(myClasses.contains(toAdd))
		{
			System.out.println("Already enrolled in " + toAdd.className);
		}else if(toAdd.availableSeats < 1)
		{
			System.out.println(toAdd.className + "-" + toAdd.section + " is full.");
		}else{
			toAdd.enrolled = true;
			toAdd.availableSeats--;
			myClasses.add(toAdd);
			System.out.println("Successfully added " + toAdd.className + "-" + toAdd.section + "!");
		}
		
	}
	
	public void drop(String name)
	{
		String className = name.substring(0, name.indexOf('-'));
		int section = Integer.parseInt(name.substring(name.indexOf('-') + 1));
		
		ClassData toDrop = findEnrolledClass(className, section);
		
		if(toDrop.enrolled == true)
		{
			toDrop.enrolled = false;
			toDrop.availableSeats++;
			myClasses.remove(toDrop);
			System.out.println("Sucessfully dropped " + toDrop.className + "-" + toDrop.section + "!");
		}else{
			System.out.println("Not enrolled in " + toDrop.className + "-" + toDrop.section + ".");
		}
	}
		
	public void swap(String originalName, String swapName)
	{
		String originalClass = originalName.substring(0, originalName.indexOf('-'));
		int originalSection = Integer.parseInt(originalName.substring(originalName.indexOf('-') + 1));
		
		String swapClass = swapName.substring(0, swapName.indexOf('-'));
		int swapSection = Integer.parseInt(swapName.substring(swapName.indexOf('-') + 1));
		
		ClassData original = findEnrolledClass(originalClass, originalSection);
		ClassData toSwap = findEnrolledClass(swapClass, swapSection);
		
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
			System.out.println(toSwap.className + "-" + toSwap.section + " is full.");
		}
	}
	
	public void printSchedule()
	{
		System.out.println("Current Schedule: ");
		for(ClassData cd : myClasses)
			System.out.println(cd.className + "-" + cd.section);
	}
	
	public void printClasses()
	{
		System.out.println("List of Courses Available: ");
		for(ClassData cd : classOptions)
			System.out.println(cd.className + "-" + cd.section);
	}
	
	static class ClassData
	{
		List<ClassData> courseOptions;
		
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
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Enrollment e = new Enrollment();
		
		e.add("BIOL30-1");
		
		e.printSchedule();
		
		Scanner scan = new Scanner(System.in);
		
		boolean done = false;
		
		while(!done)
		{
			System.out.println("Choose an number: ");
			System.out.println("1. Add");
			System.out.println("2. Drop");
			System.out.println("3. Swap");
			System.out.println("4. Exit");
			
			System.out.println();
			int input = scan.nextInt();
			System.out.println();
			
			switch(input)
			{
				case 1:
					boolean doneOne = false;
					
					while(!doneOne)
					{
						System.out.println("Adding: Choose an option: ");
						System.out.println("1. Print class options");
						System.out.println("2. Add class (exact name and section required)");
						System.out.println("3. Back");
						
						System.out.println();
						int addInput = scan.nextInt();
						System.out.println();
						
						switch(addInput)
						{
							case 1:
								e.printClasses();
								System.out.println();
								break;
							case 2:
								System.out.println("Please enter the exact course name and section number to add: ");
								String addClass = scan.nextLine();
								e.add(addClass);
								System.out.println();
								break;
							case 3:
								doneOne = true;
								break;
						}
					}
					
					break;
				case 2:
					boolean doneTwo = false;
					
					while(!doneTwo)
					{
						System.out.println("Dropping: Choose an option: ");
						System.out.println("1. Print current enrolled classes");
						System.out.println("2. Drop class (exact name and section required)");
						System.out.println("3. Back");
						
						System.out.println();
						int dropInput = scan.nextInt();
						System.out.println();
						
						switch(dropInput)
						{
							case 1:
								e.printSchedule();
								System.out.println();
							case 2:
								System.out.println("Please enter the exact course name and section number to drop: ");
								e.drop(scan.nextLine());
								System.out.println();
								break;
							case 3:
								doneTwo = true;
								break;
						}
					}
					
					break;
				case 3:
					
					boolean doneThree = false;
					
					while(!doneThree)
					{
						System.out.println("Swapping: Choose an option: ");
						System.out.println("1. Print class options");
						System.out.println("2. Print current enrolled classes");
						System.out.println("3. Swap classes (exact names and sections required)");
						System.out.println("4. Back");
						
						System.out.println();
						int swapInput = scan.nextInt();
						System.out.println();
						
						switch(swapInput)
						{
							case 1:
								e.printClasses();
								System.out.println();
								break;
							case 2:
								e.printSchedule();
								System.out.println();
								break;
							case 3:
								System.out.println("Please enter the exact course name and section number  of the class you are currently enrolled in and the exact course name and section number of the class you would like to enroll in: ");
								e.swap(scan.nextLine(), scan.nextLine());
								System.out.println();
								break;
							case 4:
								doneThree = true;
								break;
						}
					}
					
					break;
				case 4:
					System.out.println("Thanks for using our service!");
					done = true;
					break;
			}
		}
		scan.close();
	}
}
