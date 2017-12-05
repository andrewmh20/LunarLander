public class ResetErrors extends Error {
    
    
    //TODO:This Class does not work as expected
    ResetErrors(){
        super();
    }
    
    

    
    @Override
    public void causeFailure(LunarModel lm) {
        lm = new LunarModel();
    }
    
}
