package ec.gob.inec.presentacion.clases.reportes.spss;

class SPSSVariable {

    private String name;
    private int type;
    private int width;
    private int decimals;
    private String label;
    private String longName;
    private int columns;
    private int align;
    private int measure;
    private boolean fake;

    public SPSSVariable(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, String paramString3) {
        this(paramString1, paramInt1, paramInt2, paramInt3, paramString2, -1, -1, -1, paramString3);
    }

    public SPSSVariable(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, int paramInt4, int paramInt5, int paramInt6, String paramString3) {
        setName(paramString1);
        setType(paramInt1);
        setWidth(paramInt2);
        setDecimals(paramInt3);
        setLabel(paramString2);
        setColumns(paramInt4);
        setAlign(paramInt5);
        setMeasure(paramInt6);
        this.longName = paramString3;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = normalize(paramString);
    }

    private String normalize(String paramString) {
        String str = paramString.toUpperCase();
        str = stripNonASCII(str);
        if (str.length() == 0) {
            str = getNewNormalizedName();
        }
        str = pad(str, 8);
        return str;
    }

    private String pad(String paramString, int paramInt) {
        String str = paramString;
        if (str == null) {
            str = "";
        }
        if (str.length() > paramInt) {
            str = str.substring(0, paramInt);
        } else {
            for (int i = str.length(); i < paramInt; i++) {
                str = str + " ";
            }
        }
        return str;
    }

    private String stripNonASCII(String paramString) {
        String str = "";
        char[] arrayOfChar = paramString.toCharArray();
        for (int i = 0; i < arrayOfChar.length; i++) {
            char c = arrayOfChar[i];
            if (c <= '') {
                str = str + c;
            }
        }
        return str;
    }

    private String getNewNormalizedName() {
        return "VAR";
    }

    public int getType() {
        return this.type;
    }

    public void setType(int paramInt) {
        this.type = paramInt;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int paramInt) {
        if (paramInt < 0) {
            this.width = 0;
        } else if (paramInt > 255) {
            this.width = 255;
        } else {
            this.width = paramInt;
        }
    }

    public int getDecimals() {
        return this.decimals;
    }

    public void setDecimals(int paramInt) {
        if (paramInt < 0) {
            this.decimals = 0;
        } else if (paramInt > 255) {
            this.decimals = 255;
        } else {
            this.decimals = paramInt;
        }
    }

    public boolean isLabeled() {
        return getLabelLength() > 0;
    }

    public String getLabel() {
        if ((this.label == null) || (this.label.length() <= 0)) {
            return null;
        }
        return this.label;
    }

    public int getLabelLength() {
        if ((this.label == null) || (this.label.length() <= 0)) {
            return 0;
        }
        return this.label.length();
    }

    public void setLabel(String paramString) {
        if ((paramString != null) && (paramString.length() > 120)) {
            this.label = paramString.substring(0, 120);
        } else {
            this.label = paramString;
        }
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int paramInt) {
        if (paramInt <= 0) {
            this.columns = 8;
        } else {
            this.columns = paramInt;
        }
    }

    public int getAlign() {
        return this.align;
    }

    public void setAlign(int paramInt) {
        switch (paramInt) {
            case 0:
            case 1:
            case 2:
                this.align = paramInt;
                break;
            default:
                this.align = 0;
        }
    }

    public int getMeasure() {
        return this.measure;
    }

    public void setMeasure(int paramInt) {
        switch (paramInt) {
            case 1:
            case 2:
            case 3:
                this.measure = paramInt;
                break;
            default:
                this.measure = 1;
        }
    }

    public boolean equals(Object paramObject) {
        if ((paramObject instanceof String)) {
            return ((String) paramObject).equals(this.longName);
        }
        if ((paramObject instanceof SPSSVariable)) {
            return ((SPSSVariable) paramObject).getLongName().equals(this.longName);
        }
        return false;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName(String paramString) {
        this.longName = paramString;
    }

    public boolean isFake() {
        return this.fake;
    }

    public void setFake(boolean paramBoolean) {
        this.fake = paramBoolean;
    }
}


/* Location:              C:\Users\jaraujo\Documents\NetbeansMios\exportSpss\lib\spssw-1.84.jar!\com\pmstation\spss\SPSSVariable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */
