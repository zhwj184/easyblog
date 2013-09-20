package org.work;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

public class JarRunner {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, NoSuchMethodException,
			InvocationTargetException {
		URL url = new URL("file:E:/tmp/a.jar");
		// Create the class loader for the application jar file
		JarClassLoader cl = new JarClassLoader(url);
		// Get the application's main class name
		String name = cl.getMainClassName();
		// Get arguments for the application
		String[] newArgs = new String[0];
//		System.arraycopy(args, 1, newArgs, 0, newArgs.length);
		// Invoke application's main class
		cl.invokeClass(name, newArgs);
	}
}

final class JarClassLoader extends URLClassLoader {
	private URL url;

	public JarClassLoader(URL url) {
		super(new URL[] { url });
		this.url = url;
	}

	public String getMainClassName() throws IOException {
		URL u = new URL("jar", "", url + "!/");
		JarURLConnection uc = (JarURLConnection) u.openConnection();
		Attributes attr = uc.getMainAttributes();
		return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
	}

	public void invokeClass(String name, String[] args)
			throws ClassNotFoundException, NoSuchMethodException,
			InvocationTargetException {
		Class c = loadClass(name);
		Method m = c.getMethod("main", new Class[] { args.getClass() });
		m.setAccessible(true);
		int mods = m.getModifiers();
		if (m.getReturnType() != void.class || !Modifier.isStatic(mods)
				|| !Modifier.isPublic(mods)) {
			throw new NoSuchMethodException("main");
		}
		try {
			m.invoke(null, new Object[] { args });
		} catch (IllegalAccessException e) {
			System.out.println("Access denied");
		}
	}
}