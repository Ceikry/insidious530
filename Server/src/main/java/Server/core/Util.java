package core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {

	/**
	 * Capitalize the first letter of the string
	 * @return Capitalized string
	 */
	public static String capitalize(String name) {
		if (name != null && name.length() != 0) {
			char[] chars = name.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			return new String(chars);
		} else {
			return name;
		}
	}
	
	public static String strToEnum(String name) {
		name = name.toUpperCase();
		return name.replaceAll(" ", "_");
	}
	
	public static String enumToString(String name) {
		name = name.toLowerCase();
		name = name.replaceAll("_", " ");
		return capitalize(name);
	}

	public static Class[] getClasses(String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) {
		List<Class> classes = new ArrayList<Class>();
		boolean fromJar = directory.getPath().contains(".jar!");
		if (!directory.exists() && !fromJar) {
			return classes;
		}
		if (fromJar) {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			try {
				String formattedPackage = packageName.replace(".", "/");
				URL jar = Util.class.getProtectionDomain().getCodeSource().getLocation();
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				while (true) {
					ZipEntry e = zip.getNextEntry();
					if (e == null) {
						break;
					}
					String name = e.getName();
					if (e.isDirectory() || !name.startsWith(formattedPackage) || !name.endsWith(".class")) {
						continue;
					}
					classes.add(Class.forName(name.replace(".class", "").replace("/", ".")));
				}
				//System.out.println("hi");
			} catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				} else if (file.getName().endsWith(".class")) {
					try {
						classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
					} catch (Throwable e) {

					}
				}
			}
		}
		return classes;
	}

	private static char[] c = new char[]{'K', 'M', 'B'};

	/**
	 * Formats the string to a GP format
	 * @param j The integer to be formatted
	 * @return The formatted string
	 */
	private static String formatValue(double a, int b) {
		double d = ((long) a / 100) / 10.0;
		boolean isRound = (d * 10) %10 == 0;
		return (d < 1000?((d > 99.9 || isRound || (!isRound && d > 9.99)?
				(int) d * 10 / 10 : d + "") + "" + c[b]) : formatValue(d, b+1));
	}

	public static String intToKOrMil(int j) {
		if(j < 10000)
			return String.valueOf(j);
		if(j < 0x989680)
			return formatValue(j, 0);
		else if(j < 0x3B9ACA00)
			return formatValue(j, 0);
		else
			return formatValue(j, 0);
	}
}
