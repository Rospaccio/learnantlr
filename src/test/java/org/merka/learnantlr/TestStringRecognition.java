package org.merka.learnantlr;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.junit.Ignore;
import org.junit.Test;
import org.merka.learnantlr.language.ShapePlacerLexer;
import org.merka.learnantlr.language.ShapePlacerParser;
import org.merka.learnantlr.language.ShapePlacerParser.ProgramContext;
import org.merka.learnantlr.visitor.BasicDumpVisitor;
import org.merka.learnantlr.visitor.TextTreeDumpVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStringRecognition {
	
	private static final Logger logger = LoggerFactory.getLogger(TestStringRecognition.class);
	
	public static final String[] validInputStrings = {	"sphere 12 12 12 cube 2 3 4 cube 4 4 4 sphere 3 3 3",
														"sphere 12 12 12" + System.lineSeparator() + " cube 2 3 4",
														"sphere 12 12 12" + System.lineSeparator() + " cube 2 3 4 sphere 2 2 2",
														"sphere 12 12 12" + System.lineSeparator() + "cube 2 3 4",
														"sphere 0 0 0",
														"cube 0 0 0",
														"sphere 0 0 0 cube 0 0 0 ",
														"cube 0 0 0 sphere 0 0 0",
														"sphere 10 0 100",
														"sphere 12.3 0.0 1.0 cube 0.2334 12.12 1.1"};
	public static final String[] invalidInputStrings = {"sphere 12 12 12 cub 2 3 4 cube 4 4 4 sphere 3 3 3",
														"sphere 12 12 12" + System.lineSeparator() + " cube 2 3 4 4;",
														"sphere 12 12" + System.lineSeparator() + " cube 2 3 4 sphere 2 2 2" };
	
	
	@Test
	public void testExploratoryString() throws IOException {
		
		String simplestProgram = "sphere 12.3 12 12 cube 2 3 4 cube 4 4 4 sphere 3 3 3";
		
		CharStream inputCharStream = new ANTLRInputStream(new StringReader(simplestProgram));
		TokenSource tokenSource = new ShapePlacerLexer(inputCharStream);
		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		ShapePlacerParser parser = new ShapePlacerParser(inputTokenStream);
		
		parser.addErrorListener(new TestErrorListener());
		
		ProgramContext context = parser.program();
		
		logger.info(context.toString());
	}
	
	@Test
	public void testValidStrings() throws IOException{
		int index = 0;
		for(String validString : validInputStrings){
			boolean ok = isValidString(validString);
			if(!ok){
				fail("Failing string = [" + validString + "]; at index " + index);
			}
			index += 1;
		}
	}
	
	@Test
	public void testInvaliStrings() throws IOException{
		int index = 0;
		for (String invalidString : invalidInputStrings){
			boolean ok = isValidString(invalidString);
			if(ok){
				fail("String isrecognized as valid, but it should not: [" + invalidString 
						+ "]; at index " + index);
			}
			index += 1;
		}
	}
	
	@Test
	public void testJsonVisitor() throws IOException{
		String program = "sphere 0.1 1.0 1.1 cube 5.1234 5 5 sphere 10 1 3";
		TestErrorListener errorListener = new TestErrorListener(); 
		ProgramContext context = parseProgram(program, errorListener);
		
		assertFalse(errorListener.isFail());
		
		BasicDumpVisitor visitor = new BasicDumpVisitor();
		
		String jsonRepresentation = visitor.visit(context);
		logger.info("String return by the visitor = " + jsonRepresentation);
//		assertTrue(isValidString(jsonRepresentation));
		
	}
	
	@Test
	public void testTextTreeDumpVisitor() throws IOException{
		String program = "cube 1.0 0.1 1.1 sphere 1.1234 3 4";
		TestErrorListener errorListener = new TestErrorListener();
		ProgramContext parseTree = parseProgram(program, errorListener);
		TextTreeDumpVisitor visitor = new TextTreeDumpVisitor();
		String output = visitor.visit(parseTree);
		logger.info("\n\nTree:\n\n" + output);
	}
	 
	private boolean isValidString(String inputString) throws IOException{
		TestErrorListener errorListener = new TestErrorListener();
		ProgramContext context = parseProgram(inputString, errorListener);
		return !errorListener.isFail();
	}
	
	private ProgramContext parseProgram(String program, TestErrorListener errorListener) throws IOException
	{
		CharStream inputCharStream = new ANTLRInputStream(new StringReader(program));
		TokenSource tokenSource = new ShapePlacerLexer(inputCharStream);
		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		ShapePlacerParser parser = new ShapePlacerParser(inputTokenStream);
		parser.addErrorListener(errorListener);
		
		ProgramContext context = parser.program();
		return context;
	}
	
	class TestErrorListener implements ANTLRErrorListener {
		
		private boolean fail = false;
		
		public boolean isFail() {
			return fail;
		}

		public void setFail(boolean fail) {
			this.fail = fail;
		}

		@Override
		public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2,
				int arg3, String arg4, RecognitionException arg5) {
			setFail(true);
		}
		
		@Override
		public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2,
				int arg3, int arg4, ATNConfigSet arg5) {
			setFail(true);			
		}
		
		@Override
		public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2,
				int arg3, BitSet arg4, ATNConfigSet arg5) {
			setFail(true);
		}
		
		@Override
		public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3,
				boolean arg4, BitSet arg5, ATNConfigSet arg6) {
			setFail(true);
		}
	}
	
}
