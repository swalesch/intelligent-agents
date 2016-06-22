package job.itemlist;

import java.util.Map;

import com.google.common.collect.Maps;

public class ItemList {

	private static Map<Integer, Integer> _itemList;

	/**
	 * creates x random items with weight ranging from 1 to 50.
	 */
	public static void createRandomListWithXItems(int x) {
		_itemList = Maps.newHashMap();

		for (int i = 1; i <= x; i++) {
			_itemList.put(i, 1 + (int) (Math.random() * 49));
		}
	}

	/**
	 * returns null if the item is not existing
	 */
	public static int getWeightOfID(int itemId) {
		return _itemList.get(itemId);
	}

	public static int getSize() {
		return _itemList.size();
	}
}
