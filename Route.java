class Route {
    String source;
    String destination;
    Route(String source, String destination){
        this.source=source;
        this.destination=destination;
    }    
    void showRoute(){
        System.out.println("Flying from "+source+" to "+destination);
    }
}
