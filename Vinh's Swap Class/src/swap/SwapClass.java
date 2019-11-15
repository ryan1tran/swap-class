package swap;

public class SwapClass
{
	public void add(ClassData toAdd) throws NullClassException
	{
		if(toAdd.availableSeats < 1)
		{
			throw new NullClassException(toAdd.className + "-" + toAdd.section + " is full.");
		}else{
			toAdd.availableSeats--;
			toAdd.enrolled = true;
		}
	}
	
	public void drop(ClassData toDrop) throws NullClassException
	{
		if(toDrop.enrolled == true)
		{
			toDrop.availableSeats++;
			toDrop.enrolled = false;
		}else{
			throw new NullClassException("Not enrolled in " + toDrop.className + "-" + toDrop.section);
		}
	}
		
	public void swap(ClassData original, ClassData toSwap) throws NullClassException
	{
		if(toSwap.availableSeats > 0)
		{
			original.enrolled = false;
			original.availableSeats++;
			toSwap.availableSeats--;
			toSwap.enrolled = true;
			
			System.out.println("Successfully enrolled in " + toSwap.className + "-" + toSwap.section + "!");
		}else{
			throw new NullClassException(toSwap.className + "-" + toSwap.section + " is full.");
		}
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

		}
	}

	public static void main(String[] args)
	{
		System.out.println("Hello, World!");
	}
}
