package com.jeramtough.niyouji.bean.socketmessage;

import java.io.Serializable;

/**
 * @author 11718
 */
public class SocketMessage
{
	private int commandAction;
	
	private String command;
	
	public SocketMessage()
	{
	}
	
	public SocketMessage(int commandAction)
	{
		this.commandAction = commandAction;
	}
	
	public int getCommandAction()
	{
		return commandAction;
	}
	
	public void setCommandAction(int commandAction)
	{
		this.commandAction = commandAction;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
}
