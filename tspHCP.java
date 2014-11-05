import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
 
/**
 * @author Matthew Cieslak and Richard Cerone 
 */
public class tspHCP 
{
    
    int[][] edges;
    int dimension;
 
    ArrayList vert_a = new ArrayList<Integer>();
    ArrayList vert_b = new ArrayList<Integer>();
    ArrayList vals = new ArrayList<Integer>();
    public static void main(String[] args)
    {   
         
        tspHCP tsp = new tspHCP();
        tsp.readf_hcp("petersen.txt");
        for(int i=0; i<tsp.vert_a.size(); i++){
            System.out.print(tsp.vert_a.get(i) + " ");
            System.out.println(tsp.vert_b.get(i));
        }
        tsp.populateEdge();
        tsp.getvals();
        tsp.createfile();
    }
    private void createfile(){
    	Scanner in = new Scanner(System.in);
    	System.out.println("provide filename");
    	String fileName = in.nextLine();
    	System.out.println("provide comment");
    	String comment = in.nextLine();
    	in.close();
    	
    	try{
    		File file = new File(fileName + ".txt");
    		BufferedWriter out = new BufferedWriter(new FileWriter(file));
    		out.write("NAME:"+fileName+"\n");
    		out.write("COMMENT:"+comment+"\n");
    		out.write("TYPE:TSP\n");
    		out.write("DIMENSION:"+edges.length+"\n");
    		out.write("EDGE_WEIGHT_TYPE: EXPLICIT\n");
    		out.write("EDGE_WEIGHT_FORMAT: LOWER_DIAG_ROW\n");
    		out.write("EDGE_WEIGHT_SECTION\n");
    		for(int i=0; i< vals.size(); i++){
    			String write_val = vals.get(i).toString();
    			out.write(write_val+"\n");
    		}
    		out.write("EOF\n");
    		out.close();
    		
    	} catch(IOException e)
    	{
    		System.out.println("cheese error");
    	}    	
    	
    	
    }
    @SuppressWarnings("unchecked")
    private void readf_hcp(String filename){
         
        try{
            File file = new File(filename);
            BufferedReader bfrd = new BufferedReader(new FileReader(file));
            String current_line;
            boolean startread = false;
            while((current_line = bfrd.readLine()) != null){
                //System.out.println(current_line);
                if(current_line.contains("DIMENSION :")){
                    String[] splt = current_line.split("DIMENSION :");
                    String dim = splt[1].trim();
                     dimension = Integer.parseInt(dim);
                     edges = new int[dimension][dimension];
                }
                if(startread){
                    if(current_line.contains("EOF") || current_line.contains("-1"))
                        break;
                    else{
                        String vertices[] = current_line.split("\\s+");
                        if(vertices.length > 1){
                            String d1 = vertices[1].trim();
                            String d2 = vertices[2].trim();
                            int dim_a = Integer.parseInt(d1);
                            int dim_b = Integer.parseInt(d2);
                            vert_a.add(dim_a);
                            vert_b.add(dim_b);
                        }
                    }
                }
                if(current_line.contains("EDGE_DATA_SECTION")){
                    startread = true;
                }
            }
             
        } catch(IOException exp){
             
        }
    }
     
    private void populateEdge(){
        for(int i=0; i<vert_a.size(); i++){
                edges[(int) vert_b.get(i)-1][(int) vert_a.get(i)-1] = 1;
            }
        for(int i=0; i<vert_a.size(); i++){
            edges[(int) vert_a.get(i)-1][(int) vert_b.get(i)-1] = 1;
        } 
        for(int a=0; a<edges.length; a++){
            for(int b=0; b<edges[a].length; b++){
            	if(a == b){
            		edges[a][b] = 0;
            	}
                if(edges[a][b] == 0 && a != b){
                    edges[a][b] = 2;
                } else {
                     
                }
                }
            }
     
            for(int j=0; j<edges.length; j++){
                for(int x=0; x<edges[j].length; x++){
                    System.out.print(edges[j][x] + " ");
                }
                System.out.println();
            }
        }
    private void getvals(){
    	int row = 0;
    	int col = 0;
    	while(row < edges.length){
    		if(edges[row][col] == 0){
    			vals.add(edges[row][col]);
    			row++;
    			col = 0;
    			} else {
    				vals.add(edges[row][col]);
    				col++;
    			}
    		}
    	}
}
    