// Source : https://github.com/jszheng/py3antlr4book/blob/master/04-Expr/Expr.g4
grammar Hello;

/** The start rule; begin parsing here. */
start:   statement+ END;

statement
    :   class
    |   function
    |   builtin
    |   functionCall
    |   expression
    |   decdef
    |   while
    |   ifThenElseStatement
    |   END
    |   COMMENT
    ;

builtin:
    'print' '(' (expression | functionCall | builtin) ')'         #print
    | 'str' '(' expression ')'         #castStr
    | 'int' '(' expression ')'         #castInt
    | 'id' '(' (expression | functionCall | builtin) ')'          #callId
    | 'input' '(' expression* ')'       #callInput
    | 'super''(' functionparameterpasslist ')'                                  #callSuper
    ;

function:
    'def' ID functionparameterdeclarationlist ':' functionstatementlist END;

functionparameterdeclarationlist
   : '(' expression? (',' expression)* ')'
   ;

functionstatementlist:
    statement*;

functionCall:
    (classInstance=ID '.')? funcName=ID '(' functionparameterpasslist ')';

functionparameterpasslist
   : (expression | functionCall)? (',' (expression | functionCall))*
   ;

expression
 : expression op=( MUL | DIV ) expression #MULDIV
 | expression op=( ADD | SUB ) expression #ADDSUB
 | expression op=GT expression            #GREATER
 | expression op=GE expression            #GREATEREQ
 | expression op=LT expression            #LESS
 | expression op=LE expression            #LESSEQ
 | expression op=EQ expression            #EQUALS
 | expression op=NE expression            #NOTEQUALS
 | op=NOT expression                      #NOT
 | expression op=( AND | OR ) expression  #ANDOR
 | '(' expression ')'                     #expressionNested
 | NUMBER                                 #expressionNumber
 | STRING                                 #expressionString
 | BOOLEAN                                #expressionBoolean
 | ID                                     #expressionID
 | SELF                                   #expressionSelf
 | RETURN expression                      #expressionReturn
 ;

decdef:
    ID '=' builtin | ID '=' functionCall | ID '=' expression;

class:
    'class' className=ID '(' parentClass=ID? ')' ':' function*  END;

while:
    'while' '(' expression ')' ':' statement* END;

ifThenElseStatement:
    if elif* else? END;

if:
    'if' '(' expression ')' ':' statement*;

elif:
    'elif'  '(' expression ')' ':' statement* ;
else:
    'else'  ':' statement*;

block:
    '{' statement* '}';

END    : '#end';
NEWLINE:'\r'? '\n' ;
COMMENT: '#' ~[\n]* '\n';
MUL    : '*';
DIV    : '/';
ADD    : '+';
SUB    : '-';
GT     : '>';
GE     : '>=';
LT     : '<';
LE     : '<=';
EQ     : '==';
NE     : '!=';
NOT    : 'not';
AND    : 'and';
OR     : 'or';
BOOLEAN: 'True' | 'False';
NUMBER : ('-')?[0-9]+ ( '.' [0-9]+ )?;
STRING : '\'' ~'\''* '\'';
SELF   : 'self';
RETURN : 'return';
ID     :   ('self.')?[a-zA-Z_][a-zA-Z0-9_]*;
SPACE  : [ \t\r\n] -> skip;
