
public class CountingSort {

    public static void countingSort(int[] arr) {
        //Cacolo degli elementi max e min
        int max = arr[0];
        int min = arr[0];
        int i = 1;
        for(; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
            else if(arr[i] < min)
                min = arr[i];
        }
        int[] arr2 = new int[max-min+1];
        for(i = 0; i < arr2.length; i++)
            arr2[i]=0;    //inizializza a zero gli elementi di C
        for(i=0; i < arr.length; i++)
            arr2[arr[i]-min]++;                    //aumenta il numero di volte che si è incontrato il valore
        //Ordinamento in base al contenuto dell'array delle frequenze C
        int k=0;                             //indice per l'array A
        for(i=0; i<arr2.length; i++) {
            while(arr2[i]>0){                     //scrive C[i] volte il valore (i+min) nell'array A
                arr[k]=i+min;
                k++;
                arr2[i]--;
            }
        }
    }

}