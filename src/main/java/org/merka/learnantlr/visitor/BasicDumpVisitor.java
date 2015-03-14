package org.merka.learnantlr.visitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.merka.learnantlr.language.ShapePlacerParser.CoordinatesContext;
import org.merka.learnantlr.language.ShapePlacerParser.CubeDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerParser.ProgramContext;
import org.merka.learnantlr.language.ShapePlacerParser.ShapeDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerParser.SphereDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasicDumpVisitor implements ShapePlacerVisitor<String> {

	private static final Logger logger = LoggerFactory.getLogger(BasicDumpVisitor.class);
	
	public String visit(ParseTree arg0) {
		
		return null;
	}

	public String visitChildren(RuleNode arg0) {
		
		return null;
	}

	public String visitErrorNode(ErrorNode arg0) {
		
		return null;
	}

	public String visitTerminal(TerminalNode arg0) {
		logger.info("visiting terminal node: " + arg0.toStringTree());
		return arg0.getText();
	}

	public String visitProgram(ProgramContext ctx) {
		StringBuilder builder = new StringBuilder();
		for(ParseTree tree : ctx.children){
			builder.append(tree.accept(this));
		}
		return builder.toString();
	}

	public String visitShapeDefinition(ShapeDefinitionContext ctx) {
		StringBuilder builder = new StringBuilder();
		for (ParseTree tree : ctx.children){
			builder.append(tree.accept(this) + " ");
		}
		builder.append("\n");
		return builder.toString();
	}

	public String visitSphereDefinition(SphereDefinitionContext ctx) {
		return getShapeDefinitionString(ctx, "sphere");
	}

	public String visitCubeDefinition(CubeDefinitionContext ctx) {
		return getShapeDefinitionString(ctx, "cube");
	}

	public String visitCoordinates(CoordinatesContext ctx) {
		StringBuilder builder = new StringBuilder();
		for (ParseTree tree : ctx.children){
			builder.append(tree.accept(this)).append(" ");
		}
		return builder.toString();
	}
	
	private String getShapeDefinitionString(ParserRuleContext context, String keyword){
		StringBuilder builder = new StringBuilder();
		builder.append(keyword).append(" ");
		
		builder.append(context.children.get(1).accept(this)).append(" ");
		
		return builder.toString();
	}

}
