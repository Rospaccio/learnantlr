grammar ShapePlacer;

program : (shapeDefinition)+ ;

shapeDefinition : sphereDefinition | cubeDefinition | regionDefinition ;

sphereDefinition : SPHERE_KEYWORD coordinates ;

cubeDefinition : CUBE_KEYWORD coordinates ;

regionDefinition : REGION_KEYWORD coordinates '{' program '}' ;

coordinates : coordinateComponent coordinateComponent coordinateComponent ;

coordinateComponent : NUMBER ('.'NUMBER)? ;

SPHERE_KEYWORD : 'sphere' ;

CUBE_KEYWORD : 'cube' ;

REGION_KEYWORD : 'region' ;

NUMBER : [0-9]+ ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines ;
