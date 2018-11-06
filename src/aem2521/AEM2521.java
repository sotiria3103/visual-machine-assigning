
package aem2521;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sotiria Antaranian
 */

public class AEM2521 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
        List<Client> clients=new ArrayList<>(); //λίστα με όλους τους πελάτες
        List<Integer> totalCores=new ArrayList<>(); //λίστα με τις απαιτήσεις σε πυρήνες του κάθε πελάτη
        List<Float> totalOffers=new ArrayList<>(); //λίστα με τις συνολικές προσφορές του κάθε πελάτη
        int []VMs={1,2,7,11};
        
        Scanner fileScanner = new Scanner(new File("input.txt"));
        int W=fileScanner.nextInt(); //διαθέσιμοι πυρήνες σε όλους τους servers
        
        while (fileScanner.hasNext())
        {
            int cores=fileScanner.nextInt(); //απαιτήσεις σε πυρήνες
            float sOffer=fileScanner.nextFloat(); //προσφορά τιμής ανά πυρήνα
            boolean add = totalCores.add(cores);
            Client c=new Client(cores,sOffer);
            c.calculateVM(VMs,cores); //1η λειτουργία:υπολογισμός των VMs για κάθε πελάτη
            add=totalOffers.add(c.getTotalOffer());
            add = clients.add(c);
        }
        
        int i=1;
        for(Client cl:clients)
        {
           System.out.println("Client"+i+ ":" +cl.getCore1()+" 1-core, "+cl.getCore2()+" 2-core, "+cl.getCore7()+" 7-core and "+cl.getCore11()+" 11-core VMs." );
           i++;
        }
        //μετατροπή των λιστων σε πίνακες επειδή η συνάρτηση knapSack δέχεται πίνακες
        float[] tO=new float[totalOffers.size()];
        for(int j=0;j<totalOffers.size();j++)
            tO[j]=totalOffers.get(j);
        int[] tC=new int[totalCores.size()];
        for(int j=0;j<totalCores.size();j++)
            tC[j]=totalCores.get(j);
        
        KnapSack ks=new KnapSack(); 
        int n=clients.size();
        float value=ks.knapSack(W, tC, tO, n); //2η λειτουργία:εύρεση αποδεκτών πελατών και συνολικού κέρδους
       
        Collections.reverse(ks.accepted); //επειδή στη συνάρτηση knapSack ο πίνακας διατρέχεται από κάτω προς τα πάνω, τα στοιχεία εισάγονται στη λίστα ανάποδα
        String accepted1 = Arrays.toString(ks.accepted.toArray()).replace("[", "").replace("]", "");
        System.out.println("Clients accepted:"+accepted1);
        System.out.println("Total amount: "+value);
    }
    
}
