package com.jeramtough.jtemoji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JeramTough
 *         on 2017  December 21 Thursday 00:08.
 */

public class JtEmojisHandler
{
	private HashMap<String, JtEmoji> jtEmojis;
	private static volatile JtEmojisHandler jtEmojisHandler;
	
	private JtEmojisHandler()
	{
		this.jtEmojis = new HashMap<>();
		
		initResources();
	}
	
	protected void initResources()
	{
		jtEmojis.put("[a1]", new JtEmoji(R.drawable.a1, "[a1]"));
		jtEmojis.put("[a2]", new JtEmoji(R.drawable.a2, "[a2]"));
		jtEmojis.put("[a3]", new JtEmoji(R.drawable.a3, "[a3]"));
		jtEmojis.put("[a4]", new JtEmoji(R.drawable.a4, "[a4]"));
		jtEmojis.put("[a5]", new JtEmoji(R.drawable.a5, "[a5]"));
		jtEmojis.put("[a6]", new JtEmoji(R.drawable.a6, "[a6]"));
		jtEmojis.put("[a7]", new JtEmoji(R.drawable.a7, "[a7]"));
		jtEmojis.put("[a8]", new JtEmoji(R.drawable.a8, "[a8]"));
		jtEmojis.put("[a9]", new JtEmoji(R.drawable.a9, "[a9]"));
		jtEmojis.put("[a10]", new JtEmoji(R.drawable.a10, "[a10]"));
		jtEmojis.put("[a11]", new JtEmoji(R.drawable.a11, "[a11]"));
		jtEmojis.put("[a12]", new JtEmoji(R.drawable.a12, "[a12]"));
		jtEmojis.put("[a13]", new JtEmoji(R.drawable.a13, "[a13]"));
		jtEmojis.put("[a14]", new JtEmoji(R.drawable.a14, "[a14]"));
		jtEmojis.put("[a15]", new JtEmoji(R.drawable.a15, "[a15]"));
		jtEmojis.put("[a16]", new JtEmoji(R.drawable.a16, "[a16]"));
		jtEmojis.put("[a17]", new JtEmoji(R.drawable.a17, "[a17]"));
		jtEmojis.put("[a18]", new JtEmoji(R.drawable.a18, "[a18]"));
		jtEmojis.put("[a19]", new JtEmoji(R.drawable.a19, "[a19]"));
		jtEmojis.put("[a20]", new JtEmoji(R.drawable.a20, "[a20]"));
		jtEmojis.put("[a21]", new JtEmoji(R.drawable.a21, "[a21]"));
		jtEmojis.put("[a22]", new JtEmoji(R.drawable.a22, "[a22]"));
		jtEmojis.put("[a23]", new JtEmoji(R.drawable.a23, "[a23]"));
		jtEmojis.put("[a24]", new JtEmoji(R.drawable.a24, "[a24]"));
		jtEmojis.put("[a25]", new JtEmoji(R.drawable.a25, "[a25]"));
		jtEmojis.put("[a26]", new JtEmoji(R.drawable.a26, "[a26]"));
		jtEmojis.put("[a27]", new JtEmoji(R.drawable.a27, "[a27]"));
		jtEmojis.put("[a28]", new JtEmoji(R.drawable.a28, "[a28]"));
		jtEmojis.put("[a29]", new JtEmoji(R.drawable.a29, "[a29]"));
		jtEmojis.put("[a30]", new JtEmoji(R.drawable.a30, "[a30]"));
		jtEmojis.put("[a31]", new JtEmoji(R.drawable.a31, "[a31]"));
		jtEmojis.put("[a32]", new JtEmoji(R.drawable.a32, "[a32]"));
		jtEmojis.put("[a33]", new JtEmoji(R.drawable.a33, "[a33]"));
		jtEmojis.put("[a34]", new JtEmoji(R.drawable.a34, "[a34]"));
		jtEmojis.put("[a35]", new JtEmoji(R.drawable.a35, "[a35]"));
		jtEmojis.put("[a36]", new JtEmoji(R.drawable.a36, "[a36]"));
		jtEmojis.put("[a37]", new JtEmoji(R.drawable.a37, "[a37]"));
		jtEmojis.put("[a38]", new JtEmoji(R.drawable.a38, "[a38]"));
		jtEmojis.put("[a39]", new JtEmoji(R.drawable.a39, "[a39]"));
		jtEmojis.put("[a40]", new JtEmoji(R.drawable.a40, "[a40]"));
		jtEmojis.put("[a41]", new JtEmoji(R.drawable.a41, "[a41]"));
		jtEmojis.put("[a42]", new JtEmoji(R.drawable.a42, "[a42]"));
	}
	
	public static JtEmojisHandler getJtEmojisHandler()
	{
		if (jtEmojisHandler == null)
		{
			synchronized (JtEmojisHandler.class)
			{
				if (jtEmojisHandler == null)
				{
					jtEmojisHandler = new JtEmojisHandler();
				}
			}
		}
		return jtEmojisHandler;
	}
	
	public JtEmoji getJtEmojiByPlaceholder(String placeholder)
	{
		return jtEmojis.get(placeholder);
	}
	
	public ArrayList<JtEmoji> getJtEmojis()
	{
		ArrayList<JtEmoji> jtEmojiArrayList = new ArrayList<>();
		for (Map.Entry<String, JtEmoji> entry : jtEmojis.entrySet())
		{
			jtEmojiArrayList.add(entry.getValue());
		}
		return jtEmojiArrayList;
	}
	
	public ArrayList<ArrayList<JtEmoji>> getApartJtEmojis(int eachCount)
	{
		ArrayList<ArrayList<JtEmoji>> list = new ArrayList<>();
		ArrayList<JtEmoji> allJtEmojis = getJtEmojis();
		
		int index = 0;
		
		int pageCount = allJtEmojis.size() / eachCount;
		if (42 % eachCount > 0)
		{
			pageCount = pageCount + 1;
		}
		
		for (int i = 0; i < pageCount; i++)
		{
			if (i == (pageCount - 1))
			{
				eachCount = allJtEmojis.size() - i * eachCount;
			}
			ArrayList<JtEmoji> list1 = new ArrayList<>();
			for (int ii = 0; ii < eachCount; ii++)
			{
				list1.add(allJtEmojis.get(index));
				index++;
			}
			list.add(list1);
		}
		return list;
	}
	
	
}
