package algorithmExecutors;

import metalMVC.Metal;
import metalMVC.MetalModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author angel
 */
@SuppressWarnings("serial")
public class CST_opt extends AbstractDoubleProblem {
    private String ProjectPath;
    private MetalModel model;


    // Constructor de la clase DoubleProblem
    public CST_opt(MetalModel model, List<Double> lowerbounds, List<Double> upperbounds, String ProjectPath)  {
        this.model = model;
        setNumberOfVariables(model.getNumOfVariables());
        setNumberOfObjectives(1);
        setNumberOfConstraints(0) ;
        setName("prueba_GA");

        //List<Double> lowerLimit = Arrays.asList(0.1, 0.1) ;
        //List<Double> upperLimit = Arrays.asList(4.0, 4.0) ;

        setLowerLimit(lowerbounds);
        setUpperLimit(upperbounds);
        this.ProjectPath = ProjectPath;
        //setProjectPath(ProjectPath);
    }

    // Método que evalua la función de coste a partir de una entrada (solution)
    @Override
    public void evaluate(DoubleSolution solution) {

        int numberOfVariables = getNumberOfVariables();

        //La variable Double x guarda los valores que se van a evaluar en la función de coste (
        double[] x = new double[numberOfVariables] ;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = solution.getVariableValue(i) ;
        }
        ////////////////La siguientes lineas no te hacen falta porque es para lanzar el programa externo
        /////////////// asi que por ahora para lanzar una funcion de coste sencilla,
        /////////////// no le hagas caso y luego ya te las explicare
    /*
    //Escribir los nuevos valores en la macro
    String path=ProjectPath + "\\macro.bas";
        try {
            Write(x,path);
        } catch (IOException ex) {
            Logger.getLogger(CST_opt.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    //Lanzar CST (poner timers)
    String CST_path = "\"C:\\Program Files (x86)\\CST STUDIO SUITE 2015\\CST DESIGN ENVIRONMENT.exe\"";
    String macroCST = "\"" + ProjectPath + "\\macro.bas\"";
    String commandLaunchCST = CST_path + " -m " + macroCST;

        try {
            //Execute launcher VirtualBox command
            executeCommand(commandLaunchCST);
        } catch (InterruptedException ex) {
            Logger.getLogger(CST_opt.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    //Lectura de los resultados
    String path_results =ProjectPath + "\\Results.txt";
    double[][] MS11=null;
        try {
            MS11 = ReadResults(path_results);
        } catch (IOException ex) {
            Logger.getLogger(CST_opt.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    //EvaluaciÃ³n de los resultados en la funciÃ³n de coste
    double fcost;
    double[] frequency = new double [MS11.length];
    double[] S11_data = new double [MS11.length];
    
    for (int i = 0; i < MS11.length ; i++) {
      frequency[i] = MS11[i][0] ;
    }
    
    for (int i = 0; i < MS11.length ; i++) {
      S11_data[i] = MS11[i][1] ;
    }
    */

        //Funcion de coste (muy sencilla) es una función que el optimizador va a minimizar
        /*double fcost = -x[0]*5 +x[1]*20;*/
        // TODO: Do this for every fcost
        Map<String, Double> vars = new HashMap<String, Double>();
        for (int i = 0; i< model.getNumOfVariables(); i++) {
            vars.put(model.getNameOfVariables().elementAt(i),x[i]);
        }
        Expression e = new ExpressionBuilder(model.getObjFuncts(1))
                .build()
                .variables(vars);
        double fcost = e.evaluate();

        System.out.println(solution.getObjective(0));
        System.out.println(x[0]);

        /*
        for (int i = 0; i < S11_data.length; i++) {
        if(S11_data[i]<-30){
        fcost=fcost+1;
        }else if(S11_data[i]<-20){
        fcost=fcost+10;
        }else if(S11_data[i]<-10){
        fcost=fcost+1000;
        }else if(S11_data[i]<-5){
        fcost=fcost+5000;
        }else
        fcost=fcost+10000;
        }*/

        Jmetal_cst.scores.add(fcost);

        solution.setObjective(0, fcost);
    }

    //////// El resto de código son métodos que utilizaba para llamar al programa externo, estos también te lo explicaré
    //////// más adelante
   /* 
  public static void Write(double array[],String path) throws FileNotFoundException, IOException{
        //path = "C:\\Users\\angel\\Desktop\\PruebasJava\\macro.bas";
        File file =new File(path);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
       
        //Variables que se van modificar
        String[] name_variables=new String[array.length];
        name_variables[0]="wc";
        name_variables[1]="hc";
        //Valor de la variables que se van a modificar
        double[] value_variables=new double[array.length];
        value_variables[0]=array[0];
        value_variables[1]=array[1];
        
        for (int index=0; index<array.length; index++){
        String lineref;
        String line;
        
        lineref = raf.readLine();

        
         while(lineref != null)
        {
           String[] temp=lineref.split("=");
           if(temp[0].equals(name_variables[index])){
               String newline=temp[0]+"="+(Double.toString(value_variables[index]));
               String oldMacro="";
               raf.seek(0);
               while((line=raf.readLine()) != null){
                   oldMacro+=line+"\r\n";
               }
               String newasignation=oldMacro.replaceAll(lineref,newline); 
               FileWriter writer = new FileWriter(path);
               writer.write(newasignation);
               writer.close();
               break;
           }
           
           lineref = raf.readLine(); 
        }
      
        raf.close();
        raf=new RandomAccessFile(file, "rw");
                
        }
        raf.close();

    }
  
  public static String executeCommand(String command) throws InterruptedException {

		String s = null;

        try {
            
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);
            
            int timeout = 30;//En minutos
            if(!p.waitFor(timeout, TimeUnit.MINUTES)) {
            //timeout - kill the process. 
            p.destroy(); // consider using destroyForcibly instead
            }
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                //System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            //System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            //System.exit(-1);
        }
        return null;
}
  
  public static double[][] ReadResults(String path_results) throws FileNotFoundException, IOException {
        //path_results = "C:\\Users\\angel\\Desktop\\PruebasJava\\Results.txt";
        
        BufferedReader br;
        FileReader fr = new FileReader(path_results);
        br = new BufferedReader(fr);
        
        String line;
        
        //Salto las dos primeras lÃ­neas
        line = br.readLine();
        line = br.readLine();
        line = br.readLine();
        
        int index=0;
        double Results[][]=new double [1001][2];
      
        while(line != null)
        {
            String[] lines=line.trim().split(" ");
            String frequency = lines[0];
            String magnitude = lines[lines.length-1];
            //Se guarda los datos en una matriz NÂºpuntos x 2 (frequencia y magnitud)
            if(index<=1000){
                Results[index][0]=Double.parseDouble(frequency);//Frecuencia
                Results[index][1]=Double.parseDouble(magnitude);//Magnitud
                index++;
            }
            
            //System.out.println(Results[index]);
            line = br.readLine();
           
        }
        //System.out.println(Arrays.deepToString(Results));
        fr.close();
        
        return Results;
  } 
 */
}