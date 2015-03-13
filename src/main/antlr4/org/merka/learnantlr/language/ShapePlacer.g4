grammar ShapePlacer;

program : (shapeDefinition)+ ;

shapeDefinition : sphereDefinition | cubeDefinition ;

sphereDefinition : SPHERE_KEYWORD coordinates ;

cubeDefinition : CUBE_KEYWORD coordinates ;

coordinates : NUMBER NUMBER NUMBER ;

SPHERE_KEYWORD : 'sphere' ;

CUBE_KEYWORD : 'cube' ;

NUMBER : [1-9]+ ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines ;
