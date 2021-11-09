package com.github.jgzl.common;
import java.lang.instrument.Instrumentation;

/**
 * @author lihaifeng
 */
public class CommonAgent {
	public static void agentmain(String args, Instrumentation inst) {
		Class[] classes = inst.getAllLoadedClasses();
		for (Class cls : classes) {
			System.out.println("agent:" + cls.getName());
		}
	}

	public static void premain(String args, Instrumentation inst) {
		Class[] classes = inst.getAllLoadedClasses();
		for (Class cls : classes) {
			System.out.println("premain:" + cls.getName());
		}
	}
}
