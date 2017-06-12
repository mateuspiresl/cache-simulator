
import java.util.Arrays;

public class Memory
{
	public final int lineSize;
	public final int nLines;
	private byte[] data;
	private int uses = 0;

	public Memory(int lineSize, int nLines)
	{
		this.lineSize = lineSize;
		this.nLines = nLines;
		this.data = new byte[nLines * lineSize];
	}
	
	public int getUses() {
		return this.uses;
	}
	
	public void clearUses() {
		this.uses = 0;
	}

	protected byte loadOne(int address, boolean charge)
	{
		if (charge) this.uses++;
		return this.data[blockPositionOf(address)];
	}
	
	public byte loadOne(int address) {
		return loadOne(address, true);
	}
	
	protected byte[] load(int address, boolean charge)
	{
		if (charge) this.uses++;
		
		int position = blockPositionOf(address);
		return Arrays.copyOfRange(this.data, position, position + this.lineSize);
	}
	
	public byte[] load(int address) {
		return load(address, true);
	}
	
	protected void storeOne(int address, byte value, boolean charge)
	{
		if (charge) this.uses++;
		this.data[address] = value;
	}
	
	public final void storeOne(int address, byte value) {
		storeOne(address, value, true);
	}
	
	protected void store(int address, byte[] data, boolean charge)
	{
		if (charge) this.uses++;
		
		int begin = blockPositionOf(address);
		
		for (int i = 0; i < data.length; i++)
			this.data[begin + i] = data[i];
	}
	
	public final void store(int address, byte[] data) {
		store(address, data, true);
	}
	
	protected int blockPositionOf(int address) {
		return address - (address % this.lineSize);
	}
}