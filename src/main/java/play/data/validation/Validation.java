package play.data.validation;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Validation helpers.
 */
public class Validation {
    
    /**
     * The underlying JSR-303 validator.
     */
    private final static ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
    
    /**
     * Returns a JSR-303 Validator.
     */
    public static Validator getValidator() {
        return factory.getValidator();
    }
    
    
}