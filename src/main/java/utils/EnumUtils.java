package utils;

import com.mnt.core.domain.DomainEnum;

public class EnumUtils {
	public static <T> String[] enumNameToStringArray(T[] values) {  
	    int i = 0;  
	    String[] result = new String[values.length];  
	    for (T value: values) {  
	        result[i++] = ((DomainEnum)value).getName();  
	    }  
	    return result;  
	}
	
	

}
