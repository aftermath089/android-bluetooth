
class Tetrahedron{
    //reference https://keisan.casio.com/exec/system/1329962711
    private double height;
    private double s1,s2,s3;
    private double hy1,hy2,hy3;

    public double getHeight(){
        return height;
    }
    public double gets1(){
        return s1;
    }
    public double gets2(){
        return s2;
    }
    public double gets3(){
        return s3;
    }
    public double gethy1(){
        return hy1;
    }
    public double gethy2(){
        return hy2;
    }
    public double gethy3(){
        return hy3;
    }

    public void setHeight(double height){
        this.height=height;
    }
    public void sets1(double s1){
        this.s1=s1;
    }
    public void sets2(double s2){
        this.s2=s2;
    }
    public void sets3(double s3){
        this.s3=s3;
    }
    public void sethy1(double hy1){
        this.hy1=hy1;
    }
    public void sethy2(double hy2){
        this.hy2=hy2;
    }
    public void sethy3(double hy3){
        this.hy3=hy3;
    }

    public double volume(){
        //calculates volume of a tetrahedron from six edge lengths
        double volume;
        double pows1 = Math.pow(s1,2); //a4
        double pows2 = Math.pow(s2,2); //a5
        double pows3 = Math.pow(s3,2); //a6
        double powhy1 = Math.pow(hy1,2); //a1
        double powhy2 = Math.pow(hy2,2); //a2
        double powhy3 = Math.pow(hy3,2); //a3

        volume = Math.sqrt(
            (
                powhy1*pows2*(powhy2+powhy3+pows1+pows3-powhy1-pows2)+
                powhy2*pows3*(powhy1+powhy3+pows1+pows2-powhy2-pows3)+
                powhy3*pows1*(powhy1+powhy2+pows2+pows3-powhy3-pows1)-
                powhy1*powhy2*pows1-
                powhy2*powhy3*pows2-
                powhy1*powhy3*pows3-
                pows1*pows2*pows3
            )/144
        );
        return volume;
    }
    
    private double heron(double vertex1, double vertex2, double vertex3){
        //calculates the Area of triangle
        double s =  (vertex1+vertex2+vertex3)/2;
        double area = Math.sqrt(s*(s-vertex1)*(s-vertex2)*(s-vertex3));
        return area;
    }

    public double getEstimateY(){
        //get estimation of y position
        double estimateY;
        estimateY = volume()*3/heron(s1,s2,s3);
        return estimateY;
    }

    public double hDistanceToP1(){
        double height = getEstimateY();
        double distance = Math.sqrt(
            Math.pow(hy1,2)-Math.pow(height,2)
        );
        return distance;
    }

    public double hDistanceToP2(){
        double height = getEstimateY();
        double distance = Math.sqrt(
            Math.pow(hy2,2)-Math.pow(height,2)
        );
        return distance;
    }

    public double hDistanceToP3(){
        double height = getEstimateY();
        double distance = Math.sqrt(
            Math.pow(hy3,2)-Math.pow(height,2)
        );
        return distance;
    }
}