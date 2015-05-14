package com.shikk.util;
public class CommandResult {
	public int exit_value;
	public String stderr;
	public String stdout;

	CommandResult(int initValue) {
		this(initValue, null, null);
	}

	CommandResult(int initValue, String stdout, String stderr) {
		this.exit_value = initValue;
		this.stdout = stdout;
		this.stderr = stderr;
	}

	public boolean success() {
		if (exit_value == 0)
			return true;
		else
			return false;
	}
}