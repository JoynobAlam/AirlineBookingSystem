class Staff {
    int staffId;
    String name, role, assignedArea;

    Staff(int staffId, String name, String role, String assignedArea){
        this.staffId=staffId;
        this.name=name;
        this.role=role;
        this.assignedArea=assignedArea;
    }
    void showStaff(){
        System.out.println(".............Staff.............");
        System.out.println("Staff ID: "+staffId);
        System.out.println("Staff Name: "+name);
        System.out.println("Staff Roll: "+role);
        System.out.println("Area assigned for the staff: "+assignedArea);
    }
}
