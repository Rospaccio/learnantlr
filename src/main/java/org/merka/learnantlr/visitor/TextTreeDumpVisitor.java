package org.merka.learnantlr.visitor;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.merka.learnantlr.language.ShapePlacerParser.CoordinateComponentContext;
import org.merka.learnantlr.language.ShapePlacerParser.CoordinatesContext;
import org.merka.learnantlr.language.ShapePlacerParser.CubeDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerParser.ProgramContext;
import org.merka.learnantlr.language.ShapePlacerParser.RegionDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerParser.ShapeDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerParser.SphereDefinitionContext;
import org.merka.learnantlr.language.ShapePlacerVisitor;

public class TextTreeDumpVisitor implements ShapePlacerVisitor<String> {

	private int indentLevel = 0;
	
	public String visit(ParseTree arg0) {
		return arg0.accept(this);
	}

	public String visitChildren(RuleNode arg0) {
		int childrenCount = arg0.getChildCount();
		StringBuilder builder = new StringBuilder();
		increaseIndentationLevel();
		for(int i = 0; i < childrenCount; i++){
			builder.append(arg0.getChild(i).accept(this));
		}
		decreaseIndentationLevel();
		return builder.toString();
	}

	public String visitErrorNode(ErrorNode arg0) {
		
		return null;
	}

	public String visitTerminal(TerminalNode arg0) {
		return indent() + "[terminal] '" + arg0.getText() + "'" + n();
	}

	public String visitProgram(ProgramContext ctx) {
		StringBuilder builder = new StringBuilder();
		builder.append(indent()).append("[program]").append(n());
		builder.append(visitChildren(ctx));
		return builder.toString();		
	}

	public String visitShapeDefinition(ShapeDefinitionContext ctx) {
		return indent() + "[shape definition]" + n() + visitChildren(ctx);
	}

	public String visitSphereDefinition(SphereDefinitionContext ctx) {
		return indent() + "[sphere definition]" + n() + visitChildren(ctx);
	}

	public String visitCubeDefinition(CubeDefinitionContext ctx) {
		return indent() + "[cube definition]" + n() + visitChildren(ctx);
	}

	public String visitCoordinates(CoordinatesContext ctx) {
		return indent() + "[coordinates]" + n() + visitChildren(ctx);
	}

	public String visitCoordinateComponent(CoordinateComponentContext ctx) {
		return indent() + "[coordinates component]" + n() + visitChildren(ctx);
	}

	private String indent(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < getIndentLevel(); i++){
			builder.append("\t");
		}
		return builder.toString();
	}
	
	private void increaseIndentationLevel(){
		this.indentLevel += 1;
	}
	
	private void decreaseIndentationLevel(){
		this.indentLevel -= 1;
	}
	
	public int getIndentLevel() {
		return indentLevel;
	}

	public void setIndentLevel(int indentLevel) {
		this.indentLevel = indentLevel;
	}

	public String visitRegionDefinition(RegionDefinitionContext ctx) {
		return indent() + "[region]" + n() + visitChildren(ctx);
	}

	private String n(){
		return System.lineSeparator();
	}
	
}
