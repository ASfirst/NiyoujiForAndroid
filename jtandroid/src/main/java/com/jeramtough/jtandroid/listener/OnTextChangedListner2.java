package com.jeramtough.jtandroid.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author 11718
 *         on 2018  January 07 Sunday 19:57.
 */

public abstract class OnTextChangedListner2 implements TextWatcher
{
	private int wordsLengthAfterInputting;
	private int wordsCountBefterInputting;
	private String textBeforeChanged;
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		this.wordsLengthAfterInputting = after;
		textBeforeChanged = s.toString();
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		this.wordsCountBefterInputting = before;
		
		if (wordsLengthAfterInputting > wordsCountBefterInputting)
		{
			String words = s.toString().substring(start, start + count);
			if (start + count == s.length())
			{
				onAddWordsToLast(words);
			}
			else
			{
				onAddWords(words, start);
			}
		}
		else if (wordsLengthAfterInputting < wordsCountBefterInputting)
		{
			String words = textBeforeChanged.substring(start, start + before);
			onDeletedWords(words, start);
			if (start + before == textBeforeChanged.length())
			{
				onDeletedWordsFromLast(words);
			}
			else
			{
				onDeletedWords(words, start);
			}
		}
	}
	
	@Override
	public void afterTextChanged(Editable s)
	{
	
	}
	
	public abstract void onAddWords(String words, int start);
	
	public abstract void onAddWordsToLast(String words);
	
	public abstract void onDeletedWords(String words, int start);
	
	public abstract void onDeletedWordsFromLast(String words);
}
