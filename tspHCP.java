import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * tspHCP reads a file with HCP input and then converts it 
 * to a tsp instance.
 * @author Matthew Cieslak and Richard Cerone
 */
public class tspHCP
{
	int[][] edges; //Holds the edge weights.
	int dimension; //How many nodes there are in the graph.
	ArrayList<Integer>vert_a = new ArrayList<Integer>(); //Start vertexes
	ArrayList<Integer>vert_b = new ArrayList<Integer>(); //End vertexes
	//Holds lower diagonal values.
	ArrayList<Integer>lowerDiagonal = new ArrayList<Integer>(); 
	
	/**
	 * Main method runs the program.
	 * @param args
	 */
	public static void main(String[] args)
	{
		tspHCP tsp = new tspHCP();
		tsp.readf_hcp("squareWithDiagonal.txt");
		tsp.populateEdge();
		tsp.getvals();
		tsp.createfile();
	}
	/**
	 * Creates a file in tsp format using the 
	 * HCP input.
	 */
	private void createfile()
	{
		Scanner in = new Scanner(System.in);
		System.out.println("provide filename");
		String fileName = in.nextLine();
		
		System.out.println("provide comment");
		String comment = in.nextLine();
		in.close();
		
		try
		{
			//Write to file.
			File file = new File(fileName + ".txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write("NAME:"+fileName+"\n");
			out.write("COMMENT:"+comment+"\n");
			out.write("TYPE:TSP\n");
			out.write("DIMENSION:"+edges.length+"\n");
			out.write("EDGE_WEIGHT_TYPE: EXPLICIT\n");
			out.write("EDGE_WEIGHT_FORMAT: LOWER_DIAG_ROW\n");
			out.write("EDGE_WEIGHT_SECTION\n");
			
			for(int i=0; i< lowerDiagonal.size(); i++)
			{
				String write_val = lowerDiagonal.get(i).toString();
				out.write(write_val+"\n");
			}
			out.write("EOF\n");
			out.close();
			System.out.println(file.getName() + " has been created");
		} 
		catch(IOException e)
		{
			System.out.println("cheese error");
		}
	}
	
	/**
	 * Reads the HCP file and populates edges matrix
	 * and vert_a and vert_b array.
	 * @param filename
	 */
	private void readf_hcp(String filename)
	{
		try
		{
			File file = new File(filename);
			BufferedReader bfrd = new BufferedReader(new FileReader(file));
			String current_line;
			boolean startread = false;
			while((current_line = bfrd.readLine()) != null)
			{
				//System.out.println(current_line);
				if(current_line.contains("DIMENSION :"))
				{
					String[] splt = current_line.split("DIMENSION :");
					String dim = splt[1].trim();
					dimension = Integer.parseInt(dim);
					edges = new int[dimension][dimension];
				}
				
				if(startread)
				{
					if(current_line.contains("EOF") || current_line.contains("-1"))
						break;
					else
					{
						String vertices[] = current_line.split("\\s+");
						
						if(vertices.length > 1)
						{
							String d1 = vertices[1].trim();
							String d2 = vertices[2].trim();
							int dim_a = Integer.parseInt(d1);
							int dim_b = Integer.parseInt(d2);
							vert_a.add(dim_a);
							vert_b.add(dim_b);
						}
					}
				}
				if(current_line.contains("EDGE_DATA_SECTION"))
				{
					startread = true;
				}
			}
			bfrd.close();
		} 
		catch(IOException exp)
		{
			exp.printStackTrace();
		}
	}
	
	/**
	 * Fills edge with 1, 2, or 0.  1 being an edge 
	 * between two verticies, 2 being no edge, and 0 
	 * being the edge to itself. 
	 */
	private void populateEdge()
	{
		for(int i=0; i<vert_a.size(); i++)
		{
			edges[(int) vert_b.get(i)-1][(int) vert_a.get(i)-1] = 1;
		}
		for(int i=0; i<vert_a.size(); i++)
		{
			edges[(int) vert_a.get(i)-1][(int) vert_b.get(i)-1] = 1;
		}
		for(int a=0; a<edges.length; a++)
		{
			for(int b=0; b<edges[a].length; b++)
			{
				if(a == b)
				{
					edges[a][b] = 0;
				}
				if(edges[a][b] == 0 && a != b)
				{
					edges[a][b] = 2;
				} 
				else
				{
					//Do nothing.
				}
			}
		}
	}
	/**
	 * Gets the lower diagonal matrix.
	 */
	private void getvals()
	{
		int row = 0;
		int col = 0;
		while(row < edges.length)
		{
			if(edges[row][col] == 0)
			{
	
				lowerDiagonal.add(edges[row][col]);
				row++;
				col = 0;
			} 
			else 
			{
				lowerDiagonal.add(edges[row][col]);
				col++;
			}
		}
	}
}   