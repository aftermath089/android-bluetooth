import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Tetrahedron tetrahedron = new Tetrahedron();
        tetrahedron.sets1(10);
        tetrahedron.sets2(10);
        tetrahedron.sets3(10);
        tetrahedron.sethy1(10);
        tetrahedron.sethy2(10);
        tetrahedron.sethy3(10);
        System.out.println("volume: "+tetrahedron.volume());
        System.out.println("distance1: "+tetrahedron.hDistanceToP1());
        System.out.println("distance1: "+tetrahedron.hDistanceToP2());
        System.out.println("distance1: "+tetrahedron.hDistanceToP3());
    }
}