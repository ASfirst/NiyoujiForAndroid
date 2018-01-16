package com.jeramtough.niyouji.bean.socketmessage.command;

import com.alibaba.fastjson.JSON;

/**
 * @author 11718
 *         on 2018  January 16 Tuesday 19:04.
 */

public class CommandParser
{
	public static CreatePerformingRoomCommand parseCreatePerformingRoomCommand(String jsonStr)
	{
		return JSON.parseObject(jsonStr, CreatePerformingRoomCommand.class);
	}
}
