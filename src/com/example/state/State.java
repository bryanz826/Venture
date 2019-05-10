package com.example.state;

import com.example.utils.ConsoleLog;
import com.example.utils.gameloop_instructions.Interactive;

public interface State extends Interactive
{
	public default void enter()
	{
		ConsoleLog.write("Entering <" + getName() + "> state");
	}

	public default void exit()
	{
		ConsoleLog.write("Exited <" + getName() + "> state");
	}

	public String getName();
}
