package delta.games.lotro.maps.data;

/**
 * Marker.
 * @author DAM
 */
public class Marker
{
  private int _id;
  private GeoPoint _position;
  private Labels _labels;
  private int _categoryCode;
  private String _comment;

  /**
   * Constructor.
   */
  public Marker()
  {
    _labels=new Labels();
  }

  /**
   * Get the identifier of this marker.
   * @return a map-local identifier.
   */
  public int getId()
  {
    return _id;
  }

  /**
   * Set the identifier of this marker.
   * @param id Identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the position of this marker.
   * @return a geographic position or <code>null</code> if not set.
   */
  public GeoPoint getPosition()
  {
    return _position;
  }

  /**
   * Set the position of this marker.
   * @param position the position to set (may be <code>null</code>).
   */
  public void setPosition(GeoPoint position)
  {
    _position=position;
  }

  /**
   * Get the labels for this marker.
   * @return a labels manager.
   */
  public Labels getLabels()
  {
    return _labels;
  }

  /**
   * Get the default label for this marker.
   * @return a label.
   */
  public String getLabel()
  {
    return _labels.getLabel();
  }

  /**
   * Get the category code for this marker.
   * @return a category code (<code>0</code> as default).
   */
  public int getCategoryCode()
  {
    return _categoryCode;
  }

  /**
   * Set the category for this marker.
   * @param categoryCode Cateory code.
   */
  public void setCategoryCode(int categoryCode)
  {
    _categoryCode=categoryCode;
  }

  /**
   * Get the comment for this marker.
   * @return a comment or <code>null</code> if not set.
   */
  public String getComment()
  {
    return _comment;
  }

  /**
   * Set the comment for this marker.
   * @param comment the comment to set (may be <code>null</code>).
   */
  public void setComment(String comment)
  {
    _comment=comment;
  }

  @Override
  public String toString()
  {
    String label=_labels.getLabel();
    return _id+":"+label+" @"+_position+", category="+_categoryCode+", comment="+_comment;
  }
}
