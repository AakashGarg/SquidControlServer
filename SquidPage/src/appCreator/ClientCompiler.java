/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;

/**
 * 
 * @author Aakash
 */
public class ClientCompiler extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1965010811851320047L;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public synchronized static void main(String... args)
			throws FileNotFoundException, IOException, InterruptedException,
			Exception {
		// TODO code application logic here
		String userName = args[0];
		String macAddress = args[1];
		System.out.print("Step 1: Creating source file - ");
		File dir = new File("/home/aakash/Desktop/SquidRMI/test");
		File sourceFile = new File(dir, "ClientReferenceFile.java");
		File clientFile = new File(dir, "Client.java");
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(sourceFile));
		PrintWriter writer = new PrintWriter(clientFile);
		while ((line = br.readLine()) != null) {
			if (line.indexOf("aakashgarg") >= 0) {
				line = line.replace("aakashgarg", userName);
			} else if (line.indexOf("78-DD-08-C9-94-3A") >= 0) {
				line = line.replace("78-DD-08-C9-94-3A", macAddress);
			}
			writer.println(line);
		}
		br.close();
		writer.flush();
		writer.close();
		System.out.println("done");
		// compileAndJar(userName);
		createJar(userName, dir);
		// System.out.println("Step 4: Deleting File - " + clientFile.delete());
		System.out.println("jar created for user:" + userName
				+ " with mac id: " + macAddress);
	}

	private synchronized static void createJar(String userName, File dir)
			throws IOException, InterruptedException {
		Process p = null;
		System.out.println(dir.getPath());
		p = Runtime.getRuntime().exec("sudo cd " + dir.getPath());
		p.waitFor();
		System.out.print("Step 2: Compiling file - ");
		p = Runtime.getRuntime().exec(
				"sudo javac -d " + dir.getPath() + "/ " + dir.getPath()
						+ "/Client.java");
		p.waitFor();
		System.out.println("done");
		System.out.print("Step 3: Creating jar - ");
		p = Runtime.getRuntime().exec(
				"sudo jar -cvmf manifest.txt " + userName
						+ ".jar client ipMac net server");
		p.waitFor();
		System.out.println("done");
	}

	@SuppressWarnings("unused")
	private synchronized static void compileAndJar(String userName)
			throws IOException, InterruptedException {
		Process p = null;
		p = Runtime.getRuntime().exec(
				"sudo /home/aakash/Desktop/SquidRMI/test/CompileAndJar "
						+ userName);
		p.waitFor();
	}

}
