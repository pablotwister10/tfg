package algorithmExecutors;

import metalMVC.MetalModel;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CstOptInteger Class
 *
 * Implements CST optimization with type of variable Integer
 *
 * TODO: set parameters
 * TODO: Check if lowerBounds and upperBounds is needed or can be got from model.getMin(Max)IntervalOfVariablesDouble
 * @param <> Type of variable (Double, Integer)
 * @param <> Type of problem (DoubleProblem, IntegerProblem)
 */
public class CstOptInteger extends AbstractIntegerProblem {

    private MetalModel model;


    /**
     * CstOptInteger constructor
     *
     * @param model MetalModel for data abstraction
     * @param lowerBounds lower limits for variables
     * @param upperBounds upper limits for variables
     */
    CstOptInteger(MetalModel model, List<Integer> lowerBounds, List<Integer> upperBounds)  {
        this.model = model;

        setNumberOfVariables(model.getNumOfVariables());
        setNumberOfObjectives(model.getNumOfObjFuncts());
        setNumberOfConstraints(0);
        setName("prueba_GA");
        

        setLowerLimit(lowerBounds);
        setUpperLimit(upperBounds);
    }


    // Evaluation method extracting data from macro and setting it to solution
    @Override
    public void evaluate(IntegerSolution solution) {

        // Vector of variables to evaluate cost function
        double[] x = new double[model.getNumOfVariables()];

        // Initializing and extracting the value for the variables
        for (int i=0; i<model.getNumOfVariables(); i++) {
            x[i] = solution.getVariableValue(i);
        }

        // Modifying the variable value for the macro
        String path = model.getProjectPath() + "\\macro.bas";
        try {
            modifyVariableValues(x,path);
        } catch (IOException ex) {
            Logger.getLogger(CstOptInteger.class.getName()).log(Level.SEVERE,null, ex);
        }

        // Commands to run CST
        String CST_path = "\"C:\\Program Files (x86)\\CST STUDIO SUITE 2015\\CST DESIGN ENVIRONMENT.exe\"";
        String macroCST = "\"" + model.getProjectPath() + "\\macro.bas\"";
        String commandLaunchCST = CST_path + " -m " + macroCST;

        // Run CST to start optimizing
        try {
            //Execute launcher VirtualBox command
            executeCommand(commandLaunchCST);
        } catch (InterruptedException ex) {
            Logger.getLogger(CstOptInteger.class.getName()).log(Level.SEVERE,null, ex);
        }

        // Optimization results extraction

        // Name of variables to optimize
        String[] nameObjectiveVars = new String[model.getNumOfObjFuncts()];
        for (int i=0; i<model.getNumOfObjFuncts(); i++) {
            String robet = model.getObjFuncts(i+1);
            switch (model.getObjFuncts(i+1)) {
                case "S11": nameObjectiveVars[i] = "S1,1"; break;
                case "S12": nameObjectiveVars[i] = "S1,2"; break;
                case "S21": nameObjectiveVars[i] = "S2,1"; break;
                case "S22": nameObjectiveVars[i] = "S2,2"; break;
                default: nameObjectiveVars[i] = model.getObjFuncts(i+1); break;
            }
        }

        // Extracting results from txt file created with CST
        for (int i=0; i<model.getNumOfObjFuncts(); i++) {

            // Reading current file and extracting current objective variable results
            String currentResultsFile = model.getProjectPath() + "\\Results_" + nameObjectiveVars[i] + ".txt";
            double[][] currentObjectiveVar = null;
            try {
                currentObjectiveVar = extractResults(currentResultsFile);
            } catch (IOException ex) {
                Logger.getLogger(algorithmExecutors.CstOptDouble.class.getName()).log(Level.SEVERE,null, ex);
            }

            // Initialization for current result evaluation
            double fcost;
            double[] currentObjectiveVarFrequency = new double [currentObjectiveVar.length];
            double[] currentObjectiveVarData = new double [currentObjectiveVar.length];

            // The Result.txt files are two columns with frequency (on the left) and amplitude (on the right)
            for (int j=0; j<currentObjectiveVar.length; j++) {
                currentObjectiveVarFrequency[j] = currentObjectiveVar[j][0];
            }
            for (int j=0; j<currentObjectiveVar.length; j++) {
                currentObjectiveVarData[j] = currentObjectiveVar[j][1];
            }

            // Current result evaluation
            fcost = 0;
            // Minimize S11 and S22
            if (nameObjectiveVars[i].equalsIgnoreCase("S11")
                    || nameObjectiveVars[i].equalsIgnoreCase("S1,1")
                    || nameObjectiveVars[i].equalsIgnoreCase("S22")
                    || nameObjectiveVars[i].equalsIgnoreCase("S2,2")) {
                for (int j=0; j<currentObjectiveVarData.length; j++) {
                    if(currentObjectiveVarData[j]<-30)
                        fcost = fcost+1;
                    else if(currentObjectiveVarData[j]<-20)
                        fcost = fcost+10;
                    else if(currentObjectiveVarData[j]<-10)
                        fcost = fcost+1000;
                    else if(currentObjectiveVarData[j]<-5)
                        fcost = fcost+5000;
                    else
                        fcost = fcost+10000;
                }
            }
            // Maximize S12 and S21
            else if (nameObjectiveVars[i].equalsIgnoreCase("S12")
                    || nameObjectiveVars[i].equalsIgnoreCase("S1,2")
                    || nameObjectiveVars[i].equalsIgnoreCase("S21")
                    || nameObjectiveVars[i].equalsIgnoreCase("S2,1")) {
                for (int j=0; j<currentObjectiveVarData.length; j++) {
                    if(currentObjectiveVarData[j]<-30)
                        fcost = fcost+10000;
                    else if(currentObjectiveVarData[j]<-20)
                        fcost = fcost+5000;
                    else if(currentObjectiveVarData[j]<-10)
                        fcost = fcost+1000;
                    else if(currentObjectiveVarData[j]<-5)
                        fcost = fcost+10;
                    else
                        fcost = fcost+1;
                }
            }

            // Saving results and scores
            model.getMetalSolution().scores[i].add(fcost);
            // Setting algorithm objective for next evaluation
            solution.setObjective(i,fcost);

        }

    }

