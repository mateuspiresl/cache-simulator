import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CacheTest
{
	@Test
	public void test() throws Exception
	{
		Memory main = new Memory(4, 10);
		Cache cache = new Cache(4, 2, main);
		
		for (int i = 0; i < 10 * 4; i++)
			main.storeOne(i, (byte) i);
		
		main.clearUses();
		
		byte[] data0 = { 0, 1, 2, 3 };
		byte[] data1 = { 4, 5, 6, 7 };
		byte[] data2 = { 8, 9, 10, 11 };
		
		// 0, 0
		assertArrayEquals(data0 , cache.load(0));
		assertEquals(1, main.getUses());
		assertEquals(1, cache.getUses());
		
		// 0, 1
		assertArrayEquals(data0, cache.load(1));
		assertEquals(1, main.getUses());
		assertEquals(2, cache.getUses());
		
		// 0, 7
		assertArrayEquals(data0, cache.load(3));
		assertEquals(1, main.getUses());
		assertEquals(3, cache.getUses());
		
		// 1, 4
		assertArrayEquals(data1, cache.load(4));
		assertEquals(2, main.getUses());
		assertEquals(4, cache.getUses());
		
		// 1, 7
		assertArrayEquals(data1, cache.load(7));
		assertEquals(2, main.getUses());
		assertEquals(5, cache.getUses());
		
		// 0, 0
		assertArrayEquals(data0, cache.load(0));
		assertEquals(2, main.getUses());
		assertEquals(6, cache.getUses());
		
		// 1, 4
		assertArrayEquals(data1, cache.load(4));
		assertEquals(2, main.getUses());
		assertEquals(7, cache.getUses());
		
		// 2, 8
		assertArrayEquals(data2, cache.load(8));
		assertEquals(3, main.getUses());
		assertEquals(8, cache.getUses());
		
		// 1, 8
		assertArrayEquals(data1, cache.load(4));
		assertEquals(3, main.getUses());
		assertEquals(9, cache.getUses());
		
		Cache largerCache = new Cache(4, 3, main);
		
		// 0, 0
		assertArrayEquals(data0, largerCache.load(0));
		assertEquals(4, main.getUses());
		assertEquals(1, largerCache.getUses());
	
		// 1, 8
		assertArrayEquals(data1, largerCache.load(4));
		assertEquals(5, main.getUses());
		assertEquals(2, largerCache.getUses());
		
		// 0, 0
		assertArrayEquals(data0, largerCache.load(0));
		assertEquals(5, main.getUses());
		assertEquals(3, largerCache.getUses());
		
		// 1, 8
		assertArrayEquals(data1, largerCache.load(4));
		assertEquals(5, main.getUses());
		assertEquals(4, largerCache.getUses());
		
		// 2, 16
		assertArrayEquals(data2, largerCache.load(8));
		assertEquals(6, main.getUses());
		assertEquals(5, largerCache.getUses());
		
		// 0, 0
		assertArrayEquals(data0, largerCache.load(0));
		assertEquals(6, main.getUses());
		assertEquals(6, largerCache.getUses());
		
		// 1, 8
		assertArrayEquals(data1, largerCache.load(4));
		assertEquals(6, main.getUses());
		assertEquals(7, largerCache.getUses());
	}
}
