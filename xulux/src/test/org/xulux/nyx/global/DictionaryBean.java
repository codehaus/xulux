package org.xulux.nyx.global;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: DictionaryBean.java,v 1.1 2002-10-29 00:10:02 mvdb Exp $
 */
public class DictionaryBean
{
    
    private String name;
    private String street;
    private String city;
    private String ignorePrivate;
    private String ignoreProtected;
    
    /**
     * Constructor for DictionaryBean.
     */
    public DictionaryBean()
    {
        super();
    }

    /**
     * Returns the city.
     * @return String
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the street.
     * @return String
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Sets the city.
     * @param city The city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the street.
     * @param street The street to set
     */
    public void setStreet(String street)
    {
        this.street = street;
    }

    /**
     * Returns the ignore.
     * @return String
     */
    private String getIgnorePrivate()
    {
        return ignorePrivate;
    }

    /**
     * Returns the ignoreProtected.
     * @return String
     */
    protected String getIgnoreProtected()
    {
        return ignoreProtected;
    }

    /**
     * Sets the ignorePrivate.
     * @param ignorePrivate The ignorePrivate to set
     */
    public void setIgnorePrivate(String ignorePrivate)
    {
        this.ignorePrivate = ignorePrivate;
    }

    /**
     * Sets the ignoreProtected.
     * @param ignoreProtected The ignoreProtected to set
     */
    public void setIgnoreProtected(String ignoreProtected)
    {
        this.ignoreProtected = ignoreProtected;
    }

}
