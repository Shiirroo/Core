package de.shiro.system.action.manager;

public interface ActionResult<A> {

    public A getResult();

    static <A> ActionResult<A> of(A object){
        return () -> object;
    }

    static ActionResult<Boolean> Success(){
        return () -> true;
    }

    static ActionResult<Void> SuccessVoid(){
        return () -> (Void) null;
    }

    static ActionResult<Void> FailedVoid(){
        return () -> (Void) null;
    }

    static ActionResult<Boolean> Failed(){
        return () -> false;
    }

    static <A> ActionResult<A> Success(A object){
        return () -> object;
    }

    static <A> ActionResult<A> Failed(A object){
        return () -> object;
    }

    static ActionResult<String> Success(String Message){
        return () -> Message;
    }

    static ActionResult<String> Failed(String Message){
        return () -> Message;
    }

    // equals and hashcode
    public static <A> boolean equals(ActionResult<A> a, ActionResult<A> b){
        return a.getResult().equals(b.getResult());
    }
    public static <A> int hashCode(ActionResult<A> a){
        return a.getResult().hashCode();
    }

    public String toString();


}


