package org.merka.learnantlr;

import static org.junit.Assert.fail;

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
import org.junit.Test;
import org.merka.learnantlr.language.ShapePlacerLexer;
import org.merka.learnantlr.language.ShapePlacerParser;
import org.merka.learnantlr.language.ShapePlacerParser.ProgramContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStringRecognition {
	
	private static final Logger logger = LoggerFactory.getLogger(TestStringRecognition.class);
	
	@Test
	public void testExploratoryString() throws IOException {
		
		String simplestProgram = "sphere 12 12 12 cube 2 3 4 cube 4 4 4 sphere 3 3 3";
		
		CharStream inputCharStream = new ANTLRInputStream(new StringReader(simplestProgram));
		TokenSource tokenSource = new ShapePlacerLexer(inputCharStream);
		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
		ShapePlacerParser parser = new ShapePlacerParser(inputTokenStream);
		
		parser.addErrorListener(new TestErrorListener());
		
		ProgramContext context = parser.program();
		
		logger.info(context.toString());
	}
	
	class TestErrorListener implements ANTLRErrorListener {
		@Override
		public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2,
				int arg3, String arg4, RecognitionException arg5) {
			fail();
		}
		
		@Override
		public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2,
				int arg3, int arg4, ATNConfigSet arg5) {
			fail();				
		}
		
		@Override
		public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2,
				int arg3, BitSet arg4, ATNConfigSet arg5) {
			fail();
		}
		
		@Override
		public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3,
				boolean arg4, BitSet arg5, ATNConfigSet arg6) {
			fail();
		}
	}
	
}
