import java.io.*;
/** Travis Braud
    includes the bonus for reading semi-colon
  **/
 
public class Lex {

	// Character classes
	public static final int LETTER =0;
	public static final int DIGIT =1;
	public static final int UNKONWN =2;
	
	// Token Codes
	public static final int INT_LIT =10;
	public static final int IDENT =11;
	public static final int ASSIGN_OP =20;
	public static final int  ADD_OP =21;
	public static final int SUB_OP =22;
	public static final int MULT_OP =23;
	public static final int DIV_OP =24;
	public static final int LEFT_PAREN =25;
	public static final int RIGHT_PAREN =26;
	public static final int EOF = 99 ;
	
	// Global Variables
	public static int charClass;
	public static char lexeme[]; //= new char[100];
	public static char nextChar;
	public static int lexLen;
	public static int token;
	public static int nextToken;
	



    // Read in file
    private static FileInputStream finput;
    private static File file;
  

    
     // functions
    public static void addChar(){
        if(lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] =0;
        }else{
            System.out.println("Error - lexeme is too long\n");
        }
    }
    public static void getChar() throws IOException {
       if(finput.available() >0){
    	  
           nextChar = (char)finput.read();
          
        	   
           if(Character.isLetter(nextChar)){
               charClass=LETTER;
           }else if(Character.isDigit(nextChar)){
               charClass= DIGIT;
           }else {
               charClass=UNKONWN;
           }
       }else {
           charClass = EOF;
       }
    }
    
   public static void nonBlank(){
       while(Character.isSpaceChar(nextChar)){
           try {
               getChar();
           } catch (IOException ex) {
              
           }
       }
   }
   public static int lex() throws IOException{
       lexLen =0;
       nonBlank();
       switch(charClass){
           case LETTER:
               addChar();
               getChar();
               while(charClass == LETTER || charClass == DIGIT){
                   addChar();
                   getChar();
               }
               nextToken =IDENT;
               break;
           case DIGIT:
               addChar();
               getChar();
               while(charClass==DIGIT){
                   addChar();
                   getChar();
               }
               nextToken = INT_LIT;
               break;
           case UNKONWN:
               lookup(nextChar);
               getChar();
               break;
               
           case EOF:
               nextToken = EOF;
               lexeme[0] = 'E';
               lexeme[1] = 'O';
               lexeme[2] = 'F';
               lexeme[3] = 0;
               System.out.println("End of File");
               break;
           default:
        	   System.out.println("IDK what's going on");
               
       }
       System.out.printf("Next token is: %d\n" + "Next lexeme is ", nextToken);
       for(int i=0; i< lexLen ; i++) {
    	   System.out.print(lexeme[i]);
       } System.out.println();
    	   
       
       return nextToken;
   }
    
    public static int lookup(char ch){
     
        switch(ch){
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case ';':
            	addChar();						//Bonus Checks for ';' 
            	nextToken = EOF;
            	break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
                        
        }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        lexLen =0;
        lexeme = new char[100];
      
   
        for(int i=0;i<100;i++){
            lexeme[i]='0';
        }
        file = new File("C:\\Users\\tbrau\\OneDrive\\Documents\\test1.txt");
        if(!(file.isFile() && file.canRead())){
            System.out.println(file.getName()+"can't be read");
            
        }
        if(!file.exists()){
            System.out.println("filetext.txt does not exist");
           
        }
        try{
            finput = new FileInputStream(file);
            //char current;
          
            while(finput.available() >0){
                getChar();
                lex();
            }
        } catch (FileNotFoundException ex) {
     
        } catch (IOException ex) {
          
        }
        
    }
    
}
