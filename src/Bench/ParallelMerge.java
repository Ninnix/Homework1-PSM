package Bench;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    private int[] array;
    private int[] aux;
    private int sxLow;
    private int sxHigh;
    private int dxLow;
    private int dxHigh;
    private int auxLow;
    private int auxHigh;
    private int SEQUENTIAL_CUTOFF = 1024;

    ParallelMerge(int[] arr,int sxL,int sxH,int dxL, int dxH, int[] aux, int auxL, int auxH) {
        array = arr;
        this.aux = aux;
        sxLow = sxL;
        sxHigh = sxH;
        dxLow = dxL;
        dxHigh = dxH;
        auxLow = auxL;
        auxHigh = auxH;
    }

    @Override
    protected void compute() {
        int lenSx = sxHigh - sxLow;
        int lenDx = dxHigh - dxLow;
        if (lenSx < lenDx) { //scambio gli indici in modo da avere sempre array di sinistra piu' grande
            int appoggio;

            appoggio = sxLow;
            sxLow = dxLow;
            dxLow = appoggio;

            appoggio = sxHigh;
            sxHigh = dxHigh;
            dxHigh = appoggio;

            appoggio = lenSx;
            lenSx = lenDx;
            lenDx = appoggio;
        }

        if (lenSx <= SEQUENTIAL_CUTOFF) {
            //caso base ottimizzato nel caso di cutoff ad 1
            if (SEQUENTIAL_CUTOFF == 1){
                if (lenSx == 1 && lenDx == 1) {
                    if (aux[sxLow] <= aux[dxLow]) {
                        array[auxLow] = aux[sxLow];
                        array[auxLow+1] = aux[dxLow];
                    } else {
                        array[auxLow] = aux[dxLow];
                        array[auxLow+1] = aux[sxLow];
                    }
                    return;
                }
                if (lenSx == 1 && lenDx == 0) {
                    array[auxLow] = aux[sxLow];
                    return;
                }
            }
            Sorting.serialMerge2(array,sxLow,sxHigh,dxLow,dxHigh,aux,auxLow,auxHigh);
            return;
        }
        else {
            int sxMed = (sxHigh + sxLow) / 2; //mediano sottoarray piu' grande(sinistra)
            int dxInd = Sorting.binarySearch(aux[sxMed], aux, dxLow, dxHigh); //posizione mediano nel sottoarray di destra
            int auxInd = (sxMed - sxLow) + (dxInd - dxLow); //numero elementi (indice) piu' piccoli del sxMed
            ParallelMerge left = new ParallelMerge(array, sxLow, sxMed, dxLow, dxInd, aux, auxLow,auxLow + auxInd);
            ParallelMerge right = new ParallelMerge(array, sxMed, sxHigh, dxInd, dxHigh, aux, auxLow + auxInd, auxHigh);
            left.fork();
            //thread halving
            right.compute();
            left.join();
        }
    }
}
