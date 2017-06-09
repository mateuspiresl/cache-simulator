import java.util.Arrays;

public class Memory
{
	private byte[] data;
	private double latency;

	public Memory(int size)
	{
		this.data = new byte[size];
		this.latency = (double) Math.log(size);
	}

	public byte[] load(int address) throws InterruptedException
	{
		Latency.add(this.latency);
		return Arrays.copyOfRange(this.data, blockPositionOf(address), Constants.BLOCK_SIZE);
	}
	
	public void store(int address, byte value) throws InterruptedException
	{
		Latency.add(this.latency);
		this.data[address] = value;
	}
	
	public void store(int address, byte[] data) throws InterruptedException
	{
		Latency.add(this.latency);
		int begin = blockPositionOf(address);
		
		for (int i = 0; i < data.length; i++)
			this.data[begin + i] = data[i];
	}
	
	private int blockPositionOf(int address) {
		return address - (address % Constants.BLOCK_SIZE);
	}
}