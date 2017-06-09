
public class Latency
{
	private static double total = 0;
	
	public static void add(double time) {
		total += time;
	}
	
	public static double getTotal() {
		return total;
	}
}
