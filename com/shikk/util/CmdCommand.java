package com.shikk.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CmdCommand {
	
	public Shell cmd;
	public CmdCommand(){
		cmd = new Shell("cmd /c");
	}
	
	public class Shell {
		private String SHELL = "cmd";
		private Process playerProcess;

		public Shell(String arg2) {
			this.SHELL = arg2;
		}

		private String getStreamLines(InputStream paramInputStream) {
			BufferedReader reader = new BufferedReader( new InputStreamReader(paramInputStream) );
		    int read;
		    char[] buffer = new char[4096];
		    StringBuffer output = new StringBuffer();
		    try {
				while ((read = reader.read(buffer)) > 0) {
				    output.append(buffer, 0, read);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return output.toString();
		}

		private Process run(String command) {
			Process localProcess = null;
			String str1 = this.SHELL;
			String str2 = " " + command + "\n";
			try {
				Runtime localRuntime = Runtime.getRuntime();
				localProcess = localRuntime.exec(str1+str2);
//				OutputStream localOutputStream = localProcess.getOutputStream();
//				DataOutputStream localDataOutputStream = new DataOutputStream(
//						localOutputStream);
//				localDataOutputStream.writeBytes(str2);
//				localDataOutputStream.flush();
			} catch (Exception localException) {
			}
			return localProcess; 
		}

		public void runPlayerProc(String paramString) {
			System.out.println("command: "+paramString );
			playerProcess = run(paramString);
			new Thread(){
				public void run() {
					int exitValue = -1;
					String stdout = null;
					String stderr = null;
					try {
						InputStream localInputStream1 = playerProcess.getInputStream();
						stdout = getStreamLines(localInputStream1);
						InputStream localInputStream2 = playerProcess.getErrorStream();
						stderr = getStreamLines(localInputStream2);
						exitValue = playerProcess.waitFor();
					} catch (InterruptedException localInterruptedException) {
						exitValue = -1;
						String str4 = localInterruptedException.toString();
					} catch (NullPointerException localNullPointerException) {
						String str5 = localNullPointerException.toString();
						exitValue = -1;
					}
				};
			}.start();
		}
		
		
		public CommandResult runWaitFor(String paramString) {
			System.out.println("run command: "+paramString);
			Process localProcess = run(paramString);
			int exitValue = -1;
			String stdout = null;
			String stderr = null;
			try {
				InputStream localInputStream1 = localProcess.getInputStream();
				stdout = getStreamLines(localInputStream1);
				InputStream localInputStream2 = localProcess.getErrorStream();
				stderr = getStreamLines(localInputStream2);
				exitValue = localProcess.waitFor();
			} catch (InterruptedException localInterruptedException) {
				exitValue = -1;
				String str4 = localInterruptedException.toString();
			} catch (NullPointerException localNullPointerException) {
				String str5 = localNullPointerException.toString();
				exitValue = -1;
			}
			if (!paramString.equals("VBoxManage list vms")) {
//				System.out.println("run command stdout: "+stdout);
//				System.out.println("run command stderr: "+stderr);
			}
			
			return new CommandResult(exitValue, stdout, stderr);
		}
	}
	
}
