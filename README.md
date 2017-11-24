# Homework1-PSM

Homework 1: Parallel Mergesort

In questo homework si richiede di implementare e valutare sperimentalmente le prestazioni dell’algoritmo di
mergesort parallelo presentato ed analizzato in classe. L’implementazione deve essere basata sulla libreria Fork Join
di Java e deve contenere tre versioni dell’algoritmo:

• mergesort sequenziale;

• mergesort parallelo basato su merge sequenziale;

• mergesort parallelo basato su merge parallelo.

Input ed output. Il programma deve essere testato su sequenze di n interi, considerando diversi valori di n e due
diverse tipologie di input:
A. sequenze decrescenti contenenti i valori interi da n a 1;
B. sequenze di n numeri casuali.
Il valore n e la tipologia di input devono essere specificati da linea di comando. Il programma deve inoltre contenere
un metodo che verifichi che l’output sia corretto, ovvero che rispetti l’ordine crescente.

Analisi sperimentale. A corredo dell’implementazione, eseguite un’analisi sperimentale confrontando i tempi
richiesti dalle due implementazioni parallele con quelli dell’implementazione sequenziale e valutando lo speedup
ottenuto. Considerate esperimenti di tre tipi:
• Differenti tipologie di input: fissato n, i tempi di esecuzione dei diversi algoritmi variano per input di tipo A e
  input di tipo B?
• Diverse dimensioni dell’input: come varia lo speedup all’aumentare della dimensione n dell’input? Qual è il
  massimo n che riuscite a gestire facendo si che il tempo di esecuzione non sia superiore a 1 minuto o 5 minuti?
• Differenti cutoff: che impatto ha il sequential cutoff sulle prestazioni? Analizzate le prestazioni variando
  progressivamente il cutoff da 1 ad una costante che ritenete opportuna.
In ciascun esperimento, solo un parametro (tipologia di input, n, cutoff) va variato, mantenendo costanti gli altri. I
risultati ottenuti devono essere presentati in grafici o tabelle ed opportunamente discussi e motivati. I risultati sono
in linea con le vostre aspettative o riscontrate comportamenti anomali?

Instrumentazione del codice. Il codice deve essere instrumentato in modo da poter calcolare il numero di fork
effettuate e produrre il DAG di esecuzione, memorizzato come lista di archi. L’opzione di debugging deve essere
opzionale e deve essere disabilitata nei test prestazionali, in modo da non interferire con i normali tempi di esecuzione:
• Riportate in una tabella il numero di fork per i due algoritmi paralleli al variare del cutoff sequenziale;
• Rappresentate graficamente alcuni dei DAG prodotti, producendo la rappresentazione del grafo manualmente
  o, se preferite, tramite un tool per la visualizzazione di grafi (ad esempio Gephi: https://gephi.org/).Documentazione.
  A corredo dell’implementazione, inviatemi una breve relazione in formato pdf che illustri:
• le caratteristiche salienti dell’implementazione realizzata e le relative ottimizzazioni del codice;
• le caratteristiche della/e piattaforma/e su cui avete eseguito l’analisi sperimentale;
• i risultati sperimentali ottenuti, tramite tabelle e grafici opportunamente commentati;
• la modalità per compilare ed eseguire il programma (da linea di comando).

Modalità di svolgimento: potete lavorare in gruppi composti da al più 3 persone.
Deadline: 30 Dicembre 2017
