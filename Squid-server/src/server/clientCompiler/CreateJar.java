package server.clientCompiler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class CreateJar{
	private static String path = "/home/aakash/workspace/Squid-client/test/dist";
	public static void main(String... args) throws IOException	{
		String jarName = null;
		String dir = null;
		if(args.length!=0){
			dir = args[0];
			path = args[1];	
			jarName = args[2];
		}
		
		System.out.println(path);
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "client.ClientWindow");
		JarOutputStream target = new JarOutputStream(new FileOutputStream(dir+"/"+jarName), manifest);
		add(new File(path), target,"");
		target.close();
	}
 
	private static void add(File source, JarOutputStream target, String a) throws IOException
	{
		BufferedInputStream in = null;
		try{
			if (source.isDirectory())
			{
				String name = source.getPath().replace("\\", "/");
				if (!name.isEmpty())
				{
					if (!name.endsWith("/"))
						name += "/";
					//System.out.println(a+name.replace(path+"/",""));
					JarEntry entry = new JarEntry(name.replace(path+"/",""));
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile: source.listFiles())
					add(nestedFile, target,a+"--");
				return;
			}
 			//System.out.println(a+source.getPath().replace("\\", "/").replace(path+"/",""));
			JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").replace(path+"/",""));
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));
 
			byte[] buffer = new byte[1024];
			while (true)
			{
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		}
		finally{
			if (in != null)
			in.close();
		}
	}
}

