import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MemoryTest
{
	@Test
	public void test() throws Exception
	{
		Memory memory = new Memory(4, 10);
		byte data[];
		
		for (int i = 0; i < 10; i++)
			assertArrayEquals(new byte[4], memory.load(i * 4));
				
		data = new byte[4];
		data[19 % 4] = 0x19;
		memory.storeOne(19, (byte) 0x19);
		assertArrayEquals(data, memory.load(19));
		
		data = new byte[4];
		data[21 % 4] = 0x21;
		memory.storeOne(21, (byte) 0x21);
		assertArrayEquals(data, memory.load(21));
		
		data = new byte[4];
		data[19 % 4] = 0x19;
		assertArrayEquals(data, memory.load(19));
		
		data = new byte[4];
		data[21 % 4] = 0x21;
		assertArrayEquals(data, memory.load(21));
		
		data = new byte[4];
		data[0] = 0x07;
		data[1] = 0x06;
		data[2] = 0x05;
		memory.storeOne(4, (byte) 0x07);
		memory.storeOne(4 + 1, (byte) 0x06);
		memory.storeOne(4 + 2, (byte) 0x05);
		assertArrayEquals(data, memory.load(4));
		assertArrayEquals(data, memory.load(4 + 1));
		assertArrayEquals(data, memory.load(4 + 2));
		assertArrayEquals(data, memory.load(4 + 3));
		
		memory = new Memory(4, 100);
		for (int i = 0; i < 100 * 4; i++)
			memory.storeOne(i, (byte) i);
		
		assertArrayEquals(new byte[] { 0, 1, 2, 3 }, memory.load(0));
		assertArrayEquals(new byte[] { 0, 1, 2, 3 }, memory.load(1));
		assertArrayEquals(new byte[] { 0, 1, 2, 3 }, memory.load(3));
		assertArrayEquals(new byte[] { 8, 9, 10, 11 }, memory.load(8));
		assertArrayEquals(new byte[] { 8, 9, 10, 11 }, memory.load(9));
		assertArrayEquals(new byte[] { 8, 9, 10, 11 }, memory.load(11));
	}
}
