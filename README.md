# Introduction

This is the usage manual of the EXTLex Lexical Analyzer.
	
# Installation

As EXTLex is a compiled Java application, it has no need for anything to be installed. The compiled application is called EXTLex.jar and is placed inside the EXTLex folder of this package. However, if you feel the need to modify the code, just open the project in your favourite IDE (preferably Netbeans), apply your modifications and finally recompile it as a new EXTLex.jar file.
	
# Creating Language Files

Please use the "Creating a Language for EXTLex" PDF manual to produce the automata and reserved words file necessary for EXTLex to run.
	
# Running

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
				
# Output
If ran correctly, EXTLex will print a message in the following format:

	Tokens and Lexemes:
	<token_1>	<lexeme_1>
	<token_2>	<lexeme_2>
	...
	<token_n-1>	<lexeme_n-1>
	<token_n>	<lexeme_n>
		
	Errors:
	Line <i>, Character <j>:	<error_message_1>
	Line <i>, Character <j>:	<error_message_2>
	...
	Line <i>, Character <j>:	<error_message_n-1>
	Line <i>, Character <j>:	<error_message_n>
		
Each <token_i> represent the token which was assigned to the lexeme <lexeme_i>. Examples:

	<float>	123.456
	<char>	'A'
	<integer>	123456
	
Each error message <error_message_i> represents the message assigned for a given error state in the language's automata. For each error are also indicated the line <i> and character index <j> where the error was found in the code. Examples:

	Line 5, Character 14:	Missing closing sharp character on end of comment block.
	Line 21, Character 11:	Invalid numeral.
	Line 22, Character 25:	Missing closing quote on string object.
		
# Citing

Please cite the following paper when using EXTLex in any academic contribution:

```
@inproceedings{Paetzold13extlex,
author = {Gustavo Henrique Paetzold and Eler Elisandro Schemberger},
title = {EXTLex: Um Analisador Léxico Configurável},
booktitle = {V EPAC - Encontro Paranaense de Computação},
year = {2013}
}
```

# Acknowledgements

We would like to thank the Universidade Estadual do Oeste do Paraná (Western Parana State University) and the Ministério da Educação do Brasil (Brazilian Education Ministry) for the support and funding.
