- Introduction:

	This is the usage manual of the EXTLex Lexical Analyzer.
	
- Installation:

	As EXTLex is a compiled Java application, it has no need for anything to be installed. The compiled application is called EXTLex.jar
	and is placed inside the EXTLex folder of this package.
	However, if you feel the need to modify the code, just open the project in your favourite IDE (preferably Netbeans),
	apply your modifications and finally recompile it as a new EXTLex.jar file.
	
- Running:

	There are two ways of using EXTLex:
		- By running the compiled EXTLex.jar application in order to get a code's token and detected errors.
		- By adding it as a library in your Java project and using its functionalities as part of your compiler.
		
	To run the compiled EXTLex.jar, do:
		1) Use the "Creating a Language for EXTLex" PDF manual to produce the automata and reserved words files necessary for EXTLex to run.
		2) Open a terminal/cmd instance.
		3) Navigate to the EXTLex folder inside this package (where the EXTLex.jar file is placed).
		4) Run the following command:
		
				java -jar EXTLex.jar <automata_file> <reserved_words_file> <code_file>
			
		5) For help on using EXTLex, try running EXTLex without any parameters:
				
				java -jar EXTLex.jar
				
- Output:

	EXTLex