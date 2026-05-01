class User {

    String id;
    String firstname,lastname, password, birthdate;
    User(String id, String firstname, String lastname,String birthdate, String password){
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.birthdate=birthdate;
        this.password=password;
    }
    void showUser(){
        System.out.println("............User Details............");
        System.out.println("User name: "+firstname+" "+lastname);
        System.out.println("User ID: "+id);
        System.out.println("User Birthdate: "+birthdate);
    }
      boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
