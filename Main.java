import java.util.*;

// Clase Grafo
class Grafo {
    private int[][] matrizAdyacencia;
    private int numVertices;

    // Constructor
    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        matrizAdyacencia = new int[numVertices][numVertices];
        // Inicializar la matriz de adyacencia con infinito (representado por Integer.MAX_VALUE)
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizAdyacencia[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    // Método para agregar una arista ponderada
    public void agregarArista(int origen, int destino, int peso) {
        matrizAdyacencia[origen][destino] = peso;
    }

    // Método para imprimir la matriz de adyacencia
    public void imprimirMatrizAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(matrizAdyacencia[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    // Método para encontrar el camino mínimo utilizando Bellman-Ford
    public void caminoMinimo(int origen) {
        // Inicializar arreglo de distancias con infinito
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        // La distancia al origen es 0
        distancias[origen] = 0;

        // Iterar para encontrar el camino mínimo
        for (int k = 0; k < numVertices - 1; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (matrizAdyacencia[i][j] != Integer.MAX_VALUE) {
                        if (distancias[i] != Integer.MAX_VALUE && distancias[i] + matrizAdyacencia[i][j] < distancias[j]) {
                            distancias[j] = distancias[i] + matrizAdyacencia[i][j];
                        }
                    }
                }
            }
        }

        // Verificar si hay ciclos negativos
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] != Integer.MAX_VALUE) {
                    if (distancias[i] != Integer.MAX_VALUE && distancias[i] + matrizAdyacencia[i][j] < distancias[j]) {
                        System.out.println("El grafo contiene un ciclo negativo.");
                        return;
                    }
                }
            }
        }

        // Imprimir el camino mínimo
        System.out.println("Camino mínimo desde el vértice " + (origen + 1) + ":");
        for (int i = 0; i < numVertices; i++) {
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println("No hay camino desde el vértice " + (origen + 1) + " al vértice " + (i + 1));
            } else {
                System.out.println("Distancia al vértice " + (i + 1) + ": " + distancias[i]);
            }
        }
    }

    // Método para determinar si hay ciclos utilizando Depth First Search (DFS)
    public List < List < Integer >> encontrarCiclos() {
        List < List < Integer >> ciclos = new ArrayList < > ();
        boolean[] visitado = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            if (!visitado[i]) {
                List < Integer > caminoActual = new ArrayList < > ();
                dfsEncontrarCiclos(i, visitado, new boolean[numVertices], caminoActual, ciclos);
            }
        }
        return ciclos;
    }

    private void dfsEncontrarCiclos(int v, boolean[] visitado, boolean[] enCamino, List < Integer > caminoActual, List < List < Integer >> ciclos) {
        visitado[v] = true;
        enCamino[v] = true;
        caminoActual.add(v);

        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[v][i] != Integer.MAX_VALUE) {
                if (enCamino[i]) {
                    List < Integer > ciclo = new ArrayList < > ();
                    int inicio = caminoActual.indexOf(i);
                    for (int j = inicio; j < caminoActual.size(); j++) {
                        ciclo.add(caminoActual.get(j));
                    }
                    ciclos.add(ciclo);
                } else if (!visitado[i]) {
                    dfsEncontrarCiclos(i, visitado, enCamino, caminoActual, ciclos);
                }
            }
        }

        enCamino[v] = false;
        caminoActual.remove(caminoActual.size() - 1);
    }

    // Método para calcular la altura del "árbol" desde un nodo dado
    public int altura(int nodo) {
        boolean[] visitado = new boolean[numVertices];
        return alturaRec(nodo, visitado);
    }

    private int alturaRec(int nodo, boolean[] visitado) {
        visitado[nodo] = true;
        int alturaMax = 0;
        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[nodo][i] != Integer.MAX_VALUE && !visitado[i]) {
                alturaMax = Math.max(alturaMax, alturaRec(i, visitado));
            }
        }
        return alturaMax + 1;
    }

    // Método para realizar un recorrido en preorden desde un nodo dado
    public void recorridoPreorden(int nodo) {
        boolean[] visitado = new boolean[numVertices];
        System.out.print("Recorrido en preorden desde el nodo " + (nodo + 1) + ": ");
        recorridoPreordenRec(nodo, visitado);
        System.out.println();
    }

    private void recorridoPreordenRec(int nodo, boolean[] visitado) {
        visitado[nodo] = true;
        System.out.print((nodo + 1) + " ");
        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[nodo][i] != Integer.MAX_VALUE && !visitado[i]) {
                recorridoPreordenRec(i, visitado);
            }
        }
    }

    // Método para encontrar el camino más corto entre dos vértices usando Dijkstra
    public void caminoMasCortoDijkstra(int origen, int destino) {
        int[] distancias = new int[numVertices];
        boolean[] visitado = new boolean[numVertices];
        int[] predecesor = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecesor, -1);
        distancias[origen] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> distancias[v]));
        pq.add(origen);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (visitado[u]) continue;
            visitado[u] = true;

            for (int v = 0; v < numVertices; v++) {
                if (matrizAdyacencia[u][v] != Integer.MAX_VALUE && !visitado[v]) {
                    int nuevaDistancia = distancias[u] + matrizAdyacencia[u][v];
                    if (nuevaDistancia < distancias[v]) {
                        distancias[v] = nuevaDistancia;
                        predecesor[v] = u;
                        pq.add(v);
                    }
                }
            }
        }

        // Imprimir el camino más corto y su distancia
        if (distancias[destino] == Integer.MAX_VALUE) {
            System.out.println("No hay camino desde el vértice " + (origen + 1) + " al vértice " + (destino + 1));
        } else {
            System.out.println("Camino más corto (Dijkstra) desde el vértice " + (origen + 1) + " al vértice " + (destino + 1) + ":");
            List<Integer> camino = new ArrayList<>();
            for (int v = destino; v != -1; v = predecesor[v]) {
                camino.add(v);
            }
            Collections.reverse(camino);
            for (int i = 0; i < camino.size(); i++) {
                System.out.print((camino.get(i) + 1));
                if (i < camino.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("\nDistancia total: " + distancias[destino]);
        }
    }
}

