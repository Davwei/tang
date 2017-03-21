package com.Davi.poems.tools;

import java.io.UnsupportedEncodingException;

/**
 * Created by David on 2016/12/28.
 */
public class transform {

    /**
     * use to transform from big5 to gb2312
     * @param s
     * @return
     */
    public static String big5ToChinese( String s )
    {
        try{
            if ( s == null || s.equals( "" ) )
                return("");
            String newstring = null;
            newstring = new String( s.getBytes( "big5" ), "gb2312" );
            return(newstring);
        }
        catch ( UnsupportedEncodingException e )
        {
            return(s);
        }
    }


    public static String ChineseTobig5( String s )
    {
        try{
            if ( s == null || s.equals( "" ) )
                return("");
            String newstring = null;
            newstring = new String( s.getBytes( "gb2312" ), "big5" );
            return(newstring);
        }
        catch ( UnsupportedEncodingException e )
        {
            return(s);
        }
    }
}
