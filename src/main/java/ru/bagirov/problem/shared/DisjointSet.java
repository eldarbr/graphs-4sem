package problem.shared;

public class DisjointSet {
    Subset[] data;
    public DisjointSet(int size) {
        data = new Subset[size];
        for (int i = 0; i < size; i++) {
            data[i] = new Subset();
            makeSet(i);
        }
    }
    private void makeSet(int n) {
        data[n].parent = n;
        data[n].rank = 0;
    }
    public int findSet(int n) {
        if (data[n].parent == n) {
            return n;
        }
        data[n].parent = findSet(data[n].parent);
        return data[n].parent;
    }
    public void unionSet(int n, int m) {
        n = findSet(n);
        m = findSet(m);
        if (n != m) {
            if (data[n].rank < data[m].rank) {
                int tmp = n;
                n = m;
                m = tmp;
            }
            data[m].parent = n;
            if (data[m].rank == data[n].rank) {
               data[n].rank++;
            }
        }
    }
}

class Subset {
    int parent = 0;
    int rank = 0;
}
