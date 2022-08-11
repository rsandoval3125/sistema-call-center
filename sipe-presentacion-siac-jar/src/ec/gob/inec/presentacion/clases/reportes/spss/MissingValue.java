package ec.gob.inec.presentacion.clases.reportes.spss;

public class MissingValue
{
  public static final int TYPE_NO_MISSING_VALUES = 0;
  public static final int TYPE_DESCRETE_MISSING_VALUE_1 = 1;
  public static final int TYPE_DESCRETE_MISSING_VALUE_2 = 2;
  public static final int TYPE_DESCRETE_MISSING_VALUE_3 = 3;
  public static final int TYPE_MISSING_VALUE_RANGE = -2;
  public static final int TYPE_RANGE_PLUS_A_VALUE = -3;
  private Object[] values = new Object[3];
  private int type = 0;
  
  public void setOneDescreteMissingValue(double paramDouble)
  {
    this.type = 1;
    this.values[0] = new Double(paramDouble);
  }
  
  public void setTwoDescreteMissingValues(double paramDouble1, double paramDouble2)
  {
    this.type = 2;
    this.values[0] = new Double(paramDouble1);
    this.values[1] = new Double(paramDouble2);
  }
  
  public void setThreeDescreteMissingValues(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.type = 3;
    this.values[0] = new Double(paramDouble1);
    this.values[1] = new Double(paramDouble2);
    this.values[2] = new Double(paramDouble3);
  }
  
  public void setOneDescreteMissingValue(String paramString)
  {
    this.type = 1;
    this.values[0] = paramString;
  }
  
  public void setTwoDescreteMissingValues(String paramString1, String paramString2)
  {
    this.type = 2;
    this.values[0] = paramString1;
    this.values[1] = paramString2;
  }
  
  public void setThreeDescreteMissingValues(String paramString1, String paramString2, String paramString3)
  {
    this.type = 3;
    this.values[0] = paramString1;
    this.values[1] = paramString2;
    this.values[2] = paramString3;
  }
  
  public void setMissingValueRange(double paramDouble1, double paramDouble2)
  {
    this.type = -2;
    this.values[0] = new Double(paramDouble1);
    this.values[1] = new Double(paramDouble2);
  }
  
  public void setRangePlusValue(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.type = -3;
    this.values[0] = new Double(paramDouble1);
    this.values[1] = new Double(paramDouble2);
    this.values[2] = new Double(paramDouble3);
  }
  
  public Object[] getValues()
  {
    return this.values;
  }
  
  public int getType()
  {
    return this.type;
  }
}


/* Location:              C:\Users\jaraujo\Documents\NetbeansMios\exportSpss\lib\spssw-1.84.jar!\com\pmstation\spss\MissingValue.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */