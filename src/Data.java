import java.util.Random;

public class Data
{
	public static int[] generate(int memorySize, int dataSize, int nearSize, float jump)
	{
		int[] sequence = new int[dataSize];
		Random rnd = new Random();
		
		sequence[0] = 0;
		
		for (int i = 1; i < dataSize; i++)
		{
			boolean foward = rnd.nextBoolean();
			
			if (foward && sequence[i - 1] == memorySize - 1 || !foward && sequence[i - 1] == 0)
				foward = !foward;
			
			int limit = foward ? memorySize - sequence[i - 1] : sequence[i - 1];
			int step;
			
			if (rnd.nextFloat() > jump)
				step = rnd.nextInt(Math.min(limit, nearSize));
			else
			{
				if (limit <= nearSize)
				{
					foward = !foward;
					limit = foward ? memorySize - sequence[i - 1] : sequence[i - 1];
				}
				
				step = rnd.nextInt(limit - nearSize) + nearSize;
			}
			
			sequence[i] = sequence[i - 1] + (foward ? step : -step);
		}
		
		return sequence;
	}
}
