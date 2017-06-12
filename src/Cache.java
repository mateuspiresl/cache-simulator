

public class Cache extends Memory
{
	private int[] lines;
	private Memory previousMemory;
	
	public Cache(int lineSize, int nLines, Memory previousMemory) {
		super(lineSize, nLines);
		
		this.lines = new int[nLines];
		this.previousMemory = previousMemory;
		
		for (int i = 0; i < super.nLines; i++)
			this.lines[i] = -1;
	}
	
	@Override
	public byte loadOne(int address, boolean charge) {
		return load(address, charge)[address % super.nLines];
	}
	
	@Override
	public byte[] load(int address, boolean charge)
	{
		int memoryLine = address / super.lineSize;
		int cacheLine = memoryLine % super.nLines;
		int cacheAddress = cacheLine * super.lineSize;
		
		if (this.lines[cacheLine] != memoryLine) {
			this.lines[cacheLine] = memoryLine;
			super.store(cacheAddress, this.previousMemory.load(address), false);
		}
		
		return super.load(cacheAddress, charge);
	}
}
