.SUFFIXES: .java .class
JCC = javac

.java.class:
	$(JCC) *.java

default:GameTester.class
	 java GameTester

clean: 
	rm *.class
