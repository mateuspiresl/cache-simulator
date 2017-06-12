import java.text.DecimalFormat;

public class Main
{
	private static final int LINE_SIZE = 16;
	
	private static DecimalFormat format = new DecimalFormat("#0.000"); 
	
	public static void main(String[] args)
	{
		final int memorySize = 100000;
		final int memoryRange = 33;
		final int sequenceSize = 100000;
		
		for (float jump = 0.01F; jump <= 0.31F; jump += 0.1F)
		{
			int[] sequence = Data.generate(LINE_SIZE * memorySize, sequenceSize, memoryRange, jump);
						
			for (float mainToL2 = 0.01F; mainToL2 <= 0.11F; mainToL2 += 0.05F) {
				for (float l2ToL1 = 0.1F; l2ToL1 <= 0.35F; l2ToL1 += 0.2F)
				{				
					int l2Size = (int) (mainToL2 * memorySize);
					int l1Size = (int) (l2ToL1 * memorySize);
					int l2ToL1Size = (int) (l2ToL1 * l2Size);
					
					int uniqueL2Test = testUniqueCache(memorySize, l2Size, sequence);
					int uniqueL1Test = testUniqueCache(memorySize, l1Size, sequence);
					int doubleTest = testDoubleCache(memorySize, l2Size, l2ToL1Size, sequence);
					
					System.out.println(join(jump).append(",").append(join(l2Size, l1Size, l2ToL1Size)).append(",")
							.append(join(uniqueL2Test, uniqueL1Test, doubleTest)));
				}
			}
		}
	}
	
	private static int testUniqueCache(int memorySize, int cacheSize, int[] sequence)
	{
		Memory main = new Memory(LINE_SIZE, memorySize);
		Cache l1 = new Cache(LINE_SIZE, cacheSize, main);
		
		for (int address : sequence)
			l1.load(address);
		
		return main.getUses();
	}
	
	private static int testDoubleCache(int memorySize, int l2Size, int l1Size, int[] sequence)
	{
		Memory main = new Memory(LINE_SIZE, memorySize);
		Cache l2 = new Cache(LINE_SIZE, l1Size, main);
		Cache l1 = new Cache(LINE_SIZE, l2Size, l2);
		
		for (int address : sequence)
			l1.load(address);
		
		return main.getUses();
	}
	
	private static StringBuilder join(float... values)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(format.format(values[0]).replaceAll(",", "."));
		
		for (int i = 1; i < values.length; i++)
			builder.append(",").append(format.format(values[i]).replaceAll(",", "."));
		
		return builder;
	}
	
	private static StringBuilder join(int... values)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(values[0]);
		
		for (int i = 1; i < values.length; i++)
			builder.append(",").append(values[i]);
		
		return builder;
	}
}
