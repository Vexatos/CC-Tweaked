package dan200.computercraft.core.apis;

import dan200.computercraft.api.lua.LuaException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Various helpers for tables
 */
public final class TableHelper
{
    private TableHelper()
    {
        throw new IllegalStateException( "Cannot instantiate singleton " + getClass().getName() );
    }

    @Nonnull
    public static LuaException badKey( @Nonnull String key, @Nonnull String expected, @Nullable Object actual )
    {
        return badKey( key, expected, ArgumentHelper.getType( actual ) );
    }

    @Nonnull
    public static LuaException badKey( @Nonnull String key, @Nonnull String expected, @Nonnull String actual )
    {
        return new LuaException( "bad field '" + key + "' (" + expected + " expected, got " + actual + ")" );
    }

    public static double getNumberField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        Object value = table.get( key );
        if( value instanceof Number )
        {
            return ((Number) value).doubleValue();
        }
        else
        {
            throw badKey( key, "number", value );
        }
    }

    public static int getIntField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        Object value = table.get( key );
        if( value instanceof Number )
        {
            return (int) ((Number) value).longValue();
        }
        else
        {
            throw badKey( key, "number", value );
        }
    }

    public static double getRealField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        return checkReal( key, getNumberField( table, key ) );
    }

    public static boolean getBooleanField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        Object value = table.get( key );
        if( value instanceof Boolean )
        {
            return (Boolean) value;
        }
        else
        {
            throw badKey( key, "boolean", value );
        }
    }

    @Nonnull
    public static String getStringField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        Object value = table.get( key );
        if( value instanceof String )
        {
            return (String) value;
        }
        else
        {
            throw badKey( key, "string", value );
        }
    }

    @SuppressWarnings( "unchecked" )
    @Nonnull
    public static Map<Object, Object> getTableField( @Nonnull Map<?, ?> table, @Nonnull String key ) throws LuaException
    {
        Object value = table.get( key );
        if( value instanceof Map )
        {
            return (Map<Object, Object>) value;
        }
        else
        {
            throw badKey( key, "table", value );
        }
    }

    public static double optNumberField( @Nonnull Map<?, ?> table, @Nonnull String key, double def ) throws LuaException
    {
        Object value = table.get( key );
        if( value == null )
        {
            return def;
        }
        else if( value instanceof Number )
        {
            return ((Number) value).doubleValue();
        }
        else
        {
            throw badKey( key, "number", value );
        }
    }

    public static int optIntField( @Nonnull Map<?, ?> table, @Nonnull String key, int def ) throws LuaException
    {
        Object value = table.get( key );
        if( value == null )
        {
            return def;
        }
        else if( value instanceof Number )
        {
            return (int) ((Number) value).longValue();
        }
        else
        {
            throw badKey( key, "number", value );
        }
    }

    public static double optRealField( @Nonnull Map<?, ?> table, @Nonnull String key, double def ) throws LuaException
    {
        return checkReal( key, optNumberField( table, key, def ) );
    }

    public static boolean optBooleanField( @Nonnull Map<?, ?> table, @Nonnull String key, boolean def ) throws LuaException
    {
        Object value = table.get( key );
        if( value == null )
        {
            return def;
        }
        else if( value instanceof Boolean )
        {
            return (Boolean) value;
        }
        else
        {
            throw badKey( key, "boolean", value );
        }
    }

    public static String optStringField( @Nonnull Map<?, ?> table, @Nonnull String key, String def ) throws LuaException
    {
        Object value = table.get( key );
        if( value == null )
        {
            return def;
        }
        else if( value instanceof String )
        {
            return (String) value;
        }
        else
        {
            throw badKey( key, "string", value );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static Map<Object, Object> optTableField( @Nonnull Map<?, ?> table, @Nonnull String key, Map<Object, Object> def ) throws LuaException
    {
        Object value = table.get( key );
        if( value == null )
        {
            return def;
        }
        else if( value instanceof Map )
        {
            return (Map<Object, Object>) value;
        }
        else
        {
            throw badKey( key, "table", value );
        }
    }

    private static double checkReal( @Nonnull String key, double value ) throws LuaException
    {
        if( Double.isNaN( value ) )
        {
            throw badKey( key, "number", "nan" );
        }
        else if( value == Double.POSITIVE_INFINITY )
        {
            throw badKey( key, "number", "inf" );
        }
        else if( value == Double.NEGATIVE_INFINITY )
        {
            throw badKey( key, "number", "-inf" );
        }
        else
        {
            return value;
        }
    }
}