    private void modifyVariableValues(double array[],String path) throws FileNotFoundException, IOException {
        File file =new File(path);
        RandomAccessFile raf = new RandomAccessFile(file,"rw");

        String[] name_variables = new String[array.length];
        double[] value_variables = new double[array.length];

        // Optimizing functions from GUI
        for (int i=0; i<model.getNumOfVariables(); i++) {
            //Variables que se van modificar
            name_variables[i] = model.getNameOfVariables().elementAt(i);
            //Valor de la variables que se van a modificar
            value_variables[i] = array[i];
        }

        for (int index=0; index<array.length; index++){
            String lineref;
            String line;

            lineref = raf.readLine();

            while(lineref != null) {
                String[] temp=lineref.split("=");
                if(temp[0].equals(name_variables[index])) {
                    String newline=temp[0]+"="+(Double.toString(value_variables[index]));
                    String oldMacro="";
                    raf.seek(0);
                    while((line=raf.readLine()) != null) {
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

    private static void executeCommand(String command) throws InterruptedException {

        String s = null;

        try {
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);

            int timeout = 30;//En minutos
            if(!p.waitFor(timeout, TimeUnit.MINUTES)) {
                //timeout - kill the process.
                p.destroy(); // consider using destroyForcibly instead
            }

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command: ");
            while ((s = stdInput.readLine()) != null) {
                //System.out.println(s);
            }

            // read any errors from the attempted command
            System.err.println("Here is the standard error of the command (if any): ");
            while ((s = stdError.readLine()) != null) {
                System.err.println(s);
            }

            //System.exit(0);
        }
        catch (IOException e) {
            System.err.println("exception happened - here's what I know: ");
            e.printStackTrace();
            //System.exit(-1);
        }

    }

    private static double[][] extractResults(String resultsFile) throws FileNotFoundException, IOException {

        BufferedReader br;
        FileReader fr = new FileReader(resultsFile);
        br = new BufferedReader(fr);

        String line;

        // Salto las dos primeras lineas
        line = br.readLine();
        line = br.readLine();
        line = br.readLine();

        int index=0;
        double Results[][]=new double [1001][2];

        while(line != null) {
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

}