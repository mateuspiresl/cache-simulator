
public class Cache extends Memory
{
	private Memory previousMemory;
	
	public Cache(int size, Memory previousMemory) {
		super(size);
		
		this.previousMemory = previousMemory;
	}
	
	@Override
	public byte[] load(int address)
	{
		
		
		return null;
	}
}
