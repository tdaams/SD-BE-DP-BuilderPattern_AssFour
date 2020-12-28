package nl.novi.dpcc.builder.domain;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

//    public UserBuilder(){
//
//    }

    public UserBuilder withFirstName(String fn){
        this.firstName = fn;
        return this;
    }

    public UserBuilder withLastName(String ln){
        this.lastName = ln;
        return this;
    }

    public UserBuilder withUserName(String un){
        this.username = un;
        return this;
    }

    public UserBuilder withEmail(String em){
        this.email = em;
        return this;
    }

    public UserBuilder withPassword(String pw){
        this.password = pw;
        return this;
    }

    public UserBuilder withPasswordRepeatCheck(String pwrpt){
        if(!this.password.equalsIgnoreCase(pwrpt)){
            throw new RuntimeException("Passwords do not match!");
        }
        return this;
    }

    public User build(){
        return new User(firstName,lastName,username,email,password);
    }

    public User buildwithChecks(String pwchk){
        if(!password.equalsIgnoreCase(pwchk)){
            throw new RuntimeException("Password is not the same.");
        }
        return new User(username,email,password,firstName,lastName);
    }


}