// Clase Principal
public class Main {
    public static void main(String[] args) {
        // Crear un objeto Grafo
        Grafo grafo = new Grafo(13);

        // Agregar las aristas con sus pesos
        grafo.agregarArista(0, 1, 200);
        grafo.agregarArista(0, 12, 250);
        grafo.agregarArista(0, 8, 290);
        grafo.agregarArista(1, 5, 360);
        grafo.agregarArista(1, 2, 190);
        grafo.agregarArista(2, 5, 250);
        grafo.agregarArista(2, 4, 190);
        grafo.agregarArista(2, 0, 300);
        grafo.agregarArista(3, 2, 180);
        grafo.agregarArista(4, 5, 300);
        grafo.agregarArista(4, 9, 400);
        grafo.agregarArista(5, 10, 350);
        grafo.agregarArista(5, 11, 300);
        grafo.agregarArista(6, 3, 300);
        grafo.agregarArista(6, 2, 250);
        grafo.agregarArista(6, 0, 150);
        grafo.agregarArista(7, 6, 200);
        grafo.agregarArista(7, 0, 220);
        grafo.agregarArista(8, 7, 180);
        grafo.agregarArista(8, 12, 180);
        grafo.agregarArista(9, 3, 200);
        grafo.agregarArista(10, 9, 700);
        grafo.agregarArista(10, 4, 200);
        grafo.agregarArista(11, 1, 150);
        grafo.agregarArista(12, 11, 100);
        grafo.agregarArista(12, 1, 200);

        // Imprimir la matriz de adyacencia
        grafo.imprimirMatrizAdyacencia();
        System.out.println();

        // Encontrar y mostrar el camino mínimo desde el vértice 1 (0)
        grafo.caminoMinimo(0);
        System.out.println();

        // Encontrar y mostrar los ciclos en el grafo
        List < List < Integer >> ciclos = grafo.encontrarCiclos();
        System.out.println("Ciclos encontrados:");
        for (List < Integer > ciclo: ciclos) {
            for (int i = 0; i < ciclo.size(); i++) {
                System.out.print((ciclo.get(i) + 1));
                if (i < ciclo.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        System.out.println();

        // Calcular y mostrar la altura del "árbol" desde el vértice 1 (0)
        int altura = grafo.altura(0);
        System.out.println("Altura del árbol desde el vértice 1: " + altura);
        System.out.println();

        // Realizar un recorrido en preorden desde el vértice 1 (0)
        grafo.recorridoPreorden(0);
        System.out.println();

        // Encontrar y mostrar el camino más corto entre el vértice 1 (0) y el vértice 3 (2) usando Dijkstra
        grafo.caminoMasCortoDijkstra(0, 2);
        System.out.println();
    }
}