grammar ShapePlacer;

program : (shapeDefinition)+ ;

shapeDefinition : sphereDefinition | cubeDefinition ;

sphereDefinition : SPHERE_KEYWORD coordinates ;

cubeDefinition : CUBE_KEYWORD coordinates ;

coordinates : coordinateComponent coordinateComponent coordinateComponent ;

coordinateComponent : NUMBER ('.'NUMBER)? ;

SPHERE_KEYWORD : 'sphere' ;

CUBE_KEYWORD : 'cube' ;

NUMBER : [0-9]+ ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines ;
